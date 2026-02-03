package modele.dao.requetes.locataire;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Locataire;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des locataires ayant loué un bien
 * louable d'ID spécifié
 */
public class RequeteSelectLocataireByIdBienLouable extends Requete<Locataire> {

	@Override
	public String requete() {
		return "SELECT l.* FROM sae_locataire l JOIN sae_louer lo ON lo.id_locataire = l.id_locataire WHERE lo.id_bien = ? ORDER BY lo.date_debut DESC";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}

}
