package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import modele.ReleveCompteur;
import modele.dao.requetes.regularisation.SousProgrammeRegularisationSurPeriode;
import modele.dao.requetes.regularisation.SousProgrammeTotalChargesSurPeriode;
import modele.dao.requetes.regularisation.SousProgrammeTotalOrduresSurPeriode;
import modele.dao.requetes.regularisation.SousProgrammeTotalProvisionsSurPeriode;
import modele.dao.requetes.relevecompteur.RequeteDeleteReleveCompteur;
import modele.dao.requetes.relevecompteur.RequeteInsertReleveCompteur;
import modele.dao.requetes.relevecompteur.RequeteSelectLastReleveCompteurByIdCompteur;
import modele.dao.requetes.relevecompteur.RequeteSelectReleveCompteur;
import modele.dao.requetes.relevecompteur.RequeteSelectReleveCompteurById;
import modele.dao.requetes.relevecompteur.RequeteSelectReleveCompteurByIdBat;
import modele.dao.requetes.relevecompteur.RequeteSelectReleveCompteurByIdBien;

/**
 * Classe permettant l'accès et la modification de données de type
 * ReleveCompteur
 */
public class DaoReleveCompteur extends DaoModele<ReleveCompteur> implements Dao<ReleveCompteur> {

	@Override
	public void create(ReleveCompteur donnees) throws SQLException {
		RequeteInsertReleveCompteur r = new RequeteInsertReleveCompteur();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(ReleveCompteur donnees) throws SQLException {
		// Méthode non implémentée
	}

	@Override
	public void delete(ReleveCompteur donnees) throws SQLException {
		RequeteDeleteReleveCompteur r = new RequeteDeleteReleveCompteur();
		this.miseAJour(r, donnees);
	}

	@Override
	public ReleveCompteur findById(String... id) throws SQLException {
		RequeteSelectReleveCompteurById r = new RequeteSelectReleveCompteurById();
		return this.findById(r, id);
	}

	public ReleveCompteur findLastByIdCompt(String... id) throws SQLException {
		RequeteSelectLastReleveCompteurByIdCompteur r = new RequeteSelectLastReleveCompteurByIdCompteur();
		return this.findById(r, id);
	}

	@Override
	public Collection<ReleveCompteur> findAll() throws SQLException {
		RequeteSelectReleveCompteur r = new RequeteSelectReleveCompteur();
		return this.find(r);
	}

	public Collection<ReleveCompteur> findAllByIdBat(String id) throws SQLException {
		RequeteSelectReleveCompteurByIdBat r = new RequeteSelectReleveCompteurByIdBat();
		return this.find(r, id);
	}

	public Collection<ReleveCompteur> findAllByIdBien(String id) throws SQLException {
		RequeteSelectReleveCompteurByIdBien r = new RequeteSelectReleveCompteurByIdBien();
		return this.find(r, id);
	}

	public Float totalChargesSurPeriode(String... id) throws SQLException {
		SousProgrammeTotalChargesSurPeriode s = new SousProgrammeTotalChargesSurPeriode();
		return this.executer(s, id);
	}

	public Float totalOrduresSurPeriode(String... id) throws SQLException {
		SousProgrammeTotalOrduresSurPeriode s = new SousProgrammeTotalOrduresSurPeriode();
		return this.executer(s, id);
	}

	public Float totalProvisionsSurPeriode(String... id) throws SQLException {
		SousProgrammeTotalProvisionsSurPeriode s = new SousProgrammeTotalProvisionsSurPeriode();
		return this.executer(s, id);
	}

	public Float totalRegularisationSurPeriode(String... id) throws SQLException {
		SousProgrammeRegularisationSurPeriode s = new SousProgrammeRegularisationSurPeriode();
		return this.executer(s, id);
	}

	@Override
	protected ReleveCompteur creerInstance(ResultSet curseur) throws SQLException {
		return new ReleveCompteur(curseur.getDate("DATE_RELEVE").toLocalDate(), curseur.getDouble("INDEX_COMPTEUR"),
				curseur.getDouble("PRIX_UNITE"), curseur.getDouble("PARTIE_FIXE"), curseur.getString("ID_COMPTEUR"),
				curseur.getDouble("ANCIEN_INDEX"));
	}

}
