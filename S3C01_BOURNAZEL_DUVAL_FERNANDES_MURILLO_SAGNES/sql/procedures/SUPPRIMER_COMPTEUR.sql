-- Supprime un compteur ainsi que toutes les autres entrées associées
create or replace PROCEDURE SUPPRIMER_COMPTEUR(P_ID_COMPTEUR SAE_COMPTEUR.ID_COMPTEUR%TYPE) AS

BEGIN

    -- Relevé compteur
    DELETE FROM SAE_RELEVE_COMPTEUR
    WHERE ID_COMPTEUR = P_ID_COMPTEUR;

    -- Compteur
    DELETE FROM SAE_COMPTEUR
    WHERE ID_COMPTEUR = P_ID_COMPTEUR;
    
END;