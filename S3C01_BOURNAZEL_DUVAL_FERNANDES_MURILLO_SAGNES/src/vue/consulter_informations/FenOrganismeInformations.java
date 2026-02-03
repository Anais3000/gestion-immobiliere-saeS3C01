package vue.consulter_informations;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

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
import controleur.consulter_informations.fen_organisme.GestionFenOrganismeInformations;
import controleur.consulter_informations.fen_organisme.GestionTableIntervOrganisme;
import modele.Organisme;
import vue.tables.FenOrganisme;

/**
 * Fenêtre de consultation des informations d'un organisme. Affiche les données
 * de l'organisme (nom, spécialité, coordonnées, SIRET) ainsi que la liste de
 * toutes les interventions réalisées par cet organisme.
 */
public class FenOrganismeInformations extends JFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel contentPanelGeneralBorder; // Panel principal dans le JScrollPane
	private JLabel lblNomValeur; // Valeur du nom de l'organisme
	private JLabel lblSpecialiteValeur; // Valeur de la spécialité
	private JLabel lblAdresseValeur; // Valeur de l'adresse
	private JLabel lblCodePostalValeur; // Valeur du code postal
	private JLabel lblVilleValeur; // Valeur de la ville
	private JLabel lblMailValeur; // Valeur du mail
	private JLabel lblNumTelValeur; // Valeur du numéro de téléphone
	private JLabel lblNumSiretValeur; // Valeur du numéro SIRET
	private JTable tableInterventions; // Table des interventions réalisées

	// Contrôleurs
	private transient GestionFenOrganismeInformations gestionClic; // Contrôleur de la fenêtre
	private transient GestionTableIntervOrganisme gestionTableInterv; // Contrôleur de la table des interventions

	// Variables de gestion
	private transient Organisme selectOrganisme; // Organisme sélectionné
	private FenOrganisme fenAncetre; // Fenêtre ancêtre (table des organismes)

	/**
	 * Constructeur de la fenêtre de consultation d'un organisme. Crée une interface
	 * affichant toutes les informations de l'organisme (nom, spécialité, adresse,
	 * mail, téléphone, SIRET) et la liste de ses interventions. Permet la
	 * modification des informations et la suppression de l'organisme.
	 * 
	 * @param selectOrganisme2 l'organisme dont on souhaite consulter les
	 *                         informations
	 * @param fenAncetre       la fenêtre ancêtre (table des organismes)
	 */
	public FenOrganismeInformations(Organisme selectOrganisme2, FenOrganisme fenAncetre) {

		selectOrganisme = selectOrganisme2;
		this.fenAncetre = fenAncetre;

		setBounds(100, 100, 757, 600);

		// Panel principal
		contentPanelGeneralBorder = new JPanel();
		contentPanelGeneralBorder.setBorder(new EmptyBorder(5, 5, 5, 5));

		// === Crée le JScrollPane qui contiendra ton panel ===
		JScrollPane scrollPaneGeneral = new JScrollPane(contentPanelGeneralBorder);
		GridBagLayout gblContentPanelGeneralBorder = new GridBagLayout();
		gblContentPanelGeneralBorder.columnWidths = new int[] { 729, 0 };
		gblContentPanelGeneralBorder.rowHeights = new int[] { 33, 230, 165, 0 };
		gblContentPanelGeneralBorder.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gblContentPanelGeneralBorder.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPanelGeneralBorder.setLayout(gblContentPanelGeneralBorder);

		JPanel panelTitre = new JPanel();
		GridBagConstraints gbcPanelTitre = new GridBagConstraints();
		gbcPanelTitre.anchor = GridBagConstraints.NORTH;
		gbcPanelTitre.fill = GridBagConstraints.HORIZONTAL;
		gbcPanelTitre.insets = new Insets(0, 0, 5, 0);
		gbcPanelTitre.gridx = 0;
		gbcPanelTitre.gridy = 0;
		contentPanelGeneralBorder.add(panelTitre, gbcPanelTitre);
		panelTitre.setLayout(new GridLayout(1, 3, 0, 0));

		JLabel lblVide = new JLabel("");
		panelTitre.add(lblVide);

		JLabel lblTitre = new JLabel("<html><u>Informations de l'organisme :</u></html>");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 16));
		panelTitre.add(lblTitre);

		JPanel panelFlowQuitter = new JPanel();
		FlowLayout flowLayout2 = (FlowLayout) panelFlowQuitter.getLayout();
		flowLayout2.setAlignment(FlowLayout.RIGHT);
		panelTitre.add(panelFlowQuitter);

		JButton btnQuitter = new JButton("Quitter");

		panelFlowQuitter.add(btnQuitter);

		JPanel panelBoxLayoutGeneralY = new JPanel();
		GridBagConstraints gbcPanelBoxLayoutGeneralY = new GridBagConstraints();
		gbcPanelBoxLayoutGeneralY.anchor = GridBagConstraints.NORTH;
		gbcPanelBoxLayoutGeneralY.fill = GridBagConstraints.HORIZONTAL;
		gbcPanelBoxLayoutGeneralY.insets = new Insets(0, 0, 5, 0);
		gbcPanelBoxLayoutGeneralY.gridx = 0;
		gbcPanelBoxLayoutGeneralY.gridy = 1;
		contentPanelGeneralBorder.add(panelBoxLayoutGeneralY, gbcPanelBoxLayoutGeneralY);
		panelBoxLayoutGeneralY.setLayout(new BoxLayout(panelBoxLayoutGeneralY, BoxLayout.Y_AXIS));

		JPanel panelInformations = new JPanel();
		panelBoxLayoutGeneralY.add(panelInformations);
		panelInformations.setLayout(new BorderLayout(5, 5));
		panelInformations.setBorder(new EmptyBorder(0, 0, 12, 0)); // 5px de marge en haut et en bas

		JPanel panelGridLibellesInfos = new JPanel();
		panelInformations.add(panelGridLibellesInfos, BorderLayout.WEST);
		panelGridLibellesInfos.setLayout(new GridLayout(8, 1, 0, 8));

		JLabel lblNom = new JLabel("Nom : ");
		lblNom.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblNom);

		JLabel lblSpecialite = new JLabel("Spécialité : ");
		lblSpecialite.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblSpecialite);

		JLabel lblAdresse = new JLabel("Adresse : ");
		lblAdresse.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblAdresse);

		JLabel lblCodePostal = new JLabel("Code postal : ");
		lblCodePostal.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblCodePostal);

		JLabel lblVille = new JLabel("Ville : ");
		lblVille.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridLibellesInfos.add(lblVille);

		JLabel lblMail = new JLabel("Mail : ");
		lblMail.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridLibellesInfos.add(lblMail);

		JLabel lblNumTel = new JLabel("Numéro de téléphone : ");
		lblNumTel.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridLibellesInfos.add(lblNumTel);

		JLabel lblNumSiret = new JLabel("Numéro de SIRET : ");
		lblNumSiret.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridLibellesInfos.add(lblNumSiret);

		JPanel panelGridInfos = new JPanel();
		panelInformations.add(panelGridInfos, BorderLayout.CENTER);
		panelGridInfos.setLayout(new GridLayout(8, 1, 0, 8));

		lblNomValeur = new JLabel();
		lblNomValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblNomValeur);

		lblSpecialiteValeur = new JLabel();
		lblSpecialiteValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblSpecialiteValeur);

		lblAdresseValeur = new JLabel();
		lblAdresseValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblAdresseValeur);

		lblCodePostalValeur = new JLabel();
		lblCodePostalValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblCodePostalValeur);

		lblVilleValeur = new JLabel();
		lblVilleValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGridInfos.add(lblVilleValeur);

		lblMailValeur = new JLabel();
		lblMailValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridInfos.add(lblMailValeur);

		lblNumTelValeur = new JLabel();
		lblNumTelValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridInfos.add(lblNumTelValeur);

		lblNumSiretValeur = new JLabel();
		lblNumSiretValeur.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelGridInfos.add(lblNumSiretValeur);

		JLabel lblTitreInformations = new JLabel("<html><u>Informations concernant l'organisme :</u></html>");
		lblTitreInformations.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelInformations.add(lblTitreInformations, BorderLayout.NORTH);

		JPanel panelBoutonsInformations = new JPanel();
		FlowLayout flPanelBoutonsInformations = (FlowLayout) panelBoutonsInformations.getLayout();
		flPanelBoutonsInformations.setAlignment(FlowLayout.RIGHT);
		panelInformations.add(panelBoutonsInformations, BorderLayout.SOUTH);

		JButton btnModifierInformations = new JButton("Modifier informations");

		JButton btnSupprimer = new JButton("Supprimer l'organisme");
		panelBoutonsInformations.add(btnSupprimer);
		panelBoutonsInformations.add(btnModifierInformations);

		JPanel panelInterventions = new JPanel();
		GridBagConstraints gbcPanelInterventions = new GridBagConstraints();
		gbcPanelInterventions.anchor = GridBagConstraints.NORTH;
		gbcPanelInterventions.fill = GridBagConstraints.HORIZONTAL;
		gbcPanelInterventions.gridx = 0;
		gbcPanelInterventions.gridy = 2;
		contentPanelGeneralBorder.add(panelInterventions, gbcPanelInterventions);

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

		// Permet de limiter l'affichage de max 15 lignes pour le tableau
		int hauteurTableInterventions = tableInterventions.getRowHeight() * 8;
		panelInterventions.setLayout(new BorderLayout(0, 0));
		tableInterventions.setPreferredScrollableViewportSize(new java.awt.Dimension(
				tableInterventions.getPreferredScrollableViewportSize().width, hauteurTableInterventions));

		JScrollPane scrollPaneTableInterventions = new JScrollPane(tableInterventions);
		panelInterventions.add(scrollPaneTableInterventions);

		JLabel lblinterventionsSurLeBien = new JLabel("<html><u>Interventions réalisées par l'organisme :</u></html>");
		lblinterventionsSurLeBien.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelInterventions.add(lblinterventionsSurLeBien, BorderLayout.NORTH);

		scrollPaneGeneral.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPaneGeneral.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneGeneral.getVerticalScrollBar().setUnitIncrement(15); // Vitesse du scroll, comme la fenêtre est grande
		// j'ai augmenté un peu

		// Met le JScrollPane comme contentPane de la fenêtre
		setContentPane(scrollPaneGeneral);

		gestionTableInterv = new GestionTableIntervOrganisme(this);
		tableInterventions.getSelectionModel().addListSelectionListener(this.gestionTableInterv);

		gestionClic = new GestionFenOrganismeInformations(this);
		btnQuitter.addActionListener(gestionClic);
		btnModifierInformations.addActionListener(gestionClic);
		btnSupprimer.addActionListener(gestionClic);

		// Modification des libellés avec Organisme
		this.actualisationLibelles(selectOrganisme);
	}

	/**
	 * Retourne la fenêtre ancêtre pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return la fenêtre FenOrganisme
	 */
	public FenOrganisme getFenLocAncetre() {
		return this.fenAncetre;
	}

	/**
	 * Actualise l'affichage des libellés avec les informations de l'organisme. Met
	 * à jour tous les champs de la fenêtre avec les données de l'organisme passé en
	 * paramètre.
	 * 
	 * @param organismeModifie l'organisme contenant les nouvelles données à
	 *                         afficher
	 */
	public void actualisationLibelles(Organisme organismeModifie) {
		this.lblNomValeur.setText(organismeModifie.getNom());
		this.lblSpecialiteValeur.setText(organismeModifie.getSpecialite());
		this.lblAdresseValeur.setText(organismeModifie.getAdresse());
		this.lblCodePostalValeur.setText(organismeModifie.getCodePostal());
		this.lblVilleValeur.setText(organismeModifie.getVille());
		this.lblMailValeur.setText(organismeModifie.getMail());
		this.lblNumTelValeur.setText(organismeModifie.getNumTel());
		this.lblNumSiretValeur.setText(organismeModifie.getNumSIRET());
	}

	/**
	 * Retourne un organisme créé à partir des valeurs actuellement affichées dans
	 * la fenêtre. Utilisé pour récupérer les modifications apportées par
	 * l'utilisateur.
	 * 
	 * @return un nouvel objet Organisme contenant les valeurs affichées
	 */
	public Organisme getOrganismeActualise() {
		return new Organisme(this.lblNomValeur.getText(), this.lblSpecialiteValeur.getText(),
				this.lblAdresseValeur.getText(), this.lblCodePostalValeur.getText(), this.lblVilleValeur.getText(),
				this.lblMailValeur.getText(), this.lblNumTelValeur.getText(), this.lblNumSiretValeur.getText());
	}

	/**
	 * Retourne l'organisme sélectionné pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return l'organisme actuellement consulté
	 */
	public Organisme getSelectOrganisme() {
		return selectOrganisme;
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
	 * Modifie la table des interventions.
	 * 
	 * @param tableInterventions la nouvelle JTable des interventions
	 */
	public void setTableInterventions(JTable tableInterventions) {
		this.tableInterventions = tableInterventions;
	}

	/**
	 * Retourne le contrôleur de la table des interventions pour pouvoir l'utiliser
	 * dans les gestions.
	 * 
	 * @return le contrôleur GestionTableIntervOrganisme
	 */
	public GestionTableIntervOrganisme getGestionTableInterv() {
		return gestionTableInterv;
	}

	/**
	 * Modifie le contrôleur de la table des interventions.
	 * 
	 * @param gestionTableInterv le nouveau contrôleur GestionTableIntervOrganisme
	 */
	public void setGestionTableInterv(GestionTableIntervOrganisme gestionTableInterv) {
		this.gestionTableInterv = gestionTableInterv;
	}

}
