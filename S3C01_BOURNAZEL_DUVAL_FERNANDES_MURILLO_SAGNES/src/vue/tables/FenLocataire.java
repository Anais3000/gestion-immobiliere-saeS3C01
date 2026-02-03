package vue.tables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import controleur.tables.fen_locataire.GestionFenLocataire;
import controleur.tables.fen_locataire.GestionTableFenLocataire;
import modele.Locataire;
import modele.dao.DaoLocataire;
import vue.Polices;

/**
 * Fenêtre interne de gestion des locataires. Affiche une table des locataires
 * avec des filtres par nom, prénom, adresse, code postal et ville. Permet
 * d'ajouter de nouveaux locataires.
 */
public class FenLocataire extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel contentPane; // Panel principal de la fenêtre
	private JTable tableLocataires; // Table affichant la liste des locataires
	private JTextField textFieldAdresse; // Filtre par adresse
	private JTextField textFieldCP; // Filtre par code postal
	private JTextField textFieldVille; // Filtre par ville
	private JTextField textFieldNom; // Filtre par nom
	private JTextField textFieldPrenom; // Filtre par prénom
	private JPanel panelBas; // Panel du bas contenant le bouton quitter

	// Contrôleurs
	private transient GestionFenLocataire gestionClic; // Contrôleur des événements des boutons
	private transient GestionTableFenLocataire gestionTable; // Contrôleur des événements de la table

	/**
	 * Constructeur de la fenêtre de gestion des locataires. Initialise l'interface
	 * graphique avec une table des locataires, des filtres de recherche et des
	 * boutons d'action. Charge également les données depuis la base de données.
	 */
	public FenLocataire() {
		gestionClic = new GestionFenLocataire(this);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// ------------------------------- Les panels
		// ------------------------------------
		JPanel panelHaut = new JPanel(); // Ce panel contient le titre et le bouton "ajouter un locataire"
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setLayout(new BorderLayout(0, 0));
		panelHaut.setBorder(new EmptyBorder(20, 10, 20, 10)); // J'ajoute des marges pour que ce soit plus joli

		JPanel panelCentre = new JPanel(); // Ce panel contient le titre et le bouton "ajouter un l"
		getContentPane().add(panelCentre, BorderLayout.CENTER);
		panelCentre.setLayout(new BorderLayout(0, 0));

		panelBas = new JPanel(); // Ce panel contient le bouton quitter
		FlowLayout flowLayout = (FlowLayout) panelBas.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelBas, BorderLayout.SOUTH);

		// Panel des filtres de recherche
		JPanel panelfiltre = new JPanel();
		panelHaut.add(panelfiltre, BorderLayout.SOUTH);
		panelfiltre.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 2), "Filtres",
				TitledBorder.LEFT, TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.BOLD, 12), Color.black));
		panelfiltre.setLayout(new GridLayout(0, 4, 0, 0));

		// ----------------------------- éléments de panelHaut (titre et bouton)
		// ----------------------
		JLabel lblTitre = new JLabel("Locataires");
		panelHaut.add(lblTitre, BorderLayout.WEST);
		lblTitre.setFont(Polices.TITRE.getFont());

		JButton btnAjouterLocataire = new JButton("Ajouter un locataire");
		btnAjouterLocataire.addActionListener(this.gestionClic);
		panelHaut.add(btnAjouterLocataire, BorderLayout.EAST);

		// filtres
		textFieldNom = new JTextField();
		panelfiltre.add(textFieldNom);
		textFieldNom
				.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Nom", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		textFieldPrenom = new JTextField();
		panelfiltre.add(textFieldPrenom);
		textFieldPrenom
				.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Prénom", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		textFieldAdresse = new JTextField();
		panelfiltre.add(textFieldAdresse);
		textFieldAdresse.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Adresse", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		textFieldCP = new JTextField();
		panelfiltre.add(textFieldCP);
		textFieldCP.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Code Postal", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		textFieldVille = new JTextField();
		panelfiltre.add(textFieldVille);
		textFieldVille
				.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Ville", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		// -------------------------------- éléments de panelCentre (tableau)
		// ------------------------------
		JScrollPane scrollPaneLocataires = new JScrollPane();
		panelCentre.add(scrollPaneLocataires, BorderLayout.CENTER);

		tableLocataires = new JTable();
		scrollPaneLocataires.setViewportView(tableLocataires);
		tableLocataires.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "ID Locataire", "Nom", "Prénom", "Téléphone",
						"Mail", "Date de naissance", "Ville de naissance", "Adresse", "Code Postal", "Ville" }) {
					Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class,
							String.class, LocalDate.class, String.class, String.class, String.class, String.class };

					@Override
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}

					boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false,
							false, false };

					@Override
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});

		gestionTable = new GestionTableFenLocataire(this);
		this.tableLocataires.getSelectionModel().addListSelectionListener(this.gestionTable);

		// -------------------------------- éléments de panelBas (quitter)
		// ------------------------------
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this.gestionClic);
		panelBas.add(btnQuitter);

		/* J'invoque le DaoLocataire pour pouvoir avoir toutes les données de la bd */
		DaoLocataire loc = new DaoLocataire();
		Collection<Locataire> locataires;
		try {
			locataires = loc.findAll();// je récupère toutes ces locataires
			gestionTable.appliquerFiltres(locataires);// j'applique les filtres
			gestionTable.activerFiltresAutomatiques(locataires);// je les mets en écoute pour qu'à chaque changement les
																// filtres s'effectuent
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Récupère le contrôleur des événements des boutons.
	 * 
	 * @return le contrôleur des événements des boutons
	 */
	public GestionFenLocataire getGestionClic() {
		return gestionClic;
	}

	public void setGestionClic(GestionFenLocataire gestionClic) {
		this.gestionClic = gestionClic;
	}

	/**
	 * Récupère la table des locataires.
	 * 
	 * @return la table des locataires
	 */
	public JTable getTableLocataires() {
		return tableLocataires;
	}

	/**
	 * Récupère le contrôleur des événements de la table.
	 * 
	 * @return le contrôleur des événements de la table
	 */
	public GestionTableFenLocataire getGestionTable() {
		return gestionTable;
	}

	public void setTableLocataires(JTable tableLocataires) {
		this.tableLocataires = tableLocataires;
	}

	/**
	 * Récupère le champ de texte de l'adresse.
	 * 
	 * @return le champ de texte de l'adresse
	 */
	public JTextField getTextFieldAdresse() {
		return textFieldAdresse;
	}

	/**
	 * Récupère le champ de texte du code postal.
	 * 
	 * @return le champ de texte du code postal
	 */
	public JTextField getTextFieldCP() {
		return textFieldCP;
	}

	/**
	 * Récupère le champ de texte de la ville.
	 * 
	 * @return le champ de texte de la ville
	 */
	public JTextField getTextFieldVille() {
		return textFieldVille;
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
	 * Récupère le champ de texte du prénom.
	 * 
	 * @return le champ de texte du prénom
	 */
	public JTextField getTextFieldPrenom() {
		return textFieldPrenom;
	}
}
