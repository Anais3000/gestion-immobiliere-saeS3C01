package vue.modifier;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controleur.Outils;
import controleur.modifier.GestionFenModifierBatiment;
import modele.Batiment;
import vue.consulter_informations.FenBatimentInformations;

/**
 * Fenêtre de modification des informations d'un bâtiment. Permet de modifier
 * l'adresse, le code postal, la ville, la date de construction et le numéro
 * fiscal d'un bâtiment existant. L'identifiant du bâtiment n'est pas
 * modifiable.
 */
public class FenModifierBatiment extends JFrame {

	private static final long serialVersionUID = 1L;

	// Composants d'affichage
	private JPanel contentPane; // Panel principal de la fenêtre
	private JTextField textFieldIDBat; // Champ non modifiable affichant l'identifiant du bâtiment
	private JTextField textFieldDateConstructionBat; // Champ pour modifier la date de construction
	private JTextField textFieldAdresseBat; // Champ pour modifier l'adresse
	private JTextField textFieldCodePostalBat; // Champ pour modifier le code postal
	private JTextField textFieldVilleBat; // Champ pour modifier la ville
	private JTextField textFieldNumFiscal; // Champ pour modifier le numéro fiscal

	// Contrôleur
	private transient GestionFenModifierBatiment gestionClic; // Contrôleur des événements de la fenêtre

	// Données métier
	private transient Batiment batAvModif; // Bâtiment avant modification (sauvegarde de l'état initial)
	private FenBatimentInformations fenBatInfoAncetre; // Fenêtre parente d'informations du bâtiment

	public FenModifierBatiment(Batiment batimentAvantModification, FenBatimentInformations fenBatInfo) {
		this.fenBatInfoAncetre = fenBatInfo;
		batAvModif = batimentAvantModification;
		gestionClic = new GestionFenModifierBatiment(this);
		setBounds(100, 100, 450, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel du haut : titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		// Titre de la fenêtre
		JLabel lblLabelTitreModifierBatiment = new JLabel("Modifier Bâtiment");
		lblLabelTitreModifierBatiment.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreModifierBatiment.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitreModifierBatiment, BorderLayout.NORTH);

		// Panel central : formulaire de modification
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel contenant les champs de saisie avec GridBagLayout
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.CENTER);
		GridBagLayout gblPanelGridWest = new GridBagLayout();
		gblPanelGridWest.columnWidths = new int[] { 127, 0, 127, 0 };
		gblPanelGridWest.rowHeights = new int[] { 50, 50, 50, 50, 50, 50 };
		gblPanelGridWest.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gblPanelGridWest.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		panelGridWest.setLayout(gblPanelGridWest);

		// Champ identifiant (non modifiable)
		JLabel lblIdentifiantBat = new JLabel("Identifiant :");
		lblIdentifiantBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblIdentifiantBat = new GridBagConstraints();
		gbcLblIdentifiantBat.anchor = GridBagConstraints.WEST;
		gbcLblIdentifiantBat.fill = GridBagConstraints.VERTICAL;
		gbcLblIdentifiantBat.insets = new Insets(0, 0, 5, 5);
		gbcLblIdentifiantBat.gridx = 0;
		gbcLblIdentifiantBat.gridy = 0;
		panelGridWest.add(lblIdentifiantBat, gbcLblIdentifiantBat);

		// Champ affichant l'identifiant (non éditable)
		textFieldIDBat = new JTextField();
		textFieldIDBat.setText(batAvModif.getIdBat());
		textFieldIDBat.setEditable(false);
		GridBagConstraints gbcTextFieldIDBat = new GridBagConstraints();
		gbcTextFieldIDBat.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldIDBat.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldIDBat.gridx = 2;
		gbcTextFieldIDBat.gridy = 0;
		panelGridWest.add(textFieldIDBat, gbcTextFieldIDBat);
		textFieldIDBat.setColumns(10);

