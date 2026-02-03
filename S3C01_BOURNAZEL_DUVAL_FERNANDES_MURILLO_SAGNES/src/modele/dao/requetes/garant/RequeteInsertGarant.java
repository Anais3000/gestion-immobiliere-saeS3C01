package modele.dao.requetes.garant;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Garant;
import modele.dao.requetes.Requete;

/**
 * Requete permettant d'insérer un garant en BD
 */
public class RequeteInsertGarant extends Requete<Garant> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_GARANT VALUES(?,?,?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, Garant donnee) throws SQLException {
		prSt.setString(1, donnee.getIdGarant());
		prSt.setString(2, donnee.getAdresse());
		prSt.setString(3, donnee.getMail());
		prSt.setString(4, donnee.getTel());
	}

}
