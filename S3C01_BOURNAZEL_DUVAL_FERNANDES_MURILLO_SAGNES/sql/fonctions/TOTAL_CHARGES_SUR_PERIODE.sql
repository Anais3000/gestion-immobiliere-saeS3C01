--calcul le montant total des charges pour un bien louable sur une période donnée

create or replace function total_charges_sur_periode(
    P_Id_bien sae_bien_louable.id_bien%type,
    P_Date_debut_periode date,
    P_Date_fin_periode date)return Number is
    
	varId_bat sae_batiment.id_batiment%type;
	varPourcentageEntretien sae_bien_louable.pourcentage_Entretien_Parties_Communes%type;
	varPourcentageOrdure sae_bien_louable.pourcentage_Ordures_Menageres%type;
	    
	varMontant sae_charge.montant%type;
	varMontantEntretien sae_charge.montant%type;
	varMontantOrdure sae_charge.montant%type;
	locEnCours SAE_LOUER%ROWTYPE;
	dateMin SAE_LOUER.DATE_DEBUT%TYPE;
    
begin
    if P_Date_debut_periode > P_Date_fin_periode then
        RAISE_APPLICATION_ERROR(-20013,'La date de début de période doit être avant celle de fin');
    end if;
    
    Begin
        select id_batiment,pourcentage_Ordures_Menageres,pourcentage_Entretien_Parties_Communes 
        into varId_bat, varPourcentageOrdure,varPourcentageEntretien
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
    
    -- partie un peu compliquée permettant de calculer uniquement ce que doit
    -- le locataire en fonction de si il était là ou pas 
    -- (exemple : période du compteur : janvier - juin mais le locaraire arrive en avril, il ne doit payer sa part QUE a partir d'avril)
    select nvl(sum(
        -- case si c'est un compteur alors coeff sinon NON
        CASE 
            WHEN DATE_DEBUT_PERIODE IS NOT NULL AND DATE_FIN_PERIODE IS NOT NULL THEN
                montant * (
                    (LEAST(DATE_FIN_PERIODE, P_Date_fin_periode) - GREATEST(DATE_DEBUT_PERIODE, locEnCours.DATE_DEBUT) + 1) / (DATE_FIN_PERIODE - DATE_DEBUT_PERIODE + 1)
                ) ELSE montant
        END
    ), 0) into varMontant
    from sae_charge
    where id_bien = P_Id_bien
    and ID_BATIMENT IS NULL
    and ID_INTERVENTION IS NULL
    and date_facturation between dateMin and P_Date_fin_periode
    and (DATE_DEBUT_PERIODE IS NULL 
         OR (DATE_FIN_PERIODE >= locEnCours.DATE_DEBUT AND DATE_DEBUT_PERIODE <= P_Date_fin_periode));
    
    select nvl(sum(
        CASE 
            -- pareil le case ici
            WHEN DATE_DEBUT_PERIODE IS NOT NULL AND DATE_FIN_PERIODE IS NOT NULL THEN
                Montant * (
                    (LEAST(DATE_FIN_PERIODE, P_Date_fin_periode) - GREATEST(DATE_DEBUT_PERIODE, locEnCours.DATE_DEBUT) + 1) / (DATE_FIN_PERIODE - DATE_DEBUT_PERIODE + 1)
                )
            ELSE Montant
        END
    ), 0) into varMontantEntretien
    from sae_charge
    where id_batiment = varId_bat
    and date_facturation between dateMin and P_Date_fin_periode
    and lower(libelle) like '%parties communes du batiment%'
    and (DATE_DEBUT_PERIODE IS NULL 
         OR (DATE_FIN_PERIODE >= locEnCours.DATE_DEBUT AND DATE_DEBUT_PERIODE <= P_Date_fin_periode));
    
    -- puis les ordures ont jamais de compteur donc pas besoin du coeff
    select nvl(sum(Montant), 0) into varMontantOrdure
    from sae_charge
    where id_batiment = varId_bat
    and date_facturation between dateMin and P_Date_fin_periode
    and lower(libelle) like '%ordures du batiment%';
    
    return varMontant + (varMontantOrdure*(varPourcentageOrdure/100)) +(varMontantEntretien *(varPourcentageEntretien/100));
end;