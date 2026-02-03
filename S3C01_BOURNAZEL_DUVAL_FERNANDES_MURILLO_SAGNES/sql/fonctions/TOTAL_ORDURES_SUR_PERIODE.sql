-- calcul le montant total des ordures pour un bien louable sur une periode donnée

create or replace function TOTAL_ORDURES_SUR_PERIODE(
	
    P_Id_bien sae_bien_louable.id_bien%type,
    P_Date_debut_periode date,
    P_Date_fin_periode date)return Number is
    
    
    varId_bat SAE_BATIMENT.ID_BATIMENT%type;
    varMontantOrdure SAE_CHARGE.MONTANT%type;
    dateMin SAE_LOUER.DATE_DEBUT%TYPE;
    locEnCours SAE_LOUER%ROWTYPE;
    varPourcentageOrdure sae_bien_louable.pourcentage_Ordures_Menageres%type;
    
begin

    if P_Date_debut_periode > P_Date_fin_periode then
        RAISE_APPLICATION_ERROR(-20013,'La date de début de période doit être avant celle de fin');
    end if;
    
    Begin
        select id_batiment,pourcentage_Ordures_Menageres 
        into varId_bat, varPourcentageOrdure
        from sae_bien_louable
        where id_bien = P_Id_bien;
    Exception
        when NO_DATA_FOUND then
            RAISE_APPLICATION_ERROR(-20014,'le bien ' || P_Id_Bien || ' n''existe pas');
    end;
    
    
    BEGIN
        SELECT * INTO locEnCours
        FROM (
            SELECT *
            FROM SAE_LOUER
            WHERE ID_BIEN = P_Id_bien
            AND DATE_FIN >= P_Date_debut_periode
            AND DATE_DEBUT <= P_Date_fin_periode
            ORDER BY DATE_DEBUT DESC
        )
        WHERE ROWNUM = 1;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            raise_application_error(-20004,'Une régularisation ne peut se faire que sur un bien en cours de location. Le bien ' || P_Id_bien || ' n'' est pas en cours de location.');
    END;
    
    -- pour ne calculer que les charges EFFECTIVES sur la période de la location.
    dateMin := GREATEST(P_Date_debut_periode, locEnCours.DATE_DEBUT);
    
    -- puis les ordures 
    select nvl(sum(Montant), 0) into varMontantOrdure
    from sae_charge
    where id_batiment = varId_bat
    and date_facturation between dateMin and P_Date_fin_periode
    and lower(libelle) like '%ordures du batiment%';
    
    return (varMontantOrdure*(varPourcentageOrdure/100));
    
end;