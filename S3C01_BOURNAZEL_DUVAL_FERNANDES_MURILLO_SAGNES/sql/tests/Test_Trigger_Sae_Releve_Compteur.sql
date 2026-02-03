-- Créer un bâtiment de test
INSERT INTO sae_Batiment (Id_Batiment, Date_construction, Adresse, Code_postal, Ville, Numero_Fiscal_Bat)
VALUES ('TEST_BAT1', TO_DATE('2010-01-01', 'YYYY-MM-DD'), '100 Rue Test Compteur', '31000', 'Toulouse', '999999999999');

-- Créer un bien de test
INSERT INTO sae_Bien_louable (Id_Bien, Adresse, Ville, Code_postal, Type_bien, Date_construction, 
    Surface_habitable, Nb_pieces, Pourcentage_entretien_parties_communes, Pourcentage_ordures_menageres, 
    Numero_Fiscal, Loyer, Provision_pour_charges, Id_Batiment)
VALUES ('TEST_BIEN1', '100 Rue Test Appt 1', 'Toulouse', '31000', 'Logement', TO_DATE('2010-01-01', 'YYYY-MM-DD'), 
    60.00, 2, 10.00, 5.00, '888888888888', 700.00, 60.00, 'TEST_BAT1');

-- Créer des compteurs de test
-- Compteur pour un bien spécifique
INSERT INTO SAE_COMPTEUR (ID_COMPTEUR, TYPE_COMPTEUR, DATE_INSTALLATION, ID_BIEN, ID_BATIMENT)
VALUES ('TEST_COMP_BIEN1', 'Eau', TO_DATE('2024-01-01', 'YYYY-MM-DD'), 'TEST_BIEN1', NULL);

-- Compteur pour parties communes du bâtiment
INSERT INTO SAE_COMPTEUR (ID_COMPTEUR, TYPE_COMPTEUR, DATE_INSTALLATION, ID_BIEN, ID_BATIMENT)
VALUES ('TEST_COMP_BAT1', 'Electricité', TO_DATE('2024-01-01', 'YYYY-MM-DD'), NULL, 'TEST_BAT1');


SET SERVEROUTPUT ON;

BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 1 : PREMIER RELEVÉ COMPTEUR BIEN');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('  Index: 150 | Prix unitaire: 0.15€ | Partie fixe: 10€');
    DBMS_OUTPUT.PUT_LINE('  Montant attendu: (150 * 0.15) + 10 = 32.50€');
    INSERT INTO SAE_RELEVE_COMPTEUR (ID_COMPTEUR, DATE_RELEVE, INDEX_COMPTEUR, PRIX_UNITE, PARTIE_FIXE)
    VALUES ('TEST_COMP_BIEN1', TO_DATE('2024-03-01', 'YYYY-MM-DD'), 150, 0.15, 10.00);
    
    FOR r IN (SELECT * FROM SAE_RELEVE_COMPTEUR WHERE ID_COMPTEUR = 'TEST_COMP_BIEN1') LOOP
        DBMS_OUTPUT.PUT_LINE('Relevé: ' || TO_CHAR(r.DATE_RELEVE, 'DD/MM/YYYY') || 
                             ' | Index: ' || r.INDEX_COMPTEUR);
    END LOOP;
    
    FOR c IN (SELECT * FROM SAE_CHARGE WHERE LIBELLE LIKE '%TEST_BIEN1%') LOOP
        DBMS_OUTPUT.PUT_LINE('Charge créée: ' || c.MONTANT || '€ | ' || c.LIBELLE);
        DBMS_OUTPUT.PUT_LINE('Période: ' || TO_CHAR(c.DATE_DEBUT_PERIODE, 'DD/MM/YYYY') || 
                             ' au ' || TO_CHAR(c.DATE_FIN_PERIODE, 'DD/MM/YYYY'));
    END LOOP;
    
    FOR p IN (SELECT * FROM SAE_PAIEMENT WHERE LIBELLE LIKE '%TEST_COMP_BIEN1%') LOOP
        DBMS_OUTPUT.PUT_LINE('Paiement créé: ' || p.MONTANT || '€ (' || p.SENS_PAIEMENT || ')');
    END LOOP;
END;
/


