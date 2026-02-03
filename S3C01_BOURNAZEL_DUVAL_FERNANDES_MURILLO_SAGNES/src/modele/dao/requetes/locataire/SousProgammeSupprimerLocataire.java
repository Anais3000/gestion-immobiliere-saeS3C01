package modele.dao.requetes.locataire;

import java.sql.CallableStatement;
import java.sql.SQLException;

import modele.Locataire;
import modele.dao.SousProgramme;

/**
 * Sous programme permettant de supprimer un locataire d'ID spécifié
 */
public class SousProgammeSupprimerLocataire implements SousProgramme<Locataire> {

	@Override
	public String appelSousProgramme() {
		return "{call SUPPRIMER_LOCATAIRE(?)}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) {
		throw new UnsupportedOperationException("Utilisez l'autre version de la méthode parametres.");
	}

	@Override
	public void parametres(CallableStatement prSt, Locataire donnee) throws SQLException {
		prSt.setString(1, donnee.getIdLocataire());

	}

}
