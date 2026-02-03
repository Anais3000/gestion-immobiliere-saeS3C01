package vue.documents;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controleur.documents.GestionFenIRL;
import vue.Polices;

/**
 * Fenêtre de gestion et consultation des valeurs d'IRL (Indice de Référence des
 * Loyers). Affiche une table listant les valeurs d'IRL par année et trimestre.
 * Permet d'ajouter de nouvelles valeurs d'IRL, de supprimer des entrées
 * existantes, et charge automatiquement toutes les valeurs depuis la base de
 * données.
 */
public class FenValeursIRL extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel panelTable; // Panel principal contenant la table
	private JTable table; // Table affichant les valeurs d'IRL (année, trimestre, valeur)
	private JScrollPane scrollPane; // ScrollPane pour la table
	private JPanel panelBoutons; // Panel contenant les boutons ajouter et supprimer
	private JButton btnAjouter; // Bouton pour ajouter une nouvelle valeur d'IRL
	private JButton btnSupprimer; // Bouton pour supprimer la valeur sélectionnée
	private JPanel panelQuitter; // Panel contenant le bouton quitter
	private JButton btnQuitter; // Bouton pour fermer la fenêtre

	// Contrôleur et modèle
	private transient GestionFenIRL gestionClic; // Contrôleur des événements de la fenêtre
	private DefaultTableModel modeleTableIRL; // Modèle de la table des valeurs d'IRL

	/**
	 * Constructeur de la fenêtre des valeurs d'IRL. Crée l'interface avec un titre,
	 * les boutons d'ajout et suppression, une table listant toutes les valeurs
	 * d'IRL par année et trimestre, et charge automatiquement les données depuis la
	 * base de données.
	 */
	public FenValeursIRL() {

		// Initialisation du contrôleur de la fenêtre
		gestionClic = new GestionFenIRL(this);
		setBounds(100, 100, 800, 500);

		// Panel du haut : titre et boutons d'action
		JPanel panelHaut = new JPanel(); // Ce panel contient le titre et le bouton "ajouter un paiement"
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setBorder(new EmptyBorder(20, 10, 20, 10));
		panelHaut.setLayout(new BorderLayout(0, 0));

		// Titre de la fenêtre
		JLabel lblTitre = new JLabel("Valeurs d'IRL");
		panelHaut.add(lblTitre, BorderLayout.WEST);
		lblTitre.setFont(Polices.TITRE.getFont());

		// Panel contenant les boutons ajouter et supprimer
		panelBoutons = new JPanel();
		panelHaut.add(panelBoutons, BorderLayout.EAST);

		// Bouton pour ajouter une nouvelle valeur d'IRL
		btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(gestionClic);
		panelBoutons.add(btnAjouter);

		// Bouton pour supprimer la valeur d'IRL sélectionnée
		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(gestionClic);
		panelBoutons.add(btnSupprimer);

		// Panel central : table des valeurs d'IRL
		panelTable = new JPanel();
		getContentPane().add(panelTable, BorderLayout.CENTER);
		panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.X_AXIS));

		// ScrollPane contenant la table
		scrollPane = new JScrollPane();
		panelTable.add(scrollPane);

		// Création de la table avec 3 colonnes (année, trimestre, valeur)
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] { { null, "", null }, { null, null, null }, { null, null, null }, { null, null, null },
						{ null, null, null }, { null, null, null }, },
				new String[] { "Ann\u00E9e", "Trimestre", "Valeur" }) {
			Class[] columnTypes = new Class[] { Integer.class, Object.class, Object.class };

			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(2).setPreferredWidth(96);
		scrollPane.setViewportView(table);

		// Panel du bas : bouton quitter
		panelQuitter = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelQuitter.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelQuitter, BorderLayout.SOUTH);

		// Bouton Quitter
		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(gestionClic);
		panelQuitter.add(btnQuitter);

		// Chargement automatique des données dans la table
		gestionClic.ecrireLignesTable();

	}

	/**
	 * Récupère la table affichant les valeurs d'IRL.
	 * 
	 * @return la table des valeurs d'IRL
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * Récupère le modèle de la table des valeurs d'IRL.
	 * 
	 * @return le modèle de table
	 */
	public DefaultTableModel getModeleTableAssurance() {
		return modeleTableIRL;
	}

	/**
	 * Récupère le contrôleur de la fenêtre des valeurs d'IRL.
	 * 
	 * @return le contrôleur de la fenêtre
	 */
	public GestionFenIRL getGestionClic() {
		return gestionClic;
	}

}
