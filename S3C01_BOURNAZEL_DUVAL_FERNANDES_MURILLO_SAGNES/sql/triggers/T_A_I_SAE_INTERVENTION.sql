--Insère une charge lorsque qu'une intervention qui concerne 
--l'entretien des parties commune ou les ordures est insérée 
create or replace TRIGGER T_A_I_SAE_INTERVENTION
AFTER INSERT ON SAE_INTERVENTION
FOR EACH ROW

BEGIN

    IF :NEW.ENTRETIEN_PC = 1 THEN

            INSERT INTO SAE_CHARGE (libelle, date_facturation,montant,id_batiment, id_intervention)
            VALUES('Intervention entretien parties communes du batiment ' || :NEW.ID_BATIMENT || ' du '|| :New.date_intervention, :New.date_intervention, :NEW.MONTANT_FACTURE, :NEW.ID_BATIMENT, :NEW.ID_INTERVENTION);

    ELSIF :NEW.ORDURES = 1 THEN

            INSERT INTO SAE_CHARGE (libelle, date_facturation,montant,id_batiment, id_intervention)
            VALUES('Intervention ordures du batiment ' || :NEW.ID_BATIMENT || ' du '|| :New.date_intervention, :New.date_intervention, :NEW.MONTANT_FACTURE, :NEW.ID_BATIMENT, :NEW.ID_INTERVENTION);

    END IF;

END;