-- calcul le montant de la régularisation des charges d'un bien entre deux dates données
create or replace FUNCTION REGULARISATION_DES_CHARGES(P_ID_BIEN SAE_BIEN_LOUABLE.ID_BIEN%TYPE,
                                                      P_DATE_DEBUT_PERIODE DATE,
                                                      P_DATE_FIN_PERIODE DATE) RETURN NUMBER IS

    VARPROVISION SAE_BIEN_LOUABLE.PROVISION_POUR_CHARGES%TYPE;

BEGIN

    -- Pour avoir le total de provisions versées sur periode
    VARPROVISION := TOTAL_PROVISIONS_SUR_PERIODE(P_ID_BIEN, P_DATE_DEBUT_PERIODE, P_DATE_FIN_PERIODE);

    -- On retourne le total des charges sur cette periode moins le total de provisions versées
    RETURN TOTAL_CHARGES_SUR_PERIODE(P_ID_BIEN, P_DATE_DEBUT_PERIODE, P_DATE_FIN_PERIODE) - VARPROVISION;
    
END;