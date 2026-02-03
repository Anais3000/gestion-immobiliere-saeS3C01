package modele.dao.requetes.irl;

import modele.IRL;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des IRL stockés en BD, triés par
 * année décroissante puis trimestre décroissant
 */
public class RequeteSelectIRL extends Requete<IRL> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_IRL ORDER BY ANNEE DESC, TRIMESTRE DESC";
	}

}
