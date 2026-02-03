package modele.dao.requetes.organisme;

import modele.Organisme;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des organismes présents en BD
 */
public class RequeteSelectOrganisme extends Requete<Organisme> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_ORGANISME";
	}

}
