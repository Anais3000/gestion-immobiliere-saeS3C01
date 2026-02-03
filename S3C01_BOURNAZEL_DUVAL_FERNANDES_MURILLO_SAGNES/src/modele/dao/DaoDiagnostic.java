package modele.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import modele.Diagnostic;
import modele.dao.requetes.diagnostic.RequeteDeleteDiagnostic;
import modele.dao.requetes.diagnostic.RequeteInsertDiagnostic;
import modele.dao.requetes.diagnostic.RequeteSelectDiagnostic;
import modele.dao.requetes.diagnostic.RequeteSelectDiagnosticById;
import modele.dao.requetes.diagnostic.RequeteSelectDiagnosticByIdBien;

/**
 * Classe permettant l'accès et la modification de données de type Diagnostic
 */
public class DaoDiagnostic extends DaoModele<Diagnostic> implements Dao<Diagnostic> {

	@Override
	public void create(Diagnostic donnees) throws SQLException {
		RequeteInsertDiagnostic r = new RequeteInsertDiagnostic();
		this.miseAJour(r, donnees);
	}

	@Override
	public void update(Diagnostic donnees) throws SQLException {
		// Méthode non implémentée
	}

	@Override
	public void delete(Diagnostic donnees) throws SQLException {
		RequeteDeleteDiagnostic r = new RequeteDeleteDiagnostic();
		this.miseAJour(r, donnees);
	}

	@Override
	public Diagnostic findById(String... id) throws SQLException {
		RequeteSelectDiagnosticById r = new RequeteSelectDiagnosticById();
		return this.findById(r, id);
	}

	@Override
	public Collection<Diagnostic> findAll() throws SQLException {
		RequeteSelectDiagnostic r = new RequeteSelectDiagnostic();
		return this.find(r);
	}

	public Collection<Diagnostic> findAllByIdBien(String idBien) throws SQLException {
		RequeteSelectDiagnosticByIdBien r = new RequeteSelectDiagnosticByIdBien();
		return this.find(r, idBien);
	}

	@Override
	protected Diagnostic creerInstance(ResultSet curseur) throws SQLException {
		return new Diagnostic(curseur.getString("LIBELLE"), curseur.getDate("DATE_DEBUT").toLocalDate(),
				curseur.getDate("DATE_FIN").toLocalDate(), curseur.getString("DETAILS"), curseur.getString("ID_BIEN"));
	}

}
