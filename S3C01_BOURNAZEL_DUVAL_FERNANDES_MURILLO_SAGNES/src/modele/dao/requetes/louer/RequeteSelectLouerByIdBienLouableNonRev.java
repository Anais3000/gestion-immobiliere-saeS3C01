package modele.dao.requetes.louer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des contrats de location non
 * révolus d'un bien louable d'ID donné, triés par date de début décroissante
 */
public class RequeteSelectLouerByIdBienLouableNonRev extends Requete<Louer> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_Louer WHERE Id_Bien = ? AND revolue = 0 Order By Date_Debut Desc";
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
