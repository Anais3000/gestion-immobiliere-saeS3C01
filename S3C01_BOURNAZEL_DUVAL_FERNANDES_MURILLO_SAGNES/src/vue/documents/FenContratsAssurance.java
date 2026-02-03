package vue.documents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import controleur.documents.GestionFenContratsAssurance;
import controleur.documents.GestionTableAssurance;
import modele.Assurance;
import modele.dao.DaoAssurance;
import vue.Polices;

/**
 * Fenêtre de gestion et consultation des contrats d'assurance des bâtiments.
 * Affiche une table filtrable des assurances avec possibilité de filtrer par
 * année, bâtiment et type de contrat. Permet de consulter les montants payés
 * pour chaque contrat.
 */
public class FenContratsAssurance extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	// Variables swing
	private JComboBox<String> comboBoxBatiment; // ComboBox pour filtrer par bâtiment
	private JComboBox<String> comboBoxTypeDeContrat; // ComboBox pour filtrer par type de contrat
	private JTextField textFieldAnnee; // Champ de texte pour filtrer par année
	private JPanel panelTable; // Panel contenant la table
	private JTable table; // Table affichant les contrats d'assurance
	private JScrollPane scrollPane; // ScrollPane pour la table
	private JPanel panelQuitter; // Panel contenant le bouton quitter
	private JButton btnQuitter; // Bouton pour fermer la fenêtre

	// Contrôleurs et modèle
	private transient GestionFenContratsAssurance gestionClic; // Contrôleur des événements de la fenêtre
	private transient GestionTableAssurance gestionTable; // Contrôleur de la table et des filtres
	private DefaultTableModel modeleTableAssurance; // Modèle de données de la table // Modèle de données de la table

	/**
	 * Constructeur de la fenêtre des contrats d'assurance. Crée l'interface avec
	 * les filtres (année, bâtiment, type de contrat), la table d'affichage des
	 * assurances et charge automatiquement toutes les données depuis la base de
	 * données avec les filtres activés.
	 */
	public FenContratsAssurance() {

		// Initialisation du contrôleur de la fenêtre
		gestionClic = new GestionFenContratsAssurance(this);
		setBounds(100, 100, 800, 500);

		// Panel du haut : titre et filtres
		JPanel panelHaut = new JPanel(); // Ce panel contient le titre et le bouton "ajouter un paiement"
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setLayout(new BorderLayout(0, 0));
		panelHaut.setBorder(new EmptyBorder(20, 10, 20, 10));

		// Titre de la fenêtre
		JLabel lblTitre = new JLabel("Contrats d'assurance");
		panelHaut.add(lblTitre, BorderLayout.NORTH);
		lblTitre.setFont(Polices.TITRE.getFont());

		// Panel contenant les trois filtres (année, bâtiment, type de contrat)
		JPanel panelfiltre = new JPanel();
		panelHaut.add(panelfiltre, BorderLayout.CENTER);
		panelfiltre.setLayout(new GridLayout(0, 3, 0, 0));

		// Filtre par année (champ de texte)
		textFieldAnnee = new JTextField();
		textFieldAnnee
				.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Année", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Filtres", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(textFieldAnnee);
		textFieldAnnee.setColumns(10);

		// Filtre par bâtiment (combo box)
		comboBoxBatiment = new JComboBox<>();
		panelfiltre.add(comboBoxBatiment);
		comboBoxBatiment.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Bâtiment", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		// Filtre par type de contrat (combo box)
		comboBoxTypeDeContrat = new JComboBox<>();
		panelfiltre.add(comboBoxTypeDeContrat);
		comboBoxTypeDeContrat.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Type de contrat", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		// Table des contrats d'assurance
		panelTable = new JPanel();
		getContentPane().add(panelTable, BorderLayout.CENTER);
		panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.X_AXIS));

		scrollPane = new JScrollPane();
		panelTable.add(scrollPane);

		// Création de la table avec 4 colonnes (Année, Bâtiment, Montant, Type de
		// contrat)
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null }, },
				new String[] { "Ann\u00E9e", "B\u00E2timent", "Montant (€)", "Type de contrat" }) {
			Class[] columnTypes = new Class[] { Integer.class, Object.class, Double.class, Object.class };

			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		// Initialisation du gestionnaire de table (gestion des filtres et des actions)
		gestionTable = new GestionTableAssurance(this);
		table.getColumnModel().getColumn(2).setPreferredWidth(96);
		scrollPane.setViewportView(table);

		// Panel du bas : bouton quitter
		panelQuitter = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelQuitter.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelQuitter, BorderLayout.SOUTH);

		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(gestionClic);
		panelQuitter.add(btnQuitter);

		// Récupération du modèle de la table pour manipulation des données
		modeleTableAssurance = (DefaultTableModel) this.getTable().getModel();

		// ===== CHARGEMENT INITIAL DES DONNÉES =====
		// Chargement de toutes les assurances depuis la base de données
		DaoAssurance a = new DaoAssurance();
		Collection<Assurance> assurances;
		try {
			assurances = a.findAll();
			// Application des filtres sur les données chargées
			gestionTable.appliquerFiltres(assurances);
			// Activation automatique des filtres (remplissage des combo box)
			gestionTable.activerFiltresAutomatiques(assurances);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Récupère la table affichant les contrats d'assurance.
	 * 
	 * @return la table des contrats d'assurance
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * Récupère le gestionnaire de la table des assurances.
	 * 
	 * @return le gestionnaire de table
	 */
	public GestionTableAssurance getGestionTable() {
		return gestionTable;
	}

	/**
	 * Récupère la combo box de sélection du bâtiment pour le filtrage.
	 * 
	 * @return la combo box de filtrage par bâtiment
	 */
	public JComboBox<String> getComboBoxBat() {
		return comboBoxBatiment;
	}

	/**
	 * Récupère la combo box de sélection du type de contrat pour le filtrage.
	 * 
	 * @return la combo box de filtrage par type de contrat
	 */
	public JComboBox<String> getComboBoxContrat() {
		return comboBoxTypeDeContrat;
	}

	/**
	 * Récupère le champ de texte pour saisir l'année de filtrage.
	 * 
	 * @return le champ de texte de l'année
	 */
	public JTextField getTextFieldAnnee() {
		return textFieldAnnee;
	}

	/**
	 * Récupère le modèle de données de la table des assurances.
	 * 
	 * @return le modèle de table
	 */
	public DefaultTableModel getModeleTableAssurance() {
		return modeleTableAssurance;
	}

}
