package vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import controleur.fen_principal.GestionFenetrePrincipale;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import controleur.fen_principal.GestionTableSituationMoisFenPrincipal;

/**
 * Fenêtre principale de l'application de gestion locative. Centralise la
 * navigation vers toutes les fonctionnalités (locataires, biens, paiements,
 * documents…) et présente un tableau de bord consolidé (indicateurs mensuels,
 * situation des loyers et historique des actions).
 */
public class FenetrePrincipale extends JFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	// ----------------------------------------------------------------
	private JPanel contentPane; // Panel principal

	private JMenuItem mntmConnecter; // Item pour se connecter
	private JMenuItem mntmDeconnecter; // Item pour se déconnecter
	private JMenu mnLogements; // Menu accès aux biens
	private JMenu mnLocataires; // Menu accès aux locataires
	private JMenu mnPaiements; // Menu accès aux paiements
	private JMenu mnDocuments; // Menu accès aux documents
	private JMenu mnOrganismes; // Menu accès aux organismes

	private JTable tableSituationMois; // Tableau de suivi des loyers du mois
	private JTable tableHistorique; // Tableau présentant l’historique récent

	private JLabel lblTitre; // Titre principal de la fenêtre
	private JLabel lblTotalLoyerMois; // Indicateur « total loyers du mois »
	private JLabel lblMontantEncaisse; // Indicateur « loyers encaissés »
	private JLabel lblLoyersImpayes; // Indicateur « loyers impayés »
	private JLabel lblBiensLouesTotal; // Indicateur « biens loués / total »

	private JLabel lblDerniereUtilisation; // Libellé de dernière utilisation
	private JLabel lblUtilisateur; // Libellé d’utilisateur connecté

	// Contrôleurs
	private transient GestionFenetrePrincipale gestionClic; // Gestion des menus
	private transient GestionTableHistoriqueFenPrinc gestionTableHistorique; // Gestion table historique
	private transient GestionTableSituationMoisFenPrincipal gestionTableSituation; // Gestion table situation

	// Variables de gestion
	private String utilisateur; // Nom de l’utilisateur courant
	private boolean connecte = false; // État de connexion

	/**
	 * Point d’entrée de l’application. Ouvre l’interface principale sur l’EDT
	 * Swing.
	 *
	 * @param args arguments de ligne de commande (non utilisés)
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				FenetrePrincipale frame = new FenetrePrincipale();
				frame.setVisible(true);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	/**
	 * Configure l’intégralité de la fenêtre principale (menus, tableaux,
	 * indicateurs et contrôleurs).
	 */
	public FenetrePrincipale() {

		this.gestionClic = new GestionFenetrePrincipale(this);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 1065, 800);

		// -------------------------- Barre de menus --------------------------
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		this.mnPaiements = new JMenu("Paiements");
		menuBar.add(mnPaiements);

		JMenuItem mntmVoirPaiements = new JMenuItem("Consulter les paiements");
		mntmVoirPaiements.addActionListener(this.gestionClic);
		mnPaiements.add(mntmVoirPaiements);

		this.mnLogements = new JMenu("Biens");
		menuBar.add(mnLogements);

		JMenuItem mntmConsulterLogements = new JMenuItem("Consulter les biens louables");
		mntmConsulterLogements.addActionListener(this.gestionClic);
		mnLogements.add(mntmConsulterLogements);

		JMenuItem mntmConsulterBatiments = new JMenuItem("Consulter les bâtiments");
		mntmConsulterBatiments.addActionListener(this.gestionClic);
		mnLogements.add(mntmConsulterBatiments);

		JMenuItem mntmGarants = new JMenuItem("Consulter les garants");
		mntmGarants.addActionListener(this.gestionClic);
		mnLogements.add(mntmGarants);

		this.mnLocataires = new JMenu("Locataire");
		menuBar.add(mnLocataires);

		JMenuItem mntmConsulterLocataires = new JMenuItem("Consulter les locataires");
		mntmConsulterLocataires.addActionListener(this.gestionClic);
		mnLocataires.add(mntmConsulterLocataires);

		this.mnDocuments = new JMenu("Documents");
		menuBar.add(mnDocuments);

		JMenuItem mntmContratLoc = new JMenuItem("Contrats de location");
		mntmContratLoc.addActionListener(this.gestionClic);
		mnDocuments.add(mntmContratLoc);

		JMenuItem mntmDiagnostics = new JMenuItem("Diagnostics");
		mntmDiagnostics.addActionListener(this.gestionClic);
		mnDocuments.add(mntmDiagnostics);

		JMenuItem mntmEtatsDesLieux = new JMenuItem("États des lieux");
		mntmEtatsDesLieux.addActionListener(this.gestionClic);
		mnDocuments.add(mntmEtatsDesLieux);

		JMenuItem mntmFactures = new JMenuItem("Factures et devis");
		mntmFactures.addActionListener(this.gestionClic);
		mnDocuments.add(mntmFactures);

		JMenuItem mntmContratAssurance = new JMenuItem("Contrats d'assurance");
		mntmContratAssurance.addActionListener(this.gestionClic);
		mnDocuments.add(mntmContratAssurance);

		JMenuItem mntmQuittance = new JMenuItem("Générer une quittance de loyer");
		mntmQuittance.addActionListener(this.gestionClic);

		JMenuItem mntmIRL = new JMenuItem("Valeurs de l'IRL enregistrées");
		mntmIRL.addActionListener(this.gestionClic);
		mnDocuments.add(mntmIRL);
		mnDocuments.add(mntmQuittance);

		JMenuItem mntmImportCSV = new JMenuItem("Importer des loyers à partir d'un fichier CSV");
		mntmImportCSV.addActionListener(this.gestionClic);
		mnDocuments.add(mntmImportCSV);

		this.mnOrganismes = new JMenu("Organismes");
		menuBar.add(this.mnOrganismes);

		JMenuItem mntmConsulterOrganismes = new JMenuItem("Consulter les organismes");
		mntmConsulterOrganismes.addActionListener(this.gestionClic);
		this.mnOrganismes.add(mntmConsulterOrganismes);

		JMenu mnConnexion = new JMenu("Connexion");
		menuBar.add(mnConnexion);

		this.mntmConnecter = new JMenuItem("Connecter");
		mntmConnecter.addActionListener(this.gestionClic);
		mnConnexion.add(mntmConnecter);

		this.mntmDeconnecter = new JMenuItem("Déconnecter");
		mntmDeconnecter.addActionListener(this.gestionClic);
		mnConnexion.add(mntmDeconnecter);

		// -------------------------- Contenu principal -----------------------
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// -------------------------- Bandeau supérieur -----------------------
		JPanel panelNorthTitre = new JPanel();
		contentPane.add(panelNorthTitre, BorderLayout.NORTH);
		panelNorthTitre.setLayout(new GridLayout(2, 0, 0, 0));

		this.lblTitre = new JLabel("Bienvenue dans votre espace de gestion locative.");
		this.lblTitre.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 25));
		this.lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		panelNorthTitre.add(lblTitre);

		JLabel lblSousTitre = new JLabel("Suivi global de vos locations, paiements et indicateurs du mois en cours.");
		lblSousTitre.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 16));
		lblSousTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblSousTitre.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0)); // espacement
		panelNorthTitre.add(lblSousTitre);

		// -------------------------- Tableau de bord -------------------------
		JPanel panelCenter = new JPanel();
		contentPane.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));

		JPanel panelTableauBord = new JPanel();
		panelTableauBord.setBorder(BorderFactory.createEmptyBorder(0, 10, 15, 10)); // espacement
		panelCenter.add(panelTableauBord, BorderLayout.NORTH);
		panelTableauBord.setLayout(new GridLayout(0, 4, 0, 0));

		this.lblTotalLoyerMois = new JLabel("Total loyers du mois :");
		lblTotalLoyerMois.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		lblTotalLoyerMois.setToolTipText("");
		panelTableauBord.add(lblTotalLoyerMois);

		this.lblMontantEncaisse = new JLabel("Loyers Encaissé :");
		lblMontantEncaisse.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelTableauBord.add(lblMontantEncaisse);

		this.lblLoyersImpayes = new JLabel("Loyers impayés :");
		lblLoyersImpayes.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelTableauBord.add(lblLoyersImpayes);

		this.lblBiensLouesTotal = new JLabel("Biens loués / Total :");
		lblBiensLouesTotal.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelTableauBord.add(lblBiensLouesTotal);

		// -------------------------- Tableaux principaux ---------------------
		JPanel panelTable = new JPanel();
		panelCenter.add(panelTable, BorderLayout.CENTER);
		panelTable.setLayout(new GridLayout(2, 1, 0, 10));

		JPanel panelSituation = new JPanel(new BorderLayout());
		panelTable.add(panelSituation);

		JLabel lblSituationTitre = new JLabel("Situation du mois en cours");
		lblSituationTitre.setHorizontalAlignment(SwingConstants.LEFT);
		lblSituationTitre.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 15));
		panelSituation.add(lblSituationTitre, BorderLayout.NORTH);

		tableSituationMois = new JTable();
		tableSituationMois
				.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Locataire", "Bien", "Loyer (\u20AC)",
						"Provision charges (\u20AC)", "Ajustement de loyer (\u20AC)", "Date de paiement", "Statut" }) {
					Class[] columnTypes = new Class[] { String.class, String.class, Float.class, Float.class,
							Object.class, String.class, String.class };

					@Override
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});

		int visibleRowsSituation = 5;
		int hauteurVisibleSituation = tableSituationMois.getRowHeight() * visibleRowsSituation;
		tableSituationMois.setPreferredScrollableViewportSize(
				new Dimension(tableSituationMois.getPreferredSize().width, hauteurVisibleSituation));

		JScrollPane scrollPaneSituation = new JScrollPane(tableSituationMois);
		scrollPaneSituation.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelSituation.add(scrollPaneSituation, BorderLayout.CENTER);

		this.gestionTableSituation = new GestionTableSituationMoisFenPrincipal(this,
				(DefaultTableModel) tableSituationMois.getModel());

		JPanel panelHistorique = new JPanel(new BorderLayout());
		panelTable.add(panelHistorique);

		JLabel lblHistoriqueTitre = new JLabel("Historique récent");
		lblHistoriqueTitre.setHorizontalAlignment(SwingConstants.LEFT);
		lblHistoriqueTitre.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 15));
		panelHistorique.add(lblHistoriqueTitre, BorderLayout.NORTH);

		tableHistorique = new JTable();
		DefaultTableModel modelHistorique = new DefaultTableModel(new Object[][] {},
				new String[] { "Date", "Action", "Détails" }) {
			private static final int MAX_ROWS = 50;

			Class[] columnTypes = new Class[] { String.class, String.class, String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // tableau non éditable
			}

			@Override
			public void addRow(Object[] rowData) {
				super.addRow(rowData);
				// Supprime les lignes les plus anciennes si > 50
				while (getRowCount() > MAX_ROWS) {
					removeRow(MAX_ROWS - 1);
				}
			}
		};
		tableHistorique.setModel(modelHistorique);

		int visibleRowsHistorique = 10;
		int hauteurVisibleHistorique = tableHistorique.getRowHeight() * visibleRowsHistorique;
		tableHistorique.setPreferredScrollableViewportSize(
				new Dimension(tableHistorique.getPreferredSize().width, hauteurVisibleHistorique));

		JScrollPane scrollPaneHistorique = new JScrollPane(tableHistorique);
		scrollPaneHistorique.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelHistorique.add(scrollPaneHistorique, BorderLayout.CENTER);

		this.gestionTableHistorique = new GestionTableHistoriqueFenPrinc(this,
				(DefaultTableModel) tableHistorique.getModel());

		// -------------------------- Barre d'état -----------------------------
		JPanel panelSouth = new JPanel();
		contentPane.add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		this.lblDerniereUtilisation = new JLabel("Dernière utilisation :");
		this.lblDerniereUtilisation.setHorizontalAlignment(SwingConstants.CENTER);
		panelSouth.add(lblDerniereUtilisation);

		this.lblUtilisateur = new JLabel("Utilisateur connecté :");
		lblUtilisateur.setHorizontalAlignment(SwingConstants.CENTER);
		panelSouth.add(lblUtilisateur);

		contentPane.setVisible(false); // par défaut invisible jusqu'à connexion

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String dateHeureActuelle = LocalDateTime.now().format(formatter);

		setDerniereUtilisation(dateHeureActuelle);
		this.utilisateur = this.gestionClic.getUtilisateur();
		setUtilisateurConnecte(this.utilisateur);

		this.activerItems(this.connecte);

	}

	/**
	 * @return l’item de menu permettant la connexion
	 */
	public JMenuItem getMntmConnecter() {
		return mntmConnecter;
	}

	/**
	 * Modifie l’état de connexion de la fenêtre.
	 *
	 * @param estConnecte true si un utilisateur est connecté
	 */
	public void setConnecte(boolean estConnecte) {
		this.connecte = estConnecte;
	}

	/**
	 * @return true si un utilisateur est connecté
	 */
	public boolean isConnecte() {
		return connecte;
	}

	/**
	 * Active ou désactive l’ensemble des menus selon l’état de connexion.
	 *
	 * @param value true pour activer les menus applicatifs
	 */
	public void activerItems(boolean value) {
		this.mnLogements.setEnabled(value);
		this.mnLocataires.setEnabled(value);
		this.mnPaiements.setEnabled(value);
		this.mnDocuments.setEnabled(value);
		this.mnOrganismes.setEnabled(value);
		this.mntmDeconnecter.setEnabled(value);
		this.mntmConnecter.setEnabled(!value);
	}

	/**
	 * Met à jour le titre de bienvenue avec le nom de l’utilisateur.
	 *
	 * @param utilisateur nom à afficher
	 */
	public void setTitre(String utilisateur) {
		this.lblTitre.setText("Bienvenue dans votre espace de gestion locative, " + utilisateur + " !");
	}

	/**
	 * Affiche le montant total des loyers du mois.
	 *
	 * @param montant total à afficher
	 */
	public void setTotalLoyersMois(double montant) {
		this.lblTotalLoyerMois.setText("Total loyers du mois : " + montant + "€");
	}

	/**
	 * Affiche la somme encaissée pour le mois.
	 *
	 * @param montant montant encaissé
	 */
	public void setMontantEncaisse(double montant) {
		this.lblMontantEncaisse.setText("Loyers encaissé : " + montant + "€");
	}

	/**
	 * Affiche le montant des loyers impayés.
	 *
	 * @param montant total des impayés
	 */
	public void setLoyersImpayes(double montant) {
		this.lblLoyersImpayes.setText("Loyers impayés : " + montant + "€");
	}

	/**
	 * Affiche le ratio biens loués / total de biens.
	 *
	 * @param loues nombre de biens loués
	 * @param total nombre total de biens
	 */
	public void setBiensLouesTotal(int loues, int total) {
		this.lblBiensLouesTotal.setText("Biens loués / Total : " + loues + " / " + total);
	}

	/**
	 * Met à jour l’horodatage de dernière utilisation.
	 *
	 * @param dateHeure libellé à afficher
	 */
	public void setDerniereUtilisation(String dateHeure) {
		this.lblDerniereUtilisation.setText("Dernière utilisation : " + dateHeure);
	}

	/**
	 * Affiche le nom de l’utilisateur actuellement connecté.
	 *
	 * @param nomUtilisateur nom à afficher
	 */
	public void setUtilisateurConnecte(String nomUtilisateur) {
		this.lblUtilisateur.setText("Utilisateur connecté : " + nomUtilisateur);
	}

	/**
	 * Affiche ou masque le contenu principal (utilisé à la connexion).
	 *
	 * @param visible true pour rendre le contenu visible
	 */
	public void afficherContenu(boolean visible) {
		contentPane.setVisible(visible);
	}

	/**
	 * @return la table listant la situation des loyers du mois
	 */
	public JTable getTableSituationMois() {
		return tableSituationMois;
	}

	/**
	 * @return la table présentant l’historique des actions
	 */
	public JTable getTableHistorique() {
		return tableHistorique;
	}

}
