package modele.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import modele.dao.requetes.Requete;
import modele.dao.requetes.chiffres_cles.RequeteSelectTotalMontantTravauxParBatimentEtAnnee;
import modele.dao.requetes.chiffres_cles.RequeteSelectTotalMontantTravauxParBienEtAnnee;

/**
 * Classe permettant l'accès et la modification de données tirées de fonctions
 * (chiffres clés sur la page d'accueil)
 */
public class DaoChiffresCles {

	public Map<String, Double> totalMontantTravauxParBienEtAnnee(String idBien, String date) throws SQLException {
		RequeteSelectTotalMontantTravauxParBienEtAnnee r = new RequeteSelectTotalMontantTravauxParBienEtAnnee();
		return executer(r, idBien, date);
	}

	public Map<String, Double> totalMontantTravauxParBatimentEtAnnee(String idBat, String date) throws SQLException {
		RequeteSelectTotalMontantTravauxParBatimentEtAnnee r = new RequeteSelectTotalMontantTravauxParBatimentEtAnnee();
		return executer(r, idBat, date);
	}

	private HashMap<String, Double> executer(Requete<?> req, String idBien, String date) throws SQLException {
		PreparedStatement prSt = UtOracleDataSource.getConnectionBD().prepareStatement(req.requete());
		req.parametres(prSt, idBien, date);
		HashMap<String, Double> resultat = new HashMap<>();
		ResultSet res = prSt.executeQuery();
		while (res.next()) {
			resultat.put(res.getString("organisme"), res.getDouble("somme"));
		}
		res.close();
		prSt.close();
		return resultat;
	}
}
