package modele.dao.requetes.relevecompteur;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ReleveCompteur;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des relevés de compteur d'un
 * batiment d'ID donné
 */
public class RequeteSelectReleveCompteurByIdBat extends Requete<ReleveCompteur> {

	@Override
	public String requete() {
		return "SELECT R.* FROM SAE_RELEVE_COMPTEUR R, SAE_COMPTEUR C WHERE R.ID_COMPTEUR = C.ID_COMPTEUR AND"
				+ " C.ID_BATIMENT = ? ORDER BY DATE_RELEVE DESC";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
