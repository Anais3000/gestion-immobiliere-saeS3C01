package modele.dao.requetes.garant;

import java.sql.CallableStatement;
import java.sql.SQLException;

import modele.Garant;
import modele.dao.SousProgramme;

/**
 * Sous programme permettant de supprimer un garant d'ID spécifié
 */
public class SousProgrammeSupprimerGarant implements SousProgramme<Garant> {

	@Override
	public String appelSousProgramme() {
		return "{call SUPPRIMER_GARANT(?)}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) {
		throw new UnsupportedOperationException("Utilisez l'autre version de la méthode parametres.");
	}

	@Override
	public void parametres(CallableStatement prSt, Garant donnee) throws SQLException {
		prSt.setString(1, donnee.getIdGarant());
	}

}
