package modele.dao.requetes.necessiter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Necessiter;
import modele.dao.requetes.Requete;

/**
 * Requete permettant d'insérer le besoin d'une intervention d'ID donné pour un
 * bien d'ID donné
 */
public class RequeteInsertNecessiter extends Requete<Necessiter> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_NECESSITER VALUES(?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, Necessiter donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBien());
		prSt.setString(2, donnee.getIdIntervention());
	}

}
