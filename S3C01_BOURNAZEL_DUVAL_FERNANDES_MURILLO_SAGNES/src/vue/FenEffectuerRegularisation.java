package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controleur.GestionFenEffectuerRegularisation;
import controleur.Outils;
import modele.BienLouable;
import modele.Locataire;
import modele.Louer;
import vue.consulter_informations.FenBienLouableInformations;

/**
 * Fenêtre de régularisation des charges pour un locataire. Permet d'effectuer
 * le calcul et la régularisation des charges entre les provisions versées et
 * les charges effectivement payées. Offre différentes options de règlement
 * (ajout au loyer, paiement comptant, etc.).
 */
public class FenEffectuerRegularisation extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	// Variables Swing
	private JTextField textFieldNomLocataire; // Champ texte pour le nom du locataire
	private JTextField textFieldIdLoc; // Champ texte pour l'identifiant du locataire
	private JTextField textFieldIdBienLouable; // Champ texte pour l'identifiant du bien louable
	private JTextField textFieldGarages; // Champ texte pour les garages associés
	private JTextField textFieldPeriode; // Champ texte pour la période de régularisation
	private JTextField textFieldChargesPayees; // Champ texte pour les charges effectivement payées
	private JTextField textFieldChargesVersee; // Champ texte pour les provisions versées
	private JTextField textFieldRegularisatioTotal; // Champ texte pour le total de régularisation
	private JTextField textFieldProvisionAjustee; // Champ texte pour la provision ajustée
	private JTextField txtNombreDeMois; // Champ texte pour le nombre de mois

	private JRadioButton rdbtnAjouterMoisSuivant; // Bouton radio pour ajouter au loyer des mois suivants
	private JRadioButton rdbtnLocataireAPaye; // Bouton radio pour paiement comptant par le locataire
	private JRadioButton rdbtnPaiementEmisAuLocataire; // Bouton radio pour paiement envoyé au locataire

	private JLabel lblInfoMois; // Label d'information pour les mois

	// Contrôleurs
	private transient GestionFenEffectuerRegularisation gestionClic; // Contrôleur de gestion des événements
	private FenBienLouableInformations fenAncetre; // Fenêtre parente

	/**
	 * Constructeur de la fenêtre de régularisation. Initialise l'interface
	 * graphique avec tous les composants nécessaires pour effectuer une
	 * régularisation de charges.
	 *
	 * @param fenAncetre fenêtre parente
	 * @param bien       bien louable concerné par la régularisation
	 * @param louer      location associée
	 * @param loc        locataire concerné
	 */
	public FenEffectuerRegularisation(FenBienLouableInformations fenAncetre, BienLouable bien, Louer louer,
			Locataire loc) {

		this.fenAncetre = fenAncetre;

		// ------------------------------- Initialisation
		// -------------------------------
		this.gestionClic = new GestionFenEffectuerRegularisation(this, bien, louer);
		setBounds(100, 100, 708, 672);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// ------------------------------- Panel du titre
		// -------------------------------
		JPanel panelGridTitre = new JPanel();
		contentPane.add(panelGridTitre, BorderLayout.NORTH);
		panelGridTitre.setLayout(new GridLayout(0, 3, 0, 0));

		JPanel panelVide = new JPanel();
		panelGridTitre.add(panelVide);

		JLabel lblTitre = new JLabel("Régularisation des charges");
		lblTitre.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 16));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		panelGridTitre.add(lblTitre);

		JPanel panelBoutonQuitter = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBoutonQuitter.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelGridTitre.add(panelBoutonQuitter);

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setHorizontalAlignment(SwingConstants.RIGHT);
		btnQuitter.addActionListener(this.gestionClic);

		panelBoutonQuitter.add(btnQuitter);

		// ------------------------------- Panel central -------------------------------
		JPanel panelCenter = new JPanel();
		contentPane.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));

		// Labels de gauche
		JPanel panelCenterGauche = new JPanel();
		panelCenter.add(panelCenterGauche, BorderLayout.WEST);
		panelCenterGauche.setLayout(new GridLayout(9, 1, 0, 10));

		JLabel lblNomLocataire = new JLabel("Nom Locataire : ");
		lblNomLocataire.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblNomLocataire);

		JLabel lblIdLoc = new JLabel("ID Locataire : ");
		lblIdLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblIdLoc);

		JLabel lblIdBienLouable = new JLabel("ID Bien Louable : ");
		lblIdBienLouable.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblIdBienLouable);

		JLabel lblNewLabel = new JLabel("Garages associés au logement :");
		lblNewLabel.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblNewLabel);

		JLabel lblPeriode = new JLabel("Période : ");
		lblPeriode.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblPeriode);

		JLabel lblChargesPayees = new JLabel("Total Charges Effectivement Payées : ");
		lblChargesPayees.setToolTipText("");
		lblChargesPayees.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblChargesPayees);

		JLabel lblTotalProv = new JLabel("Total Provisions Pour Charges Versée : ");
		lblTotalProv.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblTotalProv);

		JLabel lblEspace = new JLabel("");
		panelCenterGauche.add(lblEspace);

		JLabel lblTotaReg = new JLabel("Total Régularisation : ");
		lblTotaReg.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 15));
		panelCenterGauche.add(lblTotaReg);

		// ----------------------------- Champs de texte -----------------------------
		JPanel panelCenterCenter = new JPanel();
		panelCenterCenter.setEnabled(false);
		panelCenter.add(panelCenterCenter, BorderLayout.CENTER);
		panelCenterCenter.setLayout(new GridLayout(9, 1, 0, 10));

		textFieldNomLocataire = new JTextField(loc.getNom());
		textFieldNomLocataire.setEditable(false);
		panelCenterCenter.add(textFieldNomLocataire);
		textFieldNomLocataire.setColumns(10);

		textFieldIdLoc = new JTextField(loc.getIdLocataire());
		textFieldIdLoc.setEditable(false);
		panelCenterCenter.add(textFieldIdLoc);
		textFieldIdLoc.setColumns(10);

		textFieldIdBienLouable = new JTextField(bien.getIdBien());
		textFieldIdBienLouable.setEditable(false);
		panelCenterCenter.add(textFieldIdBienLouable);
		textFieldIdBienLouable.setColumns(10);

		textFieldGarages = new JTextField();
		textFieldGarages.setEditable(false);
		panelCenterCenter.add(textFieldGarages);
		textFieldGarages.setColumns(10);

		textFieldPeriode = new JTextField();
		textFieldPeriode.setEditable(false);
		panelCenterCenter.add(textFieldPeriode);
		textFieldPeriode.setColumns(10);

		textFieldChargesPayees = new JTextField();
		textFieldChargesPayees.setEditable(false);
		panelCenterCenter.add(textFieldChargesPayees);
		textFieldChargesPayees.setColumns(10);

		textFieldChargesVersee = new JTextField();
		textFieldChargesVersee.setEditable(false);
		panelCenterCenter.add(textFieldChargesVersee);
		textFieldChargesVersee.setColumns(10);

		JLabel lblEspace1 = new JLabel("");
		panelCenterCenter.add(lblEspace1);

		textFieldRegularisatioTotal = new JTextField();
		textFieldRegularisatioTotal.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 15));
		textFieldRegularisatioTotal.setEditable(false);
		panelCenterCenter.add(textFieldRegularisatioTotal);
		textFieldRegularisatioTotal.setColumns(10);

		// ------------------------------- Panel sud -------------------------------
		JPanel panelSouthConfirmer = new JPanel();
		contentPane.add(panelSouthConfirmer, BorderLayout.SOUTH);
		panelSouthConfirmer.setLayout(new GridLayout(6, 1, 0, 0));

		JLabel lblVide = new JLabel("");
		panelSouthConfirmer.add(lblVide);

		// Options de règlement
		JPanel panelMoisSuivant = new JPanel();
		FlowLayout flPanelMoisSuivant = (FlowLayout) panelMoisSuivant.getLayout();
		flPanelMoisSuivant.setAlignment(FlowLayout.LEFT);
		panelSouthConfirmer.add(panelMoisSuivant);

		rdbtnAjouterMoisSuivant = new JRadioButton("Ajouter au loyer des mois suivant");
		// J'utilise un stream pour eviter de faire une classe gestion vu que c que de
		// l'affichage
		rdbtnAjouterMoisSuivant
				.addActionListener(e -> txtNombreDeMois.setEnabled(rdbtnAjouterMoisSuivant.isSelected()));
		panelMoisSuivant.add(rdbtnAjouterMoisSuivant);

		txtNombreDeMois = new JTextField();
		txtNombreDeMois.setText("Nombre de mois");
		txtNombreDeMois.setEnabled(false);
		panelMoisSuivant.add(txtNombreDeMois);
		txtNombreDeMois.setColumns(10);

		rdbtnLocataireAPaye = new JRadioButton("Le locataire paie / a payé au comptant");
		rdbtnLocataireAPaye.addActionListener(e -> txtNombreDeMois.setEnabled(rdbtnAjouterMoisSuivant.isSelected()));
		panelSouthConfirmer.add(rdbtnLocataireAPaye);

		rdbtnPaiementEmisAuLocataire = new JRadioButton("Paiement envoyé au locataire");
		rdbtnPaiementEmisAuLocataire
				.addActionListener(e -> txtNombreDeMois.setEnabled(rdbtnAjouterMoisSuivant.isSelected()));
		panelSouthConfirmer.add(rdbtnPaiementEmisAuLocataire);

		// Groupe de boutons radio
		ButtonGroup groupe = new ButtonGroup();
		groupe.add(rdbtnAjouterMoisSuivant);

		lblInfoMois = new JLabel("La somme sera uniformément répartie sur les loyers des mois prochains");
		lblInfoMois.setFont(new Font(Outils.POLICE_TAHOMA, Font.ITALIC, 10));
		panelMoisSuivant.add(lblInfoMois);
		groupe.add(rdbtnLocataireAPaye);
		groupe.add(rdbtnPaiementEmisAuLocataire);

		// ----------------------------- Ajustement provisions
		// -----------------------------
		JPanel panelProvision = new JPanel();
		FlowLayout flowLayoutProvision = (FlowLayout) panelProvision.getLayout();
		flowLayoutProvision.setAlignment(FlowLayout.LEFT);
		panelSouthConfirmer.add(panelProvision);

		JLabel lblAjusterProvision = new JLabel("Ajuster la provision pour charges du bien :");
		panelProvision.add(lblAjusterProvision);

		textFieldProvisionAjustee = new JTextField(String.valueOf(bien.getProvisionPourCharges()));
		panelProvision.add(textFieldProvisionAjustee);
		textFieldProvisionAjustee.setColumns(10);

		JLabel lblInfoCharges = new JLabel("Valeur par défaut calculée à partir du total des charges payées");
		lblInfoCharges.setFont(new Font(Outils.POLICE_TAHOMA, Font.ITALIC, 10));
		panelProvision.add(lblInfoCharges);

		// ----------------------------- Boutons -----------------------------
		JPanel panelConfirmer = new JPanel();
		FlowLayout flPanelConfirmer = (FlowLayout) panelConfirmer.getLayout();
		flPanelConfirmer.setAlignment(FlowLayout.LEFT);
		panelSouthConfirmer.add(panelConfirmer);

		JButton btnConfirmer = new JButton("Confirmer");
		btnConfirmer.addActionListener(gestionClic);

		JButton btnDetailCharges = new JButton("Détail des Charges");
		btnDetailCharges.addActionListener(gestionClic);

		panelConfirmer.add(btnDetailCharges);
		panelConfirmer.add(btnConfirmer);

		// ----------------------------- Initialisation -----------------------------
		this.gestionClic.setGaragesAssocies();
		this.gestionClic.setPeriode();
		this.gestionClic.setTotaux();

	}

	// GETTERS / SETTERS ----------------------------------------------------------

	/**
	 * Récupère le champ texte qui contient le nom du locataire.
	 *
	 * @return champ texte du nom du locataire
	 */
	public JTextField getTextFieldNomLocataire() {
		return textFieldNomLocataire;
	}

	/**
	 * Définit le champ texte associé au nom du locataire.
	 *
	 * @param textFieldNomLocataire nouveau champ texte pour le nom du locataire
	 */
	public void setTextFieldNomLocataire(JTextField textFieldNomLocataire) {
		this.textFieldNomLocataire = textFieldNomLocataire;
	}

	/**
	 * Récupère le champ texte de l'identifiant du bien louable.
	 *
	 * @return champ texte de l'identifiant du bien
	 */
	public JTextField getTextFieldIdBienLouable() {
		return textFieldIdBienLouable;
	}

	/**
	 * Définit le champ texte de l'identifiant du bien louable.
	 *
	 * @param textFieldIdBienLouable nouveau champ texte de l'identifiant du bien
	 */
	public void setTextFieldIdBienLouable(JTextField textFieldIdBienLouable) {
		this.textFieldIdBienLouable = textFieldIdBienLouable;
	}

	/**
	 * Récupère le champ texte de l'identifiant du locataire.
	 *
	 * @return champ texte de l'identifiant du locataire
	 */
	public JTextField getTextFieldIdLoc() {
		return textFieldIdLoc;
	}

	/**
	 * Définit le champ texte de l'identifiant du locataire.
	 *
	 * @param textFieldIdLoc nouveau champ texte de l'identifiant du locataire
	 */
	public void setTextFieldIdLoc(JTextField textFieldIdLoc) {
		this.textFieldIdLoc = textFieldIdLoc;
	}

	/**
	 * Récupère le champ texte indiquant la période de régularisation.
	 *
	 * @return champ texte de la période
	 */
	public JTextField getTextFieldPeriode() {
		return textFieldPeriode;
	}

	/**
	 * Définit le champ texte de la période de régularisation.
	 *
	 * @param textFieldPeriode nouveau champ texte de la période
	 */
	public void setTextFieldPeriode(JTextField textFieldPeriode) {
		this.textFieldPeriode = textFieldPeriode;
	}

	/**
	 * Récupère le champ texte du total des charges payées.
	 *
	 * @return champ texte des charges payées
	 */
	public JTextField getTextFieldChargesPayees() {
		return textFieldChargesPayees;
	}

	/**
	 * Définit le champ texte du total des charges payées.
	 *
	 * @param textFieldChargesPayees nouveau champ texte des charges payées
	 */
	public void setTextFieldChargesPayees(JTextField textFieldChargesPayees) {
		this.textFieldChargesPayees = textFieldChargesPayees;
	}

	/**
	 * Récupère le champ texte des provisions versées.
	 *
	 * @return champ texte des charges versées
	 */
	public JTextField getTextFieldChargesVersee() {
		return textFieldChargesVersee;
	}

	/**
	 * Définit le champ texte des provisions versées.
	 *
	 * @param textFieldChargesVersee nouveau champ texte des charges versées
	 */
	public void setTextFieldChargesVersee(JTextField textFieldChargesVersee) {
		this.textFieldChargesVersee = textFieldChargesVersee;
	}

	/**
	 * Récupère le champ texte du total de régularisation.
	 *
	 * @return champ texte du total calculé
	 */
	public JTextField getTextFieldRegularisatioTotal() {
		return textFieldRegularisatioTotal;
	}

	/**
	 * Définit le champ texte du total de régularisation.
	 *
	 * @param textFieldRegularisatioToral nouveau champ texte du total calculé
	 */
	public void setTextFieldRegularisatioTotal(JTextField textFieldRegularisatioToral) {
		this.textFieldRegularisatioTotal = textFieldRegularisatioToral;
	}

	/**
	 * Récupère le champ texte de la provision ajustée.
	 *
	 * @return champ texte de la provision ajustée
	 */
	public JTextField getTextFieldProvisionAjustee() {
		return textFieldProvisionAjustee;
	}

	/**
	 * Définit le champ texte de la provision ajustée.
	 *
	 * @param textFieldProvisionAjustee nouveau champ texte de la provision
	 */
	public void setTextFieldProvisionAjustee(JTextField textFieldProvisionAjustee) {
		this.textFieldProvisionAjustee = textFieldProvisionAjustee;
	}

	/**
	 * Récupère le bouton radio indiquant que le paiement est émis au locataire.
	 *
	 * @return bouton radio "paiement envoyé"
	 */
	public JRadioButton getRdbtnPaiementEmisAuLocataire() {
		return rdbtnPaiementEmisAuLocataire;
	}

	/**
	 * Définit le bouton radio indiquant que le paiement est émis au locataire.
	 *
	 * @param rdbtnPaiementEmisAuLocataire bouton radio "paiement envoyé"
	 */
	public void setRdbtnPaiementEmisAuLocataire(JRadioButton rdbtnPaiementEmisAuLocataire) {
		this.rdbtnPaiementEmisAuLocataire = rdbtnPaiementEmisAuLocataire;
	}

	/**
	 * Récupère le bouton radio permettant d'ajouter le solde aux mois suivants.
	 *
	 * @return bouton radio "ajouter aux mois suivants"
	 */
	public JRadioButton getRdbtnAjouterMoisSuivant() {
		return rdbtnAjouterMoisSuivant;
	}

	/**
	 * Définit le bouton radio permettant d'ajouter le solde aux mois suivants.
	 *
	 * @param rdbtnAjouterMoisSuivant bouton radio "ajouter aux mois suivants"
	 */
	public void setRdbtnAjouterMoisSuivant(JRadioButton rdbtnAjouterMoisSuivant) {
		this.rdbtnAjouterMoisSuivant = rdbtnAjouterMoisSuivant;
	}

	/**
	 * Récupère le bouton radio indiquant un paiement comptant du locataire.
	 *
	 * @return bouton radio "locataire a payé"
	 */
	public JRadioButton getRdbtnLocataireAPaye() {
		return rdbtnLocataireAPaye;
	}

	/**
	 * Définit le bouton radio indiquant un paiement comptant du locataire.
	 *
	 * @param rdbtnLocataireAPaye bouton radio "locataire a payé"
	 */
	public void setRdbtnLocataireAPaye(JRadioButton rdbtnLocataireAPaye) {
		this.rdbtnLocataireAPaye = rdbtnLocataireAPaye;
	}

	/**
	 * Récupère la fenêtre parente.
	 *
	 * @return fenêtre ancêtre
	 */
	public FenBienLouableInformations getFenAncetre() {
		return fenAncetre;
	}

	/**
	 * Définit la fenêtre parente.
	 *
	 * @param fenAncetre nouvelle fenêtre ancêtre
	 */
	public void setFenAncetre(FenBienLouableInformations fenAncetre) {
		this.fenAncetre = fenAncetre;
	}

	/**
	 * Récupère le champ texte contenant le nombre de mois saisi.
	 *
	 * @return champ texte du nombre de mois
	 */
	public JTextField getTxtNombreDeMois() {
		return txtNombreDeMois;
	}

	/**
	 * Définit le champ texte contenant le nombre de mois saisi.
	 *
	 * @param txtNombreDeMois nouveau champ texte du nombre de mois
	 */
	public void setTxtNombreDeMois(JTextField txtNombreDeMois) {
		this.txtNombreDeMois = txtNombreDeMois;
	}

	/**
	 * Récupère le label d'information relatif à la répartition des mois.
	 *
	 * @return label d'information des mois
	 */
	public JLabel getLblInfoMois() {
		return lblInfoMois;
	}

	/**
	 * Définit le label d'information relatif à la répartition des mois.
	 *
	 * @param lblInfoMois nouveau label d'information
	 */
	public void setLblInfoMois(JLabel lblInfoMois) {
		this.lblInfoMois = lblInfoMois;
	}

	/**
	 * Récupère le champ texte listant les garages associés au bien.
	 *
	 * @return champ texte des garages
	 */
	public JTextField getTextFieldGarages() {
		return textFieldGarages;
	}

	/**
	 * Définit le champ texte listant les garages associés au bien.
	 *
	 * @param textFieldGarages nouveau champ texte des garages
	 */
	public void setTextFieldGarages(JTextField textFieldGarages) {
		this.textFieldGarages = textFieldGarages;
	}

	/**
	 * Récupère le contrôleur chargé des évènements de la fenêtre.
	 *
	 * @return contrôleur d'interaction
	 */
	public GestionFenEffectuerRegularisation getGestionClic() {
		return gestionClic;
	}

}
