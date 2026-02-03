package modele.dao.requetes.historique;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import modele.Historique;
import modele.dao.requetes.Requete;

/**
 * Requete permettant d'insérer en BD un historique (action effectuée par
 * l'utilisateur)
 */
public class RequeteInsertHistorique extends Requete<Historique> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_HISTORIQUE (date_action, action, details) VALUES (?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, Historique donnee) throws SQLException {
		prSt.setTimestamp(1, Timestamp.valueOf(donnee.getDate()));
		prSt.setString(2, donnee.getAction());
		prSt.setString(3, donnee.getDetails());
	}
}
