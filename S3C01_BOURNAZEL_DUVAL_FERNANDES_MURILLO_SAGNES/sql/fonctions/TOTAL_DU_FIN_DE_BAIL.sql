--calcul le total du par le locataire en fin de bail afin d'effectuer le solde de tout comptes
-- pour un bien et un locataire donné, une date de début d'ajustement, un montant de dégradations,
-- et des dates de début et de fin de période pour la régularisation des charges incluse

create or replace FUNCTION TOTAL_DU_FIN_DE_BAIL(P_IDBIEN SAE_BIEN_LOUABLE.ID_BIEN%TYPE, 
                                                P_IDLOCATAIRE SAE_LOCATAIRE.ID_LOCATAIRE%TYPE, 
                                                P_DATEDEBUT SAE_LOUER.DATE_DEBUT%TYPE,
                                                P_MONTANTDEGRADATIONS NUMBER,
                                                P_DATEDEBUTPERIODE date,
                                                P_DATEFINPERIODE date) RETURN NUMBER AS 
             
    vTotalChargesPayees NUMBER;
    vTotalProvisionsRecues NUMBER;
    vTotalLoyersImpayes NUMBER;
    vTotalAjustementsRestants NUMBER;
    
    vDepotGarantie SAE_LOUER.DEPOT_DE_GARANTIE%TYPE;
                                                
BEGIN

    vTotalChargesPayees := TOTAL_CHARGES_SUR_PERIODE(P_IDBIEN, P_DATEDEBUTPERIODE, P_DATEFINPERIODE);
    
    vTotalProvisionsRecues := TOTAL_PROVISIONS_SUR_PERIODE(P_IDBIEN, P_DATEDEBUTPERIODE, P_DATEFINPERIODE);
    
    vTotalLoyersImpayes := TOTAL_LOYERS_IMPAYES(P_IDBIEN, P_IDLOCATAIRE, P_DATEDEBUT);
    
    vTotalAjustementsRestants := TOTAL_AJUSTEMENTS_LOYER_RESTANTS(P_IDBIEN, P_IDLOCATAIRE, P_DATEDEBUT);
    
    SELECT DEPOT_DE_GARANTIE INTO vDepotGarantie
    FROM SAE_LOUER
    WHERE ID_BIEN = P_IDBIEN
    AND DATE_DEBUT = P_DATEDEBUT;
    
    RETURN vTotalChargesPayees - vTotalProvisionsRecues + vTotalLoyersImpayes + vTotalAjustementsRestants + P_MONTANTDEGRADATIONS - vDepotGarantie;

EXCEPTION

    WHEN NO_DATA_FOUND THEN
        raise_application_error(-20006,'Aucune location n''existe pour ces paramètres.');

END TOTAL_DU_FIN_DE_BAIL;