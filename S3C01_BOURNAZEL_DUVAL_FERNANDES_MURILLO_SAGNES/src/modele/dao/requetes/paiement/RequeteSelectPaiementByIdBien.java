package modele.dao.requetes.paiement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Paiement;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des paiements concernant un bien
 * louable d'ID donné, triés par date de paiement décroissante et montant
 * croissant
 */
public class RequeteSelectPaiementByIdBien extends Requete<Paiement> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_PAIEMENT WHERE ID_BIEN = ? ORDER BY DATE_PAIEMENT DESC, MONTANT";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
