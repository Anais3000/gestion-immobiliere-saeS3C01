package vue.ajouter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controleur.Outils;
import controleur.ajouter.GestionFenAjouterReleveCompteur;

public class FenAjouterReleveCompteur extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JTextField textFieldDateReleve; // Champ pour la date du relevé (initialisé à la date du jour)
	private JTextField textFieldIndex; // Champ pour l'index relevé sur le compteur
	private JTextField textFieldPrixUnite; // Champ pour le prix unitaire (par unité consommée)
	private JTextField textFieldPartieFixe; // Champ pour la partie fixe de la facture
	private JRadioButton rdbtnNon; // Bouton radio "Non" pour le changement de compteur
	private JRadioButton rdbtnOui; // Bouton radio "Oui" pour le changement de compteur
	private JComboBox<String> comboBoxCompteur; // ComboBox pour sélectionner le compteur concerné

	// Contrôleur
	private transient GestionFenAjouterReleveCompteur gestionClic; // Contrôleur pour gérer les événements de cette
																	// fenêtre

	// Variables de gestion
	private String batimentOuBien; // Indique si le relevé concerne un bâtiment ou un bien louable
	private JFrame fenAncetre; // La fenêtre parente qui sera mise à jour après l'ajout

	/**
	 * Construit la page Ajouter Relevé de Compteur qui permet d'enregistrer un
	 * nouveau relevé pour un compteur. Ce constructeur initialise les champs
	 * suivants : - La sélection du compteur concerné via une combobox - La date du
	 * relevé (initialisée à la date du jour) - L'index relevé sur le compteur - Le
	 * prix par unité consommée (eau/électricité) - La partie fixe de la facture -
	 * Une option pour indiquer si le compteur a été changé (permet de saisir un
	 * index inférieur au précédent)
	 *
	 * @param id             l'identifiant du bâtiment ou du bien louable concerné
	 * @param fen            la fenêtre parente qui sera mise à jour après l'ajout
	 * @param batimentOuBien indique si le relevé concerne un "batiment" ou un
	 *                       "bien"
	 * @throws SQLException si une erreur survient lors du chargement des données
	 */
	public FenAjouterReleveCompteur(String id, JFrame fen, String batimentOuBien) throws SQLException {
		// Affectation des variables
		this.fenAncetre = fen;
		this.batimentOuBien = batimentOuBien;

		// Panel racine
		setBounds(100, 100, 457, 568);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		JLabel lblLabelTitret = new JLabel("Ajouter un Relevé de Compteur");
		lblLabelTitret.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitret.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitret, BorderLayout.NORTH);

		// Panel corps
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel contenant les labels
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.WEST);
		panelGridWest.setLayout(new GridLayout(6, 1, 10, 50));

		JLabel lblCompteur = new JLabel("Compteur :");
		lblCompteur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblCompteur);

		JLabel lblDateReleve = new JLabel("Date du relevé : ");
		lblDateReleve.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblDateReleve);

		JLabel lblIndex = new JLabel("Index :");
		lblIndex.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblIndex);

		JLabel lblPrixUnite = new JLabel("Prix/unité :");
		lblPrixUnite.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblPrixUnite);

		JLabel lblPartieFixe = new JLabel("Partie fixe :");
		lblPartieFixe.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblPartieFixe);

		JLabel lblCompteurChange = new JLabel("Compteur changé* : ");
		lblCompteurChange.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblCompteurChange);

		// Panel contenant les champs de saisie
		JPanel panelGridCenter = new JPanel();
		panelBorder.add(panelGridCenter, BorderLayout.CENTER);
		panelGridCenter.setLayout(new GridLayout(6, 0, 10, 50));

		// ComboBox pour sélectionner le compteur
		comboBoxCompteur = new JComboBox<>();
		panelGridCenter.add(comboBoxCompteur);

		// Champ date du relevé avec valeur par défaut (date du jour)
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dateDuJour = LocalDate.now().format(formatter);

		textFieldDateReleve = new JTextField();
		textFieldDateReleve.setText(dateDuJour);
		panelGridCenter.add(textFieldDateReleve);
		textFieldDateReleve.setColumns(10);

		// Champ index du compteur
		textFieldIndex = new JTextField();
		panelGridCenter.add(textFieldIndex);
		textFieldIndex.setColumns(10);

		// Champ prix par unité
		textFieldPrixUnite = new JTextField();
		panelGridCenter.add(textFieldPrixUnite);
		textFieldPrixUnite.setColumns(10);

		// Champ partie fixe de la facture
		textFieldPartieFixe = new JTextField();
		panelGridCenter.add(textFieldPartieFixe);
		textFieldPartieFixe.setColumns(10);

		// Panel contenant les boutons radio pour le changement de compteur
		JPanel panelBoutons = new JPanel();
		FlowLayout flPanelBoutons = (FlowLayout) panelBoutons.getLayout();
		flPanelBoutons.setAlignment(FlowLayout.LEFT);
		panelGridCenter.add(panelBoutons);

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenAjouterReleveCompteur(this, id);

		// Bouton radio "Oui" pour indiquer un changement de compteur
		rdbtnOui = new JRadioButton("Oui");
		panelBoutons.add(rdbtnOui);

		// Bouton radio "Non" pour indiquer qu'il n'y a pas eu de changement de compteur
		rdbtnNon = new JRadioButton("Non");
		panelBoutons.add(rdbtnNon);

		// Groupe de boutons radio (un seul sélectionnable à la fois)
		ButtonGroup groupe = new ButtonGroup();
		groupe.add(rdbtnOui);
		groupe.add(rdbtnNon);

		// Sélection par défaut : pas de changement de compteur
		rdbtnNon.setSelected(true);

		// Panel boutons de validation
		JPanel panelBorderBas = new JPanel();
		panelBorder.add(panelBorderBas, BorderLayout.SOUTH);
		panelBorderBas.setLayout(new BorderLayout(0, 0));

		JPanel panelBoutonsBas = new JPanel();
		panelBorderBas.add(panelBoutonsBas);

		JButton btnValiderAjoutRC = new JButton("Valider");
		btnValiderAjoutRC.addActionListener(this.gestionClic);
		btnValiderAjoutRC.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBoutonsBas.add(btnValiderAjoutRC);

		JButton btnAnnulerAjoutRC = new JButton("Annuler");
		btnAnnulerAjoutRC.addActionListener(this.gestionClic);
		btnAnnulerAjoutRC.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBoutonsBas.add(btnAnnulerAjoutRC);

		JLabel lblRemarque = new JLabel("* Cocher pour saisir un index inférieur au précédent");
		panelBorderBas.add(lblRemarque, BorderLayout.SOUTH);

		this.gestionClic.ajoutListenerComboBoxCompteur();

	}

	public JComboBox<String> getComboBoxCompteur() {
		return this.comboBoxCompteur;
	}

	/**
	 * Retourne le champ de la date du relevé
	 * 
	 * @return le champ de texte de la date
	 */
	public JTextField getTextFieldDateReleve() {
		return textFieldDateReleve;
	}

	/**
	 * Retourne le champ de l'index du compteur
	 * 
	 * @return le champ de texte de l'index
	 */
	public JTextField getTextFieldIndex() {
		return textFieldIndex;
	}

	/**
	 * Retourne le champ du prix par unité
	 * 
	 * @return le champ de texte du prix unitaire
	 */
	public JTextField getTextFieldPrixUnite() {
		return textFieldPrixUnite;
	}

	/**
	 * Retourne le champ de la partie fixe de la facture
	 * 
	 * @return le champ de texte de la partie fixe
	 */
	public JTextField getTextFieldPartieFixe() {
		return textFieldPartieFixe;
	}

	/**
	 * Retourne la fenêtre ancêtre pour la mettre à jour après l'ajout
	 * 
	 * @return la fenêtre parente
	 */
	public JFrame getFenAncetre() {
		return fenAncetre;
	}

	/**
	 * Retourne le bouton radio "Non" (pas de changement de compteur)
	 * 
	 * @return le bouton radio Non
	 */
	public JRadioButton getRdbtnNon() {
		return rdbtnNon;
	}

	/**
	 * Modifie le bouton radio "Non"
	 * 
	 * @param rdbtnNon le nouveau bouton radio à utiliser
	 */
	public void setRdbtnNon(JRadioButton rdbtnNon) {
		this.rdbtnNon = rdbtnNon;
	}

	/**
	 * Retourne le bouton radio "Oui" (changement de compteur)
	 * 
	 * @return le bouton radio Oui
	 */
	public JRadioButton getRdbtnOui() {
		return rdbtnOui;
	}

	/**
	 * Modifie le bouton radio "Oui"
	 * 
	 * @param rdbtnOui le nouveau bouton radio à utiliser
	 */
	public void setRdbtnOui(JRadioButton rdbtnOui) {
		this.rdbtnOui = rdbtnOui;
	}

	/**
	 * Retourne le type d'entité concernée par le relevé ("batiment" ou "bien")
	 * 
	 * @return la chaîne indiquant le type d'entité
	 */
	public String getBatimentOuBien() {
		return batimentOuBien;
	}

	/**
	 * Modifie le type d'entité concernée par le relevé
	 * 
	 * @param batimentOuBien la nouvelle valeur ("batiment" ou "bien")
	 */
	public void setBatimentOuBien(String batimentOuBien) {
		this.batimentOuBien = batimentOuBien;
	}

}
