-- Supprime un garant ainsi que toutes les autres entrées associées

create or replace PROCEDURE SUPPRIMER_GARANT(P_ID_GARANT SAE_GARANT.ID_GARANT%TYPE) AS

BEGIN

    -- Louer (contrats de loc)
    UPDATE SAE_LOUER
    SET ID_GARANT = NULL
    WHERE ID_GARANT = P_ID_GARANT;
    
    -- Garant
    DELETE FROM SAE_GARANT
    WHERE ID_GARANT = P_ID_GARANT;
    
END;