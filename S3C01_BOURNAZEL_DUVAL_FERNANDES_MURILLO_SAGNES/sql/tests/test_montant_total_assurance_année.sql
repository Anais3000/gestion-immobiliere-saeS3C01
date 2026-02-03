set serveroutput on;
-- Créer des bâtiments de test
INSERT INTO sae_Batiment (Id_Batiment, Date_construction, Adresse, Code_postal, Ville, Numero_Fiscal_Bat)
VALUES ('TEST_BAT_ASS1', TO_DATE('2015-01-01', 'YYYY-MM-DD'), '50 Rue Test Assurance', '31000', 'Toulouse', '555555555555');

INSERT INTO sae_Batiment (Id_Batiment, Date_construction, Adresse, Code_postal, Ville, Numero_Fiscal_Bat)
VALUES ('TEST_BAT_ASS2', TO_DATE('2018-06-15', 'YYYY-MM-DD'), '60 Avenue Test Assurance', '31100', 'Toulouse', '666666666666');

-- Créer des assurances de test pour TEST_BAT_ASS1
-- Année 2023 : plusieurs assurances
INSERT INTO SAE_ASSURANCE 
VALUES ('test123','aide juridique', 2023, 1200.00,'TEST_BAT_ASS1');

INSERT INTO SAE_ASSURANCE 
VALUES ('test1234','propriétaire', 2023, 800.50,'TEST_BAT_ASS1');

INSERT INTO SAE_ASSURANCE 
VALUES ('test12345','aide juridique', 2023, 350.25,'TEST_BAT_ASS1');

-- Année 2024 : une seule assurance
INSERT INTO SAE_ASSURANCE 
VALUES ('test123456','propriétaire', 2024, 1500.00,'TEST_BAT_ASS1');

-- Année 2025 : plusieurs assurances
INSERT INTO SAE_ASSURANCE
VALUES ('test1234567','aide juridique', 2025, 1000.00,'TEST_BAT_ASS1');

INSERT INTO SAE_ASSURANCE
VALUES ('test12345678','propriétaire', 2025, 600.75,'TEST_BAT_ASS2');

-- Créer des assurances pour TEST_BAT_ASS2
INSERT INTO SAE_ASSURANCE 
VALUES ('test123456789','aide juridique',2023, 2000.00,'TEST_BAT_ASS2');

INSERT INTO SAE_ASSURANCE 
VALUES ('test1234567890','propriétaire', 2024, 2500.00,'TEST_BAT_ASS2');
commit;


DECLARE
    v_resultat DECIMAL(10,2);
    v_attendu DECIMAL(10,2) := 2350.75; -- 1200 + 800.50 + 350.25
    
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 1 : ANNÉE AVEC PLUSIEURS ASSURANCES');
    DBMS_OUTPUT.PUT_LINE('========================================');

    DBMS_OUTPUT.PUT_LINE('Bâtiment: TEST_BAT_ASS1 | Année: 2023');
    DBMS_OUTPUT.PUT_LINE('Assurances: 1200€ + 800.50€ + 350.25€');
    DBMS_OUTPUT.PUT_LINE('Total attendu: ' || v_attendu || '€');
    
    v_resultat := Total_Assurance_Bat_Annee('TEST_BAT_ASS1', 2023);
    
    DBMS_OUTPUT.PUT_LINE('Total obtenu: ' || v_resultat || '€');
    
    IF v_resultat = v_attendu THEN
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Test passé : Somme correcte');
    ELSE
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Echec du Test : Somme incorrecte');
    END IF;
END;
/

DECLARE
    v_resultat DECIMAL(10,2);
    v_attendu DECIMAL(10,2) := 1500.00;
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 2 : ANNÉE AVEC UNE SEULE ASSURANCE');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Bâtiment: TEST_BAT_ASS1 | Année: 2024');
    DBMS_OUTPUT.PUT_LINE('Total attendu: ' || v_attendu || '€');
    
    v_resultat := Total_Assurance_Bat_Annee('TEST_BAT_ASS1', 2024);
    
    DBMS_OUTPUT.PUT_LINE('Total obtenu: ' || v_resultat || '€');
    
    IF v_resultat = v_attendu THEN
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Test passé : Montant correct');
    ELSE
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Echec du Test : Montant incorrect');
    END IF;
