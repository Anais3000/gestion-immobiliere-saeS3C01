--Calcul le totale des loyers(uniquement loyers) percus pour un bien sur une année donnée

create or replace function Total_loyers_percus_bien_annee(p_id_bien sae_bien_louable.id_bien%type,
    													  p_annee number)return DECIMAL is
  
    vTotal_loyers DECIMAL(10,2);
    
BEGIN

    select nvl(sum(h.loyer),0) into vTotal_loyers
    from sae_paiement p, sae_historique_loyers h
    where p.id_bien=h.id_bien
        and p.libelle = 'loyer'
        and p.sens_paiement = 'recus'
        and p.mois_concerne is not null
        and Extract(YEAR from p.mois_concerne) = p_annee
        and p.id_bien = p_id_bien
        and h.mois_concerne = (
            select max(h2.mois_concerne)
            from sae_historique_loyers h2
            where h2.id_bien = p.id_bien
                and extract(YEAR from h2.mois_concerne) <= p_annee);
                
                
    Return vTotal_loyers;
    
END;