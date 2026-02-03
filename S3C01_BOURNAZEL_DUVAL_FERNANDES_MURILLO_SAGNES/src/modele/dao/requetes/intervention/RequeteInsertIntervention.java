package modele.dao.requetes.intervention;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Intervention;
import modele.dao.requetes.Requete;

/**
 * Requete permettant d'insérer une intervention en BD
 */
public class RequeteInsertIntervention extends Requete<Intervention> {

	@Override
	public String requete() {
		return "INSERT INTO sae_INTERVENTION VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, Intervention donnee) throws SQLException {
		prSt.setString(1, donnee.getIdIntervention());
		prSt.setString(2, donnee.getIntitule());
		if (donnee.getMontantFacture() != null) {
			prSt.setString(3, donnee.getNumFacture());
		} else {
			prSt.setNull(3, java.sql.Types.NULL);

		}
		if (donnee.getMontantFacture() != null) {
			prSt.setDouble(4, donnee.getMontantFacture());
		} else {
			prSt.setNull(4, java.sql.Types.NULL);
		}

		if (donnee.getAcompteFacture() != null) {
			prSt.setDouble(5, donnee.getAcompteFacture());
		} else {
			prSt.setNull(5, java.sql.Types.NULL);
		}

		if (donnee.getDateAcompte() != null) {
			prSt.setDate(6, Date.valueOf(donnee.getDateAcompte()));
		} else {
			prSt.setNull(6, java.sql.Types.NULL);
		}

		if (donnee.getDateFacture() != null) {
			prSt.setDate(7, Date.valueOf(donnee.getDateFacture()));
		} else {
			prSt.setNull(7, java.sql.Types.NULL);
		}

		prSt.setString(8, donnee.getNumDevis());

		if (donnee.getMontantDevis() != null) {
			prSt.setDouble(9, donnee.getMontantDevis());
		} else {
			prSt.setNull(9, java.sql.Types.NULL);
		}

		if (donnee.getDateIntervention() != null) {
			prSt.setDate(10, Date.valueOf(donnee.getDateIntervention()));
		} else {
			prSt.setNull(10, java.sql.Types.NULL);
		}

		if (donnee.getMontantNonDeductible() != null) {
			prSt.setDouble(11, donnee.getMontantNonDeductible());
		} else {
			prSt.setNull(11, java.sql.Types.NULL);
		}

		if (donnee.getReduction() != null) {
			prSt.setDouble(12, donnee.getReduction());
		} else {
			prSt.setNull(12, java.sql.Types.NULL);
		}

		prSt.setString(13, donnee.getIdBatiment());
		prSt.setString(14, donnee.getIdOrganisme());
		prSt.setInt(15, donnee.getEntretienPc());
		prSt.setInt(16, donnee.getOrdures());
	}

}
