package modele.dao.requetes.necessiter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Necessiter;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des interventions nécessitées par
 * des biens louables concernant une intervention d'ID donné
 */
public class RequeteSelectNecessiterByIdIntervention extends Requete<Necessiter> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_NECESSITER WHERE ID_INTERVENTION = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
