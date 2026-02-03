
INSERT INTO sae_Batiment (Id_Batiment, Date_construction, Adresse, Code_postal, Ville, Numero_Fiscal_Bat)
VALUES ('TEST_BAT1', TO_DATE('2000-01-01', 'YYYY-MM-DD'), '10 Rue Test', '31000', 'Toulouse', '123456789012');

INSERT INTO sae_Batiment (Id_Batiment, Date_construction, Adresse, Code_postal, Ville, Numero_Fiscal_Bat)
VALUES ('TEST_BAT2', TO_DATE('2005-06-15', 'YYYY-MM-DD'), '20 Avenue Test', '31100', 'Toulouse', '987654321098');

-- Insérer des locataires de test
INSERT INTO sae_Locataire (Id_Locataire, Nom, Prenom, Num_tel, Mail, Date_naissance, Ville_naissance, Adresse, Code_postal, Ville)
VALUES ('TEST_LOC1', 'Dupont', 'Jean', '0612345678', 'jean.dupont@test.fr', TO_DATE('1990-05-15', 'YYYY-MM-DD'), 'Paris', '1 Rue Test', '31000', 'Toulouse');

INSERT INTO sae_Locataire (Id_Locataire, Nom, Prenom, Num_tel, Mail, Date_naissance, Ville_naissance, Adresse, Code_postal, Ville)
VALUES ('TEST_LOC2', 'Martin', 'Marie', '0623456789', 'marie.martin@test.fr', TO_DATE('1985-08-20', 'YYYY-MM-DD'), 'Lyon', '2 Rue Test', '31100', 'Toulouse');

INSERT INTO sae_Locataire (Id_Locataire, Nom, Prenom, Num_tel, Mail, Date_naissance, Ville_naissance, Adresse, Code_postal, Ville)
VALUES ('TEST_LOC3', 'Durand', 'Pierre', '0634567890', 'pierre.durand@test.fr', TO_DATE('1995-12-10', 'YYYY-MM-DD'), 'Marseille', '3 Rue Test', '31200', 'Toulouse');

-- Insérer des biens louables de test
INSERT INTO sae_Bien_louable (Id_Bien, Adresse, Ville, Code_postal, Type_bien, Date_construction, Surface_habitable, Nb_pieces, 
    Pourcentage_entretien_parties_communes, Pourcentage_ordures_menageres, Numero_Fiscal, Loyer, Provision_pour_charges, Id_Batiment)
VALUES ('TEST_BIEN1', '10 Rue Test Appt 1', 'Toulouse', '31000', 'Logement', TO_DATE('2000-01-01', 'YYYY-MM-DD'), 
    50.00, 2, 10.00, 5.00, '111111111111', 650.00, 50.00, 'TEST_BAT1');

INSERT INTO sae_Bien_louable (Id_Bien, Adresse, Ville, Code_postal, Type_bien, Date_construction, Surface_habitable, Nb_pieces, 
    Pourcentage_entretien_parties_communes, Pourcentage_ordures_menageres, Numero_Fiscal, Loyer, Provision_pour_charges, Id_Batiment)
VALUES ('TEST_BIEN2', '20 Avenue Test Appt 2', 'Toulouse', '31100', 'Logement', TO_DATE('2005-06-15', 'YYYY-MM-DD'), 
    75.00, 3, 15.00, 7.00, '222222222222', 850.00, 75.00, 'TEST_BAT2');

INSERT INTO sae_Bien_louable (Id_Bien, Adresse, Ville, Code_postal, Type_bien, Date_construction, Surface_habitable, Nb_pieces, 
    Pourcentage_entretien_parties_communes, Pourcentage_ordures_menageres, Numero_Fiscal, Loyer, Provision_pour_charges, Id_Batiment)
VALUES ('TEST_BIEN3', '10 Rue Test Garage', 'Toulouse', '31000', 'Garage', TO_DATE('2000-01-01', 'YYYY-MM-DD'), 
    15.00, 1, 5.00, 2.00, '333333333333', 100.00, 10.00, 'TEST_BAT1');

