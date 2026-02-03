package modele.dao.requetes.irl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.IRL;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de supprimer une valeur d'IRL enregistrée sur une année
 * spécifiée et un trimestre spécifié
 */
public class RequeteDeleteIRL extends Requete<IRL> {

	@Override
	public String requete() {
		return "DELETE FROM sae_IRL WHERE ANNEE = ? AND TRIMESTRE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, IRL donnee) throws SQLException {
		prSt.setInt(1, donnee.getAnnee());
		prSt.setInt(2, donnee.getTrimestre());
	}

}
