package vue.tables;

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
import controleur.tables.fen_organisme.GestionFenOrganisme;
import controleur.tables.fen_organisme.GestionTableOrganisme;
import modele.Organisme;
import modele.dao.DaoOrganisme;
import vue.Polices;

/**
 * Fenêtre interne de gestion des organismes. Affiche une table des organismes
 * avec des filtres par nom, spécialité et ville. Permet d'ajouter de nouveaux
 * organismes.
 */
public class FenOrganisme extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JTextField textFieldNom; // Filtre par nom
	private JTextField textFieldSpecialite; // Filtre par spécialité
	private JTextField textFieldVille; // Filtre par ville
	private JPanel panelTable; // Panel principal de la fenêtre
	private JTable table; // Table affichant la liste des organismes
	private JScrollPane scrollPane; // Panel de défilement pour la table
	private JPanel panelQuitter; // Panel du bas contenant le bouton quitter
	private JButton btnQuitter; // Bouton pour quitter la fenêtre

	// Contrôleurs et DAO
	private transient GestionFenOrganisme gestionClic; // Contrôleur des événements des boutons
	private transient GestionTableOrganisme gestionTable; // Contrôleur des événements de la table
	private transient DaoOrganisme daoOrganisme; // DAO pour accéder aux organismes

	/**
	 * Constructeur de la fenêtre de gestion des organismes. Initialise l'interface
	 * graphique avec une table des organismes, des filtres de recherche et des
	 * boutons d'action. Charge également les données depuis la base de données.
	 */
	public FenOrganisme() {
		this.gestionClic = new GestionFenOrganisme(this);
		this.daoOrganisme = new DaoOrganisme();
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelHaut = new JPanel(); // Ce panel contient le titre et le bouton "ajouter un locataire"
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setLayout(new BorderLayout(0, 0));
		panelHaut.setBorder(new EmptyBorder(20, 10, 20, 10)); // J'ajoute des marges pour que ce soit plus joli

		// Panel des filtres de recherche
		JPanel panelfiltre = new JPanel();
		panelHaut.add(panelfiltre, BorderLayout.SOUTH);
		panelfiltre.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 2), "Filtres",
				TitledBorder.LEFT, TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.BOLD, 12), Color.black));
		panelfiltre.setLayout(new GridLayout(0, 3, 0, 0));

		// ----------------------------- éléments de panelHaut ----------------------
		JLabel lblTitre = new JLabel("Organismes");
		panelHaut.add(lblTitre, BorderLayout.WEST);
		lblTitre.setFont(Polices.TITRE.getFont());

		JButton btnAjouterUnOrganisme = new JButton("Ajouter un organisme");
		btnAjouterUnOrganisme.addActionListener(this.gestionClic);
		panelHaut.add(btnAjouterUnOrganisme, BorderLayout.EAST);

		// Filtres de recherche
		textFieldNom = new JTextField();
		panelfiltre.add(textFieldNom);
		textFieldNom.setColumns(10);
		textFieldNom
				.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Nom", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		textFieldSpecialite = new JTextField();
		textFieldSpecialite.setColumns(10);
		textFieldSpecialite.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Spécialité", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(textFieldSpecialite);

		textFieldVille = new JTextField();
		textFieldVille.setColumns(10);
		textFieldVille
				.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Ville", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(textFieldVille);

		// -------------------------------- éléments de panelCentre
		// ------------------------------
		panelTable = new JPanel();
		getContentPane().add(panelTable, BorderLayout.CENTER);
		panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.X_AXIS));

		scrollPane = new JScrollPane();
		panelTable.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Num\u00E9ro de Siret", "Nom",
				"Sp\u00E9cialit\u00E9", "Adresse", "Code postal", "Ville", "Mail", "Num\u00E9ro de telephone", }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // tableau NON éditable
			}
		});

		this.gestionTable = new GestionTableOrganisme(this);
		this.table.addMouseListener(this.gestionTable.getMouseListener());
		scrollPane.setViewportView(table);

		// -------------------------------- éléments de panelBas
		// ------------------------------
		panelQuitter = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelQuitter.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		getContentPane().add(panelQuitter, BorderLayout.SOUTH);

		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this.gestionClic);
		panelQuitter.add(btnQuitter);

		// Chargement des données depuis la base de données
		Collection<Organisme> organismes;
		try {
			organismes = this.daoOrganisme.findAll(); // Récupération de tous les organismes
			gestionTable.appliquerFiltres(organismes); // Application des filtres
			gestionTable.activerFiltresAutomatiques(organismes); // Activation des filtres automatiques
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Récupère la table des organismes.
	 * 
	 * @return la table des organismes
	 */
	public JTable getTable() {
		return this.table;
	}

	/**
	 * Récupère le contrôleur des événements de la table.
	 * 
	 * @return le contrôleur des événements de la table
	 */
	public GestionTableOrganisme getGestionTable() {
		return gestionTable;
	}

	/**
	 * Récupère la table des organismes (alias de getTable()).
	 * 
	 * @return la table des organismes
	 */
	public JTable getTableOrganismes() {
		return this.table;
	}

	/**
	 * Récupère le champ de texte du nom.
	 * 
	 * @return le champ de texte du nom
	 */
	public JTextField getTextFieldNom() {
		return textFieldNom;
	}

	/**
	 * Récupère le champ de texte de la spécialité.
	 * 
	 * @return le champ de texte de la spécialité
	 */
	public JTextField getTextFieldSpecialite() {
		return textFieldSpecialite;
	}

	/**
	 * Récupère le champ de texte de la ville.
	 * 
	 * @return le champ de texte de la ville
	 */
	public JTextField getTextFieldVille() {
		return textFieldVille;
	}

}
