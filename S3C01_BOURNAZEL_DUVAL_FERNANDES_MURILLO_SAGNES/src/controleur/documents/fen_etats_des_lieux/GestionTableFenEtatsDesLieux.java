package controleur.documents.fen_etats_des_lieux;

import java.sql.SQLException;
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
import modele.Louer;
import modele.dao.DaoBienLouable;
import modele.dao.DaoLocataire;
import modele.dao.DaoLouer;
import vue.documents.FenEtatsDesLieux;

public class GestionTableFenEtatsDesLieux implements ListSelectionListener {
	private FenEtatsDesLieux fEDL;
	private DaoLouer daoLouer;
	private DefaultTableModel modeleTableFenEtatsDesLieux;
	private DaoBienLouable daoBl;
	private DaoLocataire daoLoc;

	private static final String ENTREE = "Entrée";
	private static final String SORTIE = "Sortie";

	/**
	 * Initialise la classe de gestion de la table contenant les états des lieux
	 * dans la fenetre des états des lieux
	 * 
	 * @param fEDL fenetre permettant de voir les états des lieux
	 */
	public GestionTableFenEtatsDesLieux(FenEtatsDesLieux fEDL) {
		this.fEDL = fEDL;
		this.daoLouer = new DaoLouer();
		this.daoBl = new DaoBienLouable();
		this.modeleTableFenEtatsDesLieux = (DefaultTableModel) fEDL.getTableEtatsDesLieux().getModel();
		this.daoLoc = new DaoLocataire();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int noLigne = fEDL.getTableEtatsDesLieux().getSelectedRow();
		if (noLigne == -1) {
			return;
		}
		Louer l = null;
		try {

			String dateEtat = modeleTableFenEtatsDesLieux.getValueAt(noLigne, 1).toString();
			String typeEtat = modeleTableFenEtatsDesLieux.getValueAt(noLigne, 3).toString();
			String idBien = modeleTableFenEtatsDesLieux.getValueAt(noLigne, 2).toString();

			if (typeEtat.equals(ENTREE)) {
				l = daoLouer.findByIdBienLouableEtDateEntree(idBien, dateEtat);
				if (l == null) {
					return;
				}
				fEDL.getTxtrDetails().setText(l.getDetailsEtatDesLieuxEntree());
			} else if (typeEtat.equals(SORTIE)) {
				l = daoLouer.findByIdBienLouableEtDateSortie(idBien, dateEtat);
				if (l == null) {
					return;
				}
				fEDL.getTxtrDetails().setText(l.getDetailsEtatDesLieuxSortie());
			}

		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Permet de vider la table des états des lieux
	 * 
	 * @param table table à vider
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
	 * Remplit les combo box contenant des valeurs pour appliquer des filtres
	 */
	public void remplirComboBoxes() {
		List<Louer> tableauEtatsDesLieux;
		try {
			modeleTableFenEtatsDesLieux.setRowCount(0);
			tableauEtatsDesLieux = (List<Louer>) daoLouer.findAll();
			fEDL.getComboBoxBatiment().addItem("Tous");
			fEDL.getComboBoxBienLouable().addItem("Tous");
			for (Louer l : tableauEtatsDesLieux) {
				if (!Outils.comboContient(fEDL.getComboBoxBatiment(),
						daoBl.findById(l.getIdBienLouable()).getIdBat())) {
					fEDL.getComboBoxBatiment().addItem(daoBl.findById(l.getIdBienLouable()).getIdBat());
				}
				if (!Outils.comboContient(fEDL.getComboBoxBienLouable(), l.getIdBienLouable())) {
					fEDL.getComboBoxBienLouable().addItem(daoBl.findById(l.getIdBienLouable()).getIdBien());
				}
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Permet d'appliquer les filtres choisis par l'utilisateur dans les combo box
	 * et les text fields
	 * 
	 * @param etatsDesLieux liste des objets de type Louer (contenant les états des
	 *                      lieux) sur lesquels appliquer les filtres
	 */
	public void appliquerFiltres(Collection<Louer> etatsDesLieux) {
		while (modeleTableFenEtatsDesLieux.getRowCount() > 0) {
			modeleTableFenEtatsDesLieux.removeRow(0);
		}
		String filtreBat = "";
		String filtreBL = "";
		String filtreES = "";
		if (fEDL.getComboBoxBatiment().getSelectedItem() != null) {
			filtreBat = fEDL.getComboBoxBatiment().getSelectedItem().toString();
		}
		if (fEDL.getComboBoxBienLouable().getSelectedItem() != null) {
			filtreBL = fEDL.getComboBoxBienLouable().getSelectedItem().toString();
		}
		if (fEDL.getComboBoxEntreeSortie().getSelectedItem() != null) {
			filtreES = fEDL.getComboBoxEntreeSortie().getSelectedItem().toString();
		}
		String filtreLocataire = fEDL.getTextFieldLocataire().getText().trim();
		for (Louer l : etatsDesLieux) {
			try {
				String nomLocataire = daoLoc.findById(l.getIdLocataire()).getNom();
				String idBat = daoBl.findById(l.getIdBienLouable()).getIdBat();
				// Filtre Sortie
				if (this.verifChampsSortie(filtreES, nomLocataire, filtreLocataire, filtreBat, idBat, filtreBL, l)) {
					modeleTableFenEtatsDesLieux.addRow(new Object[] { idBat, l.getDateEtatDesLieuxSortie(),
							l.getIdBienLouable(), SORTIE, nomLocataire });
				}

				// Filtre Entrée
				if (this.verifChampsEntree(filtreES, nomLocataire, filtreLocataire, filtreBat, idBat, filtreBL, l)) {

					modeleTableFenEtatsDesLieux.addRow(new Object[] { idBat, l.getDateEtatDesLieuxEntree(),
							l.getIdBienLouable(), ENTREE, nomLocataire });
				}

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	/**
	 * Vérifie les champs pour un etat des lieux d'entree
	 * 
	 * @param filtreES        filtre entrée ou sortie
	 * @param nomLocataire    nom du locataire
	 * @param filtreLocataire locataire sélectionné
	 * @param filtreBat       batiment sélectionné
	 * @param idBat           id du batiment sélectionné
	 * @param filtreBL        bien louable sélectionné
	 * @param l               objet de type Louer en question
	 * 
	 * @return true si les champs sont valides, false sinon
	 */
	private boolean verifChampsSortie(String filtreES, String nomLocataire, String filtreLocataire, String filtreBat,
			String idBat, String filtreBL, Louer l) {
		return (filtreES.equals(SORTIE)
				|| filtreES.equals("Tous") && nomLocataire.toLowerCase().startsWith(filtreLocataire.toLowerCase())
						&& (filtreBat.equals("Tous") || idBat.equals(filtreBat))
						&& (filtreBL.equals("Tous") || l.getIdBienLouable().equals(filtreBL))
						&& (l.getDateEtatDesLieuxEntree() != null && l.getDateEtatDesLieuxSortie() != null));
	}

	/**
	 * Vérifie les champs pour un etat des lieux de sortie
	 * 
	 * @param filtreES        filtre pour
	 * @param filtreES        filtre entrée ou sortie
	 * @param nomLocataire    nom du locataire
	 * @param filtreLocataire locataire sélectionné
	 * @param filtreBat       batiment sélectionné
	 * @param idBat           id du batiment sélectionné
	 * @param filtreBL        bien louable sélectionné
	 * @param l               objet de type Louer en question
	 * 
	 * @return true si les champs sont valides, false sinon
	 */
	private boolean verifChampsEntree(String filtreES, String nomLocataire, String filtreLocataire, String filtreBat,
			String idBat, String filtreBL, Louer l) {
		return (filtreES.equals(ENTREE) || filtreES.equals("Tous"))
				&& (nomLocataire.toLowerCase().startsWith(filtreLocataire.toLowerCase())
						&& (filtreBat.equals("Tous") || idBat.equals(filtreBat))
						&& (filtreBL.equals("Tous") || l.getIdBienLouable().equals(filtreBL))
						&& l.getDateEtatDesLieuxEntree() != null);
	}

	/**
	 * Permet d'écouter en permanence les filtres pour qu'ils s'appliquent dès le
	 * choix ou l'entrée d'un caractère par l'utilisateur
	 * 
	 * @param etatDesLieux liste des objets de type louer, contenant les
	 *                     informations d'états des lieux, sur lesquels les filtres
	 *                     sont appliqués
	 */
	public void activerFiltresAutomatiques(Collection<Louer> etatDesLieux) {

		DocumentListener dl = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				appliquerFiltres(etatDesLieux);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				appliquerFiltres(etatDesLieux);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				appliquerFiltres(etatDesLieux);
			}
		};

		// Champs texte
		fEDL.getTextFieldLocataire().getDocument().addDocumentListener(dl);
		fEDL.getComboBoxBatiment().addActionListener(e -> appliquerFiltres(etatDesLieux));
		fEDL.getComboBoxBienLouable().addActionListener(e -> appliquerFiltres(etatDesLieux));
		fEDL.getComboBoxEntreeSortie().addActionListener(e -> appliquerFiltres(etatDesLieux));
	}

}
