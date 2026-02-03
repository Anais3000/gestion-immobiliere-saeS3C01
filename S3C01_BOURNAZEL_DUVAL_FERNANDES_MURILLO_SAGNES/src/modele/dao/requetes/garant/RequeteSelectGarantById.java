package modele.dao.requetes.garant;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Garant;
import modele.dao.requetes.Requete;

/**
 * Requete retournant le garant d'un ID spécifié
 */
public class RequeteSelectGarantById extends Requete<Garant> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_GARANT WHERE ID_GARANT = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Garant donnee) throws SQLException {
		prSt.setString(1, donnee.getIdGarant());
	}

}
