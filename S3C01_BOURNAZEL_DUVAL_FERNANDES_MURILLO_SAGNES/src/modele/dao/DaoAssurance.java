package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import modele.Assurance;
import modele.dao.requetes.assurance.RequeteDeleteAssurance;
import modele.dao.requetes.assurance.RequeteInsertAssurance;
import modele.dao.requetes.assurance.RequeteSelectAssurance;
import modele.dao.requetes.assurance.RequeteSelectAssuranceById;
import modele.dao.requetes.assurance.RequeteSelectAssuranceByIdBat;

/**
 * Classe permettant l'accès et la modification de données de type Assurance
 */
public class DaoAssurance extends DaoModele<Assurance> implements Dao<Assurance> {

	@Override
	public void create(Assurance donnees) throws SQLException {
		RequeteInsertAssurance r = new RequeteInsertAssurance();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(Assurance donnees) throws SQLException {
		// Méthode non implémentée
	}

	@Override
	public void delete(Assurance donnees) throws SQLException {
		RequeteDeleteAssurance r = new RequeteDeleteAssurance();
		this.miseAJour(r, donnees);
	}

	@Override
	public Assurance findById(String... id) throws SQLException {
		RequeteSelectAssuranceById r = new RequeteSelectAssuranceById();
		return this.findById(r, id);
	}

	@Override
	public Collection<Assurance> findAll() throws SQLException {
		RequeteSelectAssurance r = new RequeteSelectAssurance();
		return this.find(r);
	}

	public Collection<Assurance> findAllByIdBat(String idBat) throws SQLException {
		RequeteSelectAssuranceByIdBat r = new RequeteSelectAssuranceByIdBat();
		return this.find(r, idBat);
	}

	@Override
	protected Assurance creerInstance(ResultSet curseur) throws SQLException {
		return new Assurance(curseur.getString("NUMERO_POLICE_ASSURANCE"), curseur.getString("TYPE_CONTRAT"),
				curseur.getInt("ANNEE_COUVERTURE"), curseur.getDouble("MONTANT_PAYE"),
				curseur.getString("ID_BATIMENT"));
	}

}
