package vue.ajouter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controleur.Outils;
import controleur.ajouter.GestionFenAjouterInterventionBienLouable;
import vue.consulter_informations.FenBienLouableInformations;

public class FenAjouterInterventionBienLouable extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JComboBox<String> comboBoxInterv; // ComboBox pour sélectionner une intervention existante du bâtiment

	// Contrôleur
	private transient GestionFenAjouterInterventionBienLouable gestionClic; // Contrôleur pour gérer les événements de
																			// cette fenêtre

	// Variables de gestion
	private FenBienLouableInformations fenAncestor; // La fenêtre parente (informations du bien louable)

	/**
	 * Construit la page Ajouter Intervention Bien Louable qui permet d'associer une
	 * intervention existante du bâtiment à un bien louable spécifique.
	 * 
	 * Cette fenêtre permet uniquement de sélectionner des interventions déjà
	 * saisies au niveau du bâtiment (il faut d'abord créer une intervention
	 * bâtiment avant de pouvoir l'associer à un bien).
	 *
	 * @param idBien l'identifiant du bien louable auquel on veut associer
	 *               l'intervention
	 * @param fen    la fenêtre parente (informations du bien) qui sera mise à jour
	 *               après l'ajout
	 */
	public FenAjouterInterventionBienLouable(String idBien, FenBienLouableInformations fen) {
		// Affectation de la fenêtre parente
		this.fenAncestor = fen;

		// Panel racine
		setBounds(100, 100, 450, 168);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		JLabel lblLabelTitreAjouterInterventionBien = new JLabel("Ajouter une Intervention Bien");
		lblLabelTitreAjouterInterventionBien.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreAjouterInterventionBien.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitreAjouterInterventionBien, BorderLayout.NORTH);

		// Panel corps
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel pour le label "Intervention :"
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.WEST);
		panelGridWest.setLayout(new GridLayout(1, 1, 0, 10));

		JLabel lblIntervention = new JLabel("Intervention :");
		lblIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblIntervention);

		// Panel pour la combobox des interventions
		JPanel panelGridCenter = new JPanel();
		panelBorder.add(panelGridCenter, BorderLayout.CENTER);
		panelGridCenter.setLayout(new GridLayout(1, 0, 0, 10));

		// ComboBox pour sélectionner une intervention existante du bâtiment
		comboBoxInterv = new JComboBox<>();
		panelGridCenter.add(comboBoxInterv);

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenAjouterInterventionBienLouable(this, idBien);

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Bouton pour valider l'association de l'intervention au bien
		JButton btnValiderAjoutIB = new JButton("Valider");
		btnValiderAjoutIB.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutIB);
		btnValiderAjoutIB.addActionListener(this.gestionClic);

		// Bouton pour annuler et fermer la fenêtre
		JButton btnAnnulerAjoutIB = new JButton("Annuler");
		btnAnnulerAjoutIB.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutIB);

		// Message d'information important pour l'utilisateur
		JLabel lblNewLabel = new JLabel("Veuillez d'abord saisir l'intervention dans le bâtiment du bien louable.");
		lblNewLabel.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 11));
		panelBorder.add(lblNewLabel, BorderLayout.NORTH);
		btnAnnulerAjoutIB.addActionListener(this.gestionClic);
	}

	/**
	 * Remplit la combobox avec la liste des identifiants des interventions
	 * existantes pour le bâtiment. Cette méthode est appelée par le contrôleur
	 * après avoir récupéré les interventions disponibles.
	 * 
	 * @param idsInterv la liste des identifiants d'interventions à afficher dans la
	 *                  combobox
	 */
	public void afficherIntervs(List<String> idsInterv) {
		for (String id : idsInterv) {
			comboBoxInterv.addItem(id);
		}
	}

	/**
	 * Retourne la combobox contenant les interventions disponibles
	 * 
	 * @return la combobox des interventions
	 */
	public JComboBox<String> getComboBoxInterv() {
		return comboBoxInterv;
	}

	/**
	 * Modifie la combobox des interventions
	 * 
	 * @param comboBoxInterv la nouvelle combobox à utiliser
	 */
	public void setComboBoxInterv(JComboBox<String> comboBoxInterv) {
		this.comboBoxInterv = comboBoxInterv;
	}

	/**
	 * Retourne la fenêtre ancêtre pour la mettre à jour après l'ajout
	 * 
	 * @return la fenêtre parente
	 */
	public FenBienLouableInformations getFenAncestor() {
		return fenAncestor;
	}

}
