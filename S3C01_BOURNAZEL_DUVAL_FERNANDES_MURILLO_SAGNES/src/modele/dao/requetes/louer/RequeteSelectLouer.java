package modele.dao.requetes.louer;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des contrats de location stockés
 * en BD, trié par date de début décroissante
 */
public class RequeteSelectLouer extends Requete<Louer> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_LOUER ORDER BY DATE_DEBUT DESC";
	}

}
