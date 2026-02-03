package modele.dao.requetes.bienlouable;

import modele.BienLouable;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des biens louables présents en BD
 */
public class RequeteSelectBienLouable extends Requete<BienLouable> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_BIEN_LOUABLE";
	}

}
