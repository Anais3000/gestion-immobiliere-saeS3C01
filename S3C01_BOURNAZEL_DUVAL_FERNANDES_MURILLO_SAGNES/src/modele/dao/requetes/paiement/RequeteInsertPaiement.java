package modele.dao.requetes.paiement;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Paiement;
import modele.dao.requetes.Requete;

/**
 * Requete permettant d'insérer des informations d'un paiement
 */
public class RequeteInsertPaiement extends Requete<Paiement> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_PAIEMENT (montant, sens_paiement, libelle, date_paiement, id_bien, id_intervention, mois_concerne) VALUES (?,?,?,?,?,?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, Paiement donnee) throws SQLException {
		prSt.setDouble(1, donnee.getMontant());
		prSt.setString(2, donnee.getSensPaiement());
		prSt.setString(3, donnee.getLibelle());
		prSt.setDate(4, Date.valueOf(donnee.getDatePaiement()));

		if (donnee.getIdBien() == null) {
			prSt.setNull(5, java.sql.Types.VARCHAR);
		} else {
			prSt.setString(5, donnee.getIdBien());
		}

		if (donnee.getIdIntervention() == null) {
			prSt.setNull(6, java.sql.Types.VARCHAR);
		} else {
			prSt.setString(6, donnee.getIdIntervention());
		}

		if (donnee.getMoisConcerne() == null) {
			prSt.setNull(7, java.sql.Types.DATE);
		} else {
			prSt.setDate(7, Date.valueOf(donnee.getMoisConcerne()));
		}
	}

}
