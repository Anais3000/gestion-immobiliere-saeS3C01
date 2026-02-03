package vue.consulter_informations;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import controleur.consulter_informations.fen_locataire.GestionFenLocataireInformations;
import controleur.consulter_informations.fen_locataire.GestionTableBiensFenLocInfos;
import controleur.consulter_informations.fen_locataire.GestionTablePaimentsFenLocInfos;
import modele.Locataire;
import vue.tables.FenLocataire;

/**
 * Fenêtre de consultation des informations d'un locataire. Affiche les données
 * personnelles du locataire, la liste de ses biens loués et l'historique de
 * tous ses paiements.
 */
public class FenLocataireInformations extends JFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel contentPanelGeneralBorder; // Panel principal dans le JScrollPane
	private JTable tablePaiements; // Table des paiements du locataire
	private JTable tableBiensLoues; // Table des biens loués
	private JLabel lblIdentifiantValeur; // Valeur de l'identifiant
	private JLabel lblNomValeur; // Valeur du nom
	private JLabel lblPrenomValeur; // Valeur du prénom
	private JLabel lblNumTelValeur; // Valeur du numéro de téléphone
	private JLabel lblMailValeur; // Valeur du mail
	private JLabel lblDateNaissanceValeur; // Valeur de la date de naissance
	private JLabel lblVilleNaissanceValeur; // Valeur de la ville de naissance
	private JLabel lblAdresseValeur; // Valeur de l'adresse
	private JLabel lblCodePostalValeur; // Valeur du code postal
	private JLabel lblVilleValeur; // Valeur de la ville

	// Contrôleurs
	private transient GestionFenLocataireInformations gestionClic; // Contrôleur de la fenêtre
	private transient GestionTableBiensFenLocInfos gestionTableBiens; // Contrôleur de la table des biens
	private transient GestionTablePaimentsFenLocInfos gestionTablePaiements; // Contrôleur de la table des paiements

	// Variables de gestion
	private transient Locataire selectLocataire; // Locataire sélectionné
	private FenLocataire fenLocAncetre; // Fenêtre ancêtre (table des locataires)

	/**
	 * Constructeur de la fenêtre de consultation d'un locataire. Crée une interface
	 * affichant toutes les informations personnelles du locataire, la liste de ses
	 * biens loués et l'historique complet de ses paiements. Permet la modification
	 * des informations et la suppression du locataire.
	 * 
	 * @param selectLocataire2 le locataire dont on souhaite consulter les
	 *                         informations
	 * @param fenLoc           la fenêtre ancêtre (table des locataires)
	 */
	@SuppressWarnings("static-access")
	public FenLocataireInformations(Locataire selectLocataire2, FenLocataire fenLoc) {
		this.fenLocAncetre = fenLoc;
		selectLocataire = selectLocataire2;
		gestionClic = new GestionFenLocataireInformations(this, selectLocataire);

		setBounds(100, 100, 900, 600);

		// Panel principal
		contentPanelGeneralBorder = new JPanel();
		contentPanelGeneralBorder.setBorder(new EmptyBorder(5, 5, 5, 5));

		// === Crée le JScrollPane qui contiendra ton panel ===
		JScrollPane scrollPaneGeneral = new JScrollPane(contentPanelGeneralBorder);
		contentPanelGeneralBorder.setLayout(new BorderLayout(0, 0));

		JPanel panelBoxLayoutGeneralY = new JPanel();
		contentPanelGeneralBorder.add(panelBoxLayoutGeneralY, BorderLayout.CENTER);
		panelBoxLayoutGeneralY.setLayout(new BoxLayout(panelBoxLayoutGeneralY, BoxLayout.Y_AXIS));

		JPanel panelInformations = new JPanel();
		panelBoxLayoutGeneralY.add(panelInformations);
		panelInformations.setLayout(new BorderLayout(5, 5));
		panelInformations.setBorder(new EmptyBorder(0, 0, 12, 0));

		JPanel panelGridLibellesInfos = new JPanel();
		panelInformations.add(panelGridLibellesInfos, BorderLayout.WEST);
		panelGridLibellesInfos.setLayout(new GridLayout(10, 1, 0, 8));

		JLabel lblIdentifiant = new JLabel("Identifiant : ");
		lblIdentifiant.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblIdentifiant);

		JLabel lblNom = new JLabel("Nom : ");
		lblNom.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblNom);

		JLabel lblPrenom = new JLabel("Prénom : ");
		lblPrenom.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblPrenom);

		JLabel lblNumTel = new JLabel("Numéro de téléphone : ");
		lblNumTel.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblNumTel);

		JLabel lblMail = new JLabel("Mail : ");
		lblMail.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblMail);

		JLabel lblDateNaissance = new JLabel("Date de naissance : ");
		lblDateNaissance.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblDateNaissance);

		JLabel lblVilleNaissance = new JLabel("Ville de naissance : ");
		lblVilleNaissance.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblVilleNaissance);

		JLabel lblAdresse = new JLabel("Adresse : ");
		lblAdresse.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblAdresse);

		JLabel lblCodePostal = new JLabel("Code postal : ");
		lblCodePostal.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblCodePostal);

		JLabel lblVille = new JLabel("Ville : ");
		lblVille.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblVille);

		JPanel panelGridInfos = new JPanel();
		panelInformations.add(panelGridInfos, BorderLayout.CENTER);
		panelGridInfos.setLayout(new GridLayout(10, 1, 0, 8));

		lblIdentifiantValeur = new JLabel();
		lblIdentifiantValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblIdentifiantValeur);

		lblNomValeur = new JLabel();
		lblNomValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblNomValeur);

		lblPrenomValeur = new JLabel();
		lblPrenomValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblPrenomValeur);

		lblNumTelValeur = new JLabel();
		lblNumTelValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblNumTelValeur);

		lblMailValeur = new JLabel();
		lblMailValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblMailValeur);

		lblDateNaissanceValeur = new JLabel();
		lblDateNaissanceValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblDateNaissanceValeur);

		lblVilleNaissanceValeur = new JLabel();
		lblVilleNaissanceValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblVilleNaissanceValeur);

		lblAdresseValeur = new JLabel();
		lblAdresseValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblAdresseValeur);

		lblCodePostalValeur = new JLabel();
		lblCodePostalValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblCodePostalValeur);

		lblVilleValeur = new JLabel();
		lblVilleValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblVilleValeur);

		JLabel lblTitreInformations = new JLabel("<html><u>Informations concernant le locataire :</u></html>");
		lblTitreInformations.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelInformations.add(lblTitreInformations, BorderLayout.NORTH);

		JPanel panelBoutonsInformations = new JPanel();
		FlowLayout flPanelBoutonsInformations = (FlowLayout) panelBoutonsInformations.getLayout();
		flPanelBoutonsInformations.setAlignment(FlowLayout.RIGHT);
		panelInformations.add(panelBoutonsInformations, BorderLayout.SOUTH);

		JButton btnModifierInformations = new JButton("Modifier informations");
		btnModifierInformations.addActionListener(gestionClic);
		panelBoutonsInformations.add(btnModifierInformations);

		JPanel panelBiensLouables = new JPanel();
		panelBoxLayoutGeneralY.add(panelBiensLouables);
		panelBiensLouables.setLayout(new BorderLayout(0, 5));
		panelBiensLouables.setBorder(new EmptyBorder(0, 0, 12, 0)); // 5px de marge en haut et en bas

		JLabel lblBiensLoues = new JLabel("<html><u>Biens loués par le locataire :</u></html>");
		lblBiensLoues.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelBiensLouables.add(lblBiensLoues, BorderLayout.NORTH);

		JPanel panelTableBiensLoues = new JPanel();
		panelBiensLouables.add(panelTableBiensLoues, BorderLayout.CENTER);
		panelTableBiensLoues.setLayout(new BorderLayout(0, 0));

		tableBiensLoues = new JTable();
		tableBiensLoues.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null }, { null, null, null, null }, },
				new String[] { "ID du bien lou\u00E9", "Montant loyer", "Montant provision pour charge",
						"Date de fin de bail" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		// Permet de limiter l'affichage de max 15 lignes pour le tableau
		int hauteurTableInterventions = tableBiensLoues.getRowHeight() * 5;
		tableBiensLoues.setPreferredScrollableViewportSize(new java.awt.Dimension(
				tableBiensLoues.getPreferredScrollableViewportSize().width, hauteurTableInterventions));
		// Ajout au scroll pane
		JScrollPane scrollPaneTableBiensLoues = new JScrollPane(tableBiensLoues);
		panelTableBiensLoues.add(scrollPaneTableBiensLoues, BorderLayout.CENTER);

		JPanel panelBoutonsIBiensLoues = new JPanel();
		FlowLayout flPanelBoutonsIBiensLoues = (FlowLayout) panelBoutonsIBiensLoues.getLayout();
		flPanelBoutonsIBiensLoues.setAlignment(FlowLayout.RIGHT);
		panelBiensLouables.add(panelBoutonsIBiensLoues, BorderLayout.SOUTH);

		JButton btnGenererQuittance = new JButton("Générer quittance");
		btnGenererQuittance.addActionListener(gestionClic);

		JButton btnConsulterBien = new JButton("Consulter bien");
		btnConsulterBien.addActionListener(gestionClic);

		JButton btnLoyer = new JButton("Paiement loyer");
		btnLoyer.addActionListener(gestionClic);
		panelBoutonsIBiensLoues.add(btnLoyer);
		panelBoutonsIBiensLoues.add(btnConsulterBien);
		panelBoutonsIBiensLoues.add(btnGenererQuittance);

		JPanel panelPaiements = new JPanel();
		panelBoxLayoutGeneralY.add(panelPaiements);
		panelPaiements.setLayout(new BorderLayout(5, 5));
		panelPaiements.setBorder(new EmptyBorder(0, 0, 12, 0)); // 5px de marge en haut et en bas

		JLabel lblTitrePaiements = new JLabel("<html><u>Paiements du locataire :</u></html>");
		lblTitrePaiements.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelPaiements.add(lblTitrePaiements, BorderLayout.NORTH);

		JPanel panelTablePaiements = new JPanel();
		panelPaiements.add(panelTablePaiements, BorderLayout.CENTER);
		panelTablePaiements.setLayout(new BorderLayout(0, 0));

		tablePaiements = new JTable();
		tablePaiements.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null }, { null, null, null, null, null, null },
						{ null, null, null, null, null, null } },
				new String[] { "ID du paiement", "Motif", "Date Paiement", "Mois concerné", "Montant",
						"ID du bien louable concern\u00E9" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		// Permet de limiter l'affichage de max 15 lignes pour le tableau
		int hauteurTableLogements = tablePaiements.getRowHeight() * 10;
		tablePaiements.setPreferredScrollableViewportSize(new java.awt.Dimension(
				tablePaiements.getPreferredScrollableViewportSize().width, hauteurTableLogements));

		// Ajout au scroll pane
		JScrollPane scrollPaneTablePaiements = new JScrollPane(tablePaiements);
		panelTablePaiements.add(scrollPaneTablePaiements, BorderLayout.CENTER);

		JPanel panelTitre = new JPanel();
		contentPanelGeneralBorder.add(panelTitre, BorderLayout.NORTH);
		panelTitre.setLayout(new GridLayout(1, 3, 0, 0));

		JLabel lblVide = new JLabel("");
		panelTitre.add(lblVide);

		JLabel lblTitre = new JLabel("<html><u>Informations du locataire :</u></html>");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 16));
		panelTitre.add(lblTitre);

		JPanel panelFlowQuitter = new JPanel();
		FlowLayout flowLayout2 = (FlowLayout) panelFlowQuitter.getLayout();
		flowLayout2.setAlignment(FlowLayout.RIGHT);
		panelTitre.add(panelFlowQuitter);

		JButton btnSupprimer = new JButton("Supprimer le locataire");
		panelBoutonsInformations.add(btnSupprimer);
		btnSupprimer.addActionListener(gestionClic);

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(gestionClic);
		panelFlowQuitter.add(btnQuitter);

		scrollPaneGeneral.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneGeneral.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneGeneral.getVerticalScrollBar().setUnitIncrement(15); // Vitesse du scroll, comme la fenêtre est grande
																		// j'ai augmenté un peu

		// Met le JScrollPane comme contentPane de la fenêtre
		setContentPane(scrollPaneGeneral);

		// Initialisation des gestions des tables
		gestionTableBiens = new GestionTableBiensFenLocInfos(this);
		this.tableBiensLoues.getSelectionModel().addListSelectionListener(gestionTableBiens); // Ajouter le
																								// listener à la
																								// table biens

		gestionTablePaiements = new GestionTablePaimentsFenLocInfos(this);
		this.tablePaiements.getSelectionModel().addListSelectionListener(this.tablePaiements); // Ajouter le listener à
																								// la table paiements

		// Modification des libellés avec Locataire
		this.actualisationLibelles(selectLocataire);
	}

	/**
	 * Actualise l'affichage des libellés avec les informations du locataire. Met à
	 * jour tous les champs de la fenêtre avec les données du locataire passé en
	 * paramètre.
	 * 
	 * @param locataireModifie le locataire contenant les nouvelles données à
	 *                         afficher
	 */
	public void actualisationLibelles(Locataire locataireModifie) {
		gestionClic.majLocataireSelect(locataireModifie); // IMPORTANT POUR MAJ EN ARR PLAN
		lblIdentifiantValeur.setText(locataireModifie.getIdLocataire());
		lblNomValeur.setText(locataireModifie.getNom());
		lblPrenomValeur.setText(locataireModifie.getPrenom());
		lblNumTelValeur.setText(locataireModifie.getNumTelephone());
		lblMailValeur.setText(locataireModifie.getMail());
		lblDateNaissanceValeur.setText(locataireModifie.getDateNaissance().toString());
		lblVilleNaissanceValeur.setText(locataireModifie.getVilleNaissance());
		lblAdresseValeur.setText(locataireModifie.getAdresse());
		lblCodePostalValeur.setText(locataireModifie.getCodePostal());
		lblVilleValeur.setText(locataireModifie.getVille());
	}

	/**
	 * Retourne un locataire créé à partir des valeurs actuellement affichées dans
	 * la fenêtre. Utilisé pour récupérer les modifications apportées par
	 * l'utilisateur.
	 * 
	 * @return un nouvel objet Locataire contenant les valeurs affichées
	 */
	public Locataire getLocataireActualise() {
		return new Locataire(lblIdentifiantValeur.getText(), lblNomValeur.getText(), lblPrenomValeur.getText(),
				lblNumTelValeur.getText(), lblMailValeur.getText(), LocalDate.parse(lblDateNaissanceValeur.getText()),
				lblVilleNaissanceValeur.getText(), lblAdresseValeur.getText(), lblCodePostalValeur.getText(),
				lblVilleValeur.getText());
	}

	/**
	 * Retourne la fenêtre ancêtre pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return la fenêtre FenLocataire
	 */
	public FenLocataire getFenLocAncetre() {
		return fenLocAncetre;
	}

	/**
	 * Retourne la table des biens loués pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return la JTable des biens loués
	 */
	public JTable getTableBiens() {
		return this.tableBiensLoues;
	}

	/**
	 * Retourne la table des paiements pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return la JTable des paiements
	 */
	public JTable getTablePaiements() {
		return tablePaiements;
	}

	/**
	 * Retourne le locataire sélectionné pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return le locataire actuellement consulté
	 */
	public Locataire getSelectLocataire() {
		return selectLocataire;
	}

	/**
	 * Retourne le contrôleur de la table des biens pour pouvoir l'utiliser dans les
	 * gestions.
	 * 
	 * @return le contrôleur GestionTableBiensFenLocInfos
	 */
	public GestionTableBiensFenLocInfos getGestionTableBiens() {
		return gestionTableBiens;
	}

	/**
	 * Retourne le contrôleur de la table des paiements pour pouvoir l'utiliser dans
	 * les gestions.
	 * 
	 * @return le contrôleur GestionTablePaimentsFenLocInfos
	 */
	public GestionTablePaimentsFenLocInfos getGestionTablePaiements() {
		return gestionTablePaiements;
	}

}
