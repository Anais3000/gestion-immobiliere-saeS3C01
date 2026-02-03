package modele.dao.requetes;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Classe définissant les méthodes que doivent implémenter les requêtes sur un
 * type de données donné
 * 
 * @param <T> type de données en question
 */
public abstract class Requete<T> {

	public abstract String requete();

	// On met les deux méthodes suivant avec un corps vide pour que les classes
	// héritantes n'aient pas besoin de les redéfinir
	// par exemple les requêtes sans WHERE

	public void parametres(PreparedStatement prSt, String... id) throws SQLException {
	}

	public void parametres(PreparedStatement prSt, T donnee) throws SQLException {
	}

	public void parametres(PreparedStatement prSt, int... id) throws SQLException {
	}
}