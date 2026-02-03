package modele.dao.requetes.historiqueloyer;

import java.sql.CallableStatement;
import java.sql.SQLException;

import modele.dao.SousProgramme;

/**
 * Sous programme permettant de retourner le total (montant) des loyers
 * effectivement payés pendant le mois actuel
 */
public class SousProgrammeTotalLoyerPayesMois implements SousProgramme<Float> {

	@Override
	public String appelSousProgramme() {
		return "{ ? = CALL TOTAL_LOYERS_PAYES_DANS_MOIS()}";
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
