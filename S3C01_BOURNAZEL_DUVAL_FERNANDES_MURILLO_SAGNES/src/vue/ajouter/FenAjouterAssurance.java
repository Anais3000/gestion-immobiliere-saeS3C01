package vue.ajouter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controleur.Outils;
import controleur.ajouter.GestionFenAjouterAssurance;
import vue.consulter_informations.FenBatimentInformations;

public class FenAjouterAssurance extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JTextField textFieldAnneeCouvertureAssurance; // champ de saisie de l'année de couverture
	private JTextField textFieldNumPoliceAssurance; // champ de saisie du numéro de police
	private JTextField textFieldMontantPayeAssurance; // champ de saisie du montant payé
	private JRadioButton rdbtnTypeProprietaire;
	private JRadioButton rdbtnTypeAideJuridique;

	// Contrôleurs
	private transient GestionFenAjouterAssurance gestionClic; // controleur

	// Variables de gestion
	String idBat;
	FenBatimentInformations fenAncestor;

	/**
	 * Construit la page Ajouter Assurance qui permet d'ajouter une assurance pour
	 * un bâtiment donné dont on récupère l'identifiant à partir de la page qui
	 * appelle AjouterAssurance (la fenêtre d'informations sur un bâtiment) Ce
	 * constructeur initialise les champs à remplir suivants : Numéro de police de
	 * l'assurance Le type du contrat : aide juridique ou propriétaire L'année où
	 * l'assurance est effective Le montant payé en tout sur l'année
	 *
	 * @param idBat ce paramètre est l'identifiant du bâtiment auquel l'utilisateur
	 *              veut ajouter une assurance
	 * @param fen   fenêtre parente
	 */
	public FenAjouterAssurance(String idBat, FenBatimentInformations fen) {

		// Affectation des variables
		this.idBat = idBat;
		this.fenAncestor = fen;

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenAjouterAssurance(this, idBat);

		// Panel racine
		setBounds(100, 100, 450, 363);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel titre
		JPanel panelTitre = new JPanel();
		contentPane.add(panelTitre, BorderLayout.NORTH);
		panelTitre.setLayout(new BorderLayout(0, 10));

		JLabel lblLabelTitreAjouterAssurance = new JLabel("Ajouter une Assurance");
		lblLabelTitreAjouterAssurance.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreAjouterAssurance.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panelTitre.add(lblLabelTitreAjouterAssurance, BorderLayout.NORTH);

		// Panel corps (contient le formulaire et les boutons)
		JPanel panelCorps = new JPanel();
		contentPane.add(panelCorps, BorderLayout.CENTER);
		panelCorps.setLayout(new BorderLayout(0, 0));

		// Panel formulaire
		JPanel panelFormulaireDeSaisie = new JPanel();
		panelFormulaireDeSaisie.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelCorps.add(panelFormulaireDeSaisie, BorderLayout.CENTER);
		GridBagLayout gblPanelFormulaireDeSaisie = new GridBagLayout();
		gblPanelFormulaireDeSaisie.columnWidths = new int[] { 127, 0, 127, 0 };
		gblPanelFormulaireDeSaisie.rowHeights = new int[] { 60, 60, 60, 60 };
		gblPanelFormulaireDeSaisie.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gblPanelFormulaireDeSaisie.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0 };
		panelFormulaireDeSaisie.setLayout(gblPanelFormulaireDeSaisie);

		JLabel lblNumPoliceAssurance = new JLabel("Numéro Police :");
		lblNumPoliceAssurance.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblNumPoliceAssurance = new GridBagConstraints();
		gbcLblNumPoliceAssurance.anchor = GridBagConstraints.WEST;
		gbcLblNumPoliceAssurance.fill = GridBagConstraints.VERTICAL;
		gbcLblNumPoliceAssurance.insets = new Insets(0, 0, 5, 5);
		gbcLblNumPoliceAssurance.gridx = 0;
		gbcLblNumPoliceAssurance.gridy = 0;
		panelFormulaireDeSaisie.add(lblNumPoliceAssurance, gbcLblNumPoliceAssurance);

		textFieldNumPoliceAssurance = new JTextField();
		GridBagConstraints gbcTextFieldNumPoliceAssurance = new GridBagConstraints();
		gbcTextFieldNumPoliceAssurance.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldNumPoliceAssurance.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldNumPoliceAssurance.gridx = 2;
		gbcTextFieldNumPoliceAssurance.gridy = 0;
		panelFormulaireDeSaisie.add(textFieldNumPoliceAssurance, gbcTextFieldNumPoliceAssurance);
		textFieldNumPoliceAssurance.setColumns(10);

		JLabel lblTypeContrat = new JLabel("Type de Contrat :");
		lblTypeContrat.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblTypeContrat = new GridBagConstraints();
		gbcLblTypeContrat.anchor = GridBagConstraints.WEST;
		gbcLblTypeContrat.fill = GridBagConstraints.VERTICAL;
		gbcLblTypeContrat.insets = new Insets(0, 0, 5, 5);
		gbcLblTypeContrat.gridx = 0;
		gbcLblTypeContrat.gridy = 1;
		panelFormulaireDeSaisie.add(lblTypeContrat, gbcLblTypeContrat);

		// Choix du type d'assurance avec des radiobuttons (deux possibilités)
		JPanel panelRadioButtons = new JPanel();
		GridBagConstraints gbcPanelRadioButtons = new GridBagConstraints();
		gbcPanelRadioButtons.insets = new Insets(0, 0, 5, 0);
		gbcPanelRadioButtons.fill = GridBagConstraints.BOTH;
		gbcPanelRadioButtons.gridx = 2;
		gbcPanelRadioButtons.gridy = 1;
		panelFormulaireDeSaisie.add(panelRadioButtons, gbcPanelRadioButtons);
		panelRadioButtons.setLayout(new GridLayout(0, 2, 0, 0));

		rdbtnTypeProprietaire = new JRadioButton("Propriétaire");
		panelRadioButtons.add(rdbtnTypeProprietaire);
		rdbtnTypeProprietaire.setSelected(true);

		rdbtnTypeAideJuridique = new JRadioButton("Aide juridique");
		rdbtnTypeAideJuridique.setHorizontalAlignment(SwingConstants.CENTER);
		panelRadioButtons.add(rdbtnTypeAideJuridique);

		ButtonGroup groupAssurance = new ButtonGroup();
		groupAssurance.add(rdbtnTypeProprietaire);
		groupAssurance.add(rdbtnTypeAideJuridique);

		JLabel lblAnneeCouvertureAssurance = new JLabel("Année de Couverture :");
		lblAnneeCouvertureAssurance.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblAnneeCouvertureAssurance = new GridBagConstraints();
		gbcLblAnneeCouvertureAssurance.anchor = GridBagConstraints.WEST;
		gbcLblAnneeCouvertureAssurance.fill = GridBagConstraints.VERTICAL;
		gbcLblAnneeCouvertureAssurance.insets = new Insets(0, 0, 5, 5);
		gbcLblAnneeCouvertureAssurance.gridx = 0;
		gbcLblAnneeCouvertureAssurance.gridy = 2;
		panelFormulaireDeSaisie.add(lblAnneeCouvertureAssurance, gbcLblAnneeCouvertureAssurance);

		textFieldAnneeCouvertureAssurance = new JTextField();
		GridBagConstraints gbcTextFieldAnneeCouvertureAssurance = new GridBagConstraints();
		gbcTextFieldAnneeCouvertureAssurance.insets = new Insets(0, 0, 5, 0);
		gbcTextFieldAnneeCouvertureAssurance.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldAnneeCouvertureAssurance.gridx = 2;
		gbcTextFieldAnneeCouvertureAssurance.gridy = 2;
		panelFormulaireDeSaisie.add(textFieldAnneeCouvertureAssurance, gbcTextFieldAnneeCouvertureAssurance);
		textFieldAnneeCouvertureAssurance.setColumns(10);

		JLabel lblMontantPayeAssurance = new JLabel("Montant Payé :");
		lblMontantPayeAssurance.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		GridBagConstraints gbcLblMontantPayeAssurance = new GridBagConstraints();
		gbcLblMontantPayeAssurance.anchor = GridBagConstraints.WEST;
		gbcLblMontantPayeAssurance.insets = new Insets(0, 0, 0, 5);
		gbcLblMontantPayeAssurance.gridx = 0;
		gbcLblMontantPayeAssurance.gridy = 3;
		panelFormulaireDeSaisie.add(lblMontantPayeAssurance, gbcLblMontantPayeAssurance);

		textFieldMontantPayeAssurance = new JTextField();
		GridBagConstraints gbcTextFieldMontantPayeAssurance = new GridBagConstraints();
		gbcTextFieldMontantPayeAssurance.fill = GridBagConstraints.HORIZONTAL;
		gbcTextFieldMontantPayeAssurance.gridx = 2;
		gbcTextFieldMontantPayeAssurance.gridy = 3;
		panelFormulaireDeSaisie.add(textFieldMontantPayeAssurance, gbcTextFieldMontantPayeAssurance);
		textFieldMontantPayeAssurance.setColumns(10);

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelCorps.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnValiderAjoutAssurance = new JButton("Valider");
		btnValiderAjoutAssurance.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutAssurance);
		btnValiderAjoutAssurance.addActionListener(this.gestionClic);

		JButton btnAnnulerAjoutAssurance = new JButton("Annuler");
		btnAnnulerAjoutAssurance.addActionListener(this.gestionClic);

		btnAnnulerAjoutAssurance.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutAssurance);

	}

	/**
	 * Retourne le champ de l'année de couverture de l'assurance pour pouvoir
	 * l'utiliser dans les gestions et récupérer ainsi la chaîne de caractère entrée
	 * par l'utilisateur
	 * 
	 * @return
	 */
	public JTextField getTextFieldAnneeCouvertureAssurance() {
		return textFieldAnneeCouvertureAssurance;
	}

	/**
	 * Retourne le champ du numéro de police de l'assurance pour pouvoir l'utiliser
	 * dans les gestions et récupérer ainsi la chaîne de caractère entrée par
	 * l'utilisateur
	 * 
	 * @return
	 */
	public JTextField getTextFieldNumPoliceAssurance() {
		return textFieldNumPoliceAssurance;
	}

	/**
	 * Retourne le champ du montant payé de l'assurance sur l'année pour pouvoir
	 * l'utiliser dans les gestions et récupérer ainsi la chaîne de caractère entrée
	 * par l'utilisateur
	 * 
	 * @return
	 */
	public JTextField getTextFieldMontantPayeAssurance() {
		return textFieldMontantPayeAssurance;
	}

	/**
	 * Retourne le bouton radio du choix un pour le type de contrat de l'assurance
	 * (propriétaire) pour pouvoir savoir si il est coché ou non et l'utiliser dans
	 * les gestions pour récupérer ainsi le choix de l'utilisateur
	 * 
	 * @return
	 */
	public JRadioButton getRdbtnTypeUn() {
		return rdbtnTypeProprietaire;
	}

	/**
	 * Retourne le bouton radio du choix deux pour le type de contrat de l'assurance
	 * (aide juridique) pour pouvoir savoir si il est coché ou non et l'utiliser
	 * dans les gestions pour récupérer ainsi le choix de l'utilisateur
	 * 
	 * @return
	 */
	public JRadioButton getRdbtnTypeDeux() {
		return rdbtnTypeAideJuridique;
	}

	/**
	 * Retourne la fenêtre ancêtre à la page AjouterAssurance, donc la page depuis
	 * laquelle elle est appelée, ici, FenBatimentInformations pour pouvoir la
	 * mettre à jour après l'ajout de l'assurance pour qu'elle s'ajoute bien au
	 * tableau (liste) des assurances du bâtiment
	 * 
	 * @return
	 */
	public FenBatimentInformations getFenAncestor() {
		return fenAncestor;
	}

}
