package modele.dao.requetes.irl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.IRL;
import modele.dao.requetes.Requete;

/**
 * Requete permetant d'insérer une valeur d'IRL en bd, d'une année, d'un
 * trimestre et d'une valeur spécifiée
 */
public class RequeteInsertIRL extends Requete<IRL> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_IRL VALUES(?,?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, IRL donnee) throws SQLException {
		prSt.setInt(1, donnee.getAnnee());
		prSt.setInt(2, donnee.getTrimestre());
		prSt.setDouble(3, donnee.getValeur());
	}

}
