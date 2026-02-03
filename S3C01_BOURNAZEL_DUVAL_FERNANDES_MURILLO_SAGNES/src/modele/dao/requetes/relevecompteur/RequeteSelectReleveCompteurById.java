package modele.dao.requetes.relevecompteur;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ReleveCompteur;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner le relevé de compteur d'ID donné (date du
 * relevé et ID du compteur concerné)
 */
public class RequeteSelectReleveCompteurById extends Requete<ReleveCompteur> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_RELEVE_COMPTEUR WHERE DATE_RELEVE = ? AND ID_COMPTEUR = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setDate(1, Date.valueOf(id[0]));
		prSt.setString(2, id[1]);
	}

	@Override
	public void parametres(PreparedStatement prSt, ReleveCompteur donnee) throws SQLException {
		prSt.setDate(1, Date.valueOf(donnee.getDateReleve()));
		prSt.setString(2, donnee.getIdCompteur());
	}

}
