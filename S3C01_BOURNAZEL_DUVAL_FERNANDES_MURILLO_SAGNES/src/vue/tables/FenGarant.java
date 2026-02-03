package vue.tables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

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

import controleur.tables.fen_garant.GestionFenGarant;
import controleur.tables.fen_garant.GestionTableFenGarant;
import vue.Polices;

/**
 * Fenêtre interne de gestion des garants. Affiche une table des garants avec un
 * filtre par ID. Permet d'ajouter et de supprimer des garants.
 */
public class FenGarant extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel contentPane; // Panel principal de la fenêtre
	private JTable tableGarant; // Table affichant la liste des garants
	private JTextField textFieldID; // Filtre par identifiant
	private JPanel panelBas; // Panel du bas contenant les boutons
	private JPanel panelBouton; // Panel pour le bouton d'ajout
	private JButton btnAjouterGarant; // Bouton pour ajouter un garant
	private JButton btnSupprimer; // Bouton pour supprimer un garant
	private DefaultTableModel modeleTableGarant; // Modèle de la table des garants

	// Contrôleurs
	private transient GestionFenGarant gestionClic; // Contrôleur des événements des boutons
	private transient GestionTableFenGarant gestionTable; // Contrôleur des événements de la table

	/**
	 * Constructeur de la fenêtre de gestion des garants. Initialise l'interface
	 * graphique avec une table des garants, des filtres de recherche et des boutons
	 * d'action.
	 */
	public FenGarant() {
		this.gestionClic = new GestionFenGarant(this);
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

		panelBas = new JPanel(); // Ce panel contient le bouton quitter
		FlowLayout flowLayout = (FlowLayout) panelBas.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelBas, BorderLayout.SOUTH);

		// Panel des filtres de recherche
		JPanel panelfiltre = new JPanel();
		panelHaut.add(panelfiltre, BorderLayout.SOUTH);
		panelfiltre.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 2), "Filtres",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.BOLD, 12), Color.black));
		panelfiltre.setLayout(new GridLayout(0, 4, 0, 0));

		// ----------------------------- éléments de panelHaut (titre et bouton)
		// ----------------------
		JLabel lblTitre = new JLabel("Garants");
		panelHaut.add(lblTitre, BorderLayout.WEST);
		lblTitre.setFont(Polices.TITRE.getFont());

		textFieldID = new JTextField();
		panelfiltre.add(textFieldID);
		textFieldID.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "ID", TitledBorder.LEFT,
				TitledBorder.TOP, new Font("SansSerif", Font.CENTER_BASELINE, 10), Color.black));

		panelBouton = new JPanel();
		panelHaut.add(panelBouton, BorderLayout.EAST);

		btnAjouterGarant = new JButton("Ajouter un garant");
		btnAjouterGarant.addActionListener(this.gestionClic);
		panelBouton.add(btnAjouterGarant);

		// -------------------------------- éléments de panelCentre (tableau)
		// ------------------------------
		JScrollPane scrollPaneBienLouable = new JScrollPane();
		panelCentre.add(scrollPaneBienLouable, BorderLayout.CENTER);

		tableGarant = new JTable();
		scrollPaneBienLouable.setViewportView(tableGarant);
		tableGarant.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Adresse", "Mail", "T\u00E9l\u00E9phone" }

				) {
					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				});

		JPanel panelSupprimer = new JPanel();
		panelCentre.add(panelSupprimer, BorderLayout.SOUTH);

		btnSupprimer = new JButton("Supprimer le garant sélectionné");
		panelSupprimer.add(btnSupprimer);
		btnSupprimer.setEnabled(false);
		btnSupprimer.addActionListener(gestionClic);
		modeleTableGarant = (DefaultTableModel) tableGarant.getModel();

		// -------------------------------- éléments de panelBas (quitter)
		// ------------------------------
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this.gestionClic);
		panelBas.add(btnQuitter);
		gestionTable = new GestionTableFenGarant(this);

		textFieldID.getDocument().addDocumentListener(gestionTable);
		gestionTable.appliquerFiltres();
		this.tableGarant.getSelectionModel().addListSelectionListener(this.gestionTable);
	}

	/**
	 * Récupère la table des garants.
	 * 
	 * @return la table des garants
	 */
	public JTable getTableGarant() {
		return tableGarant;
	}

	public void setTableGarant(JTable tableGarant) {
		this.tableGarant = tableGarant;
	}

	/**
	 * Récupère le champ de texte de l'identifiant.
	 * 
	 * @return le champ de texte de l'identifiant
	 */
	public JTextField getTextFieldID() {
		return textFieldID;
	}

	public void setTextFieldID(JTextField textFieldID) {
		this.textFieldID = textFieldID;
	}

	/**
	 * Récupère le bouton d'ajout de garant.
	 * 
	 * @return le bouton d'ajout de garant
	 */
	public JButton getBtnAjouterGarant() {
		return btnAjouterGarant;
	}

	public void setBtnAjouterGarant(JButton btnAjouterGarant) {
		this.btnAjouterGarant = btnAjouterGarant;
	}

	/**
	 * Récupère le modèle de la table des garants.
	 * 
	 * @return le modèle de la table des garants
	 */
	public DefaultTableModel getModeleTableGarant() {
		return modeleTableGarant;
	}

	public void setModeleTableGarant(DefaultTableModel modeleTableGarant) {
		this.modeleTableGarant = modeleTableGarant;
	}

	/**
	 * Récupère le contrôleur des événements des boutons.
	 * 
	 * @return le contrôleur des événements des boutons
	 */
	public GestionFenGarant getGestionClic() {
		return gestionClic;
	}

	public void setGestionClic(GestionFenGarant gestionClic) {
		this.gestionClic = gestionClic;
	}

	/**
	 * Récupère le bouton de suppression.
	 * 
	 * @return le bouton de suppression
	 */
	public JButton getBtnSupprimer() {
		return btnSupprimer;
	}

	public void setBtnSupprimer(JButton btnSupprimer) {
		this.btnSupprimer = btnSupprimer;
	}

	/**
	 * Récupère le contrôleur des événements de la table.
	 * 
	 * @return le contrôleur des événements de la table
	 */
	public GestionTableFenGarant getGestionTable() {
		return gestionTable;
	}

	public void setGestionTable(GestionTableFenGarant gestionTable) {
		this.gestionTable = gestionTable;
	}

}
