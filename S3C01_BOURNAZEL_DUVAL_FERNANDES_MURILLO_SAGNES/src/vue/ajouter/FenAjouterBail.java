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
import controleur.ajouter.GestionFenAjouterBail;
import modele.BienLouable;
import vue.consulter_informations.FenBienLouableInformations;

public class FenAjouterBail extends JFrame {

	private static final long serialVersionUID = 1L;

	// Variables swing
	private JPanel contentPane; // Panel racine
	private JPanel panelBoutons; // Panel contenant les boutons

	private JTextField textFieldDateFin; // Champ de saisie de la date de fin de bail
	private JTextField textFieldDateDebut; // Champ de saisie de la date de début de bail
	private JTextField textFieldDepotGarantie; // Champ de saisie du montant du dépôt de garantie

	private JButton btnAjouterGarant; // Bouton pour associer ou créer un nouveau garant (ouvre la fenêtre
										// FenChoisirOuAjouterGarant)
	private JLabel labelGarant; // Prends la valeur du garant sélectionné

	private JButton btnAjouterGarage; // Bouton pour associer un garage
	private JLabel lblAucunGarageDisp;
	private JLabel lblListeGarages;

	private JComboBox<String> comboBoxGarage; // Pour choisir le garage
	private JComboBox<String> comboBoxLocataire; // Pour choisir le locataire

	// Contrôleur
	private transient GestionFenAjouterBail gestionClic;

	// Variables de gestion
	private transient BienLouable bienConcerne; // le bien sur lequel l'utilisateur veut créer un bail
	private FenBienLouableInformations fenAncetre; // la fenêtre parente

