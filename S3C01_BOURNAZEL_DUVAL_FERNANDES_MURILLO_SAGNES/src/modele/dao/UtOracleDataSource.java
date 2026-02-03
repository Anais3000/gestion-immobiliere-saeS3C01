package modele.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import controleur.Outils;
import oracle.jdbc.datasource.impl.OracleDataSource;

/**
 * Classe permettant la connexion à la base de données
 */
public class UtOracleDataSource extends OracleDataSource {

	private static UtOracleDataSource instance;

	private static Connection connection;

	/**
	 * Définit les informations de connexion à la base de données
	 * 
	 * @param nom login de l'utilisateur
	 * @param mdp mot de passe de l'utilisateur
	 * @throws SQLException
	 */
	public UtOracleDataSource(String nom, String mdp) throws SQLException {
		this.setURL("jdbc:oracle:thin:@telline.univ-tlse3.fr:1521:etupre");
		this.setUser(nom);
		this.setPassword(mdp);
	}

	/**
	 * Retourne une instance de la source des données
	 * 
	 * @param login login de l'utilisateur
	 * @param mdp   mot de passe de l'utilisateur
	 * 
	 * @return une nouvelle instance si aucune n'existe, sinon l'instance existante
	 * @throws SQLException
	 */
	public static UtOracleDataSource creerAcces(String login, String mdp) throws SQLException {
		if (instance == null) {
			instance = new UtOracleDataSource(login, mdp);
			connection = instance.getConnection();
		}
		return instance;
	}

	/**
	 * Retourne l'objet symbolisant la connexion à la base de données
	 * 
	 * @return l'objet symbolisant la connexion, de type Connection
	 */
	public static Connection getConnectionBD() {
		return connection;
	}

	/**
	 * Permet de mettre fin à la connexion à la base de données
	 */
	public static void deconnecter() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
				instance = null;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
