--supprime des charges la charge lié au relevé compteur que se sois pour
--les compteurs du batiments ou du bien louable

create or replace trigger T_B_D_Sae_releve_Compteur
before delete on sae_releve_compteur
for each row

declare 

	varId_bien varchar2(50);
	varId_bat varchar2(50);
	varType_compteur sae_compteur.type_compteur%type;
	
begin

    select id_bien, id_batiment, type_compteur
    into varId_bien, varId_bat, varType_compteur
    from sae_compteur
    where id_compteur = :Old.id_compteur;

    if varId_bien is not null then
        delete from sae_charge
        where id_bien = varId_bien
        and date_facturation = :Old.date_releve
        and libelle = varType_compteur || ' du bien ' || varId_bien || ' du ' || :OLD.date_releve;
    end if;

    if varId_bat is not null then
        delete from sae_charge
        where id_batiment = varId_bat
        and date_facturation = :Old.date_releve
        and libelle = varType_compteur || ' parties communes du batiment ' || varId_bat || ' du ' || :Old.date_releve;
    end if;
    
    delete from sae_paiement
    where libelle = 'Paiement compteur ' || :OLD.ID_COMPTEUR
    and date_paiement = :OLD.DATE_RELEVE;
    
end;