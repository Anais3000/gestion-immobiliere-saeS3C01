package modele.dao.requetes.intervention;

import modele.Intervention;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des interventions stockées en BD
 */
public class RequeteSelectIntervention extends Requete<Intervention> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_INTERVENTION";
	}

}
