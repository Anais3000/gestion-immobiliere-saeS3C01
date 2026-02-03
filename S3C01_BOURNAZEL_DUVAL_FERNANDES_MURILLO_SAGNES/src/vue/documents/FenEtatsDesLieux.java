package vue.documents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import controleur.documents.fen_etats_des_lieux.GestionFenEtatsDesLieux;
import controleur.documents.fen_etats_des_lieux.GestionTableFenEtatsDesLieux;
import modele.Louer;
import modele.dao.DaoLouer;
import vue.Polices;

/**
 * Fenêtre de gestion et consultation des états des lieux. Affiche une table
 * filtrable des états des lieux avec possibilité de filtrer par bâtiment, bien
 * louable, locataire et type (entrée/sortie). Permet de consulter les détails
 * d'un état des lieux sélectionné dans une zone de texte dédiée.
 */
public class FenEtatsDesLieux extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JComboBox<String> comboBoxBienLouable; // ComboBox pour filtrer par bien louable
	private JComboBox<String> comboBoxBatiment; // ComboBox pour filtrer par bâtiment
	private JComboBox<String> comboBoxEntreeSortie; // ComboBox pour filtrer par type (entrée/sortie)
	private JTextField textFieldLocataire; // Champ pour filtrer par nom du locataire
	private JPanel panelTable; // Panel principal contenant les tables
	private JTable tableEtatsDesLieux; // Table affichant la liste des états des lieux
	private JScrollPane scrollPane; // ScrollPane pour la table principale
	private JScrollPane scrollPaneSecondaire; // ScrollPane pour la zone de détails
	private JLabel lblTableSecondaire; // Label "Détails" au-dessus de la zone de texte
	private JTextArea txtrDetails; // Zone de texte affichant les détails de l'état des lieux sélectionné
	private JPanel panelQuitter; // Panel contenant le bouton quitter
	private JButton btnQuitter; // Bouton pour fermer la fenêtre

	// Contrôleurs
	private transient GestionFenEtatsDesLieux gestionClic; // Contrôleur des événements de la fenêtre
	private transient GestionTableFenEtatsDesLieux gestionTable; // Contrôleur de la table et des filtres

	/**
	 * Constructeur de la fenêtre des états des lieux. Crée l'interface avec les
	 * filtres (bâtiment, bien louable, locataire, entrée/sortie), une table
	 * principale listant les états des lieux, une zone de détails affichant les
	 * informations complètes de l'état des lieux sélectionné, et charge
	 * automatiquement toutes les données depuis la base de données avec les filtres
	 * activés.
	 */
	public FenEtatsDesLieux() {
		setBounds(100, 100, 800, 500);
		this.gestionClic = new GestionFenEtatsDesLieux(this);
		JPanel panelHaut = new JPanel(); // Ce panel contient le titre et le bouton "ajouter un paiement"
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setLayout(new BorderLayout(0, 0));
		panelHaut.setBorder(new EmptyBorder(20, 10, 20, 10));

		// Titre de la fenêtre
		JLabel lblTitre = new JLabel("États des lieux");
		panelHaut.add(lblTitre, BorderLayout.NORTH);
		lblTitre.setFont(Polices.TITRE.getFont());

		// Panel contenant les quatre filtres (bâtiment, bien louable, locataire,
		// entrée/sortie)
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

		// Filtre par nom du locataire
		textFieldLocataire = new JTextField();
		textFieldLocataire.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Locataire", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(textFieldLocataire);
		textFieldLocataire.setColumns(10);

		// Filtre par type d'état des lieux (entrée, sortie ou tous)
		comboBoxEntreeSortie = new JComboBox<>();
		comboBoxEntreeSortie.setModel(new DefaultComboBoxModel<>(new String[] { "Tous", "Entrée", "Sortie" }));
		comboBoxEntreeSortie.setBorder(
				BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Entrée/Sortie", TitledBorder.LEFT,
						TitledBorder.TOP, new Font(Outils.POLICE_SANSSERIF, Font.CENTER_BASELINE, 10), Color.black));
		panelfiltre.add(comboBoxEntreeSortie);

		// Panel du bas : bouton quitter
		panelQuitter = new JPanel();
		FlowLayout flPanelQuitter = (FlowLayout) panelQuitter.getLayout();
		flPanelQuitter.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelQuitter, BorderLayout.SOUTH);

		// Panel central : table principale et zone de détails
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

		// Table principale des états des lieux (ligne 0)
		scrollPane = new JScrollPane();
		GridBagConstraints gbcScrollPanePrincipal = new GridBagConstraints();
		gbcScrollPanePrincipal.insets = new Insets(0, 0, 5, 0);
		gbcScrollPanePrincipal.fill = GridBagConstraints.BOTH;
		gbcScrollPanePrincipal.gridx = 0;
		gbcScrollPanePrincipal.gridy = 0;
		panelTable.add(scrollPane, gbcScrollPanePrincipal);

		// Création de la table avec 5 colonnes (bâtiment, date, bien, type, locataire)
		tableEtatsDesLieux = new JTable();
		tableEtatsDesLieux.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "B\u00E2timent",
				"Date etat des lieux", "Bien louable", "Entr\u00E9e/Sortie", "Nom Locataire" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		scrollPane.setViewportView(tableEtatsDesLieux);

		// Label "Détails" (ligne 1)
		lblTableSecondaire = new JLabel("Détails");
		GridBagConstraints gbcLblTableSecondaire = new GridBagConstraints();
		gbcScrollPanePrincipal.insets = new Insets(5, 5, 5, 5);
		gbcLblTableSecondaire.anchor = GridBagConstraints.WEST;
		gbcLblTableSecondaire.gridx = 0;
		gbcLblTableSecondaire.gridy = 1;
		panelTable.add(lblTableSecondaire, gbcLblTableSecondaire);

		// Zone de texte pour afficher les détails de l'état des lieux sélectionné
		// (ligne 2)
		scrollPaneSecondaire = new JScrollPane();
		GridBagConstraints gbcScrollPaneSecondaire = new GridBagConstraints();
		gbcScrollPaneSecondaire.insets = new Insets(0, 0, 5, 0);
		gbcScrollPaneSecondaire.fill = GridBagConstraints.BOTH;
		gbcScrollPaneSecondaire.gridx = 0;
		gbcScrollPaneSecondaire.gridy = 2;
		panelTable.add(scrollPaneSecondaire, gbcScrollPaneSecondaire);

		txtrDetails = new JTextArea();
		scrollPaneSecondaire.setViewportView(txtrDetails);

		// Bouton Quitter
		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(this.gestionClic);
		panelQuitter.add(btnQuitter);

		// Initialisation du gestionnaire de table
		this.gestionTable = new GestionTableFenEtatsDesLieux(this);
		// Listener pour détecter la sélection d'une ligne et afficher les détails
		this.tableEtatsDesLieux.getSelectionModel().addListSelectionListener(this.gestionTable);

		// Chargement initial des données
		// Chargement de tous les états des lieux depuis la base de données
		DaoLouer l = new DaoLouer();
		Collection<Louer> edl;
		try {
			edl = l.findAll();
			// Application des filtres sur les données chargées
			gestionTable.appliquerFiltres(edl);
			// Activation automatique des filtres (remplissage des combo box)
			gestionTable.activerFiltresAutomatiques(edl);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Récupère la table affichant les états des lieux.
	 * 
	 * @return la table des états des lieux
	 */
	public JTable getTableEtatsDesLieux() {
		return this.tableEtatsDesLieux;
	}

	/**
	 * Récupère le gestionnaire de la table des états des lieux.
	 * 
	 * @return le gestionnaire de table
	 */
	public GestionTableFenEtatsDesLieux getGestionTable() {
		return this.gestionTable;
	}

	/**
	 * Récupère la zone de texte affichant les détails de l'état des lieux
	 * sélectionné.
	 * 
	 * @return la zone de texte des détails
	 */
	public JTextArea getTxtrDetails() {
		return txtrDetails;
	}

	/**
	 * Modifie la zone de texte affichant les détails de l'état des lieux
	 * sélectionné.
	 * 
	 * @param txtrDetails la nouvelle zone de texte
	 */
	public void setTxtrDetails(JTextArea txtrDetails) {
		this.txtrDetails = txtrDetails;
	}

	/**
	 * Modifie le gestionnaire de la table des états des lieux.
	 * 
	 * @param gestionTable le nouveau gestionnaire de table
	 */
	public void setGestionTable(GestionTableFenEtatsDesLieux gestionTable) {
		this.gestionTable = gestionTable;
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
	 * Récupère la combo box de sélection du type (entrée/sortie) pour le filtrage.
	 * 
	 * @return la combo box de filtrage par type
	 */
	public JComboBox<String> getComboBoxEntreeSortie() {
		return comboBoxEntreeSortie;
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
	 * Récupère le champ de texte pour saisir le nom du locataire pour le filtrage.
	 * 
	 * @return le champ de texte du locataire
	 */
	public JTextField getTextFieldLocataire() {
		return textFieldLocataire;
	}

}
