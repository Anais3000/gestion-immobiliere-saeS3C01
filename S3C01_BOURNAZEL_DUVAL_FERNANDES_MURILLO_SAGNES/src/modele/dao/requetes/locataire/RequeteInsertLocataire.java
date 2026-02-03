package modele.dao.requetes.locataire;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Locataire;
import modele.dao.requetes.Requete;

/**
 * Requete permettant d'insérer un locataire en BD
 */
public class RequeteInsertLocataire extends Requete<Locataire> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_LOCATAIRE VALUES(?,?,?,?,?,?,?,?,?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, Locataire donnee) throws SQLException {
		prSt.setString(1, donnee.getIdLocataire());
		prSt.setString(2, donnee.getNom());
		prSt.setString(3, donnee.getPrenom());
		prSt.setString(4, donnee.getNumTelephone());
		prSt.setString(5, donnee.getMail());
		prSt.setDate(6, Date.valueOf(donnee.getDateNaissance()));
		prSt.setString(7, donnee.getVilleNaissance());
		prSt.setString(8, donnee.getAdresse());
		prSt.setString(9, donnee.getCodePostal());
		prSt.setString(10, donnee.getVille());
	}

}
