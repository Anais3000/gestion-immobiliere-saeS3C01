--calcul le total des loyers impayes du mois actuel, pour tous les biens loués

create or replace FUNCTION total_loyer_impayes_mois_actuel
(
    p_mois DATE DEFAULT SYSDATE
)
RETURN NUMBER
AS
    vMontantImpaye NUMBER :=0;
BEGIN 
    vMontantImpaye := TOTAL_LOYERS_ATTENDUS_MOIS() - TOTAL_LOYERS_PAYES_DANS_MOIS();
    RETURN vMontantImpaye;
END;