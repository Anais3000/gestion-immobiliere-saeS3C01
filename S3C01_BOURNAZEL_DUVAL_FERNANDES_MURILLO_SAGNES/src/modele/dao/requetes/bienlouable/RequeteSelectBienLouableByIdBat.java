package modele.dao.requetes.bienlouable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.BienLouable;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des biens louables d'un batiment
 * d'ID spécifié
 */
public class RequeteSelectBienLouableByIdBat extends Requete<BienLouable> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_BIEN_LOUABLE WHERE ID_BATIMENT = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
