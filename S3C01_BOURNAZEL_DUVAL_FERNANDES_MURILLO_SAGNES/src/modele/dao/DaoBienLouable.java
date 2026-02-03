package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import modele.BienLouable;
import modele.dao.requetes.bienlouable.RequeteInsertBienLouable;
import modele.dao.requetes.bienlouable.RequeteSelectBienLouable;
import modele.dao.requetes.bienlouable.RequeteSelectBienLouableById;
import modele.dao.requetes.bienlouable.RequeteSelectBienLouableByIdBat;
import modele.dao.requetes.bienlouable.RequeteSelectGarageNonLoue;
import modele.dao.requetes.bienlouable.RequeteUpdateBienLouable;
import modele.dao.requetes.bienlouable.SousProgammeSupprimerBienLouable;
import modele.dao.requetes.bienlouable.SousProgrammeTotalLoyersPercusParBienEtAnnee;
import modele.dao.requetes.bienlouable.SousProgrammeTotalProvisionsPercuesParBienEtAnnee;

/**
 * Classe permettant l'accès et la modification de données de type BienLouable
 */
public class DaoBienLouable extends DaoModele<BienLouable> implements Dao<BienLouable> {

	@Override
	public void create(BienLouable donnees) throws SQLException {
		RequeteInsertBienLouable r = new RequeteInsertBienLouable();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(BienLouable donnees) throws SQLException {
		RequeteUpdateBienLouable r = new RequeteUpdateBienLouable();
		this.miseAJour(r, donnees);

	}

	@Override
	public void delete(BienLouable donnees) throws SQLException {
		SousProgammeSupprimerBienLouable r = new SousProgammeSupprimerBienLouable();
		this.executer(r, donnees);

	}

	@Override
	public BienLouable findById(String... id) throws SQLException {
		RequeteSelectBienLouableById r = new RequeteSelectBienLouableById();
		return this.findById(r, id);
	}

	@Override
	public Collection<BienLouable> findAll() throws SQLException {
		RequeteSelectBienLouable r = new RequeteSelectBienLouable();
		return this.find(r);
	}

	public Collection<BienLouable> findGaragesNonLoues() throws SQLException {
		RequeteSelectGarageNonLoue r = new RequeteSelectGarageNonLoue();
		return this.find(r);
	}

	public Collection<BienLouable> findAllByIdBat(String idBat) throws SQLException {
		RequeteSelectBienLouableByIdBat r = new RequeteSelectBienLouableByIdBat();
		return this.find(r, idBat);
	}

	@Override
	protected BienLouable creerInstance(ResultSet curseur) throws SQLException {
		return new BienLouable(curseur.getString("ID_BIEN"), curseur.getString("ID_BATIMENT"),
				curseur.getString("ADRESSE"), curseur.getString("CODE_POSTAL"), curseur.getString("VILLE"),
				curseur.getFloat("LOYER"), curseur.getFloat("PROVISION_POUR_CHARGES"), curseur.getString("TYPE_BIEN"),
				curseur.getDate("DATE_CONSTRUCTION").toLocalDate(), curseur.getFloat("SURFACE_HABITABLE"),
				curseur.getInt("NB_PIECES"), curseur.getDouble("POURCENTAGE_ENTRETIEN_PARTIES_COMMUNES"),
				curseur.getDouble("POURCENTAGE_ORDURES_MENAGERES"), curseur.getString("NUMERO_FISCAL"),
				curseur.getInt("DERNIERE_ANNEE_MODIF_LOYER"));
	}

	// bien (String) puis année (int)
	public Float totalLoyersPercusParBienEtAnnee(String... id) throws SQLException {
		SousProgrammeTotalLoyersPercusParBienEtAnnee s = new SousProgrammeTotalLoyersPercusParBienEtAnnee();
		return this.executer(s, id);
	}

	public Float totalProvisionsPercuesParBienEtAnnee(String... id) throws SQLException {
		SousProgrammeTotalProvisionsPercuesParBienEtAnnee s = new SousProgrammeTotalProvisionsPercuesParBienEtAnnee();
		return this.executer(s, id);
	}

}
