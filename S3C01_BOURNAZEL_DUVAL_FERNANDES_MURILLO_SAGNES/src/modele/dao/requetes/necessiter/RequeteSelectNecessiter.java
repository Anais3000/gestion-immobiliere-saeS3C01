package modele.dao.requetes.necessiter;

import modele.Necessiter;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des interventions nécessitées par
 * les biens stocké en BD
 */
public class RequeteSelectNecessiter extends Requete<Necessiter> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_NECESSITER";
	}

}
