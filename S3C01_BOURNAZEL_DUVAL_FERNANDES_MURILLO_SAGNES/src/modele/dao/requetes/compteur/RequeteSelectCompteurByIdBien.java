package modele.dao.requetes.compteur;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Compteur;
import modele.dao.requetes.Requete;

/**
 * Requete retournant l'ensemble des compteurs d'un bien d'ID spécifié
 */
public class RequeteSelectCompteurByIdBien extends Requete<Compteur> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_COMPTEUR WHERE ID_BIEN = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
