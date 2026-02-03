package modele.dao.requetes.organisme;

import java.sql.CallableStatement;
import java.sql.SQLException;

import modele.Organisme;
import modele.dao.SousProgramme;

/**
 * Sous programme permettant de supprimer un organisme d'ID donné (numéro de
 * siret)
 */
public class SousProgrammeSupprimerOrganisme implements SousProgramme<Organisme> {

	@Override
	public String appelSousProgramme() {
		return "{call SUPPRIMER_ORGANISME(?)}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) {
		throw new UnsupportedOperationException("Utilisez l'autre version de la méthode parametres.");
	}

	@Override
	public void parametres(CallableStatement prSt, Organisme donnee) throws SQLException {
		prSt.setString(1, donnee.getNumSIRET());
	}

}
