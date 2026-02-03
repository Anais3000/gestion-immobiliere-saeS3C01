package modele.dao.requetes.resiliationbail;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;

import modele.dao.SousProgramme;

/**
 * Requete permettant de retourner le total du (montant) en fin de bail / solde
 * de tout compte, par le locataire si positif ou le propriétaire si négatif,
 * concernant :
 * 
 * . un ID de bien donné . un ID de locataire donné . une date de début de
 * location donnée . un montant de dégradations donné (0 si aucune) . une date
 * de début de période pour la régularisation des charges incluse . une date de
 * fin de période pour la régularisation des charges incluse
 */
public class SousProgrammeTotalDuFinDeBail implements SousProgramme<Float> {

	@Override
	public String appelSousProgramme() {
		return "{? = call  TOTAL_DU_FIN_DE_BAIL(?,?,?,?,?,?)}";
	}

	@Override
	public void parametres(CallableStatement prSt, String... parametres) throws SQLException {

		prSt.registerOutParameter(1, java.sql.Types.FLOAT);

		prSt.setString(2, parametres[0]);
		prSt.setString(3, parametres[1]);
		prSt.setDate(4, Date.valueOf(parametres[2]));
		prSt.setFloat(5, Float.parseFloat(parametres[3]));
		prSt.setDate(6, Date.valueOf(parametres[4]));
		prSt.setDate(7, Date.valueOf(parametres[5]));
	}

	@Override
	public void parametres(CallableStatement prSt, Float donnee) throws SQLException {
		throw new UnsupportedOperationException("Utilisez l'autre version de la méthode parametres");
	}

}
