package modele.dao.requetes.assurance;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assurance;
import modele.dao.requetes.Requete;

/**
 * Requête permettant de supprimer une assurance de par son numéro de police
 * d'assurance
 */
public class RequeteDeleteAssurance extends Requete<Assurance> {

	@Override
	public String requete() {
		return "DELETE FROM SAE_ASSURANCE WHERE NUMERO_POLICE_ASSURANCE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Assurance donnee) throws SQLException {
		prSt.setString(1, donnee.getNumPoliceAssurance());
	}

}
