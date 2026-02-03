package controleur.tables.fen_locataire;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Locataire;
import modele.dao.DaoLocataire;
import vue.consulter_informations.FenLocataireInformations;
import vue.tables.FenLocataire;

public class GestionTableFenLocataire implements ListSelectionListener {
	private FenLocataire fl;
	private DefaultTableModel modeleTableFenLocataire;
	private DaoLocataire daoLocataire;

	/**
	 * Initialise la classe permettant la gestion du tableau général des locataires
	 * 
	 * @param fl fenetre des biens louables contenant le tableau
	 */
	public GestionTableFenLocataire(FenLocataire fl) {
		this.fl = fl;
		modeleTableFenLocataire = (DefaultTableModel) fl.getTableLocataires().getModel();
		daoLocataire = new DaoLocataire();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) { // éviter que fen s'ouvre deux fois
			int selectedRow = this.fl.getTableLocataires().getSelectedRow();

			if (selectedRow < 0 || selectedRow >= modeleTableFenLocataire.getRowCount()) {
				return;
			}
			try {
				Locataire selectLocataire = this.lireLigneTableLocataire(selectedRow);
				FenLocataireInformations fenLI = new FenLocataireInformations(selectLocataire, fl);
				fenLI.getGestionTableBiens().ecrireLigneTableBienLouable(); // remplissage table BienLouable
				fenLI.getGestionTablePaiements().ecrireLigneTablePaiements(); // remplissage table paiements
				fenLI.setVisible(true);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Remet à 0 la table des locataires et y insère les locataires présents en BD
	 * 
	 * @throws SQLException
	 */
	public void ecrireLigneTableLocataire() {
		List<Locataire> tableauLocataires;
		modeleTableFenLocataire.setRowCount(0);
		try {
			tableauLocataires = (List<Locataire>) daoLocataire.findAll();
			for (Locataire l : tableauLocataires) {
				modeleTableFenLocataire.addRow(new Object[] { l.getIdLocataire(), l.getNom(), l.getPrenom(),
						l.getNumTelephone(), l.getMail(), l.getDateNaissance(), l.getVilleNaissance(), l.getAdresse(),
						l.getCodePostal(), l.getVille() });
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Retourne un objet de type Locataire en fonction du numéro de la ligne voulu
	 * 
	 * @param numeroLigne numéro de la ligne
	 * @return l'objet de type Locataire correspondant
	 * 
	 * @throws SQLException
	 */
	public Locataire lireLigneTableLocataire(int numeroLigne) throws SQLException {
		String idLocataire = (String) this.fl.getTableLocataires().getValueAt(numeroLigne, 0);
		return this.daoLocataire.findById(idLocataire);
	}

	/**
	 * Applique les filtres ET écrit les lignes dans la table en fonction des
	 * filtres sélectionnés
	 * 
	 * Utilisée également pour rafraichir la table quand ajout / suppression
	 */
	public void appliquerFiltres(Collection<Locataire> locataires) {
		// Vide le tableau
		modeleTableFenLocataire.setRowCount(0);
		// Récupère les textes des filtres
		String filtreNom = fl.getTextFieldNom().getText().trim().toLowerCase();
		String filtrePrenom = fl.getTextFieldPrenom().getText().trim().toLowerCase();
		String filtreAdresse = fl.getTextFieldAdresse().getText().trim().toLowerCase();
		String filtreCP = fl.getTextFieldCP().getText().trim().toLowerCase();
		String filtreVille = fl.getTextFieldVille().getText().trim().toLowerCase();
		// Ajoute uniquement les locataires correspondant aux filtres
		for (Locataire l : locataires) {
			if (l.getNom().trim().toLowerCase().startsWith(filtreNom)
					&& l.getPrenom().trim().toLowerCase().startsWith(filtrePrenom)// je mets des trim comme ça même si
																					// y'a des espaces ça marche quand
																					// même
					&& l.getAdresse().trim().toLowerCase().startsWith(filtreAdresse)
					&& l.getCodePostal().trim().toLowerCase().startsWith(filtreCP)
					&& l.getVille().trim().toLowerCase().startsWith(filtreVille)) {
				modeleTableFenLocataire.addRow(new Object[] { l.getIdLocataire(), l.getNom(), l.getPrenom(),
						l.getNumTelephone(), l.getMail(), l.getDateNaissance(), l.getVilleNaissance(), l.getAdresse(),
						l.getCodePostal(), l.getVille() });
			}
		}
	}

	/**
	 * Permet d'écouter les champs de filtres de façon permanente pour que la table
	 * se mette à jour dès qu'un filtre change
	 * 
	 * @param locataires liste des locataires sur lesquels sont appliqués les
	 *                   filtres
	 */
	public void activerFiltresAutomatiques(Collection<Locataire> locataires) {
		DocumentListener dl = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {// un caractère en plus dans le champ
				appliquerFiltres(locataires);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {// un caractère supprimé dans le champ
				appliquerFiltres(locataires);
			}

			@Override
			public void changedUpdate(
					DocumentEvent e) {/*
										 * dans notre cas il ne sert à rien mais il faut le mettre pour pouvoir
										 * initialiser un DocumentListener (il est appelé quand une propriété du texte
										 * change comme le style, la taille etc...
										 */
				appliquerFiltres(locataires);
			}
		};
		/*
		 * Ajout des textfields au documentlistener donc à n'importe quel changement
		 * dans les champs il sera activé et le filtre sera donc appliqué
		 */
		fl.getTextFieldNom().getDocument().addDocumentListener(dl);
		fl.getTextFieldPrenom().getDocument().addDocumentListener(dl);
		fl.getTextFieldAdresse().getDocument().addDocumentListener(dl);
		fl.getTextFieldCP().getDocument().addDocumentListener(dl);
		fl.getTextFieldVille().getDocument().addDocumentListener(dl);
	}
}