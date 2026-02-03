package modele.dao.requetes.batiment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modele.Batiment;
import modele.dao.requetes.Requete;

/**
 * Requete permettant de mettre à jour un batiment en BD en fonction de son
 * identifiant ID_BATIMENT
 */
public class RequeteUpdateBatiment extends Requete<Batiment> {

	@Override
	public String requete() {
		return "UPDATE SAE_BATIMENT SET DATE_CONSTRUCTION = ?, ADRESSE = ?, CODE_POSTAL = ?, VILLE = ?, NUMERO_FISCAL_BAT = ? WHERE ID_BATIMENT = ?";
	}

	@Override
	public void parametres(PreparedStatement prSt, Batiment donnee) throws SQLException {
		prSt.setDate(1, Date.valueOf(donnee.getDateConstruction()));
		prSt.setString(2, donnee.getAdresse());
		prSt.setString(3, donnee.getCodePostal());
		prSt.setString(4, donnee.getVille());
		prSt.setString(5, donnee.getNumFiscal());
		prSt.setString(6, donnee.getIdBat());
	}

}
