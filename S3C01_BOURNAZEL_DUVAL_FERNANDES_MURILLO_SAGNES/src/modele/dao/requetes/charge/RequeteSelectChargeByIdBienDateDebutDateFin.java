package modele.dao.requetes.charge;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Charge;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de renvoyer les charges correspondant à un bien louable
 * d'id spécifiée et couvrant une période entre une date de début spécifiée et
 * une date de fin spécifiée
 */
public class RequeteSelectChargeByIdBienDateDebutDateFin extends Requete<Charge> {

	@Override
	public String requete() {
		return "SELECT * " + "FROM SAE_CHARGE " + "WHERE ID_BIEN = ? " + "  AND ID_BATIMENT IS NULL"
				+ "  AND DATE_FACTURATION BETWEEN ? AND ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setDate(2, Date.valueOf(id[1]));
		prSt.setDate(3, Date.valueOf(id[2]));
	}
}
