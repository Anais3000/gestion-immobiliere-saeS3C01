package modele.dao.requetes.batiment;

import java.sql.CallableStatement;
import java.sql.SQLException;

import modele.Batiment;
import modele.dao.SousProgramme;

/**
 * Sous programme permettant de supprimer un batiment d'un identifiant spécifié
 */
public class SousProgrammeSupprimerBatiment implements SousProgramme<Batiment> {

	@Override
	public String appelSousProgramme() {
		return "{call SUPPRIMER_BATIMENT(?)}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) {
		throw new UnsupportedOperationException("Utilisez l'autre version de la méthode parametres.");
	}

	@Override
	public void parametres(CallableStatement prSt, Batiment donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBat());
	}

}
