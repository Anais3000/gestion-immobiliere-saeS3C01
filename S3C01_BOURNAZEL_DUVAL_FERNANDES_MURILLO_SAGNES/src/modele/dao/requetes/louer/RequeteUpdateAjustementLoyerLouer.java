package modele.dao.requetes.louer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de mettre à jour la valeur et la période d'un ajustement
 * de loyer concernant un bien d'id donné, un locataire d'id donné et une date
 * de début de location donnée
 */
public class RequeteUpdateAjustementLoyerLouer extends Requete<Louer> {

	@Override
	public String requete() {
		return "UPDATE SAE_LOUER SET " + "AJUSTEMENT_LOYER = ?, " + "MOIS_DEBUT_AJUSTEMENT = ?, "
				+ "MOIS_FIN_AJUSTEMENT = ? " + "WHERE ID_BIEN = ? AND ID_LOCATAIRE = ? AND TRUNC(DATE_DEBUT) = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Louer donnee) throws SQLException {
		prSt.setFloat(1, donnee.getAjustementLoyer());

		if (donnee.getMoisDebutAjustement() != null) {
			prSt.setDate(2, Date.valueOf(donnee.getMoisDebutAjustement()));
		} else {
			prSt.setNull(2, java.sql.Types.DATE);
		}

		if (donnee.getMoisFinAjustement() != null) {
			prSt.setDate(3, Date.valueOf(donnee.getMoisFinAjustement()));
		} else {
			prSt.setNull(3, java.sql.Types.DATE);
		}

		prSt.setString(4, donnee.getIdBienLouable());
		prSt.setString(5, donnee.getIdLocataire());
		prSt.setDate(6, Date.valueOf(donnee.getDateDebut()));
	}

}
