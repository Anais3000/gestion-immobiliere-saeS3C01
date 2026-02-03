--calcul le total de l'ajustement qu'il reste à payer pour le locataire
-- pour un bien donné et un locataire donné, selon une date de début d'ajustement

create or replace FUNCTION TOTAL_AJUSTEMENTS_LOYER_RESTANTS(
    P_IDBIEN SAE_BIEN_LOUABLE.ID_BIEN%TYPE, 
    P_IDLOCATAIRE SAE_LOCATAIRE.ID_LOCATAIRE%TYPE, 
    P_DATEDEBUT SAE_LOUER.DATE_DEBUT%TYPE
) RETURN NUMBER AS
    vAjustement SAE_LOUER.AJUSTEMENT_LOYER%TYPE;
    vMoisDebutAjustement SAE_LOUER.MOIS_DEBUT_AJUSTEMENT%TYPE;
    vMoisFinAjustement SAE_LOUER.MOIS_FIN_AJUSTEMENT%TYPE;
    vTotalAjustementsRestants NUMBER := 0;
    
    vLoyer NUMBER;
    vProvision NUMBER;
    vDateMoisCourant DATE;
    vPaiementCorrect INT;
    
    -- Exception personnalisée pour l'erreur -20008
    e_pas_historique EXCEPTION;
    PRAGMA EXCEPTION_INIT(e_pas_historique, -20008);
    
BEGIN
    -- On récupère les infos d'ajustement 
    BEGIN
        SELECT AJUSTEMENT_LOYER, MOIS_DEBUT_AJUSTEMENT, MOIS_FIN_AJUSTEMENT
        INTO vAjustement, vMoisDebutAjustement, vMoisFinAjustement
        FROM SAE_LOUER
        WHERE ID_BIEN = P_IDBIEN
        AND ID_LOCATAIRE = P_IDLOCATAIRE
        AND DATE_DEBUT = P_DATEDEBUT;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20005,'La location n''existe pas pour ces paramètres.');
    END;
    
    -- Si jamais eu d'ajustement retourne 0
    IF vAjustement = 0 OR vMoisDebutAjustement IS NULL OR vMoisFinAjustement IS NULL THEN
        RETURN 0;
    END IF;
    
    -- Parcourir chaque mois DEPUIS LE DÉBUT DE L'AJUSTEMENT jusqu'à la fin
    vDateMoisCourant := TRUNC(vMoisDebutAjustement, 'MM');
    
    WHILE vDateMoisCourant <= TRUNC(vMoisFinAjustement, 'MM') LOOP
        BEGIN
            -- Récupérer le loyer et la provision applicables pour ce mois
            DERNIERS_LOYER_PROVISION(P_IDBIEN, vDateMoisCourant, vLoyer, vProvision);
            
            -- Vérifier si le paiement avec ajustement a été fait pour ce mois
            SELECT COUNT(*) INTO vPaiementCorrect
            FROM SAE_PAIEMENT
            WHERE ID_BIEN = P_IDBIEN
            AND LIBELLE = 'loyer'
            AND TRUNC(MOIS_CONCERNE, 'MM') = vDateMoisCourant
            AND MONTANT = vLoyer + vProvision + vAjustement;
            
            -- Si l'ajustement n'a pas été payé pour ce mois
            IF vPaiementCorrect = 0 THEN
                vTotalAjustementsRestants := vTotalAjustementsRestants + vAjustement;
            END IF;
            
        EXCEPTION
            WHEN e_pas_historique THEN
                raise_application_error(-20009, 
                    'Impossible de calculer les ajustements restants : pas d''historique de loyer pour le mois ' || 
                    TO_CHAR(vDateMoisCourant, 'MM/YYYY'));
        END;
        
        -- Passer au mois suivant
        vDateMoisCourant := ADD_MONTHS(vDateMoisCourant, 1);
    END LOOP;
    
    RETURN vTotalAjustementsRestants;
    
END TOTAL_AJUSTEMENTS_LOYER_RESTANTS;