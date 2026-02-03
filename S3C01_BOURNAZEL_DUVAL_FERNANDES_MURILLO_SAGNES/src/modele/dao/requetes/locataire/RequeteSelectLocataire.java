package modele.dao.requetes.locataire;

import modele.Locataire;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des locataires en BD, trié par ID
 * locataire croissant
 */
public class RequeteSelectLocataire extends Requete<Locataire> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_LOCATAIRE ORDER BY ID_LOCATAIRE";
	}

}
