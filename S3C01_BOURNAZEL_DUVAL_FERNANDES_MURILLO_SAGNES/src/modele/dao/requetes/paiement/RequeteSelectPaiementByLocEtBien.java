package modele.dao.requetes.paiement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Paiement;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des paiements concernant un
 * locataire d'ID donné et un bien louable d'ID donné
 */
public class RequeteSelectPaiementByLocEtBien extends Requete<Paiement> {

	@Override
	public String requete() {
		return "SELECT * FROM sae_Paiement p, sae_Louer l WHERE p.Id_Bien = l.Id_Bien AND l.Id_Locataire = ? AND p.Id_Bien = ? AND p.MOIS_CONCERNE BETWEEN TRUNC(SYSDATE, 'MM') AND LAST_DAY(SYSDATE)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]); // Id_Locataire
		prSt.setString(2, id[1]); // Id_Bien
	}
}
