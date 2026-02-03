package modele.dao.requetes.intervention;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Intervention;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de supprimer une intervention d'un ID donné
 */
public class RequeteDeleteIntervention extends Requete<Intervention> {

	@Override
	public String requete() {
		return "DELETE FROM sae_INTERVENTION WHERE ID_INTERVENTION = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Intervention donnee) throws SQLException {
		prSt.setString(1, donnee.getIdIntervention());
	}

}
