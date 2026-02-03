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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import controleur.documents.GestionFenFacturesEtDevis;
import controleur.documents.GestionTableFacturesEtDevis;
import vue.Polices;

/**
 * Fenêtre de gestion et consultation des factures et devis d'interventions.
 * Affiche une table filtrable des factures/devis avec possibilité de filtrer
 * par période, bâtiment, bien louable et type (facture/devis). Permet de
 * consulter les biens louables concernés par la facture ou le devis sélectionné
 * dans une table dédiée.
 */
public class FenFacturesEtDevis extends JInternalFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JTextField textFieldDateDebut; // Champ pour filtrer par date de début minimale
	private JTextField textFieldDateFin; // Champ pour filtrer par date de fin maximale
	private JComboBox<String> comboBoxBatiment; // ComboBox pour filtrer par bâtiment
	private JComboBox<String> comboBoxBienLouable; // ComboBox pour filtrer par bien louable
	private JComboBox<String> comboBoFactureDevis; // ComboBox pour filtrer par type (facture/devis)
	private JPanel panelTable; // Panel principal contenant les tables
	private JTable table; // Table principale affichant la liste des factures et devis
	private JTable tableBienLouables; // Table secondaire affichant les biens concernés par la sélection
	private JScrollPane scrollPanePrincipal; // ScrollPane pour la table principale
	private JScrollPane scrollPaneBiens; // ScrollPane pour la table des biens louables
	private JLabel lblTableauBiens; // Label "Biens concernés par la sélection" au-dessus de la table secondaire
	private JPanel panelQuitter; // Panel contenant le bouton quitter
	private JButton btnQuitter; // Bouton pour fermer la fenêtre

	// Contrôleurs
	private transient GestionFenFacturesEtDevis gestionClic; // Contrôleur des événements de la fenêtre
	private transient GestionTableFacturesEtDevis gestionTable; // Contrôleur de la table et des filtres

	/**
	 * Constructeur de la fenêtre des factures et devis. Crée l'interface avec les
	 * filtres (dates, bâtiment, bien louable, type facture/devis), une table
	 * principale listant les factures et devis, une table secondaire affichant les
	 * biens louables concernés par la sélection, et active les listeners pour gérer
	 * les filtres et les sélections.
	 */
	public FenFacturesEtDevis() {
		// Initialisation du contrôleur de la fenêtre
		this.gestionClic = new GestionFenFacturesEtDevis(this);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// Panel du haut : titre et filtres
		JPanel panelHaut = new JPanel(); // Ce panel contient le titre et le bouton "ajouter un paiement"
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setLayout(new BorderLayout(0, 0));
		panelHaut.setBorder(new EmptyBorder(20, 10, 20, 10));

		// Titre de la fenêtre
		JLabel lblTitre = new JLabel("Factures et devis");
		panelHaut.add(lblTitre, BorderLayout.NORTH);
		lblTitre.setFont(Polices.TITRE.getFont());

		// Panel contenant les cinq filtres (bâtiment, bien louable, type, dates)
		JPanel panelfiltre = new JPanel();
		panelHaut.add(panelfiltre, BorderLayout.CENTER);
		panelfiltre.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 2), "Filtres",
				TitledBorder.LEFT, TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.BOLD, 12), Color.black));
		panelfiltre.setLayout(new GridLayout(0, 4, 0, 0));

		// Filtre par bâtiment
		comboBoxBatiment = new JComboBox<>();
		comboBoxBatiment.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Bâtiment", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(comboBoxBatiment);

		// Filtre par bien louable
		comboBoxBienLouable = new JComboBox<>();
		panelfiltre.add(comboBoxBienLouable);
		comboBoxBienLouable.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Bien louable", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));

		// Filtre par type (facture ou devis)
		comboBoFactureDevis = new JComboBox<>();
		comboBoFactureDevis.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Facture/Devis", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(comboBoFactureDevis);

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

		// Panel central : table principale et table des biens
		// Utilisation d'un GridBagLayout pour organiser verticalement : table
		// principale, label, table des biens
		panelTable = new JPanel();
		getContentPane().add(panelTable, BorderLayout.CENTER);
		GridBagLayout gblPanelTable = new GridBagLayout();
		gblPanelTable.rowHeights = new int[] { 200, 20, 100 };
		gblPanelTable.columnWidths = new int[] { 784 };
		gblPanelTable.columnWeights = new double[] { 0.0 };
		gblPanelTable.rowWeights = new double[] { 1.0 };
		panelTable.setLayout(gblPanelTable);

		// Table principale des factures et devis (ligne 0)
		scrollPanePrincipal = new JScrollPane();
		GridBagConstraints gbcScrollPanePrincipal = new GridBagConstraints();
		gbcScrollPanePrincipal.insets = new Insets(0, 0, 5, 0);
		gbcScrollPanePrincipal.fill = GridBagConstraints.BOTH;
		gbcScrollPanePrincipal.gridx = 0;
		gbcScrollPanePrincipal.gridy = 0;
		panelTable.add(scrollPanePrincipal, gbcScrollPanePrincipal);

		// Création de la table principale avec 7 colonnes (ID, date, bâtiment,
		// intitulé, organisme, type, montant)
		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Date", "B\u00E2timent",
				"Intitul\u00E9", "Organisme", "Facture/Devis", "Montant" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // tableau NON éditable
			}
		});
		scrollPanePrincipal.setViewportView(table);

		// Label "Biens concernés par la sélection" (ligne 1)
		lblTableauBiens = new JLabel("Biens concernés par la sélection :");
		GridBagConstraints gbcLblTableauBiens = new GridBagConstraints();
		gbcLblTableauBiens.anchor = GridBagConstraints.WEST;
		gbcLblTableauBiens.insets = new Insets(0, 0, 5, 0);
		gbcLblTableauBiens.gridx = 0;
		gbcLblTableauBiens.gridy = 1;
		panelTable.add(lblTableauBiens, gbcLblTableauBiens);

		// Table secondaire affichant les biens louables concernés par la facture/devis
		// sélectionné (ligne 2)
		scrollPaneBiens = new JScrollPane();
		GridBagConstraints gbcScrollPaneBiens = new GridBagConstraints();
		gbcScrollPaneBiens.fill = GridBagConstraints.BOTH;
		gbcScrollPaneBiens.gridx = 0;
		gbcScrollPaneBiens.gridy = 2;
		panelTable.add(scrollPaneBiens, gbcScrollPaneBiens);

		// Création de la table des biens louables avec 5 colonnes
		tableBienLouables = new JTable();
		tableBienLouables.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Adresse", "Ville", "Code postal", "Type de bien" }));
		scrollPaneBiens.setViewportView(tableBienLouables);

		// Panel du bas : bouton quitter
		panelQuitter = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelQuitter.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelQuitter, BorderLayout.SOUTH);

		// Bouton Quitter
		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this.gestionClic);
		panelQuitter.add(btnQuitter);

		// Initialisation du gestionnaire de table
		gestionTable = new GestionTableFacturesEtDevis(this);

		// Listener pour détecter la sélection d'une ligne et afficher les biens
		// concernés
		this.table.getSelectionModel().addListSelectionListener(this.gestionTable);

		// Filtres
		comboBoFactureDevis.addActionListener(gestionTable);
		comboBoxBienLouable.addActionListener(gestionTable);
		comboBoxBatiment.addActionListener(gestionTable);
		textFieldDateFin.getDocument().addDocumentListener(gestionTable);
		textFieldDateDebut.getDocument().addDocumentListener(gestionTable);
	}

	/**
	 * Récupère la table principale affichant les factures et devis.
	 * 
	 * @return la table des factures et devis
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * Modifie la table principale affichant les factures et devis.
	 * 
	 * @param table la nouvelle table
	 */
	public void setTable(JTable table) {
		this.table = table;
	}

	/**
	 * Récupère le gestionnaire de la table des factures et devis.
	 * 
	 * @return le gestionnaire de table
	 */
	public GestionTableFacturesEtDevis getGestionTable() {
		return gestionTable;
	}

	/**
	 * Modifie le gestionnaire de la table des factures et devis.
	 * 
	 * @param gestionTable le nouveau gestionnaire de table
	 */
	public void setGestionTable(GestionTableFacturesEtDevis gestionTable) {
		this.gestionTable = gestionTable;
	}

	/**
	 * Récupère la table secondaire affichant les biens louables concernés par la
	 * sélection.
	 * 
	 * @return la table des biens louables
	 */
	public JTable getTableBienLouables() {
		return tableBienLouables;
	}

	/**
	 * Modifie la table secondaire affichant les biens louables concernés par la
	 * sélection.
	 * 
	 * @param tableBienLouables la nouvelle table des biens louables
	 */
	public void setTableBienLouables(JTable tableBienLouables) {
		this.tableBienLouables = tableBienLouables;
	}

	/**
	 * Récupère le champ de texte pour saisir la date de début minimale pour le
	 * filtrage.
	 * 
	 * @return le champ de texte de la date de début
	 */
	public JTextField getTextFieldDateDebut() {
		return textFieldDateDebut;
	}

	/**
	 * Modifie le champ de texte pour saisir la date de début minimale pour le
	 * filtrage.
	 * 
	 * @param textFieldDateDebut le nouveau champ de texte
	 */
	public void setTextFieldDateDebut(JTextField textFieldDateDebut) {
		this.textFieldDateDebut = textFieldDateDebut;
	}

	/**
	 * Récupère le champ de texte pour saisir la date de fin maximale pour le
	 * filtrage.
	 * 
	 * @return le champ de texte de la date de fin
	 */
	public JTextField getTextFieldDateFin() {
		return textFieldDateFin;
	}

	/**
	 * Modifie le champ de texte pour saisir la date de fin maximale pour le
	 * filtrage.
	 * 
	 * @param textFieldDateFin le nouveau champ de texte
	 */
	public void setTextFieldDateFin(JTextField textFieldDateFin) {
		this.textFieldDateFin = textFieldDateFin;
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
	 * @param comboBoxBienLouable la nouvelle combo box
	 */
	public void setComboBoxBienLouable(JComboBox<String> comboBoxBienLouable) {
		this.comboBoxBienLouable = comboBoxBienLouable;
	}

	/**
	 * Récupère la combo box de sélection du type (facture/devis) pour le filtrage.
	 * 
	 * @return la combo box de filtrage par type
	 */
	public JComboBox<String> getComboBoFactureDevis() {
		return comboBoFactureDevis;
	}

	/**
	 * Modifie la combo box de sélection du type (facture/devis) pour le filtrage.
	 * 
	 * @param comboBoFactureDevis la nouvelle combo box
	 */
	public void setComboBoFactureDevis(JComboBox<String> comboBoFactureDevis) {
		this.comboBoFactureDevis = comboBoFactureDevis;
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
	 * @param comboBoxBatiment la nouvelle combo box
	 */
	public void setComboBoxBatiment(JComboBox<String> comboBoxBatiment) {
		this.comboBoxBatiment = comboBoxBatiment;
	}

}
