-- calcul le montant total des provisions percues pour un bien sur une année

create or replace function Total_provisions_percus_bien_annee(p_id_bien sae_bien_louable.id_bien%type,
    														  p_annee number)return DECIMAL is
  
    vTotal_provisions DECIMAL(10,2);
    
BEGIN

    select nvl(sum(h.provision),0) into vTotal_provisions
    from sae_paiement p, sae_historique_loyers h
    where p.id_bien=h.id_bien
        and p.id_bien = p_id_bien
        and p.libelle = 'loyer'
        and p.mois_concerne is not null
        and Extract(YEAR from p.mois_concerne) = p_annee
        and h.mois_concerne = (
            select max(h2.mois_concerne)
            from sae_historique_loyers h2
            where h2.id_bien = p.id_bien
                and extract(YEAR from h2.mois_concerne) <= p_annee);
        
      
    RETURN vTotal_provisions;
    
END;