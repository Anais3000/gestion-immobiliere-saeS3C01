package modele.dao.requetes.paiement;

import modele.Paiement;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des paiements stockés en BD, triés
 * par date de paiement décroissante, montant croissant
 */
public class RequeteSelectPaiement extends Requete<Paiement> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_PAIEMENT ORDER BY DATE_PAIEMENT DESC, MONTANT";
	}

}
