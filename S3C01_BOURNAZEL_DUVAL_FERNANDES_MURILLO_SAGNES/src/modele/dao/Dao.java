package modele.dao;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Interface permettant l'accès et la modification de données
 * 
 * @param <T> Type de donnée
 */
public interface Dao<T> {

	public void create(T donnees) throws SQLException;

	public void update(T donnees) throws SQLException;

	public void delete(T donnees) throws SQLException;

	public T findById(String... id) throws SQLException;
	// Avec String... c'est comme si on spécifiait un tableau de String
	// Dans la fonction on y accédera comme on accède à des éléments d'un tableau.

	public Collection<T> findAll() throws SQLException;

}
