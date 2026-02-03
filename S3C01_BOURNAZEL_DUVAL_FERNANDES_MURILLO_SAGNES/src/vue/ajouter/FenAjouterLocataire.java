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
import controleur.ajouter.GestionFenAjouterLocataire;
import modele.Locataire;
import vue.tables.FenLocataire;

public class FenAjouterLocataire extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JTextField textFieldIdentifiantLoc; // Champ de saisie de l'identifiant du locataire
	private JTextField textFieldNomLoc; // Champ de saisie du nom
	private JTextField textFieldPrenomLoc; // Champ de saisie du prénom
	private JTextField textFieldTelLoc; // Champ de saisie du numéro de téléphone
	private JTextField textFieldMailLoc; // Champ de saisie de l'adresse email
	private JTextField textFieldDateNaissanceLoc; // Champ de saisie de la date de naissance
	private JTextField textFieldVilleNaissanceLoc; // Champ de saisie de la ville de naissance
	private JTextField textFieldAdresseLoc; // Champ de saisie de l'adresse actuelle
	private JTextField textFieldCodePostal; // Champ de saisie du code postal
	private JTextField textFieldVilleLoc; // Champ de saisie de la ville actuelle

	// Contrôleur
	private transient GestionFenAjouterLocataire gestionClic; // Contrôleur pour gérer les événements de cette fenêtre

	// Variables de gestion
	private FenLocataire fenLoc; // La fenêtre parente (table des locataires)

	/**
	 * Construit la page Ajouter Locataire qui permet d'ajouter un nouveau locataire
	 * dans le système. Ce constructeur initialise les champs à remplir suivants :
	 * L'identifiant du locataire Le nom et le prénom Le téléphone et l'email La
	 * date et la ville de naissance L'adresse complète (adresse, code postal,
	 * ville)
	 *
	 * @param fenLoc la fenêtre parente (table des locataires) qui sera mise à jour
	 *               après l'ajout
	 */
	public FenAjouterLocataire(FenLocataire fenLoc) {
		// Affectation des variables
		this.fenLoc = fenLoc;

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenAjouterLocataire(this);

		// Panel racine
		setBounds(100, 100, 500, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		JLabel lblLabelTitreAjouterLocataire = new JLabel("Ajouter un Locataire");
		lblLabelTitreAjouterLocataire.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreAjouterLocataire.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitreAjouterLocataire, BorderLayout.NORTH);

		// Panel corps (contient le formulaire et les boutons)
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel formulaire - Labels
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.WEST);
		panelGridWest.setLayout(new GridLayout(10, 1, 0, 10));

		// Création des labels pour chaque champ
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

		JLabel lblDateNaissanceLoc = new JLabel("Date de Naissance :");
		lblDateNaissanceLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblDateNaissanceLoc);

		JLabel lblVilleNaissanceLoc = new JLabel("Ville de Naissance :");
		lblVilleNaissanceLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblVilleNaissanceLoc);

		JLabel lblAdresseLoc = new JLabel("Adresse :");
		lblAdresseLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblAdresseLoc);

		JLabel lblCodePostalLoc = new JLabel("Code Postal :");
		lblCodePostalLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblCodePostalLoc);

		JLabel lblVilleLoc = new JLabel("Ville :");
		lblVilleLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblVilleLoc);

		// Panel formulaire - Champs de saisie
		JPanel panelGridCenter = new JPanel();
		panelBorder.add(panelGridCenter, BorderLayout.CENTER);
		panelGridCenter.setLayout(new GridLayout(10, 0, 0, 10));

		// Création des champs de saisie
		textFieldIdentifiantLoc = new JTextField();
		panelGridCenter.add(textFieldIdentifiantLoc);
		textFieldIdentifiantLoc.setColumns(10);

		textFieldNomLoc = new JTextField();
		panelGridCenter.add(textFieldNomLoc);
		textFieldNomLoc.setColumns(10);

		textFieldPrenomLoc = new JTextField();
		panelGridCenter.add(textFieldPrenomLoc);
		textFieldPrenomLoc.setColumns(10);

		textFieldTelLoc = new JTextField();
		panelGridCenter.add(textFieldTelLoc);
		textFieldTelLoc.setColumns(10);

		textFieldMailLoc = new JTextField();
		panelGridCenter.add(textFieldMailLoc);
		textFieldMailLoc.setColumns(10);

		// Champ date de naissance avec format par défaut
		textFieldDateNaissanceLoc = new JTextField();
		textFieldDateNaissanceLoc.setText("dd-mm-yyyy");
		panelGridCenter.add(textFieldDateNaissanceLoc);
		textFieldDateNaissanceLoc.setColumns(10);

		textFieldVilleNaissanceLoc = new JTextField();
		panelGridCenter.add(textFieldVilleNaissanceLoc);
		textFieldVilleNaissanceLoc.setColumns(10);

		textFieldAdresseLoc = new JTextField();
		panelGridCenter.add(textFieldAdresseLoc);
		textFieldAdresseLoc.setColumns(10);

		textFieldCodePostal = new JTextField();
		panelGridCenter.add(textFieldCodePostal);
		textFieldCodePostal.setColumns(10);

		textFieldVilleLoc = new JTextField();
		panelGridCenter.add(textFieldVilleLoc);
		textFieldVilleLoc.setColumns(10);

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Bouton pour valider l'ajout du locataire
		JButton btnValiderAjoutLoc = new JButton("Valider");
		btnValiderAjoutLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutLoc);
		btnValiderAjoutLoc.addActionListener(this.gestionClic);

		// Bouton pour annuler et fermer la fenêtre
		JButton btnAnnulerAjoutLoc = new JButton("Annuler");
		btnAnnulerAjoutLoc.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutLoc);
		btnAnnulerAjoutLoc.addActionListener(this.gestionClic);

	}

	/**
	 * Retourne la fenêtre ancêtre (table des locataires) pour la mettre à jour
	 * après l'ajout
	 * 
	 * @return la fenêtre parente
	 */
	public FenLocataire getFenLocataire() {
		return fenLoc;
	}

	/**
	 * Crée et retourne un nouvel objet Locataire avec les valeurs saisies dans le
	 * formulaire
	 * 
	 * @return le nouveau locataire créé avec toutes ses informations
	 */
	public Locataire getNouveauLocataire() {
		return new Locataire(textFieldIdentifiantLoc.getText(), textFieldNomLoc.getText(), textFieldPrenomLoc.getText(),
				textFieldTelLoc.getText(), textFieldMailLoc.getText(),
				Outils.parseDateField(textFieldDateNaissanceLoc.getText()), textFieldVilleNaissanceLoc.getText(),
				textFieldAdresseLoc.getText(), textFieldCodePostal.getText(), textFieldVilleLoc.getText());
	}

	/**
	 * Retourne le champ de l'identifiant du locataire
	 * 
	 * @return le champ de texte de l'identifiant
	 */
	public JTextField getTextFieldIdentifiantLoc() {
		return textFieldIdentifiantLoc;
	}

	/**
	 * Retourne le champ du nom du locataire
	 * 
	 * @return le champ de texte du nom
	 */
	public JTextField getTextFieldNomLoc() {
		return textFieldNomLoc;
	}

	/**
	 * Retourne le champ du prénom du locataire
	 * 
	 * @return le champ de texte du prénom
	 */
	public JTextField getTextFieldPrenomLoc() {
		return textFieldPrenomLoc;
	}

	/**
	 * Retourne le champ du numéro de téléphone du locataire
	 * 
	 * @return le champ de texte du téléphone
	 */
	public JTextField getTextFieldTelLoc() {
		return textFieldTelLoc;
	}

	/**
	 * Retourne le champ de l'adresse email du locataire
	 * 
	 * @return le champ de texte de l'email
	 */
	public JTextField getTextFieldMailLoc() {
		return textFieldMailLoc;
	}

	/**
	 * Retourne le champ de la date de naissance du locataire
	 * 
	 * @return le champ de texte de la date de naissance
	 */
	public JTextField getTextFieldDateNaissanceLoc() {
		return textFieldDateNaissanceLoc;
	}

	/**
	 * Retourne le champ de la ville de naissance du locataire
	 * 
	 * @return le champ de texte de la ville de naissance
	 */
	public JTextField getTextFieldVilleNaissanceLoc() {
		return textFieldVilleNaissanceLoc;
	}

	/**
	 * Retourne le champ de l'adresse actuelle du locataire
	 * 
	 * @return le champ de texte de l'adresse
	 */
	public JTextField getTextFieldAdresseLoc() {
		return textFieldAdresseLoc;
	}

	/**
	 * Retourne le champ du code postal du locataire
	 * 
	 * @return le champ de texte du code postal
	 */
	public JTextField getTextFieldCodePostal() {
		return textFieldCodePostal;
	}

	/**
	 * Retourne le champ de la ville actuelle du locataire
	 * 
	 * @return le champ de texte de la ville
	 */
	public JTextField getTextFieldVilleLoc() {
		return textFieldVilleLoc;
	}

}
