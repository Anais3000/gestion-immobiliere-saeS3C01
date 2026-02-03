package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import modele.Louer;
import modele.dao.requetes.louer.RequeteInsertLouer;
import modele.dao.requetes.louer.RequeteSelectLouer;
import modele.dao.requetes.louer.RequeteSelectLouerActuel;
import modele.dao.requetes.louer.RequeteSelectLouerActuelByIdBienLouable;
import modele.dao.requetes.louer.RequeteSelectLouerByBienLouableEtLocataire;
import modele.dao.requetes.louer.RequeteSelectLouerById;
import modele.dao.requetes.louer.RequeteSelectLouerByIdBienLouable;
import modele.dao.requetes.louer.RequeteSelectLouerByIdBienLouableEtDateEntree;
import modele.dao.requetes.louer.RequeteSelectLouerByIdBienLouableEtDateEntreeEDL;
import modele.dao.requetes.louer.RequeteSelectLouerByIdBienLouableEtDateSortie;
import modele.dao.requetes.louer.RequeteSelectLouerByIdBienLouableEtDateSortieEDL;
import modele.dao.requetes.louer.RequeteSelectLouerByIdBienLouableNonRev;
import modele.dao.requetes.louer.RequeteSelectLouerByIdLocataire;
import modele.dao.requetes.louer.RequeteSelectLouerGaragesAssociesLogement;
import modele.dao.requetes.louer.RequeteUpdateAjustementLoyerLouer;
import modele.dao.requetes.louer.RequeteUpdateEtatLieuxEntreeLouer;
import modele.dao.requetes.louer.RequeteUpdateEtatLieuxSortieLouer;
import modele.dao.requetes.louer.RequeteUpdateLouer;
import modele.dao.requetes.louer.SousProgrammeRenouvelerAuto;
import modele.dao.requetes.resiliationbail.SousProgrammeTotalAjustementsRestants;
import modele.dao.requetes.resiliationbail.SousProgrammeTotalDuFinDeBail;
import modele.dao.requetes.resiliationbail.SousProgrammeTotalLoyersImpayes;

/**
 * Classe permettant l'accès et la modification de données de type Louer
 */
public class DaoLouer extends DaoModele<Louer> implements Dao<Louer> {

