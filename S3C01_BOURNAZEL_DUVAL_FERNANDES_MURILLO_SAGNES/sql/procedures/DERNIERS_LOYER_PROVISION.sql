--renvoie le dernier loyer et la dernière provision enregistrée dans l'historique
-- pour un bien donné et un mois donné

-- exemple : le loyer et la provision ont changé pour la dernière fois en juillet 2025, donc si la date en
-- entrée est le 01/10/2025 alors le loyer et la provision pour charges de juillet seront retournés

create or replace PROCEDURE DERNIERS_LOYER_PROVISION(P_IDBIEN IN SAE_BIEN_LOUABLE.ID_BIEN%TYPE,
                                                     P_PREMIERDUMOIS IN DATE,
                                                     P_LOYER OUT NUMBER,
                                                     P_PROVISION OUT NUMBER) AS 
    
    vLoyer SAE_HISTORIQUE_LOYERS.LOYER%TYPE;
    vProvision SAE_HISTORIQUE_LOYERS.PROVISION%TYPE;

BEGIN

    SELECT LOYER, PROVISION
        INTO vLoyer, vProvision
        FROM (
            SELECT LOYER, PROVISION
            FROM SAE_HISTORIQUE_LOYERS
            WHERE ID_BIEN = P_IDBIEN
            AND TRUNC(MOIS_CONCERNE) <= TRUNC(P_PREMIERDUMOIS)
            ORDER BY ID_HIST DESC
        )
    WHERE ROWNUM = 1;

    P_LOYER := vLoyer;
    P_PROVISION := vProvision;

EXCEPTION

    WHEN NO_DATA_FOUND THEN
        raise_application_error(-20008,'Aucun historique de loyer / charge présent pour cette date.');

END DERNIERS_LOYER_PROVISION;