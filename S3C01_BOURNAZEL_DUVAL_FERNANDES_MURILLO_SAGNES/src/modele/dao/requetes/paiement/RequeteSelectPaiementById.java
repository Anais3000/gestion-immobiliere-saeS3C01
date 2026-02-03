package modele.dao.requetes.paiement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Paiement;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner les paiement d'ID donné, triés par date de
 * paiment décroissante et montant croissant
 */
public class RequeteSelectPaiementById extends Requete<Paiement> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_PAIEMENT WHERE ID_PAIEMENT = ? ORDER BY DATE_PAIEMENT DESC, MONTANT";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		int i = Integer.parseInt(id[0]);
		prSt.setInt(1, i);
	}

	@Override
	public void parametres(PreparedStatement prSt, Paiement donnee) throws SQLException {
		int i = Integer.parseInt(donnee.getIdPaiement());
		prSt.setInt(1, i);
	}

}
