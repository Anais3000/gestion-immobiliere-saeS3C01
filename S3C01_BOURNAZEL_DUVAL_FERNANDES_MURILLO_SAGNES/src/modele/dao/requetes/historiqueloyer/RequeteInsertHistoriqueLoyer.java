package modele.dao.requetes.historiqueloyer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.HistoriqueLoyer;
import modele.dao.requetes.Requete;

/**
 * Requete permettant d'insérer un historique de loyer en BD
 */
public class RequeteInsertHistoriqueLoyer extends Requete<HistoriqueLoyer> {

	// SAE_HISTORIQUE_LOYERS : ID_HIST, LOYER, PROVISION, ID_BIEN, MOIS_CONCERNE
	@Override
	public String requete() {
		return "INSERT INTO SAE_HISTORIQUE_LOYERS VALUES (NULL, ?, ?, ?, ?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, HistoriqueLoyer donnee) throws SQLException {
		prSt.setFloat(1, donnee.getLoyer());
		prSt.setFloat(2, donnee.getProvision());
		prSt.setString(3, donnee.getIdBien());
		prSt.setDate(4, Date.valueOf(donnee.getMoisConcerne()));
	}
}