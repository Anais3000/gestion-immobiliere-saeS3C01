package modele.dao.requetes.batiment;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Batiment;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner le batiment en BD d'un certain identifiant
 */
public class RequeteSelectBatimentById extends Requete<Batiment> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_BATIMENT WHERE ID_BATIMENT = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Batiment donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBat());
	}

}
