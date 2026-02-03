SET SERVEROUTPUT ON;
INSERT INTO sae_Batiment (Id_Batiment, Date_construction, Adresse, Code_postal, Ville, Numero_Fiscal_Bat)
VALUES ('TEST_BAT_LOYER', TO_DATE('2010-01-01', 'YYYY-MM-DD'), '200 Rue Test Loyer', '31000', 'Toulouse', '111111111111');

COMMIT;



BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 1 : INSERTION D''UN BIEN');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Insertion d''un bien avec :');
    DBMS_OUTPUT.PUT_LINE('Loyer: 800€');
    DBMS_OUTPUT.PUT_LINE('Provision: 100€');
    DBMS_OUTPUT.PUT_LINE('Date construction: 15/03/2020');
    
    INSERT INTO sae_Bien_louable (
        Id_Bien, Adresse, Ville, Code_postal, Type_bien, Date_construction, 
        Surface_habitable, Nb_pieces, Pourcentage_entretien_parties_communes, 
        Pourcentage_ordures_menageres, Numero_Fiscal, Loyer, Provision_pour_charges, Id_Batiment
    )
    VALUES (
        'TEST_BIEN_LOYER1', '200 Rue Test Appt 1', 'Toulouse', '31000', 'Logement', 
        TO_DATE('2020-03-15', 'YYYY-MM-DD'), 70.00, 3, 12.00, 6.00, 
        '222222222222', 800.00, 100.00, 'TEST_BAT_LOYER'
    );
    FOR h IN (SELECT * FROM SAE_HISTORIQUE_LOYERS WHERE ID_BIEN = 'TEST_BIEN_LOYER1' ORDER BY MOIS_CONCERNE) LOOP
        DBMS_OUTPUT.PUT_LINE('Mois concerné: ' || TO_CHAR(h.MOIS_CONCERNE, 'DD/MM/YYYY'));
        DBMS_OUTPUT.PUT_LINE('Loyer: ' || h.LOYER || '€');
        DBMS_OUTPUT.PUT_LINE('Provision: ' || h.PROVISION || '€');
    END LOOP;
    
    DECLARE
        v_count NUMBER;
        v_mois_attendu DATE := TO_DATE('2020-03-01', 'YYYY-MM-DD');
        v_mois_obtenu DATE;
    BEGIN
    SELECT COUNT(*) INTO v_count FROM SAE_HISTORIQUE_LOYERS WHERE ID_BIEN = 'TEST_BIEN_LOYER1';
        
        IF v_count = 1 THEN
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Test passé : Un seul enregistrement créé');
        
        SELECT MOIS_CONCERNE INTO v_mois_obtenu 
            FROM SAE_HISTORIQUE_LOYERS 
            WHERE ID_BIEN = 'TEST_BIEN_LOYER1';
            
            IF v_mois_obtenu = v_mois_attendu THEN
                DBMS_OUTPUT.PUT_LINE('Test passé : Mois concerné correct (1er jour du mois)');
            ELSE
                DBMS_OUTPUT.PUT_LINE('Echec du Test : Mois incorrect');
            END IF;
        ELSE
            DBMS_OUTPUT.PUT_LINE('');
            DBMS_OUTPUT.PUT_LINE('Echec du Test : Nombre d''enregistrements incorrect (' || v_count || ')');
        END IF;
    END;
END;
/


BEGIN 
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 2 : UPDATE DU LOYER');
    DBMS_OUTPUT.PUT_LINE('========================================');   
    DBMS_OUTPUT.PUT_LINE('Modification du loyer : 800€ -> 850€');
    DBMS_OUTPUT.PUT_LINE('Provision inchangée : 100€');
    
    UPDATE sae_Bien_louable
    SET LOYER = 850.00
    WHERE ID_BIEN = 'TEST_BIEN_LOYER1';
    commit;
     FOR h IN (SELECT * FROM SAE_HISTORIQUE_LOYERS WHERE ID_BIEN = 'TEST_BIEN_LOYER1' ORDER BY MOIS_CONCERNE) LOOP
        DBMS_OUTPUT.PUT_LINE('Mois concerné: ' || TO_CHAR(h.MOIS_CONCERNE, 'DD/MM/YYYY'));
        DBMS_OUTPUT.PUT_LINE('Loyer: ' || h.LOYER || '€');
        DBMS_OUTPUT.PUT_LINE('Provision: ' || h.PROVISION || '€');
    END LOOP;
    
     DECLARE
        v_count NUMBER;
        v_dernier_loyer NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_count FROM SAE_HISTORIQUE_LOYERS WHERE ID_BIEN = 'TEST_BIEN_LOYER1';
        
        IF v_count = 2 THEN
            DBMS_OUTPUT.PUT_LINE('');
            DBMS_OUTPUT.PUT_LINE('Test passé : Nouvel enregistrement créé (total: 2)');
            
            SELECT LOYER INTO v_dernier_loyer
            FROM SAE_HISTORIQUE_LOYERS
            WHERE ID_BIEN = 'TEST_BIEN_LOYER1'
            AND MOIS_CONCERNE = TRUNC(SYSDATE, 'MM');
            
            IF v_dernier_loyer = 850 THEN
                DBMS_OUTPUT.PUT_LINE('Test passé : Nouveau loyer enregistré correctement');
            ELSE
                DBMS_OUTPUT.PUT_LINE('Echec du Test: Loyer incorrect');
            END IF;
        ELSE
            DBMS_OUTPUT.PUT_LINE('');
            DBMS_OUTPUT.PUT_LINE('Echec du Test : Nombre d''enregistrements incorrect (' || v_count || ')');
        END IF;
    END;
