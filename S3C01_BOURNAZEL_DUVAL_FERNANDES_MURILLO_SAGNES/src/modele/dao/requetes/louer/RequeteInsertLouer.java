package modele.dao.requetes.louer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Requete permettant d'insérer un contrat de location en BD
 */
public class RequeteInsertLouer extends Requete<Louer> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_LOUER VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, Louer donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBienLouable());
		prSt.setString(2, donnee.getIdLocataire());
		prSt.setDate(3, Date.valueOf(donnee.getDateDebut()));
		prSt.setDate(4, Date.valueOf(donnee.getDateFin()));
		prSt.setDouble(5, donnee.getDepotDeGarantie());
		if (donnee.getDateEtatDesLieuxEntree() != null) {
			prSt.setDate(6, Date.valueOf(donnee.getDateEtatDesLieuxEntree()));
		} else {
			prSt.setNull(6, java.sql.Types.NULL);
		}
		if (donnee.getDateEtatDesLieuxSortie() != null) {
			prSt.setDate(7, Date.valueOf(donnee.getDateEtatDesLieuxSortie()));
		} else {
			prSt.setNull(7, java.sql.Types.NULL);
		}
		prSt.setString(8, donnee.getDetailsEtatDesLieuxEntree());
		prSt.setString(9, donnee.getDetailsEtatDesLieuxSortie());
		prSt.setString(10, donnee.getIdGarant());
		if (donnee.getDateDerniereRegul() != null) {
			prSt.setDate(11, Date.valueOf(donnee.getDateDerniereRegul()));
		} else {
			prSt.setNull(11, java.sql.Types.NULL);
		}
		if (donnee.getAjustementLoyer() != null) {
			prSt.setFloat(12, donnee.getAjustementLoyer());
		} else {
			prSt.setFloat(12, 0f);
		}
		if (donnee.getMoisFinAjustement() != null) {
			prSt.setDate(13, Date.valueOf(donnee.getMoisFinAjustement()));
		} else {
			prSt.setNull(13, java.sql.Types.NULL);
		}
		prSt.setString(14, donnee.getIdLogementAssocie());
		prSt.setInt(15, donnee.getRevolue());
		if (donnee.getMoisDebutAjustement() != null) {
			prSt.setDate(16, Date.valueOf(donnee.getMoisDebutAjustement()));
		} else {
			prSt.setNull(16, java.sql.Types.NULL);
		}
	}

}
