package controleur.tables.fen_organisme;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Organisme;
import modele.dao.DaoOrganisme;
import vue.consulter_informations.FenOrganismeInformations;
import vue.tables.FenOrganisme;

public class GestionTableOrganisme {

	private FenOrganisme orga;
	private DefaultTableModel modelTableOrga;
	private DaoOrganisme daoOrganisme;

	/**
	 * Initialise la classe permettant la gestion du tableau général des organismes
	 * 
	 * @param orga fenetre des organismes contenant le tableau
	 */
	public GestionTableOrganisme(FenOrganisme orga) {
		this.orga = orga;
		this.daoOrganisme = new DaoOrganisme();
		this.modelTableOrga = (DefaultTableModel) orga.getTable().getModel();
	}

	/**
	 * Permet l'ouverture de la fenêtre d'informations de l'organisme en fonction de
	 * la ligne sélectionnée
	 * 
	 * @param selectedRow numéro de la ligne sélectionnée
	 */
	private void ouvririFenetre(int selectedRow) {
		if (selectedRow != -1) {
			try {
				Organisme selectOrganisme = this.lireLigneTableOrganisme(selectedRow);
				FenOrganismeInformations fenOI = new FenOrganismeInformations(selectOrganisme, this.orga);
				fenOI.setVisible(true);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Vide entièremenent la table des organismes
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
	 * Remet à 0 la table des organismes et y insère les biens louables présents en
	 * BD
	 */
	public void ecrireLigneTableOrganisme() {
		List<Organisme> tableauOrgas;
		try {
			modelTableOrga.setRowCount(0);
			tableauOrgas = (List<Organisme>) daoOrganisme.findAll();
			for (Organisme o : tableauOrgas) {
				// "Num\u00E9ro de Siret", "Nom", "Sp\u00E9cialit\u00E9", "Adresse", "Code
				// postal", "Ville", "Mail", "Num\u00E9ro de telephone",
				modelTableOrga.addRow(new Object[] { o.getNumSIRET(), o.getNom(), o.getSpecialite(), o.getAdresse(),
						o.getCodePostal(), o.getVille(), o.getMail(), o.getNumTel() });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Retourne un objet de type Organisme en fonction du numéro de la ligne voulu
	 * 
	 * @param numeroLigne numéro de la ligne
	 * @return l'objet de type Organisme correspondant
	 * 
	 * @throws SQLException
	 */
	public Organisme lireLigneTableOrganisme(int numeroLigne) throws SQLException {
		String idOrganisme = (String) this.orga.getTableOrganismes().getValueAt(numeroLigne, 0);
		return this.daoOrganisme.findById(idOrganisme);
	}

	/**
	 * Applique les filtres ET écrit les lignes dans la table en fonction des
	 * filtres sélectionnés
	 * 
	 * Utilisée également pour rafraichir la table quand ajout / suppression
	 * 
	 * @param organismes liste des organismes sur lesquels sont appliqués les
	 *                   filtres
	 */
	public void appliquerFiltres(Collection<Organisme> organismes) {

		while (modelTableOrga.getRowCount() > 0) {
			modelTableOrga.removeRow(0);
		}
		String filtreNom = orga.getTextFieldNom().getText().trim().toLowerCase();
		String filtreSpecialite = orga.getTextFieldSpecialite().getText().trim().toLowerCase();
		String filtreVille = orga.getTextFieldVille().getText().trim().toLowerCase();
		for (Organisme o : organismes) {
			if (o.getNom().trim().toLowerCase().startsWith(filtreNom)
					&& o.getSpecialite().trim().toLowerCase().startsWith(filtreSpecialite)
					&& o.getVille().trim().toLowerCase().startsWith(filtreVille)) {
				modelTableOrga.addRow(new Object[] { o.getNumSIRET(), o.getNom(), o.getSpecialite(), o.getAdresse(),
						o.getCodePostal(), o.getVille(), o.getMail(), o.getNumTel() });
			}
		}
	}

	/**
	 * Permet d'écouter les champs de filtres de façon permanente pour que la table
	 * se mette à jour dès qu'un filtre change
	 */
	public void activerFiltresAutomatiques(Collection<Organisme> organismes) {
		DocumentListener dl = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {// un caractère en plus dans le champ
				appliquerFiltres(organismes);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {// un caractère supprimé dans le champ
				appliquerFiltres(organismes);
			}

			@Override
			public void changedUpdate(
					DocumentEvent e) {/*
										 * dans notre cas il ne sert à rien mais il faut le mettre pour pouvoir
										 * initialiser un DocumentListener (il est appelé quand une propriété du texte
										 * change comme le style, la taille etc...
										 */
				appliquerFiltres(organismes);
			}
		};
		/*
		 * Ajout des textfields au documentlistener donc à n'importe quel changement
		 * dans les champs il sera activé et le filtre sera donc appliqué
		 */
		orga.getTextFieldNom().getDocument().addDocumentListener(dl);
		orga.getTextFieldSpecialite().getDocument().addDocumentListener(dl);
		orga.getTextFieldVille().getDocument().addDocumentListener(dl);
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
					int clickedRow = orga.getTableOrganismes().rowAtPoint(e.getPoint());
					ouvririFenetre(clickedRow);
				}
			}
		};
	}

}
