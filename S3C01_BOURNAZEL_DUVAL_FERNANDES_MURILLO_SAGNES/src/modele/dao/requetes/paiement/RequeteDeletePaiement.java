package modele.dao.requetes.paiement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Paiement;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de supprimer un paiement d'un ID donné
 */
public class RequeteDeletePaiement extends Requete<Paiement> {

	@Override
	public String requete() {
		return "DELETE FROM SAE_PAIEMENT WHERE ID_PAIEMENT = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Paiement donnee) throws SQLException {
		int i = Integer.parseInt(donnee.getIdPaiement());
		prSt.setInt(1, i);
	}
}
