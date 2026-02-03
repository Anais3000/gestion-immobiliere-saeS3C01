package modele.dao.requetes.intervention;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Intervention;
import modele.dao.requetes.Requete;

/**
 * Retourne l'ensemble des interventions d'un batiment d'ID spécifié
 */
public class RequeteSelectInterventionByIdBat extends Requete<Intervention> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_INTERVENTION WHERE ID_BATIMENT = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
