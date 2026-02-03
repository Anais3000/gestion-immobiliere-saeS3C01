package modele.dao.requetes.relevecompteur;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ReleveCompteur;
import modele.dao.requetes.Requete;

/**
 * Requete permettant d'insérer un relevé de compteur en BD
 */
public class RequeteInsertReleveCompteur extends Requete<ReleveCompteur> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_RELEVE_COMPTEUR VALUES(?,?,?,?,?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, ReleveCompteur donnee) throws SQLException {
		prSt.setDate(1, Date.valueOf(donnee.getDateReleve()));
		prSt.setDouble(2, donnee.getIndexCompteur());
		prSt.setDouble(3, donnee.getPrixParUnite());
		prSt.setDouble(4, donnee.getPartieFixe());
		prSt.setString(5, donnee.getIdCompteur());
		prSt.setDouble(6, donnee.getAncienIndex());
	}

}
