package modele.dao.requetes.historiqueloyer;

import java.sql.CallableStatement;
import java.sql.SQLException;

import modele.dao.SousProgramme;

/**
 * Sous programme permettant de retourner le total des loyers impayés sur le
 * mois actuel
 */
public class SousProgrammeTotalLoyerImpayesMoisActuel implements SousProgramme<Float> {

	@Override
	public String appelSousProgramme() {
		return "{ ? = CALL TOTAL_LOYER_IMPAYES_MOIS_ACTUEL()}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) throws SQLException {
		prSt.registerOutParameter(1, java.sql.Types.FLOAT);
	}

	@Override
	public void parametres(CallableStatement prSt, Float donnee) throws SQLException {
		throw new UnsupportedOperationException("Pas de paramtètre dans cette méthode");

	}

}
