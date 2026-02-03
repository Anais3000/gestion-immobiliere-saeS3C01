package vue.ajouter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controleur.Outils;
import controleur.ajouter.GestionFenAjouterPaiement;
import vue.tables.FenPaiement;

public class FenAjouterPaiementOccasionnel extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JTextField textFieldDatePaiement; // Champ pour la date du paiement (format DD-MM-YYYY)
	private JRadioButton rdbtnEmisPaiement; // Bouton radio pour un paiement émis (dépense)
	private JRadioButton rdbtnRecuPaiement; // Bouton radio pour un paiement reçu (recette)
	private JLabel lblLibelle; // Label pour le libellé
	private JPanel panelGridWest; // Panel contenant le formulaire avec GridBagLayout
	private JTextField textFieldLibelle; // Champ pour le libellé du paiement
	private JTextField textFieldMontant; // Champ pour le montant du paiement
	private JLabel lblMontant; // Label pour le montant
	private JPanel panelInfoGrid; // Panel contenant les messages informatifs
	private JLabel lblInfo2; // Message d'information ligne 2
	private JLabel lblInfo1; // Message d'information ligne 1

	// Contrôleur
	private transient GestionFenAjouterPaiement gestionClic; // Contrôleur pour gérer les événements de cette fenêtre

	// Variables de mise en forme
	private int marge; // Marge pour l'espacement dans le GridBagLayout

	/**
	 * Construit la page Ajouter Paiement Occasionnel qui permet d'enregistrer un
	 * paiement ponctuel qui n'est pas un paiement de loyer ou de régularisation
	 * (ces derniers sont enregistrés automatiquement).
	 * 
	 * Ce constructeur initialise les champs suivants : - Le sens du paiement (émis
	 * ou reçu) via des boutons radio - Le libellé du paiement (description) - La
	 * date du paiement - Le montant du paiement
	 * 
	 * Important : Cette fenêtre ne doit être utilisée que pour les paiements
	 * occasionnels. Les paiements de loyers et de régularisations sont gérés par
	 * d'autres fenêtres spécifiques.
	 *
	 * @param fenPaiement la fenêtre parente (table des paiements) qui sera mise à
	 *                    jour après l'ajout
	 */
	public FenAjouterPaiementOccasionnel(FenPaiement fenPaiement) {
		// Affectation des variables
		marge = 15;

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenAjouterPaiement(fenPaiement, this);

		// Panel racine
		setBounds(100, 100, 555, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		JLabel lblLabelTitreAjouterPaiement = new JLabel("Ajouter un Paiement");
		lblLabelTitreAjouterPaiement.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreAjouterPaiement.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitreAjouterPaiement, BorderLayout.CENTER);

		panelInfoGrid = new JPanel();
		panel.add(panelInfoGrid, BorderLayout.SOUTH);
		panelInfoGrid.setLayout(new GridLayout(2, 0, 0, 0));

		lblInfo1 = new JLabel("Cette fenête ne doit être utilisée que pour les paiements occasionnels.");
		lblInfo1.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelInfoGrid.add(lblInfo1);

		lblInfo2 = new JLabel("Les paiements de loyers, de régularisations, etc. sont rentrés automatiquement.");
		lblInfo2.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		panelInfoGrid.add(lblInfo2);

		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.NORTH);
		GridBagLayout gblPanelGridWest = new GridBagLayout();
		gblPanelGridWest.columnWidths = new int[] { 127, 30, 127, 30 };
		gblPanelGridWest.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panelGridWest.setLayout(gblPanelGridWest);

		// Boutons radio pour choisir le sens du paiement (émis ou reçu)
		JLabel lblTypePaiement = new JLabel("Sens du Paiement :");
		lblTypePaiement.setHorizontalAlignment(SwingConstants.LEFT);
		lblTypePaiement.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblTypePaiement = new GridBagConstraints();
		gbcLblTypePaiement.anchor = GridBagConstraints.WEST;
		gbcLblTypePaiement.insets = new Insets(15, 0, 15, 5);
		gbcLblTypePaiement.gridx = 0;
		gbcLblTypePaiement.gridy = 0;
		panelGridWest.add(lblTypePaiement, gbcLblTypePaiement);

		// Panel contenant les deux boutons radio
		JPanel panelRadio = new JPanel();
		GridBagConstraints gbcPanelRadio = new GridBagConstraints();
		gbcPanelRadio.insets = new Insets(marge, 0, marge, 0);
		gbcPanelRadio.fill = GridBagConstraints.BOTH;
		gbcPanelRadio.gridx = 2;
		gbcPanelRadio.gridy = 0;
		panelGridWest.add(panelRadio, gbcPanelRadio);
		panelRadio.setLayout(new GridLayout(0, 2, 0, 0));

		rdbtnEmisPaiement = new JRadioButton("Émis");
		rdbtnEmisPaiement.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 14));
		rdbtnEmisPaiement.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnEmisPaiement.setActionCommand("emis");
		panelRadio.add(rdbtnEmisPaiement);

		rdbtnRecuPaiement = new JRadioButton("Reçu");
		rdbtnRecuPaiement.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 14));
		rdbtnRecuPaiement.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnRecuPaiement.setActionCommand("recu");
		rdbtnRecuPaiement.setSelected(true);
		panelRadio.add(rdbtnRecuPaiement);

		ButtonGroup groupPaiement = new ButtonGroup();
		groupPaiement.add(rdbtnEmisPaiement);
		groupPaiement.add(rdbtnRecuPaiement);

		// Champ libellé du paiement
		lblLibelle = new JLabel("Libellé");
		lblLibelle.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblProvisionPaiement = new GridBagConstraints();
		gbcLblProvisionPaiement.anchor = GridBagConstraints.WEST;
		gbcLblProvisionPaiement.fill = GridBagConstraints.VERTICAL;
		gbcLblProvisionPaiement.insets = new Insets(15, 0, 15, 5);
		gbcLblProvisionPaiement.gridx = 0;
		gbcLblProvisionPaiement.gridy = 1;
		panelGridWest.add(lblLibelle, gbcLblProvisionPaiement);

		textFieldLibelle = new JTextField();
		GridBagConstraints gbcComboBoxLibelle = new GridBagConstraints();
		gbcComboBoxLibelle.insets = new Insets(marge, 0, marge, 0);
		gbcComboBoxLibelle.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBoxLibelle.gridx = 2;
		gbcComboBoxLibelle.gridy = 1;
		panelGridWest.add(textFieldLibelle, gbcComboBoxLibelle);

		// Champ date du paiement avec format par défaut
		JLabel lblDatePaiement = new JLabel("Date du Paiement :");
		lblDatePaiement.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblDatePaiement = new GridBagConstraints();
		gbcLblDatePaiement.anchor = GridBagConstraints.WEST;
		gbcLblDatePaiement.fill = GridBagConstraints.VERTICAL;
		gbcLblDatePaiement.insets = new Insets(15, 0, 15, 5);
		gbcLblDatePaiement.gridx = 0;
		gbcLblDatePaiement.gridy = 2;
		panelGridWest.add(lblDatePaiement, gbcLblDatePaiement);

		textFieldDatePaiement = new JTextField();
		textFieldDatePaiement.setText("DD-MM-YYYY");
		GridBagConstraints gbcTextFieldDatePaiement = new GridBagConstraints();
		gbcTextFieldDatePaiement.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldDatePaiement.insets = new Insets(marge, 0, marge, 0);
		gbcTextFieldDatePaiement.gridx = 2;
		gbcTextFieldDatePaiement.gridy = 2;
		panelGridWest.add(textFieldDatePaiement, gbcTextFieldDatePaiement);
		textFieldDatePaiement.setColumns(10);

		// Champ montant du paiement
		GridBagConstraints gbcLblBien = new GridBagConstraints();
		gbcLblBien.anchor = GridBagConstraints.WEST;
		gbcLblBien.fill = GridBagConstraints.VERTICAL;
		gbcLblBien.insets = new Insets(15, 0, 15, 5);
		gbcLblBien.gridx = 0;
		gbcLblBien.gridy = 3;

		GridBagConstraints gbcLblIntervention = new GridBagConstraints();
		gbcLblIntervention.anchor = GridBagConstraints.WEST;
		gbcLblIntervention.fill = GridBagConstraints.VERTICAL;
		gbcLblIntervention.insets = new Insets(marge, 0, marge, 0);
		gbcLblIntervention.gridx = 0;
		gbcLblIntervention.gridy = 4;

		lblMontant = new JLabel("Montant :");
		lblMontant.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblMontant = new GridBagConstraints();
		gbcLblMontant.anchor = GridBagConstraints.WEST;
		gbcLblMontant.insets = new Insets(15, 0, 15, 5);
		gbcLblMontant.gridx = 0;
		gbcLblMontant.gridy = 3;
		panelGridWest.add(lblMontant, gbcLblMontant);

		textFieldMontant = new JTextField();
		GridBagConstraints gbcSpinnerMontant = new GridBagConstraints();
		gbcSpinnerMontant.fill = GridBagConstraints.HORIZONTAL;
		gbcSpinnerMontant.gridx = 2;
		gbcSpinnerMontant.gridy = 3;
		panelGridWest.add(textFieldMontant, gbcSpinnerMontant);

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Bouton pour valider l'ajout du paiement
		JButton btnValiderAjoutPaiement = new JButton("Valider");
		btnValiderAjoutPaiement.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutPaiement);
		btnValiderAjoutPaiement.addActionListener(this.gestionClic);

		// Bouton pour annuler et fermer la fenêtre
		JButton btnAnnulerAjoutPaiement = new JButton("Annuler");
		btnAnnulerAjoutPaiement.addActionListener(this.gestionClic);

		btnAnnulerAjoutPaiement.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutPaiement);

	}

	/**
	 * Retourne le champ du libellé du paiement
	 * 
	 * @return le champ de texte du libellé
	 */
	public JTextField getTextFieldLibelle() {
		return textFieldLibelle;
	}

	/**
	 * Modifie le champ du libellé
	 * 
	 * @param textFieldLibelle le nouveau champ de texte à utiliser
	 */
	public void setTextFieldLibelle(JTextField textFieldLibelle) {
		this.textFieldLibelle = textFieldLibelle;
	}

	/**
	 * Retourne le champ de la date de paiement
	 * 
	 * @return le champ de texte de la date
	 */
	public JTextField getTextFieldDatePaiement() {
		return textFieldDatePaiement;
	}

	/**
	 * Modifie le champ de la date de paiement
	 * 
	 * @param textFieldDatePaiement le nouveau champ de texte à utiliser
	 */
	public void setTextFieldDatePaiement(JTextField textFieldDatePaiement) {
		this.textFieldDatePaiement = textFieldDatePaiement;
	}

	/**
	 * Retourne le bouton radio pour les paiements émis (dépenses)
	 * 
	 * @return le bouton radio émis
	 */
	public JRadioButton getRdbtnEmisPaiement() {
		return rdbtnEmisPaiement;
	}

	/**
	 * Modifie le bouton radio pour les paiements émis
	 * 
	 * @param rdbtnEmisPaiement le nouveau bouton radio à utiliser
	 */
	public void setRdbtnEmisPaiement(JRadioButton rdbtnEmisPaiement) {
		this.rdbtnEmisPaiement = rdbtnEmisPaiement;
	}

	/**
	 * Retourne le bouton radio pour les paiements reçus (recettes)
	 * 
	 * @return le bouton radio reçu
	 */
	public JRadioButton getRdbtnRecuPaiement() {
		return rdbtnRecuPaiement;
	}

	/**
	 * Modifie le bouton radio pour les paiements reçus
	 * 
	 * @param rdbtnRecuPaiement le nouveau bouton radio à utiliser
	 */
	public void setRdbtnRecuPaiement(JRadioButton rdbtnRecuPaiement) {
		this.rdbtnRecuPaiement = rdbtnRecuPaiement;
	}

	/**
	 * Retourne le champ du montant du paiement
	 * 
	 * @return le champ de texte du montant
	 */
	public JTextField getTextFieldMontant() {
		return textFieldMontant;
	}

	/**
	 * Modifie le champ du montant
	 * 
	 * @param textFieldMontant le nouveau champ de texte à utiliser
	 */
	public void setTextFieldMontant(JTextField textFieldMontant) {
		this.textFieldMontant = textFieldMontant;
	}
}
