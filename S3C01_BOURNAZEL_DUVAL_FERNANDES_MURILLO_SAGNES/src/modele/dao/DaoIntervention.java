package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import modele.Intervention;
import modele.dao.requetes.intervention.RequeteInsertIntervention;
import modele.dao.requetes.intervention.RequeteSelectIntervention;
import modele.dao.requetes.intervention.RequeteSelectInterventionById;
import modele.dao.requetes.intervention.RequeteSelectInterventionByIdBat;
import modele.dao.requetes.intervention.RequeteSelectInterventionByIdBien;
import modele.dao.requetes.intervention.RequeteSelectInterventionByIdOrganisme;
import modele.dao.requetes.intervention.RequeteUpdateFactureIntervention;
import modele.dao.requetes.intervention.SousProgrammeSupprimerIntervention;

/**
 * Classe permettant l'accès et la modification de données de type Intervention
 */
public class DaoIntervention extends DaoModele<Intervention> implements Dao<Intervention> {

	@Override
	public void create(Intervention donnees) throws SQLException {
		RequeteInsertIntervention r = new RequeteInsertIntervention();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(Intervention donnees) throws SQLException {
		// Méthode non implémentée
	}

	public void updateFacture(Intervention donnees) throws SQLException {
		RequeteUpdateFactureIntervention r = new RequeteUpdateFactureIntervention();
		this.miseAJour(r, donnees);
	}

	@Override
	public void delete(Intervention donnees) throws SQLException {
		SousProgrammeSupprimerIntervention s = new SousProgrammeSupprimerIntervention();
		this.executer(s, donnees);
	}

	@Override
	public Intervention findById(String... id) throws SQLException {
		RequeteSelectInterventionById r = new RequeteSelectInterventionById();
		return this.findById(r, id);
	}

	@Override
	public Collection<Intervention> findAll() throws SQLException {
		RequeteSelectIntervention r = new RequeteSelectIntervention();
		return this.find(r);
	}

	public Collection<Intervention> findAllByIdBat(String idBat) throws SQLException {
		RequeteSelectInterventionByIdBat r = new RequeteSelectInterventionByIdBat();
		return this.find(r, idBat);
	}

	public Collection<Intervention> findAllByIdBien(String idBien) throws SQLException {
		RequeteSelectInterventionByIdBien r = new RequeteSelectInterventionByIdBien();
		return this.find(r, idBien);
	}

	public Collection<Intervention> findAllByIdOrganisme(String idOrganisme) throws SQLException {
		RequeteSelectInterventionByIdOrganisme r = new RequeteSelectInterventionByIdOrganisme();
		return this.find(r, idOrganisme);
	}

	@Override
	protected Intervention creerInstance(ResultSet curseur) throws SQLException {

		LocalDate dateFacture = null;
		if (curseur.getDate("DATE_FACTURE") != null) {
			dateFacture = curseur.getDate("DATE_FACTURE").toLocalDate();
		}

		Float montantFacture = curseur.getObject("MONTANT_FACTURE", Float.class); // obligé car Float et si null
																					// retourne 0.0

		return new Intervention(curseur.getString("ID_INTERVENTION"), curseur.getString("INTITULE"),
				curseur.getDate("DATE_INTERVENTION").toLocalDate(), curseur.getString("NUMERO_FACTURE"), dateFacture,
				montantFacture, DaoModele.safeDate(curseur.getDate("DATE_ACOMPTE")),
				DaoModele.safeValue(curseur.getFloat("ACOMPTE")), curseur.getString("NUMERO_DEVIS"),
				curseur.getFloat("MONTANT_DEVIS"), curseur.getFloat("MONTANT_NON_DEDUCTIBLE"),
				curseur.getFloat("REDUCTION"), curseur.getString("NUM_SIRET"), curseur.getString("ID_BATIMENT"),
				curseur.getInt("ENTRETIEN_PC"), curseur.getInt("ORDURES"));
	}

}
