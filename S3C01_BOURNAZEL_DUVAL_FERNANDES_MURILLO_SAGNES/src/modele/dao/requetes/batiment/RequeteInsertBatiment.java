package modele.dao.requetes.batiment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Batiment;
import modele.dao.requetes.Requete;

/**
 * Requête permettant d'insérer un batiment en BD
 */
public class RequeteInsertBatiment extends Requete<Batiment> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_BATIMENT VALUES(?,?,?,?,?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, Batiment donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBat());
		prSt.setDate(2, Date.valueOf(donnee.getDateConstruction()));
		prSt.setString(3, donnee.getAdresse());
		prSt.setString(4, donnee.getCodePostal());
		prSt.setString(5, donnee.getVille());
		prSt.setString(6, donnee.getNumFiscal());
	}

}
