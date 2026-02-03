package modele.dao.requetes.assurance;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assurance;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner toutes les assurances en BD liés à un
 * batiment d'ID spécifié
 */
public class RequeteSelectAssuranceByIdBat extends Requete<Assurance> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_ASSURANCE WHERE ID_BATIMENT = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
