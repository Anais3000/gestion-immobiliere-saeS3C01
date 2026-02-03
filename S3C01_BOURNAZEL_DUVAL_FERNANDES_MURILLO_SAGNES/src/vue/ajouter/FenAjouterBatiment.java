package vue.ajouter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controleur.Outils;
import controleur.ajouter.GestionFenAjouterBatiment;
import vue.tables.FenBatiment;

public class FenAjouterBatiment extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JTextField textFieldIDBat; // Champ de saisie de l'identifiant du bâtiment
	private JTextField textFieldDateConstructionBat; // Champ de saisie de la date de construction
	private JTextField textFieldAdresseBat; // Champ de saisie de l'adresse
	private JTextField textFieldCodePostalBat; // Champ de saisie du code postal (5 chiffres)
	private JTextField textFieldVilleBat; // Champ de saisie de la ville
	private JTextField textFieldNumFiscal; // Champ de saisie du numéro fiscal (12 chiffres)

	// Contrôleur
	private transient GestionFenAjouterBatiment gestionClic; // Contrôleur pour gérer les événements de cette fenêtre

	// Variables de gestion
	private FenBatiment fB; // La fenêtre parente (table des bâtiments)

	/**
	 * Construit la page Ajouter Bâtiment qui permet d'ajouter un nouveau bâtiment
	 * Ce constructeur initialise les champs à remplir suivants : L'identifiant du
	 * bâtiment La date de construction L'adresse complète (adresse, code postal,
	 * ville) Le numéro fiscal du bâtiment
	 *
	 * @param fB la fenêtre parente (table des bâtiments) qui sera mise à jour après
	 *           l'ajout
	 */
	public FenAjouterBatiment(FenBatiment fB) {
		// Affectation des variables
		this.fB = fB;

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenAjouterBatiment(this);

		// Panel racine
		setBounds(100, 100, 450, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		JLabel lblLabelTitreAjouterBatiment = new JLabel("Ajouter un Bâtiment");
		lblLabelTitreAjouterBatiment.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreAjouterBatiment.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitreAjouterBatiment, BorderLayout.NORTH);

		// Panel corps (contient le formulaire et les boutons)
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel formulaire de saisie
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelBorder.add(panelGridWest, BorderLayout.CENTER);
		GridBagLayout gblPanelGridWest = new GridBagLayout();
		gblPanelGridWest.columnWidths = new int[] { 127, 0, 127, 0 };
		gblPanelGridWest.rowHeights = new int[] { 50, 50, 50, 50, 50, 50 };
		gblPanelGridWest.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gblPanelGridWest.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		panelGridWest.setLayout(gblPanelGridWest);

		JLabel lblIdentifiantBat = new JLabel("Identifiant :");
		lblIdentifiantBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblIdentifiantBat = new GridBagConstraints();
		gbcLblIdentifiantBat.anchor = GridBagConstraints.WEST;
		gbcLblIdentifiantBat.fill = GridBagConstraints.VERTICAL;
		gbcLblIdentifiantBat.insets = new Insets(0, 0, 5, 5);
		gbcLblIdentifiantBat.gridx = 0;
		gbcLblIdentifiantBat.gridy = 0;
		panelGridWest.add(lblIdentifiantBat, gbcLblIdentifiantBat);

		textFieldIDBat = new JTextField();
		GridBagConstraints gbcTextFieldIDBat = new GridBagConstraints();
		gbcTextFieldIDBat.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldIDBat.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldIDBat.gridx = 2;
		gbcTextFieldIDBat.gridy = 0;
		panelGridWest.add(textFieldIDBat, gbcTextFieldIDBat);
		textFieldIDBat.setColumns(10);

		JLabel lblDateConstructionBat = new JLabel("Date de Construction :");
		lblDateConstructionBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblDateConstructionBat = new GridBagConstraints();
		gbcLblDateConstructionBat.anchor = GridBagConstraints.WEST;
		gbcLblDateConstructionBat.fill = GridBagConstraints.VERTICAL;
		gbcLblDateConstructionBat.insets = new Insets(0, 0, 5, 5);
		gbcLblDateConstructionBat.gridx = 0;
		gbcLblDateConstructionBat.gridy = 1;
		panelGridWest.add(lblDateConstructionBat, gbcLblDateConstructionBat);

		textFieldDateConstructionBat = new JTextField();
		GridBagConstraints gbcTextFieldDateConstructionBat = new GridBagConstraints();
		gbcTextFieldDateConstructionBat.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldDateConstructionBat.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldDateConstructionBat.gridx = 2;
		gbcTextFieldDateConstructionBat.gridy = 1;
		panelGridWest.add(textFieldDateConstructionBat, gbcTextFieldDateConstructionBat);
		textFieldDateConstructionBat.setColumns(10);

		JLabel lblAdresseBat = new JLabel("Adresse :");
		lblAdresseBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblAdresseBat = new GridBagConstraints();
		gbcLblAdresseBat.anchor = GridBagConstraints.WEST;
		gbcLblAdresseBat.fill = GridBagConstraints.VERTICAL;
		gbcLblAdresseBat.insets = new Insets(0, 0, 5, 5);
		gbcLblAdresseBat.gridx = 0;
		gbcLblAdresseBat.gridy = 2;
		panelGridWest.add(lblAdresseBat, gbcLblAdresseBat);

		textFieldAdresseBat = new JTextField();
		GridBagConstraints gbcTextFieldAdresseBat = new GridBagConstraints();
		gbcTextFieldAdresseBat.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldAdresseBat.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldAdresseBat.gridx = 2;
		gbcTextFieldAdresseBat.gridy = 2;
		panelGridWest.add(textFieldAdresseBat, gbcTextFieldAdresseBat);
		textFieldAdresseBat.setColumns(10);

		JLabel lblCodePostalBat = new JLabel("Code Postal :");
		lblCodePostalBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblCodePostalBat = new GridBagConstraints();
		gbcLblCodePostalBat.anchor = GridBagConstraints.WEST;
		gbcLblCodePostalBat.insets = new Insets(0, 0, 5, 5);
		gbcLblCodePostalBat.gridx = 0;
		gbcLblCodePostalBat.gridy = 3;
		panelGridWest.add(lblCodePostalBat, gbcLblCodePostalBat);

		textFieldCodePostalBat = new JTextField();
		GridBagConstraints gbcTextFieldCodePostalBat = new GridBagConstraints();
		gbcTextFieldCodePostalBat.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldCodePostalBat.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldCodePostalBat.gridx = 2;
		gbcTextFieldCodePostalBat.gridy = 3;
		panelGridWest.add(textFieldCodePostalBat, gbcTextFieldCodePostalBat);
		textFieldCodePostalBat.setColumns(10);

		JLabel lblVilleBat = new JLabel("Ville :");
		lblVilleBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblVilleBat = new GridBagConstraints();
		gbcLblVilleBat.anchor = GridBagConstraints.WEST;
		gbcLblVilleBat.insets = new Insets(0, 0, 0, 5);
		gbcLblVilleBat.gridx = 0;
		gbcLblVilleBat.gridy = 4;
		panelGridWest.add(lblVilleBat, gbcLblVilleBat);

		textFieldVilleBat = new JTextField();
		GridBagConstraints gbcTextFieldVilleBat = new GridBagConstraints();
		gbcTextFieldVilleBat.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldVilleBat.gridx = 2;
		gbcTextFieldVilleBat.gridy = 4;
		panelGridWest.add(textFieldVilleBat, gbcTextFieldVilleBat);
		textFieldVilleBat.setColumns(10);

		JLabel lblNumFiscal = new JLabel("Numéro Fiscal :");
		lblNumFiscal.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		lblVilleBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblNumFiscal = new GridBagConstraints();
		gbcLblNumFiscal.anchor = GridBagConstraints.WEST;
		gbcLblNumFiscal.insets = new Insets(0, 0, 0, 5);
		gbcLblNumFiscal.gridx = 0;
		gbcLblNumFiscal.gridy = 5;
		panelGridWest.add(lblNumFiscal, gbcLblNumFiscal);

		textFieldNumFiscal = new JTextField();
		GridBagConstraints gbcTextFieldNumFiscal = new GridBagConstraints();
		gbcTextFieldNumFiscal.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldNumFiscal.gridx = 2;
		gbcTextFieldNumFiscal.gridy = 5;
		panelGridWest.add(textFieldNumFiscal, gbcTextFieldNumFiscal);
		textFieldVilleBat.setColumns(10);

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnValiderAjoutBat = new JButton("Valider");
		btnValiderAjoutBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutBat);
		btnValiderAjoutBat.addActionListener(this.gestionClic);

		JButton btnAnnulerAjoutBat = new JButton("Annuler");
		btnAnnulerAjoutBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutBat);
		btnAnnulerAjoutBat.addActionListener(this.gestionClic);

	}

	/**
	 * Retourne le champ de l'adresse du bâtiment pour pouvoir l'utiliser dans les
	 * gestions et récupérer ainsi la chaîne de caractères entrée par l'utilisateur
	 * 
	 * @return le champ de texte de l'adresse
	 */
	public JTextField getTextFieldAdresseBat() {
		return textFieldAdresseBat;
	}

	/**
	 * Modifie le champ de l'adresse du bâtiment
	 * 
	 * @param textFieldAdresseBat le nouveau champ de texte
	 */
	public void setTextFieldAdresseBat(JTextField textFieldAdresseBat) {
		this.textFieldAdresseBat = textFieldAdresseBat;
	}

	/**
	 * Retourne le champ de l'identifiant du bâtiment pour pouvoir l'utiliser dans
	 * les gestions et récupérer ainsi la chaîne de caractères entrée par
	 * l'utilisateur
	 * 
	 * @return le champ de texte de l'identifiant
	 */
	public JTextField getTextFieldIDBat() {
		return textFieldIDBat;
	}

	/**
	 * Modifie le champ de l'identifiant du bâtiment
	 * 
	 * @param textFieldIDBat le nouveau champ de texte
	 */
	public void setTextFieldIDBat(JTextField textFieldIDBat) {
		this.textFieldIDBat = textFieldIDBat;
	}

	/**
	 * Retourne le champ de la date de construction du bâtiment pour pouvoir
	 * l'utiliser dans les gestions et récupérer ainsi la chaîne de caractères
	 * entrée par l'utilisateur
	 * 
	 * @return le champ de texte de la date de construction
	 */
	public JTextField getTextFieldDateConstructionBat() {
		return textFieldDateConstructionBat;
	}

	/**
	 * Modifie le champ de la date de construction du bâtiment
	 * 
	 * @param textFieldDateConstructionBat le nouveau champ de texte
	 */
	public void setTextFieldDateConstructionBat(JTextField textFieldDateConstructionBat) {
		this.textFieldDateConstructionBat = textFieldDateConstructionBat;
	}

	/**
	 * Retourne le champ du code postal du bâtiment pour pouvoir l'utiliser dans les
	 * gestions et récupérer ainsi la chaîne de caractères entrée par l'utilisateur
	 * 
	 * @return le champ de texte du code postal
	 */
	public JTextField getTextFieldCodePostalBat() {
		return textFieldCodePostalBat;
	}

	/**
	 * Modifie le champ du code postal du bâtiment
	 * 
	 * @param textFieldCodePostalBat le nouveau champ de texte
	 */
	public void setTextFieldCodePostalBat(JTextField textFieldCodePostalBat) {
		this.textFieldCodePostalBat = textFieldCodePostalBat;
	}

	/**
	 * Retourne le champ de la ville du bâtiment pour pouvoir l'utiliser dans les
	 * gestions et récupérer ainsi la chaîne de caractères entrée par l'utilisateur
	 * 
	 * @return le champ de texte de la ville
	 */
	public JTextField getTextFieldVilleBat() {
		return textFieldVilleBat;
	}

	/**
	 * Modifie le champ de la ville du bâtiment
	 * 
	 * @param textFieldVilleBat le nouveau champ de texte
	 */
	public void setTextFieldVilleBat(JTextField textFieldVilleBat) {
		this.textFieldVilleBat = textFieldVilleBat;
	}

	/**
	 * Retourne la fenêtre ancêtre à la page AjouterBatiment, donc la page depuis
	 * laquelle elle est appelée, ici, FenBatiment pour pouvoir la mettre à jour
	 * après l'ajout du bâtiment pour qu'il s'ajoute bien au tableau (liste) des
	 * bâtiments
	 * 
	 * @return la fenêtre parente (table des bâtiments)
	 */
	public FenBatiment getfB() {
		return fB;
	}

	/**
	 * Modifie la fenêtre ancêtre
	 * 
	 * @param fB la nouvelle fenêtre parente
	 */
	public void setfB(FenBatiment fB) {
		this.fB = fB;
	}

	/**
	 * Retourne le champ du numéro fiscal du bâtiment pour pouvoir l'utiliser dans
	 * les gestions et récupérer ainsi la chaîne de caractères entrée par
	 * l'utilisateur
	 * 
	 * @return le champ de texte du numéro fiscal
	 */
	public JTextField getTextFieldNumFiscal() {
		return textFieldNumFiscal;
	}

	/**
	 * Modifie le champ du numéro fiscal du bâtiment
	 * 
	 * @param textFieldNumFiscal le nouveau champ de texte
	 */
	public void setTextFieldNumFiscal(JTextField textFieldNumFiscal) {
		this.textFieldNumFiscal = textFieldNumFiscal;
	}

}