BEGIN
    DBMS_OUTPUT.PUT_LINE('=========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 2 : DEUXIÈME RELEVÉ (historique présent)');
    DBMS_OUTPUT.PUT_LINE('==========================================');
    DBMS_OUTPUT.PUT_LINE('  Index: 350 (delta: 200) | Prix unitaire: 0.15€ | Partie fixe: 10€');
    DBMS_OUTPUT.PUT_LINE('  Montant attendu: (200 * 0.15) + 10 = 40.00€');
    
    INSERT INTO SAE_RELEVE_COMPTEUR (ID_COMPTEUR, DATE_RELEVE, INDEX_COMPTEUR, PRIX_UNITE, PARTIE_FIXE)
    VALUES ('TEST_COMP_BIEN1', TO_DATE('2024-06-01', 'YYYY-MM-DD'), 350, 0.15, 10.00);
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('--- Tous les relevés ---');
    FOR r IN (SELECT * FROM SAE_RELEVE_COMPTEUR WHERE ID_COMPTEUR = 'TEST_COMP_BIEN1' ORDER BY DATE_RELEVE) LOOP
        DBMS_OUTPUT.PUT_LINE('Relevé: ' || TO_CHAR(r.DATE_RELEVE, 'DD/MM/YYYY') || 
                             ' | Index: ' || r.INDEX_COMPTEUR);
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('--- Dernière charge créée ---');
    FOR c IN (SELECT * FROM SAE_CHARGE 
              WHERE LIBELLE LIKE '%TEST_BIEN1%' 
              AND DATE_FACTURATION = TO_DATE('2024-06-01', 'YYYY-MM-DD')) LOOP
        DBMS_OUTPUT.PUT_LINE('Charge: ' || c.MONTANT || '€ | ' || c.LIBELLE);
        DBMS_OUTPUT.PUT_LINE('Période: ' || TO_CHAR(c.DATE_DEBUT_PERIODE, 'DD/MM/YYYY') || 
                             ' au ' || TO_CHAR(c.DATE_FIN_PERIODE, 'DD/MM/YYYY'));
    END LOOP;
END;
/


BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 3 : COMPTEUR PARTIES COMMUNES');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Index: 500 | Prix unitaire: 0.10€ | Partie fixe: 15€');
    DBMS_OUTPUT.PUT_LINE('Montant attendu: (500 * 0.10) + 15 = 65.00€');
    
    INSERT INTO SAE_RELEVE_COMPTEUR (ID_COMPTEUR, DATE_RELEVE, INDEX_COMPTEUR, PRIX_UNITE, PARTIE_FIXE)
    VALUES ('TEST_COMP_BAT1', TO_DATE('2024-03-01', 'YYYY-MM-DD'), 500, 0.10, 15.00);
    
     DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('--- Charge bâtiment créée ---');
    FOR c IN (SELECT * FROM SAE_CHARGE WHERE ID_BATIMENT = 'TEST_BAT1') LOOP
        DBMS_OUTPUT.PUT_LINE('Charge: ' || c.MONTANT || '€');
        DBMS_OUTPUT.PUT_LINE('Libellé: ' || c.LIBELLE);
        DBMS_OUTPUT.PUT_LINE('ID_BATIMENT: ' || c.ID_BATIMENT);
    END LOOP;
END;
/


BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 4 : CHANGEMENT COMPTEUR (index < précédent)');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Nouvel index: 50 (< ancien 350, compteur changé)');
    DBMS_OUTPUT.PUT_LINE('Prix unitaire: 0.15€ | Partie fixe: 10€');
    DBMS_OUTPUT.PUT_LINE('Montant attendu: (50 * 0.15) + 10 = 17.50€');
    
    INSERT INTO SAE_RELEVE_COMPTEUR (ID_COMPTEUR, DATE_RELEVE, INDEX_COMPTEUR, PRIX_UNITE, PARTIE_FIXE)
    VALUES ('TEST_COMP_BIEN1', TO_DATE('2024-09-01', 'YYYY-MM-DD'), 50, 0.15, 10.00);
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('--- Historique complet des relevés ---');
    FOR r IN (SELECT * FROM SAE_RELEVE_COMPTEUR WHERE ID_COMPTEUR = 'TEST_COMP_BIEN1' ORDER BY DATE_RELEVE) LOOP
        DBMS_OUTPUT.PUT_LINE(TO_CHAR(r.DATE_RELEVE, 'DD/MM/YYYY') || ' | Index: ' || r.INDEX_COMPTEUR);
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE('--- Charge du changement de compteur ---');
    FOR c IN (SELECT * FROM SAE_CHARGE 
              WHERE LIBELLE LIKE '%TEST_BIEN1%' 
              AND DATE_FACTURATION = TO_DATE('2024-09-01', 'YYYY-MM-DD')) LOOP
        DBMS_OUTPUT.PUT_LINE('Charge: ' || c.MONTANT || '€');
    END LOOP;
