package modele.dao.requetes.relevecompteur;

import modele.ReleveCompteur;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des relevés de compteur stockés en
 * BD
 */
public class RequeteSelectReleveCompteur extends Requete<ReleveCompteur> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_RELEVE_COMPTEUR";
	}

}
