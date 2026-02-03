package modele.dao.requetes.paiement;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Paiement;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de retourner l'ensemble des paiements de loyer concernant
 * un bien d'ID donné et effectués entre une date de début et une date de fin
 * donnée
 */
public class RequeteSelectPaiementLoyerIdBienBetweenMonths extends Requete<Paiement> {

	@Override
	public String requete() {
		return "SELECT * FROM SAE_PAIEMENT WHERE LIBELLE = 'loyer' AND ID_BIEN = ? AND MOIS_CONCERNE BETWEEN ? AND ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
		prSt.setString(1, id[0]);
		prSt.setDate(2, Date.valueOf(id[1]));
		prSt.setDate(3, Date.valueOf(id[2]));
	}

}
