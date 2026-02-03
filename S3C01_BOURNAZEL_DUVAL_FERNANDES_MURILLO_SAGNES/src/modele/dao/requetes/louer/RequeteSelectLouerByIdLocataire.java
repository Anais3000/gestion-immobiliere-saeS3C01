package modele.dao.requetes.louer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Retourne l'ensemble des contrats de location associés à un locataire d'id
 * donné, triés par date de début décroissante
 */
public class RequeteSelectLouerByIdLocataire extends Requete<Louer> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_Louer WHERE Id_Locataire = ? Order By Date_Debut Desc";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
