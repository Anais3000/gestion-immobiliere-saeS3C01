package vue.consulter_informations;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import controleur.consulter_informations.fen_bien_louable.GestionFenBienLouableInformations;
import controleur.consulter_informations.fen_bien_louable.GestionTableAnciensLocBienInfos;
import controleur.consulter_informations.fen_bien_louable.GestionTableCompteursBienInfos;
import controleur.consulter_informations.fen_bien_louable.GestionTableDiagnosticsBienInfos;
import controleur.consulter_informations.fen_bien_louable.GestionTableEtatsLieuxFenBLInfos;
import controleur.consulter_informations.fen_bien_louable.GestionTableIntervBienInfos;
import controleur.consulter_informations.fen_bien_louable.GestionTableRegularisationBienInfos;
import controleur.consulter_informations.fen_bien_louable.GestionTableRelevBienInfos;
import controleur.consulter_informations.fen_bien_louable.GestionTableSoldeToutCompteBienInfos;
import controleur.consulter_informations.fen_bien_louable.GestionTableTotalTravaux;
import modele.BienLouable;
import vue.tables.FenBienLouable;

/**
 * Fenêtre de consultation des informations d'un bien louable. Affiche les
 * données du bien (identifiant, type, adresse, caractéristiques), les
 * informations du bail en cours, la liste des diagnostics, interventions,
 * anciens locataires, compteurs, relevés, états des lieux, soldes de tout
 * compte, régularisations de charges et les chiffres clés annuels.
 */