END;
/



DECLARE
    v_resultat DECIMAL(10,2);
    v_attendu DECIMAL(10,2) := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 3 : ANNÉE SANS ASSURANCE');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Bâtiment: TEST_BAT_ASS1 | Année: 2022');
    DBMS_OUTPUT.PUT_LINE('Aucune assurance pour cette année');
    DBMS_OUTPUT.PUT_LINE('Résultat attendu: 0');
    
    v_resultat := Total_Assurance_Bat_Annee('TEST_BAT_ASS1', 2022);
    
    IF v_resultat = v_attendu THEN
        DBMS_OUTPUT.PUT_LINE('Total obtenu: 0');
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Test passé');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Total obtenu: ' || v_resultat || '€');
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Echec du Test : Devrait retourner 0 ');
    END IF;
END;
/

DECLARE
    v_resultat_bat1 DECIMAL(10,2);
    v_resultat_bat2 DECIMAL(10,2);
    v_attendu_bat1 DECIMAL(10,2) := 2350.75;
    v_attendu_bat2 DECIMAL(10,2) := 2000.00;
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 4 : ISOLATION PAR BÂTIMENT');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Vérification que les totaux sont bien isolés par bâtiment');
    DBMS_OUTPUT.PUT_LINE('Année: 2023');
    
    v_resultat_bat1 := Total_Assurance_Bat_Annee('TEST_BAT_ASS1', 2023);
    v_resultat_bat2 := Total_Assurance_Bat_Annee('TEST_BAT_ASS2', 2023);
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('TEST_BAT_ASS1: ' || v_resultat_bat1 || '€ (attendu: ' || v_attendu_bat1 || '€)');
    DBMS_OUTPUT.PUT_LINE('TEST_BAT_ASS2: ' || v_resultat_bat2 || '€ (attendu: ' || v_attendu_bat2 || '€)');
    
    IF v_resultat_bat1 = v_attendu_bat1 AND v_resultat_bat2 = v_attendu_bat2 THEN
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE(' Test passé : Isolation correcte par bâtiment');
    ELSE
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Echec du Test : Problème d''isolation');
    END IF;
    
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('✗ ERREUR : ' || SQLERRM);
END;
/

DECLARE
    v_resultat_2023 DECIMAL(10,2);
    v_resultat_2024 DECIMAL(10,2);
    v_resultat_2025 DECIMAL(10,2);
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 5 : ISOLATION PAR ANNÉE');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Vérification que les totaux sont bien isolés par année');
    DBMS_OUTPUT.PUT_LINE('Bâtiment: TEST_BAT_ASS1');
    
    v_resultat_2023 := Total_Assurance_Bat_Annee('TEST_BAT_ASS1', 2023);
    v_resultat_2024 := Total_Assurance_Bat_Annee('TEST_BAT_ASS1', 2024);
    v_resultat_2025 := Total_Assurance_Bat_Annee('TEST_BAT_ASS1', 2025);
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('2023: ' || v_resultat_2023 || '€');
    DBMS_OUTPUT.PUT_LINE('2024: ' || v_resultat_2024 || '€');
    DBMS_OUTPUT.PUT_LINE('2025: ' || v_resultat_2025 || '€');
    
    IF v_resultat_2023 != v_resultat_2024 AND v_resultat_2024 != v_resultat_2025 THEN
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Test passé : Isolation correcte par année');
    ELSE
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Attention : Totaux identiques (peut être normal)');
    END IF;
END;
/

Exec supprimer_batiment('TEST_BAT_ASS1');
Exec supprimer_batiment('TEST_BAT_ASS2');
commit;
