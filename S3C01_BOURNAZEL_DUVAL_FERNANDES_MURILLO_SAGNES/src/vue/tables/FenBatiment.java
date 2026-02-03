package vue.tables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
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
import controleur.tables.fen_batiment.GestionFenBatiment;
import controleur.tables.fen_batiment.GestionTableFenBatiment;
import vue.Polices;

/**
 * Fenêtre interne de gestion des bâtiments. Affiche une table des bâtiments
 * avec des filtres pour rechercher par identifiant, dates, adresse, code postal
 * et ville. Permet d'ajouter de nouveaux bâtiments et de consulter les
 * informations détaillées.
 */
public class FenBatiment extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel contentPane; // Panel principal de la fenêtre
	private JTable tableBatiment; // Table affichant la liste des bâtiments
	private JTextField textDateDebut; // Filtre par date de début
	private JTextField textDateFin; // Filtre par date de fin
	private JTextField textFieldAdresse; // Filtre par adresse
	private JTextField textFieldCP; // Filtre par code postal
	private JTextField textFieldVille; // Filtre par ville
	private JTextField textFieldID; // Filtre par identifiant
	private JPanel panelBas; // Panel du bas contenant les boutons
	private JPanel panel; // Panel pour le bouton d'ajout
	private JButton btnAjouterBatiment; // Bouton pour ajouter un bâtiment

	// Contrôleurs
	private transient GestionFenBatiment gestionClic; // Contrôleur des événements des boutons
	private transient GestionTableFenBatiment gestionTable; // Contrôleur des événements de la table

	/**
	 * Constructeur de la fenêtre de gestion des bâtiments. Initialise l'interface
	 * graphique avec une table des bâtiments, des filtres de recherche et des
	 * boutons d'action.
	 */
	public FenBatiment() {

		gestionClic = new GestionFenBatiment(this);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// ------------------------------- Les panels
		// ------------------------------------
		JPanel panelHaut = new JPanel();
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setLayout(new BorderLayout(0, 0));
		panelHaut.setBorder(new EmptyBorder(20, 10, 20, 10));

		JPanel panelCentre = new JPanel();
		getContentPane().add(panelCentre, BorderLayout.CENTER);
		panelCentre.setLayout(new BorderLayout(0, 0));

		// Panel du bas pour les boutons
		panelBas = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBas.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelBas, BorderLayout.SOUTH);

		// Panel des filtres de recherche
		JPanel panelfiltre = new JPanel();
		panelHaut.add(panelfiltre, BorderLayout.SOUTH);
		panelfiltre.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 2), "Filtres",
				TitledBorder.LEFT, TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.BOLD, 12), Color.black));
		panelfiltre.setLayout(new GridLayout(0, 4, 0, 0));

		// ----------------------------- filtres ----------------------
		textFieldID = new JTextField();
		panelfiltre.add(textFieldID);
		textFieldID.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Identifiant", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		// ----------------------------- Dates en JFormattedTextField
		// ----------------------

		textDateDebut = new JTextField();
		textDateDebut.setText("DD-MM-AAAA");
		textDateDebut.setToolTipText("");
		textDateDebut.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Date début", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(textDateDebut);

		textDateFin = new JTextField();
		textDateFin.setText("DD-MM-AAAA");
		textDateFin.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Date fin", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		panelfiltre.add(textDateFin);

		// Filtres d'adresse
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

		// Titre de la fenêtre
		JLabel lblTitre = new JLabel("Bâtiments");
		panelHaut.add(lblTitre, BorderLayout.WEST);
		lblTitre.setFont(Polices.TITRE.getFont());

		// Bouton d'ajout de bâtiment
		panel = new JPanel();
		panelHaut.add(panel, BorderLayout.EAST);

		btnAjouterBatiment = new JButton("Ajouter un bâtiment");
		btnAjouterBatiment.addActionListener(gestionClic);
		panel.add(btnAjouterBatiment);

		// Configuration de la table des bâtiments
		JScrollPane scrollPaneBatiments = new JScrollPane();
		panelCentre.add(scrollPaneBatiments, BorderLayout.CENTER);

		tableBatiment = new JTable();
		scrollPaneBatiments.setViewportView(tableBatiment);
		tableBatiment.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Id", "Date de construction", "Adresse", "Code postal", "Ville" }) {

			Class[] columnTypes = new Class[] { String.class, LocalDate.class, String.class, String.class,
					String.class };

			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		// Configuration des contrôleurs et filtres
		gestionTable = new GestionTableFenBatiment(this);
		this.tableBatiment.addMouseListener(this.gestionTable.getMouseListener());

		// Bouton quitter
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this.gestionClic);
		panelBas.add(btnQuitter);

		// Activation des filtres automatiques
		gestionTable.appliquerFiltres();
		gestionTable.activerFiltresAutomatiques();

	}

	// GETTERS ---------------------------------------------------------------------

	/**
	 * Récupère la table des bâtiments.
	 * 
	 * @return la table des bâtiments
	 */
	public JTable getTableBatiment() {
		return tableBatiment;
	}

	/**
	 * Récupère le contrôleur de la table des bâtiments.
	 * 
	 * @return le contrôleur de la table
	 */
	public GestionTableFenBatiment getGestionTable() {
		return this.gestionTable;
	}

	/**
	 * Récupère le champ de texte de la date de début.
	 * 
	 * @return le champ de texte de la date de début
	 */
	public JTextField getTextDateDebut() {
		return textDateDebut;
	}

	/**
	 * Récupère le champ de texte de la date de fin.
	 * 
	 * @return le champ de texte de la date de fin
	 */
	public JTextField getTextDateFin() {
		return textDateFin;
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
	 * Récupère le champ de texte de l'identifiant.
	 * 
	 * @return le champ de texte de l'identifiant
	 */
	public JTextField getTextFieldID() {
		return textFieldID;
	}

}
