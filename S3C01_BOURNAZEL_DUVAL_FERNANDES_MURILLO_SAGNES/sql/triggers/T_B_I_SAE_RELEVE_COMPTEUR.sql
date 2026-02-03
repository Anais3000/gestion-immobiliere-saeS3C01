-- calcul et ajoute au charges la facture d'un compteur aux charges que se soit
-- pour les biens louables ou les batiments

create or replace trigger T_B_I_SAE_RELEVE_COMPTEUR
Before insert on sae_releve_compteur
for each row
declare

	varId_bien varchar2(50);
	varId_bat varchar2(50);
	
	varDernierReleve sae_releve_compteur%rowtype;
	dernierReleveTrouve BOOLEAN := TRUE;
	
	varMontant Number;
	varType_compteur sae_compteur.type_compteur%type;
	
	varDateDebutPeriode SAE_CHARGE.DATE_DEBUT_PERIODE%TYPE;
	varDateFinPeriode SAE_CHARGE.DATE_FIN_PERIODE%TYPE;

begin

    select id_bien,id_batiment, type_compteur into varId_bien, varId_bat, varType_compteur
    from sae_compteur
    where id_compteur = :New.id_compteur;


    Begin  
        select * into varDernierReleve
        from (
            select *
            from sae_releve_compteur 
            where id_compteur = :New.id_compteur
            and date_releve < :New.date_releve
            order by date_releve desc
            )
        where ROWNUM =1;
    Exception
        when NO_DATA_FOUND then
            dernierReleveTrouve := FALSE;
    end;
    
    
    -- On part du principe que la période couverte est la période date ancien relevé - date nouveau relevé
    -- si le compteur est nouveau (et donc pas d'ancien relevé), on prend la date d'installation du compteur
    if NOT dernierReleveTrouve then
        SELECT DATE_INSTALLATION INTO varDateDebutPeriode
        FROM SAE_COMPTEUR
        WHERE ID_COMPTEUR = :NEW.ID_COMPTEUR;
        varMontant := (:New.index_compteur * :New.prix_unite) + :New.partie_fixe;
    else
        varDateDebutPeriode := varDernierReleve.DATE_RELEVE + 1;
        -- if nécessaire si on change le compteur car sinon ça donne une valeur négative
        if :NEW.index_compteur >= varDernierReleve.index_compteur THEN
            varMontant := ((:New.index_compteur - varDernierReleve.index_compteur) * :New.prix_unite) + :New.partie_fixe;
        else 
            varMontant := (:New.index_compteur * :New.prix_unite) + :New.partie_fixe;
        end if;
    end if; 
    
    varDateFinPeriode := :NEW.DATE_RELEVE;

	-- Si le compteur appartient à un bien alors
    if varId_bien is not null then
        insert into sae_charge (libelle, date_facturation,montant,id_bien, date_debut_periode, date_fin_periode)
        values(varType_compteur || ' du bien ' || varId_bien || ' du '|| :New.date_releve, :New.date_releve,varMontant, varId_bien, varDateDebutPeriode, varDateFinPeriode);
    end if;
    
    -- Si le compteur appartient à un batiment en général alors
    if varId_bat is not null then
        insert into sae_charge (libelle, date_facturation,montant,id_batiment, date_debut_periode, date_fin_periode)
        values(varType_compteur || ' parties communes du batiment ' || varId_bat || ' du '|| :New.date_releve, :New.date_releve,varMontant, varId_bat, varDateDebutPeriode, varDateFinPeriode);
    end if;
    
    insert into sae_paiement (montant, sens_paiement, libelle, date_paiement)
    values(varMontant, 'emis', 'Paiement compteur ' || :NEW.ID_COMPTEUR, :NEW.DATE_RELEVE);
    
end;