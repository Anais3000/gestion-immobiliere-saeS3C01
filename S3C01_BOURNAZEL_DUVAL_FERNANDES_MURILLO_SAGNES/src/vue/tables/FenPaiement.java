package vue.tables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
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
import javax.swing.table.TableColumn;

import controleur.Outils;
import controleur.tables.GestionFenPaiement;
import controleur.tables.GestionTablePaiement;
import vue.Polices;

/**
 * Fenêtre interne de gestion des paiements. Affiche une table des paiements
 * avec des filtres par montant, dates et nom de locataire. Permet d'ajouter et
 * de supprimer des paiements.
 */
public class FenPaiement extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel contentPane; // Panel principal de la fenêtre
	private JTable tablePaiements; // Table affichant la liste des paiements
	private JTextField textFieldDateDebut; // Filtre par date de début
	private JTextField textFieldDateFin; // Filtre par date de fin
	private JPanel panelBas; // Panel du bas contenant les boutons
	private JPanel panelMontantMin; // Panel pour le spinner montant minimum
	private JSpinner spinnerMontantMin; // Spinner pour le montant minimum
	private JPanel panelMontantMax; // Panel pour le spinner montant maximum
	private JSpinner spinnerMontantMax; // Spinner pour le montant maximum
	private JTextField textFieldNomLoc; // Filtre par nom de locataire
	private JButton btnSupprimer; // Bouton pour supprimer un paiement

	// Contrôleurs
	private transient GestionFenPaiement gestionClic; // Contrôleur des événements des boutons
	private transient GestionTablePaiement gestionTable; // Contrôleur des événements de la table

	/**
	 * Constructeur de la fenêtre de gestion des paiements. Initialise l'interface
	 * graphique avec une table des paiements, des filtres de recherche et des
	 * boutons d'action.
	 */
	public FenPaiement() {
		gestionClic = new GestionFenPaiement(this);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// ------------------------------- Les panels
		// ------------------------------------
		JPanel panelHaut = new JPanel(); // Ce panel contient le titre et le bouton "ajouter un paiement"
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setLayout(new BorderLayout(0, 0));
		panelHaut.setBorder(new EmptyBorder(20, 10, 20, 10)); // J'ajoute des marges pour que ce soit plus joli

		JPanel panelCentre = new JPanel();
		getContentPane().add(panelCentre, BorderLayout.CENTER);
		panelCentre.setLayout(new BorderLayout(0, 0));

		// Panel du bas pour les boutons
		panelBas = new JPanel();
		getContentPane().add(panelBas, BorderLayout.SOUTH);

		// Panel des filtres de recherche
		JPanel panelfiltre = new JPanel();
		panelHaut.add(panelfiltre, BorderLayout.SOUTH);
		panelfiltre.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 2), "Filtres",
				TitledBorder.LEFT, TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.BOLD, 12), Color.black));
		panelfiltre.setLayout(new GridLayout(0, 4, 0, 0));

		// ----------------------------- éléments de panelHaut (titre et bouton)
		// ----------------------
		JLabel lblTitre = new JLabel("Paiements");
		panelHaut.add(lblTitre, BorderLayout.WEST);
		lblTitre.setFont(Polices.TITRE.getFont());

		JButton btnAjouterPaiement = new JButton("Ajouter un paiement");
		btnAjouterPaiement.addActionListener(this.gestionClic);
		panelHaut.add(btnAjouterPaiement, BorderLayout.EAST);

		// Filtres de recherche
		panelMontantMin = new JPanel();
		panelMontantMin.setBackground(new Color(255, 255, 255));
		panelMontantMin.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Montant minimum", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(panelMontantMin);
		panelMontantMin.setLayout(new BorderLayout(0, 0));

		spinnerMontantMin = new JSpinner();
		spinnerMontantMin = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 1.0));
		panelMontantMin.add(spinnerMontantMin);

		panelMontantMax = new JPanel();
		panelMontantMax.setBackground(new Color(255, 255, 255));
		panelMontantMax.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Montant maximum", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(panelMontantMax);
		panelMontantMax.setLayout(new BorderLayout(0, 0));

		spinnerMontantMax = new JSpinner();
		spinnerMontantMax = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 1.0));
		panelMontantMax.add(spinnerMontantMax);

		textFieldDateDebut = new JTextField();
		panelfiltre.add(textFieldDateDebut);
		textFieldDateDebut.setColumns(10);
		textFieldDateDebut.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Date début", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		textFieldDateFin = new JTextField();
		textFieldDateFin.setColumns(10);
		textFieldDateFin.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Date fin", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(textFieldDateFin);

		textFieldNomLoc = new JTextField();
		textFieldNomLoc.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Nom Locataire",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelfiltre.add(textFieldNomLoc);
		textFieldNomLoc.setColumns(10);

		// -------------------------------- éléments de panelBas (tableau)
		// ------------------------------
		JScrollPane scrollPanePaiement = new JScrollPane();
		panelCentre.add(scrollPanePaiement, BorderLayout.CENTER);

		tablePaiements = new JTable();
		scrollPanePaiement.setViewportView(tablePaiements);
		tablePaiements.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Id Paiement", "Date Paiement",
				"Locataire", "logement", "intervention", "Libellé", "Émis/Reçu", "Montant" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		// Masquer la colonne ID
		TableColumn column = tablePaiements.getColumnModel().getColumn(0);
		tablePaiements.getColumnModel().removeColumn(column);

		gestionTable = new GestionTablePaiement(this);
		this.tablePaiements.getSelectionModel().addListSelectionListener(this.gestionTable);

		// -------------------------------- éléments de panelBas (quitter)
		// ------------------------------
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this.gestionClic);
		panelBas.setLayout(new BorderLayout(0, 0));
		panelBas.add(btnQuitter, BorderLayout.EAST);

		btnSupprimer = new JButton("Supprimer le paiement sélectionné");
		btnSupprimer.addActionListener(gestionClic);
		btnSupprimer.setEnabled(false);
		panelBas.add(btnSupprimer, BorderLayout.WEST);

		// Activation des filtres automatiques
		gestionTable.activerFiltresAutomatiques();
		gestionTable.activerFiltresAutomatiques();
	}

	/**
	 * Récupère la table des paiements.
	 * 
	 * @return la table des paiements
	 */
	public JTable getTablePaiements() {
		return tablePaiements;
	}

	/**
	 * Récupère le contrôleur des événements de la table.
	 * 
	 * @return le contrôleur des événements de la table
	 */
	public GestionTablePaiement getGestionTable() {
		return gestionTable;
	}

	/**
	 * Récupère le champ de texte de la date de début.
	 * 
	 * @return le champ de texte de la date de début
	 */
	public JTextField getTextFieldDateDebut() {
		return textFieldDateDebut;
	}

	/**
	 * Récupère le champ de texte de la date de fin.
	 * 
	 * @return le champ de texte de la date de fin
	 */
	public JTextField getTextFieldDateFin() {
		return textFieldDateFin;
	}

	/**
	 * Récupère le spinner du montant minimum.
	 * 
	 * @return le spinner du montant minimum
	 */
	public JSpinner getSpinnerMontantMin() {
		return spinnerMontantMin;
	}

	/**
	 * Récupère le spinner du montant maximum.
	 * 
	 * @return le spinner du montant maximum
	 */
	public JSpinner getSpinnerMontantMax() {
		return spinnerMontantMax;
	}

	/**
	 * Récupère le champ de texte du nom de locataire.
	 * 
	 * @return le champ de texte du nom de locataire
	 */
	public JTextField getTextFieldNomLoc() {
		return textFieldNomLoc;
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

	public void setTablePaiements(JTable tablePaiements) {
		this.tablePaiements = tablePaiements;
	}

}
