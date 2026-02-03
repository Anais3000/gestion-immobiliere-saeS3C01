--renouvelle automatiquement les baux pour 3 ans si la location n'est pas révolue
--et que la date de fin de bail est inférieur a la date du jour

create or replace PROCEDURE RENOUVELER_BAUX_AUTOMATIQUE AS 

    CURSOR locs IS
    SELECT *
    FROM SAE_LOUER
    WHERE REVOLUE = 0;

BEGIN
    
    FOR l IN locs LOOP
        IF(l.DATE_FIN < SYSDATE) THEN
            UPDATE SAE_LOUER
            SET DATE_FIN = ADD_MONTHS(l.DATE_FIN, 36) 
            WHERE ID_BIEN = l.ID_BIEN
            AND ID_LOCATAIRE = l.ID_LOCATAIRE
            AND DATE_DEBUT = l.DATE_DEBUT;
        END IF;
    END LOOP;

END RENOUVELER_BAUX_AUTOMATIQUE;