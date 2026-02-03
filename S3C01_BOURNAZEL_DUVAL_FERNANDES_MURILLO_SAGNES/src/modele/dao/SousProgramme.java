package modele.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * Interface permettant l'accès à des fonctions et procédures stockées en BD
 */
public interface SousProgramme<T> {

	public abstract String appelSousProgramme();

	public abstract void parametres(CallableStatement prSt, String... parametres) throws SQLException;

	public abstract void parametres(CallableStatement prSt, T donnee) throws SQLException;

}
