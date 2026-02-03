SET SERVEROUTPUT ON;

-- Créer un bâtiment de test
INSERT INTO SAE_BATIMENT (Id_Batiment, Date_construction, Adresse, Code_postal, Ville, Numero_Fiscal_Bat)
VALUES ('TEST_BAT_PAIE', TO_DATE('2010-01-01', 'YYYY-MM-DD'), '300 Rue Test Paiement', '31000', 'Toulouse', '222222222222');

-- Créer un bien de test
INSERT INTO SAE_BIEN_LOUABLE (Id_Bien, Adresse, Ville, Code_postal, Type_bien, Date_construction, 
    Surface_habitable, Nb_pieces, Pourcentage_entretien_parties_communes, Pourcentage_ordures_menageres, 
    Numero_Fiscal, Loyer, Provision_pour_charges, Id_Batiment)
VALUES ('TEST_BIEN_PAIE', '300 Rue Test Appt 1', 'Toulouse', '31000', 'Logement', TO_DATE('2010-01-01', 'YYYY-MM-DD'), 
    60.00, 2, 10.00, 5.00, '333333333333', 700.00, 60.00, 'TEST_BAT_PAIE');

-- Créer un locataire de test
INSERT INTO SAE_LOCATAIRE (Id_Locataire, Nom, Prenom, Date_naissance, mail, Num_Tel, Adresse, Code_postal, Ville)
VALUES ('TEST_LOC1', 'Dupont', 'Jean', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'jean.dupont@test.fr', '0612345678', 
    '10 Rue Test', '31000', 'Toulouse');
    
-- Créer une location active (du 01/01/2024 au 31/12/2024)
INSERT INTO SAE_LOUER (Id_Locataire, Id_Bien, Date_debut, Date_fin, depot_de_garantie)
VALUES ('TEST_LOC1', 'TEST_BIEN_PAIE', TO_DATE('2024-01-01', 'YYYY-MM-DD'), TO_DATE('2024-12-31', 'YYYY-MM-DD'),530);
--Créer un Organisme
INSERT INTO SAE_ORGANISME (NUM_SIRET,NOM,ADRESSE,CODE_POSTAL,VILLE,MAIL,NUM_TEL,SPECIALITE)
VALUES ('14789632154678','Répare2000','13 Rue des Pomme de Terre',31000,'Toulouse','Repare2000@mail.com','0708050603','réparation');

-- Créer une intervention pour les tests
INSERT INTO SAE_INTERVENTION (
    ID_INTERVENTION, INTITULE, NUMERO_FACTURE, MONTANT_FACTURE, 
    ACOMPTE, DATE_FACTURE, NUMERO_DEVIS, MONTANT_DEVIS, 
    DATE_INTERVENTION, MONTANT_NON_DEDUCTIBLE, REDUCTION, 
    ID_BATIMENT,NUM_SIRET, ENTRETIEN_PC, ORDURES
)
VALUES (
    'TEST_INT_PAIE', 'Réparation test', 'FACT999', 500.00,
    0, TO_DATE('2024-06-15', 'YYYY-MM-DD'), 'DEV999', 500.00,
    TO_DATE('2024-06-15', 'YYYY-MM-DD'), 0, 0,
    'TEST_BAT_PAIE','14789632154678', 0, 0
);

DECLARE 
    Exception_bien_non_loue EXCEPTION;
    PRAGMA EXCEPTION_INIT(Exception_bien_non_loue, -20004);
    
    Exception_paiement_mixte EXCEPTION;
    PRAGMA EXCEPTION_INIT(Exception_paiement_mixte, -20003);

BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 1 : PAIEMENT DE LOYER VALIDE');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Date paiement: 05/06/2024 (dans la période de location)');
    DBMS_OUTPUT.PUT_LINE('Montant: 760€ (loyer + charges)');
    
    INSERT INTO SAE_PAIEMENT (Libelle, Date_paiement, Montant, Sens_paiement, Id_Bien, Id_Intervention)
    VALUES ('loyer', TO_DATE('2024-06-05', 'YYYY-MM-DD'), 760.00, 'recus', 'TEST_BIEN_PAIE', NULL);
    
    DBMS_OUTPUT.PUT_LINE('TEST RÉUSSI : Paiement de loyer inséré');
    FOR p IN (SELECT * FROM SAE_PAIEMENT WHERE ID_BIEN = 'TEST_BIEN_PAIE') LOOP
        DBMS_OUTPUT.PUT_LINE('Montant: ' || p.MONTANT || '€');
        DBMS_OUTPUT.PUT_LINE('Date: ' || TO_CHAR(p.DATE_PAIEMENT, 'DD/MM/YYYY'));
    END LOOP;
