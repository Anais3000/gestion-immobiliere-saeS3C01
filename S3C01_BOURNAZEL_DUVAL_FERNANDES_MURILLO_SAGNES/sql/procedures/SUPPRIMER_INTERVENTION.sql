-- Supprime une intervention ainsi que toutes les autres entrées associées

create or replace PROCEDURE SUPPRIMER_INTERVENTION (P_IDINTERV SAE_INTERVENTION.ID_INTERVENTION%TYPE) 
IS

    INTERV SAE_INTERVENTION%ROWTYPE;

BEGIN

    SELECT * INTO INTERV
    FROM SAE_INTERVENTION
    WHERE ID_INTERVENTION = P_IDINTERV;

    IF(P_IDINTERV IS NULL) THEN
        raise_application_error(-20001,'Tous les parametres doivent etre renseignes');
    END IF;

    -- Paiement
    DELETE FROM SAE_PAIEMENT
    WHERE ID_INTERVENTION = P_IDINTERV;

    -- Necessiter (interventions pour biens)
    DELETE FROM SAE_NECESSITER
    WHERE ID_INTERVENTION = P_IDINTERV;
    
    -- Charge locataire si entretien ou ordures
    IF(INTERV.ENTRETIEN_PC = 1 OR INTERV.ORDURES = 1) THEN
        DELETE FROM SAE_CHARGE
        WHERE ID_INTERVENTION = P_IDINTERV;
    END IF;

    -- Intervention
    DELETE FROM SAE_INTERVENTION
    WHERE ID_INTERVENTION = P_IDINTERV;

END;