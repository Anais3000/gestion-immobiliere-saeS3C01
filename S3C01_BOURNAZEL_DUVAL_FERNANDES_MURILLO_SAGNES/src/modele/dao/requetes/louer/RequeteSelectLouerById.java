package modele.dao.requetes.louer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des contrats de location d'un ID
 * spécifié (date de début, id du bien associé et id du locataire associé)
 */
public class RequeteSelectLouerById extends Requete<Louer> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_LOUER WHERE DATE_DEBUT = ? AND ID_BIEN = ? AND ID_LOCATAIRE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setDate(1, Date.valueOf(id[0]));
		prSt.setString(2, id[1]);
		prSt.setString(3, id[2]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Louer donnee) throws SQLException {
		prSt.setDate(1, Date.valueOf(donnee.getDateDebut()));
		prSt.setString(2, donnee.getIdBienLouable());
		prSt.setString(3, donnee.getIdLocataire());
	}

}
