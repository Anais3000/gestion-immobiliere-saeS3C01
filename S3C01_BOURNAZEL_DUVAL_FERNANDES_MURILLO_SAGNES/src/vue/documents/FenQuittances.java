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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import controleur.documents.GestionFenQuittances;
import controleur.documents.GestionTableQuittances;

/**
 * Fenêtre de génération et consultation des quittances de loyer. Affiche une
 * table filtrable des quittances avec possibilité de filtrer par période (dates
 * de début et fin), bien louable et locataire. Permet de générer des quittances
 * pour les entrées sélectionnées dans la table.
 */
public class FenQuittances extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JTextField textFieldDateDebut; // Champ pour filtrer par date de début
	private JTextField textFieldDateFin; // Champ pour filtrer par date de fin
	private JComboBox<String> comboBoxBienLouable; // ComboBox pour filtrer par bien louable
	private JComboBox<String> comboBoxLocataire; // ComboBox pour filtrer par locataire
	private JPanel panelHaut; // Panel supérieur contenant le titre et les filtres
	private JPanel panelFiltres; // Panel contenant les filtres
	private JPanel panelDates; // Panel contenant les deux champs de dates
	private JPanel panelTable; // Panel principal contenant la table et le bouton générer
	private JTable tableQuittance; // Table affichant la liste des quittances
	private JScrollPane scrollPane; // ScrollPane pour la table
	private JLabel lblGenererQuittance; // Label "Générer une Quittance" en haut de la fenêtre
	private JPanel panelGenerer; // Panel contenant le bouton de génération
	private JButton btnGenererQuittance; // Bouton pour générer la quittance sélectionnée
	private JPanel panelQuitter; // Panel contenant le bouton quitter
	private JButton btnQuitter; // Bouton pour fermer la fenêtre

	// Contrôleurs
	private transient GestionFenQuittances gestionClic; // Contrôleur des événements de la fenêtre
	private transient GestionTableQuittances gestionTable; // Contrôleur de la table et des filtres

	/**
	 * Constructeur de la fenêtre des quittances. Crée l'interface avec le titre,
	 * les filtres (dates, bien louable, locataire), une table listant les
	 * quittances à générer, un bouton pour générer la quittance sélectionnée, et
	 * initialise le contrôleur de la table.
	 */
	public FenQuittances() {
		// Initialisation du contrôleur de la fenêtre
		this.gestionClic = new GestionFenQuittances(this);
		setBounds(100, 100, 800, 500);

		// Panel du haut : titre et filtres
		panelHaut = new JPanel();
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setLayout(new BorderLayout(0, 0));

		// Panel contenant les filtres (dates, bien louable, locataire)
		panelFiltres = new JPanel();
		panelFiltres.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Filtres", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelHaut.add(panelFiltres, BorderLayout.CENTER);
		panelFiltres.setLayout(new GridLayout(0, 3, 0, 0));

		// Panel contenant les deux champs de dates (début et fin)
		panelDates = new JPanel();
		panelFiltres.add(panelDates);
		panelDates.setLayout(new GridLayout(0, 2, 0, 0));

		// Filtre par date de début
		textFieldDateDebut = new JTextField();
		textFieldDateDebut.setFont(new Font("Tahoma", Font.PLAIN, 10));
		panelDates.add(textFieldDateDebut);
		textFieldDateDebut.setColumns(10);
		textFieldDateDebut.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Date D\u00E9but",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));

		// Filtre par date de fin
		textFieldDateFin = new JTextField();
		panelDates.add(textFieldDateFin);
		textFieldDateFin.setColumns(10);
		textFieldDateFin.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Date Fin", TitledBorder.LEFT,
				TitledBorder.TOP, null, new Color(0, 0, 0)));

		// Filtre par bien louable
		comboBoxBienLouable = new JComboBox<>();
		comboBoxBienLouable.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Bien louable",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.CENTER_BASELINE, 10), Color.black));
		panelFiltres.add(comboBoxBienLouable);

		// Filtre par locataire
		comboBoxLocataire = new JComboBox<>();
		comboBoxLocataire.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black, 1), "Locataire",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("SansSerif", Font.CENTER_BASELINE, 10), Color.black));
		panelFiltres.add(comboBoxLocataire);

		// Titre de la fenêtre
		lblGenererQuittance = new JLabel("Générer une Quittance");
		lblGenererQuittance.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
		panelHaut.add(lblGenererQuittance, BorderLayout.NORTH);

		// Panel central : table des quittances et bouton générer
		panelTable = new JPanel();
		getContentPane().add(panelTable, BorderLayout.CENTER);

		// ScrollPane contenant la table des quittances
		scrollPane = new JScrollPane();

		// Création de la table avec 4 colonnes (mois, bien, locataire, ID locataire)
		tableQuittance = new JTable();
		tableQuittance.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Mois concerné", "Bien louable", "Locataire", "ID Locataire" }) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // tableau NON éditable
			}
		});

		scrollPane.setViewportView(tableQuittance);
		panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.Y_AXIS));
		panelTable.add(scrollPane);

		// Panel contenant le bouton de génération de quittance
		panelGenerer = new JPanel();
		panelTable.add(panelGenerer);

		// Bouton pour générer la quittance de la ligne sélectionnée
		btnGenererQuittance = new JButton("Générer quittance");
		btnGenererQuittance.addActionListener(this.gestionClic);
		panelGenerer.add(btnGenererQuittance);

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
		this.gestionTable = new GestionTableQuittances(this);

	}

	/**
	 * Récupère le modèle de la table des quittances.
	 * 
	 * @return le modèle de table
	 */
	public DefaultTableModel getGestionTableModel() {
		return (DefaultTableModel) tableQuittance.getModel();
	}

	/**
	 * Récupère le gestionnaire de la table des quittances.
	 * 
	 * @return le gestionnaire de table
	 */
	public GestionTableQuittances getGestionTable() {
		return this.gestionTable;
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
	 * Récupère le champ de texte pour saisir la date de début pour le filtrage.
	 * 
	 * @return le champ de texte de la date de début
	 */
	public JTextField getTextFieldDateDebut() {
		return textFieldDateDebut;
	}

	/**
	 * Récupère le champ de texte pour saisir la date de fin pour le filtrage.
	 * 
	 * @return le champ de texte de la date de fin
	 */
	public JTextField getTextFieldDateFin() {
		return textFieldDateFin;
	}

	/**
	 * Récupère la combo box de sélection du locataire pour le filtrage.
	 * 
	 * @return la combo box de filtrage par locataire
	 */
	public JComboBox<String> getComboBoxLocataire() {
		return comboBoxLocataire;
	}

	/**
	 * Récupère la table affichant les quittances à générer.
	 * 
	 * @return la table des quittances
	 */
	public JTable getTableQuittance() {
		return tableQuittance;
	}

}
