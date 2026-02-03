package vue;

import java.awt.BorderLayout;
import java.awt.Color;
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

import controleur.GestionFenFinDeBail;
import controleur.Outils;
import modele.Locataire;
import modele.Louer;
import vue.consulter_informations.FenBienLouableInformations;

/**
 * Fenêtre de gestion de la fin de bail (solde de tout compte). Permet de
 * calculer et d'enregistrer les informations liées à la fin d'un bail, incluant
 * les charges, les loyers impayés, les ajustements, le dépôt de garantie et les
 * éventuelles dégradations.
 */
public class FenFinDeBail extends JFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel contentPane; // Panel principal de la fenêtre
	private JTextField textFieldNomLocataire; // Champ du nom du locataire
	private JTextField textFieldIdBienLouable; // Champ de l'identifiant du bien louable
	private JTextField textFieldIdLoc; // Champ de l'identifiant du locataire
	private JTextField textFieldPeriode; // Champ de la période des charges
	private JTextField textFieldChargesPayees; // Champ du total des charges payées
	private JTextField textFieldChargesVersee; // Champ du total des provisions versées
	private JTextField textFieldTotalDu; // Champ du total dû (résultat du calcul)
	private JTextField textFieldDegradations; // Champ du montant des dégradations
	private JTextField textFieldLoyersImpayes; // Champ du total des loyers impayés
	private JTextField textFieldAjustementsRestants; // Champ des ajustements de loyer restants
	private JTextField textFieldGarages; // Champ des garages associés au bien
	private JTextField textFieldDepotGarantie; // Champ du dépôt de garantie
	private JButton btnCalculer; // Bouton pour calculer le total dû
	private JRadioButton rdbtnPaiementRecu; // Bouton radio pour indiquer un paiement reçu
	private JRadioButton rdbtnPaiementEmis; // Bouton radio pour indiquer un paiement émis
	private JRadioButton rdbtnPaiementPasFait; // Bouton radio pour indiquer qu'aucun paiement n'a été fait

	// Contrôleurs
	private transient GestionFenFinDeBail gestionClic; // Contrôleur des événements
	private FenBienLouableInformations fenAncetre; // Fenêtre parente

	/**
	 * Constructeur de la fenêtre de fin de bail. Initialise l'interface graphique
	 * avec tous les champs nécessaires pour effectuer le solde de tout compte lors
	 * de la fin d'un bail.
	 *
	 * @param fenAncetre la fenêtre parente
	 * @param location   la location concernée
	 * @param locataire  le locataire concerné
	 */
	public FenFinDeBail(FenBienLouableInformations fenAncetre, Louer location, Locataire locataire) {

		this.fenAncetre = fenAncetre;

		setBounds(100, 100, 1127, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		this.gestionClic = new GestionFenFinDeBail(this, location, locataire);

		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new BorderLayout());
		contentPane.add(panelNorth, BorderLayout.NORTH);

		// -------- Panel du titre --------
		JPanel panelGridTitre = new JPanel();
		panelGridTitre.setLayout(new GridLayout(0, 3, 0, 0));
		panelNorth.add(panelGridTitre, BorderLayout.NORTH);

		JPanel panelVide = new JPanel();
		panelGridTitre.add(panelVide);

		JLabel lblTitre = new JLabel("Fin de bail (solde de tout compte)");
		lblTitre.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 16));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		panelGridTitre.add(lblTitre);

		JPanel panelBoutonQuitter = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBoutonQuitter.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelGridTitre.add(panelBoutonQuitter);

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setHorizontalAlignment(SwingConstants.RIGHT);
		btnQuitter.addActionListener(gestionClic);
		panelBoutonQuitter.add(btnQuitter);

		JPanel panelInfo = new JPanel();
		panelInfo.setLayout(new GridLayout(5, 1, 0, 0));

		JLabel lblInfoRegularisation = new JLabel("Les charges de cette année sont incluses dans la régularisation.");
		lblInfoRegularisation.setFont(new Font(Outils.POLICE_TAHOMA, Font.ITALIC, 12));
		lblInfoRegularisation.setForeground(new Color(0, 0, 0));
		lblInfoRegularisation.setHorizontalAlignment(SwingConstants.CENTER);

		panelInfo.add(lblInfoRegularisation);

		JLabel lblInfoLoyers = new JLabel(
				"Le montant de loyers impayés s'appuie sur le loyer et la provision en vigueur pour ce bien.");
		lblInfoLoyers.setFont(new Font(Outils.POLICE_TAHOMA, Font.ITALIC, 12));
		lblInfoLoyers.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfo.add(lblInfoLoyers);
		panelNorth.add(panelInfo, BorderLayout.CENTER);

		JLabel lblPensezA = new JLabel("<html><u>Avant de faire le solde, pensez à :</u></html>");
		lblPensezA.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelInfo.add(lblPensezA);

		JLabel lblInfoCompteurs = new JLabel("- relever les compteurs du bien et des garages");
		lblInfoCompteurs.setHorizontalAlignment(SwingConstants.LEFT);
		lblInfoCompteurs.setForeground(new Color(200, 0, 0));
		lblInfoCompteurs.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelInfo.add(lblInfoCompteurs);

		JLabel lblNewLabel = new JLabel("- effectuer la régularisation de l'année précédente");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setForeground(new Color(200, 0, 0));
		lblNewLabel.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 12));
		panelInfo.add(lblNewLabel);

		JPanel panelCenter = new JPanel();
		contentPane.add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(0, 0));

		JPanel panelCenterGauche = new JPanel();
		panelCenter.add(panelCenterGauche, BorderLayout.WEST);
		panelCenterGauche.setLayout(new GridLayout(14, 1, 0, 10));

		JLabel lblNomLocataire = new JLabel("Nom Locataire : ");
		lblNomLocataire.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblNomLocataire);

		JLabel lblIdLoc = new JLabel("ID Locataire : ");
		lblIdLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblIdLoc);

		JLabel lblIdBienLouable = new JLabel("ID Bien Louable : ");
		lblIdBienLouable.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblIdBienLouable);

		JLabel lblGaragesAssocies = new JLabel("Garages associés : ");
		lblGaragesAssocies.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblGaragesAssocies);

		JLabel lblPeriode = new JLabel("Période charges : ");
		lblPeriode.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblPeriode);

		JLabel lblChargesPayees = new JLabel("Total Charges Effectivement Payées : ");
		lblChargesPayees.setToolTipText("");
		lblChargesPayees.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblChargesPayees);

		JLabel lblTotalProv = new JLabel("Total Provisions Pour Charges Versée : ");
		lblTotalProv.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblTotalProv);

		JLabel lblLoyersImpayes = new JLabel("Total loyers impayés : ");
		lblLoyersImpayes.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblLoyersImpayes);

		JLabel lblAjustementsRestants = new JLabel("Ajustements de loyer restants :");
		lblAjustementsRestants.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblAjustementsRestants);

		JLabel lblDepotGarantie = new JLabel("Dépôt de garantie :");
		lblDepotGarantie.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblDepotGarantie);

		JLabel lblMontantDegradations = new JLabel("Montant des dégradations : ");
		lblMontantDegradations.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 12));
		panelCenterGauche.add(lblMontantDegradations);

		JPanel panelBoutonCalculer = new JPanel();
		FlowLayout flowLayoutCalculer = (FlowLayout) panelBoutonCalculer.getLayout();
		flowLayoutCalculer.setAlignment(FlowLayout.LEFT);
		panelCenterGauche.add(panelBoutonCalculer);

		btnCalculer = new JButton("Calculer total dû");
		btnCalculer.addActionListener(gestionClic);
		panelBoutonCalculer.add(btnCalculer);

		JLabel lblVide1 = new JLabel("");
		panelCenterGauche.add(lblVide1);

		JLabel lblTotaReg = new JLabel("Total dû : ");
		lblTotaReg.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 15));
		panelCenterGauche.add(lblTotaReg);

		JPanel panelCenterCenter = new JPanel();
		panelCenterCenter.setEnabled(false);
		panelCenter.add(panelCenterCenter, BorderLayout.CENTER);
		panelCenterCenter.setLayout(new GridLayout(14, 1, 0, 10));

		textFieldNomLocataire = new JTextField();
		textFieldNomLocataire.setText("[nom loc]");
		textFieldNomLocataire.setEditable(false);
		panelCenterCenter.add(textFieldNomLocataire);
		textFieldNomLocataire.setColumns(10);

		textFieldIdLoc = new JTextField();
		textFieldIdLoc.setText("[id loc]");
		textFieldIdLoc.setEditable(false);
		panelCenterCenter.add(textFieldIdLoc);
		textFieldIdLoc.setColumns(10);

		textFieldIdBienLouable = new JTextField();
		textFieldIdBienLouable.setText("[id bien]");
		textFieldIdBienLouable.setEditable(false);
		panelCenterCenter.add(textFieldIdBienLouable);
		textFieldIdBienLouable.setColumns(10);

		textFieldGarages = new JTextField();
		textFieldGarages.setEditable(false);
		panelCenterCenter.add(textFieldGarages);
		textFieldGarages.setColumns(10);

		textFieldPeriode = new JTextField();
		textFieldPeriode.setText("[période]");
		textFieldPeriode.setEditable(false);
		panelCenterCenter.add(textFieldPeriode);
		textFieldPeriode.setColumns(10);

		textFieldChargesPayees = new JTextField();
		textFieldChargesPayees.setText("[total]");
		textFieldChargesPayees.setEditable(false);
		panelCenterCenter.add(textFieldChargesPayees);
		textFieldChargesPayees.setColumns(10);

		textFieldChargesVersee = new JTextField();
		textFieldChargesVersee.setText("[total]");
		textFieldChargesVersee.setEditable(false);
		panelCenterCenter.add(textFieldChargesVersee);
		textFieldChargesVersee.setColumns(10);

		textFieldLoyersImpayes = new JTextField();
		textFieldLoyersImpayes.setEditable(false);
		panelCenterCenter.add(textFieldLoyersImpayes);
		textFieldLoyersImpayes.setColumns(10);

		textFieldAjustementsRestants = new JTextField();
		textFieldAjustementsRestants.setEditable(false);
		panelCenterCenter.add(textFieldAjustementsRestants);
		textFieldAjustementsRestants.setColumns(10);

		textFieldDepotGarantie = new JTextField();
		textFieldDepotGarantie.setEditable(false);
		panelCenterCenter.add(textFieldDepotGarantie);
		textFieldDepotGarantie.setColumns(10);

		textFieldDegradations = new JTextField();
		panelCenterCenter.add(textFieldDegradations);
		textFieldDegradations.setColumns(10);

		JLabel lblEspace1 = new JLabel("");
		panelCenterCenter.add(lblEspace1);

		JLabel lblEspace2 = new JLabel("");
		panelCenterCenter.add(lblEspace2);

		textFieldTotalDu = new JTextField();
		textFieldTotalDu.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 15));
		textFieldTotalDu.setEditable(false);
		panelCenterCenter.add(textFieldTotalDu);
		textFieldTotalDu.setColumns(10);

		JPanel panelSouthBouton = new JPanel();
		contentPane.add(panelSouthBouton, BorderLayout.SOUTH);

		panelSouthBouton.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panelBtnPaiement = new JPanel();
		FlowLayout flowLayoutPaiement = (FlowLayout) panelBtnPaiement.getLayout();
		flowLayoutPaiement.setAlignment(FlowLayout.LEFT);
		panelSouthBouton.add(panelBtnPaiement);

		rdbtnPaiementRecu = new JRadioButton("Paiement du locataire reçu");
		panelBtnPaiement.add(rdbtnPaiementRecu);

		rdbtnPaiementEmis = new JRadioButton("Paiement émis au locataire");
		panelBtnPaiement.add(rdbtnPaiementEmis);

		rdbtnPaiementPasFait = new JRadioButton(
				"Aucun paiement n'a été émis / reçu. J'ai pris note des informations ci-dessus.");
		panelBtnPaiement.add(rdbtnPaiementPasFait);

		rdbtnPaiementEmis.setVisible(false);
		rdbtnPaiementRecu.setVisible(false);
		rdbtnPaiementPasFait.setVisible(false);

		ButtonGroup groupe = new ButtonGroup();
		groupe.add(rdbtnPaiementRecu);
		groupe.add(rdbtnPaiementEmis);
		groupe.add(rdbtnPaiementPasFait);

		JPanel panelBoutonFin = new JPanel();
		panelSouthBouton.add(panelBoutonFin);

		JButton btnFinBail = new JButton("Ajouter l'état des lieux de sortie et mettre fin au bail");
		panelBoutonFin.add(btnFinBail);
		btnFinBail.addActionListener(gestionClic);

		this.gestionClic.remplirChamps();

	}

	// GETTERS ---------------------------------------------------------------------

	/**
	 * Récupère le champ de texte du nom du locataire.
	 *
	 * @return le champ de texte du nom du locataire
	 */
	public JTextField getTextFieldNomLocataire() {
		return textFieldNomLocataire;
	}

	/**
	 * Récupère le champ de texte de l'identifiant du bien louable.
	 *
	 * @return le champ de texte de l'identifiant du bien louable
	 */
	public JTextField getTextFieldIdBienLouable() {
		return textFieldIdBienLouable;
	}

	/**
	 * Récupère le champ de texte de l'identifiant du locataire.
	 *
	 * @return le champ de texte de l'identifiant du locataire
	 */
	public JTextField getTextFieldIdLoc() {
		return textFieldIdLoc;
	}

	/**
	 * Récupère le champ de texte de la période des charges.
	 *
	 * @return le champ de texte de la période
	 */
	public JTextField getTextFieldPeriode() {
		return textFieldPeriode;
	}

	/**
	 * Récupère le champ de texte du total des charges payées.
	 *
	 * @return le champ de texte des charges payées
	 */
	public JTextField getTextFieldChargesPayees() {
		return textFieldChargesPayees;
	}

	/**
	 * Récupère le champ de texte du total des provisions versées.
	 *
	 * @return le champ de texte des provisions versées
	 */
	public JTextField getTextFieldChargesVersee() {
		return textFieldChargesVersee;
	}

	/**
	 * Récupère le champ de texte du total dû.
	 *
	 * @return le champ de texte du total dû
	 */
	public JTextField getTextFieldTotalDu() {
		return textFieldTotalDu;
	}

	/**
	 * Récupère le champ de texte du montant des dégradations.
	 *
	 * @return le champ de texte des dégradations
	 */
	public JTextField getTextFieldDegradations() {
		return textFieldDegradations;
	}

	/**
	 * Récupère le champ de texte du total des loyers impayés.
	 *
	 * @return le champ de texte des loyers impayés
	 */
	public JTextField getTextFieldLoyersImpayes() {
		return textFieldLoyersImpayes;
	}

	/**
	 * Récupère le champ de texte des ajustements restants.
	 *
	 * @return le champ de texte des ajustements restants
	 */
	public JTextField getTextFieldAjustementsRestants() {
		return textFieldAjustementsRestants;
	}

	/**
	 * Récupère le champ de texte des garages associés.
	 *
	 * @return le champ de texte des garages
	 */
	public JTextField getTextFieldGarages() {
		return textFieldGarages;
	}

	/**
	 * Récupère le champ de texte du dépôt de garantie.
	 *
	 * @return le champ de texte du dépôt de garantie
	 */
	public JTextField getTextFieldDepotGarantie() {
		return textFieldDepotGarantie;
	}

	/**
	 * Récupère le bouton de calcul du total dû.
	 *
	 * @return le bouton de calcul
	 */
	public JButton getBtnCalculer() {
		return btnCalculer;
	}

	/**
	 * Récupère le bouton radio indiquant un paiement reçu.
	 *
	 * @return le bouton radio paiement reçu
	 */
	public JRadioButton getRdbtnPaiementRecu() {
		return rdbtnPaiementRecu;
	}

	/**
	 * Récupère le bouton radio indiquant un paiement émis.
	 *
	 * @return le bouton radio paiement émis
	 */
	public JRadioButton getRdbtnPaiementEmis() {
		return rdbtnPaiementEmis;
	}

	/**
	 * Récupère le bouton radio indiquant qu'aucun paiement n'a été fait.
	 *
	 * @return le bouton radio aucun paiement
	 */
	public JRadioButton getRdbtnPaiementPasFait() {
		return rdbtnPaiementPasFait;
	}

	/**
	 * Récupère la fenêtre parente.
	 *
	 * @return la fenêtre parente
	 */
	public FenBienLouableInformations getFenAncetre() {
		return fenAncetre;
	}

	// SETTERS ---------------------------------------------------------------------

	/**
	 * Définit le champ de texte du nom du locataire.
	 *
	 * @param textFieldNomLocataire le champ de texte du nom du locataire
	 */
	public void setTextFieldNomLocataire(JTextField textFieldNomLocataire) {
		this.textFieldNomLocataire = textFieldNomLocataire;
	}

	/**
	 * Définit le champ de texte de l'identifiant du bien louable.
	 *
	 * @param textFieldIdBienLouable le champ de texte de l'identifiant du bien
	 *                               louable
	 */
	public void setTextFieldIdBienLouable(JTextField textFieldIdBienLouable) {
		this.textFieldIdBienLouable = textFieldIdBienLouable;
	}

	/**
	 * Définit le champ de texte de l'identifiant du locataire.
	 *
	 * @param textFieldIdLoc le champ de texte de l'identifiant du locataire
	 */
	public void setTextFieldIdLoc(JTextField textFieldIdLoc) {
		this.textFieldIdLoc = textFieldIdLoc;
	}

	/**
	 * Définit le champ de texte de la période des charges.
	 *
	 * @param textFieldPeriode le champ de texte de la période
	 */
	public void setTextFieldPeriode(JTextField textFieldPeriode) {
		this.textFieldPeriode = textFieldPeriode;
	}

	/**
	 * Définit le champ de texte du total des charges payées.
	 *
	 * @param textFieldChargesPayees le champ de texte des charges payées
	 */
	public void setTextFieldChargesPayees(JTextField textFieldChargesPayees) {
		this.textFieldChargesPayees = textFieldChargesPayees;
	}

	/**
	 * Définit le champ de texte du total des provisions versées.
	 *
	 * @param textFieldChargesVersee le champ de texte des provisions versées
	 */
	public void setTextFieldChargesVersee(JTextField textFieldChargesVersee) {
		this.textFieldChargesVersee = textFieldChargesVersee;
	}

	/**
	 * Définit le champ de texte du total dû.
	 *
	 * @param textFieldTotalDu le champ de texte du total dû
	 */
	public void setTextFieldTotalDu(JTextField textFieldTotalDu) {
		this.textFieldTotalDu = textFieldTotalDu;
	}

	/**
	 * Définit le champ de texte du montant des dégradations.
	 *
	 * @param textFieldDegradations le champ de texte des dégradations
	 */
	public void setTextFieldDegradations(JTextField textFieldDegradations) {
		this.textFieldDegradations = textFieldDegradations;
	}

	/**
	 * Définit le champ de texte du total des loyers impayés.
	 *
	 * @param textFieldLoyersImpayes le champ de texte des loyers impayés
	 */
	public void setTextFieldLoyersImpayes(JTextField textFieldLoyersImpayes) {
		this.textFieldLoyersImpayes = textFieldLoyersImpayes;
	}

	/**
	 * Définit le champ de texte des ajustements restants.
	 *
	 * @param textFieldAjustementsRestants le champ de texte des ajustements
	 *                                     restants
	 */
	public void setTextFieldAjustementsRestants(JTextField textFieldAjustementsRestants) {
		this.textFieldAjustementsRestants = textFieldAjustementsRestants;
	}

	/**
	 * Définit le bouton de calcul du total dû.
	 *
	 * @param btnCalculer le bouton de calcul
	 */
	public void setBtnCalculer(JButton btnCalculer) {
		this.btnCalculer = btnCalculer;
	}

	/**
	 * Définit le bouton radio indiquant un paiement reçu.
	 *
	 * @param rdbtnPaiementRecu le bouton radio paiement reçu
	 */
	public void setRdbtnPaiementRecu(JRadioButton rdbtnPaiementRecu) {
		this.rdbtnPaiementRecu = rdbtnPaiementRecu;
	}

	/**
	 * Définit le bouton radio indiquant un paiement émis.
	 *
	 * @param rdbtnPaiementEmis le bouton radio paiement émis
	 */
	public void setRdbtnPaiementEmis(JRadioButton rdbtnPaiementEmis) {
		this.rdbtnPaiementEmis = rdbtnPaiementEmis;
	}

	/**
	 * Définit le bouton radio indiquant qu'aucun paiement n'a été fait.
	 *
	 * @param rdbtnPaiementPasFait le bouton radio aucun paiement
	 */
	public void setRdbtnPaiementPasFait(JRadioButton rdbtnPaiementPasFait) {
		this.rdbtnPaiementPasFait = rdbtnPaiementPasFait;
	}

	/**
	 * Définit la fenêtre parente.
	 *
	 * @param fenAncetre la fenêtre parente
	 */
	public void setFenAncetre(FenBienLouableInformations fenAncetre) {
		this.fenAncetre = fenAncetre;
	}

	/**
	 * Définit le champ de texte des garages associés.
	 *
	 * @param textFieldGarages le champ de texte des garages
	 */
	public void setTextFieldGarages(JTextField textFieldGarages) {
		this.textFieldGarages = textFieldGarages;
	}

	/**
	 * Définit le champ de texte du dépôt de garantie.
	 *
	 * @param textFieldDepotGarantie le champ de texte du dépôt de garantie
	 */
	public void setTextFieldDepotGarantie(JTextField textFieldDepotGarantie) {
		this.textFieldDepotGarantie = textFieldDepotGarantie;
	}

}
