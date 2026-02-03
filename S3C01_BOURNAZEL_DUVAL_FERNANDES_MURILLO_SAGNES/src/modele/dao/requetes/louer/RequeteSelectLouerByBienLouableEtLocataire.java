package modele.dao.requetes.louer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des contrats de location entre un
 * bien d'ID donné et un locataire d'ID donné
 */
public class RequeteSelectLouerByBienLouableEtLocataire extends Requete<Louer> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_Louer WHERE Id_Bien = ? AND Id_Locataire = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setString(2, id[1]);
	}

	@Override
	public void parametres(PreparedStatement prSt, Louer donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBienLouable());
		prSt.setString(2, donnee.getIdLocataire());
	}
}
