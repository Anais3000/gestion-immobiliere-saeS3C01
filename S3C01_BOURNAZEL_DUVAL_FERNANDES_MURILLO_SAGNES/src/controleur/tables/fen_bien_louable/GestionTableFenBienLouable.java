package controleur.tables.fen_bien_louable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.BienLouable;
import modele.Locataire;
import modele.Louer;
import modele.dao.DaoBienLouable;
import modele.dao.DaoLocataire;
import modele.dao.DaoLouer;
import vue.consulter_informations.FenBienLouableInformations;
import vue.tables.FenBienLouable;

public class GestionTableFenBienLouable {
	private FenBienLouable fb;
	private DefaultTableModel modeleTableFenBienLouable;
	private DaoBienLouable daoBL;
	private Collection<BienLouable> bienLouables;

	/**
	 * Initialise la classe permettant la gestion du tableau général des biens
	 * louables
	 * 
	 * @param fen fenetre des biens louables contenant le tableau
	 */
	public GestionTableFenBienLouable(FenBienLouable fb) {
		this.fb = fb;
		modeleTableFenBienLouable = (DefaultTableModel) fb.getTableBienLouable().getModel();
		daoBL = new DaoBienLouable();
		try {
			bienLouables = daoBL.findAll();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
		fb.getComboBoxBatiment().addItem("Tous");
		for (BienLouable b : bienLouables) {
			if (!Outils.comboContient(fb.getComboBoxBatiment(), b.getIdBat())) {
				fb.getComboBoxBatiment().addItem(b.getIdBat());
			}
		}
	}

	/**
	 * Permet l'ouverture de la fenêtre d'informations du bien louable en fonction
	 * de la ligne sélectionnée
	 * 
	 * @param selectedRow numéro de la ligne sélectionnée
	 */
	private void ouvririFenetre(int selectedRow) {
		if (selectedRow != -1) {
			try {
				BienLouable selectBien = this.lireLigneTableBiens(selectedRow);
				GestionTableFenBienLouable.chargerFenetreBienLouable(selectBien, fb);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Retourne un objet de type BienLouable en fonction du numéro de la ligne voulu
	 * 
	 * @param numeroLigne numéro de la ligne
	 * @return l'objet de type BienLouable correspondant
	 * 
	 * @throws SQLException
	 */
	public BienLouable lireLigneTableBiens(int numeroLigne) throws SQLException {
		String idBien = (String) this.fb.getTableBienLouable().getValueAt(numeroLigne, 0);
		return this.daoBL.findById(idBien);
	}

	/**
	 * Remet à 0 la table des biens louables et y insère les biens louables présents
	 * en BD
	 * 
	 * @throws SQLException
	 */
	public void ecrireLigneTableBienLouable() throws SQLException {
		modeleTableFenBienLouable.setRowCount(0);
		bienLouables = daoBL.findAll();
		for (BienLouable b : bienLouables) {
			modeleTableFenBienLouable.addRow(new Object[] { b.getIdBien(), b.getAdresse(), b.getVille(),
					b.getCodePostal(), b.getTypeBien(), b.getLoyer(), b.getNbPieces(), b.getIdBat() });
		}
	}

	/**
	 * Applique les filtres ET écrit les lignes dans la table en fonction des
	 * filtres sélectionnés
	 * 
	 * Utilisée également pour rafraichir la table quand ajout / suppression
	 */
	public void appliquerFiltres() {

		// Vide le tableau
		while (modeleTableFenBienLouable.getRowCount() > 0) {
			modeleTableFenBienLouable.removeRow(0);
		}

		int filtreNbPiece = (int) fb.getSpinner().getValue();
		String filtreTypeBien = (String) fb.getComboBoxTypeBien().getSelectedItem();
		String filtreAdresse = fb.getTextFieldAdresse().getText().trim().toLowerCase();
		String filtreCP = fb.getTextFieldCP().getText().trim().toLowerCase();
		String filtreVille = fb.getTextFieldVille().getText().trim().toLowerCase();
		String filtreBat = (String) fb.getComboBoxBatiment().getSelectedItem();

		for (BienLouable bl : bienLouables) {
			if (bl.getAdresse().trim().toLowerCase().contains(filtreAdresse)
					&& bl.getVille().trim().toLowerCase().startsWith(filtreVille)
					&& (filtreTypeBien.equals("Tous") || bl.getTypeBien().equals(filtreTypeBien))
					&& bl.getCodePostal().trim().toLowerCase().startsWith(filtreCP)
					&& (filtreNbPiece == 0 || bl.getNbPieces() == filtreNbPiece)
					&& (filtreBat.equals("Tous") || bl.getIdBat().equals(filtreBat))) {
				modeleTableFenBienLouable.addRow(new Object[] { bl.getIdBien(), bl.getAdresse(), bl.getVille(),
						bl.getCodePostal(), bl.getTypeBien(), bl.getLoyer(), bl.getNbPieces(), bl.getIdBat() });
			}
		}
	}

	/**
	 * Permet d'écouter les champs de filtres de façon permanente pour que la table
	 * se mette à jour dès qu'un filtre change
	 */
	public void activerFiltresAutomatiques() {
		DocumentListener dl = new DocumentListener() {
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
		};
		fb.getTextFieldVille().getDocument().addDocumentListener(dl);
		fb.getTextFieldCP().getDocument().addDocumentListener(dl);
		fb.getTextFieldVille().getDocument().addDocumentListener(dl);
		fb.getSpinner().addChangeListener(e -> appliquerFiltres());
		fb.getComboBoxTypeBien().addActionListener(e -> appliquerFiltres());
		fb.getComboBoxBatiment().addActionListener(e -> appliquerFiltres());
		fb.getTextFieldAdresse().getDocument().addDocumentListener(dl);
	}

	/**
	 * Permet de charger l'entièreté de la fenêtre des informations d'un bien
	 * louable Statique car on a besoin d'y accéder quand on associe un nouveau loc
	 * par exemple.
	 * 
	 * @param selectBien bien louable en question
	 * @param fb         fenêtre de l'ensemble des biens louables
	 * @throws SQLException
	 */
	public static void chargerFenetreBienLouable(BienLouable selectBien, FenBienLouable fb) throws SQLException {
		FenBienLouableInformations fenBLI = new FenBienLouableInformations(selectBien, fb);
		// Ecrire toutes les données de chaque table de la fenetre avant d'afficher
		fenBLI.getGestionTableRelev().ecrireLigneTable(selectBien.getIdBien());
		fenBLI.getGestionTableInterv().ecrireLigneTable(selectBien.getIdBien());
		fenBLI.getGestionTableDiag().ecrireLigneTable(selectBien.getIdBien());
		fenBLI.getGestionTableAnciensLocataires().ecrireLigneTable(selectBien.getIdBien());
		fenBLI.getGestionTableAnciensEtatsLieux().ecrireLigneTable(selectBien.getIdBien());
		fenBLI.getGestionTableCompt().ecrireLigneTable(selectBien.getIdBien());
		fenBLI.getGestionTableSTC().ecrireLigneTable(selectBien.getIdBien());
		fenBLI.getGestionTableRegularisationCharges().ecrireLigneTable(selectBien.getIdBien());

		// Activation / désactivation des champs en fonction de si bail en cours ou non
		DaoLouer daoLouer = new DaoLouer();
		DaoLocataire daoLocataire = new DaoLocataire();

		Louer derniereLoc = daoLouer.findByIdBienLouable(selectBien.getIdBien());

		if (derniereLoc == null || derniereLoc.getRevolue() == 1) {
			fenBLI.desactiverChampsBail();
		} else {
			fenBLI.activerChampsBail();

			Locataire locataireActuel = daoLocataire.findById(derniereLoc.getIdLocataire());

			fenBLI.getLblIdLocataireValeur().setText(derniereLoc.getIdLocataire());
			fenBLI.getLblNomLocataireValeur().setText(locataireActuel.getNom());

			fenBLI.getLblPrenomLocataireValeur().setText(locataireActuel.getPrenom());
			fenBLI.getLblDateDebutBailValeur().setText(derniereLoc.getDateDebut().toString());

			fenBLI.getLblDateFinBailValeur().setText(derniereLoc.getDateFin().toString());
			fenBLI.getLblIdGarantValeur().setText(derniereLoc.getIdGarant());
			fenBLI.getLblDepotGarantieValeur().setText(String.valueOf(derniereLoc.getDepotDeGarantie()) + "€");

			setGaragesAssocies(fenBLI);

			if (derniereLoc.getDetailsEtatDesLieuxEntree() == null) {
				fenBLI.getBtnAjouterEtatLieuxEntree().setVisible(true);
				fenBLI.getBtnConsulterEtatLieuxEntree().setVisible(false);
			} else {
				fenBLI.getBtnAjouterEtatLieuxEntree().setVisible(false);
				fenBLI.getBtnConsulterEtatLieuxEntree().setVisible(true);
			}
		}

		fenBLI.setVisible(true);
	}

	/**
	 * Permet de fixer la liste des garages associés à une location
	 * 
	 * @param fBLI fenetre informations sur le bien louable
	 */
	private static void setGaragesAssocies(FenBienLouableInformations fBLI) {
		StringBuilder idGarages = new StringBuilder();

		DaoLouer daoL = new DaoLouer();
		ArrayList<Louer> listeLocsGaragesAssocies = null;
		try {
			listeLocsGaragesAssocies = (ArrayList<Louer>) daoL.findAllLouerGaragesAssociesLogement(
					fBLI.getLblDateDebutBailValeur().getText(), fBLI.getLblDateFinBailValeur().getText(),
					fBLI.getLblIdentifiantValeur().getText());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
		for (Louer l : listeLocsGaragesAssocies) {
			if (idGarages.length() == 0) {
				idGarages.append(l.getIdBienLouable());
			} else {
				idGarages.append(", ").append(l.getIdBienLouable());
			}
		}
		if (idGarages.length() == 0) {
			fBLI.lblGaragesAssociesValeur().setText("Aucun garage associé.");
		} else {
			fBLI.lblGaragesAssociesValeur().setText(idGarages.toString());
		}
	}

	/**
	 * Permet d'ouvrir la fenêtre du bien sélectionné en fonction de l'endroit où
	 * l'utilisateur clique dans le tableau
	 * 
	 * @return un listener de souris permettant cette action
	 */
	public MouseAdapter getMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) { // Un simple clic
					int clickedRow = fb.getTableBienLouable().rowAtPoint(e.getPoint());
					ouvririFenetre(clickedRow);
				}
			}
		};
	}
}