/* test la fonction TOTAL_ORDURES_SUR_PERIODE qui calcul le montant des ordures ménagères
pour un bien louable sur une période donnée censé renvoyé 522.
*/


INSERT INTO sae_Batiment VALUES ('BAT014', TO_DATE('2010-05-15', 'YYYY-MM-DD'), '40 rue des lilas', '31000', 'Toulouse',125469878963);
INSERT INTO sae_Bien_louable VALUES ('BIEN045', '40 rue des lilas - Apt 301', 'Toulouse', '31200', 'Logement', TO_DATE('2018-03-10', 'YYYY-MM-DD'), 90.00, 4, 10, 60, '345678501234', 1250.00, 50.00, 'BAT014',null);
INSERT INTO sae_Bien_louable VALUES ('BIEN046', '40 rue des lilas - Apt 302', 'Toulouse', '31200', 'Logement', TO_DATE('2018-03-10', 'YYYY-MM-DD'), 60.00, 2, 39, 32, '345978901235', 800.00, 33.75, 'BAT014',null);
INSERT INTO sae_compteur values ('BIEN045', null, 'CPT_EAU_045','Eau', TO_DATE('2024-01-15', 'YYYY-MM-DD'),0);
INSERT INTO sae_Locataire VALUES ('LOC045', 'Momo', 'Rameur', '0667890123', 'momolerameur@email.fr', TO_DATE('1985-02-12', 'YYYY-MM-DD'), 'Nice', '40 rue des lilas - Apt 301', '31200', 'Toulouse');
INSERT INTO sae_Louer VALUES ('BIEN045', 'LOC045', TO_DATE('2024-09-01', 'YYYY-MM-DD'), TO_DATE('2027-08-31', 'YYYY-MM-DD'), 1700.00, TO_DATE('2024-08-28', 'YYYY-MM-DD'), NULL, 'Appartement en bon état général. Quelques traces sur les murs. Équipements fonctionnels.', NULL, NULL, NULL, NULL, NULL, NULL, 0,NULL);
INSERT INTO sae_Releve_compteur VALUES (TO_DATE('2026-02-15', 'YYYY-MM-DD'), 2376, 0, 0, 'CPT_EAU_045',0);
INSERT INTO sae_Releve_compteur VALUES (TO_DATE('2026-03-15', 'YYYY-MM-DD'), 2463, 2.86, 5.51, 'CPT_EAU_045',2376);
INSERT INTO sae_Releve_compteur VALUES (TO_DATE('2026-06-15', 'YYYY-MM-DD'), 2568, 2.86, 5.51, 'CPT_EAU_045',2463);
Insert Into sae_Intervention values('Intervention_ordures_BAT014_fin','orudre du bat fin 2026',165874648,450,0,Null, TO_DATE('2026-12-01', 'YYYY-MM-DD'),null,null,TO_DATE('2026-12-01', 'YYYY-MM-DD'), 0,0,'BAT014',23456789012345,0,1);
Insert Into sae_Intervention values('Intervention_entretien_BAT014','ménage partie communes 2026',165874948,180,0,Null, TO_DATE('2026-05-01', 'YYYY-MM-DD'),null,null,TO_DATE('2026-05-01', 'YYYY-MM-DD'), 0,0,'BAT014',23456789012345,1,0);
Insert Into sae_Intervention values('Intervention_ordures_BAT014_moitié','orudre du bat mi 2026',165874648,420,0,Null, TO_DATE('2026-06-01', 'YYYY-MM-DD'),null,null,TO_DATE('2026-06-01', 'YYYY-MM-DD'), 0,0,'BAT014',23456789012345,0,1);

SELECT TOTAL_ORDURES_SUR_PERIODE('BIEN045',TO_DATE('2026-01-01', 'YYYY-MM-DD'),TO_DATE('2026-12-31', 'YYYY-MM-DD')) from dual;

rollback;

