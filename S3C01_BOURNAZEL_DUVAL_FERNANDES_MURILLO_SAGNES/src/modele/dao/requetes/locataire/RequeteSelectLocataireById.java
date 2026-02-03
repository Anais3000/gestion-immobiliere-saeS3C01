package modele.dao.requetes.locataire;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Locataire;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner le locataire d'un ID spécifié
 */
public class RequeteSelectLocataireById extends Requete<Locataire> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_LOCATAIRE WHERE ID_LOCATAIRE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Locataire donnee) throws SQLException {
		prSt.setString(1, donnee.getIdLocataire());
	}

}
