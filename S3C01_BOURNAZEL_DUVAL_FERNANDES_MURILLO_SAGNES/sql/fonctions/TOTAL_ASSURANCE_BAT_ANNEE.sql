--calcul le montant total des assurances pour un batiment et une année donnée

create or replace function Total_Assurance_Bat_Annee
    (p_id_bat sae_batiment.id_batiment%type,
    p_annee number)return Number is
  
    vMontant Number(10,2);
BEGIN
    select nvl(sum(montant_paye),0) into vMontant
    from sae_assurance
    where id_batiment = p_id_bat
        and annee_couverture = p_annee;
    return vMontant;
END;