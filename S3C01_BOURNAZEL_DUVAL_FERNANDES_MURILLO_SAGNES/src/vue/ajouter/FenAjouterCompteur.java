package vue.ajouter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controleur.Outils;
import controleur.ajouter.GestionFenAjouterCompteur;

public class FenAjouterCompteur extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JTextField textFieldIdCompteur; // Champ de saisie de l'identifiant du compteur
	private JTextField textFieldDateInstallation; // Champ de saisie de la date d'installation
	private JTextField textFieldIndexDep; // Champ de saisie de l'index de départ
	private JComboBox<String> comboBoxTypeCompteur; // ComboBox pour sélectionner le type (Eau, Électrique)

	// Contrôleur
	private transient GestionFenAjouterCompteur gestionClic; // Contrôleur pour gérer les événements de cette fenêtre

	// Variables de gestion
	private JFrame fenAncetre; // La fenêtre parente (informations du bâtiment ou du bien louable)
	private String batimentOuBien; // Indique si le compteur est associé à un bâtiment ou un bien louable

	/**
	 * Construit la page Ajouter Compteur qui permet d'ajouter un nouveau compteur
	 * pour un bâtiment ou un bien louable. Ce constructeur initialise les champs à
	 * remplir suivants : L'identifiant du compteur Le type du compteur (Eau ou
	 * Électrique) dans une combobox La date d'installation (initialisée à la date
	 * du jour) L'index de départ (initialisé à 0)
	 *
	 * @param id             l'identifiant du bâtiment ou du bien louable auquel le
	 *                       compteur est associé
	 * @param fen            la fenêtre parente qui sera mise à jour après l'ajout
	 * @param batimentOuBien chaîne indiquant si c'est un "batiment" ou un "bien"
	 */
	public FenAjouterCompteur(String id, JFrame fen, String batimentOuBien) {
		// Affectation des variables
		this.batimentOuBien = batimentOuBien;
		this.fenAncetre = fen;

		// Panel racine
		setBounds(100, 100, 450, 257);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		JLabel lblLabelTitre = new JLabel("Ajouter un Compteur");
		lblLabelTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitre.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitre, BorderLayout.NORTH);

		// Panel corps (contient le formulaire et les boutons)
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel formulaire - Labels
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.WEST);
		panelGridWest.setLayout(new GridLayout(4, 1, 20, 10));

		JLabel lblDateReleve = new JLabel("Identificateur Compteur : ");
		lblDateReleve.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblDateReleve);

		JLabel lblTypeCompteur = new JLabel("Type du Compteur :");
		lblTypeCompteur.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblTypeCompteur);

		JLabel lblDateInstallation = new JLabel("Date d'installation : ");
		lblDateInstallation.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblDateInstallation);

		JLabel lblIndexDep = new JLabel("Index de départ :");
		lblIndexDep.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblIndexDep);

		// Panel formulaire - Champs de saisie
		JPanel panelGridCenter = new JPanel();
		panelBorder.add(panelGridCenter, BorderLayout.CENTER);
		panelGridCenter.setLayout(new GridLayout(4, 0, 20, 10));

		textFieldIdCompteur = new JTextField();
		panelGridCenter.add(textFieldIdCompteur);
		textFieldIdCompteur.setColumns(10);

		comboBoxTypeCompteur = new JComboBox<>();
		comboBoxTypeCompteur.setModel(new DefaultComboBoxModel<>(new String[] { "Eau", "Électrique" }));
		panelGridCenter.add(comboBoxTypeCompteur);

		textFieldDateInstallation = new JTextField();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dateFormatee = LocalDate.now().format(formatter);
		textFieldDateInstallation.setText(dateFormatee);
		textFieldDateInstallation.setColumns(10);
		panelGridCenter.add(textFieldDateInstallation);

		textFieldIndexDep = new JTextField();
		panelGridCenter.add(textFieldIndexDep);
		textFieldIndexDep.setText("0");
		textFieldIndexDep.setColumns(10);

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenAjouterCompteur(this, id);

		JButton btnValiderAjoutC = new JButton("Valider");
		btnValiderAjoutC.addActionListener(this.gestionClic);
		btnValiderAjoutC.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutC);

		JButton btnAnnulerAjoutC = new JButton("Annuler");
		btnAnnulerAjoutC.addActionListener(this.gestionClic);
		btnAnnulerAjoutC.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutC);
	}

	/**
	 * Retourne le champ de l'identifiant du compteur pour l'utiliser dans les
	 * gestions
	 * 
	 * @return le champ de texte de l'identifiant
	 */
	public JTextField getTextFieldIdCompteur() {
		return textFieldIdCompteur;
	}

	/**
	 * Retourne la combobox du type de compteur pour récupérer le choix de
	 * l'utilisateur
	 * 
	 * @return la combobox du type de compteur
	 */
	public JComboBox<String> getComboBoxTypeCompteur() {
		return comboBoxTypeCompteur;
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
	 * Retourne le champ de la date d'installation du compteur
	 * 
	 * @return le champ de texte de la date d'installation
	 */
	public JTextField getTextFieldDateInstallation() {
		return textFieldDateInstallation;
	}

	/**
	 * Modifie le champ de la date d'installation
	 * 
	 * @param textFieldDateInstallation le nouveau champ de texte
	 */
	public void setTextFieldDateInstallation(JTextField textFieldDateInstallation) {
		this.textFieldDateInstallation = textFieldDateInstallation;
	}

	/**
	 * Retourne le champ de l'index de départ du compteur
	 * 
	 * @return le champ de texte de l'index de départ
	 */
	public JTextField getTextFieldIndexDep() {
		return textFieldIndexDep;
	}

	/**
	 * Modifie le champ de l'index de départ
	 * 
	 * @param textFieldIndexDep le nouveau champ de texte
	 */
	public void setTextFieldIndexDep(JTextField textFieldIndexDep) {
		this.textFieldIndexDep = textFieldIndexDep;
	}

	/**
	 * Retourne la chaîne indiquant si le compteur est associé à un bâtiment ou un
	 * bien louable
	 * 
	 * @return "batiment" ou "bien"
	 */
	public String getBatimentOuBien() {
		return batimentOuBien;
	}

	/**
	 * Modifie la chaîne indiquant si le compteur est associé à un bâtiment ou un
	 * bien louable
	 * 
	 * @param batimentOuBien "batiment" ou "bien"
	 */
	public void setBatimentOuBien(String batimentOuBien) {
		this.batimentOuBien = batimentOuBien;
	}

}
