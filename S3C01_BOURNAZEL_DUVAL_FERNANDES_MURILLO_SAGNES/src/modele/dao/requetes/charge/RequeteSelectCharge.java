package modele.dao.requetes.charge;

import modele.Charge;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de renvoyer l'ensemble des charges présentes en BD
 */
public class RequeteSelectCharge extends Requete<Charge> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_CHARGE";
	}

}
