package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import modele.Charge;
import modele.dao.requetes.charge.RequeteSelectCharge;
import modele.dao.requetes.charge.RequeteSelectChargeById;
import modele.dao.requetes.charge.RequeteSelectChargeByIdBatDateDebutDateFin;
import modele.dao.requetes.charge.RequeteSelectChargeByIdBienDateDebutDateFin;
import modele.dao.requetes.charge.RequeteSelectChargeByIdInterventionDateDebutDateFin;

/**
 * Classe permettant l'accès et la modification de données de type Charge
 */
public class DaoCharge extends DaoModele<Charge> implements Dao<Charge> {

	@Override
	public void create(Charge donnees) throws SQLException {
		// Méthode non implémentée

	}

	@Override
	public void update(Charge donnees) throws SQLException {
		// Méthode non implémentée

	}

	@Override
	public void delete(Charge donnees) throws SQLException {
		// Méthode non implémentée
	}

	@Override
	public Charge findById(String... id) throws SQLException {
		RequeteSelectChargeById r = new RequeteSelectChargeById();
		return this.findById(r, id);
	}

	@Override
	public Collection<Charge> findAll() throws SQLException {
		RequeteSelectCharge r = new RequeteSelectCharge();
		return this.find(r);
	}

	public Collection<Charge> findAllByIdBienLouableDateDebutDateFin(String idBien, String dateDebut, String dateFin)
			throws SQLException {
		RequeteSelectChargeByIdBienDateDebutDateFin r = new RequeteSelectChargeByIdBienDateDebutDateFin();
		return this.find(r, idBien, dateDebut, dateFin);
	}

	public Collection<Charge> findAllByIdBatimentDateDebutDateFin(String idBat, String dateDebut, String dateFin)
			throws SQLException {
		RequeteSelectChargeByIdBatDateDebutDateFin r = new RequeteSelectChargeByIdBatDateDebutDateFin();
		return this.find(r, idBat, dateDebut, dateFin);
	}

	public Collection<Charge> findAllByIdInterventionDateDebutDateFin(String idBat, String dateDebut, String dateFin)
			throws SQLException {
		RequeteSelectChargeByIdInterventionDateDebutDateFin r = new RequeteSelectChargeByIdInterventionDateDebutDateFin();
		return this.find(r, idBat, dateDebut, dateFin);
	}

	@Override
	protected Charge creerInstance(ResultSet curseur) throws SQLException {
		LocalDate dateDebut = null;
		if (curseur.getDate("DATE_DEBUT_PERIODE") != null) {
			dateDebut = curseur.getDate("DATE_DEBUT_PERIODE").toLocalDate();
		}

		LocalDate dateFin = null;
		if (curseur.getDate("DATE_FIN_PERIODE") != null) {
			dateFin = curseur.getDate("DATE_FIN_PERIODE").toLocalDate();
		}

		return new Charge(curseur.getString("ID_CHARGE"), curseur.getString("LIBELLE"),
				curseur.getDate("DATE_FACTURATION").toLocalDate(), curseur.getInt("MONTANT"),
				curseur.getString("COMMENTAIRE"), curseur.getString("ID_BATIMENT"), curseur.getString("ID_BIEN"),
				dateDebut, dateFin);
	}
}
