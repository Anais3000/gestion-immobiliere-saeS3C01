package modele.dao.requetes.assurance;

import modele.Assurance;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner toutes les assurances en BD, triées par année
 * de couverture décroissante
 */
public class RequeteSelectAssurance extends Requete<Assurance> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_ASSURANCE ORDER BY ANNEE_COUVERTURE DESC";
	}

}
