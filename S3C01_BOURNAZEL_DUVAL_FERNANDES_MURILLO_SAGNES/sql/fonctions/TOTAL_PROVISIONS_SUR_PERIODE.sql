--calul le montant total des provisions versées pour un bien louable
-- sur une periode donnée

create or replace FUNCTION TOTAL_PROVISIONS_SUR_PERIODE(
    P_Id_bien SAE_BIEN_LOUABLE.ID_BIEN%TYPE,
    P_Date_debut_periode DATE,
    P_Date_fin_periode DATE
) RETURN NUMBER IS
    
    dateMin DATE;
    locEnCours SAE_LOUER%ROWTYPE;
    vDateMoisCourant DATE;
    vTotalProvisions NUMBER := 0;
    vLoyer NUMBER;
    vProvision NUMBER;

    -- Exception personnalisée pour l'erreur -20008
    e_pas_historique EXCEPTION;
    PRAGMA EXCEPTION_INIT(e_pas_historique, -20008);

BEGIN
    -- Vérifier qu'une location existe sur la période
    BEGIN
        SELECT * INTO locEnCours
        FROM (
            SELECT *
            FROM SAE_LOUER
            WHERE ID_BIEN = P_Id_bien
            AND DATE_FIN >= P_Date_debut_periode
            AND DATE_DEBUT <= P_Date_fin_periode
            ORDER BY DATE_DEBUT DESC
        )
        WHERE ROWNUM = 1;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            raise_application_error(-20004, 'Une régularisation ne peut se faire que sur un bien en cours de location. Le bien ' || P_Id_bien || ' n''est pas en cours de location.');
    END;

    -- Calculer date debut effective (plus recente entre début période et debut location)
    dateMin := GREATEST(TRUNC(P_Date_debut_periode, 'MM'), TRUNC(locEnCours.DATE_DEBUT, 'MM'));

    vDateMoisCourant := dateMin;

    WHILE vDateMoisCourant <= TRUNC(P_Date_fin_periode, 'MM') LOOP
        BEGIN
            -- On recupere la provision du mois en question
            DERNIERS_LOYER_PROVISION(P_Id_bien, vDateMoisCourant, vLoyer, vProvision);

            vTotalProvisions := vTotalProvisions + vProvision;

        EXCEPTION
            WHEN e_pas_historique THEN
                raise_application_error(-20009, 
                    'Impossible de calculer les provisions : pas d''historique de provision pour le mois ' || 
                    TO_CHAR(vDateMoisCourant, 'MM/YYYY'));
        END;

        vDateMoisCourant := ADD_MONTHS(vDateMoisCourant, 1);
    END LOOP;

    RETURN vTotalProvisions;

END TOTAL_PROVISIONS_SUR_PERIODE;