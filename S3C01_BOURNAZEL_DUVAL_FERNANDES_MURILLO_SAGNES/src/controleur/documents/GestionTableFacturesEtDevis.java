package controleur.documents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.BienLouable;
import modele.Intervention;
import modele.Necessiter;
import modele.dao.DaoBienLouable;
import modele.dao.DaoIntervention;
import modele.dao.DaoNecessiterIntervention;
import modele.dao.DaoOrganisme;
import vue.documents.FenFacturesEtDevis;

// en cours
public class GestionTableFacturesEtDevis implements ListSelectionListener, DocumentListener, ActionListener {
	private FenFacturesEtDevis f;
	private DefaultTableModel modeleTableFacturesEtDevis;
	private DefaultTableModel modeleTableBienLouables;
	private DaoIntervention dao;
	private DaoNecessiterIntervention daoNI;
	private DaoBienLouable daoBL;
	private DaoOrganisme daoOrg;
	private List<Intervention> tableauIntervention;

	/**
	 * Initialise la classe de gestion de la fenetre des factures et devis
	 * 
	 * @param f fenetre contenant la liste des factures et devis
	 */
	public GestionTableFacturesEtDevis(FenFacturesEtDevis f) {
		this.f = f;
		modeleTableFacturesEtDevis = (DefaultTableModel) f.getTable().getModel();
		modeleTableBienLouables = (DefaultTableModel) f.getTableBienLouables().getModel();
		dao = new DaoIntervention();
		daoNI = new DaoNecessiterIntervention();
		daoBL = new DaoBienLouable();
		daoOrg = new DaoOrganisme();
		try {
			tableauIntervention = (List<Intervention>) dao.findAll();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int noLigne = f.getTable().getSelectedRow();
		if (noLigne < 0 || modeleTableFacturesEtDevis.getValueAt(noLigne, 0) == null) {
			return;
		}
		modeleTableBienLouables.setRowCount(0);
		try {
			String identifiantInterview = "";
			if (modeleTableFacturesEtDevis.getValueAt(noLigne, 0) != null) {
				identifiantInterview = modeleTableFacturesEtDevis.getValueAt(noLigne, 0).toString();
			}

			Collection<Necessiter> tabN = daoNI.findByIdIntervention(identifiantInterview);
			// "ID", "Date", "B\u00E2timent", "Intitul\u00E9", "Organisme", "Facture/Devis",
			// "Montant"
			if (tabN != null) {
				tabN = daoNI.findByIdIntervention(modeleTableFacturesEtDevis.getValueAt(noLigne, 0).toString());
			}
			for (Necessiter n : tabN) {
				BienLouable b = daoBL.findById(n.getIdBien());
				modeleTableBienLouables.addRow(new Object[] { b.getIdBien(), b.getAdresse(), b.getVille(),
						b.getCodePostal(), b.getTypeBien() });
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Remplit les combo box de valeurs permettant d'appliquer les filtres
	 */
	public void remplirComboBoxes() {
		f.getComboBoxBienLouable().addItem("tous");
		f.getComboBoxBatiment().addItem("tous");
		f.getComboBoFactureDevis().addItem("tous");
		try {
			Collection<Necessiter> tabNN = daoNI.findAll();
			for (Necessiter n : tabNN) {
				if (!Outils.comboContient(f.getComboBoxBienLouable(), n.getIdBien())) {
					f.getComboBoxBienLouable().addItem(n.getIdBien());
				}
			}
			for (Intervention i : tableauIntervention) {

				if (!Outils.comboContient(f.getComboBoxBatiment(), i.getIdBatiment())) {
					f.getComboBoxBatiment().addItem(i.getIdBatiment());
				}
				if (!Outils.comboContient(f.getComboBoFactureDevis(), i.getNumFacture()) && i.getNumFacture() != null
						&& !(i.getNumFacture().equals(""))) {
					f.getComboBoFactureDevis().addItem(i.getNumFacture());
				}

			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Permet d'appliquer les filtres choisis par l'utilisateur dans les champs et
	 * les combo box
	 */
	public void appliquerFiltres() {

		DefaultTableModel modeleTableIntervention = (DefaultTableModel) f.getTable().getModel();

		modeleTableIntervention.setRowCount(0);
		f.getTableBienLouables().clearSelection();
		modeleTableBienLouables.setRowCount(0);

		LocalDate ldDebut = Outils.parseDateField(f.getTextFieldDateDebut().getText());
		LocalDate ldFin = Outils.parseDateField(f.getTextFieldDateFin().getText());
		String filtreBL = this.setStringBL();
		String filtreBat = this.setStringBat();
		String filtreFac = this.setStringFac();

		// "Date d\u00E9but", "Date fin", "Bien louable", "Bâtiment", "Nom"

		for (Intervention i : tableauIntervention) {
			try {
				LocalDate interDate = i.getDateIntervention();
				// Trouver tous les biens liés à l'intervention
				List<Necessiter> tabN = daoNI.findByIdIntervention(i.getIdIntervention());

				List<String> listeS = new ArrayList<>();
				for (Necessiter n : tabN) {
					listeS.add(n.getIdBien());
				}

				boolean cond1 = (listeS.contains(filtreBL) || filtreBL.equals("tous"))
						&& (i.getIdBatiment().toLowerCase().contains(filtreBat) || filtreBat.equals("tous"));
				boolean cond2 = (i.getNumFacture() != null && i.getNumFacture().toLowerCase().contains(filtreFac)
						|| filtreFac.equals("tous"));
				boolean cond3 = (ldDebut == null || !interDate.isBefore(ldDebut));
				boolean cond4 = (ldFin == null || !interDate.isAfter(ldFin));

				if (cond1 && cond2 && cond3 && cond4) {
					modeleTableFacturesEtDevis.addRow(new Object[] { i.getIdIntervention(),
							Outils.formaterDateDDMMYYY(i.getDateIntervention()), i.getIdBatiment(), i.getIntitule(),
							daoOrg.findById(i.getIdOrganisme()).getNom(), i.getNumFacture(), i.getMontantFacture() });
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}

	/**
	 * Permet de réinitialiser le bien louable sélectionné en vidant la combo
	 * 
	 * @return un string vide
	 */
	private String setStringBL() {
		if (f.getComboBoxBienLouable().getSelectedItem() != null) {
			return f.getComboBoxBienLouable().getSelectedItem().toString().trim();
		}
		return "";
	}

	/**
	 * Permet de réinitialiser le batiment sélectionné en vidant la combo
	 * 
	 * @return un string vide
	 */
	private String setStringBat() {
		if (f.getComboBoxBatiment().getSelectedItem() != null) {
			return f.getComboBoxBatiment().getSelectedItem().toString().trim().toLowerCase();
		}
		return "";
	}

	/**
	 * Permet de réinitialiser la facture sélectionnée en vidant la combo
	 * 
	 * @return un string vide
	 */
	private String setStringFac() {
		if (f.getComboBoFactureDevis().getSelectedItem() != null) {
			return f.getComboBoFactureDevis().getSelectedItem().toString().trim().toLowerCase();
		}
		return "";
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
