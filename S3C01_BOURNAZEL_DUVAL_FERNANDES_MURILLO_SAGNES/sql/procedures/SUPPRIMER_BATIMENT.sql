-- Supprime un batiment ainsi que toutes les autres entrées associées

create or replace PROCEDURE SUPPRIMER_BATIMENT (P_ID_BATIMENT SAE_BATIMENT.ID_BATIMENT%TYPE) 
IS

BEGIN

    IF(P_ID_BATIMENT IS NULL) THEN
        raise_application_error(-20001,'Tous les parametres doivent etre renseignes');
    END IF;
    
    -- Charge
    DELETE FROM SAE_CHARGE
    WHERE ID_BATIMENT = P_ID_BATIMENT;

    -- Bien Louable
    FOR bl IN (SELECT sae_bien_louable.id_bien
                FROM sae_bien_louable
                where id_batiment = P_ID_BATIMENT) LOOP
         SUPPRIMER_BIEN_LOUABLE(bl.id_bien);
    END LOOP;

    -- Intervention
    FOR i IN (SELECT sae_intervention.id_intervention
                FROM sae_intervention
                where id_batiment = P_ID_BATIMENT) LOOP
         SUPPRIMER_INTERVENTION(i.id_intervention);
    END LOOP;
    
    -- Compteur
    FOR c IN (SELECT sae_compteur.id_compteur
                FROM sae_compteur
                where id_batiment = P_ID_BATIMENT) LOOP
         SUPPRIMER_COMPTEUR(c.id_compteur);
    END LOOP;
    
    -- Assurance
    DELETE FROM SAE_ASSURANCE
    WHERE ID_BATIMENT = P_ID_BATIMENT;

    -- Batiment
    DELETE FROM SAE_BATIMENT
    WHERE ID_BATIMENT = P_ID_BATIMENT;

END;