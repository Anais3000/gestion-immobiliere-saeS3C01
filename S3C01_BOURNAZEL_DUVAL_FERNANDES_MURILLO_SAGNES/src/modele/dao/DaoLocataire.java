package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import modele.Locataire;
import modele.dao.requetes.locataire.RequeteInsertLocataire;
import modele.dao.requetes.locataire.RequeteSelectLocataire;
import modele.dao.requetes.locataire.RequeteSelectLocataireById;
import modele.dao.requetes.locataire.RequeteSelectLocataireByIdBienLouable;
import modele.dao.requetes.locataire.RequeteUpdateLocataire;
import modele.dao.requetes.locataire.SousProgammeSupprimerLocataire;

/**
 * Classe permettant l'accès et la modification de données de type Locataire
 */
public class DaoLocataire extends DaoModele<Locataire> implements Dao<Locataire> {

	@Override
	public void create(Locataire donnees) throws SQLException {
		RequeteInsertLocataire r = new RequeteInsertLocataire();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(Locataire donnees) throws SQLException {
		RequeteUpdateLocataire r = new RequeteUpdateLocataire();
		this.miseAJour(r, donnees);
	}

	@Override
	public void delete(Locataire donnees) throws SQLException {
		SousProgammeSupprimerLocataire s = new SousProgammeSupprimerLocataire();
		this.executer(s, donnees);
	}

	@Override
	public Locataire findById(String... id) throws SQLException {
		RequeteSelectLocataireById r = new RequeteSelectLocataireById();
		return this.findById(r, id);
	}

	@Override
	public Collection<Locataire> findAll() throws SQLException {
		RequeteSelectLocataire r = new RequeteSelectLocataire();
		return this.find(r);
	}

	public Locataire findByIdBienLouable(String... id) throws SQLException {
		RequeteSelectLocataireByIdBienLouable r = new RequeteSelectLocataireByIdBienLouable();
		return this.findById(r, id);
	}

	@Override
	protected Locataire creerInstance(ResultSet curseur) throws SQLException {
		return new Locataire(curseur.getString("ID_LOCATAIRE"), curseur.getString("NOM"), curseur.getString("PRENOM"),
				curseur.getString("NUM_TEL"), curseur.getString("MAIL"),
				curseur.getDate("DATE_NAISSANCE").toLocalDate(), curseur.getString("VILLE_NAISSANCE"),
				curseur.getString("ADRESSE"), curseur.getString("CODE_POSTAL"), curseur.getString("VILLE"));
	}

}