END;
/




DECLARE
    v_nb_releves NUMBER;
    v_nb_charges NUMBER;
    v_nb_paiements NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('STATISTIQUES AVANT SUPPRESSION');
    DBMS_OUTPUT.PUT_LINE('========================================');
    SELECT COUNT(*) INTO v_nb_releves FROM SAE_RELEVE_COMPTEUR WHERE ID_COMPTEUR = 'TEST_COMP_BIEN1';
    SELECT COUNT(*) INTO v_nb_charges FROM SAE_CHARGE WHERE LIBELLE LIKE '%TEST_BIEN1%';
    SELECT COUNT(*) INTO v_nb_paiements FROM SAE_PAIEMENT WHERE LIBELLE LIKE '%TEST_COMP_BIEN1%';
    
    DBMS_OUTPUT.PUT_LINE('Compteur TEST_COMP_BIEN1:');
    DBMS_OUTPUT.PUT_LINE('Relevés : ' || v_nb_releves);
    DBMS_OUTPUT.PUT_LINE('Charges : ' || v_nb_charges);
    DBMS_OUTPUT.PUT_LINE('Paiements : ' || v_nb_paiements);
END;
/


DECLARE
    v_nb_charges_avant NUMBER;
    v_nb_charges_apres NUMBER;
    v_nb_paiements_avant NUMBER;
    v_nb_paiements_apres NUMBER;
    v_date_releve DATE := TO_DATE('2024-06-01', 'YYYY-MM-DD');
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 5 : SUPPRESSION D''UN RELEVÉ');
    DBMS_OUTPUT.PUT_LINE('========================================');
    SELECT COUNT(*) INTO v_nb_charges_avant FROM SAE_CHARGE WHERE LIBELLE LIKE '%TEST_BIEN1%';
    SELECT COUNT(*) INTO v_nb_paiements_avant FROM SAE_PAIEMENT WHERE LIBELLE LIKE '%TEST_COMP_BIEN1%';
    
    DBMS_OUTPUT.PUT_LINE('Suppression du relevé du 01/06/2024...');
    DBMS_OUTPUT.PUT_LINE('Avant suppression :');
    DBMS_OUTPUT.PUT_LINE('Charges : ' || v_nb_charges_avant);
    DBMS_OUTPUT.PUT_LINE('Paiements : ' || v_nb_paiements_avant);
    
    DELETE FROM SAE_RELEVE_COMPTEUR 
    WHERE ID_COMPTEUR = 'TEST_COMP_BIEN1' 
    AND DATE_RELEVE = v_date_releve;
    
    SELECT COUNT(*) INTO v_nb_charges_apres FROM SAE_CHARGE WHERE LIBELLE LIKE '%TEST_BIEN1%';
    SELECT COUNT(*) INTO v_nb_paiements_apres FROM SAE_PAIEMENT WHERE LIBELLE LIKE '%TEST_COMP_BIEN1%';
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('Après suppression :');
    DBMS_OUTPUT.PUT_LINE('Charges : ' || v_nb_charges_apres);
    DBMS_OUTPUT.PUT_LINE('Paiements : ' || v_nb_paiements_apres);
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('Charges supprimées : ' || (v_nb_charges_avant - v_nb_charges_apres));
    DBMS_OUTPUT.PUT_LINE('Paiements supprimés : ' || (v_nb_paiements_avant - v_nb_paiements_apres));
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('--- Relevés restants ---');
    FOR r IN (SELECT * FROM SAE_RELEVE_COMPTEUR WHERE ID_COMPTEUR = 'TEST_COMP_BIEN1' ORDER BY DATE_RELEVE) LOOP
        DBMS_OUTPUT.PUT_LINE(TO_CHAR(r.DATE_RELEVE, 'DD/MM/YYYY') || ' | Index: ' || r.INDEX_COMPTEUR);
    END LOOP;
    
END;
/

ROLLBACK;
EXEC supprimer_batiment('TEST_BAT1');
COMMIT;


    
    