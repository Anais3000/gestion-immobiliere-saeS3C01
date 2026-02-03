package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import modele.Compteur;
import modele.dao.requetes.compteur.RequeteDeleteCompteur;
import modele.dao.requetes.compteur.RequeteInsertCompteur;
import modele.dao.requetes.compteur.RequeteSelectCompteur;
import modele.dao.requetes.compteur.RequeteSelectCompteurById;
import modele.dao.requetes.compteur.RequeteSelectCompteurByIdBat;
import modele.dao.requetes.compteur.RequeteSelectCompteurByIdBien;

/**
 * Classe permettant l'accès et la modification de données de type Compteur
 */
public class DaoCompteur extends DaoModele<Compteur> implements Dao<Compteur> {

	@Override
	public void create(Compteur donnees) throws SQLException {
		RequeteInsertCompteur r = new RequeteInsertCompteur();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(Compteur donnees) throws SQLException {
		// Méthode non implémentée
	}

	@Override
	public void delete(Compteur donnees) throws SQLException {
		RequeteDeleteCompteur r = new RequeteDeleteCompteur();
		this.miseAJour(r, donnees);
	}

	@Override
	public Compteur findById(String... id) throws SQLException {
		RequeteSelectCompteurById r = new RequeteSelectCompteurById();
		return this.findById(r, id);
	}

	@Override
	public Collection<Compteur> findAll() throws SQLException {
		RequeteSelectCompteur r = new RequeteSelectCompteur();
		return this.find(r);
	}

	public Collection<Compteur> findAllByIdBat(String idBat) throws SQLException {
		RequeteSelectCompteurByIdBat r = new RequeteSelectCompteurByIdBat();
		return this.find(r, idBat);
	}

	public Collection<Compteur> findAllByIdBien(String id) throws SQLException {
		RequeteSelectCompteurByIdBien r = new RequeteSelectCompteurByIdBien();
		return this.find(r, id);
	}

	@Override
	protected Compteur creerInstance(ResultSet curseur) throws SQLException {

		LocalDate dateInstal = null;
		if (curseur.getDate("DATE_INSTALLATION") != null) {
			dateInstal = curseur.getDate("DATE_INSTALLATION").toLocalDate();
		}

		return new Compteur(curseur.getString("ID_BATIMENT"), curseur.getString("ID_BIEN"),
				curseur.getString("TYPE_COMPTEUR"), curseur.getString("ID_COMPTEUR"), dateInstal,
				curseur.getFloat("INDEX_DEPART"));
	}

}
