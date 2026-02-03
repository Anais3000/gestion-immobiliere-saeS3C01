package controleur.documents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Diagnostic;
import modele.dao.DaoBienLouable;
import modele.dao.DaoDiagnostic;
import vue.documents.FenDiagnostics;

public class GestionTableDiagnostics implements ListSelectionListener, DocumentListener, ActionListener {
	private FenDiagnostics f;
	private DefaultTableModel modeleTableFenDiagnostics;
	private DaoDiagnostic dao;
	private DaoBienLouable daoBL;
	private List<Diagnostic> tableauDiagnostics;

	/**
	 * Initialise la classe de gestion de la fenetre des diagnostics
	 * 
	 * @param f fenetre contenant la liste des diagnostics
	 */
	public GestionTableDiagnostics(FenDiagnostics f) {
		this.f = f;
		modeleTableFenDiagnostics = f.getGestionTableModel();
		dao = new DaoDiagnostic();
		daoBL = new DaoBienLouable();
		try {
			tableauDiagnostics = (List<Diagnostic>) dao.findAll();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int noLigne = f.getTable().getSelectedRow();
		if (noLigne == -1) {
			return;
		}
		Diagnostic b;
		try {
			b = dao.findById(modeleTableFenDiagnostics.getValueAt(noLigne, 4).toString(),
					Outils.formaterDateYYYYMMDD(modeleTableFenDiagnostics.getValueAt(noLigne, 0).toString()),
					modeleTableFenDiagnostics.getValueAt(noLigne, 2).toString());
			f.getTxtrDetails().setText("par défaut");
			f.getTxtrDetails().setText(b.getDetails());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Remplit les combo box des valeurs permettant d'appliquer des filtres
	 */
	public void remplirComboboxes() {
		f.getComboBoxBienLouable().addItem("tous");
		f.getComboBoxBatiment().addItem("tous");
		try {
			for (Diagnostic d : tableauDiagnostics) {
				String idBat = daoBL.findById(d.getIdBien()).getIdBat();
				if (!Outils.comboContient(f.getComboBoxBienLouable(), d.getIdBien())) {
					f.getComboBoxBienLouable().addItem(d.getIdBien());
				}
				if (!Outils.comboContient(f.getComboBoxBatiment(), idBat)) {
					f.getComboBoxBatiment().addItem(idBat);
				}
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Applique les filtres choisis par l'utilisateur dans les champs et les combo
	 * box
	 * 
	 * @param diags liste des diagnostics sur lesquels sont appliqués les filtres
	 */
	public void appliquerFiltres(Collection<Diagnostic> diags) {
		DefaultTableModel modeleTableDiagnostic = (DefaultTableModel) f.getTable().getModel();
		modeleTableDiagnostic.setRowCount(0);

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
		String filtreNom = f.getTextFieldNom().getText().trim().toLowerCase();

		// "Date d\u00E9but", "Date fin", "Bien louable", "Bâtiment", "Nom"

		for (Diagnostic d : diags) {
			try {
				LocalDate diagDateDeb = d.getDateDebut();
				LocalDate diagDateFin = d.getDateFin();
				String idBat;

				idBat = daoBL.findById(d.getIdBien()).getIdBat().toLowerCase();

				if ((d.getIdBien().toLowerCase().contains(filtreBL) || filtreBL.equals("tous"))
						&& (idBat.contains(filtreBat) || filtreBat.equals("tous"))
						&& d.getLibelle().toLowerCase().startsWith(filtreNom)
						&& (ldDebut == null || !diagDateDeb.isBefore(ldDebut))
						&& (ldFin == null || !diagDateFin.isAfter(ldFin))) {
					modeleTableFenDiagnostics.addRow(new Object[] { Outils.formaterDateDDMMYYY(d.getDateDebut()),
							Outils.formaterDateDDMMYYY(d.getDateFin()), d.getIdBien(), idBat, d.getLibelle() });
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
		appliquerFiltres(tableauDiagnostics);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		appliquerFiltres(tableauDiagnostics);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		appliquerFiltres(tableauDiagnostics);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		appliquerFiltres(tableauDiagnostics);
	}
}