package controleur.tables.fen_garant;

import java.sql.SQLException;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Garant;
import modele.dao.DaoGarant;
import vue.tables.FenGarant;

public class GestionTableFenGarant implements DocumentListener, ListSelectionListener {
	private FenGarant f;
	private DefaultTableModel modeleTableGarant;
	private Collection<Garant> garants;
	private DaoGarant daoG;

	/**
	 * Initialise la classe permettant la gestion du tableau général des garants
	 * 
	 * @param f fenetre des garants contenant le tableau
	 */
	public GestionTableFenGarant(FenGarant f) {
		this.f = f;
		this.modeleTableGarant = f.getModeleTableGarant();
		this.daoG = new DaoGarant();
		try {
			garants = daoG.findAll();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Remet à 0 la table des garants puis la remplit en fonction des données de la
	 * BD
	 */
	public void appliquerFiltres() {

		modeleTableGarant.setRowCount(0);

		String filtreId = f.getTextFieldID().getText().trim().toLowerCase();

		for (Garant g : garants) {
			String idGarant = g.getIdGarant().trim().toLowerCase();
			if (idGarant.contains(filtreId)) {
				// la table : "ID", "Adresse", "Mail", "T\u00E9l\u00E9phone"
				modeleTableGarant.addRow(new Object[] { g.getIdGarant(), g.getAdresse(), g.getMail(), g.getTel() });
			}
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		appliquerFiltres();

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		appliquerFiltres();

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		appliquerFiltres();

	}

	/**
	 * Permet de recharger la liste des garants en récupérant une nouvelle fois la
	 * liste des garants depuis la BD, pour la mettre à jour
	 */
	public void rechargerGarants() {
		try {
			garants = daoG.findAll();
			appliquerFiltres();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int selectedRow = this.f.getTableGarant().getSelectedRow();
		if (selectedRow < 0 || selectedRow >= modeleTableGarant.getRowCount()) {
			return;
		}
		f.getBtnSupprimer().setEnabled(true);
	}

}
