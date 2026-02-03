package vue.ajouter;

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
import controleur.ajouter.GestionFenAjouterOrganisme;
import modele.Organisme;
import vue.tables.FenOrganisme;

public class FenAjouterOrganisme extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JTextField textFieldNumSiretOrga; // Champ de saisie du numéro SIRET (14 chiffres)
	private JTextField textFieldNomOrga; // Champ de saisie du nom de l'organisme
	private JTextField textFieldAdresseOrga; // Champ de saisie de l'adresse
	private JTextField textFieldMailOrga; // Champ de saisie de l'email
	private JTextField textFieldSpecialiteOrga; // Champ de saisie de la spécialité
	private JTextField textFieldNumTelOrga; // Champ de saisie du numéro de téléphone
	private JTextField textFieldVilleOrga; // Champ de saisie de la ville
	private JTextField textFieldCodePostalOrga; // Champ de saisie du code postal

	// Contrôleur
	private transient GestionFenAjouterOrganisme gestionClic; // Contrôleur pour gérer les événements de cette fenêtre

	// Variables de gestion
	private FenOrganisme fenO; // La fenêtre parente (table des organismes)

	/**
	 * Construit la page Ajouter Organisme qui permet d'ajouter un nouvel organisme
	 * dans le système. Ce constructeur initialise les champs à remplir suivants :
	 * Le numéro SIRET de l'organisme Le nom de l'organisme L'adresse complète
	 * (adresse, ville, code postal) L'email et le numéro de téléphone La spécialité
	 * de l'organisme (assurance, banque, etc.)
	 *
	 * @param fenO la fenêtre parente (table des organismes) qui sera mise à jour
	 *             après l'ajout
	 */
	public FenAjouterOrganisme(FenOrganisme fenO) {
		// Affectation des variables
		this.fenO = fenO;

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenAjouterOrganisme(this);

		// Panel racine
		setBounds(100, 100, 450, 363);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		JLabel lblLabelTitreAjouterOrganisme = new JLabel("Ajouter un Organisme");
		lblLabelTitreAjouterOrganisme.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreAjouterOrganisme.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitreAjouterOrganisme, BorderLayout.NORTH);

		// Panel corps (contient le formulaire et les boutons)
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel formulaire
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.CENTER);
		panelGridWest.setLayout(new GridLayout(1, 2, 0, 0));

		// Panel Labels
		JPanel panelLbl = new JPanel();
		panelGridWest.add(panelLbl);
		panelLbl.setLayout(new GridLayout(8, 1, 0, 10));
		// Création des labels pour chaque champ
		JLabel lblNumSiretOrga = new JLabel("Num Siret :");
		lblNumSiretOrga.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelLbl.add(lblNumSiretOrga);

		JLabel lblNomOrga = new JLabel("Nom :");
		lblNomOrga.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelLbl.add(lblNomOrga);

		JLabel lblAdresseOrga = new JLabel("Adresse :");
		lblAdresseOrga.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelLbl.add(lblAdresseOrga);

		JLabel lblVilleOrga = new JLabel("Ville :");
		lblVilleOrga.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelLbl.add(lblVilleOrga);

		JLabel lblCodePostalOrga = new JLabel("Code Postal :");
		lblCodePostalOrga.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelLbl.add(lblCodePostalOrga);

		JLabel lblAdresseMail = new JLabel("Adresse Mail :");
		lblAdresseMail.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelLbl.add(lblAdresseMail);

		JLabel lblNumTelOrga = new JLabel("Numéro téléphone :");
		lblNumTelOrga.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelLbl.add(lblNumTelOrga);

		JLabel lblSpecialiteOrga = new JLabel("Spécialité :");
		lblSpecialiteOrga.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelLbl.add(lblSpecialiteOrga);

		// Panel Champs de saisie
		JPanel panelTextField = new JPanel();
		panelGridWest.add(panelTextField);
		panelTextField.setLayout(new GridLayout(8, 1, 0, 10));

		// Création des champs de saisie
		textFieldNumSiretOrga = new JTextField();
		textFieldNumSiretOrga.setColumns(10);
		panelTextField.add(textFieldNumSiretOrga);

		textFieldNomOrga = new JTextField();
		textFieldNomOrga.setColumns(10);
		panelTextField.add(textFieldNomOrga);

		textFieldAdresseOrga = new JTextField();
		textFieldAdresseOrga.setColumns(10);
		panelTextField.add(textFieldAdresseOrga);

		textFieldVilleOrga = new JTextField();
		panelTextField.add(textFieldVilleOrga);
		textFieldVilleOrga.setColumns(10);

		textFieldCodePostalOrga = new JTextField();
		panelTextField.add(textFieldCodePostalOrga);
		textFieldCodePostalOrga.setColumns(10);

		textFieldMailOrga = new JTextField();
		textFieldMailOrga.setColumns(10);
		panelTextField.add(textFieldMailOrga);

		textFieldNumTelOrga = new JTextField();
		textFieldNumTelOrga.setColumns(10);
		panelTextField.add(textFieldNumTelOrga);

		textFieldSpecialiteOrga = new JTextField();
		textFieldSpecialiteOrga.setColumns(10);
		panelTextField.add(textFieldSpecialiteOrga);

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Bouton pour valider l'ajout de l'organisme
		JButton btnValiderAjoutOrga = new JButton("Valider");
		btnValiderAjoutOrga.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutOrga);
		btnValiderAjoutOrga.addActionListener(this.gestionClic);

		// Bouton pour annuler et fermer la fenêtre
		JButton btnAnnulerAjoutOrga = new JButton("Annuler");
		btnAnnulerAjoutOrga.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutOrga);
		btnAnnulerAjoutOrga.addActionListener(this.gestionClic);
	}

	/**
	 * Crée et retourne un nouvel objet Organisme avec les valeurs saisies dans le
	 * formulaire
	 * 
	 * @return le nouvel organisme créé avec toutes ses informations
	 */
	public Organisme getNouvelOrganisme() {
		return new Organisme(textFieldNumSiretOrga.getText(), textFieldNomOrga.getText(),
				textFieldAdresseOrga.getText(), textFieldVilleOrga.getText(), textFieldCodePostalOrga.getText(),
				textFieldMailOrga.getText(), textFieldNumTelOrga.getText(), textFieldSpecialiteOrga.getText());
	}

	/**
	 * Retourne la fenêtre ancêtre (table des organismes) pour la mettre à jour
	 * après l'ajout
	 * 
	 * @return la fenêtre parente
	 */
	public FenOrganisme getFenOrganisme() {
		return fenO;
	}

	/**
	 * Retourne le champ du numéro SIRET de l'organisme
	 * 
	 * @return le champ de texte du numéro SIRET
	 */
	public JTextField getTextFieldNumSiretOrga() {
		return textFieldNumSiretOrga;
	}

	/**
	 * Retourne le champ du nom de l'organisme
	 * 
	 * @return le champ de texte du nom
	 */
	public JTextField getTextFieldNomOrga() {
		return textFieldNomOrga;
	}

	/**
	 * Retourne le champ de l'adresse de l'organisme
	 * 
	 * @return le champ de texte de l'adresse
	 */
	public JTextField getTextFieldAdresseOrga() {
		return textFieldAdresseOrga;
	}

	/**
	 * Retourne le champ de l'adresse email de l'organisme
	 * 
	 * @return le champ de texte de l'email
	 */
	public JTextField getTextFieldMailOrga() {
		return textFieldMailOrga;
	}

	/**
	 * Retourne le champ de la spécialité de l'organisme
	 * 
	 * @return le champ de texte de la spécialité
	 */
	public JTextField getTextFieldSpecialiteOrga() {
		return textFieldSpecialiteOrga;
	}

	/**
	 * Retourne le champ du numéro de téléphone de l'organisme
	 * 
	 * @return le champ de texte du téléphone
	 */
	public JTextField getTextFieldNumTelOrga() {
		return textFieldNumTelOrga;
	}

	/**
	 * Retourne le champ de la ville de l'organisme
	 * 
	 * @return le champ de texte de la ville
	 */
	public JTextField getTextFieldVilleOrga() {
		return textFieldVilleOrga;
	}

	/**
	 * Retourne le champ du code postal de l'organisme
	 * 
	 * @return le champ de texte du code postal
	 */
	public JTextField getTextFieldCodePostalOrga() {
		return textFieldCodePostalOrga;
	}

}
