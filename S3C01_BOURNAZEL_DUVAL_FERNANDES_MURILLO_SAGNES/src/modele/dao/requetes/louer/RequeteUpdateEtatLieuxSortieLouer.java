package modele.dao.requetes.louer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Louer;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de mettre à jour les informations de l'état des lieux de
 * sortie d'un contrat de location d'ID donné
 */
public class RequeteUpdateEtatLieuxSortieLouer extends Requete<Louer> {

	@Override
	public String requete() {
		return "UPDATE SAE_LOUER SET DATE_ETAT_DES_LIEUX_SORTIE = ?, DETAILS_ETAT_DES_LIEUX_SORTIE = ? WHERE DATE_DEBUT = ? AND ID_BIEN = ? AND ID_LOCATAIRE = ?";
	}

	@Override
	// Il faut donc d'abord changer l'objet Louer
	public void parametres(PreparedStatement prSt, Louer donnee) throws SQLException {
		prSt.setDate(1, Date.valueOf(donnee.getDateEtatDesLieuxSortie()));
		prSt.setString(2, donnee.getDetailsEtatDesLieuxSortie());
		prSt.setDate(3, Date.valueOf(donnee.getDateDebut()));
		prSt.setString(4, donnee.getIdBienLouable());
		prSt.setString(5, donnee.getIdLocataire());
	}

}
