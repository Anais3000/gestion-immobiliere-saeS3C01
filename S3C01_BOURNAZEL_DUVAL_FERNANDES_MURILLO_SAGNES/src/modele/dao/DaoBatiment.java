package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import modele.Batiment;
import modele.dao.requetes.batiment.RequeteInsertBatiment;
import modele.dao.requetes.batiment.RequeteSelectBatiment;
import modele.dao.requetes.batiment.RequeteSelectBatimentById;
import modele.dao.requetes.batiment.RequeteUpdateBatiment;
import modele.dao.requetes.batiment.SousProgrammeSupprimerBatiment;
import modele.dao.requetes.batiment.SousProgrammeTotalAssuranceBatAnnee;
import modele.dao.requetes.batiment.SousProgrammeTotalOrduresBatimentSurAnnee;

/**
 * Classe permettant l'accès et la modification de données de type Batiment
 */
public class DaoBatiment extends DaoModele<Batiment> implements Dao<Batiment> {

	@Override
	public void create(Batiment donnees) throws SQLException {
		RequeteInsertBatiment r = new RequeteInsertBatiment();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(Batiment donnees) throws SQLException {
		RequeteUpdateBatiment r = new RequeteUpdateBatiment();
		this.miseAJour(r, donnees);
	}

	@Override
	public void delete(Batiment donnees) throws SQLException {
		SousProgrammeSupprimerBatiment s = new SousProgrammeSupprimerBatiment();
		this.executer(s, donnees);
	}

	@Override
	public Batiment findById(String... id) throws SQLException {
		RequeteSelectBatimentById r = new RequeteSelectBatimentById();
		return this.findById(r, id);
	}

	@Override
	public Collection<Batiment> findAll() throws SQLException {
		RequeteSelectBatiment r = new RequeteSelectBatiment();
		return this.find(r);
	}

	public Float totalOrduresBatimentSurAnnee(String... id) throws SQLException {
		SousProgrammeTotalOrduresBatimentSurAnnee s = new SousProgrammeTotalOrduresBatimentSurAnnee();
		return this.executer(s, id);
	}

	public Float totalAssuranceBatAnnee(String... id) throws SQLException {
		SousProgrammeTotalAssuranceBatAnnee s = new SousProgrammeTotalAssuranceBatAnnee();
		return this.executer(s, id);
	}

	@Override
	protected Batiment creerInstance(ResultSet curseur) throws SQLException {
		return new Batiment(curseur.getString("ID_BATIMENT"), curseur.getDate("DATE_CONSTRUCTION").toLocalDate(),
				curseur.getString("ADRESSE"), curseur.getString("CODE_POSTAL"), curseur.getString("VILLE"),
				curseur.getString("NUMERO_FISCAL_BAT"));
	}

}
