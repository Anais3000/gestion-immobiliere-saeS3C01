package modele.dao.requetes.charge;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Charge;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de renvoyer la charge d'un id spécifié
 */
public class RequeteSelectChargeById extends Requete<Charge> {

	@Override
	public String requete() {
		return "SELECT * FROM CHARGE WHERE ID_CHARGE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Charge donnee) throws SQLException {
		prSt.setString(1, donnee.getIdCharge());
	}

}