EXCEPTION
    WHEN Exception_bien_non_loue THEN
        DBMS_OUTPUT.PUT_LINE('ERREUR INATTENDUE : Exception bien non loué levée');
        DBMS_OUTPUT.PUT_LINE('Message: ' || SQLERRM);
    WHEN Exception_paiement_mixte THEN
        DBMS_OUTPUT.PUT_LINE('ERREUR INATTENDUE : Exception paiement mixte levée');
        DBMS_OUTPUT.PUT_LINE('Message: ' || SQLERRM);
END;
/
DECLARE 
    Exception_bien_non_loue EXCEPTION;
    PRAGMA EXCEPTION_INIT(Exception_bien_non_loue, -20004);
    
    Exception_paiement_mixte EXCEPTION;
    PRAGMA EXCEPTION_INIT(Exception_paiement_mixte, -20003);
BEGIN
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 2 : PAIEMENT LOYER HORS PÉRIODE');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Date paiement: 15/06/2025 (hors période de location)');
    DBMS_OUTPUT.PUT_LINE('Erreur attendue: -20004 "Le bien n''est pas occupé"');
    INSERT INTO SAE_PAIEMENT (Libelle, Date_paiement, Montant, Sens_paiement, Id_Bien, Id_Intervention)
    VALUES ('loyer', TO_DATE('2025-06-15', 'YYYY-MM-DD'), 760.00, 'recus', 'TEST_BIEN_PAIE', NULL);
    DBMS_OUTPUT.PUT_LINE('ERREUR : Le paiement a été accepté alors qu''il ne devrait pas !');
EXCEPTION 
    WHEN Exception_bien_non_loue THEN
        DBMS_OUTPUT.PUT_LINE('TEST RÉUSSI : Exception_bien_non_loue levée correctement');
        DBMS_OUTPUT.PUT_LINE('Message: ' || SQLERRM);
    WHEN Exception_paiement_mixte THEN
        DBMS_OUTPUT.PUT_LINE('ERREUR INATTENDUE : Exception paiement mixte levée au lieu de bien non loué');
        DBMS_OUTPUT.PUT_LINE('Message: ' || SQLERRM);
END;
/
DECLARE 
    Exception_bien_non_loue EXCEPTION;
    PRAGMA EXCEPTION_INIT(Exception_bien_non_loue, -20004);
    
    Exception_paiement_mixte EXCEPTION;
    PRAGMA EXCEPTION_INIT(Exception_paiement_mixte, -20003);
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 3 : BIEN ET INTERVENTION RENSEIGNÉS');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Tentative d''insérer un paiement avec id_bien ET id_intervention');
    DBMS_OUTPUT.PUT_LINE('Erreur attendue: -20003');
    
    INSERT INTO SAE_PAIEMENT (Libelle, Date_paiement, Montant, Sens_paiement, Id_Bien, Id_Intervention)
    VALUES ('paiement mixte', TO_DATE('2024-06-15', 'YYYY-MM-DD'), 500.00, 'emis', 'TEST_BIEN_PAIE', 'TEST_INT_PAIE');
    
    DBMS_OUTPUT.PUT_LINE('ERREUR : Le paiement a été accepté alors qu''il ne devrait pas !');
    
    EXCEPTION
    WHEN Exception_paiement_mixte THEN
        DBMS_OUTPUT.PUT_LINE('TEST RÉUSSI : Exception_paiement_mixte levée correctement');
        DBMS_OUTPUT.PUT_LINE('Message: ' || SQLERRM);
    WHEN Exception_bien_non_loue THEN
        DBMS_OUTPUT.PUT_LINE('ERREUR INATTENDUE : Exception bien non loué levée au lieu de paiement mixte');
        DBMS_OUTPUT.PUT_LINE('Message: ' || SQLERRM);
