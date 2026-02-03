package modele.dao.requetes.bienlouable;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.BienLouable;
import modele.dao.requetes.Requete;

/**
 * Requête permettant d'insérer un bien louable en BD
 */
public class RequeteInsertBienLouable extends Requete<BienLouable> {

	@Override
	public String requete() {
		return "INSERT INTO SAE_BIEN_LOUABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	@Override
	public void parametres(PreparedStatement prSt, BienLouable donnee) throws SQLException {
		prSt.setString(1, donnee.getIdBien());
		prSt.setString(2, donnee.getAdresse());
		prSt.setString(3, donnee.getVille());
		prSt.setString(4, donnee.getCodePostal());
		prSt.setString(5, donnee.getTypeBien());
		prSt.setDate(6, Date.valueOf(donnee.getDateConstruction()));
		prSt.setDouble(7, donnee.getSurfaceHabitable());
		prSt.setInt(8, donnee.getNbPieces());
		prSt.setDouble(9, donnee.getPourcentageEntretienPartiesCommunes());
		prSt.setDouble(10, donnee.getPourcentageOrduresMenageres());
		prSt.setString(11, donnee.getNumeroFiscal());
		prSt.setDouble(12, donnee.getLoyer());
		prSt.setDouble(13, donnee.getProvisionPourCharges());
		prSt.setString(14, donnee.getIdBat());
		if (donnee.getDerniereAnneeModifLoyer() == null) {
			prSt.setNull(15, java.sql.Types.NULL);
		} else {
			prSt.setInt(15, donnee.getDerniereAnneeModifLoyer());
		}
	}

}
