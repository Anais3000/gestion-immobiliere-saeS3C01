package modele.dao.requetes.relevecompteur;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ReleveCompteur;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de sélectionner le dernier relevé de compteur concernant
 * un compteur d'ID donné
 */
public class RequeteSelectLastReleveCompteurByIdCompteur extends Requete<ReleveCompteur> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_RELEVE_COMPTEUR WHERE ID_COMPTEUR = ? ORDER BY DATE_RELEVE DESC FETCH FIRST 1 ROWS ONLY";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, ReleveCompteur donnee) throws SQLException {
		prSt.setString(1, donnee.getIdCompteur());
	}

}
