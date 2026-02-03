package vue.ajouter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controleur.Outils;
import controleur.consulter_informations.fen_bien_louable.GestionFenPaiementLoyer;
import modele.BienLouable;
import modele.Louer;

public class FenPaiementLoyer extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JPanel panelGridWest; // Panel contenant le formulaire avec GridBagLayout
	private JComboBox<String> comboBoxMois; // ComboBox pour sélectionner le mois de loyer à payer
	private JLabel lblMois; // Label pour le mois
	private JTextField textFieldMontant; // Champ du montant (loyer + provision pour charges)
	private JLabel lblMontant; // Label pour le montant
	private JTextField textFieldDate; // Champ de la date de paiement (initialisé à la date du jour)

	// Contrôleur
	private transient GestionFenPaiementLoyer gestionClic; // Contrôleur pour gérer les événements de cette fenêtre

	// Variables de mise en forme
	int marge; // Marge pour l'espacement dans le GridBagLayout

	/**
	 * Construit la page Paiement du Loyer qui permet d'enregistrer le paiement du
	 * loyer mensuel pour une location. Ce constructeur initialise les champs
	 * suivants : - Le mois de loyer à payer (sélection dans une combobox des mois
	 * non encore payés) - Le montant calculé automatiquement (loyer + provision
	 * pour charges du mois sélectionné) - La date du paiement (initialisée à la
	 * date du jour)
	 * 
	 * Note : Le montant prend en compte les ajustements de loyer après
	 * régularisation si applicable.
	 *
	 * @param bien le bien louable concerné par le paiement
	 * @param loc  la location (bail) concernée par le paiement
	 */
	public FenPaiementLoyer(BienLouable bien, Louer loc) {
		// Affectation des variables
		marge = 15;

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenPaiementLoyer(this, bien, loc);

		// Panel racine
		setBounds(100, 100, 666, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		JLabel lblTitre = new JLabel("Paiement du loyer");
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblTitre, BorderLayout.NORTH);

		// Panel corps
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel formulaire avec GridBagLayout pour un positionnement précis
		panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.CENTER);
		GridBagLayout gblPanelGridWest = new GridBagLayout();
		gblPanelGridWest.columnWidths = new int[] { 127, 127 };
		gblPanelGridWest.columnWeights = new double[] { 0.0, 1.0 };
		panelGridWest.setLayout(gblPanelGridWest);

		// ComboBox pour sélectionner le mois de loyer à payer
		lblMois = new JLabel("Mois de loyer payé :");
		lblMois.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblMois = new GridBagConstraints();
		gbcLblMois.anchor = GridBagConstraints.WEST;
		gbcLblMois.fill = GridBagConstraints.VERTICAL;
		gbcLblMois.insets = new Insets(15, 0, 15, 5);
		gbcLblMois.gridx = 0;
		gbcLblMois.gridy = 0;
		panelGridWest.add(lblMois, gbcLblMois);

		comboBoxMois = new JComboBox<>();
		GridBagConstraints gbcComboBoxMois = new GridBagConstraints();
		gbcComboBoxMois.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBoxMois.insets = new Insets(15, 0, 15, 0);
		gbcComboBoxMois.gridx = 1;
		gbcComboBoxMois.gridy = 0;
		panelGridWest.add(comboBoxMois, gbcComboBoxMois);

		// Champ montant calculé automatiquement
		lblMontant = new JLabel("Montant (en €) :");
		lblMontant.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblMontant = new GridBagConstraints();
		gbcLblMontant.anchor = GridBagConstraints.WEST;
		gbcLblMontant.insets = new Insets(15, 0, 15, 5);
		gbcLblMontant.gridx = 0;
		gbcLblMontant.gridy = 1;
		panelGridWest.add(lblMontant, gbcLblMontant);

		// Calcul du montant : loyer + provision pour charges
		Float montant = bien.getLoyer() + bien.getProvisionPourCharges();

		textFieldMontant = new JTextField(String.valueOf(montant));
		GridBagConstraints gbcSpinnerMontant = new GridBagConstraints();
		gbcSpinnerMontant.insets = new Insets(0, 0, 5, 0);
		gbcSpinnerMontant.fill = GridBagConstraints.HORIZONTAL;
		gbcSpinnerMontant.gridx = 1;
		gbcSpinnerMontant.gridy = 1;
		panelGridWest.add(textFieldMontant, gbcSpinnerMontant);

		// Panel contenant les messages informatifs sur le calcul du montant
		JPanel panelInfos = new JPanel();
		GridBagConstraints gbcPanelInfos = new GridBagConstraints();
		gbcPanelInfos.fill = GridBagConstraints.BOTH;
		gbcPanelInfos.insets = new Insets(0, 0, 5, 0);
		gbcPanelInfos.gridx = 1;
		gbcPanelInfos.gridy = 2;
		panelGridWest.add(panelInfos, gbcPanelInfos);
		panelInfos.setLayout(new GridLayout(2, 1, 0, 0));

		JLabel lblInfoMontant = new JLabel(
				"Montant calculé à partir du loyer et de la provision pour charges du mois sélectionné.");
		lblInfoMontant.setHorizontalAlignment(SwingConstants.LEFT);
		panelInfos.add(lblInfoMontant);

		JLabel lblInfoAjustement = new JLabel(
				"*Si un ajustement de loyer a été prévu après une régularisation, il est pris en compte.");
		lblInfoAjustement.setHorizontalAlignment(SwingConstants.LEFT);
		panelInfos.add(lblInfoAjustement);

		// Champ date du paiement avec valeur par défaut (date du jour)
		JLabel lblDate = new JLabel("Date du paiement :");
		lblDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblDate = new GridBagConstraints();
		gbcLblDate.insets = new Insets(0, 0, 5, 5);
		gbcLblDate.gridx = 0;
		gbcLblDate.gridy = 3;
		panelGridWest.add(lblDate, gbcLblDate);

		textFieldDate = new JTextField();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dateFormatee = LocalDate.now().format(formatter);
		textFieldDate.setText(String.valueOf(dateFormatee));
		GridBagConstraints gbcTextFieldDate = new GridBagConstraints();
		gbcTextFieldDate.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldDate.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldDate.gridx = 1;
		gbcTextFieldDate.gridy = 3;
		panelGridWest.add(textFieldDate, gbcTextFieldDate);
		textFieldDate.setColumns(10);

		JPanel panelRadio = new JPanel();
		GridBagConstraints gbcPanelRadio = new GridBagConstraints();
		gbcPanelRadio.insets = new Insets(15, 0, 15, 0);
		gbcPanelRadio.fill = GridBagConstraints.BOTH;
		gbcPanelRadio.gridx = 1;
		gbcPanelRadio.gridy = 4;
		panelGridWest.add(panelRadio, gbcPanelRadio);
		panelRadio.setLayout(new GridLayout(0, 2, 0, 0));

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Bouton pour valider l'enregistrement du paiement
		JButton btnValiderAjoutPaiement = new JButton("Valider");
		btnValiderAjoutPaiement.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutPaiement);
		btnValiderAjoutPaiement.addActionListener(this.gestionClic);

		// Bouton pour annuler et fermer la fenêtre
		JButton btnAnnulerAjoutPaiement = new JButton("Annuler");
		btnAnnulerAjoutPaiement.addActionListener(this.gestionClic);
		btnAnnulerAjoutPaiement.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutPaiement);

		// Initialisation des données via le contrôleur
		gestionClic.remplirComboboxes(); // Remplit la combobox avec les mois non payés
		gestionClic.ajouterListenerComboBox(); // Ajoute un listener pour mettre à jour le montant selon le mois
		gestionClic.setTextMontant(); // Définit le montant initial
	}

	/**
	 * Retourne la combobox des mois de loyer
	 * 
	 * @return la combobox permettant de sélectionner le mois à payer
	 */
	public JComboBox<String> getComboBoxMois() {
		return comboBoxMois;
	}

	/**
	 * Modifie la combobox des mois
	 * 
	 * @param comboBoxBien la nouvelle combobox à utiliser
	 */
	public void setComboBoxMois(JComboBox<String> comboBoxBien) {
		this.comboBoxMois = comboBoxBien;
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

	/**
	 * Retourne le champ de la date de paiement
	 * 
	 * @return le champ de texte de la date
	 */
	public JTextField getTextFieldDate() {
		return textFieldDate;
	}

	/**
	 * Retourne le contrôleur de cette fenêtre
	 * 
	 * @return le gestionnaire d'événements
	 */
	public GestionFenPaiementLoyer getGestionClic() {
		return gestionClic;
	}

}
