package modele.dao.requetes.intervention;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Intervention;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'intervention d'un ID spécifié
 */
public class RequeteSelectInterventionById extends Requete<Intervention> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_INTERVENTION WHERE ID_INTERVENTION = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Intervention donnee) throws SQLException {
		prSt.setString(1, donnee.getIdIntervention());
	}

}
