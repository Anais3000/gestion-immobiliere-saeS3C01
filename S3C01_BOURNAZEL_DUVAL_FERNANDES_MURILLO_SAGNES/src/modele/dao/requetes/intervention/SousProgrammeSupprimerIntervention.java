package modele.dao.requetes.intervention;

import java.sql.CallableStatement;
import java.sql.SQLException;

import modele.Intervention;
import modele.dao.SousProgramme;

/**
 * Sous programme permettant de supprimer une intervention d'un ID spécifié
 */
public class SousProgrammeSupprimerIntervention implements SousProgramme<Intervention> {

	@Override
	public String appelSousProgramme() {
		return "{call SUPPRIMER_INTERVENTION(?)}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) {
		throw new UnsupportedOperationException(
				"Utilisez l'autre version de la méthode parametres(Creneau donnee, String nomE, String prenomE, String idEns");
	}

	@Override
	public void parametres(CallableStatement prSt, Intervention donnee) throws SQLException {
		String idInterv = donnee.getIdIntervention();
		prSt.setString(1, idInterv);
	}

}
