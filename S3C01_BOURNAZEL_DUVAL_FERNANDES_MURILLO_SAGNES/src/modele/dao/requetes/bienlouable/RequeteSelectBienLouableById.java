package modele.dao.requetes.bienlouable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.BienLouable;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner le bien louable en BD d'un ID spécifié
 */
public class RequeteSelectBienLouableById extends Requete<BienLouable> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_BIEN_LOUABLE WHERE ID_BIEN = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, BienLouable donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBien());
	}

}
