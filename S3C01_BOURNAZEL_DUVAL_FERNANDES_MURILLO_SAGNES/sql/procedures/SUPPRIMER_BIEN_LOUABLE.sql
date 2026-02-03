-- Supprime un bien louable ainsi que toutes les autres entrées associées

create or replace PROCEDURE SUPPRIMER_BIEN_LOUABLE(P_ID_BIEN SAE_BIEN_LOUABLE.ID_BIEN%TYPE) AS

BEGIN

    -- Necessiter (intervention)
    DELETE FROM SAE_NECESSITER
    WHERE ID_BIEN = P_ID_BIEN;
    
    -- Louer
    DELETE FROM SAE_LOUER
    WHERE ID_BIEN = P_ID_BIEN;

    -- Paiement
    DELETE FROM SAE_PAIEMENT
    WHERE ID_BIEN = P_ID_BIEN;
    
    -- Diagnostic
    DELETE FROM SAE_DIAGNOSTICS
    WHERE ID_BIEN = P_ID_BIEN;
    
    -- Compteur
    FOR C IN(SELECT ID_COMPTEUR
             FROM SAE_COMPTEUR
             WHERE ID_BIEN = P_ID_BIEN)
    LOOP
        SUPPRIMER_COMPTEUR(C.ID_COMPTEUR);
    END LOOP;
    
    -- Charge
    DELETE FROM SAE_CHARGE
    WHERE ID_BIEN = P_ID_BIEN;
    
    -- Historique des loyers
    DELETE FROM SAE_HISTORIQUE_LOYERS
    WHERE ID_BIEN = P_ID_BIEN;
    
    -- Bien louable
    DELETE FROM SAE_BIEN_LOUABLE
    WHERE ID_BIEN = P_ID_BIEN;
        
END;