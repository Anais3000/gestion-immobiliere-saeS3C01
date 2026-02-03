package modele.dao.requetes.batiment;

import modele.Batiment;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des batiments en BD
 */
public class RequeteSelectBatiment extends Requete<Batiment> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_BATIMENT";
	}

}
