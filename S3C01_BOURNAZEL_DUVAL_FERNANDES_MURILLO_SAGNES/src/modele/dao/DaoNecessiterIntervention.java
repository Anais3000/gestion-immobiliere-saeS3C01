package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import modele.Necessiter;
import modele.dao.requetes.necessiter.RequeteDeleteNecessiter;
import modele.dao.requetes.necessiter.RequeteInsertNecessiter;
import modele.dao.requetes.necessiter.RequeteSelectNecessiter;
import modele.dao.requetes.necessiter.RequeteSelectNecessiterById;
import modele.dao.requetes.necessiter.RequeteSelectNecessiterByIdBien;
import modele.dao.requetes.necessiter.RequeteSelectNecessiterByIdIntervention;

/**
 * Classe permettant l'accès et la modification de données de type Necessiter
 */
public class DaoNecessiterIntervention extends DaoModele<Necessiter> implements Dao<Necessiter> {

	@Override
	public void create(Necessiter donnees) throws SQLException {
		RequeteInsertNecessiter r = new RequeteInsertNecessiter();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(Necessiter donnees) throws SQLException {
		// Méthode non implémentée
	}

	@Override
	public void delete(Necessiter donnees) throws SQLException {
		RequeteDeleteNecessiter r = new RequeteDeleteNecessiter();
		this.miseAJour(r, donnees);
	}

	@Override
	public Necessiter findById(String... id) throws SQLException {
		RequeteSelectNecessiterById r = new RequeteSelectNecessiterById();
		return this.findById(r, id);
	}

	public List<Necessiter> findByIdIntervention(String... id) throws SQLException {
		RequeteSelectNecessiterByIdIntervention r = new RequeteSelectNecessiterByIdIntervention();
		return find(r, id);
	}

	public List<Necessiter> findByIdBien(String... id) throws SQLException {
		RequeteSelectNecessiterByIdBien r = new RequeteSelectNecessiterByIdBien();
		return this.find(r, id);
	}

	@Override
	public Collection<Necessiter> findAll() throws SQLException {
		RequeteSelectNecessiter r = new RequeteSelectNecessiter();
		return this.find(r);
	}

	@Override
	protected Necessiter creerInstance(ResultSet curseur) throws SQLException {
		return new Necessiter(curseur.getString("ID_BIEN"), curseur.getString("ID_INTERVENTION"));
	}

}
