package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import modele.IRL;
import modele.dao.requetes.irl.RequeteDeleteIRL;
import modele.dao.requetes.irl.RequeteInsertIRL;
import modele.dao.requetes.irl.RequeteSelectIRL;
import modele.dao.requetes.irl.RequeteSelectIRLById;

/**
 * Classe permettant l'accès et la modification de données de type IRL
 */
public class DaoIRL extends DaoModele<IRL> implements Dao<IRL> {

	@Override
	public void create(IRL donnees) throws SQLException {
		RequeteInsertIRL r = new RequeteInsertIRL();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(IRL donnees) throws SQLException {
		// Méthode non implémentée
	}

	@Override
	public void delete(IRL donnees) throws SQLException {
		RequeteDeleteIRL r = new RequeteDeleteIRL();
		this.miseAJour(r, donnees);
	}

	@Override
	public IRL findById(String... id) throws SQLException {
		RequeteSelectIRLById r = new RequeteSelectIRLById();
		return this.findById(r, id);
	}

	@Override
	public Collection<IRL> findAll() throws SQLException {
		RequeteSelectIRL r = new RequeteSelectIRL();
		return this.find(r);
	}

	@Override
	protected IRL creerInstance(ResultSet curseur) throws SQLException {
		return new IRL(curseur.getInt("ANNEE"), curseur.getInt("TRIMESTRE"), curseur.getDouble("VALEUR_IRL"));
	}

}
