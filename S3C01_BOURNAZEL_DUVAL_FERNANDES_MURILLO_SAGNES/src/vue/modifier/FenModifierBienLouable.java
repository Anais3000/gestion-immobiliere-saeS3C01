package vue.modifier;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import controleur.modifier.GestionFenModifierBienLouable;
import modele.BienLouable;
import vue.consulter_informations.FenBienLouableInformations;

/**
 * Fenêtre de modification des informations d'un bien louable. Permet à
 * l'utilisateur de modifier les caractéristiques d'un bien immobilier (adresse,
 * surface, nombre de pièces, loyer, etc.) à travers une interface graphique.
 */
public class FenModifierBienLouable extends JFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel contentPane; // Panel principal de la fenêtre
	private JTextField textFieldIdentifiant; // Champ pour l'identifiant (non modifiable)
	private JTextField textFieldAdresse; // Champ pour modifier l'adresse
	private JTextField textFieldDateConstruction; // Champ pour modifier la date de construction
	private JTextField textFieldSurfaceHabitable; // Champ pour modifier la surface habitable
	private JTextField textFieldNombrePieces; // Champ pour modifier le nombre de pièces
	private JTextField textFieldPourcentagePC; // Champ pour modifier le pourcentage d'entretien des parties communes
	private JTextField textFieldOrduresMenageres; // Champ pour modifier le pourcentage d'ordures ménagères
	private JTextField textFieldNumeroFiscal; // Champ pour modifier le numéro fiscal
	private JTextField textFieldCodePostal; // Champ pour modifier le code postal
	private JTextField textFieldVille; // Champ pour modifier la ville
	private JTextField textFieldLoyer; // Champ pour modifier le loyer
	private JTextField textFieldProvision; // Champ pour modifier la provision pour charges

	// Contrôleur
	private transient GestionFenModifierBienLouable gestionClic; // Contrôleur des événements de la fenêtre

	// Variables de gestion
	private transient BienLouable bienAvModif; // Bien louable avant modification (sauvegarde de l'état initial)
	private FenBienLouableInformations fenBienLouableAncetre; // Fenêtre parente d'informations du bien louable

	/**
	 * Constructeur de la fenêtre de modification d'un bien louable. Initialise
	 * l'interface graphique avec un formulaire pré-rempli contenant les
	 * informations actuelles du bien louable sélectionné.
	 * 
	 * @param selectBien  le bien louable à modifier
	 * @param fenBienInfo la fenêtre parente d'informations du bien louable
	 */
	public FenModifierBienLouable(BienLouable selectBien, FenBienLouableInformations fenBienInfo) {
		this.bienAvModif = selectBien;
		this.fenBienLouableAncetre = fenBienInfo;

		gestionClic = new GestionFenModifierBienLouable(this);

		setBounds(100, 100, 500, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel du haut : titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		// Titre de la fenêtre
		JLabel lblLabelTitreModifierBien = new JLabel("Modifier informations bien louable");
		lblLabelTitreModifierBien.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreModifierBien.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitreModifierBien, BorderLayout.NORTH);

		// Panel central : formulaire de modification
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel des labels (côté gauche) avec GridLayout
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.WEST);
		panelGridWest.setLayout(new GridLayout(12, 1, 0, 10));

		JLabel lblIdentifiantLoc = new JLabel("Identifiant :");
		lblIdentifiantLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblIdentifiantLoc);

		JLabel lblAdresse = new JLabel("Adresse :");
		lblAdresse.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblAdresse);

		JLabel lblVille = new JLabel("Ville :");
		lblVille.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblVille);

		JLabel lblCodePostal = new JLabel("Code Postal :");
		lblCodePostal.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblCodePostal);

		JLabel lblDateConstruction = new JLabel("Date de construction : ");
		lblDateConstruction.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblDateConstruction);

		JLabel lblSurfaceHabitable = new JLabel("Surface habitable :");
		lblSurfaceHabitable.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblSurfaceHabitable);

		JLabel lblNombrePieces = new JLabel("Nombre de pièces :");
		lblNombrePieces.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblNombrePieces);

		JLabel lblPourcentagePC = new JLabel("Pourcentage d'entretien PC* : ");
		lblPourcentagePC.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblPourcentagePC);

		JLabel lblOrdureMenageres = new JLabel("Pourcentage ordures ménagères : ");
		lblOrdureMenageres.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblOrdureMenageres);

		JLabel lblNumeroFiscal = new JLabel("Numéro fiscal :");
		lblNumeroFiscal.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblNumeroFiscal);

		JLabel lblProvision = new JLabel("Provision pour charges :");
		lblProvision.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 14));
		panelGridWest.add(lblProvision);

		JLabel lblLoyer = new JLabel("Loyer :");
		lblLoyer.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelGridWest.add(lblLoyer);

		// Panel des champs de saisie (côté droit) avec GridLayout
		JPanel panelGridCenter = new JPanel();
		panelBorder.add(panelGridCenter, BorderLayout.CENTER);
		panelGridCenter.setLayout(new GridLayout(12, 0, 0, 10));

		textFieldIdentifiant = new JTextField();
		textFieldIdentifiant.setText(this.bienAvModif.getIdBien());
		textFieldIdentifiant.setEditable(false);
		panelGridCenter.add(textFieldIdentifiant);
		textFieldIdentifiant.setColumns(10);

		textFieldAdresse = new JTextField();
		textFieldAdresse.setText(this.bienAvModif.getAdresse());
		panelGridCenter.add(textFieldAdresse);
		textFieldAdresse.setColumns(10);

		textFieldVille = new JTextField();
		textFieldVille.setText(this.bienAvModif.getVille());
		panelGridCenter.add(textFieldVille);
		textFieldVille.setColumns(10);

		textFieldCodePostal = new JTextField();
		textFieldCodePostal.setText(this.bienAvModif.getCodePostal());

		panelGridCenter.add(textFieldCodePostal);
		textFieldCodePostal.setColumns(10);

		textFieldDateConstruction = new JTextField();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		textFieldDateConstruction.setText(this.bienAvModif.getDateConstruction().format(formatter));
		panelGridCenter.add(textFieldDateConstruction);
		textFieldDateConstruction.setColumns(10);

		textFieldSurfaceHabitable = new JTextField();
		textFieldSurfaceHabitable.setText(String.valueOf(this.bienAvModif.getSurfaceHabitable()));
		panelGridCenter.add(textFieldSurfaceHabitable);
		textFieldSurfaceHabitable.setColumns(10);

		textFieldNombrePieces = new JTextField();
		textFieldNombrePieces.setText(String.valueOf(this.bienAvModif.getNbPieces()));
		panelGridCenter.add(textFieldNombrePieces);
		textFieldNombrePieces.setColumns(10);

		textFieldPourcentagePC = new JTextField();
		textFieldPourcentagePC.setText(String.valueOf(this.bienAvModif.getPourcentageEntretienPartiesCommunes()));
		panelGridCenter.add(textFieldPourcentagePC);
		textFieldPourcentagePC.setColumns(10);

		textFieldOrduresMenageres = new JTextField();
		textFieldOrduresMenageres.setText(String.valueOf(this.bienAvModif.getPourcentageOrduresMenageres()));
		panelGridCenter.add(textFieldOrduresMenageres);
		textFieldOrduresMenageres.setColumns(10);

		textFieldNumeroFiscal = new JTextField();
		textFieldNumeroFiscal.setText(this.bienAvModif.getNumeroFiscal());
		panelGridCenter.add(textFieldNumeroFiscal);
		textFieldNumeroFiscal.setColumns(10);

		textFieldProvision = new JTextField();
		textFieldProvision.setText(String.valueOf(this.bienAvModif.getProvisionPourCharges()));
		panelGridCenter.add(textFieldProvision);
		textFieldProvision.setColumns(10);

		textFieldLoyer = new JTextField();
		textFieldLoyer.setText(String.valueOf(this.bienAvModif.getLoyer()));
		panelGridCenter.add(textFieldLoyer);
		textFieldLoyer.setColumns(10);

		// Panel du bas : boutons valider et annuler
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Bouton pour valider les modifications
		JButton btnValiderAjoutLoc = new JButton("Valider");
		btnValiderAjoutLoc.addActionListener(gestionClic);
		btnValiderAjoutLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutLoc);

		// Bouton pour annuler et fermer la fenêtre
		JButton btnAnnulerAjoutLoc = new JButton("Annuler");
		btnAnnulerAjoutLoc.addActionListener(gestionClic);
		btnAnnulerAjoutLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutLoc);

		// Label explicatif pour les parties communes
		JLabel lblPartiesCommunes = new JLabel("* : parties communes");
		lblPartiesCommunes.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 10));
		contentPane.add(lblPartiesCommunes, BorderLayout.SOUTH);
	}

	/**
	 * Récupère le bien louable avec les modifications saisies par l'utilisateur.
	 * Crée un nouveau bien louable avec les valeurs modifiées des champs de saisie.
	 * 
	 * @return le bien louable modifié
	 */
	public BienLouable getBienLouableModifie() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return new BienLouable(bienAvModif.getIdBien(), bienAvModif.getIdBat(), textFieldAdresse.getText(),
				textFieldCodePostal.getText(), textFieldVille.getText(), Float.valueOf(textFieldLoyer.getText()),
				Float.valueOf(textFieldProvision.getText()), bienAvModif.getTypeBien(),
				LocalDate.parse(textFieldDateConstruction.getText(), formatter),
				Float.valueOf(textFieldSurfaceHabitable.getText()), Integer.valueOf(textFieldNombrePieces.getText()),
				Double.valueOf(textFieldPourcentagePC.getText()), Double.valueOf(textFieldOrduresMenageres.getText()),
				textFieldNumeroFiscal.getText(), bienAvModif.getDerniereAnneeModifLoyer());
	}

	/**
	 * Récupère la fenêtre parente d'informations du bien louable.
	 * 
	 * @return la fenêtre parente
	 */
	public FenBienLouableInformations getFenBienLouableAncetre() {
		return fenBienLouableAncetre;
	}

	/**
	 * Récupère le bien louable avant modification (état initial).
	 * 
	 * @return le bien louable avant modification
	 */
	public BienLouable getBienAvModif() {
		return bienAvModif;
	}

	/**
	 * Modifie le bien louable avant modification.
	 * 
	 * @param bl le nouveau bien louable avant modification
	 */
	public void setBienAvModif(BienLouable bl) {
		this.bienAvModif = bl;
	}

	/**
	 * Récupère le champ de texte de l'identifiant.
	 * 
	 * @return le champ de texte de l'identifiant
	 */
	public JTextField getTextFieldIdentifiant() {
		return textFieldIdentifiant;
	}

	/**
	 * Récupère le champ de texte de l'adresse.
	 * 
	 * @return le champ de texte de l'adresse
	 */
	public JTextField getTextFieldAdresse() {
		return textFieldAdresse;
	}

	/**
	 * Récupère le champ de texte de la date de construction.
	 * 
	 * @return le champ de texte de la date de construction
	 */
	public JTextField getTextFieldDateConstruction() {
		return textFieldDateConstruction;
	}

	/**
	 * Récupère le champ de texte de la surface habitable.
	 * 
	 * @return le champ de texte de la surface habitable
	 */
	public JTextField getTextFieldSurfaceHabitable() {
		return textFieldSurfaceHabitable;
	}

	/**
	 * Récupère le champ de texte du nombre de pièces.
	 * 
	 * @return le champ de texte du nombre de pièces
	 */
	public JTextField getTextFieldNombrePieces() {
		return textFieldNombrePieces;
	}

	/**
	 * Récupère le champ de texte du pourcentage d'entretien des parties communes.
	 * 
	 * @return le champ de texte du pourcentage d'entretien des parties communes
	 */
	public JTextField getTextFieldPourcentagePC() {
		return textFieldPourcentagePC;
	}

	/**
	 * Récupère le champ de texte du pourcentage d'ordures ménagères.
	 * 
	 * @return le champ de texte du pourcentage d'ordures ménagères
	 */
	public JTextField getTextFieldOrduresMenageres() {
		return textFieldOrduresMenageres;
	}

	/**
	 * Récupère le champ de texte du numéro fiscal.
	 * 
	 * @return le champ de texte du numéro fiscal
	 */
	public JTextField getTextFieldNumeroFiscal() {
		return textFieldNumeroFiscal;
	}

	/**
	 * Récupère le champ de texte du code postal.
	 * 
	 * @return le champ de texte du code postal
	 */
	public JTextField getTextFieldCodePostal() {
		return textFieldCodePostal;
	}

	/**
	 * Récupère le champ de texte de la ville.
	 * 
	 * @return le champ de texte de la ville
	 */
	public JTextField getTextFieldVille() {
		return textFieldVille;
	}

	/**
	 * Récupère le champ de texte du loyer.
	 * 
	 * @return le champ de texte du loyer
	 */
	public JTextField getTextFieldLoyer() {
		return textFieldLoyer;
	}

	/**
	 * Récupère le champ de texte de la provision pour charges.
	 * 
	 * @return le champ de texte de la provision pour charges
	 */
	public JTextField getTextFieldProvision() {
		return textFieldProvision;
	}

}
