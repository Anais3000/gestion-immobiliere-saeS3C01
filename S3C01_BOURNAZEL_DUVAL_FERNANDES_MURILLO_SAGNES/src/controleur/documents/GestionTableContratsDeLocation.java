package controleur.documents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Louer;
import modele.dao.DaoBienLouable;
import modele.dao.DaoLouer;
import vue.documents.FenContratsDeLocation;

public class GestionTableContratsDeLocation implements ListSelectionListener, DocumentListener, ActionListener {
	private FenContratsDeLocation f;
	private DefaultTableModel modeleTableContratsDeLocation;
	private DaoLouer dao;
	private DaoBienLouable daoBL;
	private List<Louer> tableauContratsDeLocation;

	/**
	 * Initialise la classe de gestion de la fenetre des contrats de location
	 * 
	 * @param f fenetre contenant la liste des contrats de location
	 */
	public GestionTableContratsDeLocation(FenContratsDeLocation f) {
		this.f = f;
		modeleTableContratsDeLocation = (DefaultTableModel) f.getTable().getModel();
		dao = new DaoLouer();
		daoBL = new DaoBienLouable();
		try {
			tableauContratsDeLocation = (List<Louer>) dao.findAll();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Les méthodes utilisées sont ci-dessous
	}

	/**
	 * Remplit les combo box permettant d'appliquer les filtres
	 */
	public void remplirComboBoxes() {
		f.getComboBoxBatiment().addItem("tous");
		f.getComboBoxBienLouable().addItem("tous");
		try {
			for (Louer l : tableauContratsDeLocation) {
				String idBat = daoBL.findById(l.getIdBienLouable()).getIdBat();
				if (!Outils.comboContient(f.getComboBoxBatiment(), idBat)) {
					f.getComboBoxBatiment().addItem(idBat);
				}
				if (!Outils.comboContient(f.getComboBoxBienLouable(), l.getIdBienLouable())) {
					f.getComboBoxBienLouable().addItem(l.getIdBienLouable());
				}
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Applique les filtres choisis dans les champs et les combo box
	 */
	public void appliquerFiltres() {
		DefaultTableModel modeleTableDiagnostic = (DefaultTableModel) f.getTable().getModel();
		while (modeleTableDiagnostic.getRowCount() > 0) {
			modeleTableDiagnostic.removeRow(0);
		}

		LocalDate ldDebut = Outils.parseDateField(f.getTextFieldDateDebut().getText());
		LocalDate ldFin = Outils.parseDateField(f.getTextFieldDateFin().getText());
		String filtreBL = "";
		String filtreBat = "";
		if (f.getComboBoxBienLouable().getSelectedItem() != null) {
			filtreBL = f.getComboBoxBienLouable().getSelectedItem().toString().trim().toLowerCase();
		}
		if (f.getComboBoxBatiment().getSelectedItem() != null) {
			filtreBat = f.getComboBoxBatiment().getSelectedItem().toString().trim().toLowerCase();
		}
		String filtreLoc = f.getTextFieldLocataire().getText().trim().toLowerCase();

		// "Date d\u00E9but", "Date fin", "Bien louable", "Bâtiment", "Nom"

		for (Louer l : tableauContratsDeLocation) {
			try {
				LocalDate diagDateDeb = l.getDateDebut();
				LocalDate diagDateFin = l.getDateFin();
				String idBat;

				idBat = daoBL.findById(l.getIdBienLouable()).getIdBat().toLowerCase();

				if ((l.getIdBienLouable().toLowerCase().contains(filtreBL) || filtreBL.equals("tous"))
						&& (idBat.contains(filtreBat) || filtreBat.equals("tous"))
						&& l.getIdLocataire().toLowerCase().startsWith(filtreLoc)
						&& (ldDebut == null || !diagDateDeb.isBefore(ldDebut))
						&& (ldFin == null || !diagDateFin.isAfter(ldFin))) {
					modeleTableContratsDeLocation.addRow(new Object[] { Outils.formaterDateDDMMYYY(l.getDateDebut()),
							Outils.formaterDateDDMMYYY(l.getDateFin()), idBat, l.getIdBienLouable(), l.getIdLocataire(),
							l.getDepotDeGarantie() });
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		appliquerFiltres();
	}
}
