/* Test de la fonction Total_Loyers_impayer qui 
calcul le montant des loyers impayés entre la date du jour et le début de la location
Le test renvoie 830*(le nombre de moi entre le 1er janvier 2026 et la date du jour + 1)
*/

INSERT INTO sae_Batiment VALUES ('BAT014', TO_DATE('2010-05-15', 'YYYY-MM-DD'), '40 rue des lilas', '31000', 'Toulouse',125469878963);
INSERT INTO sae_Bien_louable VALUES ('BIEN045', '40 rue des lilas - Apt 301', 'Toulouse', '31200', 'Logement', TO_DATE('2018-03-10', 'YYYY-MM-DD'), 90.00, 4, 10, 60, '345678501234',830.00, 70.00, 'BAT014',null);
INSERT INTO sae_Locataire VALUES ('LOC045', 'Momo', 'Rameur', '0667890123', 'momolerameur@email.fr', TO_DATE('1985-02-12', 'YYYY-MM-DD'), 'Nice', '40 rue des lilas - Apt 301', '31200', 'Toulouse');
INSERT INTO sae_Louer VALUES ('BIEN045', 'LOC045', TO_DATE('2023-04-24', 'YYYY-MM-DD'), TO_DATE('2029-12-24', 'YYYY-MM-DD'), 1660.00, TO_DATE('2024-12-28', 'YYYY-MM-DD'), NULL, 'Appartement en bon état général. Quelques traces sur les murs. Équipements fonctionnels.', NULL, NULL, TO_DATE('2025-12-28','YYYY-MM-DD'), NULL ,NULL, NULL, 0, NULL);
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2023-04-25', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2023-04-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2023-05-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2023-05-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2023-06-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2023-06-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2023-07-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2023-07-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2023-08-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2023-08-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2023-09-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2023-09-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2023-10-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2023-10-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2023-11-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2023-11-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2023-12-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2023-12-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2024-01-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2024-01-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2024-02-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2024-02-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2024-03-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2024-03-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2024-04-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2024-04-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2024-05-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2024-05-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2024-06-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2024-06-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2024-07-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2024-07-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2024-08-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2024-08-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2024-09-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2024-09-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2024-10-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2024-10-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2024-11-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2024-11-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2024-12-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2024-12-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2025-01-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2025-01-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2025-02-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2025-02-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2025-03-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2025-03-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2025-04-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2025-04-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2025-05-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2025-05-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2025-06-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2025-06-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2025-07-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2025-07-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2025-08-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2025-08-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2025-09-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2025-09-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2025-10-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2025-10-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2025-11-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2025-11-01', 'YYYY-MM-DD'));
Insert into sae_paiement (montant,sens_paiement,libelle,date_paiement,id_bien,id_intervention,mois_concerne) 
values(900,'recus','loyer',TO_DATE('2025-12-28', 'YYYY-MM-DD'),'BIEN045',null,TO_DATE('2025-12-01', 'YYYY-MM-DD'));

select Total_loyers_impayes('BIEN045','LOC045',TO_DATE('2023-04-24', 'YYYY-MM-DD')) from dual;

rollback;

