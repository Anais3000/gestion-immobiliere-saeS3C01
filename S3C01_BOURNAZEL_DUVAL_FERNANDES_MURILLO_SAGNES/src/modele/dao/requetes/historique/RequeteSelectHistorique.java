package modele.dao.requetes.historique;

import modele.Historique;
import modele.dao.requetes.Requete;

/**
 * Requete retournant l'ensemble des actions d'historique stockées en BD, par
 * ordre de date de l'action décroissante
 */
public class RequeteSelectHistorique extends Requete<Historique> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_HISTORIQUE ORDER BY date_action DESC";
	}

}
