package modele.dao.requetes.diagnostic;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Diagnostic;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de supprimer un diagnistic d'un libellé, d'une date de
 * début et d'un identifiant de bien spécifié
 */
public class RequeteDeleteDiagnostic extends Requete<Diagnostic> {

	@Override
	public String requete() {
		return "DELETE FROM sae_DIAGNOSTICS WHERE LIBELLE = ? AND DATE_DEBUT = ? AND ID_BIEN = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Diagnostic donnee) throws SQLException {
		prSt.setString(1, donnee.getLibelle());
		prSt.setDate(2, java.sql.Date.valueOf(donnee.getDateDebut()));
		prSt.setString(3, donnee.getIdBien());
	}

}
