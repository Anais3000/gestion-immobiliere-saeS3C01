package modele.dao.requetes.louer;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des contrats de location non
 * révolus
 */
public class RequeteSelectLouerActuel extends Requete<Louer> {

	@Override
	public String requete() {
		return "select * from sae_louer where revolue= 0";
	}

}
