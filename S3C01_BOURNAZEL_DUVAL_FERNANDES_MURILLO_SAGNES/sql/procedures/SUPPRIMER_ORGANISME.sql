-- Supprime un organisme ainsi que toutes les autres entrées associées

create or replace PROCEDURE SUPPRIMER_ORGANISME(P_NUM_SIRET SAE_ORGANISME.NUM_SIRET%TYPE) AS

BEGIN

    -- Intervention
    FOR I IN (SELECT ID_INTERVENTION
              FROM SAE_INTERVENTION
              WHERE NUM_SIRET = P_NUM_SIRET)
    LOOP
        SUPPRIMER_INTERVENTION(I.ID_INTERVENTION);
    END LOOP;

    -- Organisme
    DELETE FROM SAE_ORGANISME
    WHERE NUM_SIRET = P_NUM_SIRET;
    
END;