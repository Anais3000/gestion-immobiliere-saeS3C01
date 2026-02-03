package vue.consulter_informations;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

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
import controleur.consulter_informations.fen_batiment.GestionFenBatimentInformations;
import controleur.consulter_informations.fen_batiment.GestionTableAssFenBatInfos;
import controleur.consulter_informations.fen_batiment.GestionTableBiensFenBatInfos;
import controleur.consulter_informations.fen_batiment.GestionTableCompteursFenBatInfos;
import controleur.consulter_informations.fen_batiment.GestionTableIntervFenBatInfos;
import controleur.consulter_informations.fen_batiment.GestionTableRelevFenBatInfos;
import controleur.consulter_informations.fen_batiment.GestionTableTotalTravaux;
import modele.Batiment;
import vue.tables.FenBatiment;

/**
 * Fenêtre de consultation des informations d'un bâtiment. Affiche les données
 * du bâtiment (identifiant, adresse, date de construction, numéro fiscal),
 * ainsi que la liste de ses biens louables, interventions, assurances,
 * compteurs et relevés. Permet également de consulter les chiffres clés (total
 * des travaux) par année.
 */
public class FenBatimentInformations extends JFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel contentPanelGeneralBorder; // Panel principal dans le JScrollPane
	private JTable tableBiens; // Table des biens louables du bâtiment
	private JTable tableInterventions; // Table des interventions sur le bâtiment
	private JTable tableAssurances; // Table des assurances du bâtiment
	private JTable tableReleves; // Table des relevés de compteur
	private JTable tableCompteurs; // Table des compteurs du bâtiment
	private JTable tableTravaux; // Table des chiffres clés (total travaux)
	private JLabel lblIdentifiantValeur; // Valeur de l'identifiant
	private JLabel lblAdresseValeur; // Valeur de l'adresse
	private JLabel lblCodePostalValeur; // Valeur du code postal
	private JLabel lblVilleValeur; // Valeur de la ville
	private JLabel lblDateConstructionValeur; // Valeur de la date de construction
	private JLabel lblNumeroFiscalValeur; // Valeur du numéro fiscal
	private JComboBox<Integer> comboBoxAnnee; // ComboBox pour sélectionner l'année des chiffres clés

	// Contrôleurs
	private transient GestionFenBatimentInformations gestionClic; // Contrôleur de la fenêtre
	private transient GestionTableBiensFenBatInfos gestionTableBiens; // Contrôleur de la table des biens
	private transient GestionTableIntervFenBatInfos gestionTableInterv; // Contrôleur de la table des interventions
	private transient GestionTableAssFenBatInfos gestionTableAss; // Contrôleur de la table des assurances
	private transient GestionTableRelevFenBatInfos gestionTableRelev; // Contrôleur de la table des relevés
	private transient GestionTableCompteursFenBatInfos gestionTableCompt; // Contrôleur de la table des compteurs
	private transient GestionTableTotalTravaux gestionTableTotalTravaux; // Contrôleur de la table des travaux

	// Variables de gestion
	private transient Batiment selectBatiment; // Bâtiment sélectionné
	private FenBatiment fenBatAncetre; // Fenêtre ancêtre (table des bâtiments)

	/**
	 * Constructeur de la fenêtre de consultation d'un bâtiment. Crée une interface
	 * affichant toutes les informations du bâtiment, ses biens louables,
	 * interventions, assurances, compteurs, relevés et les chiffres clés annuels.
	 * Permet la modification des informations et la suppression du bâtiment.
	 * 
	 * @param selectBatiment2 le bâtiment dont on souhaite consulter les
	 *                        informations
	 * @param fenAncetre      la fenêtre ancêtre (table des bâtiments)
	 */
	public FenBatimentInformations(Batiment selectBatiment2, FenBatiment fenAncetre) {

		// Initialisation des variables de gestion
		fenBatAncetre = fenAncetre;
		selectBatiment = selectBatiment2;

		// Création du contrôleur principal qui gère les événements de la fenêtre
		gestionClic = new GestionFenBatimentInformations(this, selectBatiment);

		// Configuration de la taille et position de la fenêtre
		setBounds(100, 100, 900, 600);

		// Panel principal
		contentPanelGeneralBorder = new JPanel();
		contentPanelGeneralBorder.setBorder(new EmptyBorder(5, 5, 5, 5));

		// ScrollPane pour permettre le défilement de tout le contenu
		JScrollPane scrollPaneGeneral = new JScrollPane(contentPanelGeneralBorder);
		contentPanelGeneralBorder.setLayout(new BorderLayout(0, 0));

		// Panel central avec un BoxLayout vertical pour empiler tous les panels de
		// sections
		JPanel panelBoxLayoutGeneralY = new JPanel();
		contentPanelGeneralBorder.add(panelBoxLayoutGeneralY, BorderLayout.CENTER);
		panelBoxLayoutGeneralY.setLayout(new BoxLayout(panelBoxLayoutGeneralY, BoxLayout.Y_AXIS));

		// ===== SECTION 1 : INFORMATIONS DU BÂTIMENT =====
		JPanel panelInformations = new JPanel();
		panelBoxLayoutGeneralY.add(panelInformations);
		panelInformations.setLayout(new BorderLayout(5, 5));
		panelInformations.setBorder(new EmptyBorder(0, 0, 12, 0)); // 5px de marge en haut et en bas

		// Panel contenant les libellés (côté gauche)
		JPanel panelGridLibelleInfos = new JPanel();
		panelInformations.add(panelGridLibelleInfos, BorderLayout.WEST);
		panelGridLibelleInfos.setLayout(new GridLayout(6, 1, 0, 8));

		JLabel lblIdentifiant = new JLabel("Identifiant : ");
		lblIdentifiant.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibelleInfos.add(lblIdentifiant);

		JLabel lblAdresse = new JLabel("Adresse : ");
		lblAdresse.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibelleInfos.add(lblAdresse);

		JLabel lblCodePostal = new JLabel("Code postal : ");
		lblCodePostal.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibelleInfos.add(lblCodePostal);

		JLabel lblVille = new JLabel("Ville : ");
		lblVille.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibelleInfos.add(lblVille);

		JLabel lblDateConstruction = new JLabel("Date de construction : ");
		lblDateConstruction.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibelleInfos.add(lblDateConstruction);

		JLabel lblNumeroFiscal = new JLabel("Numéro fiscal :");
		panelGridLibelleInfos.add(lblNumeroFiscal);

		// Panel contenant les valeurs des informations (côté centre)
		JPanel panelGridInfos = new JPanel();
		panelInformations.add(panelGridInfos, BorderLayout.CENTER);
		panelGridInfos.setLayout(new GridLayout(6, 1, 0, 8));

		// Remplissage des valeurs à partir du bâtiment sélectionné
		lblIdentifiantValeur = new JLabel(selectBatiment.getIdBat());
		lblIdentifiantValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblIdentifiantValeur);

		lblAdresseValeur = new JLabel(selectBatiment.getAdresse());
		lblAdresseValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblAdresseValeur);

		lblCodePostalValeur = new JLabel(selectBatiment.getCodePostal());
		lblCodePostalValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblCodePostalValeur);

		lblVilleValeur = new JLabel(selectBatiment.getVille());
		lblVilleValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblVilleValeur);

		lblDateConstructionValeur = new JLabel((selectBatiment.getDateConstruction()).toString());
		lblDateConstructionValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblDateConstructionValeur);

		// Gestion du cas où le numéro fiscal peut être null
		if (selectBatiment.getNumFiscal() == null) {
			lblNumeroFiscalValeur = new JLabel("aucun");
		} else {
			lblNumeroFiscalValeur = new JLabel(selectBatiment.getNumFiscal());
		}
		lblNumeroFiscalValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblNumeroFiscalValeur);

		// Titre de la section en haut
		JLabel lblTitreInformations = new JLabel("<html><u>Informations concernant le bâtiment :</u></html>");
		lblTitreInformations.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelInformations.add(lblTitreInformations, BorderLayout.NORTH);

		// Boutons d'action pour modifier et supprimer le bâtiment (en bas à droite)
		JPanel panelBoutonsInformations = new JPanel();
		FlowLayout flpanelBoutonsInformations = (FlowLayout) panelBoutonsInformations.getLayout();
		flpanelBoutonsInformations.setAlignment(FlowLayout.RIGHT);
		panelInformations.add(panelBoutonsInformations, BorderLayout.SOUTH);

		JButton btnModifierInformations = new JButton("Modifier informations");
		btnModifierInformations.addActionListener(gestionClic);

		JButton btnSupprimerBatiment = new JButton("Supprimer le bâtiment");
		btnSupprimerBatiment.addActionListener(gestionClic);
		panelBoutonsInformations.add(btnSupprimerBatiment);

		panelBoutonsInformations.add(btnModifierInformations);

		// ===== SECTION 2 : CHIFFRES CLÉS - TOTAL DES TRAVAUX =====
		JPanel panelTotalTravaux = new JPanel();
		panelBoxLayoutGeneralY.add(panelTotalTravaux);
		panelTotalTravaux.setLayout(new BorderLayout(5, 5));
		panelTotalTravaux.setBorder(new EmptyBorder(0, 0, 12, 0)); // 5px de marge en haut et en bas

		JLabel lblTravaux = new JLabel("Total des travaux");
		lblTravaux.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelTotalTravaux.add(lblTravaux, BorderLayout.NORTH);

		// Panel contenant la combo box année et la table des totaux
		JPanel panelValeursTravaux = new JPanel();
		panelTotalTravaux.add(panelValeursTravaux, BorderLayout.SOUTH);

		// Table affichant le total des travaux par organisme
		tableTravaux = new JTable();
		tableTravaux.setModel(
				new DefaultTableModel(new Object[][] { { null, null }, }, new String[] { "Organisme", "Montant" }));

		// Gestionnaire de la table qui se met à jour lors du changement d'année
		gestionTableTotalTravaux = new GestionTableTotalTravaux(this);

		// ComboBox pour sélectionner l'année dont on veut voir les travaux
		comboBoxAnnee = new JComboBox<>();
		panelValeursTravaux.add(comboBoxAnnee);
		comboBoxAnnee.addActionListener(gestionTableTotalTravaux);

		JScrollPane scrollPaneTravaux = new JScrollPane(tableTravaux);
		panelValeursTravaux.add(scrollPaneTravaux);

		// ===== SECTION 3 : BIENS LOUABLES DU BÂTIMENT =====
		JPanel panelBiens = new JPanel();
		panelBoxLayoutGeneralY.add(panelBiens);
		panelBiens.setLayout(new BorderLayout(5, 5));
		panelBiens.setBorder(new EmptyBorder(0, 0, 12, 0)); // 5px de marge en haut et en bas

		JLabel lblTitreBiens = new JLabel("<html><u>Biens présents dans le bâtiment :</u></html>");
		lblTitreBiens.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelBiens.add(lblTitreBiens, BorderLayout.NORTH);

		// Panel contenant la table des biens
		JPanel panelTableBiens = new JPanel();
		panelBiens.add(panelTableBiens, BorderLayout.CENTER);
		panelTableBiens.setLayout(new BorderLayout(0, 0));

		// Création de la table des biens avec son modèle (toutes les colonnes non
		// éditables)
		this.tableBiens = new JTable();
		tableBiens.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null } },
				new String[] { "Identifiant", "Type", "Date de construction", "Surface habitable",
						"Nombre de pi\u00E8ces", "Num\u00E9ro fiscal" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		// Gestionnaire qui gère la sélection et les actions sur les biens
		gestionTableBiens = new GestionTableBiensFenBatInfos(this);

		// Permet de limiter l'affichage de max 15 lignes pour le tableau
		int hauteurTableLogements = tableBiens.getRowHeight() * 15;
		tableBiens.setPreferredScrollableViewportSize(
				new java.awt.Dimension(tableBiens.getPreferredScrollableViewportSize().width, hauteurTableLogements));

		// Ajout au scroll pane
		JScrollPane scrollPaneTableBiens = new JScrollPane(tableBiens);
		panelTableBiens.add(scrollPaneTableBiens, BorderLayout.CENTER);

		JPanel panelBoutonsBiens = new JPanel();
		FlowLayout flPanelBoutonsBiens = (FlowLayout) panelBoutonsBiens.getLayout();
		flPanelBoutonsBiens.setAlignment(FlowLayout.RIGHT);
		panelBiens.add(panelBoutonsBiens, BorderLayout.SOUTH);

		JButton btnCalculTaxeFonciere = new JButton("Calculer Taxe Foncière");
		panelBoutonsBiens.add(btnCalculTaxeFonciere);
		btnCalculTaxeFonciere.addActionListener(gestionClic);

		JButton btnAjouterBienLouable = new JButton("Ajouter un bien");
		btnAjouterBienLouable.addActionListener(gestionClic);

		JButton btnConsulterBien = new JButton("Consulter le bien");
		btnConsulterBien.addActionListener(gestionClic);
		panelBoutonsBiens.add(btnConsulterBien);
		panelBoutonsBiens.add(btnAjouterBienLouable);

		// ===== SECTION 4 : INTERVENTIONS SUR LE BÂTIMENT =====
		JPanel panelInterventions = new JPanel();
		panelBoxLayoutGeneralY.add(panelInterventions);
		panelInterventions.setLayout(new BorderLayout(0, 5));
		panelInterventions.setBorder(new EmptyBorder(0, 0, 12, 0)); // 5px de marge en haut et en bas

		JLabel lblinterventionsSurLeBat = new JLabel("<html><u>Interventions sur le bâtiment :</u></html>");
		lblinterventionsSurLeBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelInterventions.add(lblinterventionsSurLeBat, BorderLayout.NORTH);

		// Panel contenant la table des interventions
		JPanel panelTableInterventions = new JPanel();
		panelInterventions.add(panelTableInterventions, BorderLayout.CENTER);
		panelTableInterventions.setLayout(new BorderLayout(0, 0));

		// Table des interventions (intitulé, facture, devis, date)
		tableInterventions = new JTable();
		tableInterventions
				.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null, null } },
						new String[] { "Identifiant", "Intitul\u00E9", "Num\u00E9ro facture", "Montant facture",
								"Num\u00E9ro devis", "Montant devis", "Date d'intervention" }) {
					boolean[] columnEditables = new boolean[] { true, false, false, false, false, false, false };

					@Override
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});

		// Gestionnaire qui gère les actions sur les interventions
		gestionTableInterv = new GestionTableIntervFenBatInfos(this);

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

		JButton btnSupprimerIntervention = new JButton("Supprimer l'intervention");
		btnSupprimerIntervention.addActionListener(gestionClic);

		JButton btnAjouterIntervention = new JButton("Ajouter une intervention");
		btnAjouterIntervention.addActionListener(gestionClic);

		JButton btnPayerInterv = new JButton("Payer l'intervention");
		btnPayerInterv.addActionListener(gestionClic);
		panelBoutonsInterventions.add(btnPayerInterv);
		panelBoutonsInterventions.add(btnAjouterIntervention);
		panelBoutonsInterventions.add(btnSupprimerIntervention);

		JButton btnInfosFacture = new JButton("Ajouter les infos. facture");
		btnInfosFacture.addActionListener(gestionClic);
		panelBoutonsInterventions.add(btnInfosFacture);

		// ===== SECTION 5 : ASSURANCES DU BÂTIMENT =====
		JPanel panelAssurances = new JPanel();
		panelAssurances.setBorder(new EmptyBorder(0, 0, 12, 0));
		panelBoxLayoutGeneralY.add(panelAssurances);
		panelAssurances.setLayout(new BorderLayout(0, 5));

		// Panel contenant la table des assurances
		JPanel panelTableAssurances = new JPanel();
		panelAssurances.add(panelTableAssurances, BorderLayout.CENTER);
		panelTableAssurances.setLayout(new BorderLayout(0, 0));

		// Table des assurances (police, type, année, montant)
		tableAssurances = new JTable();
		tableAssurances.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null } },
				new String[] { "Num\u00E9ro de police d'assurance", "Type de contrat", "Ann\u00E9e de couverture",
						"Montant pay\u00E9" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		// Gestionnaire qui gère les actions sur les assurances
		gestionTableAss = new GestionTableAssFenBatInfos(this);

		// Permet de limiter l'affichage de max 15 lignes pour le tableau
		int hauteurTableAssurances = tableAssurances.getRowHeight() * 5;
		tableAssurances.setPreferredScrollableViewportSize(new java.awt.Dimension(
				tableAssurances.getPreferredScrollableViewportSize().width, hauteurTableAssurances));
		// Ajout au scroll pane
		JScrollPane scrollPaneTableAssurances = new JScrollPane(tableAssurances);
		panelTableAssurances.add(scrollPaneTableAssurances, BorderLayout.CENTER);

		JLabel lblAssurancesBat = new JLabel("<html><u>Assurances du bâtiment :</u></html>");
		lblAssurancesBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelAssurances.add(lblAssurancesBat, BorderLayout.NORTH);

		JPanel panelBoutonsAssurance = new JPanel();
		FlowLayout flowLayout3 = (FlowLayout) panelBoutonsAssurance.getLayout();
		flowLayout3.setAlignment(FlowLayout.RIGHT);
		panelAssurances.add(panelBoutonsAssurance, BorderLayout.SOUTH);

		JButton btnAjouterAssurance = new JButton("Ajouter une assurance");
		btnAjouterAssurance.addActionListener(gestionClic);

		JButton btnSommeAnnee = new JButton("Somme sur Année");
		panelBoutonsAssurance.add(btnSommeAnnee);
		btnSommeAnnee.addActionListener(gestionClic);
		panelBoutonsAssurance.add(btnAjouterAssurance);

		JButton btnSupprimerAssurance = new JButton("Supprimer l'assurance");
		btnSupprimerAssurance.addActionListener(gestionClic);
		panelBoutonsAssurance.add(btnSupprimerAssurance);

		// ===== SECTION 6 : RELEVÉS DE COMPTEURS =====
		JPanel panelRelevesCompteurs = new JPanel();
		panelBoxLayoutGeneralY.add(panelRelevesCompteurs);
		panelRelevesCompteurs.setLayout(new BorderLayout(0, 5));

		JLabel lblRelevesCompteurs = new JLabel("<html><u>Relevés de compteurs du bâtiment :</u></html>");
		lblRelevesCompteurs.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelRelevesCompteurs.add(lblRelevesCompteurs, BorderLayout.NORTH);

		// Panel contenant la table des relevés
		JPanel panelTableReleves = new JPanel();
		panelRelevesCompteurs.add(panelTableReleves, BorderLayout.CENTER);
		panelTableReleves.setLayout(new BorderLayout(0, 0));

		// Table des relevés de compteurs (identifiant, date, index, prix, total)
		tableReleves = new JTable();
		tableReleves.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null, null } },
				new String[] { "Identifiant compteur", "Date du relev\u00E9", "Index", "Prix / unit\u00E9",
						"Partie fixe", "Total" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		// Gestionnaire qui gère les actions sur les relevés
		gestionTableRelev = new GestionTableRelevFenBatInfos(this);

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

		JButton btnAjouterReleve = new JButton("Ajouter un relevé");
		btnAjouterReleve.addActionListener(gestionClic);
		panelBoutonsCompteurs.add(btnAjouterReleve);

		JButton btnSupprimerReleve = new JButton("Supprimer le relevé");
		btnSupprimerReleve.addActionListener(gestionClic);
		panelBoutonsCompteurs.add(btnSupprimerReleve);

		// ===== SECTION 7 : COMPTEURS DU BÂTIMENT =====
		JPanel panelCompteurs = new JPanel();
		panelBoxLayoutGeneralY.add(panelCompteurs);
		panelCompteurs.setLayout(new BorderLayout(0, 5));
		panelCompteurs.setBorder(new EmptyBorder(12, 0, 12, 0));

		JLabel lblCompteursBat = new JLabel("<html><u>Compteurs présents dans le bâtiment :</u></html>");
		lblCompteursBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelCompteurs.add(lblCompteursBat, BorderLayout.NORTH);

		// Panel contenant la table des compteurs
		JPanel panelTableCompteurs = new JPanel();
		panelCompteurs.add(panelTableCompteurs, BorderLayout.CENTER);
		panelTableCompteurs.setLayout(new BorderLayout(0, 0));

		// Table des compteurs (identifiant, type, date d'installation)
		tableCompteurs = new JTable();
		tableCompteurs.setModel(new DefaultTableModel(new Object[][] { { null, null, null }, },
				new String[] { "Identifiant du compteur", "Type de compteur", "Date d'installation" }) {
			boolean[] columnEditables = new boolean[] { false, false, true };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		// Gestionnaire qui gère les actions sur les compteurs
		gestionTableCompt = new GestionTableCompteursFenBatInfos(this);

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

		// ===== PANEL TITRE ET BOUTON QUITTER (EN HAUT DE LA FENÊTRE) =====
		JPanel panelTitre = new JPanel();
		contentPanelGeneralBorder.add(panelTitre, BorderLayout.NORTH);
		panelTitre.setLayout(new GridLayout(1, 3, 0, 0));
		panelTitre.setBorder(new EmptyBorder(0, 0, 12, 0));

		// Label vide pour centrer le titre
		JLabel lblVide = new JLabel("");
		panelTitre.add(lblVide);

		// Titre principal de la fenêtre (centré)
		JLabel lblTitre = new JLabel("<html><u>Informations du bâtiment :</u></html>");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 16));
		panelTitre.add(lblTitre);

		// Bouton Quitter (aligné à droite)
		JPanel panelFlowQuitter = new JPanel();
		FlowLayout flowLayout2 = (FlowLayout) panelFlowQuitter.getLayout();
		flowLayout2.setAlignment(FlowLayout.RIGHT);
		panelTitre.add(panelFlowQuitter);

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(gestionClic);
		panelFlowQuitter.add(btnQuitter);

		// Configuration des barres de défilement
		scrollPaneGeneral.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneGeneral.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneGeneral.getVerticalScrollBar().setUnitIncrement(15); // Vitesse du scroll, comme la fenêtre est grande
																		// j'ai augmenté un peu

		// Définition du scrollpane comme contenu principal de la fenêtre
		setContentPane(scrollPaneGeneral);

		// ===== INITIALISATION DES DONNÉES =====
		// Remplissage de toutes les tables avec les données du bâtiment
		gestionClic.initialiserValeurs();

		this.tableBiens.getSelectionModel().addListSelectionListener(gestionTableBiens); // Ajouter le listener à
																							// la table biens
	}

	/**
	 * Actualise l'affichage des libellés avec les informations du bâtiment. Met à
	 * jour tous les champs de la fenêtre avec les données du bâtiment passé en
	 * paramètre.
	 * 
	 * @param batimentModifie le bâtiment contenant les nouvelles données à afficher
	 */
	public void actualisationLibelles(Batiment batimentModifie) {
		gestionClic.majBatSelect(batimentModifie);
		lblIdentifiantValeur.setText(batimentModifie.getIdBat());
		lblAdresseValeur.setText(batimentModifie.getAdresse());
		lblCodePostalValeur.setText(batimentModifie.getCodePostal());
		lblVilleValeur.setText(batimentModifie.getVille());
		lblDateConstructionValeur.setText(batimentModifie.getDateConstruction().toString());
		lblNumeroFiscalValeur.setText(batimentModifie.getNumFiscal());
	}

	/**
	 * Retourne la fenêtre ancêtre pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return la fenêtre FenBatiment
	 */
	public FenBatiment getFenBatAncetre() {
		return fenBatAncetre;
	}

	/**
	 * Retourne le contrôleur de la table des biens pour pouvoir l'utiliser dans les
	 * gestions.
	 * 
	 * @return le contrôleur GestionTableBiensFenBatInfos
	 */
	public GestionTableBiensFenBatInfos getGestionTableBiens() {
		return gestionTableBiens;
	}

	/**
	 * Retourne le contrôleur de la table des interventions pour pouvoir l'utiliser
	 * dans les gestions.
	 * 
	 * @return le contrôleur GestionTableIntervFenBatInfos
	 */
	public GestionTableIntervFenBatInfos getGestionTableInterv() {
		return gestionTableInterv;
	}

	/**
	 * Retourne le contrôleur de la table des assurances pour pouvoir l'utiliser
	 * dans les gestions.
	 * 
	 * @return le contrôleur GestionTableAssFenBatInfos
	 */
	public GestionTableAssFenBatInfos getGestionTableAss() {
		return gestionTableAss;
	}

	/**
	 * Retourne le contrôleur de la table des relevés pour pouvoir l'utiliser dans
	 * les gestions.
	 * 
	 * @return le contrôleur GestionTableRelevFenBatInfos
	 */
	public GestionTableRelevFenBatInfos getGestionTableRelev() {
		return gestionTableRelev;
	}

	/**
	 * Retourne le contrôleur de la table des compteurs pour pouvoir l'utiliser dans
	 * les gestions.
	 * 
	 * @return le contrôleur GestionTableCompteursFenBatInfos
	 */
	public GestionTableCompteursFenBatInfos getGestionTableCompt() {
		return gestionTableCompt;
	}

	/**
	 * Retourne la table des biens louables pour pouvoir l'utiliser dans les
	 * gestions.
	 * 
	 * @return la JTable des biens louables
	 */
	public JTable getTableBiens() {
		return this.tableBiens;
	}

	/**
	 * Retourne la table des interventions pour pouvoir l'utiliser dans les
	 * gestions.
	 * 
	 * @return la JTable des interventions
	 */
	public JTable getTableInterventions() {
		return tableInterventions;
	}

	/**
	 * Retourne la table des assurances pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return la JTable des assurances
	 */
	public JTable getTableAssurances() {
		return tableAssurances;
	}

	/**
	 * Retourne la table des relevés pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return la JTable des relevés
	 */
	public JTable getTableReleves() {
		return tableReleves;
	}

	/**
	 * Retourne la table des compteurs pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return la JTable des compteurs
	 */
	public JTable getTableCompteurs() {
		return tableCompteurs;
	}

	/**
	 * Retourne le bâtiment sélectionné pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return le bâtiment actuellement consulté
	 */
	public Batiment getSelectBatiment() {
		return selectBatiment;
	}

	/**
	 * Modifie le bâtiment sélectionné.
	 * 
	 * @param selectBatiment2 le nouveau bâtiment
	 */
	public void setSelectBatiment(Batiment selectBatiment2) {
		selectBatiment = selectBatiment2;
	}

	/**
	 * Retourne la table des travaux (chiffres clés) pour pouvoir l'utiliser dans
	 * les gestions.
	 * 
	 * @return la JTable des travaux
	 */
	public JTable getTableTravaux() {
		return tableTravaux;
	}

	/**
	 * Modifie la table des travaux.
	 * 
	 * @param tableTravaux la nouvelle JTable des travaux
	 */
	public void setTableTravaux(JTable tableTravaux) {
		this.tableTravaux = tableTravaux;
	}

	/**
	 * Retourne la ComboBox de sélection d'année pour pouvoir l'utiliser dans les
	 * gestions.
	 * 
	 * @return la JComboBox des années
	 */
	public JComboBox<Integer> getComboBoxAnnee() {
		return comboBoxAnnee;
	}

	/**
	 * Modifie la ComboBox de sélection d'année.
	 * 
	 * @param comboBoxAnnee la nouvelle JComboBox d'années
	 */
	public void setComboBoxAnnee(JComboBox<Integer> comboBoxAnnee) {
		this.comboBoxAnnee = comboBoxAnnee;
	}

	/**
	 * Retourne le contrôleur de la table des travaux pour pouvoir l'utiliser dans
	 * les gestions.
	 * 
	 * @return le contrôleur GestionTableTotalTravaux
	 */
	public GestionTableTotalTravaux getGestionTableTotalTravaux() {
		return gestionTableTotalTravaux;
	}

}
