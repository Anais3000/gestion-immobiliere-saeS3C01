package modele.dao.requetes.paiement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Paiement;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des paiements d'un bien d'ID donné
 * et d'un libellé donné
 */
public class RequeteSelectPaiementByIdBienEtLibelle extends Requete<Paiement> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_PAIEMENT WHERE ID_BIEN = ? AND LIBELLE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);

	}

}
