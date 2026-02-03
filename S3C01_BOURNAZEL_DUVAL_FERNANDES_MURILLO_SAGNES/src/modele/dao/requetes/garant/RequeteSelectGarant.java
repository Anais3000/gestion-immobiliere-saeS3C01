package modele.dao.requetes.garant;

import modele.Garant;
import modele.dao.requetes.Requete;

/**
 * Requete retournant l'ensemble des garants stockés en BD
 */
public class RequeteSelectGarant extends Requete<Garant> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_GARANT";
	}

}
