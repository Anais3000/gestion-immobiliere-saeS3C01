package vue.modifier;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controleur.Outils;
import controleur.modifier.GestionFenModifierOrganisme;
import modele.Organisme;
import vue.consulter_informations.FenOrganismeInformations;

/**
 * Fenêtre de modification des informations d'un organisme. Permet à
 * l'utilisateur de modifier les caractéristiques d'un organisme (nom,
 * spécialité, coordonnées, adresse, etc.) à travers une interface graphique.
 */
public class FenModifierOrganisme extends JFrame {

	private static final long serialVersionUID = 1L;

	// Composants d'affichage
	private JPanel contentPane; // Panel principal de la fenêtre
	private JTextField textFieldNumTelOrg; // Champ pour modifier le numéro de téléphone
	private JTextField textFieldNomOrg; // Champ pour modifier le nom
	private JTextField textFieldPrenomLoc; // Champ pour modifier le prénom (non utilisé dans ce formulaire)
	private JTextField textFieldNumSiretOrg; // Champ pour le numéro SIRET (non modifiable)
	private JTextField textFieldMailOrg; // Champ pour modifier l'adresse email
	private JTextField textFieldDateNaissance; // Champ pour modifier la date de naissance (non utilisé dans ce
												// formulaire)
	private JTextField textFieldVilleNaissanceLoc; // Champ pour modifier la ville de naissance (non utilisé dans ce
													// formulaire)
	private JTextField textFieldAdresseOrg; // Champ pour modifier l'adresse
	private JTextField textFieldCodePostalOrg; // Champ pour modifier le code postal
	private JTextField textFieldVilleOrg; // Champ pour modifier la ville
	private JTextField textFieldSpecialite; // Champ pour modifier la spécialité

	// Contrôleur
	private transient GestionFenModifierOrganisme gestionClic; // Contrôleur des événements de la fenêtre

	// Données métier
	private transient Organisme organismeAvModif; // Organisme avant modification (sauvegarde de l'état initial)
	private FenOrganismeInformations fenOrgaInfoAncetre; // Fenêtre parente d'informations de l'organisme

	/**
	 * Constructeur de la fenêtre de modification d'un organisme. Initialise
	 * l'interface graphique avec un formulaire pré-rempli contenant les
	 * informations actuelles de l'organisme sélectionné.
	 * 
	 * @param organismeAvantModification l'organisme à modifier
	 * @param fenOrgaInfo                la fenêtre parente d'informations de
	 *                                   l'organisme
	 */
	public FenModifierOrganisme(Organisme organismeAvantModification, FenOrganismeInformations fenOrgaInfo) {
		this.fenOrgaInfoAncetre = fenOrgaInfo;
		this.organismeAvModif = organismeAvantModification;
		this.gestionClic = new GestionFenModifierOrganisme(this);

		setBounds(100, 100, 500, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel du haut : titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		// Titre de la fenêtre
		JLabel lblLabelTitreModifierLocataire = new JLabel("Modifier informations organisme");
		lblLabelTitreModifierLocataire.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreModifierLocataire.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitreModifierLocataire, BorderLayout.NORTH);

		// Panel central : formulaire de modification
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel des labels (côté gauche) avec GridLayout
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.WEST);
		panelGridWest.setLayout(new GridLayout(8, 1, 0, 10));