END;
/
DECLARE 
    Exception_bien_non_loue EXCEPTION;
    PRAGMA EXCEPTION_INIT(Exception_bien_non_loue, -20004);
    
    Exception_paiement_mixte EXCEPTION;
    PRAGMA EXCEPTION_INIT(Exception_paiement_mixte, -20003);
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 4 : PAIEMENT D''INTERVENTION');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Paiement pour intervention uniquement (id_bien = NULL)');
    
    INSERT INTO SAE_PAIEMENT (Libelle, Date_paiement, Montant, Sens_paiement, Id_Bien, Id_Intervention)
    VALUES ('Paiement réparation', TO_DATE('2024-06-20', 'YYYY-MM-DD'), 500.00, 'emis', NULL, 'TEST_INT_PAIE');
    
    DBMS_OUTPUT.PUT_LINE('TEST RÉUSSI : Paiement d''intervention inséré');
    FOR p IN (SELECT * FROM SAE_PAIEMENT WHERE ID_INTERVENTION = 'TEST_INT_PAIE') LOOP
        DBMS_OUTPUT.PUT_LINE('Montant: ' || p.MONTANT || '€');
        DBMS_OUTPUT.PUT_LINE('Intervention: ' || p.ID_INTERVENTION);
    END LOOP;
    
    EXCEPTION
    WHEN Exception_bien_non_loue THEN
        DBMS_OUTPUT.PUT_LINE('ERREUR INATTENDUE : Exception bien non loué levée');
        DBMS_OUTPUT.PUT_LINE('Message: ' || SQLERRM);
    WHEN Exception_paiement_mixte THEN
        DBMS_OUTPUT.PUT_LINE('ERREUR INATTENDUE : Exception paiement mixte levée');
        DBMS_OUTPUT.PUT_LINE('Message: ' || SQLERRM);
END;
/
DECLARE
    Exception_bien_non_loue EXCEPTION;
    PRAGMA EXCEPTION_INIT(Exception_bien_non_loue, -20004);
    
    Exception_paiement_mixte EXCEPTION;
    PRAGMA EXCEPTION_INIT(Exception_paiement_mixte, -20003);
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 5 : AUTRE TYPE DE PAIEMENT');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Libellé: "charges" au lieu de "loyer"');
    DBMS_OUTPUT.PUT_LINE('Pas de vérification de période attendue');
    
    INSERT INTO SAE_PAIEMENT (Libelle, Date_paiement, Montant, Sens_paiement, Id_Bien, Id_Intervention)
    VALUES ('charges', TO_DATE('2025-06-15', 'YYYY-MM-DD'), 100.00, 'recus', 'TEST_BIEN_PAIE', NULL);
    DBMS_OUTPUT.PUT_LINE('TEST RÉUSSI : Paiement "charges" accepté même hors période');
    
    EXCEPTION
    WHEN Exception_bien_non_loue THEN
        DBMS_OUTPUT.PUT_LINE('ERREUR : Exception bien non loué levée pour un paiement non-loyer');
        DBMS_OUTPUT.PUT_LINE('Message: ' || SQLERRM);
    WHEN Exception_paiement_mixte THEN
        DBMS_OUTPUT.PUT_LINE('ERREUR INATTENDUE : Exception paiement mixte levée');
        DBMS_OUTPUT.PUT_LINE('Message: ' || SQLERRM);
END;
/

DECLARE
    Exception_bien_non_loue EXCEPTION;
    PRAGMA EXCEPTION_INIT(Exception_bien_non_loue, -20004);
    
    Exception_paiement_mixte EXCEPTION;
    PRAGMA EXCEPTION_INIT(Exception_paiement_mixte, -20003);
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 6 : UPDATE PAIEMENT VERS DATE INVALIDE');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Modification date: 05/06/2024 -> 15/06/2025 (hors période)');
    DBMS_OUTPUT.PUT_LINE('Erreur attendue: -20004');
    
    UPDATE SAE_PAIEMENT
    SET DATE_PAIEMENT = TO_DATE('2025-06-15', 'YYYY-MM-DD')
    WHERE ID_BIEN = 'TEST_BIEN_PAIE'
    AND LIBELLE = 'loyer';
    
    DBMS_OUTPUT.PUT_LINE('ERREUR : L''update a été accepté alors qu''il ne devrait pas !');
EXCEPTION
    WHEN Exception_bien_non_loue THEN
        DBMS_OUTPUT.PUT_LINE('TEST RÉUSSI : Exception_bien_non_loue levée correctement');
        DBMS_OUTPUT.PUT_LINE('Message: ' || SQLERRM);
    WHEN Exception_paiement_mixte THEN
        DBMS_OUTPUT.PUT_LINE('ERREUR INATTENDUE : Exception paiement mixte levée');
        DBMS_OUTPUT.PUT_LINE('Message: ' || SQLERRM);
END;
/
EXEC SUPPRIMER_ORGANISME('14789632154678');
EXEC SUPPRIMER_LOCATAIRE('TEST_LOC1');
EXEC SUPPRIMER_BATIMENT('TEST_BAT_PAIE');

COMMIT;
