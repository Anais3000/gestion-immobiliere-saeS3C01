package modele.dao.requetes.locataire;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Locataire;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de mettre à jour les informations d'un locataire d'ID
 * spécifié
 */
public class RequeteUpdateLocataire extends Requete<Locataire> {

	@Override
	public String requete() {
		return "UPDATE SAE_LOCATAIRE SET NOM = ?, PRENOM = ?, NUM_TEL = ?, MAIL = ?, DATE_NAISSANCE = ?, VILLE_NAISSANCE = ?,"
				+ "ADRESSE = ?, CODE_POSTAL = ?, VILLE = ? WHERE ID_LOCATAIRE = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Locataire donnee) throws SQLException {
		prSt.setString(1, donnee.getNom());
		prSt.setString(2, donnee.getPrenom());
		prSt.setString(3, donnee.getNumTelephone());
		prSt.setString(4, donnee.getMail());
		prSt.setDate(5, Date.valueOf(donnee.getDateNaissance()));
		prSt.setString(6, donnee.getVilleNaissance());
		prSt.setString(7, donnee.getAdresse());
		prSt.setString(8, donnee.getCodePostal());
		prSt.setString(9, donnee.getVille());

		prSt.setString(10, donnee.getIdLocataire());
	}

}
