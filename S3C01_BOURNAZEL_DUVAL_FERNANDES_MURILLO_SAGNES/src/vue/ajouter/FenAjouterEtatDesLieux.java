package vue.ajouter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import controleur.Outils;
import controleur.ajouter.GestionFenAjouterEtatDesLieux;
import modele.Louer;
import vue.consulter_informations.FenBienLouableInformations;

public class FenAjouterEtatDesLieux extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JTextField textFieldDateEDL; // Champ de saisie de la date de l'état des lieux
	private JRadioButton rdbtnEntreeEDL; // Bouton radio pour un état des lieux d'entrée
	private JRadioButton rdbtnSortieEDL; // Bouton radio pour un état des lieux de sortie
	private JTextArea textAreaDetails; // Zone de texte pour les détails de l'état des lieux

	// Contrôleur
	private transient GestionFenAjouterEtatDesLieux gestionClic; // Contrôleur pour gérer les événements de cette
																	// fenêtre

	// Variables de gestion
	private transient Louer location; // La location concernée par l'état des lieux
	private FenBienLouableInformations fenAncetre; // La fenêtre parente (informations du bien louable)

	/**
	 * Enumération pour définir le type d'état des lieux
	 */
	public enum Type {
		ENTREE, SORTIE
	}

	/**
	 * Construit la page Ajouter État Des Lieux qui permet d'ajouter un nouvel état
	 * des lieux pour une location. Ce constructeur initialise les champs à remplir
	 * suivants : Le type (état des lieux d'entrée ou de sortie) avec des boutons
	 * radio La date de l'état des lieux (initialisée à la date du jour) Les détails
	 * de l'état des lieux dans une zone de texte
	 * 
	 * Note : Pour un état des lieux de sortie, le bouton annuler est masqué et la
	 * fenêtre ne peut pas être fermée sans valider car c'est obligatoire pour
	 * terminer une location.
	 *
	 * @param fenAncetre la fenêtre parente (informations du bien) qui sera mise à
	 *                   jour après l'ajout
	 * @param location   la location concernée par cet état des lieux
	 * @param type       le type d'état des lieux (énumération ENTREE ou SORTIE)
	 */
	public FenAjouterEtatDesLieux(FenBienLouableInformations fenAncetre, Louer location, Type type) {

		// Affectation des variables
		this.location = location;
		this.fenAncetre = fenAncetre;

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenAjouterEtatDesLieux(this);

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

		JLabel lblLabelAjouterEtatDesLieux = new JLabel("Ajouter un État des Lieux");
		lblLabelAjouterEtatDesLieux.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelAjouterEtatDesLieux.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelAjouterEtatDesLieux, BorderLayout.NORTH);

		// Panel corps (contient le formulaire et les boutons)
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.CENTER);
		GridBagLayout gblPanelGridWest = new GridBagLayout();
		gblPanelGridWest.columnWidths = new int[] { 127, 30, 300 };
		gblPanelGridWest.rowHeights = new int[] { 60, 60, 120 };
		gblPanelGridWest.columnWeights = new double[] { 1.0, 0.0, 1.0 };
		gblPanelGridWest.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		panelGridWest.setLayout(gblPanelGridWest);

		JLabel lblTypeEDL = new JLabel("Type :");
		lblTypeEDL.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbcLblTypeEDL = new GridBagConstraints();
		gbcLblTypeEDL.anchor = GridBagConstraints.WEST;
		gbcLblTypeEDL.insets = new Insets(0, 0, 5, 5);
		gbcLblTypeEDL.gridx = 0;
		gbcLblTypeEDL.gridy = 0;
		panelGridWest.add(lblTypeEDL, gbcLblTypeEDL);

		// Panel contenant les boutons radio pour le type d'état des lieux
		JPanel panelTypeEDL = new JPanel();
		GridBagConstraints gbcPanelTypeEDL = new GridBagConstraints();
		gbcPanelTypeEDL.insets = new Insets(0, 0, 5, 0);
		gbcPanelTypeEDL.fill = GridBagConstraints.BOTH;
		gbcPanelTypeEDL.gridx = 2;
		gbcPanelTypeEDL.gridy = 0;
		panelGridWest.add(panelTypeEDL, gbcPanelTypeEDL);
		panelTypeEDL.setLayout(new GridLayout(0, 2, 0, 0));

		rdbtnEntreeEDL = new JRadioButton("Entrée");
		panelTypeEDL.add(rdbtnEntreeEDL);

		rdbtnSortieEDL = new JRadioButton("Sortie");
		panelTypeEDL.add(rdbtnSortieEDL);

		if (type == Type.ENTREE) {
			rdbtnSortieEDL.setEnabled(false);
			rdbtnEntreeEDL.setSelected(true);
			rdbtnEntreeEDL.setEnabled(false);
		} else {
			rdbtnEntreeEDL.setEnabled(false);
			rdbtnSortieEDL.setSelected(true);
			rdbtnSortieEDL.setEnabled(false);
		}

		// Champ date avec valeur par défaut (date du jour)
		JLabel lblDateEDL = new JLabel("Date :");
		lblDateEDL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblDateEDL = new GridBagConstraints();
		gbcLblDateEDL.anchor = GridBagConstraints.WEST;
		gbcLblDateEDL.fill = GridBagConstraints.VERTICAL;
		gbcLblDateEDL.insets = new Insets(0, 0, 5, 5);
		gbcLblDateEDL.gridx = 0;
		gbcLblDateEDL.gridy = 1;
		panelGridWest.add(lblDateEDL, gbcLblDateEDL);

		textFieldDateEDL = new JTextField();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dateFormatee = LocalDate.now().format(formatter);
		textFieldDateEDL.setText(dateFormatee);

		GridBagConstraints gbcTextFieldDateEntreeEDL = new GridBagConstraints();
		gbcTextFieldDateEntreeEDL.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldDateEntreeEDL.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldDateEntreeEDL.gridx = 2;
		gbcTextFieldDateEntreeEDL.gridy = 1;
		panelGridWest.add(textFieldDateEDL, gbcTextFieldDateEntreeEDL);
		textFieldDateEDL.setColumns(10);

		// Zone de texte pour les détails de l'état des lieux
		JLabel lblDetailsEDL = new JLabel("Détails :");
		lblDetailsEDL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblDetailsEDL = new GridBagConstraints();
		gbcLblDetailsEDL.anchor = GridBagConstraints.NORTHWEST;
		gbcLblDetailsEDL.insets = new Insets(0, 0, 5, 5);
		gbcLblDetailsEDL.gridx = 0;
		gbcLblDetailsEDL.gridy = 2;
		panelGridWest.add(lblDetailsEDL, gbcLblDetailsEDL);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbcScrollPane = new GridBagConstraints();
		gbcScrollPane.insets = new Insets(0, 0, 5, 0);
		gbcScrollPane.fill = GridBagConstraints.BOTH;
		gbcScrollPane.gridx = 2;
		gbcScrollPane.gridy = 2;
		panelGridWest.add(scrollPane, gbcScrollPane);

		textAreaDetails = new JTextArea();
		scrollPane.setViewportView(textAreaDetails);
		textAreaDetails.setFont(new Font("Arial", Font.PLAIN, 13));
		textAreaDetails.setBorder(new MatteBorder(1, 1, 1, 1, new Color(117, 117, 117)));

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Bouton pour valider l'ajout de l'état des lieux
		JButton btnValiderAjoutEDL = new JButton("Valider");
		btnValiderAjoutEDL.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutEDL);
		btnValiderAjoutEDL.addActionListener(this.gestionClic);

		// Bouton pour annuler et fermer la fenêtre
		JButton btnAnnulerAjoutEDL = new JButton("Annuler");
		btnAnnulerAjoutEDL.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutEDL);
		btnAnnulerAjoutEDL.addActionListener(this.gestionClic);

		// Configuration spéciale pour un état des lieux de sortie (obligatoire)
		// L'état des lieux de sortie doit impérativement être saisi pour terminer la
		// location
		if (type == Type.SORTIE) {
			textFieldDateEDL.setEditable(false);
			btnAnnulerAjoutEDL.setVisible(false);
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		}

		pack();
	}

	/**
	 * Retourne la location concernée par cet état des lieux
	 * 
	 * @return l'objet Louer représentant la location
	 */
	public Louer getLouer() {
		return this.location;
	}

	/**
	 * Retourne le bouton radio pour l'état des lieux d'entrée
	 * 
	 * @return le bouton radio d'entrée
	 */
	public JRadioButton getRdbtnEntreeEDL() {
		return rdbtnEntreeEDL;
	}

	/**
	 * Retourne le bouton radio pour l'état des lieux de sortie
	 * 
	 * @return le bouton radio de sortie
	 */
	public JRadioButton getRdbtnSortieEDL() {
		return rdbtnSortieEDL;
	}

	/**
	 * Retourne le champ de la date de l'état des lieux
	 * 
	 * @return le champ de texte de la date
	 */
	public JTextField getTextFieldDateEDL() {
		return textFieldDateEDL;
	}

	/**
	 * Retourne la zone de texte contenant les détails de l'état des lieux
	 * 
	 * @return la zone de texte des détails
	 */
	public JTextArea getTextAreaDetails() {
		return textAreaDetails;
	}

	/**
	 * Retourne la fenêtre ancêtre pour la mettre à jour après l'ajout
	 * 
	 * @return la fenêtre parente
	 */
	public FenBienLouableInformations getFenAncetre() {
		return fenAncetre;
	}

}
