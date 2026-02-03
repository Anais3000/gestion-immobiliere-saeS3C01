package vue.ajouter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controleur.Outils;
import controleur.ajouter.fen_ajouter_bien_louable.GestionFenAjouterBienLouable;
import controleur.ajouter.fen_ajouter_bien_louable.GestionFenAjouterBienLouableComboBox;
import modele.Batiment;
import modele.BienLouable;
import modele.dao.DaoBatiment;
import vue.consulter_informations.FenBatimentInformations;
import vue.tables.FenBienLouable;

public class FenAjouterBienLouable extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JTextField textFieldIdentifiantBL; // Champ pour l'identifiant du bien louable
	private JTextField textFieldIDBatBL; // Champ pour l'ID du bâtiment (affichage uniquement)
	private JComboBox<String> comboBoxIDBatBL; // ComboBox pour sélectionner le bâtiment
	private JTextField textFieldAdresseBL; // Champ pour l'adresse (héritée du bâtiment)
	private JTextField textFieldDateConstruction; // Champ pour la date de construction (héritée du bâtiment)
	private JTextField textFieldSurface; // Champ pour la surface habitable en m²
	private JTextField textFieldPourcentagePartieC; // Champ pour le pourcentage d'entretien des parties communes
	private JTextField textFieldPourcentageOrdures; // Champ pour le pourcentage des ordures ménagères
	private JTextField textFieldNumeroFiscalBL; // Champ pour le numéro fiscal
	private JTextField textFieldCodePostalBL; // Champ pour le code postal (hérité du bâtiment)
	private JTextField textFieldVilleBL; // Champ pour la ville (héritée du bâtiment)
	private JTextField textFieldLoyer; // Champ pour le montant du loyer
	private JTextField textFieldProvisions; // Champ pour le montant de la provision pour charges
	private JSpinner spinnerNbPieces; // Spinner pour le nombre de pièces
	private JRadioButton rdbtnLogement; // Bouton radio pour le type "Logement"
	private JRadioButton rdbtnGarage; // Bouton radio pour le type "Garage"
	private ButtonGroup groupBienLouable; // Groupe de boutons radio pour le type de bien louable

	// Contrôleurs
	private transient GestionFenAjouterBienLouable gestionClic; // Contrôleur pour gérer les événements de cette fenêtre
	private transient GestionFenAjouterBienLouableComboBox gestionCombo; // Contrôleur pour gérer la combobox des
																			// bâtiments

	// Variables de gestion
	private transient Batiment bat; // Le bâtiment associé au bien louable
	private FenBatimentInformations fBI; // La fenêtre parente informations bâtiment
	private FenBienLouable fBL; // La fenêtre parente table des biens louables
	private transient DaoBatiment daoBat; // DAO pour accéder aux données des bâtiments

	/**
	 * Construit la page Ajouter Bien Louable qui permet de créer un nouveau bien
	 * louable dans un bâtiment. Ce constructeur initialise les champs suivants :
	 * L'identifiant du bien louable L'identifiant du bâtiment (avec combobox si le
	 * bâtiment n'est pas prédéfini) L'adresse, code postal, ville et date de
	 * construction (hérités du bâtiment) Le type de bien (logement ou garage) via
	 * boutons radio La surface habitable en m² Le nombre de pièces (via spinner)
	 * Les pourcentages d'entretien des parties communes et des ordures ménagères Le
	 * numéro fiscal Le loyer mensuel La provision pour charges
	 * 
	 * Si un bâtiment est passé en paramètre, ses informations sont automatiquement
	 * utilisées. Sinon, une combobox permet de sélectionner un bâtiment existant.
	 *
	 * @param bat le bâtiment associé au bien louable (peut être null si sélection
	 *            via combobox)
	 * @param fBI la fenêtre parente informations bâtiment, si on ajoute un bien
	 *            louable depuis FenBatimentInformations (sinon null)
	 * @param fBL la fenêtre parente table des biens louables, si on ajoute un bien
	 *            louable depuis FenBienLouable (sinon null)
	 */
	public FenAjouterBienLouable(Batiment bat, FenBatimentInformations fBI, FenBienLouable fBL) {
		// Affectation des variables
		this.bat = bat;
		this.fBI = fBI;
		this.fBL = fBL;
		this.daoBat = new DaoBatiment();

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenAjouterBienLouable(this);

		// Panel racine
		setBounds(100, 100, 450, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		JLabel lblLabelTitreAjouterBienLouable = new JLabel("Ajouter un Bien Louable");
		lblLabelTitreAjouterBienLouable.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreAjouterBienLouable.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitreAjouterBienLouable, BorderLayout.NORTH);

		// Panel corps
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel contenant tous les labels (14 champs)
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.WEST);
		panelGridWest.setLayout(new GridLayout(14, 1, 0, 10));

		JLabel lblIdentifiantBL = new JLabel("Identifiant :");
		lblIdentifiantBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblIdentifiantBL);

		JLabel lblIdentifiantBatBL = new JLabel("Identifiant du Bâtiment :");
		lblIdentifiantBatBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblIdentifiantBatBL);

		JLabel lblAdresseBL = new JLabel("Adresse :");
		lblAdresseBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblAdresseBL);

		JLabel lblCodePostalBL = new JLabel("Code Postal :");
		lblCodePostalBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblCodePostalBL);

		JLabel lblVilleBL = new JLabel("Ville :");
		lblVilleBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblVilleBL);

		JLabel lblDateConstructionBL = new JLabel("Date Construction :");
		lblDateConstructionBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblDateConstructionBL);

		JLabel lblTypeBL = new JLabel("Type :");
		lblTypeBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblTypeBL);

		JLabel lblSurfaceHabitableBL = new JLabel("Surface Habitable :");
		lblSurfaceHabitableBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblSurfaceHabitableBL);

		JLabel lblNombrePiecesBL = new JLabel("Nombre de Pièces :");
		lblNombrePiecesBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblNombrePiecesBL);

		JLabel lblPourcEntretienPCBL = new JLabel("Pourcentage d'Entretien PC* :");
		lblPourcEntretienPCBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblPourcEntretienPCBL);

		JLabel lblPourcentageOrduresMenageresBL = new JLabel("Pourcentage Ordures Ménagères :");
		lblPourcentageOrduresMenageresBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblPourcentageOrduresMenageresBL);

		JLabel lblNumFiscalBL = new JLabel("Numéro Fiscal :");
		lblNumFiscalBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblNumFiscalBL);

		JLabel lblLoyerBL = new JLabel("Loyer :");
		lblLoyerBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 14));
		panelGridWest.add(lblLoyerBL);

		JLabel lblProvisionBL = new JLabel("Provision pour charges :");
		lblProvisionBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 14));
		panelGridWest.add(lblProvisionBL);

		// Panel contenant tous les champs de saisie
		JPanel panelGridCenter = new JPanel();
		panelBorder.add(panelGridCenter, BorderLayout.CENTER);
		panelGridCenter.setLayout(new GridLayout(14, 0, 0, 10));

		// Cas 1 : Ajout depuis la table des biens louables (bâtiment non prédéfini)
		if (this.bat == null) {
			textFieldIdentifiantBL = new JTextField();
			panelGridCenter.add(textFieldIdentifiantBL);
			textFieldIdentifiantBL.setColumns(10);

			this.gestionCombo = new GestionFenAjouterBienLouableComboBox(this, this.daoBat);

			comboBoxIDBatBL = new JComboBox<>();
			panelGridCenter.add(comboBoxIDBatBL);
			comboBoxIDBatBL.addActionListener(gestionCombo);
			try {
				this.gestionCombo.chargerComboBoxIDBatBL();

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Champs hérités du bâtiment sélectionné (non éditables)
			textFieldAdresseBL = new JTextField();
			panelGridCenter.add(textFieldAdresseBL);
			textFieldAdresseBL.setColumns(10);
			textFieldAdresseBL.setEditable(false);

			textFieldCodePostalBL = new JTextField();
			panelGridCenter.add(textFieldCodePostalBL);
			textFieldCodePostalBL.setColumns(10);
			textFieldCodePostalBL.setEditable(false);

			textFieldVilleBL = new JTextField();
			panelGridCenter.add(textFieldVilleBL);
			textFieldVilleBL.setColumns(10);
			textFieldVilleBL.setEditable(false);

			textFieldDateConstruction = new JTextField();
			panelGridCenter.add(textFieldDateConstruction);
			textFieldDateConstruction.setColumns(10);
			textFieldDateConstruction.setEditable(false);

			// Initialisation des champs avec les valeurs du premier bâtiment de la combobox
			String idBat = (String) this.getComboBoxIDBatBL().getSelectedItem();
			Batiment firstBat;
			try {
				firstBat = this.daoBat.findById(idBat);
				textFieldAdresseBL.setText(firstBat.getAdresse());
				textFieldCodePostalBL.setText(firstBat.getCodePostal());
				textFieldVilleBL.setText(firstBat.getVille());
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				textFieldDateConstruction.setText(firstBat.getDateConstruction().format(formatter));
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}

		}
		// Cas 2 : Ajout depuis les informations d'un bâtiment (bâtiment prédéfini)
		if (this.bat != null) {
			// Champ identifiant du bien louable
			textFieldIdentifiantBL = new JTextField();
			panelGridCenter.add(textFieldIdentifiantBL);
			textFieldIdentifiantBL.setColumns(10);

			// Champ ID bâtiment pré-rempli et non éditable
			textFieldIDBatBL = new JTextField();
			panelGridCenter.add(textFieldIDBatBL);
			textFieldIDBatBL.setColumns(10);
			textFieldIDBatBL.setText(this.bat.getIdBat());
			textFieldIDBatBL.setEditable(false);

			// Champs hérités du bâtiment prédéfini (non éditables)
			textFieldAdresseBL = new JTextField();
			panelGridCenter.add(textFieldAdresseBL);
			textFieldAdresseBL.setColumns(10);
			textFieldAdresseBL.setText(this.bat.getAdresse());
			textFieldAdresseBL.setEditable(false);

			textFieldCodePostalBL = new JTextField();
			panelGridCenter.add(textFieldCodePostalBL);
			textFieldCodePostalBL.setColumns(10);
			textFieldCodePostalBL.setText(this.bat.getCodePostal());
			textFieldCodePostalBL.setEditable(false);

			textFieldVilleBL = new JTextField();
			panelGridCenter.add(textFieldVilleBL);
			textFieldVilleBL.setColumns(10);
			textFieldVilleBL.setText(this.bat.getVille());
			textFieldVilleBL.setEditable(false);

			textFieldDateConstruction = new JTextField();
			panelGridCenter.add(textFieldDateConstruction);
			textFieldDateConstruction.setColumns(10);
			textFieldDateConstruction.setText(this.bat.getDateConstruction().toString());
			textFieldDateConstruction.setEditable(false);
		}

		// Panel contenant les boutons radio pour le type de bien
		JPanel panelRadioButtons = new JPanel();
		panelGridCenter.add(panelRadioButtons);
		panelRadioButtons.setLayout(new GridLayout(0, 2, 0, 0));

		// Boutons radio pour choisir entre logement et garage
		rdbtnLogement = new JRadioButton("Logement");
		rdbtnLogement.setActionCommand("Logement");
		rdbtnLogement.setSelected(true); // Sélectionné par défaut
		panelRadioButtons.add(rdbtnLogement);

		rdbtnGarage = new JRadioButton("Garage");
		rdbtnGarage.setActionCommand("Garage");
		panelRadioButtons.add(rdbtnGarage);

		// Groupe de boutons radio (un seul sélectionnable)
		groupBienLouable = new ButtonGroup();
		groupBienLouable.add(rdbtnLogement);
		groupBienLouable.add(rdbtnGarage);
		groupBienLouable.isSelected(rdbtnGarage.getModel());

		// Format pour les nombres décimaux (surface)
		NumberFormat surfaceFormat = NumberFormat.getNumberInstance();
		surfaceFormat.setMaximumFractionDigits(2);

		// Champs spécifiques au bien louable
		// Champ surface habitable
		textFieldSurface = new JTextField();
		panelGridCenter.add(textFieldSurface);
		textFieldSurface.setColumns(10);

		// Spinner nombre de pièces
		spinnerNbPieces = new JSpinner();
		panelGridCenter.add(spinnerNbPieces);

		// Champ pourcentage d'entretien des parties communes
		textFieldPourcentagePartieC = new JTextField();
		panelGridCenter.add(textFieldPourcentagePartieC);

		// Champ pourcentage des ordures ménagères
		textFieldPourcentageOrdures = new JTextField();
		panelGridCenter.add(textFieldPourcentageOrdures);

		// Champ numéro fiscal
		textFieldNumeroFiscalBL = new JTextField();
		panelGridCenter.add(textFieldNumeroFiscalBL);
		textFieldNumeroFiscalBL.setColumns(10);

		// Champ loyer mensuel
		textFieldLoyer = new JTextField();
		panelGridCenter.add(textFieldLoyer);
		textFieldLoyer.setColumns(10);

		// Champ provision pour charges
		textFieldProvisions = new JTextField();
		panelGridCenter.add(textFieldProvisions);
		textFieldProvisions.setColumns(10);

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Bouton pour valider l'ajout du bien louable
		JButton btnValiderAjoutBL = new JButton("Valider");
		btnValiderAjoutBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutBL);
		btnValiderAjoutBL.addActionListener(this.gestionClic);

		// Bouton pour annuler et fermer la fenêtre
		JButton btnAnnulerAjoutBL = new JButton("Annuler");
		btnAnnulerAjoutBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutBL);
		btnAnnulerAjoutBL.addActionListener(this.gestionClic);

		// Note explicative en bas de la fenêtre
		JLabel lblAsterisque = new JLabel("* : Parties Communes");
		contentPane.add(lblAsterisque, BorderLayout.SOUTH);
	}

	/**
	 * Crée et retourne un nouvel objet BienLouable à partir des données saisies
	 * dans le formulaire. Cette méthode gère deux cas : - Si aucun bâtiment n'est
	 * prédéfini (bat == null), récupère le bâtiment sélectionné dans la combobox -
	 * Si un bâtiment est prédéfini, utilise directement ses informations
	 * 
	 * La méthode parse les valeurs des champs (loyer, surface, pourcentages) et
	 * crée un objet BienLouable complet.
	 * 
	 * @return le nouveau bien louable créé avec toutes les données saisies
	 * @throws SQLException si une erreur survient lors de la récupération du
	 *                      bâtiment depuis la base de données
	 */
	public BienLouable getNvBienLouable() throws SQLException {
		BienLouable bl = null;
		if (this.bat == null) {
			Batiment selectBatimentCombo = this.daoBat.findById((String) this.comboBoxIDBatBL.getSelectedItem());

			bl = new BienLouable(this.textFieldIdentifiantBL.getText(), selectBatimentCombo.getIdBat(),
					selectBatimentCombo.getAdresse(), selectBatimentCombo.getCodePostal(),
					selectBatimentCombo.getVille(), Float.parseFloat(this.textFieldLoyer.getText()),
					Float.parseFloat(this.textFieldProvisions.getText()),
					this.groupBienLouable.getSelection().getActionCommand(), selectBatimentCombo.getDateConstruction(),
					Float.parseFloat(this.textFieldSurface.getText()),
					((Number) this.spinnerNbPieces.getValue()).intValue(),
					Double.parseDouble(this.textFieldPourcentagePartieC.getText()), // CHANGÉ ICI
					Double.parseDouble(this.textFieldPourcentageOrdures.getText()), // CHANGÉ ICI
					this.textFieldNumeroFiscalBL.getText(), null);
		} else {
			bl = new BienLouable(this.textFieldIdentifiantBL.getText(), this.bat.getIdBat(), this.bat.getAdresse(),
					this.bat.getCodePostal(), this.bat.getVille(), Float.parseFloat(this.textFieldLoyer.getText()),
					Float.parseFloat(this.textFieldProvisions.getText()),
					this.groupBienLouable.getSelection().getActionCommand(), this.bat.getDateConstruction(),
					Float.parseFloat(this.textFieldSurface.getText()),
					((Number) this.spinnerNbPieces.getValue()).intValue(),
					Double.parseDouble(this.textFieldPourcentagePartieC.getText()), // CHANGÉ ICI
					Double.parseDouble(this.textFieldPourcentageOrdures.getText()), // CHANGÉ ICI
					this.textFieldNumeroFiscalBL.getText(), null);
		}
		return bl;
	}

	/**
	 * Retourne la fenêtre parente informations bâtiment
	 * 
	 * @return la fenêtre FenBatimentInformations ou null si non définie
	 */
	public FenBatimentInformations getfBI() {
		return fBI;
	}

	/**
	 * Retourne la fenêtre parente table des biens louables
	 * 
	 * @return la fenêtre FenBienLouable ou null si non définie
	 */
	public FenBienLouable getfBL() {
		return fBL;
	}

	/**
	 * Retourne le bâtiment associé au bien louable
	 * 
	 * @return le bâtiment ou null si sélection via combobox
	 */
	public Batiment getBat() {
		return bat;
	}

	/**
	 * Retourne la combobox de sélection du bâtiment
	 * 
	 * @return la combobox des bâtiments
	 */
	public JComboBox<String> getComboBoxIDBatBL() {
		return comboBoxIDBatBL;
	}

	/**
	 * Modifie le texte du champ adresse (utilisé lors de la sélection d'un bâtiment
	 * dans la combobox)
	 * 
	 * @param adresseBL la nouvelle adresse à afficher
	 */
	public void setTextFieldAdresseBL(String adresseBL) {
		this.textFieldAdresseBL.setText(adresseBL);
	}

	/**
	 * Modifie le texte du champ code postal (utilisé lors de la sélection d'un
	 * bâtiment dans la combobox)
	 * 
	 * @param codePostalBL le nouveau code postal à afficher
	 */
	public void setTextFieldCodePostalBL(String codePostalBL) {
		this.textFieldCodePostalBL.setText(codePostalBL);
	}

	/**
	 * Modifie le texte du champ ville (utilisé lors de la sélection d'un bâtiment
	 * dans la combobox)
	 * 
	 * @param villeBL la nouvelle ville à afficher
	 */
	public void setTextFieldVilleBL(String villeBL) {
		this.textFieldVilleBL.setText(villeBL);

	}

	/**
	 * Modifie le texte du champ date de construction (utilisé lors de la sélection
	 * d'un bâtiment dans la combobox)
	 * 
	 * @param dateConstruction la nouvelle date de construction à afficher
	 */
	public void setTextFieldDateConstruction(String dateConstruction) {
		this.textFieldDateConstruction.setText(dateConstruction);
	}

	/**
	 * Retourne le champ de l'identifiant du bien louable
	 * 
	 * @return le champ de texte de l'identifiant
	 */
	public JTextField getTextFieldIdentifiantBL() {
		return textFieldIdentifiantBL;
	}

	/**
	 * Retourne le champ de l'ID du bâtiment (cas bâtiment prédéfini)
	 * 
	 * @return le champ de texte de l'ID bâtiment
	 */
	public JTextField getTextFieldIDBatBL() {
		return textFieldIDBatBL;
	}

	/**
	 * Retourne le champ de l'adresse
	 * 
	 * @return le champ de texte de l'adresse
	 */
	public JTextField getTextFieldAdresseBL() {
		return textFieldAdresseBL;
	}

	/**
	 * Retourne le champ de la date de construction
	 * 
	 * @return le champ de texte de la date de construction
	 */
	public JTextField getTextFieldDateConstruction() {
		return textFieldDateConstruction;
	}

	/**
	 * Retourne le champ du pourcentage d'entretien des parties communes
	 * 
	 * @return le champ de texte du pourcentage PC
	 */
	public JTextField getTextFieldPourcentageEntretienPCBL() {
		return textFieldPourcentagePartieC;
	}

	/**
	 * Retourne le champ du pourcentage des ordures ménagères
	 * 
	 * @return le champ de texte du pourcentage ordures
	 */
	public JTextField getTextFieldPourcentageOrdureMenageresBL() {
		return textFieldPourcentageOrdures;
	}

	/**
	 * Retourne le champ du numéro fiscal
	 * 
	 * @return le champ de texte du numéro fiscal
	 */
	public JTextField getTextFieldNumeroFiscalBL() {
		return textFieldNumeroFiscalBL;
	}

	/**
	 * Retourne le champ du code postal
	 * 
	 * @return le champ de texte du code postal
	 */
	public JTextField getTextFieldCodePostalBL() {
		return textFieldCodePostalBL;
	}

	/**
	 * Retourne le champ de la ville
	 * 
	 * @return le champ de texte de la ville
	 */
	public JTextField getTextFieldVilleBL() {
		return textFieldVilleBL;
	}

	/**
	 * Retourne le spinner du nombre de pièces
	 * 
	 * @return le spinner du nombre de pièces
	 */
	public JSpinner getSpinnerNbPieces() {
		return spinnerNbPieces;
	}

	/**
	 * Retourne le champ du loyer mensuel
	 * 
	 * @return le champ de texte du loyer
	 */
	public JTextField getTextFieldLoyer() {
		return textFieldLoyer;
	}

	/**
	 * Retourne le champ de la provision pour charges
	 * 
	 * @return le champ de texte de la provision
	 */
	public JTextField getTextFieldProvision() {
		return textFieldProvisions;
	}

	/**
	 * Retourne le champ de la surface habitable
	 * 
	 * @return le champ de texte de la surface
	 */
	public JTextField getTextFieldSurface() {
		return textFieldSurface;
	}

	/**
	 * Modifie la fenêtre parente informations bâtiment
	 * 
	 * @param fBI la nouvelle fenêtre parente
	 */
	public void setfBI(FenBatimentInformations fBI) {
		this.fBI = fBI;
	}

}