		JLabel lblNumSiretOrga = new JLabel("Num Siret :");
		lblNumSiretOrga.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblNumSiretOrga);

		JLabel lblNomOrg = new JLabel("Nom :");
		lblNomOrg.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblNomOrg);

		JLabel lblSpecialite = new JLabel("Spécialité :");
		lblSpecialite.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblSpecialite);

		JLabel lblAdresseOrg = new JLabel("Adresse :");
		lblAdresseOrg.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblAdresseOrg);

		JLabel lblCodePostalOrg = new JLabel("Code Postal :");
		lblCodePostalOrg.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblCodePostalOrg);

		JLabel lblVilleOrg = new JLabel("Ville :");
		lblVilleOrg.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblVilleOrg);

		JLabel lblNumTel = new JLabel("Numéro de tel : ");
		lblNumTel.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblNumTel);

		JLabel lblMailOrg = new JLabel("Mail :");
		lblMailOrg.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblMailOrg);

		// Panel des champs de saisie (côté droit) avec GridLayout
		JPanel panelGridCenter = new JPanel();
		panelBorder.add(panelGridCenter, BorderLayout.CENTER);
		panelGridCenter.setLayout(new GridLayout(8, 0, 0, 10));

		textFieldNumSiretOrg = new JTextField();
		textFieldNumSiretOrg.setEditable(false);
		panelGridCenter.add(textFieldNumSiretOrg);
		textFieldNumSiretOrg.setColumns(10);
		textFieldNumSiretOrg.setText(organismeAvantModification.getNumSIRET());

		textFieldNomOrg = new JTextField();
		panelGridCenter.add(textFieldNomOrg);
		textFieldNomOrg.setColumns(10);
		textFieldNomOrg.setText(organismeAvantModification.getNom());

		textFieldSpecialite = new JTextField();
		panelGridCenter.add(textFieldSpecialite);
		textFieldSpecialite.setColumns(10);
		textFieldSpecialite.setText(organismeAvantModification.getSpecialite());

		textFieldAdresseOrg = new JTextField();
		panelGridCenter.add(textFieldAdresseOrg);
		textFieldAdresseOrg.setColumns(10);
		textFieldAdresseOrg.setText(organismeAvantModification.getAdresse());

		textFieldCodePostalOrg = new JTextField();
		panelGridCenter.add(textFieldCodePostalOrg);
		textFieldCodePostalOrg.setColumns(10);
		textFieldCodePostalOrg.setText(organismeAvantModification.getCodePostal());

		textFieldVilleOrg = new JTextField();
		panelGridCenter.add(textFieldVilleOrg);
		textFieldVilleOrg.setColumns(10);
		textFieldVilleOrg.setText(organismeAvantModification.getVille());

		textFieldNumTelOrg = new JTextField();
		panelGridCenter.add(textFieldNumTelOrg);
		textFieldNumTelOrg.setColumns(10);
		textFieldNumTelOrg.setText(organismeAvantModification.getNumTel());

		textFieldMailOrg = new JTextField();
		panelGridCenter.add(textFieldMailOrg);
		textFieldMailOrg.setColumns(10);
		textFieldMailOrg.setText(organismeAvantModification.getMail());

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
	 * Récupère la fenêtre parente d'informations de l'organisme.
	 * 
	 * @return la fenêtre parente
	 */
	public FenOrganismeInformations getFenOrganismeInformations() {
		return this.fenOrgaInfoAncetre;
	}

	/**
	 * Récupère l'organisme avec les modifications saisies par l'utilisateur. Crée
	 * un nouveau organisme avec les valeurs modifiées des champs de saisie.
	 * 
	 * @return l'organisme modifié
	 */
	public Organisme getOrganismeModifie() {
		return new Organisme(this.organismeAvModif.getNumSIRET(), this.textFieldNomOrg.getText(),
				this.textFieldAdresseOrg.getText(), this.textFieldVilleOrg.getText(),
				this.textFieldCodePostalOrg.getText(), this.textFieldMailOrg.getText(),
				this.textFieldNumTelOrg.getText(), this.textFieldSpecialite.getText());
	}

	/**
	 * Récupère l'organisme avant modification (état initial).
	 * 
	 * @return l'organisme avant modification
	 */
	public Organisme getOrganismeAvantModification() {
		return this.organismeAvModif;
	}

	/**
	 * Récupère le champ de texte du numéro de téléphone.
	 * 
	 * @return le champ de texte du numéro de téléphone
	 */
	public JTextField getTextFieldNumTelOrg() {
		return textFieldNumTelOrg;
	}

	/**
	 * Récupère le champ de texte du nom.
	 * 
	 * @return le champ de texte du nom
	 */
	public JTextField getTextFieldNomOrg() {
		return textFieldNomOrg;
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
	 * Récupère le champ de texte du numéro SIRET.
	 * 
	 * @return le champ de texte du numéro SIRET
	 */
	public JTextField getTextFieldNumSiretOrg() {
		return textFieldNumSiretOrg;
	}

	/**
	 * Récupère le champ de texte de l'adresse email.
	 * 
	 * @return le champ de texte de l'adresse email
	 */
	public JTextField getTextFieldMailOrg() {
		return textFieldMailOrg;
	}

	/**
	 * Récupère le champ de texte de la date de naissance.
	 * 
	 * @return le champ de texte de la date de naissance
	 */
	public JTextField getTextFieldDateNaissance() {
		return textFieldDateNaissance;
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
	public JTextField getTextFieldAdresseOrg() {
		return textFieldAdresseOrg;
	}

	/**
	 * Récupère le champ de texte du code postal.
	 * 
	 * @return le champ de texte du code postal
	 */
	public JTextField getTextFieldCodePostalOrg() {
		return textFieldCodePostalOrg;
	}

	/**
	 * Récupère le champ de texte de la ville.
	 * 
	 * @return le champ de texte de la ville
	 */
	public JTextField getTextFieldVilleOrg() {
		return textFieldVilleOrg;
	}

	/**
	 * Récupère le champ de texte de la spécialité.
	 * 
	 * @return le champ de texte de la spécialité
	 */
	public JTextField getTextFieldSpecialite() {
		return textFieldSpecialite;
	}

}
