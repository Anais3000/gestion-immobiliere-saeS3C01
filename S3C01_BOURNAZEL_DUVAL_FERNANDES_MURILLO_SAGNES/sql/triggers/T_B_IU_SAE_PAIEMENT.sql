-- lève des exceptions adéquates en fonction de l'insertion d'un paiement
-- un paiement concerne soit une intervention, soit un bien, et il faut que
-- le bien soit occupé

create or replace trigger T_B_IU_sae_Paiement
before insert or update on sae_Paiement
for each row

declare

	varId_locataire sae_locataire.id_locataire%type;
	
begin

    if :New.id_bien is not null and :New.id_intervention is not null then
        RAISE_APPLICATION_ERROR(-20003,'Un paiement concerne soit une intervention soit un bien louable');
    end if;
    
    if :New.libelle = 'loyer' then
    
        Begin
            select id_locataire into varId_locataire
            from sae_louer
            where id_bien = :New.id_bien
            and :New.date_paiement between date_debut and date_fin;
        Exception
            when NO_DATA_FOUND then
                varId_locataire := null;
        end;
        
        if varId_locataire is null then
            RAISE_APPLICATION_ERROR(-20004,'Le bien n''est pas occupé');
        end if;
        
    end if;
    
end;