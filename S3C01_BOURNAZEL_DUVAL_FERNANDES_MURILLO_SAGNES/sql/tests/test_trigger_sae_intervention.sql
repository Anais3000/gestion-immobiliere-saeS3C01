INSERT INTO sae_Batiment (Id_Batiment, Date_construction, Adresse, Code_postal, Ville, Numero_Fiscal_Bat)
VALUES ('TEST_BAT1', TO_DATE('2010-01-01', 'YYYY-MM-DD'), '100 Rue Test Compteur', '31000', 'Toulouse', '999999999999');
    
INSERT INTO sae_Organisme VALUES('11866788964582','Netoie2000','13 rue Danette aux fruits','31250','Muret','Netoie2000@mail.com','0758961234','Nettoyage');

INSERT INTO sae_Organisme VALUES('11866788974582','Ordure2000','13 rue Danette aux fruits','31250','Muret','Ordure2000@mail.com','0758961334','Collecte Ordure');

SEt SERVEROUTPUT ON;

BEGIN

    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 1 : INTERVENTION ENTRETIEN PC');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Montant facture: 250.00€');
    
    INSERT INTO SAE_INTERVENTION (ID_INTERVENTION, INTITULE, NUMERO_FACTURE, MONTANT_FACTURE, ACOMPTE, DATE_ACOMPTE, DATE_FACTURE, NUMERO_DEVIS, 
        MONTANT_DEVIS, DATE_INTERVENTION, MONTANT_NON_DEDUCTIBLE, REDUCTION, ID_BATIMENT, NUM_SIRET, ENTRETIEN_PC, ORDURES)
    VALUES ('TEST_1','Nettoyage escaliers','FACT001',250.00,0,NULL,TO_DATE('2024-06-15', 'YYYY-MM-DD'),'DEV001',
        250.00,TO_DATE('2024-06-15', 'YYYY-MM-DD'),0,0,'TEST_BAT1','11866788964582',1,0);
    
    FOR i IN (SELECT * FROM SAE_INTERVENTION WHERE ID_INTERVENTION = 'TEST_1') LOOP
        DBMS_OUTPUT.PUT_LINE('ID: ' || i.ID_INTERVENTION);
        DBMS_OUTPUT.PUT_LINE('Intitulé: ' || i.INTITULE);
        DBMS_OUTPUT.PUT_LINE('Date: ' || TO_CHAR(i.DATE_INTERVENTION, 'DD/MM/YYYY'));
        DBMS_OUTPUT.PUT_LINE('Montant: ' || i.MONTANT_FACTURE || '€');
        DBMS_OUTPUT.PUT_LINE('Entretien PC: ' || i.ENTRETIEN_PC);
        DBMS_OUTPUT.PUT_LINE('Ordures: ' || i.ORDURES);
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE('--- Charge créée ---');
    FOR c IN (SELECT * FROM SAE_CHARGE WHERE ID_INTERVENTION = 'TEST_1') LOOP
        DBMS_OUTPUT.PUT_LINE('Libellé: ' || c.LIBELLE);
        DBMS_OUTPUT.PUT_LINE('Montant: ' || c.MONTANT || '€');
        DBMS_OUTPUT.PUT_LINE('Date facturation: ' || TO_CHAR(c.DATE_FACTURATION, 'DD/MM/YYYY'));
        DBMS_OUTPUT.PUT_LINE('ID Bâtiment: ' || c.ID_BATIMENT);
    END LOOP;
    
END;
/

BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 2 : INTERVENTION ORDURES');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Montant facture: 180.00€');
    
    INSERT INTO SAE_INTERVENTION (ID_INTERVENTION, INTITULE, NUMERO_FACTURE, MONTANT_FACTURE, ACOMPTE, DATE_ACOMPTE, DATE_FACTURE, NUMERO_DEVIS, 
        MONTANT_DEVIS, DATE_INTERVENTION, MONTANT_NON_DEDUCTIBLE, REDUCTION, ID_BATIMENT, NUM_SIRET, ENTRETIEN_PC, ORDURES)
    VALUES ('TEST_2','Collecte Ordure','FACT002',180.00,0,NULL,TO_DATE('2024-06-15', 'YYYY-MM-DD'),'DEV002',
        180.00,TO_DATE('2024-06-15', 'YYYY-MM-DD'),0,0,'TEST_BAT1','11866788974582',0,1);
    
    DBMS_OUTPUT.PUT_LINE('--- Charge créée ---');
    FOR c IN (SELECT * FROM SAE_CHARGE WHERE ID_INTERVENTION = 'TEST_2') LOOP
        DBMS_OUTPUT.PUT_LINE('Libellé: ' || c.LIBELLE);
        DBMS_OUTPUT.PUT_LINE('Montant: ' || c.MONTANT || '€');
        DBMS_OUTPUT.PUT_LINE('Date facturation: ' || TO_CHAR(c.DATE_FACTURATION, 'DD/MM/YYYY'));
        DBMS_OUTPUT.PUT_LINE('ID Bâtiment: ' || c.ID_BATIMENT);
    END LOOP;
END;
/
DECLARE
    v_nb_charge NUMBER;
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 3 : INTERVENTION NORMALE');
    DBMS_OUTPUT.PUT_LINE('========================================'); 
    DBMS_OUTPUT.PUT_LINE('Aucune charge ne devrait être créée.');
    
    INSERT INTO SAE_INTERVENTION (ID_INTERVENTION, INTITULE, NUMERO_FACTURE, MONTANT_FACTURE, ACOMPTE, DATE_ACOMPTE, DATE_FACTURE, NUMERO_DEVIS, 
        MONTANT_DEVIS, DATE_INTERVENTION, MONTANT_NON_DEDUCTIBLE, REDUCTION, ID_BATIMENT, NUM_SIRET, ENTRETIEN_PC, ORDURES)
    VALUES ('TEST_3','Collecte Ordure','FACT003',500.00,0,NULL,TO_DATE('2024-06-15', 'YYYY-MM-DD'),'DEV003',
        500.00,TO_DATE('2024-06-15', 'YYYY-MM-DD'),0,0,'TEST_BAT1','11866788964582',0,1);
    SELECT COUNT(*) INTO v_nb_charge
    FROM SAE_CHARGE 
    WHERE ID_INTERVENTION = 'TEST_3';
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('Nombre de charges créées: ' || v_nb_charge);
    IF v_nb_charge = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Aucune charge créée (comportement attendu)');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Une charge a été créée alors qu''elle ne devrait pas !');
    END IF;
END;
/
BEGIN
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('TEST 4 : UPDATE INTERVENTION');
    DBMS_OUTPUT.PUT_LINE('========================================');
    DBMS_OUTPUT.PUT_LINE('Modification de l''intervention TEST_INT1...');
    DBMS_OUTPUT.PUT_LINE('Ancien montant: 250€ -> Nouveau: 300€');
    DBMS_OUTPUT.PUT_LINE('Ancienne date: 15/06/2024 -> Nouvelle: 20/06/2024');
    
    UPDATE SAE_INTERVENTION
    SET MONTANT_FACTURE = 300.00,
        DATE_INTERVENTION = TO_DATE('2024-06-20', 'YYYY-MM-DD')
    WHERE ID_INTERVENTION = 'TEST_1';
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('--- APRÈS UPDATE ---');
    FOR c IN (SELECT * FROM SAE_CHARGE WHERE ID_INTERVENTION = 'TEST_1') LOOP
        DBMS_OUTPUT.PUT_LINE('Charge - Montant: ' || c.MONTANT || '€ | Date: ' || TO_CHAR(c.DATE_FACTURATION, 'DD/MM/YYYY'));
        IF c.MONTANT = 300.00 AND c.DATE_FACTURATION = TO_DATE('2024-06-20', 'YYYY-MM-DD') THEN
            DBMS_OUTPUT.PUT_LINE('La charge a été mise à jour correctement');
        ELSE
            DBMS_OUTPUT.PUT_LINE('La charge n''a pas été mise à jour');
        END IF;
    END LOOP;
END;
/

ROLLBACK;
DELETE FROM sae_organisme WHERE adresse = '13 rue Danette aux fruits';
EXEC SUPPRIMER_BATIMENT('TEST_BAT1');
COMMIT;
        
    




