package modele.dao.requetes.resiliationbail;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;

import modele.dao.SousProgramme;

/**
 * Requete permettant de retourner le total des loyers impayés d'un bien d'ID
 * donné, pour un locataire d'ID donné et une date de début de bail donnée
 */
public class SousProgrammeTotalLoyersImpayes implements SousProgramme<Float> {

	@Override
	public String appelSousProgramme() {
		return "{? = call  TOTAL_LOYERS_IMPAYES(?,?,?)}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) throws SQLException {

		prSt.registerOutParameter(1, java.sql.Types.FLOAT);

		prSt.setString(2, parametres[0]);
		prSt.setString(3, parametres[1]);
		prSt.setDate(4, Date.valueOf(parametres[2]));
	}

	@Override
	public void parametres(CallableStatement prSt, Float donnee) throws SQLException {
		throw new UnsupportedOperationException("Utilisez l'autre version de la méthode parametres");
	}

}
