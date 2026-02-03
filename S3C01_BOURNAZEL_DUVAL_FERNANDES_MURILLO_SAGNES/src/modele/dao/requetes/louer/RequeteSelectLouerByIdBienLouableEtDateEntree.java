package modele.dao.requetes.louer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des contrats de location
 * concernant un bien d'ID donné et une date de début donnée
 */
public class RequeteSelectLouerByIdBienLouableEtDateEntree extends Requete<Louer> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_Louer WHERE Id_Bien = ? AND DATE_DEBUT = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setDate(2, java.sql.Date.valueOf(id[1]));
	}
}
