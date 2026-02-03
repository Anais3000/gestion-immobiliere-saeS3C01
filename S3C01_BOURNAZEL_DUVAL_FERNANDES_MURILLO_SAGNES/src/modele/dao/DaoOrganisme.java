package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import modele.Organisme;
import modele.dao.requetes.organisme.RequeteInsertOrganisme;
import modele.dao.requetes.organisme.RequeteSelectOrganisme;
import modele.dao.requetes.organisme.RequeteSelectOrganismeById;
import modele.dao.requetes.organisme.RequeteSelectOrganismeByNom;
import modele.dao.requetes.organisme.RequeteUpdateOrganisme;
import modele.dao.requetes.organisme.SousProgrammeSupprimerOrganisme;

/**
 * Classe permettant l'accès et la modification de données de type Organisme
 */
public class DaoOrganisme extends DaoModele<Organisme> implements Dao<Organisme> {

	@Override
	public void create(Organisme donnees) throws SQLException {
		RequeteInsertOrganisme r = new RequeteInsertOrganisme();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(Organisme donnees) throws SQLException {
		RequeteUpdateOrganisme r = new RequeteUpdateOrganisme();
		this.miseAJour(r, donnees);
	}

	@Override
	public void delete(Organisme donnees) throws SQLException {
		SousProgrammeSupprimerOrganisme s = new SousProgrammeSupprimerOrganisme();
		this.executer(s, donnees);
	}

	@Override
	public Organisme findById(String... id) throws SQLException {
		RequeteSelectOrganismeById r = new RequeteSelectOrganismeById();
		return this.findById(r, id);
	}

	public Organisme findByNom(String nom) throws SQLException {
		RequeteSelectOrganismeByNom r = new RequeteSelectOrganismeByNom();
		return this.findById(r, nom);
	}

	@Override
	public Collection<Organisme> findAll() throws SQLException {
		RequeteSelectOrganisme r = new RequeteSelectOrganisme();
		return this.find(r);
	}

	@Override
	protected Organisme creerInstance(ResultSet curseur) throws SQLException {
		return new Organisme(curseur.getString("NUM_SIRET"), curseur.getString("NOM"), curseur.getString("ADRESSE"),
				curseur.getString("VILLE"), curseur.getString("CODE_POSTAL"), curseur.getString("MAIL"),
				curseur.getString("NUM_TEL"), curseur.getString("SPECIALITE"));
	}

}
