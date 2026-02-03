package modele.dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modele.HistoriqueLoyer;
import modele.dao.requetes.Requete;

/**
 * Classe abstraite définissant les méthodes d'accès et de modification des
 * données des autres classes Dao
 */
public abstract class DaoModele<T> implements Dao<T> {

	protected abstract T creerInstance(ResultSet curseur) throws SQLException;

	protected List<T> select(PreparedStatement prSt) throws SQLException {

		List<T> listResult = new ArrayList<>();
		ResultSet res = prSt.executeQuery();
		while (res.next()) {
			T instance = this.creerInstance(res);
			listResult.add(instance);
		}
		res.close();
		prSt.close();
		return listResult;
	}

	protected int miseAJour(Requete<T> req, T donnee) throws SQLException {
		try (PreparedStatement prSt = UtOracleDataSource.getConnectionBD().prepareStatement(req.requete())) {
			// Affecte les paramètres de la requête
			req.parametres(prSt, donnee);
			// Exécute la mise à jour
			return prSt.executeUpdate();
			// pas besoin du prSt.close() car try-with ressources
		}
	}

	protected List<T> find(Requete<T> req, String... id) throws SQLException {
		List<T> listResult = new ArrayList<>();
		try (PreparedStatement prSt = UtOracleDataSource.getConnectionBD().prepareStatement(req.requete())) {
			// Affecte les paramètres de la requête
			req.parametres(prSt, id);
			// Exécute le find
			listResult = this.select(prSt);
			// pas besoin du ptSt.close() car try-with ressources
			return listResult;
		}
	}

	protected T findById(Requete<T> req, String... id) throws SQLException {
		List<T> listResult = new ArrayList<>();
		try (PreparedStatement prSt = UtOracleDataSource.getConnectionBD().prepareStatement(req.requete())) {
			// Affecte les paramètres de la requête
			req.parametres(prSt, id);
			// Exécute le find
			listResult = this.select(prSt);
			if (listResult.isEmpty()) {
				return null;
			} else {
				return listResult.get(0); // par défaut le premier élément de la liste est considéré comme l'élément de
											// cet ID
			}
		}
	}

	protected boolean executer(SousProgramme<T> ssprg, T donnee) throws SQLException {
		try (CallableStatement caSt = UtOracleDataSource.getConnectionBD().prepareCall(ssprg.appelSousProgramme())) {
			// Affecte les paramètres de la requête
			ssprg.parametres(caSt, donnee);
			// Exécute la mise à jour
			return caSt.execute();
			// pas besoin du caST.close() car try-with ressources
		}
	}

	protected Float executer(SousProgramme<Float> ssprg, String... id) throws SQLException {
		try (CallableStatement caSt = UtOracleDataSource.getConnectionBD().prepareCall(ssprg.appelSousProgramme())) {
			// Affecte les paramètres de la requête
			ssprg.parametres(caSt, id);
			// Exécute la mise à jour
			caSt.execute();
			return caSt.getFloat(1);
			// pas besoin du caST.close() car try-with ressources
		}
	}

	protected Float executerFloat(SousProgramme<Float> ssprg) throws SQLException {
		try (CallableStatement caSt = UtOracleDataSource.getConnectionBD().prepareCall(ssprg.appelSousProgramme())) {

			ssprg.parametres(caSt);
			caSt.execute();
			return caSt.getFloat(1);
		}
	}

	protected boolean executer(SousProgramme<T> ssprg) throws SQLException {
		try (CallableStatement caSt = UtOracleDataSource.getConnectionBD().prepareCall(ssprg.appelSousProgramme())) {
			// Exécute la mise à jour
			return caSt.execute();
			// pas besoin du caST.close() car try-with ressources
		}
	}

	protected HistoriqueLoyer executerHistorique(SousProgramme<HistoriqueLoyer> ssprg, String... id)
			throws SQLException {
		try (CallableStatement caSt = UtOracleDataSource.getConnectionBD().prepareCall(ssprg.appelSousProgramme())) {
			// Affecte les paramètres de la requête
			ssprg.parametres(caSt, id);

			caSt.execute();

			Float loyer = caSt.getFloat(3);
			Float provision = caSt.getFloat(4);

			// Pas besoin de caSt.close car try-with

			return new HistoriqueLoyer(loyer, provision, id[0], LocalDate.parse(id[1]));
		}
	}

	public static <T> T safeValue(T value) {
		if (value == null) {
			return null;
		} else {
			return value;
		}
	}

	public static LocalDate safeDate(java.sql.Date d) {
		if (d == null) {
			return null;
		} else {
			return d.toLocalDate();
		}
	}

}
