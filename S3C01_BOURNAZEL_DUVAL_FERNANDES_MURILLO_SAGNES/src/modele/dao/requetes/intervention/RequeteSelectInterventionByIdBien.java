package modele.dao.requetes.intervention;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Intervention;
import modele.dao.requetes.Requete;

/**
 * Retourne l'ensemble des interventions d'un bien louable d'ID spécifié
 */
public class RequeteSelectInterventionByIdBien extends Requete<Intervention> {

	@Override
	public String requete() {
		return "SELECT I.* FROM sae_INTERVENTION I, sae_NECESSITER N WHERE I.ID_INTERVENTION = N.ID_INTERVENTION AND N.ID_BIEN = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
