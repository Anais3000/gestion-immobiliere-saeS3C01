package modele.dao.requetes.historiqueloyer;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;

import modele.HistoriqueLoyer;
import modele.dao.SousProgramme;

/**
 * Sous programme permettant de retourner le dernier loyer et la derniere
 * provision pour charges d'un bien d'un id spécifié en fonction de la date
 * renseignée
 */
public class SousProgrammeDerniersLoyerProvision implements SousProgramme<HistoriqueLoyer> {

	@Override
	public String appelSousProgramme() {
		return "{call DERNIERS_LOYER_PROVISION(?,?,?,?)}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) throws SQLException {
		prSt.setString(1, parametres[0]);
		prSt.setDate(2, Date.valueOf(parametres[1]));
		prSt.registerOutParameter(3, java.sql.Types.FLOAT);
		prSt.registerOutParameter(4, java.sql.Types.FLOAT);
	}

	@Override
	public void parametres(CallableStatement prSt, HistoriqueLoyer donnee) throws SQLException {
		throw new UnsupportedOperationException("Utilisez l'autre version de la méthode parametres");

	}

}
