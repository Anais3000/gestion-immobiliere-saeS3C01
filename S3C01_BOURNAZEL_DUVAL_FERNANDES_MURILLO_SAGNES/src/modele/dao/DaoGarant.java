package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import modele.Garant;
import modele.dao.requetes.garant.RequeteInsertGarant;
import modele.dao.requetes.garant.RequeteSelectGarant;
import modele.dao.requetes.garant.RequeteSelectGarantById;
import modele.dao.requetes.garant.SousProgrammeSupprimerGarant;

/**
 * Classe permettant l'accès et la modification de données de type Garant
 */
public class DaoGarant extends DaoModele<Garant> implements Dao<Garant> {

	@Override
	public void create(Garant donnees) throws SQLException {
		RequeteInsertGarant r = new RequeteInsertGarant();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(Garant donnees) throws SQLException {
		// Méthode non implémentée
	}

	@Override
	public void delete(Garant donnees) throws SQLException {
		SousProgrammeSupprimerGarant s = new SousProgrammeSupprimerGarant();
		this.executer(s, donnees);

	}

	@Override
	public Garant findById(String... id) throws SQLException {
		RequeteSelectGarantById r = new RequeteSelectGarantById();
		return this.findById(r, id);
	}

	@Override
	public Collection<Garant> findAll() throws SQLException {
		RequeteSelectGarant r = new RequeteSelectGarant();
		return this.find(r);
	}

	@Override
	protected Garant creerInstance(ResultSet curseur) throws SQLException {
		return new Garant(curseur.getString("ID_GARANT"), curseur.getString("ADRESSE"), curseur.getString("MAIL"),
				curseur.getString("NUM_TEL"));
	}

}
