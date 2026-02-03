package modele.dao.requetes.louer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Retourne l'ensemble des contrats de location concernant les garages d'un bien
 * d'ID renseigné pour une date de début et une date de fin donnée
 */
public class RequeteSelectLouerGaragesAssociesLogement extends Requete<Louer> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_LOUER " + " WHERE DATE_DEBUT = ?" + " AND DATE_FIN = ?"
				+ " AND ID_LOGEMENT_ASSOCIE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setDate(1, Date.valueOf(id[0]));
		prSt.setDate(2, Date.valueOf(id[1]));
		prSt.setString(3, id[2]);
	}

}