-- Insérer des locations de test (table SAE_LOUER)
-- CAS 1 : Bail expiré depuis longtemps (doit être renouvelé)
INSERT INTO SAE_LOUER (ID_BIEN, ID_LOCATAIRE, DATE_DEBUT, DATE_FIN,DEPOT_DE_GARANTIE, REVOLUE)
VALUES ('TEST_BIEN1', 'TEST_LOC1', TO_DATE('2021-01-01', 'YYYY-MM-DD'), TO_DATE('2024-01-01', 'YYYY-MM-DD'),500, 0);

-- CAS 2 : Bail expiré récemment (doit être renouvelé)
INSERT INTO SAE_LOUER (ID_BIEN, ID_LOCATAIRE, DATE_DEBUT, DATE_FIN,DEPOT_DE_GARANTIE,REVOLUE)
VALUES ('TEST_BIEN2', 'TEST_LOC2', TO_DATE('2022-01-01', 'YYYY-MM-DD'), ADD_MONTHS(SYSDATE, -2),500, 0);

-- CAS 3 : Bail en cours (ne doit PAS être renouvelé)
INSERT INTO SAE_LOUER (ID_BIEN, ID_LOCATAIRE, DATE_DEBUT, DATE_FIN,DEPOT_DE_GARANTIE, REVOLUE)
VALUES ('TEST_BIEN3', 'TEST_LOC3', TO_DATE('2024-01-01', 'YYYY-MM-DD'), ADD_MONTHS(SYSDATE, 12),500, 0);




SELECT id_bien, id_locataire, date_debut, date_fin, revolue,
       CASE 
           WHEN date_fin < SYSDATE THEN 'À renouveler'
           ELSE 'En cours'
       END AS STATUT
FROM sae_louer
WHERE revolue = 0
AND Id_bien LIKE 'TEST%'
ORDER BY date_fin;

--test automatisé
SET SERVEROUTPUT ON;

DECLARE
    vcount_avant NUMBER;
    vcount_apres NUMBER;
    vdate_fin_avant DATE;
    vdate_fin_apres DATE;
BEGIN
    -- Compte les baux expirés avant
    SELECT COUNT(*) INTO vcount_avant
    FROM sae_louer
    WHERE revolue = 0 AND date_fin < SYSDATE
    AND Id_bien LIKE 'TEST%';
    
    DBMS_OUTPUT.PUT_LINE('Baux expirés avant renouvellement : ' || vcount_avant);
    
    -- Sauvegarder une date de fin pour comparaison
    BEGIN
        SELECT DATE_FIN INTO vdate_fin_avant
        FROM sae_louer
        WHERE revolue = 0 AND date_fin < SYSDATE
        AND Id_bien LIKE 'TEST%'
        AND ROWNUM = 1;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            vdate_fin_avant := NULL;
    END;
    
    -- Exécuter la procédure
    RENOUVELER_BAUX_AUTOMATIQUE;
    
    -- Compter les baux expirés après
    SELECT COUNT(*) INTO vcount_apres
    FROM sae_louer
    WHERE revolue = 0 AND date_fin < SYSDATE
    AND Id_bien LIKE 'TEST%';
    
    
    DBMS_OUTPUT.PUT_LINE('Baux expirés après renouvellement : ' || vcount_apres);
    
    -- Vérifier le changement de date
    IF vdate_fin_avant IS NOT NULL THEN
        DBMS_OUTPUT.PUT_LINE('Date avant : ' || TO_CHAR(vdate_fin_avant, 'DD/MM/YYYY'));
        DBMS_OUTPUT.PUT_LINE('Date attendue : ' || TO_CHAR(ADD_MONTHS(vdate_fin_avant, 36), 'DD/MM/YYYY'));
    END IF;
    
    -- Test réussi si aucun bail expiré restant
    IF vcount_apres = 0 THEN
        DBMS_OUTPUT.PUT_LINE('TEST RÉUSSI : Tous les baux ont été renouvelés');
    ELSE
        DBMS_OUTPUT.PUT_LINE('TEST ÉCHOUÉ : Il reste des baux expirés');
    END IF;
    
    ROLLBACK; -- Annuler les changements pour rejouer le test
END;





