package controleur.documents;

import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Assurance;
import modele.dao.DaoAssurance;
import vue.documents.FenContratsAssurance;

public class GestionTableAssurance implements ListSelectionListener {

	private FenContratsAssurance assu;

	private DaoAssurance daoAssurance;

	private DefaultTableModel modelTableAss;

	/**
	 * Initialise la classe de gestion de la fenetre des contrats d'assurance
	 * 
	 * @param assu fenetre contenant la liste des contrats d'assurance
	 */
	public GestionTableAssurance(FenContratsAssurance assu) {
		this.assu = assu;
		this.daoAssurance = new DaoAssurance();
		this.modelTableAss = (DefaultTableModel) assu.getTable().getModel();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Seules les methodes ci-dessous sont utilisées
	}

	/**
	 * Met la table des contrats d'assurance à 0 puis la remplit
	 * 
	 * @param table table contenant les contrats d'assurance
	 */
	public static void viderTable(JTable table) {
		try {
			DefaultTableModel modeleTable = (DefaultTableModel) table.getModel();
			for (int i = 0; i < modeleTable.getRowCount(); i++) {
				for (int y = 0; y < modeleTable.getColumnCount(); y++) {
					modeleTable.setValueAt(null, i, y);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Remplit les combobox de filtres
	 */
	public void remplirComboBoxes() {
		List<Assurance> tableauAss;
		try {
			modelTableAss.setRowCount(0);
			tableauAss = (List<Assurance>) daoAssurance.findAll();
			assu.getComboBoxBat().addItem("Tous");
			assu.getComboBoxContrat().addItem("Tous");
			for (Assurance a : tableauAss) {
				if (!Outils.comboContient(assu.getComboBoxBat(), a.getBat())) {
					assu.getComboBoxBat().addItem(a.getBat());
				}
				if (!Outils.comboContient(assu.getComboBoxContrat(), a.getTypeContrat())) {
					assu.getComboBoxContrat().addItem(a.getTypeContrat());
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Applique les filtres sélectionnés par l'utilisateur
	 * 
	 * @param assurances liste des assurances sur lesquelles les filtres sont
	 *                   appliqués
	 */
	public void appliquerFiltres(Collection<Assurance> assurances) {
		DefaultTableModel modeleTableAssurance = assu.getModeleTableAssurance();
		while (modeleTableAssurance.getRowCount() > 0) {
			modeleTableAssurance.removeRow(0);
		}
		String filtreBat = "";
		String filtreContrat = "";
		if (assu.getComboBoxBat().getSelectedItem() != null) {
			filtreBat = assu.getComboBoxBat().getSelectedItem().toString();
		}
		if (assu.getComboBoxContrat().getSelectedItem() != null) {
			filtreContrat = assu.getComboBoxContrat().getSelectedItem().toString();
		}
		String filtreAnnee = assu.getTextFieldAnnee().getText().trim();
		for (Assurance a : assurances) {
			if (((filtreAnnee == null) || (String.valueOf(a.getAnneeCouverture()).startsWith(filtreAnnee)))
					&& (filtreBat.equals("Tous") || a.getBat().equals(filtreBat))
					&& (filtreContrat.equals("Tous") || a.getTypeContrat().equals(filtreContrat))) {
				modeleTableAssurance.addRow(
						new Object[] { a.getAnneeCouverture(), a.getBat(), a.getMontantPaye(), a.getTypeContrat() });
			}
		}
	}

	/**
	 * Active la gestion automatique des filtres dès qu'une valeur change
	 * 
	 * @param assurances liste des assurances sur lequelles sont appliqués les
	 *                   filtres
	 */
	public void activerFiltresAutomatiques(Collection<Assurance> assurances) {

		DocumentListener dl = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				appliquerFiltres(assurances);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				appliquerFiltres(assurances);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				appliquerFiltres(assurances);
			}
		};

		// Champs texte
		assu.getTextFieldAnnee().getDocument().addDocumentListener(dl);
		assu.getComboBoxBat().addActionListener(e -> appliquerFiltres(assurances));
		assu.getComboBoxContrat().addActionListener(e -> appliquerFiltres(assurances));
	}
}
