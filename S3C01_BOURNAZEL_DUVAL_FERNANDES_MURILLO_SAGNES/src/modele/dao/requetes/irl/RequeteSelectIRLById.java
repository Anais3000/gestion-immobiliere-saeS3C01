package modele.dao.requetes.irl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.IRL;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner un IRL d'une année et d'un trimestre spécifié
 */
public class RequeteSelectIRLById extends Requete<IRL> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_IRL WHERE ANNEE = ? AND TRIMESTRE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
	}

	@Override
	public void parametres(PreparedStatement prSt, IRL donnee) throws SQLException {
		prSt.setInt(1, donnee.getAnnee());
		prSt.setInt(2, donnee.getTrimestre());
	}

}
