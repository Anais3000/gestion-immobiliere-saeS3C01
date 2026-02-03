package modele.dao.requetes.intervention;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Intervention;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de mettre a jour les informations de facturation d'une
 * intervention d'ID spécifié
 */
public class RequeteUpdateFactureIntervention extends Requete<Intervention> {

	@Override
	public String requete() {
		return "UPDATE sae_INTERVENTION SET " + "NUMERO_FACTURE = ?," + "MONTANT_FACTURE = ?," + "DATE_FACTURE = ? "
				+ "WHERE ID_INTERVENTION = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Intervention donnee) throws SQLException {
		prSt.setString(1, donnee.getNumFacture());
		prSt.setFloat(2, donnee.getMontantFacture());
		prSt.setDate(3, Date.valueOf(donnee.getDateFacture()));
		prSt.setString(4, donnee.getIdIntervention());
	}

}