		// Champ date de construction
		JLabel lblDateConstructionBat = new JLabel("Date de Construction :");
		lblDateConstructionBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblDateConstructionBat = new GridBagConstraints();
		gbcLblDateConstructionBat.anchor = GridBagConstraints.WEST;
		gbcLblDateConstructionBat.fill = GridBagConstraints.VERTICAL;
		gbcLblDateConstructionBat.insets = new Insets(0, 0, 5, 5);
		gbcLblDateConstructionBat.gridx = 0;
		gbcLblDateConstructionBat.gridy = 1;
		panelGridWest.add(lblDateConstructionBat, gbcLblDateConstructionBat);

		// Champ pour modifier la date de construction (format dd-MM-yyyy)
		textFieldDateConstructionBat = new JTextField();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		textFieldDateConstructionBat.setText(batAvModif.getDateConstruction().format(formatter));
		GridBagConstraints gbcTextFieldDateConstructionBat = new GridBagConstraints();
		gbcTextFieldDateConstructionBat.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldDateConstructionBat.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldDateConstructionBat.gridx = 2;
		gbcTextFieldDateConstructionBat.gridy = 1;
		panelGridWest.add(textFieldDateConstructionBat, gbcTextFieldDateConstructionBat);
		textFieldDateConstructionBat.setColumns(10);

		// Champ adresse
		JLabel lblAdresseBat = new JLabel("Adresse :");
		lblAdresseBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblAdresseBat = new GridBagConstraints();
		gbcLblAdresseBat.anchor = GridBagConstraints.WEST;
		gbcLblAdresseBat.fill = GridBagConstraints.VERTICAL;
		gbcLblAdresseBat.insets = new Insets(0, 0, 5, 5);
		gbcLblAdresseBat.gridx = 0;
		gbcLblAdresseBat.gridy = 2;
		panelGridWest.add(lblAdresseBat, gbcLblAdresseBat);

		// Champ pour modifier l'adresse
		textFieldAdresseBat = new JTextField();
		textFieldAdresseBat.setText(batAvModif.getAdresse());
		GridBagConstraints gbcTextFieldAdresseBat = new GridBagConstraints();
		gbcTextFieldAdresseBat.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldAdresseBat.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldAdresseBat.gridx = 2;
		gbcTextFieldAdresseBat.gridy = 2;
		panelGridWest.add(textFieldAdresseBat, gbcTextFieldAdresseBat);
		textFieldAdresseBat.setColumns(10);

		// Champ code postal
		JLabel lblCodePostalBat = new JLabel("Code Postal :");
		lblCodePostalBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblCodePostalBat = new GridBagConstraints();
		gbcLblCodePostalBat.anchor = GridBagConstraints.WEST;
		gbcLblCodePostalBat.insets = new Insets(0, 0, 5, 5);
		gbcLblCodePostalBat.gridx = 0;
		gbcLblCodePostalBat.gridy = 3;
		panelGridWest.add(lblCodePostalBat, gbcLblCodePostalBat);

		// Champ pour modifier le code postal
		textFieldCodePostalBat = new JTextField();
		textFieldCodePostalBat.setText(batAvModif.getCodePostal());
		GridBagConstraints gbcTextFieldCodePostalBat = new GridBagConstraints();
		gbcTextFieldCodePostalBat.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldCodePostalBat.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldCodePostalBat.gridx = 2;
		gbcTextFieldCodePostalBat.gridy = 3;
		panelGridWest.add(textFieldCodePostalBat, gbcTextFieldCodePostalBat);
		textFieldCodePostalBat.setColumns(10);

		// Champ ville
		JLabel lblVilleBat = new JLabel("Ville :");
		lblVilleBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblVilleBat = new GridBagConstraints();
		gbcLblVilleBat.anchor = GridBagConstraints.WEST;
		gbcLblVilleBat.insets = new Insets(0, 0, 5, 5);
		gbcLblVilleBat.gridx = 0;
		gbcLblVilleBat.gridy = 4;
		panelGridWest.add(lblVilleBat, gbcLblVilleBat);

		// Champ pour modifier la ville
		textFieldVilleBat = new JTextField();
		textFieldVilleBat.setText(batAvModif.getVille());
		GridBagConstraints gbcTextFieldVilleBat = new GridBagConstraints();
		gbcTextFieldVilleBat.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldVilleBat.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldVilleBat.gridx = 2;
		gbcTextFieldVilleBat.gridy = 4;
		panelGridWest.add(textFieldVilleBat, gbcTextFieldVilleBat);
		textFieldVilleBat.setColumns(10);

