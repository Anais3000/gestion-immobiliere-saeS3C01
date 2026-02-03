package modele.dao.requetes.necessiter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Necessiter;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des interventions nécessitées par
 * les biens d'ID spécifique et concernant une intervention d'ID spécifiée
 */
public class RequeteSelectNecessiterById extends Requete<Necessiter> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_NECESSITER WHERE ID_BIEN = ? AND ID_INTERVENTION = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Necessiter donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBien());
		prSt.setString(2, donnee.getIdIntervention());
	}

}
