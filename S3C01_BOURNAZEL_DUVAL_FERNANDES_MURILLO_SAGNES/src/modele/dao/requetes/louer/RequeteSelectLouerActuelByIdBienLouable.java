package modele.dao.requetes.louer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des locations actuelles pour un
 * bien d'ID donné
 */
public class RequeteSelectLouerActuelByIdBienLouable extends Requete<Louer> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_Louer WHERE Id_Bien = ? AND DATE_FIN >= SYSDATE";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Louer donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBienLouable());
	}

}
