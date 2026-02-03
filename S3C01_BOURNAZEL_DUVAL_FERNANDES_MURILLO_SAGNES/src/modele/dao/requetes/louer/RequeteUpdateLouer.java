package modele.dao.requetes.louer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de mettre à jour les informations d'un contrat de location
 * d'ID donné
 */
public class RequeteUpdateLouer extends Requete<Louer> {

	@Override
	public String requete() {
		return "UPDATE SAE_LOUER SET DATE_FIN = ?, DEPOT_DE_GARANTIE = ?, DATE_ETAT_DES_LIEUX_ENTREE = ?, DATE_ETAT_DES_LIEUX_SORTIE = ?,"
				+ "DETAILS_ETAT_DES_LIEUX_ENTREE = ?, DETAILS_ETAT_DES_LIEUX_SORTIE = ?, ID_GARANT = ?, DATE_DERNIERE_REGUL = ?, "
				+ "AJUSTEMENT_LOYER = ?, MOIS_FIN_AJUSTEMENT = ?, ID_LOGEMENT_ASSOCIE = ?, REVOLUE = ? "
				+ "WHERE ID_BIEN = ? AND ID_LOCATAIRE = ? AND DATE_DEBUT = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Louer donnee) throws SQLException {
		prSt.setDate(1, Date.valueOf(donnee.getDateFin()));
		prSt.setDouble(2, donnee.getDepotDeGarantie());

		if (donnee.getDateEtatDesLieuxEntree() != null) {
			prSt.setDate(3, Date.valueOf(donnee.getDateEtatDesLieuxEntree()));
		} else {
			prSt.setNull(3, java.sql.Types.NULL);
		}

		if (donnee.getDateEtatDesLieuxSortie() != null) {
			prSt.setDate(4, Date.valueOf(donnee.getDateEtatDesLieuxSortie()));
		} else {
			prSt.setNull(4, java.sql.Types.NULL);
		}

		prSt.setString(5, donnee.getDetailsEtatDesLieuxEntree());
		prSt.setString(6, donnee.getDetailsEtatDesLieuxSortie());
		prSt.setString(7, donnee.getIdGarant());

		if (donnee.getDateDerniereRegul() != null) {
			prSt.setDate(8, Date.valueOf(donnee.getDateDerniereRegul()));
		} else {
			prSt.setNull(8, java.sql.Types.NULL);
		}

		prSt.setFloat(9, donnee.getAjustementLoyer());

		if (donnee.getMoisFinAjustement() != null) {
			prSt.setDate(10, Date.valueOf(donnee.getMoisFinAjustement()));
		} else {
			prSt.setNull(10, java.sql.Types.NULL);
		}

		prSt.setString(11, donnee.getIdLogementAssocie());
		prSt.setInt(12, donnee.getRevolue());

		prSt.setString(13, donnee.getIdBienLouable());
		prSt.setString(14, donnee.getIdLocataire());
		prSt.setDate(15, Date.valueOf(donnee.getDateDebut()));
	}

}
