package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import modele.Historique;
import modele.dao.requetes.historique.RequeteInsertHistorique;
import modele.dao.requetes.historique.RequeteSelectHistorique;

/**
 * Classe permettant l'accès et la modification de données de type Historique
 */
public class DaoHistorique extends DaoModele<Historique> implements Dao<Historique> {

	@Override
	public void create(Historique donnees) throws SQLException {
		RequeteInsertHistorique r = new RequeteInsertHistorique();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(Historique donnees) throws SQLException {
		// Méthode non implémentée

	}

	@Override
	public void delete(Historique donnees) throws SQLException {
		// Méthode non implémentée

	}

	@Override
	public Historique findById(String... id) throws SQLException {
		// Méthode non implémentée
		return null;
	}

	@Override
	public Collection<Historique> findAll() throws SQLException {
		RequeteSelectHistorique r = new RequeteSelectHistorique();
		return this.find(r);
	}

	@Override
	protected Historique creerInstance(ResultSet curseur) throws SQLException {
		return new Historique(curseur.getTimestamp("DATE_ACTION").toLocalDateTime(), curseur.getString("ACTION"),
				curseur.getString("DETAILS"));
	}

}
