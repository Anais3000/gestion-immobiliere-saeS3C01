package modele.dao.requetes.assurance;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assurance;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'assurance en BD possédant un numéro de
 * police d'assurance spécifique
 */
public class RequeteSelectAssuranceById extends Requete<Assurance> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_ASSURANCE WHERE NUMERO_POLICE_ASSURANCE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Assurance donnee) throws SQLException {
		prSt.setString(1, donnee.getNumPoliceAssurance());
	}

}
