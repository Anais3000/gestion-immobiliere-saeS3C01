package modele.dao.requetes.compteur;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Compteur;
import modele.dao.requetes.Requete;

/**
 * Requete permettant d'insérer un compteur en BD
 */
public class RequeteInsertCompteur extends Requete<Compteur> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_COMPTEUR VALUES(?,?,?,?,?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, Compteur donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBien());
		prSt.setString(2, donnee.getIdBat());
		prSt.setString(3, donnee.getIdCompteur());
		prSt.setString(4, donnee.getTypeCompteur());
		prSt.setDate(5, Date.valueOf(donnee.getDateInstallation()));
		prSt.setFloat(6, donnee.getIndexDepart());
	}

}
