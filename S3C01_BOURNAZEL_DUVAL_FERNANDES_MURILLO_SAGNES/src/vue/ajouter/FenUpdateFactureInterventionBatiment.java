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
import controleur.ajouter.GestionFenUpdateFactureInterventionBatiment;
import vue.consulter_informations.FenBatimentInformations;

public class FenUpdateFactureInterventionBatiment extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JTextField textFieldNumFactureIntervention; // Champ pour le numéro de la facture
	private JTextField textFieldDateFactureIntervention; // Champ pour la date de la facture (format DD-MM-AAAA)
	private JTextField textFieldMontantFactureIntervention; // Champ pour le montant de la facture

	// Contrôleur
	private transient GestionFenUpdateFactureInterventionBatiment gestionClic; // Contrôleur pour gérer les événements
																				// de cette fenêtre

	// Variables de gestion
	private FenBatimentInformations fenAncestor; // La fenêtre parente (informations du bâtiment)

	/**
	 * Construit la page Modifier Facture Intervention Bâtiment qui permet de mettre
	 * à jour les informations de facturation d'une intervention existante sur un
	 * bâtiment. Cette fenêtre permet de compléter ou modifier : - Le numéro de la
	 * facture - Le montant de la facture - La date de la facture
	 *
	 * @param idInterv l'identifiant de l'intervention dont on veut modifier la
	 *                 facture
	 * @param fen      la fenêtre parente (informations du bâtiment) qui sera mise à
	 *                 jour après la modification
	 */
	public FenUpdateFactureInterventionBatiment(String idInterv, FenBatimentInformations fen) {
		// Affectation de la fenêtre parente
		this.fenAncestor = fen;

		// Panel racine
		setBounds(100, 100, 571, 230);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel titre avec l'identifiant de l'intervention
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		JLabel lblTitre = new JLabel();
		lblTitre.setText("Informations de l'intervention : " + idInterv);
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblTitre, BorderLayout.NORTH);

		// Panel corps
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel contenant les labels
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.WEST);
		panelGridWest.setLayout(new GridLayout(3, 1, 0, 10));

		JLabel lblNumFactureIntervention = new JLabel("Numero Facture :");
		lblNumFactureIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblNumFactureIntervention);

		JLabel lblMontantFactureIntervention = new JLabel("Montant Facture :");
		lblMontantFactureIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblMontantFactureIntervention);

		JLabel lblDateFactureIntervention = new JLabel("Date Facture :");
		lblDateFactureIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblDateFactureIntervention);

		// Panel contenant les champs de saisie
		JPanel panelGridCenter = new JPanel();
		panelBorder.add(panelGridCenter, BorderLayout.CENTER);
		panelGridCenter.setLayout(new GridLayout(3, 0, 0, 10));

		// Champ numéro de facture
		textFieldNumFactureIntervention = new JTextField();
		textFieldNumFactureIntervention.setText("");
		panelGridCenter.add(textFieldNumFactureIntervention);
		textFieldNumFactureIntervention.setColumns(10);

		// Champ montant de la facture
		textFieldMontantFactureIntervention = new JTextField();
		textFieldMontantFactureIntervention.setText("");
		panelGridCenter.add(textFieldMontantFactureIntervention);
		textFieldMontantFactureIntervention.setColumns(10);

		// Champ date de la facture avec format par défaut
		textFieldDateFactureIntervention = new JTextField();
		textFieldDateFactureIntervention.setText("DD-MM-AAAA");
		panelGridCenter.add(textFieldDateFactureIntervention);
		textFieldDateFactureIntervention.setColumns(10);

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenUpdateFactureInterventionBatiment(this, idInterv);

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnValiderAjoutIB = new JButton("Valider");
		btnValiderAjoutIB.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutIB);
		btnValiderAjoutIB.addActionListener(this.gestionClic);

		JButton btnAnnulerAjoutIB = new JButton("Annuler");
		btnAnnulerAjoutIB.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutIB);
		btnAnnulerAjoutIB.addActionListener(this.gestionClic);
	}

	/**
	 * Retourne le champ de texte du numéro de facture
	 * 
	 * @return le champ de texte du numéro de facture
	 */
	public JTextField getTextFieldNumFactureIntervention() {
		return textFieldNumFactureIntervention;
	}

	/**
	 * Retourne le champ de texte de la date de facture
	 * 
	 * @return le champ de texte de la date de facture
	 */
	public JTextField getTextFieldDateFactureIntervention() {
		return textFieldDateFactureIntervention;
	}

	/**
	 * Retourne le champ de texte du montant de la facture
	 * 
	 * @return le champ de texte du montant de la facture
	 */
	public JTextField getTextFieldMontantFactureIntervention() {
		return textFieldMontantFactureIntervention;
	}

	/**
	 * Retourne la fenêtre parente afin de pouvoir mettre sa table à jour
	 * 
	 * @return la fenêtre parente
	 */
	public FenBatimentInformations getFenAncestor() {
		return fenAncestor;
	}

}
