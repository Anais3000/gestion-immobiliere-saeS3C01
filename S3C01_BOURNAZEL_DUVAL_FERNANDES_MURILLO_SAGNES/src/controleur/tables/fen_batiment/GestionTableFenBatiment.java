package controleur.tables.fen_batiment;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Batiment;
import modele.dao.DaoBatiment;
import vue.consulter_informations.FenBatimentInformations;
import vue.tables.FenBatiment;

public class GestionTableFenBatiment {
	private FenBatiment fb;
	private DefaultTableModel modeleTableBatiment;
	private DaoBatiment daoBat;
	private DateTimeFormatter formatter;
	private Collection<Batiment> batiments;

	/**
	 * Initialise la classe permettant la gestion du tableau général des batiments
	 * 
	 * @param fb fenetre des batiments contenant le tableau
	 */
	public GestionTableFenBatiment(FenBatiment fb) {
		this.fb = fb;
		this.modeleTableBatiment = (DefaultTableModel) fb.getTableBatiment().getModel();
		this.daoBat = new DaoBatiment();
		formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	}

	/**
	 * Permet l'ouverture de la fenêtre d'informations du batiment en fonction de la
	 * ligne sélectionnée
	 * 
	 * @param selectedRow numéro de la ligne sélectionnée
	 */
	private void ouvririFenetre(int selectedRow) {
		if (selectedRow >= modeleTableBatiment.getRowCount()) {
			return;
		}

		if (selectedRow != -1) {
			try {
				Batiment selectBatiment = this.lireLigneTableBatiments(selectedRow);
				if (selectBatiment == null) {
					return;
				}
				FenBatimentInformations fenBI = new FenBatimentInformations(selectBatiment, fb);
				fenBI.getGestionTableBiens().ecrireLigneTable();
				fenBI.getGestionTableInterv().ecrireLigneTable();
				fenBI.getGestionTableAss().ecrireLigneTable();
				fenBI.getGestionTableRelev().ecrireLigneTable();
				fenBI.getGestionTableCompt().ecrireLigneTable();
				fenBI.setVisible(true);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Retourne un objet de type batiment en fonction du numéro de la ligne voulu
	 * 
	 * @param numeroLigne numéro de la ligne
	 * @return l'objet de type Batiment correspondant
	 * 
	 * @throws SQLException
	 */
	public Batiment lireLigneTableBatiments(int numeroLigne) throws SQLException {
		String idBatiment = (String) this.fb.getTableBatiment().getValueAt(numeroLigne, 0);
		return this.daoBat.findById(idBatiment);
	}

	/**
	 * Applique les filtres ET écrit les lignes dans la table en fonction des
	 * filtres sélectionnés
	 * 
	 * Utilisée également pour rafraichir la table quand ajout / suppression
	 */
	public void appliquerFiltres() {

		// On initialise la liste des batiments à chaque fois pour prendre en compte les
		// bâtiments ajoutés si tel est le cas
		try {
			batiments = daoBat.findAll();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		while (modeleTableBatiment.getRowCount() > 0) {
			modeleTableBatiment.removeRow(0);
		}

		String filtreId = fb.getTextFieldID().getText().trim().toLowerCase();
		String filtreAdresse = fb.getTextFieldAdresse().getText().trim().toLowerCase();
		String filtreCP = fb.getTextFieldCP().getText().trim().toLowerCase();
		String filtreVille = fb.getTextFieldVille().getText().trim().toLowerCase();

		LocalDate ldDebut = Outils.parseDateField(fb.getTextDateDebut().getText());
		LocalDate ldFin = Outils.parseDateField(fb.getTextDateFin().getText());

		for (Batiment b : batiments) {
			LocalDate date = b.getDateConstruction();

			if (b.getIdBat().toLowerCase().contains(filtreId) && b.getAdresse().toLowerCase().contains(filtreAdresse)
					&& b.getCodePostal().toLowerCase().startsWith(filtreCP)
					&& b.getVille().toLowerCase().startsWith(filtreVille)
					&& (ldDebut == null || !date.isBefore(ldDebut)) && (ldFin == null || !date.isAfter(ldFin))) {
				String dateFormatee = date.format(formatter);
				modeleTableBatiment.addRow(
						new Object[] { b.getIdBat(), dateFormatee, b.getAdresse(), b.getCodePostal(), b.getVille() });
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

		// Champs texte
		fb.getTextFieldAdresse().getDocument().addDocumentListener(dl);
		fb.getTextFieldCP().getDocument().addDocumentListener(dl);
		fb.getTextFieldVille().getDocument().addDocumentListener(dl);
		fb.getTextFieldID().getDocument().addDocumentListener(dl);

		// Champs de date : on écoute le texte, pas la valeur
		fb.getTextDateDebut().getDocument().addDocumentListener(dl);
		fb.getTextDateFin().getDocument().addDocumentListener(dl);
	}

	/**
	 * Permet d'ouvrir la fenêtre du batiment sélectionné en fonction de l'endroit
	 * où l'utilisateur clique dans le tableau
	 * 
	 * @return un listener de souris permettant cette action
	 */
	public MouseAdapter getMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) { // Un simple clic
					int clickedRow = fb.getTableBatiment().rowAtPoint(e.getPoint());
					ouvririFenetre(clickedRow);
				}
			}
		};
	}

}
