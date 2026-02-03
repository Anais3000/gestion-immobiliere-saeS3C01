--calcul le total des loyers (uniquement loyers) qui ont été payé durant un mois donné
--prends le mois en cours par défaut

create or replace FUNCTION TOTAL_LOYERS_PAYES_DANS_MOIS
(p_Mois DATE DEFAULT SYSDATE)
RETURN NUMBER
AS
    vTotalPaye NUMBER := 0;
    vMois DATE;
BEGIN
    vMois := TRUNC(p_Mois, 'MM');

    SELECT NVL(SUM(montant),0) INTO vTotalPaye
    FROM sae_paiement
    WHERE libelle='loyer'
    AND TRUNC(mois_concerne, 'MM')=vMois;
    RETURN vTotalPaye;
END;