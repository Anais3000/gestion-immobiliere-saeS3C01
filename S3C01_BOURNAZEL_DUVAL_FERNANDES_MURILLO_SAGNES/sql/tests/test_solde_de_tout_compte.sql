/* test de la fonction Total_du_fin_de_bail qui renvoie le montant du solde de tout compte
Le test est effectué en Janvier 2026 la valeur correct obtenu en janviers 2026 : -232.95
Pour chaque moi passer apres le Janvier 2026 vous devais ajouter 910 au résultat obtenue en Janvier 2026.
*/
INSERT INTO sae_Batiment VALUES ('BAT014', TO_DATE('2010-05-15', 'YYYY-MM-DD'), '40 rue des lilas', '31000', 'Toulouse',125469878963);
INSERT INTO sae_Bien_louable VALUES ('BIEN045', '40 rue des lilas - Apt 301', 'Toulouse', '31200', 'Logement', TO_DATE('2018-03-10', 'YYYY-MM-DD'), 90.00, 4, 10, 60, '345678501234',830.00, 70.00, 'BAT014',null);
INSERT INTO sae_compteur values ('BIEN045', null, 'CPT_EAU_045','Eau', TO_DATE('2024-01-15', 'YYYY-MM-DD'),0);
INSERT INTO sae_Locataire VALUES ('LOC045', 'Momo', 'Rameur', '0667890123', 'momolerameur@email.fr', TO_DATE('1985-02-12', 'YYYY-MM-DD'), 'Nice', '40 rue des lilas - Apt 301', '31200', 'Toulouse');
INSERT INTO sae_Louer VALUES ('BIEN045', 'LOC045', TO_DATE('2023-04-24', 'YYYY-MM-DD'), TO_DATE('2026-01-24', 'YYYY-MM-DD'), 1660.00, TO_DATE('2024-12-28', 'YYYY-MM-DD'), NULL, 'Appartement en bon état général. Quelques traces sur les murs. Équipements fonctionnels.', NULL, NULL, TO_DATE('2025-12-28','YYYY-MM-DD'),  10 ,TO_DATE('2030-01-01','YYYY-MM-DD'), NULL, 0,TO_DATE('2026-01-01','YYYY-MM-DD'));
INSERT INTO sae_Releve_compteur VALUES (TO_DATE('2024-02-15', 'YYYY-MM-DD'), 2376, 0, 0, 'CPT_EAU_045',0);
INSERT INTO sae_Releve_compteur VALUES (TO_DATE('2025-03-15', 'YYYY-MM-DD'), 2463, 2.86, 5.51, 'CPT_EAU_045',2376);
INSERT INTO sae_Releve_compteur VALUES (TO_DATE('2025-09-15', 'YYYY-MM-DD'), 2568, 2.86, 5.51, 'CPT_EAU_045',2463);
INSERT INTO sae_Releve_compteur VALUES (TO_DATE('2025-12-15', 'YYYY-MM-DD'), 2723, 2.86, 5.51, 'CPT_EAU_045',2568);
INSERT INTO sae_Releve_compteur VALUES (TO_DATE('2026-01-21', 'YYYY-MM-DD'), 2762, 2.86, 5.51, 'CPT_EAU_045',2723);
Insert Into sae_Intervention values('Intervention_ordures_BAT014','orudre du bat fin 2026',165874648,450,0,Null, TO_DATE('2026-12-01', 'YYYY-MM-DD'),null,null,TO_DATE('2026-12-01', 'YYYY-MM-DD'), 0,0,'BAT014',23456789012345,0,1);
Insert Into sae_Intervention values('Intervention_entretien_BAT014','ménage partie communes 2026',165874948,180,0,Null, TO_DATE('2026-05-01', 'YYYY-MM-DD'),null,null,TO_DATE('2026-05-01', 'YYYY-MM-DD'), 0,0,'BAT014',23456789012345,1,0);
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


select Total_du_fin_de_bail('BIEN045','LOC045',TO_DATE('2023-04-24', 'YYYY-MM-DD'),350,TO_DATE('2026-01-01', 'YYYY-MM-DD'),TO_DATE('2026-01-24', 'YYYY-MM-DD')) from dual;
rollback;


