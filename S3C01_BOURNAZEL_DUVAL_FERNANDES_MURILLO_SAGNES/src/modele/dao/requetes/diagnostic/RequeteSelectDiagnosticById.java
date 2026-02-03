package modele.dao.requetes.diagnostic;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Diagnostic;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner un diagnostic d'id spécifié (libellé, date de
 * début et id du bien associé)
 */
public class RequeteSelectDiagnosticById extends Requete<Diagnostic> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_DIAGNOSTICS WHERE LIBELLE = ? AND DATE_DEBUT = ? AND ID_BIEN = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setDate(2, java.sql.Date.valueOf(id[1])); // car (normalement ?) on renseigne la date en LocalDate on fait
														// la conversion en sql.Date
		prSt.setString(3, id[2]);

	}

	@Override
	public void parametres(PreparedStatement prSt, Diagnostic donnee) throws SQLException {
		prSt.setString(1, donnee.getLibelle());
		prSt.setDate(2, java.sql.Date.valueOf(donnee.getDateDebut()));
		prSt.setString(3, donnee.getIdBien());
	}

}
