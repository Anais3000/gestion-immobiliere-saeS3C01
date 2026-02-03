package modele.dao.requetes.compteur;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Compteur;
import modele.dao.requetes.Requete;

/**
 * Requete retournant un compteur d'ID spécifié
 */
public class RequeteSelectCompteurById extends Requete<Compteur> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_COMPTEUR WHERE ID_COMPTEUR = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Compteur donnee) throws SQLException {
		prSt.setString(1, donnee.getIdCompteur());
	}

}
