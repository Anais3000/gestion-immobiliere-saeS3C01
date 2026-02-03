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
import controleur.modifier.GestionFenModifierLocataires;
import modele.Locataire;
import vue.consulter_informations.FenLocataireInformations;

/**
 * Fenêtre de modification des informations d'un locataire. Permet à
 * l'utilisateur de modifier les caractéristiques d'un locataire (nom, prénom,
 * coordonnées, adresse, etc.) à travers une interface graphique.
 */
public class FenModifierLocataire extends JFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel contentPane; // Panel principal de la fenêtre
	private JTextField textFieldIdentifiantLoc; // Champ pour l'identifiant (non modifiable)
	private JTextField textFieldNomLoc; // Champ pour modifier le nom
	private JTextField textFieldPrenomLoc; // Champ pour modifier le prénom
	private JTextField textFieldTelLoc; // Champ pour modifier le numéro de téléphone
	private JTextField textFieldMailLoc; // Champ pour modifier l'adresse email
	private JTextField textFieldDateNaissanceLoc; // Champ pour modifier la date de naissance
	private JTextField textFieldVilleNaissanceLoc; // Champ pour modifier la ville de naissance
	private JTextField textFieldAdresseLoc; // Champ pour modifier l'adresse
	private JTextField textFieldCodePostal; // Champ pour modifier le code postal
	private JTextField textFieldVilleLoc; // Champ pour modifier la ville

	// Contrôleur
	private transient GestionFenModifierLocataires gestionClic; // Contrôleur des événements de la fenêtre

	// Variables de gestion
	private transient Locataire locataireAvModif; // Locataire avant modification (sauvegarde de l'état initial)
	private FenLocataireInformations fenLocInfoAncetre; // Fenêtre parente d'informations du locataire

	/**
	 * Constructeur de la fenêtre de modification d'un locataire. Initialise
	 * l'interface graphique avec un formulaire pré-rempli contenant les
	 * informations actuelles du locataire sélectionné.
	 * 
	 * @param locataireAvantModification le locataire à modifier
	 * @param fenLocInfo                 la fenêtre parente d'informations du
	 *                                   locataire
	 */
	public FenModifierLocataire(Locataire locataireAvantModification, FenLocataireInformations fenLocInfo) {
		this.fenLocInfoAncetre = fenLocInfo;
		this.locataireAvModif = locataireAvantModification;
		this.gestionClic = new GestionFenModifierLocataires(this);

		setBounds(100, 100, 500, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel du haut : titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		// Titre de la fenêtre
		JLabel lblLabelTitreModifierOrganisme = new JLabel("Modifier informations locataires");
		lblLabelTitreModifierOrganisme.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreModifierOrganisme.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitreModifierOrganisme, BorderLayout.NORTH);

		// Panel central : formulaire de modification
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel des labels (côté gauche) avec GridLayout
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.WEST);
		panelGridWest.setLayout(new GridLayout(10, 1, 0, 10));

		JLabel lblIdentifiantLoc = new JLabel("Identifiant :");
		lblIdentifiantLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblIdentifiantLoc);

		JLabel lblNomLoc = new JLabel("Nom :");
		lblNomLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblNomLoc);

		JLabel lblPrenomLoc = new JLabel("Prénom :");
		lblPrenomLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblPrenomLoc);

		JLabel lblNumeroLoc = new JLabel("Téléphone :");
		lblNumeroLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblNumeroLoc);

		JLabel lblMailLoc = new JLabel("Mail :");
		lblMailLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblMailLoc);

		JLabel lblMailDateNaissanceLoc = new JLabel("Date de naissance :");
		lblMailDateNaissanceLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblMailDateNaissanceLoc);

		JLabel lblMailVilleNaissanceLoc = new JLabel("Ville de naissance :");
		lblMailVilleNaissanceLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblMailVilleNaissanceLoc);

		JLabel lblAdresseLoc = new JLabel("Adresse :");
		lblAdresseLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblAdresseLoc);

		JLabel lblCodePostalLoc = new JLabel("Code Postal :");
		lblCodePostalLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblCodePostalLoc);

		JLabel lblVilleLoc = new JLabel("Ville :");
		lblVilleLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblVilleLoc);

		// Panel des champs de saisie (côté droit) avec GridLayout
		JPanel panelGridCenter = new JPanel();
		panelBorder.add(panelGridCenter, BorderLayout.CENTER);
		panelGridCenter.setLayout(new GridLayout(10, 0, 0, 10));

		textFieldIdentifiantLoc = new JTextField();
		textFieldIdentifiantLoc.setEditable(false);
		panelGridCenter.add(textFieldIdentifiantLoc);
		textFieldIdentifiantLoc.setColumns(10);
		textFieldIdentifiantLoc.setText(locataireAvModif.getIdLocataire());

		textFieldNomLoc = new JTextField();
		panelGridCenter.add(textFieldNomLoc);
		textFieldNomLoc.setColumns(10);
		textFieldNomLoc.setText(locataireAvModif.getNom());

		textFieldPrenomLoc = new JTextField();
		panelGridCenter.add(textFieldPrenomLoc);
		textFieldPrenomLoc.setColumns(10);
		textFieldPrenomLoc.setText(locataireAvModif.getPrenom());

		textFieldTelLoc = new JTextField();
		panelGridCenter.add(textFieldTelLoc);
		textFieldTelLoc.setColumns(10);
		textFieldTelLoc.setText(locataireAvModif.getNumTelephone());

		textFieldMailLoc = new JTextField();
		panelGridCenter.add(textFieldMailLoc);
		textFieldMailLoc.setColumns(10);
		textFieldMailLoc.setText(locataireAvModif.getMail());

		textFieldDateNaissanceLoc = new JTextField();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		textFieldDateNaissanceLoc.setText(this.locataireAvModif.getDateNaissance().format(formatter));
		panelGridCenter.add(textFieldDateNaissanceLoc);
		textFieldDateNaissanceLoc.setColumns(10);

		textFieldVilleNaissanceLoc = new JTextField();
		panelGridCenter.add(textFieldVilleNaissanceLoc);
		textFieldVilleNaissanceLoc.setColumns(10);
		textFieldVilleNaissanceLoc.setText(locataireAvModif.getVilleNaissance());

		textFieldAdresseLoc = new JTextField();
		panelGridCenter.add(textFieldAdresseLoc);
		textFieldAdresseLoc.setColumns(10);
		textFieldAdresseLoc.setText(locataireAvModif.getAdresse());

		textFieldCodePostal = new JTextField();
		panelGridCenter.add(textFieldCodePostal);
		textFieldCodePostal.setColumns(10);
		textFieldCodePostal.setText(locataireAvModif.getCodePostal());

		textFieldVilleLoc = new JTextField();
		panelGridCenter.add(textFieldVilleLoc);
		textFieldVilleLoc.setColumns(10);
		textFieldVilleLoc.setText(locataireAvModif.getVille());

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
	}

	/**
	 * Récupère la fenêtre parente d'informations du locataire.
	 * 
	 * @return la fenêtre parente
	 */
	public FenLocataireInformations getFenLocInfoAncetre() {
		return fenLocInfoAncetre;
	}

	/**
	 * Récupère le locataire avec les modifications saisies par l'utilisateur. Crée
	 * un nouveau locataire avec les valeurs modifiées des champs de saisie.
	 * 
	 * @return le locataire modifié
	 */
	public Locataire getLocataireModifie() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		return new Locataire(locataireAvModif.getIdLocataire(), textFieldNomLoc.getText(), textFieldPrenomLoc.getText(),
				textFieldTelLoc.getText(), textFieldMailLoc.getText(),
				LocalDate.parse(textFieldDateNaissanceLoc.getText(), formatter), textFieldVilleNaissanceLoc.getText(),
				textFieldAdresseLoc.getText(), textFieldCodePostal.getText(), textFieldVilleLoc.getText());
	}

	/**
	 * Récupère le locataire avant modification (état initial).
	 * 
	 * @return le locataire avant modification
	 */
	public Locataire getLocataireAvantModification() {
		return this.locataireAvModif;
	}

	/**
	 * Récupère le champ de texte de l'identifiant.
	 * 
	 * @return le champ de texte de l'identifiant
	 */
	public JTextField getTextFieldIdentifiantLoc() {
		return textFieldIdentifiantLoc;
	}

	/**
	 * Récupère le champ de texte du nom.
	 * 
	 * @return le champ de texte du nom
	 */
	public JTextField getTextFieldNomLoc() {
		return textFieldNomLoc;
	}

	/**
	 * Récupère le champ de texte du prénom.
	 * 
	 * @return le champ de texte du prénom
	 */
	public JTextField getTextFieldPrenomLoc() {
		return textFieldPrenomLoc;
	}

	/**
	 * Récupère le champ de texte du numéro de téléphone.
	 * 
	 * @return le champ de texte du numéro de téléphone
	 */
	public JTextField getTextFieldTelLoc() {
		return textFieldTelLoc;
	}

	/**
	 * Récupère le champ de texte de l'adresse email.
	 * 
	 * @return le champ de texte de l'adresse email
	 */
	public JTextField getTextFieldMailLoc() {
		return textFieldMailLoc;
	}

	/**
	 * Récupère le champ de texte de la date de naissance.
	 * 
	 * @return le champ de texte de la date de naissance
	 */
	public JTextField getTextFieldDateNaissanceLoc() {
		return textFieldDateNaissanceLoc;
	}

	/**
	 * Récupère le champ de texte de la ville de naissance.
	 * 
	 * @return le champ de texte de la ville de naissance
	 */
	public JTextField getTextFieldVilleNaissanceLoc() {
		return textFieldVilleNaissanceLoc;
	}

	/**
	 * Récupère le champ de texte de l'adresse.
	 * 
	 * @return le champ de texte de l'adresse
	 */
	public JTextField getTextFieldAdresseLoc() {
		return textFieldAdresseLoc;
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
	public JTextField getTextFieldVilleLoc() {
		return textFieldVilleLoc;
	}

}
