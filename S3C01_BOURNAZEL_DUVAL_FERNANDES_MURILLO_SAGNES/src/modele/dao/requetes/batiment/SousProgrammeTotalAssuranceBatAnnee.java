package modele.dao.requetes.batiment;

import java.sql.CallableStatement;
import java.sql.SQLException;

import modele.dao.SousProgramme;

/**
 * Sous programme permettant de retourner le total des montants d'assurances
 * d'un batiment d'id spécifié sur une année spécifiée
 */
public class SousProgrammeTotalAssuranceBatAnnee implements SousProgramme<Float> {

	@Override
	public String appelSousProgramme() {
		return "{? = call  Total_Assurance_Bat_Annee(?,?)}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) throws SQLException {
		prSt.registerOutParameter(1, java.sql.Types.FLOAT);
		prSt.setString(2, parametres[0]);
		prSt.setInt(3, Integer.parseInt(parametres[1]));
	}

	@Override
	public void parametres(CallableStatement prSt, Float donnee) throws SQLException {
		throw new UnsupportedOperationException("Utilisez l'autre version de la méthode parametres");
	}

}
