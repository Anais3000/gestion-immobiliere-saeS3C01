package vue.documents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import controleur.documents.GestionFenDiagnostics;
import controleur.documents.GestionTableDiagnostics;
import vue.Polices;

/**
 * Fenêtre de gestion et consultation des diagnostics des biens louables.
 * Affiche une table filtrable des diagnostics avec possibilité de filtrer par
 * période, bâtiment, bien louable et nom du diagnostic. Permet de consulter les
 * détails d'un diagnostic sélectionné dans une zone de texte dédiée.
 */
public class FenDiagnostics extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	// Composants de filtrage
	private JTextField textFieldDateDebut; // Champ pour filtrer par date de début minimale
	private JTextField textFieldDateFin; // Champ pour filtrer par date de fin maximale
	private JTextField textFieldNom; // Champ pour filtrer par nom du diagnostic
	private JComboBox<String> comboBoxBienLouable; // ComboBox pour filtrer par bien louable
	private JComboBox<String> comboBoxBatiment; // ComboBox pour filtrer par bâtiment

	// Composants d'affichage
	private JPanel panelTable; // Panel principal contenant les tables
	private JTable tablePrincipale; // Table affichant la liste des diagnostics
	private JScrollPane scrollPanePrincipal; // ScrollPane pour la table principale
	private JScrollPane scrollPaneSecondaire; // ScrollPane pour la zone de détails
	private JLabel lblTableSecondaire; // Label "Détails" au-dessus de la zone de texte
	private JTextArea txtrDetails; // Zone de texte affichant les détails du diagnostic sélectionné
	private JPanel panelQuitter; // Panel contenant le bouton quitter
	private JButton btnQuitter; // Bouton pour fermer la fenêtre

	// Contrôleurs
	private transient GestionFenDiagnostics gestionClic; // Contrôleur des événements de la fenêtre
	private transient GestionTableDiagnostics gestionTable; // Contrôleur de la table et des filtres

	/**
	 * Constructeur de la fenêtre des diagnostics. Crée l'interface avec les filtres
	 * (dates, bâtiment, bien louable, nom), une table principale listant les
	 * diagnostics, une zone de détails affichant les informations complètes du
	 * diagnostic sélectionné, et charge automatiquement toutes les données depuis
	 * la base de données avec les filtres activés.
	 */
	public FenDiagnostics() {
		this.gestionClic = new GestionFenDiagnostics(this);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelHaut = new JPanel(); // Ce panel contient le titre et le bouton "ajouter un paiement"
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setLayout(new BorderLayout(0, 0));
		panelHaut.setBorder(new EmptyBorder(20, 10, 20, 10));

		// Titre de la fenêtre
		JLabel lblTitre = new JLabel("Diagnostics");
		panelHaut.add(lblTitre, BorderLayout.NORTH);
		lblTitre.setFont(Polices.TITRE.getFont());

		// Panel contenant les cinq filtres (dates, bâtiment, bien louable, nom)
		JPanel panelfiltre = new JPanel();
		panelHaut.add(panelfiltre, BorderLayout.CENTER);
		panelfiltre.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 2), "Filtres",
				TitledBorder.LEFT, TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.BOLD, 12), Color.black));
		panelfiltre.setLayout(new GridLayout(0, 4, 0, 0));

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

		// Filtre par bâtiment
		comboBoxBatiment = new JComboBox<>();
		panelfiltre.add(comboBoxBatiment);
		comboBoxBatiment.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Bâtiment", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		// Filtre par bien louable
		comboBoxBienLouable = new JComboBox<>();
		panelfiltre.add(comboBoxBienLouable);
		comboBoxBienLouable.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Bien louable", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		// Filtre par nom du diagnostic
		textFieldNom = new JTextField();
		textFieldNom.setColumns(10);
		textFieldNom
				.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Nom", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(textFieldNom);

		// ===== PANEL CENTRAL : TABLE PRINCIPALE ET ZONE DE DÉTAILS =====
		// Utilisation d'un GridBagLayout pour organiser verticalement : table
		// principale, label, zone de détails
		panelTable = new JPanel();
		getContentPane().add(panelTable, BorderLayout.CENTER);
		GridBagLayout gblPanelTable = new GridBagLayout();
		gblPanelTable.columnWidths = new int[] { 784, 0 };
		gblPanelTable.rowHeights = new int[] { 200, 40, 120 }; // Hauteurs pour table, label et détails
		gblPanelTable.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gblPanelTable.rowWeights = new double[] { 0.0, 1.0, 1.0 };
		panelTable.setLayout(gblPanelTable);

		// Table principale des diagnostics (ligne 0)
		scrollPanePrincipal = new JScrollPane();
		GridBagConstraints gbcScrollPanePrincipal = new GridBagConstraints();
		gbcScrollPanePrincipal.insets = new Insets(0, 0, 5, 0);
		gbcScrollPanePrincipal.fill = GridBagConstraints.BOTH;
		gbcScrollPanePrincipal.gridx = 0;
		gbcScrollPanePrincipal.gridy = 0;
		panelTable.add(scrollPanePrincipal, gbcScrollPanePrincipal);

		// Création de la table avec 5 colonnes (dates, bien, bâtiment, nom)
		tablePrincipale = new JTable();
		tablePrincipale.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Date d\u00E9but", "Date fin", "Bien louable", "Bâtiment", "Nom" }

		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		scrollPanePrincipal.setViewportView(tablePrincipale);

		// Label "Détails" (ligne 1)
		lblTableSecondaire = new JLabel("Détails");
		GridBagConstraints gbcLblTableSecondaire = new GridBagConstraints();
		gbcScrollPanePrincipal.insets = new Insets(5, 5, 5, 5);
		gbcLblTableSecondaire.anchor = GridBagConstraints.WEST;
		gbcLblTableSecondaire.gridx = 0;
		gbcLblTableSecondaire.gridy = 1;
		panelTable.add(lblTableSecondaire, gbcLblTableSecondaire);

		// Zone de texte pour afficher les détails du diagnostic sélectionné (ligne 2)
		scrollPaneSecondaire = new JScrollPane();
		GridBagConstraints gbcScrollPaneSecondaire = new GridBagConstraints();
		gbcScrollPaneSecondaire.insets = new Insets(0, 0, 5, 0);
		gbcScrollPaneSecondaire.fill = GridBagConstraints.BOTH;
		gbcScrollPaneSecondaire.gridx = 0;
		gbcScrollPaneSecondaire.gridy = 2;
		panelTable.add(scrollPaneSecondaire, gbcScrollPaneSecondaire);

		txtrDetails = new JTextArea();
		scrollPaneSecondaire.setViewportView(txtrDetails);

		// Panel du bas : bouton quitter
		panelQuitter = new JPanel();
		FlowLayout flPanelQuitter = (FlowLayout) panelQuitter.getLayout();
		flPanelQuitter.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelQuitter, BorderLayout.SOUTH);

		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this.gestionClic);
		panelQuitter.add(btnQuitter);

		// Initialisation du gestionnaire de table
		this.gestionTable = new GestionTableDiagnostics(this);
		// Listener pour détecter la sélection d'une ligne et afficher les détails
		this.tablePrincipale.getSelectionModel().addListSelectionListener(this.gestionTable);

		// Document listeners
		textFieldDateDebut.getDocument().addDocumentListener(gestionTable);
		comboBoxBienLouable.addActionListener(gestionTable);
		comboBoxBatiment.addActionListener(gestionTable);
		textFieldDateFin.getDocument().addDocumentListener(gestionTable);
		textFieldNom.getDocument().addDocumentListener(gestionTable);

	}

	/**
	 * Récupère le gestionnaire de la table des diagnostics.
	 * 
	 * @return le gestionnaire de table
	 */
	public GestionTableDiagnostics getGestionTable() {
		return gestionTable;
	}

	/**
	 * Récupère le modèle de données de la table principale.
	 * 
	 * @return le modèle de table
	 */
	public DefaultTableModel getGestionTableModel() {
		return (DefaultTableModel) tablePrincipale.getModel();
	}

	/**
	 * Modifie le gestionnaire de la table des diagnostics.
	 * 
	 * @param gestionTable le nouveau gestionnaire de table
	 */
	public void setGestionTable(GestionTableDiagnostics gestionTable) {
		this.gestionTable = gestionTable;
	}

	/**
	 * Récupère la table principale affichant les diagnostics.
	 * 
	 * @return la table des diagnostics
	 */
	public JTable getTable() {
		return tablePrincipale;
	}

	/**
	 * Modifie la table principale affichant les diagnostics.
	 * 
	 * @param table la nouvelle table
	 */
	public void setTable(JTable table) {
		this.tablePrincipale = table;
	}

	/**
	 * Récupère la zone de texte affichant les détails du diagnostic sélectionné.
	 * 
	 * @return la zone de texte des détails
	 */
	public JTextArea getTxtrDetails() {
		return txtrDetails;
	}

	/**
	 * Modifie la zone de texte affichant les détails du diagnostic sélectionné.
	 * 
	 * @param txtrDetails la nouvelle zone de texte
	 */
	public void setTxtrDetails(JTextArea txtrDetails) {
		this.txtrDetails = txtrDetails;
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
	 * Récupère le champ de texte pour saisir le nom du diagnostic pour le filtrage.
	 * 
	 * @return le champ de texte du nom
	 */
	public JTextField getTextFieldNom() {
		return textFieldNom;
	}

	/**
	 * Modifie le champ de texte pour saisir le nom du diagnostic pour le filtrage.
	 * 
	 * @param textFieldNom le nouveau champ de texte du nom
	 */
	public void setTextFieldNom(JTextField textFieldNom) {
		this.textFieldNom = textFieldNom;
	}

}
