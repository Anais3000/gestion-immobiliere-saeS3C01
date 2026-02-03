-- Supprime un locataire ainsi que toutes les autres entrées associées

create or replace PROCEDURE SUPPRIMER_LOCATAIRE (P_ID_LOCATAIRE SAE_LOCATAIRE.ID_LOCATAIRE%TYPE) 
IS

BEGIN

    IF(P_ID_LOCATAIRE IS NULL) THEN
        raise_application_error(-20001,'Tous les parametres doivent etre renseignes');
    END IF;

    -- Garant (optionnel mais mieux)
    FOR g IN (SELECT sae_garant.id_garant FROM sae_garant, sae_Louer
                where sae_garant.id_garant = sae_louer.id_garant
                and sae_louer.id_locataire = P_ID_LOCATAIRE) LOOP
         SUPPRIMER_GARANT(g.id_garant);
    END LOOP;

    -- Louer
    DELETE FROM SAE_LOUER
    WHERE ID_LOCATAIRE = P_ID_LOCATAIRE;

    -- Locataire
    DELETE FROM SAE_LOCATAIRE
    WHERE ID_LOCATAIRE = P_ID_LOCATAIRE;

END;