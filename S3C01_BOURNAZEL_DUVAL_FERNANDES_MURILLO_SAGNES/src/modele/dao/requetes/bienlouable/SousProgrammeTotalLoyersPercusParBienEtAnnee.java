package modele.dao.requetes.bienlouable;

import java.sql.CallableStatement;
import java.sql.SQLException;

import modele.dao.SousProgramme;

/**
 * Sous programme permettant de retourner le total des loyers percus par un bien
 * louable d'id spécifié sur une année spécifiée
 */
public class SousProgrammeTotalLoyersPercusParBienEtAnnee implements SousProgramme<Float> {

	@Override
	public String appelSousProgramme() {
		// bien (String) puis année (int)
		return "{? = call Total_loyers_percus_bien_annee(?,?)}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) throws SQLException {
		prSt.registerOutParameter(1, java.sql.Types.FLOAT);
		prSt.setString(2, parametres[0]);
		int annee = Integer.parseInt(parametres[1]);
		prSt.setInt(3, annee);
	}

	@Override
	public void parametres(CallableStatement prSt, Float donnee) throws SQLException {
		throw new UnsupportedOperationException("Veuillez utiliser l'autre méthode parametres");
	}

}