		// Champ numéro fiscal
		JLabel lblNewLabel = new JLabel("Numéro fiscal :");
		lblNewLabel.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblNewLabel = new GridBagConstraints();
		gbcLblNewLabel.anchor = GridBagConstraints.WEST;
		gbcLblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbcLblNewLabel.gridx = 0;
		gbcLblNewLabel.gridy = 5;
		panelGridWest.add(lblNewLabel, gbcLblNewLabel);

		// Champ pour modifier le numéro fiscal
		textFieldNumFiscal = new JTextField();
		textFieldNumFiscal.setColumns(10);
		textFieldNumFiscal.setText(batAvModif.getNumFiscal());
		GridBagConstraints gbcTextFieldNumFiscal = new GridBagConstraints();
		gbcTextFieldNumFiscal.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldNumFiscal.gridx = 2;
		gbcTextFieldNumFiscal.gridy = 5;
		panelGridWest.add(textFieldNumFiscal, gbcTextFieldNumFiscal);

		// Panel du bas : boutons valider et annuler
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Bouton pour valider les modifications
		JButton btnValiderAjoutBat = new JButton("Valider");
		btnValiderAjoutBat.addActionListener(gestionClic);
		btnValiderAjoutBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutBat);

		// Bouton pour annuler et fermer la fenêtre
		JButton btnAnnulerAjoutBat = new JButton("Annuler");
		btnAnnulerAjoutBat.addActionListener(gestionClic);
		btnAnnulerAjoutBat.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutBat);

	}

	/**
	 * Récupère la fenêtre parente d'informations du bâtiment.
	 * 
	 * @return la fenêtre parente
	 */
	public FenBatimentInformations getFenBatInfoAncetre() {
		return fenBatInfoAncetre;
	}

	/**
	 * Récupère le bâtiment avec les modifications saisies par l'utilisateur. Crée
	 * un nouveau bâtiment avec les valeurs modifiées des champs de saisie.
	 * 
	 * @return le bâtiment modifié
	 */
	public Batiment getBatimentModifie() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return new Batiment(batAvModif.getIdBat(), LocalDate.parse(textFieldDateConstructionBat.getText(), formatter),
				textFieldAdresseBat.getText(), textFieldCodePostalBat.getText(), textFieldVilleBat.getText(),
				textFieldNumFiscal.getText());
	}

	/**
	 * Récupère le bâtiment avant modification (état initial).
	 * 
	 * @return le bâtiment avant modification
	 */
	public Batiment getBatimentAvantModification() {
		return this.batAvModif;
	}

	/**
	 * Récupère le champ de texte de l'adresse.
	 * 
	 * @return le champ de texte de l'adresse
	 */
	public JTextField getTextFieldAdresseBat() {
		return textFieldAdresseBat;
	}

	/**
	 * Récupère le champ de texte de l'identifiant (non modifiable).
	 * 
	 * @return le champ de texte de l'identifiant
	 */
	public JTextField getTextFieldIDBat() {
		return textFieldIDBat;
	}

	/**
	 * Récupère le champ de texte de la date de construction.
	 * 
	 * @return le champ de texte de la date de construction
	 */
	public JTextField getTextFieldDateConstructionBat() {
		return textFieldDateConstructionBat;
	}

	/**
	 * Récupère le champ de texte du code postal.
	 * 
	 * @return le champ de texte du code postal
	 */
	public JTextField getTextFieldCodePostalBat() {
		return textFieldCodePostalBat;
	}

	/**
	 * Récupère le champ de texte de la ville.
	 * 
	 * @return le champ de texte de la ville
	 */
	public JTextField getTextFieldVilleBat() {
		return textFieldVilleBat;
	}

	/**
	 * Récupère le champ de texte du numéro fiscal.
	 * 
	 * @return le champ de texte du numéro fiscal
	 */
	public JTextField getTextFieldNumFiscal() {
		return textFieldNumFiscal;
	}

}
