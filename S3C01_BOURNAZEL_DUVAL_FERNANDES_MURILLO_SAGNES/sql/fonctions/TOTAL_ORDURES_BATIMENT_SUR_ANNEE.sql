--calcul le montant total des ordures menagère pour un batiment sur une annee donnée

create or replace function Total_ordures_Batiment_sur_Annee(p_id_bat sae_batiment.id_batiment%type,
    														p_annee number)RETURN DECIMAL IS
  
    vOrdure DECIMAL(10,2);
    
BEGIN

    select nvl(sum(montant),0) into vOrdure
    from sae_charge
    where id_batiment = p_id_bat
        and libelle like '%ordures du batiment%'
        and Extract(YEAR from date_facturation) = p_annee;
        
    RETURN vOrdure;
    
END;