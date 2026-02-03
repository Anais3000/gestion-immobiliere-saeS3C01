package modele.dao.requetes.bienlouable;

import modele.BienLouable;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des biens louables de type garage
 * qui ne sont pas actuellement loués
 */
public class RequeteSelectGarageNonLoue extends Requete<BienLouable> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_BIEN_LOUABLE WHERE TYPE_BIEN = 'Garage' AND ID_BIEN NOT IN "
				+ "(SELECT ID_BIEN FROM SAE_LOUER WHERE SYSDATE BETWEEN DATE_DEBUT AND DATE_FIN)";
	}

}