	/**
	 * Construit la page Ajouter Bail qui permet d'ajouter un bail pour un bien
	 * louable, un locataire choisi dans une combobox et un garant associé qu'on
	 * choisit dans la page ChoisirOuAjouterGarant Ce constructeur initialise les
	 * champs à remplir suivants : La combobox du locataire associé avec la liste
	 * des locataires Un bouton redirigeant vers la page ChoisirOuAjouterGarant La
	 * date de début et la date de fin du bail Le dépôt de garantie La liste des
	 * garages qui ne sont pas encore associés à des logements dans une combobox Un
	 * bouton pour associer le garage choisi dans la combobox au logement
	 * 
	 * @param fenBien      la fenêtre parente
	 * @param bienConcerne le bien sur lequel l'utilisateur veut créer un bail
	 */
	public FenAjouterBail(FenBienLouableInformations fenBien, BienLouable bienConcerne) {

		this.bienConcerne = bienConcerne;
		this.fenAncetre = fenBien;

		setBounds(100, 100, 526, 607);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0)); // Panel parent

		// ----------------- Panels -------------------------
		JPanel panelTitre = new JPanel(); // Panel qui contient le titre
		contentPane.add(panelTitre, BorderLayout.NORTH);
		panelTitre.setLayout(new BorderLayout(0, 10));

		JPanel panelCentre = new JPanel(); // Contient Les champs
		contentPane.add(panelCentre, BorderLayout.CENTER);
		panelCentre.setLayout(new BorderLayout());

		JPanel panelChamps = new JPanel(); // Contient Les champs
		panelChamps.setBackground(new Color(240, 240, 240));
		panelCentre.add(panelChamps, BorderLayout.NORTH);
		GridBagLayout gblPanelGridWest = new GridBagLayout();
		gblPanelGridWest.columnWidths = new int[] { 120, 127 };
		gblPanelGridWest.columnWeights = new double[] { 0.0, 1.0 };
		gblPanelGridWest.rowHeights = new int[] { 50, 50, 50, 50, 50, 50, 50 };
		gblPanelGridWest.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		panelChamps.setLayout(gblPanelGridWest);

		panelBoutons = new JPanel();
		contentPane.add(panelBoutons, BorderLayout.SOUTH);
		panelBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// ------------------ PanelTitre --------------------------------

		JLabel lblLabelTitreAjouterBail = new JLabel("Associer un logement à un locataire");
		lblLabelTitreAjouterBail.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreAjouterBail.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panelTitre.add(lblLabelTitreAjouterBail, BorderLayout.NORTH);

		// ------------------ Panel Champs -----------------------------
		JLabel tABienConcerne = new JLabel(
				"<html><u>Vous allez associer un locataire au logement situé à l'adresse suivante :</u></html>");
		panelTitre.add(tABienConcerne, BorderLayout.CENTER);
		tABienConcerne.setOpaque(false);
		tABienConcerne.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));

		JPanel panelAdresse = new JPanel();
		panelAdresse.setLayout(new GridLayout(2, 1, 0, 5));
		panelAdresse.setOpaque(false);
		panelTitre.add(panelAdresse, BorderLayout.SOUTH);

		JLabel tAAdresse = new JLabel(this.bienConcerne.getAdresse() + ", " + this.bienConcerne.getCodePostal() + " "
				+ this.bienConcerne.getVille());
		tAAdresse.setOpaque(false);
		tAAdresse.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelAdresse.add(tAAdresse);

		JLabel tAInfoCompteurs = new JLabel("Pensez à relever les compteurs avant de créer le bail.");
		tAInfoCompteurs.setOpaque(false);
		tAInfoCompteurs.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		tAInfoCompteurs.setForeground(new Color(200, 0, 0));
		panelAdresse.add(tAInfoCompteurs);

		// Associer le locataire
		JLabel lblLocataire = new JLabel("Locataire associé");
		GridBagConstraints gbcLblLocataire = new GridBagConstraints();
		gbcLblLocataire.insets = new Insets(0, 5, 5, 5);
		gbcLblLocataire.gridx = 0;
		gbcLblLocataire.gridy = 0;
		gbcLblLocataire.anchor = GridBagConstraints.WEST;
		panelChamps.add(lblLocataire, gbcLblLocataire);

		comboBoxLocataire = new JComboBox<>();
		GridBagConstraints gbcComboBoxGarage = new GridBagConstraints();
		gbcComboBoxGarage.insets = new Insets(0, 5, 5, 0);
		gbcComboBoxGarage.fill = GridBagConstraints.HORIZONTAL;
		gbcComboBoxGarage.gridx = 1;
		gbcComboBoxGarage.gridy = 0;
		gbcComboBoxGarage.anchor = GridBagConstraints.WEST;
		panelChamps.add(comboBoxLocataire, gbcComboBoxGarage);

		// Instanciation du contrôleur
		gestionClic = new GestionFenAjouterBail(this);

		// Associer le garant

		JLabel lblGarant = new JLabel("Garant associé");
		GridBagConstraints gbcLblGarant = new GridBagConstraints();
		gbcLblGarant.insets = new Insets(0, 5, 5, 5);
		gbcLblGarant.gridx = 0;
		gbcLblGarant.gridy = 1;
		gbcLblGarant.anchor = GridBagConstraints.WEST;
		panelChamps.add(lblGarant, gbcLblGarant);

		btnAjouterGarant = new JButton("Ajouter un garant");
		btnAjouterGarant.addActionListener(this.gestionClic);

		labelGarant = new JLabel();
		labelGarant.setVisible(false); // Garant invisible au départ

		GridBagConstraints gbcBtnAjouterGarant = new GridBagConstraints();
		gbcBtnAjouterGarant.insets = new Insets(0, 5, 5, 0);
		gbcBtnAjouterGarant.fill = GridBagConstraints.HORIZONTAL;
		gbcBtnAjouterGarant.gridx = 1;
		gbcBtnAjouterGarant.gridy = 1;
		gbcBtnAjouterGarant.anchor = GridBagConstraints.WEST;
		panelChamps.add(btnAjouterGarant, gbcBtnAjouterGarant);
		panelChamps.add(labelGarant, gbcBtnAjouterGarant);

		JLabel lblDateDebut = new JLabel(" Date début");
		GridBagConstraints gbcLblDateDebut = new GridBagConstraints();
		gbcLblDateDebut.anchor = GridBagConstraints.WEST;
		gbcLblDateDebut.insets = new Insets(0, 0, 5, 5);
		gbcLblDateDebut.gridx = 0;
		gbcLblDateDebut.gridy = 2;
		panelChamps.add(lblDateDebut, gbcLblDateDebut);

		textFieldDateDebut = new JTextField();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dateFormatee = LocalDate.now().format(formatter);
		textFieldDateDebut.setText(dateFormatee);
		GridBagConstraints gbcTextFieldDateDebut = new GridBagConstraints();
		gbcTextFieldDateDebut.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldDateDebut.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldDateDebut.gridx = 1;
		gbcTextFieldDateDebut.gridy = 2;
		panelChamps.add(textFieldDateDebut, gbcTextFieldDateDebut);
		textFieldDateDebut.setColumns(10);

		JLabel lblDateFin = new JLabel(" Date fin");
		GridBagConstraints gbcLblDateFin = new GridBagConstraints();
		gbcLblDateFin.anchor = GridBagConstraints.WEST;
		gbcLblDateFin.insets = new Insets(0, 0, 5, 5);
		gbcLblDateFin.gridx = 0;
		gbcLblDateFin.gridy = 3;
		panelChamps.add(lblDateFin, gbcLblDateFin);

		textFieldDateFin = new JTextField();
		dateFormatee = LocalDate.now().plusYears(3).format(formatter);
		textFieldDateFin.setText(dateFormatee);
		GridBagConstraints gbcTextFieldDateFin = new GridBagConstraints();
		gbcTextFieldDateFin.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldDateFin.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldDateFin.gridx = 1;
		gbcTextFieldDateFin.gridy = 3;
		panelChamps.add(textFieldDateFin, gbcTextFieldDateFin);
		textFieldDateFin.setColumns(10);

		JLabel lblDepotGarantie = new JLabel("Dépôt de garantie");
		GridBagConstraints gbcLblDepotGarantie = new GridBagConstraints();
		gbcLblDepotGarantie.anchor = GridBagConstraints.WEST;
		gbcLblDepotGarantie.insets = new Insets(0, 5, 5, 5);
		gbcLblDepotGarantie.gridx = 0;
		gbcLblDepotGarantie.gridy = 4; // nouvelle ligne
		panelChamps.add(lblDepotGarantie, gbcLblDepotGarantie);

		textFieldDepotGarantie = new JTextField();
		GridBagConstraints gbcTextFieldDepotGarantie = new GridBagConstraints();
		gbcTextFieldDepotGarantie.insets = new Insets(0, 5, 5, 0);
		gbcTextFieldDepotGarantie.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldDepotGarantie.gridx = 1;
		gbcTextFieldDepotGarantie.gridy = 4; // 👈 même ligne
		panelChamps.add(textFieldDepotGarantie, gbcTextFieldDepotGarantie);
		textFieldDepotGarantie.setColumns(10);

		JLabel lblGarages = new JLabel(" Associer les garages :");
		lblGarages.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbcLblGarages = new GridBagConstraints();
		gbcLblGarages.anchor = GridBagConstraints.WEST;
		gbcLblGarages.insets = new Insets(0, 0, 5, 5);
		gbcLblGarages.gridx = 0;
		gbcLblGarages.gridy = 5;
		panelChamps.add(lblGarages, gbcLblGarages);

		JPanel panelGarages = new JPanel();
		GridBagConstraints gbcPanelGarages = new GridBagConstraints();
		gbcPanelGarages.insets = new Insets(0, 0, 5, 0);
		gbcPanelGarages.fill = GridBagConstraints.HORIZONTAL;
		gbcPanelGarages.anchor = GridBagConstraints.WEST;
		gbcPanelGarages.gridx = 1;
		gbcPanelGarages.gridy = 5;
		panelChamps.add(panelGarages, gbcPanelGarages);
		panelGarages.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		comboBoxGarage = new JComboBox<>();
		comboBoxGarage.setModel(new DefaultComboBoxModel<>(new String[] { "abcdefg", "hijklmnop" }));
		panelGarages.add(comboBoxGarage);

		btnAjouterGarage = new JButton("Ajouter le garage");
		btnAjouterGarage.addActionListener(gestionClic);
		panelGarages.add(btnAjouterGarage);

		lblAucunGarageDisp = new JLabel("Aucun garage disponible.");
		lblAucunGarageDisp.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 10));
		panelGarages.add(lblAucunGarageDisp);

		JLabel lblGaragesAssocies = new JLabel(" Garages associés :");
		GridBagConstraints gbcLblGaragesAssocies = new GridBagConstraints();
		gbcLblGaragesAssocies.anchor = GridBagConstraints.WEST;
		gbcLblGaragesAssocies.insets = new Insets(0, 0, 0, 5);
		gbcLblGaragesAssocies.gridx = 0;
		gbcLblGaragesAssocies.gridy = 6;
		panelChamps.add(lblGaragesAssocies, gbcLblGaragesAssocies);

		lblListeGarages = new JLabel(" Aucun garage associé.");
		GridBagConstraints gbcLblListeGarages = new GridBagConstraints();
		gbcLblListeGarages.anchor = GridBagConstraints.WEST;
		gbcLblListeGarages.gridx = 1;
		gbcLblListeGarages.gridy = 6;
		panelChamps.add(lblListeGarages, gbcLblListeGarages);

		// ------------------ Panel panelBoutons ------------------------
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(this.gestionClic);
		panelBoutons.add(btnValider);

		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(this.gestionClic);
		panelBoutons.add(btnAnnuler);

		// Remplis la combobox avec les garages qu'on peut associer à ce bien
		this.gestionClic.remplirComboGarages();

	}

	/**
	 * Retourne la combobox avec la liste de tout les locataires qui va nous
	 * permettre de récupérer le locataire choisi pour ce bail dans les gestions
	 * 
	 * @return
	 */
	public JComboBox<String> getComboBoxLocataire() {
		return this.comboBoxLocataire;
	}

	/**
	 * Permet de rendre le label du garant visible avec son identifiant et d'enlever
	 * le bouton ajouter garant lorsqu'un garant est sélectionné
	 * 
	 * @param idGarant
	 */
	public void garantSelectionne(String idGarant) {
		this.btnAjouterGarant.setVisible(false);

		this.labelGarant.setText(idGarant);
		this.labelGarant.setVisible(true);
	}

	/**
	 * Retourne le champ de date de fin du bail pour pouvoir récupérer cette date
	 * entrée par l'utilisateur pour pouvoir utiliser cette données dans les
	 * gestions
	 * 
	 * @return
	 */
	public JTextField getTextFieldDateFin() {
		return textFieldDateFin;
	}

	/**
	 * Retourne le champ de date de début du bail pour pouvoir récupérer cette date
	 * entrée par l'utilisateur pour pouvoir utiliser cette donnée dans les gestions
	 * 
	 * @return
	 */
	public JTextField getTextFieldDateDebut() {
		return textFieldDateDebut;
	}

	/**
	 * Retourne le champ de dépôt de garantie du bail pour pouvoir récupérer ce
	 * montant entré par l'utilisateur pour pouvoir utiliser cette donnée dans les
	 * gestions
	 * 
	 * @return
	 */
	public JTextField getTextFieldDepotGarantie() {
		return textFieldDepotGarantie;
	}

	/**
	 * Retourne le label du garant associé au bail pour pouvoir récupérer cet
	 * identifiant pour pouvoir utiliser cette donnée dans les gestions
	 * 
	 * @return
	 */
	public JLabel getLabelGarant() {
		return labelGarant;
	}

	/**
	 * 
	 * @return
	 */
	public BienLouable getBienConcerne() {
		return bienConcerne;
	}

	public FenBienLouableInformations getFenAncetre() {
		return fenAncetre;
	}

	public JComboBox<String> getComboBoxGarage() {
		return comboBoxGarage;
	}

	public void setComboBoxGarage(JComboBox<String> comboBoxGarage) {
		this.comboBoxGarage = comboBoxGarage;
	}

	public JButton getBtnAjouterGarage() {
		return btnAjouterGarage;
	}

	public void setBtnAjouterGarage(JButton btnAjouterGarage) {
		this.btnAjouterGarage = btnAjouterGarage;
	}

	public JLabel getLblAucunGarageDisp() {
		return lblAucunGarageDisp;
	}

	public void setLblAucunGarageDisp(JLabel lblAucunGarageDisp) {
		this.lblAucunGarageDisp = lblAucunGarageDisp;
	}

	public JLabel getLblListeGarages() {
		return lblListeGarages;
	}

	public void setLblListeGarages(JLabel lblListeGarages) {
		this.lblListeGarages = lblListeGarages;
	}

}
