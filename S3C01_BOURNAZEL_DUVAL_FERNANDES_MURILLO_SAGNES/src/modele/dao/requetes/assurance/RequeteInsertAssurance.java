package modele.dao.requetes.assurance;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Assurance;
import modele.dao.requetes.Requete;

/**
 * Requête permettant d'insérer une assurance en BD
 */
public class RequeteInsertAssurance extends Requete<Assurance> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_ASSURANCE VALUES(?,?,?,?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, Assurance donnee) throws SQLException {
		prSt.setString(1, donnee.getNumPoliceAssurance());
		prSt.setString(2, donnee.getTypeContrat());
		prSt.setInt(3, donnee.getAnneeCouverture());
		prSt.setDouble(4, donnee.getMontantPaye());
		prSt.setString(5, donnee.getBat());
	}

}
