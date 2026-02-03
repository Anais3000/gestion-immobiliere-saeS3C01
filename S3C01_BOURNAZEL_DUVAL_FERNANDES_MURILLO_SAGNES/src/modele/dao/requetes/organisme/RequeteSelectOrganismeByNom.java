package modele.dao.requetes.organisme;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Organisme;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'organisme d'un nom donné
 */
public class RequeteSelectOrganismeByNom extends Requete<Organisme> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_ORGANISME WHERE UPPER(NOM) = UPPER(?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
