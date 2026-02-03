package modele.dao.requetes.paiement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Paiement;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des paiements concernant un
 * locataire d'ID donné, un bien d'ID donné et une date de paiement donnée
 */
public class RequeteSelectPaiementByLocEtBienEtDate extends Requete<Paiement> {

	@Override
	public String requete() {
		return "SELECT p.Id_Paiement FROM sae_Paiement p JOIN sae_Louer l ON p.Id_Bien = l.Id_Bien WHERE EXTRACT(MONTH FROM p.Date_paiement) = ? AND EXTRACT(YEAR FROM p.Date_paiement) = ? AND l.Id_Locataire = ? AND p.Id_Bien = ? AND p.Date_paiement BETWEEN ? AND AND NVL(?, SYSDATE)";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]); // mois
		prSt.setString(2, id[1]); // année
		prSt.setString(3, id[2]); // Id_Locataire
		prSt.setString(4, id[3]); // Id_Bien
	}
}
