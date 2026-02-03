package vue.ajouter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controleur.ajouter.GestionChoisirAjouterGarant;
import vue.tables.FenGarant;

public class FenChoisirOuAjouterGarant extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JTextField textFieldIdGarant; // Champ de saisie de l'identifiant du garant
	private JTextField textFieldAdresse; // Champ de saisie de l'adresse du garant
	private JTextField textFieldMail; // Champ de saisie de l'email du garant
	private JTextField textFieldNumTel; // Champ de saisie du numéro de téléphone
	private JLabel lblIdGarant; // Label pour l'identifiant
	private JLabel lblAdresse; // Label pour l'adresse
	private JLabel lblMail; // Label pour l'email
	private JLabel lblNumTel; // Label pour le numéro de téléphone
	private JRadioButton rdbtnRechercherGarant; // Bouton radio pour le mode recherche
	private JRadioButton rdbtnAjouterGarant; // Bouton radio pour le mode ajout
	private JComboBox<String> comboBoxRechercheGarant; // ComboBox pour sélectionner un garant existant
	private List<JLabel> lLabelAjouter = new ArrayList<>(); // Liste des labels du mode ajout
	private List<JTextField> lJTextField = new ArrayList<>(); // Liste des champs du mode ajout

	// Contrôleur
	private transient GestionChoisirAjouterGarant gestionClic; // Contrôleur pour gérer les événements de cette fenêtre

	// Variables de gestion
	private FenAjouterBail fenAncetre; // La fenêtre parente FenAjouterBail
	private FenGarant fG; // La fenêtre parente FenGarant
	private boolean ajouterBail; // Indique si on est en mode ajout de bail (true) ou ajout direct (false)

	/**
	 * Construit la page Choisir ou Ajouter Garant permettant de sélectionner un
	 * garant existant ou d'en créer un nouveau Ce constructeur est utilisé lors de
	 * l'ajout d'un bail pour associer un garant L'utilisateur peut choisir entre :
	 * - Rechercher et sélectionner un garant existant dans la base - Ajouter un
	 * nouveau garant en renseignant son identifiant, adresse, email et numéro de
	 * téléphone
	 *
	 * @param fenAncetre la fenêtre parente FenAjouterBail qui sera mise à jour
	 *                   après la sélection/ajout
	 */
	public FenChoisirOuAjouterGarant(FenAjouterBail fenAncetre) {
		// Affectation des variables
		ajouterBail = true;
		this.fenAncetre = fenAncetre;

		// Construction de l'interface graphique
		creerLaFenetre();
	}

	/**
	 * Construit la page Ajouter Garant pour ajouter directement un nouveau garant
	 * depuis la table des garants Ce constructeur désactive automatiquement le mode
	 * recherche et active uniquement le mode ajout avec tous les champs de saisie
	 * prêts à être remplis
	 *
	 * @param fG la fenêtre parente FenGarant (table des garants) qui sera mise à
	 *           jour après l'ajout
	 */
	public FenChoisirOuAjouterGarant(FenGarant fG) {
		// Affectation des variables
		ajouterBail = false;
		this.fG = fG;

		// Construction de l'interface graphique
		creerLaFenetre();

		// Configuration du mode ajout uniquement
		rdbtnRechercherGarant.setEnabled(false);
		comboBoxRechercheGarant.setEnabled(false);
		rdbtnAjouterGarant.setSelected(true);
		textFieldIdGarant.setEnabled(true);
		textFieldAdresse.setEnabled(true);
		textFieldMail.setEnabled(true);
		textFieldNumTel.setEnabled(true);
		lblIdGarant.setEnabled(true);
		lblAdresse.setEnabled(true);
		lblMail.setEnabled(true);
		lblNumTel.setEnabled(true);
	}

	/**
	 * Crée et configure l'interface graphique de la fenêtre de choix/ajout de
	 * garant. La fenêtre propose deux modes : rechercher un garant existant ou en
	 * ajouter un nouveau.
	 */
	private void creerLaFenetre() {
		// Affectation des variables
		gestionClic = new GestionChoisirAjouterGarant(this);

		// Configuration de base de la fenêtre
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));

		ButtonGroup groupeGarant = new ButtonGroup();

		// Panel titre
		JLabel lblTitre = new JLabel("Choisir ou Ajouter un Garant");
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblTitre, BorderLayout.NORTH);

		// Panel corps (contient le formulaire et les boutons)
		JPanel panelGarant = new JPanel();
		getContentPane().add(panelGarant, BorderLayout.CENTER);
		panelGarant.setLayout(new GridLayout(2, 0, 0, 0));

		// Panel mode recherche
		JPanel panelRechercheGarant = new JPanel();
		panelGarant.add(panelRechercheGarant);

		rdbtnRechercherGarant = new JRadioButton("Rechercher Garant");
		groupeGarant.add(rdbtnRechercherGarant);
		rdbtnRechercherGarant.setSelected(true);
		rdbtnRechercherGarant.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnRechercherGarant.addActionListener(gestionClic);
		panelRechercheGarant.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelRechercheGarant.add(rdbtnRechercherGarant);

		comboBoxRechercheGarant = new JComboBox<>();
		comboBoxRechercheGarant.setModel(new DefaultComboBoxModel<>(new String[] { "" }));
		panelRechercheGarant.add(comboBoxRechercheGarant);
		comboBoxRechercheGarant.setPreferredSize(new java.awt.Dimension(292, 25));

		// Panel mode ajout
		JPanel panelAjouterGarant = new JPanel();
		panelGarant.add(panelAjouterGarant);

		rdbtnAjouterGarant = new JRadioButton("Ajouter Garant");
		groupeGarant.add(rdbtnAjouterGarant);
		rdbtnAjouterGarant.addActionListener(gestionClic);
		panelAjouterGarant.setLayout(new GridLayout(0, 3, 0, 0));
		panelAjouterGarant.add(rdbtnAjouterGarant);

		JPanel panelNouveauGarantLbl = new JPanel();
		panelAjouterGarant.add(panelNouveauGarantLbl);
		panelNouveauGarantLbl.setLayout(new GridLayout(0, 1, 0, 0));

		lblIdGarant = new JLabel("IdGarant : ");
		lblIdGarant.setHorizontalAlignment(SwingConstants.CENTER);
		panelNouveauGarantLbl.add(lblIdGarant);

		lblAdresse = new JLabel("Adresse :");
		lblAdresse.setHorizontalAlignment(SwingConstants.CENTER);
		panelNouveauGarantLbl.add(lblAdresse);

		lblMail = new JLabel("Mail :");
		lblMail.setHorizontalAlignment(SwingConstants.CENTER);
		panelNouveauGarantLbl.add(lblMail);

		lblNumTel = new JLabel("Numéro Téléphone :");
		lblNumTel.setHorizontalAlignment(SwingConstants.CENTER);
		panelNouveauGarantLbl.add(lblNumTel);

		lLabelAjouter.add(lblIdGarant);
		lLabelAjouter.add(lblAdresse);
		lLabelAjouter.add(lblMail);
		lLabelAjouter.add(lblNumTel);

		JPanel panelNouveauGarantTextField = new JPanel();
		panelAjouterGarant.add(panelNouveauGarantTextField);
		panelNouveauGarantTextField.setLayout(new GridLayout(0, 1, 0, 0));

		textFieldIdGarant = new JTextField();
		panelNouveauGarantTextField.add(textFieldIdGarant);
		textFieldIdGarant.setColumns(10);

		textFieldAdresse = new JTextField();
		panelNouveauGarantTextField.add(textFieldAdresse);
		textFieldAdresse.setColumns(10);

		textFieldMail = new JTextField();
		panelNouveauGarantTextField.add(textFieldMail);
		textFieldMail.setColumns(10);

		textFieldNumTel = new JTextField();
		panelNouveauGarantTextField.add(textFieldNumTel);
		textFieldNumTel.setColumns(10);

		lJTextField.add(textFieldIdGarant);
		lJTextField.add(textFieldAdresse);
		lJTextField.add(textFieldMail);
		lJTextField.add(textFieldNumTel);

		for (JLabel l : lLabelAjouter) {
			l.setEnabled(false);
		}
		for (JTextField tf : lJTextField) {
			tf.setEnabled(false);
		}

		// Panel boutons
		JPanel panelButton = new JPanel();
		getContentPane().add(panelButton, BorderLayout.SOUTH);

		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(this.gestionClic);
		panelButton.add(btnValider);

		JButton btnQuitter = new JButton("Annuler");
		btnQuitter.addActionListener(this.gestionClic);
		panelButton.add(btnQuitter);

		gestionClic.chargerGarants();
	}

	/**
	 * Retourne le bouton radio pour sélectionner le mode "Rechercher Garant" Ce
	 * bouton permet de basculer vers le mode recherche où l'utilisateur peut
	 * sélectionner un garant existant dans la ComboBox
	 * 
	 * @return le bouton radio du mode recherche
	 */
	public JRadioButton getRdbtnRechercherGarant() {
		return rdbtnRechercherGarant;
	}

	/**
	 * Retourne le bouton radio pour sélectionner le mode "Ajouter Garant" Ce bouton
	 * permet de basculer vers le mode ajout où l'utilisateur peut saisir les
	 * informations d'un nouveau garant
	 * 
	 * @return le bouton radio du mode ajout
	 */
	public JRadioButton getRdbtnAjouterGarant() {
		return rdbtnAjouterGarant;
	}

	/**
	 * Retourne la ComboBox contenant la liste des garants existants pour permettre
	 * la sélection d'un garant déjà enregistré
	 * 
	 * @return la ComboBox de recherche de garant
	 */
	public JComboBox<String> getComboBoxRechercheGarant() {
		return comboBoxRechercheGarant;
	}

	/**
	 * Retourne la liste des labels associés aux champs du mode ajout Cette liste
	 * est utilisée pour activer/désactiver les labels en fonction du mode
	 * sélectionné
	 * 
	 * @return la liste des labels du formulaire d'ajout
	 */
	public List<JLabel> getlLabelAjouter() {
		return lLabelAjouter;
	}

	/**
	 * Retourne la liste des champs de texte du mode ajout Cette liste est utilisée
	 * pour activer/désactiver les champs en fonction du mode sélectionné
	 * 
	 * @return la liste des champs de texte du formulaire d'ajout
	 */
	public List<JTextField> getlJTextField() {
		return lJTextField;
	}

	/**
	 * Retourne la fenêtre ancêtre FenAjouterBail depuis laquelle cette fenêtre a
	 * été ouverte pour pouvoir la mettre à jour après l'ajout ou la sélection d'un
	 * garant
	 * 
	 * @return la fenêtre parente FenAjouterBail, ou null si ouverte depuis
	 *         FenGarant
	 */
	public FenAjouterBail getFenAncetre() {
		return fenAncetre;
	}

	/**
	 * Retourne le champ de l'identifiant du garant pour pouvoir l'utiliser dans les
	 * gestions et récupérer ainsi la chaîne de caractères entrée par l'utilisateur
	 * 
	 * @return le champ de texte de l'identifiant
	 */
	public JTextField getTextFieldIdGarant() {
		return textFieldIdGarant;
	}

	/**
	 * Retourne le champ de l'adresse du garant pour pouvoir l'utiliser dans les
	 * gestions et récupérer ainsi la chaîne de caractères entrée par l'utilisateur
	 * 
	 * @return le champ de texte de l'adresse
	 */
	public JTextField getTextFieldAdresse() {
		return textFieldAdresse;
	}

	/**
	 * Retourne le champ de l'email du garant pour pouvoir l'utiliser dans les
	 * gestions et récupérer ainsi la chaîne de caractères entrée par l'utilisateur
	 * 
	 * @return le champ de texte de l'email
	 */
	public JTextField getTextFieldMail() {
		return textFieldMail;
	}

	/**
	 * Retourne le champ du numéro de téléphone du garant pour pouvoir l'utiliser
	 * dans les gestions et récupérer ainsi la chaîne de caractères entrée par
	 * l'utilisateur
	 * 
	 * @return le champ de texte du numéro de téléphone
	 */
	public JTextField getTextFieldNumTel() {
		return textFieldNumTel;
	}

	/**
	 * Indique si la fenêtre a été ouverte dans le contexte d'ajout d'un bail Permet
	 * de différencier l'origine de l'appel (FenAjouterBail ou FenGarant)
	 * 
	 * @return true si ouvert depuis FenAjouterBail, false si ouvert depuis
	 *         FenGarant
	 */
	public boolean isAjouterBail() {
		return ajouterBail;
	}

	/**
	 * Modifie l'indicateur du contexte d'utilisation de la fenêtre
	 * 
	 * @param ajouterBail true si utilisé pour ajouter un bail, false sinon
	 */
	public void setAjouterBail(boolean ajouterBail) {
		this.ajouterBail = ajouterBail;
	}

	/**
	 * Retourne la fenêtre ancêtre FenGarant depuis laquelle cette fenêtre a été
	 * ouverte pour pouvoir la mettre à jour après l'ajout d'un nouveau garant
	 * 
	 * @return la fenêtre parente FenGarant, ou null si ouverte depuis
	 *         FenAjouterBail
	 */
	public FenGarant getfG() {
		return fG;
	}

	/**
	 * Modifie la fenêtre ancêtre FenGarant
	 * 
	 * @param fG la nouvelle fenêtre parente
	 */
	public void setfG(FenGarant fG) {
		this.fG = fG;
	}

}