END;
/

BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 3 : UPDATE DE LA PROVISION');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Modification de la provision : 100€ -> 120€');
    DBMS_OUTPUT.PUT_LINE('Loyer inchangé : 850€');
    
    UPDATE sae_Bien_louable
    SET PROVISION_POUR_CHARGES = 120.00
    WHERE ID_BIEN = 'TEST_BIEN_LOYER1';
    commit;
    
    FOR h IN (SELECT * FROM SAE_HISTORIQUE_LOYERS WHERE ID_BIEN = 'TEST_BIEN_LOYER1' ORDER BY MOIS_CONCERNE) LOOP
        DBMS_OUTPUT.PUT_LINE('Mois concerné: ' || TO_CHAR(h.MOIS_CONCERNE, 'DD/MM/YYYY'));
        DBMS_OUTPUT.PUT_LINE('Loyer: ' || h.LOYER || '€');
        DBMS_OUTPUT.PUT_LINE('Provision: ' || h.PROVISION || '€');
    END LOOP;
    DECLARE
        v_count NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_count FROM SAE_HISTORIQUE_LOYERS WHERE ID_BIEN = 'TEST_BIEN_LOYER1';
        
        IF v_count = 3 THEN
            DBMS_OUTPUT.PUT_LINE('');
            DBMS_OUTPUT.PUT_LINE('Test passé : Nouvel enregistrement créé (total: 3)');
        ELSE
            DBMS_OUTPUT.PUT_LINE('');
            DBMS_OUTPUT.PUT_LINE('Echec du Test : Nombre d''enregistrements incorrect (' || v_count || ')');
        END IF;
    END;
END;
/

BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 4 : UPDATE LOYER ET PROVISION');
    DBMS_OUTPUT.PUT_LINE('========================================');

    DBMS_OUTPUT.PUT_LINE('Modification simultanée :');
    DBMS_OUTPUT.PUT_LINE('Loyer : 850€ -> 900€');
    DBMS_OUTPUT.PUT_LINE('Provision : 120€ -> 150€');
    
    UPDATE sae_Bien_louable
    SET LOYER = 900.00,
        PROVISION_POUR_CHARGES = 150.00
    WHERE ID_BIEN = 'TEST_BIEN_LOYER1';
    commit;
    
     FOR h IN (SELECT * FROM SAE_HISTORIQUE_LOYERS WHERE ID_BIEN = 'TEST_BIEN_LOYER1' ORDER BY MOIS_CONCERNE) LOOP
        DBMS_OUTPUT.PUT_LINE('Mois concerné: ' || TO_CHAR(h.MOIS_CONCERNE, 'DD/MM/YYYY'));
        DBMS_OUTPUT.PUT_LINE('Loyer: ' || h.LOYER || '€');
        DBMS_OUTPUT.PUT_LINE('Provision: ' || h.PROVISION || '€');
    END LOOP;
    
    
    DECLARE
        v_count NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_count FROM SAE_HISTORIQUE_LOYERS WHERE ID_BIEN = 'TEST_BIEN_LOYER1';
        
        IF v_count = 4 THEN
            DBMS_OUTPUT.PUT_LINE('');
            DBMS_OUTPUT.PUT_LINE('Test passé : Nouvel enregistrement créé (total: 4)');
        ELSE
            DBMS_OUTPUT.PUT_LINE('');
            DBMS_OUTPUT.PUT_LINE('Echec du Test : Nombre d''enregistrements incorrect (' || v_count || ')');
        END IF;
    END;
END;
/

BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 5 : UPDATE AUTRE CHAMP (pas loyer/provision)');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Modification de la surface habitable : 70m² -> 75m²');
    DBMS_OUTPUT.PUT_LINE('Loyer et provision inchangés');
    
    UPDATE sae_Bien_louable
    SET SURFACE_HABITABLE = 75.00
    WHERE ID_BIEN = 'TEST_BIEN_LOYER1';
    commit;
    
    DECLARE
        v_count NUMBER;
    BEGIN
        SELECT COUNT(*) INTO v_count FROM SAE_HISTORIQUE_LOYERS WHERE ID_BIEN = 'TEST_BIEN_LOYER1';
        
        IF v_count = 4 THEN
            DBMS_OUTPUT.PUT_LINE('');
            DBMS_OUTPUT.PUT_LINE('Test Passé : Aucun nouvel enregistrement (total reste: 4)');
            DBMS_OUTPUT.PUT_LINE('Comportement correct : trigger ne s''active pas');
        ELSE
            DBMS_OUTPUT.PUT_LINE('');
            DBMS_OUTPUT.PUT_LINE('Echec du Test: Nombre d''enregistrements incorrect (' || v_count || ')');
        END IF;
    END;
END;
/

BEGIN
    -- Supprimer les données de test existantes
    DELETE FROM SAE_HISTORIQUE_LOYERS WHERE ID_BIEN LIKE 'TEST%';
    DELETE FROM SAE_LOUER WHERE ID_BIEN LIKE 'TEST%';
    DELETE FROM SAE_BIEN_LOUABLE WHERE ID_BIEN LIKE 'TEST%';
    DELETE FROM SAE_BATIMENT WHERE ID_BATIMENT LIKE 'TEST%';
    commit;
END;
/
    
   
    
    

    