	@Override
	public void create(Louer donnees) throws SQLException {
		RequeteInsertLouer r = new RequeteInsertLouer();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(Louer donnees) throws SQLException {
		RequeteUpdateLouer r = new RequeteUpdateLouer();
		this.miseAJour(r, donnees);
	}

	// Update l'etat des lieux entree, on doit d'abord creer un objet Louer avec les
	// nouvelles valeurs
	public void updateEtatLieuxEntree(Louer donnees) throws SQLException {
		RequeteUpdateEtatLieuxEntreeLouer r = new RequeteUpdateEtatLieuxEntreeLouer();
		this.miseAJour(r, donnees);
	}

	// Update l'etat des lieux sortie, on doit d'abord creer un objet Louer avec les
	// nouvelles valeurs
	public void updateEtatLieuxSortie(Louer donnees) throws SQLException {
		RequeteUpdateEtatLieuxSortieLouer r = new RequeteUpdateEtatLieuxSortieLouer();
		this.miseAJour(r, donnees);
	}

	public void updateAjustementLoyer(Louer donnees) throws SQLException {
		RequeteUpdateAjustementLoyerLouer r = new RequeteUpdateAjustementLoyerLouer();
		this.miseAJour(r, donnees);
	}

	@Override
	public void delete(Louer donnees) throws SQLException {
		// Méthode non implémentée
	}

	@Override
	public Louer findById(String... id) throws SQLException {
		RequeteSelectLouerById r = new RequeteSelectLouerById();
		return this.findById(r, id);
	}

	public Louer findByIdBienEtIdLocataire(String... id) throws SQLException {
		RequeteSelectLouerByBienLouableEtLocataire r = new RequeteSelectLouerByBienLouableEtLocataire();
		return this.findById(r, id);
	}

	public Louer findByIdBienLouable(String... id) throws SQLException {
		RequeteSelectLouerByIdBienLouableNonRev r = new RequeteSelectLouerByIdBienLouableNonRev();
		return this.findById(r, id);
	}

	public Louer findByIdBienLouableEtDateEntree(String... id) throws SQLException {
		RequeteSelectLouerByIdBienLouableEtDateEntree r = new RequeteSelectLouerByIdBienLouableEtDateEntree();
		return this.findById(r, id);
	}

	public Louer findByIdBienLouableEtDateEntreeEDL(String... id) throws SQLException {
		RequeteSelectLouerByIdBienLouableEtDateEntreeEDL r = new RequeteSelectLouerByIdBienLouableEtDateEntreeEDL();
		return this.findById(r, id);
	}

	public Louer findByIdBienLouableEtDateSortie(String... id) throws SQLException {
		RequeteSelectLouerByIdBienLouableEtDateSortie r = new RequeteSelectLouerByIdBienLouableEtDateSortie();
		return this.findById(r, id);
	}

	public Louer findByIdBienLouableEtDateSortieEDL(String... id) throws SQLException {
		RequeteSelectLouerByIdBienLouableEtDateSortieEDL r = new RequeteSelectLouerByIdBienLouableEtDateSortieEDL();
		return this.findById(r, id);
	}

	public Louer findByIdBienLouableActuel(String... id) throws SQLException {
		RequeteSelectLouerActuelByIdBienLouable r = new RequeteSelectLouerActuelByIdBienLouable();
		return this.findById(r, id);
	}

	public Float totalLoyersImpayes(String... id) throws SQLException {
		SousProgrammeTotalLoyersImpayes s = new SousProgrammeTotalLoyersImpayes();
		return this.executer(s, id);
	}

	public Float totalAjustementsRestants(String... id) throws SQLException {
		SousProgrammeTotalAjustementsRestants s = new SousProgrammeTotalAjustementsRestants();
		return this.executer(s, id);
	}

	public Float totalDuFinDeBail(String... id) throws SQLException {
		SousProgrammeTotalDuFinDeBail s = new SousProgrammeTotalDuFinDeBail();
		return this.executer(s, id);
	}

	public boolean renouvelerBauxAutomatique() throws SQLException {
		SousProgrammeRenouvelerAuto s = new SousProgrammeRenouvelerAuto();
		return this.executer(s);
	}

	@Override
	public Collection<Louer> findAll() throws SQLException {
		RequeteSelectLouer r = new RequeteSelectLouer();
		return this.find(r);
	}

	public Collection<Louer> findAllActuel() throws SQLException {
		RequeteSelectLouerActuel r = new RequeteSelectLouerActuel();
		return this.find(r);
	}

	public Collection<Louer> findAllByIdLoc(String idLoc) throws SQLException {
		RequeteSelectLouerByIdLocataire r = new RequeteSelectLouerByIdLocataire();
		return this.find(r, idLoc);
	}

	public Collection<Louer> findAllByIdBien(String idBien) throws SQLException {
		RequeteSelectLouerByIdBienLouable r = new RequeteSelectLouerByIdBienLouable();
		return this.find(r, idBien);
	}

	public Collection<Louer> findAllLouerGaragesAssociesLogement(String... id) throws SQLException {
		RequeteSelectLouerGaragesAssociesLogement r = new RequeteSelectLouerGaragesAssociesLogement();
		return this.find(r, id);
	}

	@Override
	protected Louer creerInstance(ResultSet curseur) throws SQLException {
		LocalDate dateDebut = null;
		LocalDate dateFin = null;
		LocalDate dateEtatLieuxEntree = null;
		LocalDate dateEtatLieuxSortie = null;
		LocalDate dateDerniereRegul = null;
		LocalDate moisFinAjustement = null;
		LocalDate moisDebutAjustement = null;

		if (curseur.getDate("DATE_DEBUT") != null) {
			dateDebut = curseur.getDate("DATE_DEBUT").toLocalDate();
		}
		if (curseur.getDate("DATE_FIN") != null) {
			dateFin = curseur.getDate("DATE_FIN").toLocalDate();
		}
		if (curseur.getDate("DATE_ETAT_DES_LIEUX_ENTREE") != null) {
			dateEtatLieuxEntree = curseur.getDate("DATE_ETAT_DES_LIEUX_ENTREE").toLocalDate();
		}
		if (curseur.getDate("DATE_ETAT_DES_LIEUX_SORTIE") != null) {
			dateEtatLieuxSortie = curseur.getDate("DATE_ETAT_DES_LIEUX_SORTIE").toLocalDate();
		}
		if (curseur.getDate("DATE_DERNIERE_REGUL") != null) {
			dateDerniereRegul = curseur.getDate("DATE_DERNIERE_REGUL").toLocalDate();
		}
		if (curseur.getDate("MOIS_FIN_AJUSTEMENT") != null) {
			moisFinAjustement = curseur.getDate("MOIS_FIN_AJUSTEMENT").toLocalDate();
		}
		if (curseur.getDate("MOIS_DEBUT_AJUSTEMENT") != null) {
			moisDebutAjustement = curseur.getDate("MOIS_DEBUT_AJUSTEMENT").toLocalDate();
		}

		return new Louer(dateDebut, dateFin, curseur.getFloat("DEPOT_DE_GARANTIE"), dateEtatLieuxEntree,
				dateEtatLieuxSortie, curseur.getString("DETAILS_ETAT_DES_LIEUX_ENTREE"),
				curseur.getString("DETAILS_ETAT_DES_LIEUX_SORTIE"), curseur.getString("ID_LOCATAIRE"),
				curseur.getString("ID_BIEN"), curseur.getString("ID_GARANT"), dateDerniereRegul,
				curseur.getFloat("AJUSTEMENT_LOYER"), moisFinAjustement, curseur.getString("ID_LOGEMENT_ASSOCIE"),
				curseur.getInt("REVOLUE"), moisDebutAjustement);
	}

}
