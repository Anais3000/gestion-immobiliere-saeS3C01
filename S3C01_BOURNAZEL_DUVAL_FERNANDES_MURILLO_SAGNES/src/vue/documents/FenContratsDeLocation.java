package vue.documents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import controleur.documents.GestionFenContratsDeLocation;
import controleur.documents.GestionTableContratsDeLocation;
import vue.Polices;

/**
 * Fenêtre de gestion et consultation des contrats de location (baux). Affiche
 * une table filtrable des contrats avec possibilité de filtrer par période,
 * locataire, bâtiment et bien louable. Permet de consulter les informations de
 * chaque bail (dates, dépôt de garantie, etc.).
 */
public class FenContratsDeLocation extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JTextField textFieldDateDebut; // Champ pour filtrer par date de début minimale
	private JTextField textFieldDateFin; // Champ pour filtrer par date de fin maximale
	private JTextField textFieldLocataire; // Champ pour filtrer par nom de locataire
	private JComboBox<String> comboBoxBienLouable; // ComboBox pour filtrer par bien louable
	private JComboBox<String> comboBoxBatiment; // ComboBox pour filtrer par bâtiment
	private JPanel panelTable; // Panel contenant la table
	private JTable table; // Table affichant les contrats de location
	private JScrollPane scrollPane; // ScrollPane pour la table
	private JPanel panelQuitter; // Panel contenant le bouton quitter
	private JButton btnQuitter; // Bouton pour fermer la fenêtre

	// Contrôleurs
	private transient GestionFenContratsDeLocation gestionClic; // Contrôleur des événements de la fenêtre
	private transient GestionTableContratsDeLocation gestionTable; // Contrôleur de la table et des filtres //
																	// Contrôleur de la table et des filtres

	/**
	 * Constructeur de la fenêtre des contrats de location. Crée l'interface avec
	 * les filtres (dates, locataire, bâtiment, bien louable), la table d'affichage
	 * des baux et configure automatiquement les listeners pour le filtrage
	 * dynamique des données.
	 */
	public FenContratsDeLocation() {
		// Initialisation du contrôleur de la fenêtre
		this.gestionClic = new GestionFenContratsDeLocation(this);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// Panel du haut : titre et filtres
		JPanel panelHaut = new JPanel(); // Ce panel contient le titre et le bouton "ajouter un paiement"
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setLayout(new BorderLayout(0, 0));
		panelHaut.setBorder(new EmptyBorder(20, 10, 20, 10));

		// Panel contenant les cinq filtres (dates, locataire, bâtiment, bien louable)
		JPanel panelfiltre = new JPanel();
		panelHaut.add(panelfiltre, BorderLayout.CENTER);
		panelfiltre.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 2), "Filtres",
				TitledBorder.LEFT, TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.BOLD, 12), Color.black));
		panelfiltre.setLayout(new GridLayout(0, 4, 0, 0));

		// Titre de la fenêtre
		JLabel lblTitre = new JLabel("Contrats de location");
		panelHaut.add(lblTitre, BorderLayout.NORTH);
		lblTitre.setFont(Polices.TITRE.getFont());

		// Filtre par date de début minimale
		textFieldDateDebut = new JTextField();
		panelfiltre.add(textFieldDateDebut);
		textFieldDateDebut.setColumns(10);
		textFieldDateDebut.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Date min", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		// Filtre par date de fin maximale
		textFieldDateFin = new JTextField();
		textFieldDateFin.setColumns(10);
		textFieldDateFin.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Date max", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(textFieldDateFin);

		// Filtre par nom du locataire
		textFieldLocataire = new JTextField();
		textFieldLocataire.setColumns(10);
		textFieldLocataire.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Locataire", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(textFieldLocataire);

		// Filtre par bâtiment
		comboBoxBatiment = new JComboBox<>();
		panelfiltre.add(comboBoxBatiment);
		comboBoxBatiment.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Batiment", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		// Filtre par bien louable
		comboBoxBienLouable = new JComboBox<>();
		panelfiltre.add(comboBoxBienLouable);
		comboBoxBienLouable.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Bien louable", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		// ===== TABLE DES CONTRATS DE LOCATION =====
		panelTable = new JPanel();
		getContentPane().add(panelTable, BorderLayout.CENTER);
		panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.X_AXIS));

		scrollPane = new JScrollPane();
		panelTable.add(scrollPane);

		// Création de la table avec 6 colonnes (dates, bâtiment, bien, locataire,
		// dépôt)
		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Date de d\u00E9but", "Date de fin",
				"B\u00E2timent", "Bien louable", "Locataire", "D\u00E9pot de garantie" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(84);
		scrollPane.setViewportView(table);

		// ===== PANEL DU BAS : BOUTON QUITTER =====
		panelQuitter = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelQuitter.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelQuitter, BorderLayout.SOUTH);

		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this.gestionClic);
		btnQuitter.setHorizontalTextPosition(SwingConstants.CENTER);
		panelQuitter.add(btnQuitter);

		// Initialisation du gestionnaire de table (gestion des filtres)
		gestionTable = new GestionTableContratsDeLocation(this);

		// Listeners
		// Les listeners détectent les changements dans les champs de filtrage et
		// mettent à jour la table automatiquement
		textFieldDateDebut.getDocument().addDocumentListener(gestionTable);
		comboBoxBienLouable.addActionListener(gestionTable);
		textFieldLocataire.getDocument().addDocumentListener(gestionTable);
		comboBoxBatiment.addActionListener(gestionTable);
		textFieldDateFin.getDocument().addDocumentListener(gestionTable);
	}

	/**
	 * Récupère la table affichant les contrats de location.
	 * 
	 * @return la table des contrats de location
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * Modifie la table affichant les contrats de location.
	 * 
	 * @param table la nouvelle table des contrats
	 */
	public void setTable(JTable table) {
		this.table = table;
	}

	/**
	 * Récupère le gestionnaire de la table des contrats de location.
	 * 
	 * @return le gestionnaire de table
	 */
	public GestionTableContratsDeLocation getGestionTable() {
		return gestionTable;
	}

	/**
	 * Modifie le gestionnaire de la table des contrats de location.
	 * 
	 * @param gestionTable le nouveau gestionnaire de table
	 */
	public void setGestionTable(GestionTableContratsDeLocation gestionTable) {
		this.gestionTable = gestionTable;
	}

	/**
	 * Récupère le champ de texte pour saisir la date de début minimale du filtre.
	 * 
	 * @return le champ de texte de la date de début
	 */
	public JTextField getTextFieldDateDebut() {
		return textFieldDateDebut;
	}

	/**
	 * Modifie le champ de texte pour saisir la date de début minimale du filtre.
	 * 
	 * @param textFieldDateDebut le nouveau champ de texte de la date de début
	 */
	public void setTextFieldDateDebut(JTextField textFieldDateDebut) {
		this.textFieldDateDebut = textFieldDateDebut;
	}

	/**
	 * Récupère la combo box de sélection du bien louable pour le filtrage.
	 * 
	 * @return la combo box de filtrage par bien louable
	 */
	public JComboBox<String> getComboBoxBienLouable() {
		return comboBoxBienLouable;
	}

	/**
	 * Modifie la combo box de sélection du bien louable pour le filtrage.
	 * 
	 * @param comboBoxBienLouable la nouvelle combo box de bien louable
	 */
	public void setComboBoxBienLouable(JComboBox<String> comboBoxBienLouable) {
		this.comboBoxBienLouable = comboBoxBienLouable;
	}

	/**
	 * Récupère la combo box de sélection du bâtiment pour le filtrage.
	 * 
	 * @return la combo box de filtrage par bâtiment
	 */
	public JComboBox<String> getComboBoxBatiment() {
		return comboBoxBatiment;
	}

	/**
	 * Modifie la combo box de sélection du bâtiment pour le filtrage.
	 * 
	 * @param comboBoxBatiment la nouvelle combo box de bâtiment
	 */
	public void setComboBoxBatiment(JComboBox<String> comboBoxBatiment) {
		this.comboBoxBatiment = comboBoxBatiment;
	}

	/**
	 * Récupère le champ de texte pour saisir la date de fin maximale du filtre.
	 * 
	 * @return le champ de texte de la date de fin
	 */
	public JTextField getTextFieldDateFin() {
		return textFieldDateFin;
	}

	/**
	 * Modifie le champ de texte pour saisir la date de fin maximale du filtre.
	 * 
	 * @param textFieldDateFin le nouveau champ de texte de la date de fin
	 */
	public void setTextFieldDateFin(JTextField textFieldDateFin) {
		this.textFieldDateFin = textFieldDateFin;
	}

	/**
	 * Récupère le champ de texte pour saisir le nom du locataire pour le filtrage.
	 * 
	 * @return le champ de texte du locataire
	 */
	public JTextField getTextFieldLocataire() {
		return textFieldLocataire;
	}

	/**
	 * Modifie le champ de texte pour saisir le nom du locataire pour le filtrage.
	 * 
	 * @param textFieldLocataire le nouveau champ de texte du locataire
	 */
	public void setTextFieldLocataire(JTextField textFieldLocataire) {
		this.textFieldLocataire = textFieldLocataire;
	}

}
