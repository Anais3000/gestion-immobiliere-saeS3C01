package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import modele.Paiement;
import modele.dao.requetes.paiement.RequeteDeletePaiement;
import modele.dao.requetes.paiement.RequeteInsertPaiement;
import modele.dao.requetes.paiement.RequeteSelectPaiement;
import modele.dao.requetes.paiement.RequeteSelectPaiementById;
import modele.dao.requetes.paiement.RequeteSelectPaiementByIdBien;
import modele.dao.requetes.paiement.RequeteSelectPaiementByIdBienEtLibelle;
import modele.dao.requetes.paiement.RequeteSelectPaiementByIdInterv;
import modele.dao.requetes.paiement.RequeteSelectPaiementByIdLoc;
import modele.dao.requetes.paiement.RequeteSelectPaiementByLocEtBien;
import modele.dao.requetes.paiement.RequeteSelectPaiementByLocEtBienEtDate;
import modele.dao.requetes.paiement.RequeteSelectPaiementLoyerIdBienBetweenMonths;

/**
 * Classe permettant l'accès et la modification de données de type Paiement
 */
public class DaoPaiement extends DaoModele<Paiement> implements Dao<Paiement> {

	@Override
	public void create(Paiement donnees) throws SQLException {
		RequeteInsertPaiement r = new RequeteInsertPaiement();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(Paiement donnees) throws SQLException {
		// Méthode non implémentée
	}

	@Override
	public void delete(Paiement donnees) throws SQLException {
		RequeteDeletePaiement r = new RequeteDeletePaiement();
		this.miseAJour(r, donnees);
	}

	@Override
	public Paiement findById(String... id) throws SQLException {
		RequeteSelectPaiementById r = new RequeteSelectPaiementById();
		return this.findById(r, id);
	}

	@Override
	public Collection<Paiement> findAll() throws SQLException {
		RequeteSelectPaiement r = new RequeteSelectPaiement();
		return this.find(r);
	}

	public Collection<Paiement> findAllByIdLoc(String idLoc) throws SQLException {
		RequeteSelectPaiementByIdLoc r = new RequeteSelectPaiementByIdLoc();
		return this.find(r, idLoc);
	}

	public Collection<Paiement> findAllByIdBien(String idBien) throws SQLException {
		RequeteSelectPaiementByIdBien r = new RequeteSelectPaiementByIdBien();
		return this.find(r, idBien);
	}

	public Collection<Paiement> findAllPaiementLoyerByIdBien(String idBien, String dateDebutBail, String dateFinBail)
			throws SQLException {
		RequeteSelectPaiementLoyerIdBienBetweenMonths r = new RequeteSelectPaiementLoyerIdBienBetweenMonths();
		return this.find(r, idBien, dateDebutBail, dateFinBail);
	}

	// Historique Solde Tout ou Historique Régularisation Chagre
	public Collection<Paiement> findAllPaiementByidBienEtLibelle(String idBien, String libelle) throws SQLException {
		RequeteSelectPaiementByIdBienEtLibelle r = new RequeteSelectPaiementByIdBienEtLibelle();
		return this.find(r, idBien, libelle);
	}

	public Paiement findByIdInterv(String idBien) throws SQLException {
		RequeteSelectPaiementByIdInterv r = new RequeteSelectPaiementByIdInterv();
		return this.findById(r, idBien);
	}

	public Paiement findByIdLocataireIdBienDate(String... id) throws SQLException {
		RequeteSelectPaiementByLocEtBienEtDate r = new RequeteSelectPaiementByLocEtBienEtDate();
		return this.findById(r, id);
	}

	public Paiement findByIdLocataireIdBien(String... id) throws SQLException {
		RequeteSelectPaiementByLocEtBien r = new RequeteSelectPaiementByLocEtBien();
		return this.findById(r, id);
	}

	@Override
	protected Paiement creerInstance(ResultSet curseur) throws SQLException {

		LocalDate moisConcerneLoyer = null;
		if (curseur.getDate("MOIS_CONCERNE") != null) {
			moisConcerneLoyer = curseur.getDate("MOIS_CONCERNE").toLocalDate();
		}

		return new Paiement(curseur.getString("ID_PAIEMENT"), curseur.getFloat("MONTANT"),
				curseur.getString("SENS_PAIEMENT"), curseur.getString("LIBELLE"),
				curseur.getDate("DATE_PAIEMENT").toLocalDate(), curseur.getString("ID_BIEN"),
				curseur.getString("ID_INTERVENTION"), moisConcerneLoyer);
	}
}
