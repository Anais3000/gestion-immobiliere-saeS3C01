package modele.dao.requetes.intervention;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Intervention;
import modele.dao.requetes.Requete;

/**
 * Retourne l'ensemble des interventions d'un organisme d'ID spécifié
 */
public class RequeteSelectInterventionByIdOrganisme extends Requete<Intervention> {

	@Override
	public String requete() {
		return "SELECT I.* FROM sae_INTERVENTION I WHERE I.NUM_SIRET = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
