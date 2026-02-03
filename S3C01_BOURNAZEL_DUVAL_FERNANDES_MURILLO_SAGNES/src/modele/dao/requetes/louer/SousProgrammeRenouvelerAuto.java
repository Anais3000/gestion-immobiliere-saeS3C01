package modele.dao.requetes.louer;

import java.sql.CallableStatement;
import java.sql.SQLException;

import modele.Louer;
import modele.dao.SousProgramme;

/**
 * Sous programme permettant de renouveler automatiquement pour 3 ans tous les
 * contrats de location qui n'ont pas été marqués manuellement comme terminés
 * mais dont la date de fin est dépassée
 */
public class SousProgrammeRenouvelerAuto implements SousProgramme<Louer> {

	@Override
	public String appelSousProgramme() {
		return "{call RENOUVELER_BAUX_AUTOMATIQUE()}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) throws SQLException {
		throw new UnsupportedOperationException("Ce sous-programme ne prend pas de paramètres.");
	}

	@Override
	public void parametres(CallableStatement prSt, Louer donnee) throws SQLException {
		throw new UnsupportedOperationException("Ce sous-programme ne prend pas de paramètres.");
	}

}
