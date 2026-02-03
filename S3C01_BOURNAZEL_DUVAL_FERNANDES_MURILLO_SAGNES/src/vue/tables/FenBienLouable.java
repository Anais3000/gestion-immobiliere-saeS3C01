package vue.tables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import controleur.tables.fen_bien_louable.GestionFenBienLouable;
import controleur.tables.fen_bien_louable.GestionTableFenBienLouable;
import vue.Polices;

/**
 * Fenêtre interne de gestion des biens louables. Affiche une table des biens
 * louables avec des filtres pour rechercher par adresse, code postal, ville,
 * nombre de pièces, type de bien et bâtiment. Permet d'ajouter de nouveaux
 * biens louables et de consulter les informations détaillées.
 */
public class FenBienLouable extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel contentPane; // Panel principal de la fenêtre
	private JTable tableBienLouable; // Table affichant la liste des biens louables
	private JTextField textFieldAdresse; // Filtre par adresse
	private JTextField textFieldCP; // Filtre par code postal
	private JTextField textFieldVille; // Filtre par ville
	private JTextField textFieldTypeBien; // Filtre par type de bien (non utilisé dans l'interface)
	private JTextField textFieldSurface; // Filtre par surface
	private JComboBox<String> comboBoxTypeBien; // ComboBox pour filtrer par type de bien
	private JComboBox<String> comboBoxBatiment; // ComboBox pour filtrer par bâtiment
	private JPanel panelBas; // Panel du bas contenant le bouton quitter
	private JPanel panelBouton; // Panel pour les boutons d'action
	private JPanel panelNbPieces; // Panel pour le spinner du nombre de pièces
	private JButton btnAjouterBienLouable; // Bouton pour ajouter un bien louable
	private JButton btnIRL; // Bouton pour ajouter l'IRL
	private JSpinner spinner; // Spinner pour filtrer par nombre de pièces
	private JLabel lblValeurIRL; // Label affichant la valeur actuelle de l'IRL

	// Contrôleurs
	private transient GestionFenBienLouable gestionClic; // Contrôleur des événements des boutons
	private transient GestionTableFenBienLouable gestionTable; // Contrôleur des événements de la table

	/**
	 * Constructeur de la fenêtre de gestion des biens louables. Initialise
	 * l'interface graphique avec une table des biens louables, des filtres de
	 * recherche avancés et des boutons d'action.
	 */
	public FenBienLouable() {
		gestionClic = new GestionFenBienLouable(this);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// ------------------------------- Les panels
		// ------------------------------------
		JPanel panelHaut = new JPanel(); // Ce panel contient le titre et le bouton "ajouter un bien louable"
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setLayout(new BorderLayout(0, 0));
		panelHaut.setBorder(new EmptyBorder(20, 10, 20, 10)); // J'ajoute des marges pour que ce soit plus joli

		JPanel panelCentre = new JPanel();
		getContentPane().add(panelCentre, BorderLayout.CENTER);
		panelCentre.setLayout(new BorderLayout(0, 0));

		// Panel du bas pour le bouton quitter
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
		JLabel lblTitre = new JLabel("Biens louables");
		panelHaut.add(lblTitre, BorderLayout.WEST);
		lblTitre.setFont(Polices.TITRE.getFont());

		// Configuration des filtres de recherche
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

		// Filtre par nombre de pièces avec spinner
		panelNbPieces = new JPanel();
		panelNbPieces.setBackground(new Color(255, 255, 255));
		panelNbPieces.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Nombre de Pièces", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(panelNbPieces);
		panelNbPieces.setLayout(new BorderLayout(0, 0));

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
		panelNbPieces.add(spinner);

		// ComboBox pour filtrer par type de bien
		comboBoxTypeBien = new JComboBox<>();
		comboBoxTypeBien.setModel(new DefaultComboBoxModel<>(new String[] { "Tous", "Garage", "Logement" }));
		panelfiltre.add(comboBoxTypeBien);
		comboBoxTypeBien.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Type bien", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		// ComboBox pour filtrer par bâtiment
		comboBoxBatiment = new JComboBox<>();
		panelfiltre.add(comboBoxBatiment);
		comboBoxBatiment.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Bâtiment", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		// Filtre par surface
		textFieldSurface = new JTextField();
		textFieldSurface.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Surface", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		// Panel des boutons d'action
		panelBouton = new JPanel();
		panelHaut.add(panelBouton, BorderLayout.EAST);

		btnAjouterBienLouable = new JButton("Ajouter un bien louable");
		btnAjouterBienLouable.addActionListener(this.gestionClic);

		btnIRL = new JButton("Ajouter l'IRL");
		btnIRL.addActionListener(gestionClic);

		// Label affichant la valeur actuelle de l'IRL
		lblValeurIRL = new JLabel("Valeur actuelle de l'IRL : [valeur]");
		lblValeurIRL.setFont(new Font("Tahoma", Font.BOLD, 10));
		panelBouton.add(lblValeurIRL);
		panelBouton.add(btnIRL);
		panelBouton.add(btnAjouterBienLouable);

		// -------------------------------- éléments de panelCentre (tableau)
		// ------------------------------
		JScrollPane scrollPaneBienLouable = new JScrollPane();
		panelCentre.add(scrollPaneBienLouable, BorderLayout.CENTER);

		tableBienLouable = new JTable();
		scrollPaneBienLouable.setViewportView(tableBienLouable);
		tableBienLouable.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null }, }, new String[] { "ID", "Adresse",
						"Ville", "Code postal", "Type de bien", "Loyer", "Nombre Pi\u00E8ces", "B\u00E2timent" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class, String.class,
					String.class, Float.class, String.class };

			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // tableau NON éditable
			}
		});

		// Configuration des contrôleurs et filtres
		gestionTable = new GestionTableFenBienLouable(this);
		this.tableBienLouable.addMouseListener(this.gestionTable.getMouseListener());

		// -------------------------------- éléments de panelBas (quitter)
		// ------------------------------
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this.gestionClic);
		panelBas.add(btnQuitter);

		// Chargement des données et activation des filtres
		try {
			gestionTable.ecrireLigneTableBienLouable();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		gestionTable.appliquerFiltres();
		gestionTable.activerFiltresAutomatiques();

		gestionClic.afficherIRL();
	}

	/**
	 * Récupère le contrôleur des événements des boutons.
	 * 
	 * @return le contrôleur des boutons
	 */
	public GestionFenBienLouable getGestionClic() {
		return gestionClic;
	}

	/**
	 * Modifie le contrôleur des événements des boutons.
	 * 
	 * @param gestionClic le nouveau contrôleur des boutons
	 */
	public void setGestionClic(GestionFenBienLouable gestionClic) {
		this.gestionClic = gestionClic;
	}

	/**
	 * Récupère la table des biens louables.
	 * 
	 * @return la table des biens louables
	 */
	public JTable getTableBienLouable() {
		return tableBienLouable;
	}

	/**
	 * Modifie la table des biens louables.
	 * 
	 * @param tableBienLouable la nouvelle table des biens louables
	 */
	public void setTableBienLouable(JTable tableBienLouable) {
		this.tableBienLouable = tableBienLouable;
	}

	/**
	 * Récupère le contrôleur de la table des biens louables.
	 * 
	 * @return le contrôleur de la table
	 */
	public GestionTableFenBienLouable getGestionTable() {
		return gestionTable;
	}

	/**
	 * Modifie le contrôleur de la table des biens louables.
	 * 
	 * @param gestionTable le nouveau contrôleur de la table
	 */
	public void setGestionTable(GestionTableFenBienLouable gestionTable) {
		this.gestionTable = gestionTable;
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
	 * Récupère le champ de texte du type de bien.
	 * 
	 * @return le champ de texte du type de bien
	 */
	public JTextField getTextFieldTypeBien() {
		return textFieldTypeBien;
	}

	/**
	 * Récupère le champ de texte de la surface.
	 * 
	 * @return le champ de texte de la surface
	 */
	public JTextField getTextFieldSurface() {
		return textFieldSurface;
	}

	/**
	 * Récupère la comboBox du type de bien.
	 * 
	 * @return la comboBox du type de bien
	 */
	public JComboBox<String> getComboBoxTypeBien() {
		return comboBoxTypeBien;
	}

	/**
	 * Récupère le spinner du nombre de pièces.
	 * 
	 * @return le spinner du nombre de pièces
	 */
	public JSpinner getSpinner() {
		return spinner;
	}

	/**
	 * Récupère la comboBox du bâtiment.
	 * 
	 * @return la comboBox du bâtiment
	 */
	public JComboBox<String> getComboBoxBatiment() {
		return comboBoxBatiment;
	}

	/**
	 * Modifie la comboBox du bâtiment.
	 * 
	 * @param comboBoxBatiment la nouvelle comboBox du bâtiment
	 */
	public void setComboBoxBatiment(JComboBox<String> comboBoxBatiment) {
		this.comboBoxBatiment = comboBoxBatiment;
	}

	/**
	 * Récupère le label de la valeur IRL.
	 * 
	 * @return le label de la valeur IRL
	 */
	public JLabel getLblValeurIRL() {
		return lblValeurIRL;
	}

	/**
	 * Modifie le label de la valeur IRL.
	 * 
	 * @param lblValeurIRL le nouveau label de la valeur IRL
	 */
	public void setLblValeurIRL(JLabel lblValeurIRL) {
		this.lblValeurIRL = lblValeurIRL;
	}

}
