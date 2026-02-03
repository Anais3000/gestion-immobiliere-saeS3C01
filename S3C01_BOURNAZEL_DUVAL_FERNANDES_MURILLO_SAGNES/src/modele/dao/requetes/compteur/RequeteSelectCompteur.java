package modele.dao.requetes.compteur;

import modele.Compteur;
import modele.dao.requetes.Requete;

/**
 * Requete retournant l'ensemble des compteurs stockés en BD
 */
public class RequeteSelectCompteur extends Requete<Compteur> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_COMPTEUR";
	}

}
