package modele.dao.requetes.bienlouable;

import java.sql.CallableStatement;
import java.sql.SQLException;

import modele.BienLouable;
import modele.dao.SousProgramme;

/**
 * Sous programme permettant de supprimer un bien louable d'un ID spécifié
 */
public class SousProgammeSupprimerBienLouable implements SousProgramme<BienLouable> {

	@Override
	public String appelSousProgramme() {
		return "{call supprimer_bien_louable(?)}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) {
		throw new UnsupportedOperationException("Utilisez l'autre version de la méthode parametres.");
	}

	@Override
	public void parametres(CallableStatement prSt, BienLouable donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBien());

	}

}
