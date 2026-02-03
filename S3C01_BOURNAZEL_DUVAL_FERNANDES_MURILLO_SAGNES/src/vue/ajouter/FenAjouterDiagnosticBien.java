package vue.ajouter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import controleur.Outils;
import controleur.ajouter.GestionFenAjouterDiagnosticBien;
import vue.consulter_informations.FenBienLouableInformations;

public class FenAjouterDiagnosticBien extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JTextField textDateFinDiagnostic; // Champ de saisie de la date de fin de validité
	private JTextField textFieldDateDebutDiagnostic; // Champ de saisie de la date de début de validité
	private JComboBox<String> comboBoxLibDiagnostic; // ComboBox pour sélectionner le type de diagnostic
	private JTextArea detailDiagnostic; // Zone de texte pour les détails du diagnostic

	// Contrôleur
	private transient GestionFenAjouterDiagnosticBien gestionClic; // Contrôleur pour gérer les événements de cette
																	// fenêtre

	// Variables de gestion
	private FenBienLouableInformations fenAncestor; // La fenêtre parente (informations du bien louable)

	/**
	 * Construit la page Ajouter Diagnostic Bien qui permet d'ajouter un nouveau
	 * diagnostic pour un bien louable. Ce constructeur initialise les champs à
	 * remplir suivants : Le type de diagnostic dans une combobox (DPE, amiante,
	 * électricité, etc.) La date de début de validité (initialisée à la date du
	 * jour) La date de fin de validité Les détails du diagnostic dans une zone de
	 * texte
	 *
	 * @param idBien l'identifiant du bien louable concerné par le diagnostic
	 * @param fen    la fenêtre parente (informations du bien) qui sera mise à jour
	 *               après l'ajout
	 */
	public FenAjouterDiagnosticBien(String idBien, FenBienLouableInformations fen) {

		// Affectation des variables
		this.fenAncestor = fen;

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

		JLabel lblLabelAjouterDiagnostic = new JLabel("Ajouter un Diagnostic");
		lblLabelAjouterDiagnostic.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelAjouterDiagnostic.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelAjouterDiagnostic, BorderLayout.NORTH);

		// Panel corps (contient le formulaire et les boutons)
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel formulaire avec GridBagLayout
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.CENTER);
		GridBagLayout gblPanelGridWest = new GridBagLayout();
		gblPanelGridWest.columnWidths = new int[] { 127, 30, 300, 0 };
		gblPanelGridWest.rowHeights = new int[] { 60, 60, 60, 120, 0 };
		gblPanelGridWest.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gblPanelGridWest.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 1.0 };
		panelGridWest.setLayout(gblPanelGridWest);

		// ComboBox pour sélectionner le type de diagnostic
		JLabel lblTypeDiagnostic = new JLabel("Type :");
		lblTypeDiagnostic.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblTypeDiagnostic = new GridBagConstraints();
		gbcLblTypeDiagnostic.anchor = GridBagConstraints.WEST;
		gbcLblTypeDiagnostic.fill = GridBagConstraints.VERTICAL;
		gbcLblTypeDiagnostic.insets = new Insets(0, 0, 5, 5);
		gbcLblTypeDiagnostic.gridx = 0;
		gbcLblTypeDiagnostic.gridy = 0;
		panelGridWest.add(lblTypeDiagnostic, gbcLblTypeDiagnostic);

		comboBoxLibDiagnostic = new JComboBox<>();
		comboBoxLibDiagnostic.setModel(new DefaultComboBoxModel<>(new String[] { "Certificat de superficie",
				"Exposition au plomb", "Diagnostic électricité", "Certificat de surface habitable",
				"Etat des risques et pollutions", "Etat des nuisances sonores et aériennes",
				"Déclaration de sinistres indemnisés", "DPE", "Dossier amiante parties privatives" }));
		GridBagConstraints gbcComboBoxLibDiagnostic = new GridBagConstraints();
		gbcComboBoxLibDiagnostic.insets = new Insets(0, 0, 5, 0);
		gbcComboBoxLibDiagnostic.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBoxLibDiagnostic.gridx = 2;
		gbcComboBoxLibDiagnostic.gridy = 0;
		panelGridWest.add(comboBoxLibDiagnostic, gbcComboBoxLibDiagnostic);

		// Champ date de début avec valeur par défaut (date du jour)
		JLabel lblDateDebutDiagnostic = new JLabel("Date de Début :");
		lblDateDebutDiagnostic.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblDateDebutDiagnostic = new GridBagConstraints();
		gbcLblDateDebutDiagnostic.anchor = GridBagConstraints.WEST;
		gbcLblDateDebutDiagnostic.fill = GridBagConstraints.VERTICAL;
		gbcLblDateDebutDiagnostic.insets = new Insets(0, 0, 5, 5);
		gbcLblDateDebutDiagnostic.gridx = 0;
		gbcLblDateDebutDiagnostic.gridy = 1;
		panelGridWest.add(lblDateDebutDiagnostic, gbcLblDateDebutDiagnostic);

		textFieldDateDebutDiagnostic = new JTextField();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dateFormatee = LocalDate.now().format(formatter);
		textFieldDateDebutDiagnostic.setText(dateFormatee);
		GridBagConstraints gbcTextFieldDateDebutDiagnostic = new GridBagConstraints();
		gbcTextFieldDateDebutDiagnostic.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldDateDebutDiagnostic.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldDateDebutDiagnostic.gridx = 2;
		gbcTextFieldDateDebutDiagnostic.gridy = 1;
		panelGridWest.add(textFieldDateDebutDiagnostic, gbcTextFieldDateDebutDiagnostic);
		textFieldDateDebutDiagnostic.setColumns(10);

		// Champ date de fin de validité
		JLabel lblDateFinDiagnostic = new JLabel("Date de Fin :");
		lblDateFinDiagnostic.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblDateFinDiagnostic = new GridBagConstraints();
		gbcLblDateFinDiagnostic.anchor = GridBagConstraints.WEST;
		gbcLblDateFinDiagnostic.fill = GridBagConstraints.VERTICAL;
		gbcLblDateFinDiagnostic.insets = new Insets(0, 0, 5, 5);
		gbcLblDateFinDiagnostic.gridx = 0;
		gbcLblDateFinDiagnostic.gridy = 2;
		panelGridWest.add(lblDateFinDiagnostic, gbcLblDateFinDiagnostic);

		textDateFinDiagnostic = new JTextField();
		GridBagConstraints gbcTextDateFinDiagnostic = new GridBagConstraints();
		gbcTextDateFinDiagnostic.insets = new Insets(0, 0, 5, 0);
		gbcTextDateFinDiagnostic.fill = GridBagConstraints.HORIZONTAL;
		gbcTextDateFinDiagnostic.gridx = 2;
		gbcTextDateFinDiagnostic.gridy = 2;
		panelGridWest.add(textDateFinDiagnostic, gbcTextDateFinDiagnostic);
		textDateFinDiagnostic.setColumns(10);

		// Zone de texte pour les détails du diagnostic
		JLabel lblDetailsDiagnostic = new JLabel("Détails :");
		lblDetailsDiagnostic.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblDetailsDiagnostic = new GridBagConstraints();
		gbcLblDetailsDiagnostic.anchor = GridBagConstraints.NORTHWEST;
		gbcLblDetailsDiagnostic.insets = new Insets(0, 0, 5, 5);
		gbcLblDetailsDiagnostic.gridx = 0;
		gbcLblDetailsDiagnostic.gridy = 3;
		panelGridWest.add(lblDetailsDiagnostic, gbcLblDetailsDiagnostic);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		GridBagConstraints gbcScrollPane = new GridBagConstraints();
		gbcScrollPane.insets = new Insets(0, 0, 5, 0);
		gbcScrollPane.fill = GridBagConstraints.BOTH;
		gbcScrollPane.gridx = 2;
		gbcScrollPane.gridy = 3;
		panelGridWest.add(scrollPane, gbcScrollPane);

		detailDiagnostic = new JTextArea();
		detailDiagnostic.setFont(new Font("Arial", Font.PLAIN, 13));
		detailDiagnostic.setBorder(new MatteBorder(1, 1, 1, 1, new Color(117, 117, 117)));
		scrollPane.setViewportView(detailDiagnostic);

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenAjouterDiagnosticBien(this, idBien);

		// Bouton pour valider l'ajout du diagnostic
		JButton btnValiderAjoutDiagnostic = new JButton("Valider");
		btnValiderAjoutDiagnostic.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutDiagnostic);
		btnValiderAjoutDiagnostic.addActionListener(this.gestionClic);

		// Bouton pour annuler et fermer la fenêtre
		JButton btnAnnulerAjoutDiagnostic = new JButton("Annuler");
		btnAnnulerAjoutDiagnostic.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutDiagnostic);
		btnAnnulerAjoutDiagnostic.addActionListener(this.gestionClic);

		pack();
	}

	/**
	 * Retourne le champ de la date de fin de validité du diagnostic
	 * 
	 * @return le champ de texte de la date de fin
	 */
	public JTextField getTextDateFinDiagnostic() {
		return textDateFinDiagnostic;
	}

	/**
	 * Retourne le champ de la date de début de validité du diagnostic
	 * 
	 * @return le champ de texte de la date de début
	 */
	public JTextField getTextFieldDateDebutDiagnostic() {
		return textFieldDateDebutDiagnostic;
	}

	/**
	 * Retourne la combobox du type de diagnostic pour récupérer le choix de
	 * l'utilisateur
	 * 
	 * @return la combobox du type de diagnostic
	 */
	public JComboBox<String> getComboBoxLibDiagnostic() {
		return comboBoxLibDiagnostic;
	}

	/**
	 * Retourne la zone de texte contenant les détails du diagnostic
	 * 
	 * @return la zone de texte des détails
	 */
	public JTextArea getDetailsDiagnostic() {
		return detailDiagnostic;
	}

	/**
	 * Retourne la fenêtre ancêtre pour la mettre à jour après l'ajout
	 * 
	 * @return la fenêtre parente
	 */
	public FenBienLouableInformations getFenAncetre() {
		return this.fenAncestor;
	}

}
