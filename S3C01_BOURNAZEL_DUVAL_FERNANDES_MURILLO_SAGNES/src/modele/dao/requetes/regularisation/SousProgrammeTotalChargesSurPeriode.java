package modele.dao.requetes.regularisation;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;

import modele.dao.SousProgramme;

/**
 * Sous programme permettant de retourner le total (montant) des charges
 * effectivement payées concernant un bien d'ID donné et sur une période d'une
 * date de début donnée à une date de fin donnée
 */
public class SousProgrammeTotalChargesSurPeriode implements SousProgramme<Float> {

	@Override
	public String appelSousProgramme() {
		return "{? = call TOTAL_CHARGES_SUR_PERIODE(?,?,?)}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) throws SQLException {

		prSt.registerOutParameter(1, java.sql.Types.FLOAT);

		prSt.setString(2, parametres[0]);
		prSt.setDate(3, Date.valueOf(parametres[1]));
		prSt.setDate(4, Date.valueOf(parametres[2]));
	}

	@Override
	public void parametres(CallableStatement prSt, Float donnee) throws SQLException {
		throw new UnsupportedOperationException("Utilisez l'autre version de la méthode parametres");
	}

}
