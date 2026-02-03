package modele.dao.requetes.organisme;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Organisme;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de mettre à jour les informations d'un organisme d'ID
 * donné
 */
public class RequeteUpdateOrganisme extends Requete<Organisme> {

	@Override
	public String requete() {
		return "UPDATE SAE_ORGANISME SET NOM = ?, ADRESSE = ?, CODE_POSTAL = ?, VILLE = ?,"
				+ "MAIL = ?, NUM_TEL = ?, SPECIALITE = ? WHERE NUM_SIRET = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Organisme donnee) throws SQLException {
		prSt.setString(1, donnee.getNom());
		prSt.setString(2, donnee.getAdresse());
		prSt.setString(3, donnee.getCodePostal());
		prSt.setString(4, donnee.getVille());
		prSt.setString(5, donnee.getMail());
		prSt.setString(6, donnee.getNumTel());
		prSt.setString(7, donnee.getSpecialite());
		prSt.setString(8, donnee.getNumSIRET());
	}

}
