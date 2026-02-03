package modele.dao.requetes.louer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des contrats de location
 * concernant un bien d'ID donné et une date d'état des lieux d'entrée donnée
 */
public class RequeteSelectLouerByIdBienLouableEtDateEntreeEDL extends Requete<Louer> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_Louer WHERE Id_Bien = ? AND DATE_ETAT_DES_LIEUX_ENTREE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setDate(2, java.sql.Date.valueOf(id[1]));
	}
}
