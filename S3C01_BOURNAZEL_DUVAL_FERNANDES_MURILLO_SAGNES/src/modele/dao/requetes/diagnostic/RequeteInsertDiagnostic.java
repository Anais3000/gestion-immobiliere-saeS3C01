package modele.dao.requetes.diagnostic;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Diagnostic;
import modele.dao.requetes.Requete;

/**
 * Requete permettant d'insérer un diagnostic en BD
 */
public class RequeteInsertDiagnostic extends Requete<Diagnostic> {

	@Override
	public String requete() {
		return "INSERT INTO sae_DIAGNOSTICS VALUES(?,?,?,?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, Diagnostic donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBien());
		prSt.setString(2, donnee.getLibelle());
		prSt.setDate(3, Date.valueOf(donnee.getDateDebut()));
		prSt.setDate(4, Date.valueOf(donnee.getDateFin()));
		prSt.setString(5, donnee.getDetails());
	}

}
