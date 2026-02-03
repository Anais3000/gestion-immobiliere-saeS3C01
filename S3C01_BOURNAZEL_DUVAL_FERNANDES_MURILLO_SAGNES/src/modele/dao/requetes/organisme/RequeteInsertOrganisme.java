package modele.dao.requetes.organisme;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Organisme;
import modele.dao.requetes.Requete;

/**
 * Requete permettant d'insérer un nouvel Organisme en BD
 */
public class RequeteInsertOrganisme extends Requete<Organisme> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_ORGANISME VALUES(?,?,?,?,?,?,?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, Organisme donnee) throws SQLException {
		prSt.setString(1, donnee.getNumSIRET());
		prSt.setString(2, donnee.getNom());
		prSt.setString(3, donnee.getAdresse());
		prSt.setString(4, donnee.getCodePostal());
		prSt.setString(5, donnee.getVille());
		prSt.setString(6, donnee.getMail());
		prSt.setString(7, donnee.getNumTel());
		prSt.setString(8, donnee.getSpecialite());
	}

}
