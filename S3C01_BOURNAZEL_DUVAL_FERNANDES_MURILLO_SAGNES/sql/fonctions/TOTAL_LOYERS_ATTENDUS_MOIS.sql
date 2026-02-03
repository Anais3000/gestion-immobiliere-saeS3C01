--calcul le total des loyers+provision attendus pour le mois en cours selon
-- les biens en cours de location

create or replace FUNCTION TOTAL_LOYERS_ATTENDUS_MOIS
(p_mois date default sysdate)
RETURN NUMBER
AS
    vMontant NUMBER := 0;
    vAjustement NUMBER :=0;
    
  
BEGIN
           
        SELECT NVL(SUM(loyer + provision_pour_charges),0)INTO vMontant
        FROM sae_bien_louable bl, sae_louer l
        WHERE l.id_bien=bl.id_bien
        AND revolue = 0;
        
       select  NVL(SUM(ajustement_loyer),0) into vAjustement
       FROM sae_bien_louable bl, sae_louer l
        WHERE l.id_bien=bl.id_bien
        AND p_mois between MOIS_DEBUT_AJUSTEMENT and MOIS_FIN_AJUSTEMENT
        AND revolue = 0;
            

    RETURN vMontant + vAjustement;
END;