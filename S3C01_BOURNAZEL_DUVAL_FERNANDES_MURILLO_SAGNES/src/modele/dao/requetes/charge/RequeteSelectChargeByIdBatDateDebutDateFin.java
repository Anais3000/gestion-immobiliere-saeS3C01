package modele.dao.requetes.charge;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Charge;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de renvoyer les charges correspondant à un bat d'id
 * spécifiée et couvrant une période entre une date de début spécifiée et une
 * date de fin spécifiée
 */
public class RequeteSelectChargeByIdBatDateDebutDateFin extends Requete<Charge> {
	@Override
	public String requete() {
		return "SELECT * " + "FROM SAE_CHARGE " + "WHERE ID_BATIMENT = ? " + "  AND (DATE_DEBUT_PERIODE IS NOT NULL "
				+ "       OR DATE_FIN_PERIODE IS NOT NULL) " + "  AND ID_BIEN IS NULL "
				+ "  AND DATE_FACTURATION BETWEEN ? AND ? " + "  AND DATE_FIN_PERIODE >= ? "
				+ "  AND DATE_DEBUT_PERIODE <= ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setDate(2, Date.valueOf(id[1]));
		prSt.setDate(3, Date.valueOf(id[2]));
		prSt.setDate(4, Date.valueOf(id[1]));
		prSt.setDate(5, Date.valueOf(id[2]));
	}
}