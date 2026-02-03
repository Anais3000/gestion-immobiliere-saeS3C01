package modele.dao.requetes.diagnostic;

import modele.Diagnostic;
import modele.dao.requetes.Requete;

/**
 * Requete retournant l'ensemble des diagnostics stockés en BD, triés par date
 * de début de couverture décroissante
 */
public class RequeteSelectDiagnostic extends Requete<Diagnostic> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_DIAGNOSTICS ORDER BY DATE_DEBUT DESC";
	}

}
