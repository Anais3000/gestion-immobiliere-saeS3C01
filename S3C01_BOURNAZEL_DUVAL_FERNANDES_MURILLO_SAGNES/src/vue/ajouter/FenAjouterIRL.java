package vue.ajouter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

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
import controleur.ajouter.GestionFenAjouterIRL;
import modele.IRL;
import vue.documents.FenValeursIRL;
import vue.tables.FenBienLouable;

public class FenAjouterIRL extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JTextField textFieldAnneeIRL; // Champ de saisie de l'année de l'IRL
	private JTextField textFieldValeurIRL; // Champ de saisie de la valeur de l'IRL
	private JComboBox<String> comboBoxTrimestreIRL; // ComboBox pour sélectionner le trimestre

	// Contrôleur
	private transient GestionFenAjouterIRL gestionClic; // Contrôleur pour gérer les événements de cette fenêtre

	/**
	 * Construit la page Ajouter IRL qui permet d'ajouter un nouvel indice de
	 * révision des loyers (IRL). Ce constructeur initialise les champs à remplir
	 * suivants : L'année de l'IRL Le trimestre dans une combobox (Premier,
	 * Deuxième, Troisième, Quatrième) La valeur de l'IRL
	 *
	 * @param fenBL  la fenêtre parente (table des biens louables) qui peut être
	 *               mise à jour après l'ajout
	 * @param fenIRL la fenêtre des valeurs IRL qui sera mise à jour après l'ajout
	 */
	public FenAjouterIRL(FenBienLouable fenBL, FenValeursIRL fenIRL) {
		// Instanciation du contrôleur
		this.gestionClic = new GestionFenAjouterIRL(this, fenBL, fenIRL);

		// Panel racine
		setBounds(100, 100, 450, 208);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		JLabel lblLabelTitreAjouterIRL = new JLabel("Ajouter un IRL");
		lblLabelTitreAjouterIRL.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreAjouterIRL.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitreAjouterIRL, BorderLayout.NORTH);

		// Panel corps (contient le formulaire et les boutons)
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel formulaire - Labels
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.WEST);
		panelGridWest.setLayout(new GridLayout(3, 1, 0, 10));

		// Création des labels pour chaque champ
		JLabel lblAnneeIRL = new JLabel("Année :");
		lblAnneeIRL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblAnneeIRL);

		JLabel lblTrimestreIRL = new JLabel("Trimestre :");
		lblTrimestreIRL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblTrimestreIRL);

		JLabel lblValeurIRL = new JLabel("Valeur :");
		lblValeurIRL.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblValeurIRL);

		// Panel formulaire - Champs de saisie
		JPanel panelGridCenter = new JPanel();
		panelBorder.add(panelGridCenter, BorderLayout.CENTER);
		panelGridCenter.setLayout(new GridLayout(3, 0, 0, 10));

		// Champ de saisie de l'année
		textFieldAnneeIRL = new JTextField();
		panelGridCenter.add(textFieldAnneeIRL);
		textFieldAnneeIRL.setColumns(10);

		// ComboBox pour sélectionner le trimestre
		comboBoxTrimestreIRL = new JComboBox<>();
		comboBoxTrimestreIRL
				.setModel(new DefaultComboBoxModel<>(new String[] { "Premier", "Deuxième", "Troisième", "Quatrième" }));
		panelGridCenter.add(comboBoxTrimestreIRL);

		// Champ de saisie de la valeur de l'IRL
		textFieldValeurIRL = new JTextField();
		panelGridCenter.add(textFieldValeurIRL);
		textFieldValeurIRL.setColumns(10);

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Bouton pour valider l'ajout de l'IRL
		JButton btnValiderAjoutBL = new JButton("Valider");
		btnValiderAjoutBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutBL);
		btnValiderAjoutBL.addActionListener(this.gestionClic);

		// Bouton pour annuler et fermer la fenêtre
		JButton btnAnnulerAjoutBL = new JButton("Annuler");
		btnAnnulerAjoutBL.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutBL);
		btnAnnulerAjoutBL.addActionListener(this.gestionClic);
	}

	/**
	 * Crée et retourne un nouvel objet IRL avec les valeurs saisies dans le
	 * formulaire
	 * 
	 * @return le nouvel IRL créé avec toutes ses informations
	 */
	public IRL getNouvelIRL() {
		return new IRL(Integer.valueOf(textFieldAnneeIRL.getText()), comboBoxTrimestreIRL.getSelectedIndex() + 1,
				Double.valueOf(textFieldValeurIRL.getText()));
	}

	/**
	 * Retourne le champ de l'année de l'IRL pour l'utiliser dans les gestions
	 * 
	 * @return le champ de texte de l'année
	 */
	public JTextField getTextFieldAnneeIRL() {
		return textFieldAnneeIRL;
	}

	/**
	 * Retourne le champ de la valeur de l'IRL pour l'utiliser dans les gestions
	 * 
	 * @return le champ de texte de la valeur
	 */
	public JTextField getTextFieldValeurIRL() {
		return textFieldValeurIRL;
	}

	/**
	 * Retourne la combobox du trimestre de l'IRL pour récupérer le choix de
	 * l'utilisateur
	 * 
	 * @return la combobox du trimestre
	 */
	public JComboBox<String> getComboBoxTrimestreIRL() {
		return comboBoxTrimestreIRL;
	}

}
