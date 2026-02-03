package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

import modele.HistoriqueLoyer;
import modele.dao.requetes.historiqueloyer.SousProgrammeDerniersLoyerProvision;
import modele.dao.requetes.historiqueloyer.SousProgrammeTotalLoyerImpayesMoisActuel;
import modele.dao.requetes.historiqueloyer.SousProgrammeTotalLoyerMois;
import modele.dao.requetes.historiqueloyer.SousProgrammeTotalLoyerPayesMois;

/**
 * Classe permettant l'accès et la modification de données de type
 * HistoriqueLoyer
 */
public class DaoHistoriqueLoyer extends DaoModele<HistoriqueLoyer> implements Dao<HistoriqueLoyer> {

	@Override
	public void create(HistoriqueLoyer donnees) throws SQLException {
		// Méthode non implémentée
	}

	@Override
	public void update(HistoriqueLoyer donnees) throws SQLException {
		// Méthode non implémentée
	}

	@Override
	public void delete(HistoriqueLoyer donnees) throws SQLException {
		// Méthode non implémentée
	}

	@Override
	public HistoriqueLoyer findById(String... id) throws SQLException {
		// Méthode non implémentée
		return null;
	}

	public HistoriqueLoyer findByIdBienPremierDuMois(String idBien, String premierDuMois) throws SQLException {
		SousProgrammeDerniersLoyerProvision sp = new SousProgrammeDerniersLoyerProvision();
		return this.executerHistorique(sp, idBien, premierDuMois);
	}

	public Float findTotalLoyerImpayesMoisActuel() throws SQLException {
		SousProgrammeTotalLoyerImpayesMoisActuel sp = new SousProgrammeTotalLoyerImpayesMoisActuel();
		return this.executerFloat(sp);
	}

	public Float findTotalLoyerMoisActuel() throws SQLException {
		SousProgrammeTotalLoyerMois sp = new SousProgrammeTotalLoyerMois();
		return this.executerFloat(sp);
	}

	public Float findTotalLoyerPayesMoisActuel() throws SQLException {
		SousProgrammeTotalLoyerPayesMois sp = new SousProgrammeTotalLoyerPayesMois();
		return this.executerFloat(sp);
	}

	@Override
	public Collection<HistoriqueLoyer> findAll() throws SQLException {
		// Méthode non implémentée
		return Collections.emptyList();
	}

	@Override
	protected HistoriqueLoyer creerInstance(ResultSet curseur) throws SQLException {
		return new HistoriqueLoyer(curseur.getFloat("LOYER"), curseur.getFloat("PROVISION"),
				curseur.getString("ID_BIEN"), (curseur.getDate("MOIS_CONCERNE").toLocalDate()));
	}

}
