package modele.dao.requetes.diagnostic;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Diagnostic;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des diagnostics d'un bien d'ID
 * spécifié
 */
public class RequeteSelectDiagnosticByIdBien extends Requete<Diagnostic> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_DIAGNOSTICS WHERE ID_BIEN = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
	}
}