public class FenBienLouableInformations extends JFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel contentPanelGeneralBorder; // Panel principal dans le JScrollPane
	private JTable tableDiagnostics; // Table des diagnostics du bien
	private JTable tableInterventions; // Table des interventions sur le bien
	private JTable tableAnciensLocataires; // Table des anciens locataires
	private JTable tableReleves; // Table des relevés de compteur
	private JTable tableAnciensEtatsLieux; // Table des anciens états des lieux
	private JTable tableCompteurs; // Table des compteurs du bien
	private JTable tableHistoriqueSoldeToutCompte; // Table de l'historique des soldes de tout compte
	private JTable tableRegularisationCharges; // Table des régularisations de charges
	private JTable tableTravaux; // Table des chiffres clés (total travaux)

	// Labels informations bien
	private JLabel lblIdentifiantValeur; // Valeur de l'identifiant
	private JLabel lblTypeBienValeur; // Valeur du type de bien
	private JLabel lblAdresseValeur; // Valeur de l'adresse
	private JLabel lblCodePostalValeur; // Valeur du code postal
	private JLabel lblVilleValeur; // Valeur de la ville
	private JLabel lblDateConstructionValeur; // Valeur de la date de construction
	private JLabel lblSurfaceHabitableValeur; // Valeur de la surface habitable
	private JLabel lblNbPiecesValeur; // Valeur du nombre de pièces
	private JLabel lblPourcentagePartiesCommunesValeur; // Valeur du pourcentage parties communes
	private JLabel lblDPourcentageOrduresValeur; // Valeur du pourcentage ordures
	private JLabel lblIdBatRattacheValeur; // Valeur de l'ID bâtiment rattaché
	private JLabel lblNumeroFiscalValeur; // Valeur du numéro fiscal
	private JLabel lblGaragesAssociesValeur; // Valeur des garages associés

	// Composants panel bail
	private JLabel lblTitreBail; // Titre de la section bail
	private JPanel panelContenuBail; // Panel contenant les infos du bail
	private JPanel panelGridLibellesBail; // Panel des libellés du bail
	private JButton btnCreerBail; // Bouton créer bail
	private JButton btnResilierBail; // Bouton résilier bail
	private JButton btnAjouterEtatLieuxEntree; // Bouton ajouter état des lieux d'entrée
	private JButton btnConsulterEtatLieuxEntree; // Bouton consulter état des lieux d'entrée
	private JButton btnPaiementLoyer; // Bouton paiement loyer

	// Labels informations bail
	private JLabel lblIdLocataireValeur; // Valeur de l'ID locataire
	private JLabel lblNomLocataireValeur; // Valeur du nom locataire
	private JLabel lblPrenomLocataireValeur; // Valeur du prénom locataire
	private JLabel lblDateDebutBailValeur; // Valeur de la date de début bail
	private JLabel lblDateFinBailValeur; // Valeur de la date de fin bail
	private JLabel lblIdGarantValeur; // Valeur de l'ID garant
	private JLabel lblProvisionValeur; // Valeur de la provision
	private JLabel lblLoyerValeur; // Valeur du loyer
	private JLabel lblDepotGarantieValeur; // Valeur du dépôt de garantie

	// Composants chiffres clés
	private JComboBox<Integer> comboBoxAnnee; // ComboBox pour sélectionner l'année
	private JLabel lblValeurLoyers; // Valeur des loyers perçus
	private JLabel lblValeurProvisions; // Valeur des provisions perçues
	private JLabel lblValeurTravaux; // Valeur des travaux

	// Contrôleurs
	private transient GestionFenBienLouableInformations gestionClic; // Contrôleur de la fenêtre
	private transient GestionTableAnciensLocBienInfos gestionTableAnciensLocataires; // Contrôleur anciens locataires
	private transient GestionTableEtatsLieuxFenBLInfos gestionTableAnciensEtatsLieux; // Contrôleur états des lieux
	private transient GestionTableRelevBienInfos gestionTableRelev; // Contrôleur relevés
	private transient GestionTableIntervBienInfos gestionTableInterv; // Contrôleur interventions
	private transient GestionTableDiagnosticsBienInfos gestionTableDiag; // Contrôleur diagnostics
	private transient GestionTableCompteursBienInfos gestionTableCompt; // Contrôleur compteurs
	private transient GestionTableSoldeToutCompteBienInfos gestionTableSTC; // Contrôleur soldes de tout compte
	private transient GestionTableRegularisationBienInfos gestionTableRegularisationCharges; // Contrôleur
																								// régularisations
	private transient GestionTableTotalTravaux gestionTableChiffresCles; // Contrôleur chiffres clés

	// Variables de gestion
	private transient BienLouable selectBien; // Bien louable sélectionné
	private FenBienLouable fenBienAncetre; // Fenêtre ancêtre (table des biens louables)

	/**
	 * Constructeur de la fenêtre de consultation d'un bien louable. Crée une
	 * interface complète affichant toutes les informations du bien, du bail en
	 * cours (si existant), des diagnostics, interventions, anciens locataires,
	 * compteurs, relevés, états des lieux, soldes de tout compte, régularisations
	 * et chiffres clés. Permet la création/résiliation de bail et la gestion des
	 * documents associés.
	 * 
	 * @param selectBien le bien louable dont on souhaite consulter les informations
	 * @param fenAncetre la fenêtre ancêtre (table des biens louables)
	 */
	public FenBienLouableInformations(BienLouable selectBien, FenBienLouable fenAncetre) {
		this.selectBien = selectBien;
		this.fenBienAncetre = fenAncetre;

		gestionClic = new GestionFenBienLouableInformations(this, selectBien);

		setBounds(100, 100, 900, 600);

		// === Crée le panel principal ===
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
		panelGridLibellesInfos.setLayout(new GridLayout(14, 1, 0, 8));

		JLabel lblIdBien = new JLabel("Identifiant : ");
		lblIdBien.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblIdBien);

		JLabel lblTypeBien = new JLabel("Type de bien :");
		lblTypeBien.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblTypeBien);

		JLabel lblAdresse = new JLabel("Adresse : ");
		lblAdresse.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblAdresse);

		JLabel lblCodePostal = new JLabel("Code postal : ");
		lblCodePostal.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblCodePostal);

		JLabel lblVille = new JLabel("Ville : ");
		lblVille.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblVille);

		JLabel lblDateConstruction = new JLabel("Date de construction : ");
		lblDateConstruction.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblDateConstruction);

		JLabel lblSurfaceHabitable = new JLabel("Surface habitable : ");
		lblSurfaceHabitable.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblSurfaceHabitable);

		JLabel lblNbPieces = new JLabel("Nombre de pièces :");
		lblNbPieces.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblNbPieces);

		JLabel lblLoyer = new JLabel("Loyer : ");
		lblLoyer.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridLibellesInfos.add(lblLoyer);

		JLabel lblProvision = new JLabel("Provision pour charges : ");
		lblProvision.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridLibellesInfos.add(lblProvision);

		JLabel lblPourcentagePartiesCommunes = new JLabel("Part entretien des parties communes :");
		lblPourcentagePartiesCommunes.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblPourcentagePartiesCommunes);

		JLabel lblPourcentageOrdures = new JLabel("Part ordures ménagères :");
		lblPourcentageOrdures.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblPourcentageOrdures);

		JLabel lblIdBatRattache = new JLabel("ID du bâtiment rattaché :");
		lblIdBatRattache.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridLibellesInfos.add(lblIdBatRattache);

		JLabel lblNumeroFiscal = new JLabel("Numéro fiscal :");
		lblNumeroFiscal.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridLibellesInfos.add(lblNumeroFiscal);

		JPanel panelGridInfos = new JPanel();
		panelInformations.add(panelGridInfos, BorderLayout.CENTER);
		panelGridInfos.setLayout(new GridLayout(14, 1, 0, 8));

		lblIdentifiantValeur = new JLabel(selectBien.getIdBien());
		lblIdentifiantValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblIdentifiantValeur);

		lblTypeBienValeur = new JLabel(selectBien.getTypeBien());
		lblTypeBienValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblTypeBienValeur);

		lblAdresseValeur = new JLabel(selectBien.getAdresse());
		lblAdresseValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblAdresseValeur);

		lblCodePostalValeur = new JLabel(selectBien.getCodePostal());
		lblCodePostalValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblCodePostalValeur);

		lblVilleValeur = new JLabel(selectBien.getVille());
		lblVilleValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblVilleValeur);

		lblDateConstructionValeur = new JLabel(selectBien.getDateConstruction().toString());
		lblDateConstructionValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblDateConstructionValeur);

		lblSurfaceHabitableValeur = new JLabel(String.valueOf(selectBien.getSurfaceHabitable()) + "m2");
		lblSurfaceHabitableValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblSurfaceHabitableValeur);

		lblNbPiecesValeur = new JLabel(String.valueOf(selectBien.getNbPieces()));
		lblNbPiecesValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblNbPiecesValeur);

		lblLoyerValeur = new JLabel(String.valueOf(selectBien.getLoyer()) + "€");
		lblLoyerValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridInfos.add(lblLoyerValeur);

		lblProvisionValeur = new JLabel(String.valueOf(selectBien.getProvisionPourCharges()) + "€");
		lblProvisionValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridInfos.add(lblProvisionValeur);

		lblPourcentagePartiesCommunesValeur = new JLabel(
				String.valueOf(selectBien.getPourcentageEntretienPartiesCommunes()) + "%");
		lblPourcentagePartiesCommunesValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblPourcentagePartiesCommunesValeur);

		lblDPourcentageOrduresValeur = new JLabel(String.valueOf(selectBien.getPourcentageOrduresMenageres()) + "%");
		lblDPourcentageOrduresValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblDPourcentageOrduresValeur);

		lblIdBatRattacheValeur = new JLabel(selectBien.getIdBat());
		lblIdBatRattacheValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridInfos.add(lblIdBatRattacheValeur);

		lblNumeroFiscalValeur = new JLabel(selectBien.getNumeroFiscal());
		lblNumeroFiscalValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridInfos.add(lblNumeroFiscalValeur);

		JLabel lblTitreInformations = new JLabel("<html><u>Informations concernant le bien louable :</u></html>");
		lblTitreInformations.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelInformations.add(lblTitreInformations, BorderLayout.NORTH);

		JPanel panelBoutonsInformations = new JPanel();
		FlowLayout flPanelBoutonsInformations = (FlowLayout) panelBoutonsInformations.getLayout();
		flPanelBoutonsInformations.setAlignment(FlowLayout.RIGHT);
		panelInformations.add(panelBoutonsInformations, BorderLayout.SOUTH);

		JButton btnModifierInformations = new JButton("Modifier informations");
		btnModifierInformations.addActionListener(gestionClic);

		JButton btnConsulterBatiment = new JButton("Consulter bâtiment");
		btnConsulterBatiment.addActionListener(gestionClic);
		panelBoutonsInformations.add(btnConsulterBatiment);
		panelBoutonsInformations.add(btnModifierInformations);

		JButton btnSupprimerBienLouable = new JButton("Supprimer le Bien Louable");
		btnSupprimerBienLouable.addActionListener(gestionClic);
		panelBoutonsInformations.add(btnSupprimerBienLouable);

		// PANEL CHIFFRES CLES
		JPanel panelChiffresCles = new JPanel();
		panelBoxLayoutGeneralY.add(panelChiffresCles);
		panelChiffresCles.setLayout(new BorderLayout(5, 5));
		panelChiffresCles.setBorder(new EmptyBorder(0, 0, 12, 0)); // espace en bas

		JLabel lblTitreChiffresCles = new JLabel("<html><u>Chiffres clés</u></html>");
		lblTitreChiffresCles.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelChiffresCles.add(lblTitreChiffresCles, BorderLayout.NORTH);

		JPanel panelChiffresCorps = new JPanel();
		panelChiffresCles.add(panelChiffresCorps, BorderLayout.SOUTH);
		GridBagLayout gblPanelChiffresCorps = new GridBagLayout();
		gblPanelChiffresCorps.columnWidths = new int[] { 0, 0 };
		gblPanelChiffresCorps.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gblPanelChiffresCorps.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gblPanelChiffresCorps.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE, 0.0, 1.0 };
		panelChiffresCorps.setLayout(gblPanelChiffresCorps);

		JLabel lblAnnee = new JLabel("Année");
		GridBagConstraints gbcLblAnnee = new GridBagConstraints();
		gbcLblAnnee.fill = GridBagConstraints.HORIZONTAL;
		gbcLblAnnee.insets = new Insets(0, 0, 5, 5);
		gbcLblAnnee.gridx = 0;
		gbcLblAnnee.gridy = 0;
		panelChiffresCorps.add(lblAnnee, gbcLblAnnee);

		comboBoxAnnee = new JComboBox<>();
		GridBagConstraints gbcComboBoxAnnee = new GridBagConstraints();
		gbcComboBoxAnnee.insets = new Insets(0, 20, 5, 0);
		gbcComboBoxAnnee.anchor = GridBagConstraints.WEST;
		gbcComboBoxAnnee.gridx = 1;
		gbcComboBoxAnnee.gridy = 0;
		panelChiffresCorps.add(comboBoxAnnee, gbcComboBoxAnnee);

		JLabel lblMontantLoyers = new JLabel("Montant des loyers perçus");
		GridBagConstraints gbcLblMontantLoyers = new GridBagConstraints();
		gbcLblMontantLoyers.anchor = GridBagConstraints.WEST;
		gbcLblMontantLoyers.insets = new Insets(0, 0, 5, 5);
		gbcLblMontantLoyers.gridx = 0;
		gbcLblMontantLoyers.gridy = 1;
		panelChiffresCorps.add(lblMontantLoyers, gbcLblMontantLoyers);

		lblValeurLoyers = new JLabel("lorem ipsum");
		lblValeurLoyers.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbcLblValeurLoyers = new GridBagConstraints();
		gbcLblValeurLoyers.anchor = GridBagConstraints.WEST;
		gbcLblValeurLoyers.insets = new Insets(0, 20, 5, 0);
		gbcLblValeurLoyers.gridx = 1;
		gbcLblValeurLoyers.gridy = 1;
		panelChiffresCorps.add(lblValeurLoyers, gbcLblValeurLoyers);

		JLabel lblProvisions = new JLabel("Montant des provisions perçues");
		GridBagConstraints gbcLblProvisions = new GridBagConstraints();
		gbcLblProvisions.anchor = GridBagConstraints.WEST;
		gbcLblProvisions.insets = new Insets(0, 0, 5, 5);
		gbcLblProvisions.gridx = 0;
		gbcLblProvisions.gridy = 2;
		panelChiffresCorps.add(lblProvisions, gbcLblProvisions);

		lblValeurProvisions = new JLabel("lorem ipsum");
		lblValeurLoyers.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbcLblValeurProvisions = new GridBagConstraints();
		gbcLblValeurProvisions.anchor = GridBagConstraints.WEST;
		gbcLblValeurProvisions.insets = new Insets(0, 20, 5, 0);
		gbcLblValeurProvisions.gridx = 1;
		gbcLblValeurProvisions.gridy = 2;
		panelChiffresCorps.add(lblValeurProvisions, gbcLblValeurProvisions);

		JLabel lblTravaux = new JLabel("Montant des travaux");
		GridBagConstraints gbcLblTravaux = new GridBagConstraints();
		gbcLblTravaux.anchor = GridBagConstraints.WEST;
		gbcLblTravaux.insets = new Insets(0, 0, 5, 5);
		gbcLblTravaux.gridx = 0;
		gbcLblTravaux.gridy = 3;
		panelChiffresCorps.add(lblTravaux, gbcLblTravaux);

		tableTravaux = new JTable();
		tableTravaux.setModel(new DefaultTableModel(new Object[][] { { null, null }, },
				new String[] { "Organisme", "Montant des travaux" }));

		JScrollPane scrollPaneTableTravaux;
		scrollPaneTableTravaux = new JScrollPane(tableTravaux);
		GridBagConstraints gbcScrollPaneTableProvisions = new GridBagConstraints();
		gbcScrollPaneTableProvisions.anchor = GridBagConstraints.WEST;
		gbcScrollPaneTableProvisions.insets = new Insets(0, 20, 5, 0);
		gbcScrollPaneTableProvisions.gridx = 1;
		gbcScrollPaneTableProvisions.gridy = 3;
		panelChiffresCorps.add(scrollPaneTableTravaux, gbcScrollPaneTableProvisions);

		/////////////////////////
		// === PANEL BAIL === //
		///////////////////////
		///
		/// Note : les JLabel sont remplis à l'initialisation de la fenêtre, depuis
		///////////////////////// GestionFenBienLouable
		/// lorsqu'un bien louable est sélectionné

		JPanel panelBail = new JPanel();
		panelBoxLayoutGeneralY.add(panelBail);
		panelBail.setLayout(new BorderLayout(5, 5));
		panelBail.setBorder(new EmptyBorder(0, 0, 12, 0)); // espace en bas

		// --- Titre du panel ---
		lblTitreBail = new JLabel("<html><u>Un bail est en cours sur ce bien :</u></html>");
		lblTitreBail.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelBail.add(lblTitreBail, BorderLayout.NORTH);

		// --- Contenu central (à personnaliser plus tard) ---
		panelContenuBail = new JPanel();
		panelBail.add(panelContenuBail, BorderLayout.CENTER);
		panelContenuBail.setLayout(new BorderLayout(5, 5));

		panelGridLibellesBail = new JPanel();
		panelContenuBail.add(panelGridLibellesBail, BorderLayout.WEST);
		panelGridLibellesBail.setLayout(new GridLayout(9, 1, 0, 8));

		JLabel lblIdLocataire = new JLabel("ID du locataire : ");
		lblIdLocataire.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesBail.add(lblIdLocataire);

		JLabel lblNomLocataire = new JLabel("Nom du locataire :");
		lblNomLocataire.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesBail.add(lblNomLocataire);

		JLabel lblPrenomLocataire = new JLabel("Prénom du locataire : ");
		lblPrenomLocataire.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesBail.add(lblPrenomLocataire);

		JLabel lblDateDebutBail = new JLabel("Date de début du bail :");
		lblDateDebutBail.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesBail.add(lblDateDebutBail);

		JLabel lblDateFinBail = new JLabel("Date de fin du bail : ");
		lblDateFinBail.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesBail.add(lblDateFinBail);

		JLabel lblGaragesAssocies = new JLabel("Liste des garages associés : ");
		lblGaragesAssocies.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesBail.add(lblGaragesAssocies);

		JLabel lblDepotGarantie = new JLabel("Dépôt de garantie : ");
		panelGridLibellesBail.add(lblDepotGarantie);

		JLabel lblEtatLieuxEntree = new JLabel("État des lieux entrée : ");
		lblEtatLieuxEntree.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesBail.add(lblEtatLieuxEntree);

		JLabel lblIdGarant = new JLabel("ID du garant rattaché : ");
		lblIdGarant.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridLibellesBail.add(lblIdGarant);

		JPanel panelGridBail = new JPanel();
		panelContenuBail.add(panelGridBail, BorderLayout.CENTER);
		panelGridBail.setLayout(new GridLayout(9, 1, 0, 8));

		lblIdLocataireValeur = new JLabel("");
		lblIdLocataireValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridBail.add(lblIdLocataireValeur);

		lblNomLocataireValeur = new JLabel("");
		lblNomLocataireValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridBail.add(lblNomLocataireValeur);

		lblPrenomLocataireValeur = new JLabel("");
		lblPrenomLocataireValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridBail.add(lblPrenomLocataireValeur);

		lblDateDebutBailValeur = new JLabel("");
		lblDateDebutBailValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridBail.add(lblDateDebutBailValeur);

		lblDateFinBailValeur = new JLabel("");
		lblDateFinBailValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridBail.add(lblDateFinBailValeur);

		lblGaragesAssociesValeur = new JLabel("[liste garages]");
		lblGaragesAssociesValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridBail.add(lblGaragesAssociesValeur);

		lblDepotGarantieValeur = new JLabel("[Lorem ipsum]");
		panelGridBail.add(lblDepotGarantieValeur);

		JPanel panelBoutonEtatLieuxEntree = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBoutonEtatLieuxEntree.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelGridBail.add(panelBoutonEtatLieuxEntree);

		btnAjouterEtatLieuxEntree = new JButton("Ajouter");
		btnAjouterEtatLieuxEntree.addActionListener(gestionClic);
		panelBoutonEtatLieuxEntree.add(btnAjouterEtatLieuxEntree);

		btnConsulterEtatLieuxEntree = new JButton("Consulter");
		btnConsulterEtatLieuxEntree.addActionListener(gestionClic);
		panelBoutonEtatLieuxEntree.add(btnConsulterEtatLieuxEntree);

		lblIdGarantValeur = new JLabel("[Lorem ipsum dolor sit amet consectetur adipiscing elit.]");
		lblIdGarantValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridBail.add(lblIdGarantValeur);

		// --- Boutons du bas ---
		JPanel panelBoutonsBail = new JPanel();
		FlowLayout flPanelBoutonsBail = (FlowLayout) panelBoutonsBail.getLayout();
		flPanelBoutonsBail.setAlignment(FlowLayout.RIGHT);
		panelBail.add(panelBoutonsBail, BorderLayout.SOUTH);

		btnCreerBail = new JButton("Créer un bail");
		btnCreerBail.addActionListener(gestionClic);

		btnPaiementLoyer = new JButton("Paiement loyer");
		btnPaiementLoyer.addActionListener(gestionClic);
		panelBoutonsBail.add(btnPaiementLoyer);
		panelBoutonsBail.add(btnCreerBail);

		btnResilierBail = new JButton("Résilier le bail");
		btnResilierBail.addActionListener(gestionClic);
		panelBoutonsBail.add(btnResilierBail);

		/////////////////////////////
		// === FIN PANEL BAIL === //
		///////////////////////////

		JPanel panelRelevesCompteurs = new JPanel();
		panelBoxLayoutGeneralY.add(panelRelevesCompteurs);
		panelRelevesCompteurs.setLayout(new BorderLayout(0, 5));

		JLabel lblRelevesCompteurs = new JLabel("<html><u>Relevés de compteurs du bien :</u></html>");
		lblRelevesCompteurs.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelRelevesCompteurs.add(lblRelevesCompteurs, BorderLayout.NORTH);
		panelRelevesCompteurs.setBorder(new EmptyBorder(0, 0, 12, 0)); // espace en bas

		JPanel panelTableReleves = new JPanel();
		panelRelevesCompteurs.add(panelTableReleves, BorderLayout.CENTER);
		panelTableReleves.setLayout(new BorderLayout(0, 0));

		tableReleves = new JTable();
		tableReleves.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null }, },
				new String[] { "Identifiant compteur", "Date du relev\u00E9", "Index", "Prix / unit\u00E9",
						"Partie fixe", "Total" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		gestionTableRelev = new GestionTableRelevBienInfos(this);

		// Permet de limiter l'affichage de max 15 lignes pour le tableau
		int hauteurTableReleves = tableReleves.getRowHeight() * 5;
		tableReleves.setPreferredScrollableViewportSize(
				new java.awt.Dimension(tableReleves.getPreferredScrollableViewportSize().width, hauteurTableReleves));

		// Ajout au scroll pane
		JScrollPane scrollPaneTableReleves = new JScrollPane(tableReleves);
		panelTableReleves.add(scrollPaneTableReleves, BorderLayout.CENTER);

		JPanel panelBoutonsCompteurs = new JPanel();
		FlowLayout flowLayout4 = (FlowLayout) panelBoutonsCompteurs.getLayout();
		flowLayout4.setAlignment(FlowLayout.RIGHT);
		panelRelevesCompteurs.add(panelBoutonsCompteurs, BorderLayout.SOUTH);

		JButton btnRegularisation = new JButton("Effectuer la régularisation");
		btnRegularisation.addActionListener(gestionClic);
		panelBoutonsCompteurs.add(btnRegularisation);

		JButton btnSupprimerReleve = new JButton("Supprimer le relevé");
		btnSupprimerReleve.addActionListener(gestionClic);

		JButton btnAjouterReleve = new JButton("Ajouter un relevé");
		btnAjouterReleve.addActionListener(gestionClic);
		panelBoutonsCompteurs.add(btnAjouterReleve);
		panelBoutonsCompteurs.add(btnSupprimerReleve);

		JPanel panelInterventions = new JPanel();
		panelBoxLayoutGeneralY.add(panelInterventions);
		panelInterventions.setLayout(new BorderLayout(0, 5));
		panelInterventions.setBorder(new EmptyBorder(0, 0, 12, 0)); // 5px de marge en haut et en bas

		JLabel lblinterventionsSurLeBien = new JLabel("<html><u>Interventions sur le bien :</u></html>");
		lblinterventionsSurLeBien.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelInterventions.add(lblinterventionsSurLeBien, BorderLayout.NORTH);

		JPanel panelTableInterventions = new JPanel();
		panelInterventions.add(panelTableInterventions, BorderLayout.CENTER);
		panelTableInterventions.setLayout(new BorderLayout(0, 0));

		tableInterventions = new JTable();
		tableInterventions
				.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null, null }, },
						new String[] { "Identifiant", "Intitul\u00E9", "Num\u00E9ro facture", "Montant facture",
								"Num\u00E9ro devis", "Montant devis", "Date d'intervention" }) {
					boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

					@Override
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});

		gestionTableInterv = new GestionTableIntervBienInfos(this);

		// Permet de limiter l'affichage de max 15 lignes pour le tableau
		int hauteurTableInterventions = tableInterventions.getRowHeight() * 8;
		tableInterventions.setPreferredScrollableViewportSize(new java.awt.Dimension(
				tableInterventions.getPreferredScrollableViewportSize().width, hauteurTableInterventions));
		// Ajout au scroll pane
		JScrollPane scrollPaneTableInterventions = new JScrollPane(tableInterventions);
		panelTableInterventions.add(scrollPaneTableInterventions, BorderLayout.CENTER);

		JPanel panelBoutonsInterventions = new JPanel();
		FlowLayout flowLayout1 = (FlowLayout) panelBoutonsInterventions.getLayout();
		flowLayout1.setAlignment(FlowLayout.RIGHT);
		panelInterventions.add(panelBoutonsInterventions, BorderLayout.SOUTH);

		JButton btnAjouterIntervention = new JButton("Ajouter une intervention");
		btnAjouterIntervention.addActionListener(gestionClic);
		panelBoutonsInterventions.add(btnAjouterIntervention);

		JButton btnSupprimerIntervention = new JButton("Supprimer l'intervention");
		btnSupprimerIntervention.addActionListener(gestionClic);
		panelBoutonsInterventions.add(btnSupprimerIntervention);

		JPanel panelDiagnostics = new JPanel();
		panelBoxLayoutGeneralY.add(panelDiagnostics);
		panelDiagnostics.setLayout(new BorderLayout(5, 5));
		panelDiagnostics.setBorder(new EmptyBorder(0, 0, 12, 0)); // 5px de marge en haut et en bas

		JLabel lblTitreDiagnostics = new JLabel("<html><u>Diagnostics du bien :</u></html>");
		lblTitreDiagnostics.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelDiagnostics.add(lblTitreDiagnostics, BorderLayout.NORTH);

		JPanel panelTableDiagnostics = new JPanel();
		panelDiagnostics.add(panelTableDiagnostics, BorderLayout.CENTER);
		panelTableDiagnostics.setLayout(new BorderLayout(0, 0));

		tableDiagnostics = new JTable();
		tableDiagnostics.setModel(new DefaultTableModel(new Object[][] { { null, null, null } },
				new String[] { "Libell\u00E9", "Date d\u00E9but", "Date fin" }) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		gestionTableDiag = new GestionTableDiagnosticsBienInfos(this);

		// Permet de limiter l'affichage de max 15 lignes pour le tableau
		int hauteurTableDiagnostics = tableDiagnostics.getRowHeight() * 5;
		tableDiagnostics.setPreferredScrollableViewportSize(new java.awt.Dimension(
				tableDiagnostics.getPreferredScrollableViewportSize().width, hauteurTableDiagnostics));

		// Ajout au scroll pane
		JScrollPane scrollPaneTablDiagnostics = new JScrollPane(tableDiagnostics);
		panelTableDiagnostics.add(scrollPaneTablDiagnostics, BorderLayout.CENTER);

		JPanel panelBoutonsDiagnostics = new JPanel();
		FlowLayout flPanelBoutonsDiagnostics = (FlowLayout) panelBoutonsDiagnostics.getLayout();
		flPanelBoutonsDiagnostics.setAlignment(FlowLayout.RIGHT);
		panelDiagnostics.add(panelBoutonsDiagnostics, BorderLayout.SOUTH);

		JButton btnAjouterDiagnostic = new JButton("Ajouter un diagnostic");
		btnAjouterDiagnostic.addActionListener(gestionClic);
		panelBoutonsDiagnostics.add(btnAjouterDiagnostic);

		JButton btnSupprimerDiagnostic = new JButton("Supprimer le diagnostic");
		btnSupprimerDiagnostic.addActionListener(gestionClic);
		panelBoutonsDiagnostics.add(btnSupprimerDiagnostic);

		JPanel panelAnciensLocataires = new JPanel();
		panelAnciensLocataires.setBorder(new EmptyBorder(0, 0, 12, 0));
		panelBoxLayoutGeneralY.add(panelAnciensLocataires);
		panelAnciensLocataires.setLayout(new BorderLayout(0, 5));

		JPanel panelTableAnciensLocataires = new JPanel();
		panelAnciensLocataires.add(panelTableAnciensLocataires, BorderLayout.CENTER);
		panelTableAnciensLocataires.setLayout(new BorderLayout(0, 0));

		tableAnciensLocataires = new JTable();
		tableAnciensLocataires.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null } },
				new String[] { "Date de fin du bail", "ID du locataire", "Nom", "Pr\u00E9nom",
						"Num\u00E9ro de t\u00E9l\u00E9phone" }) {
			boolean[] columnEditables = new boolean[] { true, false, false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		gestionTableAnciensLocataires = new GestionTableAnciensLocBienInfos(this);

		// Permet de limiter l'affichage de max 15 lignes pour le tableau
		int hauteurTableAnciensLocataires = tableAnciensLocataires.getRowHeight() * 5;
		tableAnciensLocataires.setPreferredScrollableViewportSize(new java.awt.Dimension(
				tableAnciensLocataires.getPreferredScrollableViewportSize().width, hauteurTableAnciensLocataires));
		// Ajout au scroll pane
		JScrollPane scrollPaneTableAnciensLocataires = new JScrollPane(tableAnciensLocataires);
		panelTableAnciensLocataires.add(scrollPaneTableAnciensLocataires, BorderLayout.CENTER);

		JLabel lblAnciensLocataires = new JLabel("<html><u>Anciens locataires du bien :</u></html>");
		lblAnciensLocataires.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelAnciensLocataires.add(lblAnciensLocataires, BorderLayout.NORTH);

		JPanel panelBoutonsAnciensLocataires = new JPanel();
		FlowLayout flPanelBoutonsAnciensLocataires = (FlowLayout) panelBoutonsAnciensLocataires.getLayout();
		flPanelBoutonsAnciensLocataires.setAlignment(FlowLayout.RIGHT);
		panelAnciensLocataires.add(panelBoutonsAnciensLocataires, BorderLayout.SOUTH);

		JButton btnConsulterAncienLocataire = new JButton("Consulter locataire");
		btnConsulterAncienLocataire.addActionListener(gestionClic);
		panelBoutonsAnciensLocataires.add(btnConsulterAncienLocataire);

		// === PANEL ANCIENS ÉTATS DES LIEUX ===
		JPanel panelAnciensEtatsLieux = new JPanel();
		panelAnciensEtatsLieux.setBorder(new EmptyBorder(0, 0, 12, 0));
		panelBoxLayoutGeneralY.add(panelAnciensEtatsLieux);
		panelAnciensEtatsLieux.setLayout(new BorderLayout(0, 5));

		// --- Tableau principal ---
		JPanel panelTableAnciensEtatsLieux = new JPanel();
		panelAnciensEtatsLieux.add(panelTableAnciensEtatsLieux, BorderLayout.CENTER);
		panelTableAnciensEtatsLieux.setLayout(new BorderLayout(0, 0));

		this.tableAnciensEtatsLieux = new JTable();
		tableAnciensEtatsLieux.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null } },
				new String[] { "Date de l'\u00E9tat des lieux", "Type (entr\u00E9e/sortie)", "ID du locataire",
						"Nom du locataire" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		gestionTableAnciensEtatsLieux = new GestionTableEtatsLieuxFenBLInfos(this);

		// --- Limite d'affichage : 15 lignes ---
		int hauteurTableAnciensEtatsLieux = tableAnciensEtatsLieux.getRowHeight() * 3;
		tableAnciensEtatsLieux.setPreferredScrollableViewportSize(new java.awt.Dimension(
				tableAnciensEtatsLieux.getPreferredScrollableViewportSize().width, hauteurTableAnciensEtatsLieux));

		// --- ScrollPane du tableau ---
		JScrollPane scrollPaneTableAnciensEtatsLieux = new JScrollPane(tableAnciensEtatsLieux);
		panelTableAnciensEtatsLieux.add(scrollPaneTableAnciensEtatsLieux, BorderLayout.CENTER);

		// --- Titre du panel ---
		JLabel lblAnciensEtatsLieux = new JLabel("<html><u>Anciens états des lieux du bien :</u></html>");
		lblAnciensEtatsLieux.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelAnciensEtatsLieux.add(lblAnciensEtatsLieux, BorderLayout.NORTH);

		// --- Boutons ---
		JPanel panelBoutonsAnciensEtatsLieux = new JPanel();
		FlowLayout flPanelBoutonsAnciensEtatsLieux = (FlowLayout) panelBoutonsAnciensEtatsLieux.getLayout();
		flPanelBoutonsAnciensEtatsLieux.setAlignment(FlowLayout.RIGHT);
		panelAnciensEtatsLieux.add(panelBoutonsAnciensEtatsLieux, BorderLayout.SOUTH);

		JButton btnConsulterAncienEtatLieux = new JButton("Consulter état des lieux");
		btnConsulterAncienEtatLieux.addActionListener(gestionClic);
		panelBoutonsAnciensEtatsLieux.add(btnConsulterAncienEtatLieux);

		// === SECTION COMPTEURS ===
		JPanel panelCompteurs = new JPanel();
		panelBoxLayoutGeneralY.add(panelCompteurs);
		panelCompteurs.setLayout(new BorderLayout(0, 5));
		panelCompteurs.setBorder(new EmptyBorder(0, 0, 12, 0));

		JLabel lblCompteursBien = new JLabel("<html><u>Compteurs du bien :</u></html>");
		lblCompteursBien.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelCompteurs.add(lblCompteursBien, BorderLayout.NORTH);

		JPanel panelTableCompteurs = new JPanel();
		panelCompteurs.add(panelTableCompteurs, BorderLayout.CENTER);
		panelTableCompteurs.setLayout(new BorderLayout(0, 0));

		tableCompteurs = new JTable();
		tableCompteurs.setModel(new DefaultTableModel(new Object[][] { { null, null, null }, },
				new String[] { "Identifiant du compteur", "Type de compteur", "Date d'installation" }) {
			boolean[] columnEditables = new boolean[] { false, false, true };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		gestionTableCompt = new GestionTableCompteursBienInfos(this);

		// Limite l'affichage à 6 lignes max
		int hauteurTableCompteurs = tableCompteurs.getRowHeight() * 6;
		tableCompteurs.setPreferredScrollableViewportSize(new java.awt.Dimension(
				tableCompteurs.getPreferredScrollableViewportSize().width, hauteurTableCompteurs));

		// Ajout au scrollpane
		JScrollPane scrollPaneTableCompteurs = new JScrollPane(tableCompteurs);
		panelTableCompteurs.add(scrollPaneTableCompteurs, BorderLayout.CENTER);

		JPanel panelBoutonsCompteursGestion = new JPanel();
		FlowLayout flowLayoutCompteurs = (FlowLayout) panelBoutonsCompteursGestion.getLayout();
		flowLayoutCompteurs.setAlignment(FlowLayout.RIGHT);
		panelCompteurs.add(panelBoutonsCompteursGestion, BorderLayout.SOUTH);

		JButton btnAjouterCompteur = new JButton("Ajouter un compteur");
		btnAjouterCompteur.addActionListener(gestionClic);
		panelBoutonsCompteursGestion.add(btnAjouterCompteur);

		JButton btnSupprimerCompteur = new JButton("Supprimer le compteur");
		btnSupprimerCompteur.addActionListener(gestionClic);
		panelBoutonsCompteursGestion.add(btnSupprimerCompteur);

		//

		// === SECTION SOLDE TOUT COMPTE ===
		JPanel panelHistoriqueSoldeToutCompte = new JPanel();
		panelBoxLayoutGeneralY.add(panelHistoriqueSoldeToutCompte);
		panelHistoriqueSoldeToutCompte.setLayout(new BorderLayout(0, 5));
		panelHistoriqueSoldeToutCompte.setBorder(new EmptyBorder(0, 0, 12, 0));

		JLabel lblHistoriqueSoldeToutCompte = new JLabel("<html><u>Anciens Soldes de tout compte :</u></html>");
		lblHistoriqueSoldeToutCompte.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelHistoriqueSoldeToutCompte.add(lblHistoriqueSoldeToutCompte, BorderLayout.NORTH);

		JPanel panelTableHistoriqueSoldeToutCompte = new JPanel();
		panelHistoriqueSoldeToutCompte.add(panelTableHistoriqueSoldeToutCompte, BorderLayout.CENTER);
		panelTableHistoriqueSoldeToutCompte.setLayout(new BorderLayout(0, 0));

		tableHistoriqueSoldeToutCompte = new JTable();
		tableHistoriqueSoldeToutCompte.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Date Paiement", "Émis / Reçu", "Montant" }) {
					boolean[] columnEditables = new boolean[] { false, false, false };

					@Override
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});

		gestionTableSTC = new GestionTableSoldeToutCompteBienInfos(this);
		tableHistoriqueSoldeToutCompte.getSelectionModel().addListSelectionListener(gestionTableSTC);

		int hauteurTableHistoriqueSTC = tableHistoriqueSoldeToutCompte.getRowHeight() * 6;
		tableHistoriqueSoldeToutCompte.setPreferredScrollableViewportSize(new Dimension(
				tableHistoriqueSoldeToutCompte.getPreferredScrollableViewportSize().width, hauteurTableHistoriqueSTC));

		JScrollPane scrollPaneTableHistoriqueSTC = new JScrollPane(tableHistoriqueSoldeToutCompte);
		panelTableHistoriqueSoldeToutCompte.add(scrollPaneTableHistoriqueSTC, BorderLayout.CENTER);

		// === SECTION REGULARISATION DES CHARGES ===
		JPanel panelRegularisationCharges = new JPanel();
		panelBoxLayoutGeneralY.add(panelRegularisationCharges);
		panelRegularisationCharges.setLayout(new BorderLayout(0, 5));
		panelRegularisationCharges.setBorder(new EmptyBorder(0, 0, 12, 0));

		// --- Titre ---
		JLabel lblRegularisationCharges = new JLabel("<html><u>Anciennes Régularisations des charges :</u></html>");
		lblRegularisationCharges.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelRegularisationCharges.add(lblRegularisationCharges, BorderLayout.NORTH);

		// --- Table ---
		JPanel panelTableRegularisationCharges = new JPanel();
		panelRegularisationCharges.add(panelTableRegularisationCharges, BorderLayout.CENTER);
		panelTableRegularisationCharges.setLayout(new BorderLayout(0, 0));

		tableRegularisationCharges = new JTable();
		tableRegularisationCharges.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "Date Paiement", "Émis / Reçu", "Montant" }) {
					boolean[] columnEditables = new boolean[] { false, false, false };

					@Override
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});

		gestionTableRegularisationCharges = new GestionTableRegularisationBienInfos(this);
		tableRegularisationCharges.getSelectionModel().addListSelectionListener(gestionTableRegularisationCharges);

		// Limite d'affichage : 6 lignes
		int hauteurTableRegularisation = tableRegularisationCharges.getRowHeight() * 6;
		tableRegularisationCharges.setPreferredScrollableViewportSize(new Dimension(
				tableRegularisationCharges.getPreferredScrollableViewportSize().width, hauteurTableRegularisation));

		// ScrollPane
		JScrollPane scrollPaneTableRegularisation = new JScrollPane(tableRegularisationCharges);
		panelTableRegularisationCharges.add(scrollPaneTableRegularisation, BorderLayout.CENTER);

		JPanel panelTitre = new JPanel();
		contentPanelGeneralBorder.add(panelTitre, BorderLayout.NORTH);
		panelTitre.setLayout(new GridLayout(1, 3, 0, 0));

		JLabel lblVide = new JLabel("");
		panelTitre.add(lblVide);

		JLabel lblTitre = new JLabel("<html><u>Informations du bien louable :</u></html>");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 16));
		panelTitre.add(lblTitre);

		JPanel panelFlowQuitter = new JPanel();
		FlowLayout flowLayout2 = (FlowLayout) panelFlowQuitter.getLayout();
		flowLayout2.setAlignment(FlowLayout.RIGHT);
		panelTitre.add(panelFlowQuitter);

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(gestionClic);
		panelFlowQuitter.add(btnQuitter);

		scrollPaneGeneral.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneGeneral.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneGeneral.getVerticalScrollBar().setUnitIncrement(15); // Vitesse du scroll, comme la fenêtre est grande
																		// j'ai augmenté un peu

		// Met le JScrollPane comme contentPane de la fenêtre
		setContentPane(scrollPaneGeneral);

		// Créer gestionTableTravaux AVANT d'initialiser les valeurs pour que le
		// listener soit prêt
		gestionTableChiffresCles = new GestionTableTotalTravaux(this);
		gestionClic.initialiserValeurs();
		gestionTableChiffresCles.setValeursLabels();
		comboBoxAnnee.addActionListener(gestionTableChiffresCles);

		this.tableAnciensLocataires.getSelectionModel().addListSelectionListener(gestionTableAnciensLocataires); // Ajouter
																													// //
																													// table
																													// biens
		this.tableAnciensEtatsLieux.getSelectionModel().addListSelectionListener(gestionTableAnciensEtatsLieux);

		// Remplir la table des travaux après l'initialisation
		gestionTableChiffresCles.ecrireLigneTable();
	}

	/**
	 * Actualise l'affichage des libellés avec les informations du bien louable. Met
	 * à jour tous les champs de la fenêtre avec les données du bien passé en
	 * paramètre.
	 * 
	 * @param bienModifie le bien louable contenant les nouvelles données à afficher
	 */
	public void actualisationLibelles(BienLouable bienModifie) {
		gestionClic.majBienSelect(bienModifie); // IMPORTANT POUR METTRE A J EN ARRIERE PLAN
		lblIdentifiantValeur.setText(bienModifie.getIdBien());
		lblTypeBienValeur.setText(bienModifie.getTypeBien());
		lblAdresseValeur.setText(bienModifie.getAdresse());
		lblCodePostalValeur.setText(bienModifie.getCodePostal());
		lblVilleValeur.setText(bienModifie.getVille());
		lblDateConstructionValeur.setText(String.valueOf(bienModifie.getDateConstruction()));
		lblSurfaceHabitableValeur.setText(String.valueOf(bienModifie.getSurfaceHabitable()));
		lblNbPiecesValeur.setText(String.valueOf(bienModifie.getNbPieces()));
		lblPourcentagePartiesCommunesValeur
				.setText(String.valueOf(bienModifie.getPourcentageEntretienPartiesCommunes()));
		lblDPourcentageOrduresValeur.setText(String.valueOf(bienModifie.getPourcentageOrduresMenageres()));
		lblIdBatRattacheValeur.setText(bienModifie.getIdBat());
		lblNumeroFiscalValeur.setText(bienModifie.getNumeroFiscal());
		lblLoyerValeur.setText(String.valueOf(bienModifie.getLoyer()));
		lblProvisionValeur.setText(String.valueOf(bienModifie.getProvisionPourCharges()));
	}

	/**
	 * Récupère la table affichant l'historique des anciens locataires du bien.
	 * 
	 * @return la table des anciens locataires
	 */
	public JTable getTableAnciensLocataires() {
		return this.tableAnciensLocataires;
	}

	/**
	 * Récupère la table affichant l'historique des anciens états des lieux du bien.
	 * 
	 * @return la table des anciens états des lieux
	 */
	public JTable getTableAnciensEtatsLieux() {
		return this.tableAnciensEtatsLieux;
	}

	/**
	 * Récupère la fenêtre ancêtre de la hiérarchie des fenêtres de bien louable.
	 * 
	 * @return la fenêtre ancêtre de type FenBienLouable
	 */
	public FenBienLouable getFenBienAncetre() {
		return this.fenBienAncetre;
	}

	/**
	 * Récupère la table affichant les diagnostics du bien.
	 * 
	 * @return la table des diagnostics
	 */
	public JTable getTableDiagnostics() {
		return tableDiagnostics;
	}

	/**
	 * Récupère la table affichant les interventions effectuées sur le bien.
	 * 
	 * @return la table des interventions
	 */
	public JTable getTableInterventions() {
		return tableInterventions;
	}

	/**
	 * Récupère la table affichant les relevés de compteurs du bien.
	 * 
	 * @return la table des relevés de compteurs
	 */
	public JTable getTableReleves() {
		return tableReleves;
	}

	/**
	 * Récupère le gestionnaire de la table des relevés de compteurs.
	 * 
	 * @return le gestionnaire de table des relevés
	 */
	public GestionTableRelevBienInfos getGestionTableRelev() {
		return gestionTableRelev;
	}

	/**
	 * Récupère le gestionnaire de la table des interventions.
	 * 
	 * @return le gestionnaire de table des interventions
	 */
	public GestionTableIntervBienInfos getGestionTableInterv() {
		return gestionTableInterv;
	}

	/**
	 * Récupère le gestionnaire de la table des diagnostics.
	 * 
	 * @return le gestionnaire de table des diagnostics
	 */
	public GestionTableDiagnosticsBienInfos getGestionTableDiag() {
		return gestionTableDiag;
	}

	/**
	 * Récupère le gestionnaire de la table des anciens locataires.
	 * 
	 * @return le gestionnaire de table des anciens locataires
	 */
	public GestionTableAnciensLocBienInfos getGestionTableAnciensLocataires() {
		return gestionTableAnciensLocataires;
	}

	/**
	 * Récupère le gestionnaire de la table des anciens états des lieux.
	 * 
	 * @return le gestionnaire de table des états des lieux
	 */
	public GestionTableEtatsLieuxFenBLInfos getGestionTableAnciensEtatsLieux() {
		return gestionTableAnciensEtatsLieux;
	}

	/**
	 * Récupère le gestionnaire de la table des compteurs.
	 * 
	 * @return le gestionnaire de table des compteurs
	 */
	public GestionTableCompteursBienInfos getGestionTableCompt() {
		return gestionTableCompt;
	}

	/**
	 * Récupère le gestionnaire de la table des soldes de tout compte.
	 * 
	 * @return le gestionnaire de table STC
	 */
	public GestionTableSoldeToutCompteBienInfos getGestionTableSTC() {
		return gestionTableSTC;
	}

	/**
	 * Récupère le gestionnaire de la table des régularisations de charges.
	 * 
	 * @return le gestionnaire de table de régularisation
	 */
	public GestionTableRegularisationBienInfos getGestionTableRegularisationCharges() {
		return gestionTableRegularisationCharges;
	}

	/**
	 * Récupère la table affichant l'historique des soldes de tout compte.
	 * 
	 * @return la table de l'historique STC
	 */
	public JTable getTableHistoriqueSoldeToutCompte() {
		return tableHistoriqueSoldeToutCompte;
	}

	/**
	 * Récupère la table affichant les régularisations de charges.
	 * 
	 * @return la table des régularisations de charges
	 */
	public JTable getTableRegularisationCharges() {
		return tableRegularisationCharges;
	}

	/**
	 * Récupère la table affichant les compteurs du bien.
	 * 
	 * @return la table des compteurs
	 */
	public JTable getTableCompteurs() {
		return tableCompteurs;
	}

	/**
	 * Récupère le label affichant la valeur de l'identifiant du locataire.
	 * 
	 * @return le label de l'identifiant du locataire
	 */
	public JLabel getLblIdLocataireValeur() {
		return lblIdLocataireValeur;
	}

	/**
	 * Récupère le label affichant la valeur du nom du locataire.
	 * 
	 * @return le label du nom du locataire
	 */
	public JLabel getLblNomLocataireValeur() {
		return lblNomLocataireValeur;
	}

	/**
	 * Récupère le label affichant la valeur du prénom du locataire.
	 * 
	 * @return le label du prénom du locataire
	 */
	public JLabel getLblPrenomLocataireValeur() {
		return lblPrenomLocataireValeur;
	}

	/**
	 * Récupère le label affichant la valeur de la date de début du bail.
	 * 
	 * @return le label de la date de début du bail
	 */
	public JLabel getLblDateDebutBailValeur() {
		return lblDateDebutBailValeur;
	}

	/**
	 * Récupère le label affichant la valeur de la date de fin du bail.
	 * 
	 * @return le label de la date de fin du bail
	 */
	public JLabel getLblDateFinBailValeur() {
		return lblDateFinBailValeur;
	}

	/**
	 * Récupère le label affichant la valeur de l'identifiant du garant.
	 * 
	 * @return le label de l'identifiant du garant
	 */
	public JLabel getLblIdGarantValeur() {
		return lblIdGarantValeur;
	}

	/**
	 * Récupère le label affichant la valeur du dépôt de garantie.
	 * 
	 * @return le label du dépôt de garantie
	 */
	public JLabel getLblDepotGarantieValeur() {
		return lblDepotGarantieValeur;
	}

	/**
	 * Récupère le bouton permettant d'ajouter un état des lieux d'entrée.
	 * 
	 * @return le bouton d'ajout d'état des lieux d'entrée
	 */
	public JButton getBtnAjouterEtatLieuxEntree() {
		return btnAjouterEtatLieuxEntree;
	}

	/**
	 * Récupère le bouton permettant de consulter l'état des lieux d'entrée.
	 * 
	 * @return le bouton de consultation d'état des lieux d'entrée
	 */
	public JButton getBtnConsulterEtatLieuxEntree() {
		return btnConsulterEtatLieuxEntree;
	}

	/**
	 * Récupère le bouton permettant d'effectuer un paiement de loyer.
	 * 
	 * @return le bouton de paiement de loyer
	 */
	public JButton getBtnPaiementLoyer() {
		return btnPaiementLoyer;
	}

	/**
	 * Désactive l'affichage des champs liés au bail lorsqu'aucun bail n'est actif.
	 * Affiche un message indiquant que le bien est disponible à la location.
	 */
	public void desactiverChampsBail() {
		lblTitreBail.setText("<html><u> Le bien est disponible à la location. Vous pouvez créer un bail.</u></html>");
		panelContenuBail.setVisible(false);
		panelGridLibellesBail.setVisible(false);
		btnCreerBail.setVisible(true);
		btnResilierBail.setVisible(false);
		btnPaiementLoyer.setVisible(false);
	}

	/**
	 * Active l'affichage des champs liés au bail lorsqu'un bail est en cours.
	 * Affiche toutes les informations et actions relatives au bail actif.
	 */
	public void activerChampsBail() {
		lblTitreBail.setText("<html><u>Un bail est en cours sur ce bien :</u></html>");
		panelContenuBail.setVisible(true);
		panelGridLibellesBail.setVisible(true);
		btnCreerBail.setVisible(false);
		btnResilierBail.setVisible(true);
		btnPaiementLoyer.setVisible(true);
	}

	/**
	 * Récupère le label affichant la valeur des garages associés au bien.
	 * 
	 * @return le label des garages associés
	 */
	public JLabel lblGaragesAssociesValeur() {
		return lblGaragesAssociesValeur;
	}

	/**
	 * Modifie le label affichant la valeur des garages associés au bien.
	 * 
	 * @param lblGaragesAssociesValeurs2 le nouveau label des garages associés
	 */
	public void setLblGaragesAssociesValeur(JLabel lblGaragesAssociesValeurs2) {
		this.lblGaragesAssociesValeur = lblGaragesAssociesValeurs2;
	}

	/**
	 * Récupère le label affichant la valeur de l'identifiant du bien.
	 * 
	 * @return le label de l'identifiant
	 */
	public JLabel getLblIdentifiantValeur() {
		return lblIdentifiantValeur;
	}

	/**
	 * Modifie le gestionnaire de la table des soldes de tout compte.
	 * 
	 * @param gestionTableSTC2 le nouveau gestionnaire de table STC
	 */
	public void setGestionTableSTC(GestionTableSoldeToutCompteBienInfos gestionTableSTC2) {
		gestionTableSTC = gestionTableSTC2;
	}

	/**
	 * Modifie le gestionnaire de la table des régularisations de charges.
	 * 
	 * @param gestionTableRegularisationCharges2 le nouveau gestionnaire de table de
	 *                                           régularisation
	 */
	public void setGestionTableRegularisationCharges(
			GestionTableRegularisationBienInfos gestionTableRegularisationCharges2) {
		gestionTableRegularisationCharges = gestionTableRegularisationCharges2;
	}

	/**
	 * Récupère la combo box de sélection de l'année pour les chiffres clés.
	 * 
	 * @return la combo box de l'année
	 */
	public JComboBox<Integer> getComboBoxAnnee() {
		return comboBoxAnnee;
	}

	/**
	 * Modifie la combo box de sélection de l'année pour les chiffres clés.
	 * 
	 * @param comboBoxAnnee la nouvelle combo box de l'année
	 */
	public void setComboBoxAnnee(JComboBox<Integer> comboBoxAnnee) {
		this.comboBoxAnnee = comboBoxAnnee;
	}

	/**
	 * Récupère le label affichant la valeur totale des loyers.
	 * 
	 * @return le label de la valeur des loyers
	 */
	public JLabel getLblValeurLoyers() {
		return lblValeurLoyers;
	}

	/**
	 * Modifie le label affichant la valeur totale des loyers.
	 * 
	 * @param lblValeurLoyers le nouveau label de la valeur des loyers
	 */
	public void setLblValeurLoyers(JLabel lblValeurLoyers) {
		this.lblValeurLoyers = lblValeurLoyers;
	}

	/**
	 * Récupère le label affichant la valeur totale des provisions sur charges.
	 * 
	 * @return le label de la valeur des provisions
	 */
	public JLabel getLblValeurProvisions() {
		return lblValeurProvisions;
	}

	/**
	 * Modifie le label affichant la valeur totale des provisions sur charges.
	 * 
	 * @param lblValeurProvisions le nouveau label de la valeur des provisions
	 */
	public void setLblValeurProvisions(JLabel lblValeurProvisions) {
		this.lblValeurProvisions = lblValeurProvisions;
	}

	/**
	 * Récupère le label affichant la valeur totale des travaux.
	 * 
	 * @return le label de la valeur des travaux
	 */
	public JLabel getLblValeurTravaux() {
		return lblValeurTravaux;
	}

	/**
	 * Modifie le label affichant la valeur totale des travaux.
	 * 
	 * @param lblValeurTravaux le nouveau label de la valeur des travaux
	 */
	public void setLblValeurTravaux(JLabel lblValeurTravaux) {
		this.lblValeurTravaux = lblValeurTravaux;
	}

	/**
	 * Récupère le bien louable actuellement sélectionné.
	 * 
	 * @return le bien louable sélectionné
	 */
	public BienLouable getSelectBien() {
		return selectBien;
	}

	/**
	 * Modifie le bien louable actuellement sélectionné.
	 * 
	 * @param selectBien le nouveau bien louable à sélectionner
	 */
	public void setSelectBien(BienLouable selectBien) {
		this.selectBien = selectBien;
	}

	/**
	 * Récupère la table affichant les travaux effectués sur le bien.
	 * 
	 * @return la table des travaux
	 */
	public JTable getTableTravaux() {
		return tableTravaux;
	}

	public void setTableTravaux(JTable tableTravaux) {
		this.tableTravaux = tableTravaux;
	}

}
