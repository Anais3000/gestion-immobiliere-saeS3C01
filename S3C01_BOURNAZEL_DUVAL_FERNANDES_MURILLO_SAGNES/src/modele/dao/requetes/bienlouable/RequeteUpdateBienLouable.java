package modele.dao.requetes.bienlouable;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.BienLouable;
import modele.dao.requetes.Requete;

/**
 * Requête permettant de mettre à jour un bien louable d'un id spécifié en BD
 */
public class RequeteUpdateBienLouable extends Requete<BienLouable> {

	@Override
	public String requete() {
		return "UPDATE SAE_BIEN_LOUABLE SET ADRESSE = ?, VILLE = ?, CODE_POSTAL = ?, TYPE_BIEN = ?, DATE_CONSTRUCTION = ?,"
				+ "SURFACE_HABITABLE = ?, NB_PIECES = ?, POURCENTAGE_ENTRETIEN_PARTIES_COMMUNES = ?, POURCENTAGE_ORDURES_MENAGERES = ?,"
				+ "NUMERO_FISCAL = ?, LOYER = ?, PROVISION_POUR_CHARGES = ?, ID_BATIMENT = ?, DERNIERE_ANNEE_MODIF_LOYER = ? WHERE ID_BIEN = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, BienLouable donnee) throws SQLException {
		prSt.setString(1, donnee.getAdresse());
		prSt.setString(2, donnee.getVille());
		prSt.setString(3, donnee.getCodePostal());
		prSt.setString(4, donnee.getTypeBien());
		prSt.setDate(5, Date.valueOf(donnee.getDateConstruction()));
		prSt.setDouble(6, donnee.getSurfaceHabitable());
		prSt.setInt(7, donnee.getNbPieces());
		prSt.setDouble(8, donnee.getPourcentageEntretienPartiesCommunes());
		prSt.setDouble(9, donnee.getPourcentageOrduresMenageres());
		prSt.setString(10, donnee.getNumeroFiscal());
		prSt.setDouble(11, donnee.getLoyer());
		prSt.setDouble(12, donnee.getProvisionPourCharges());
		prSt.setString(13, donnee.getIdBat());

		if (donnee.getDerniereAnneeModifLoyer() == null) {
			prSt.setNull(14, java.sql.Types.NULL);
		} else {
			prSt.setInt(14, donnee.getDerniereAnneeModifLoyer());
		}

		prSt.setString(15, donnee.getIdBien());
	}

}
