--insère le changement de loyer dans l'historique loyers apres une insertion ou 
--ou une mise a jour du loyer ou de la provision pour charge d'un bien louable

create or replace TRIGGER "T_A_IU_SAE_BIEN_LOUABLE" AFTER INSERT OR UPDATE ON SAE_BIEN_LOUABLE
FOR EACH ROW
DECLARE

    premier_jour_du_mois DATE;
    
BEGIN

    -- si on insère deux valeurs differentes le meme mois il faudra faire un tri desc sur l'id de historique_loyers

    IF INSERTING THEN
    
    	premier_jour_du_mois :=  TRUNC(:NEW.DATE_CONSTRUCTION, 'MM');
    
        INSERT INTO SAE_HISTORIQUE_LOYERS (LOYER, PROVISION, ID_BIEN, MOIS_CONCERNE)
        VALUES (:NEW.LOYER, :NEW.PROVISION_POUR_CHARGES, :NEW.ID_BIEN, premier_jour_du_mois);
    END IF;
    
    
    IF UPDATING THEN
        IF(:NEW.LOYER <> :OLD.LOYER OR :NEW.PROVISION_POUR_CHARGES <> :OLD.PROVISION_POUR_CHARGES) THEN
    
            premier_jour_du_mois :=  TRUNC(SYSDATE, 'MM');
            
            INSERT INTO SAE_HISTORIQUE_LOYERS (LOYER, PROVISION, ID_BIEN, MOIS_CONCERNE)
            VALUES (:NEW.LOYER, :NEW.PROVISION_POUR_CHARGES, :NEW.ID_BIEN, premier_jour_du_mois);
            
        END IF;
    END IF;

END;