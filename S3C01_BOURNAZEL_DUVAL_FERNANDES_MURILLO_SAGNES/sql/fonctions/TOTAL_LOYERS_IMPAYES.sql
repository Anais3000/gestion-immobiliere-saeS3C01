--calcul le montant des loyers+provisions impayes pour un bien sur une periode donnée
-- et pour un locataire donné

create or replace FUNCTION TOTAL_LOYERS_IMPAYES(
    P_IDBIEN SAE_BIEN_LOUABLE.ID_BIEN%TYPE, 
    P_IDLOCATAIRE SAE_LOCATAIRE.ID_LOCATAIRE%TYPE, 
    P_DATEDEBUT SAE_LOUER.DATE_DEBUT%TYPE
) RETURN NUMBER AS 
    vNombreMoisEcoules INT;
    vTotalImpayes NUMBER := 0;
    vLoyer NUMBER;
    vProvision NUMBER;
    vDateMoisCourant DATE;
    vPaiementExiste INT;
    
    e_pas_historique EXCEPTION;
    PRAGMA EXCEPTION_INIT(e_pas_historique, -20008);
    
BEGIN
    -- ATTENTION cette fonction part du principe qu'un loyer est dû dès une nouvelle période de loyer entamée (dès le premier du mois)
    
    -- Calculer le nombre de mois écoulés depuis le début de la location
    vNombreMoisEcoules := FLOOR(MONTHS_BETWEEN(TRUNC(SYSDATE, 'MM'), TRUNC(P_DATEDEBUT, 'MM'))) + 1;
    
    -- Si aucun mois écoulé, retourner 0
    IF vNombreMoisEcoules <= 0 THEN
        RETURN 0;
    END IF;
    
    -- Parcourir chaque mois depuis la date de début
    vDateMoisCourant := TRUNC(P_DATEDEBUT, 'MM');
    
    FOR i IN 1..vNombreMoisEcoules LOOP
        -- Vérifier si le loyer de ce mois a été payé
        SELECT COUNT(*) INTO vPaiementExiste
        FROM SAE_PAIEMENT
        WHERE ID_BIEN = P_IDBIEN
        AND LIBELLE = 'loyer'
        AND TRUNC(MOIS_CONCERNE, 'MM') = vDateMoisCourant;
        
        -- Si le loyer n'a pas été payé pour ce mois
        IF vPaiementExiste = 0 THEN
            BEGIN
                -- Récupérer le loyer et la provision applicables pour ce mois
                DERNIERS_LOYER_PROVISION(P_IDBIEN, vDateMoisCourant, vLoyer, vProvision);
                
                -- Ajouter au total
                vTotalImpayes := vTotalImpayes + vLoyer + vProvision;
                
            EXCEPTION
                WHEN e_pas_historique THEN
                    raise_application_error(-20009, 'Impossible de calculer les impayés : pas d''historique de loyer pour le mois ' || TO_CHAR(vDateMoisCourant, 'MM/YYYY'));
            END;
        END IF;
        
        vDateMoisCourant := ADD_MONTHS(vDateMoisCourant, 1);
    END LOOP;
    
    RETURN vTotalImpayes;
    
END TOTAL_LOYERS_IMPAYES;