package modele.dao.requetes.relevecompteur;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.ReleveCompteur;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de supprimer un relevé compteur d'ID donné (date du relevé
 * et ID du compteur concerné)
 */
public class RequeteDeleteReleveCompteur extends Requete<ReleveCompteur> {

	@Override
	public String requete() {
		return "DELETE FROM SAE_RELEVE_COMPTEUR WHERE DATE_RELEVE = ? AND ID_COMPTEUR = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, ReleveCompteur donnee) throws SQLException {
		prSt.setDate(1, Date.valueOf(donnee.getDateReleve()));
		prSt.setString(2, donnee.getIdCompteur());
	}

}
