package modele.dao.requetes.compteur;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Compteur;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de supprimer un compteur d'ID spécifié
 */
public class RequeteDeleteCompteur extends Requete<Compteur> {

	@Override
	public String requete() {
		return "DELETE FROM SAE_COMPTEUR WHERE ID_COMPTEUR = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Compteur donnee) throws SQLException {
		prSt.setString(1, donnee.getIdCompteur());
	}

}
