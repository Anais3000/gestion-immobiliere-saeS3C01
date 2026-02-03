package vue.documents;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controleur.documents.GestionFenImportCSV;
import vue.Polices;

/**
 * Fenêtre d'importation de loyers depuis un fichier CSV. Permet à l'utilisateur
 * de sélectionner un fichier CSV contenant des loyers à importer dans
 * l'application. Les loyers importés doivent concerner des baux en cours et le
 * locataire précisé doit correspondre au locataire du bail actuel.
 */
public class FenImportCSV extends JInternalFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private static final int MARGE = 10; // Constante pour les marges des composants
	JLabel lblNomFichier; // Label affichant le nom du fichier CSV sélectionné // Label affichant le nom
							// du fichier CSV sélectionné

	// Contrôleur
	private transient GestionFenImportCSV gestionClics; // Contrôleur des événements de la fenêtre

	/**
	 * Constructeur de la fenêtre d'importation de loyers depuis un fichier CSV.
	 * Crée l'interface avec un titre, des explications sur les contraintes
	 * d'importation, un bouton pour choisir le fichier CSV, l'affichage du fichier
	 * sélectionné, et les boutons de validation/annulation.
	 */
	public FenImportCSV() {
		setBounds(100, 100, 450, 300);
		// Initialisation du contrôleur de la fenêtre
		gestionClics = new GestionFenImportCSV(this);

		// Titre de la fenêtre
		JLabel lblTitre = new JLabel("Importer des loyers avec un fichier CSV");
		getContentPane().add(lblTitre, BorderLayout.NORTH);
		lblTitre.setFont(Polices.TITRE.getFont());

		// Panel central : explications et choix du fichier
		JPanel panelCentre = new JPanel();
		getContentPane().add(panelCentre, BorderLayout.CENTER);
		panelCentre.setLayout(new BorderLayout(0, 0));

		// Panel contenant les explications et le bouton de sélection
		JPanel panelChoixFichier = new JPanel();
		panelCentre.add(panelChoixFichier, BorderLayout.CENTER);
		GridBagLayout gblPanelChoixFichier = new GridBagLayout();
		gblPanelChoixFichier.columnWidths = new int[] { 0, 0 };
		gblPanelChoixFichier.rowHeights = new int[] { 23, 0, 0, 0, 0, 0, 0 };
		gblPanelChoixFichier.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gblPanelChoixFichier.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelChoixFichier.setLayout(gblPanelChoixFichier);

		// Label d'explication principale
		JLabel lblExplications = new JLabel("Choisissez le fichier contenant les loyers que vous souhaitez ajouter");
		lblExplications.setVerticalAlignment(SwingConstants.TOP);
		lblExplications.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbcLblExplications = new GridBagConstraints();
		gbcLblExplications.anchor = GridBagConstraints.WEST;
		gbcLblExplications.insets = new Insets(MARGE, MARGE, MARGE, MARGE);
		gbcLblExplications.gridx = 0;
		gbcLblExplications.gridy = 0;
		panelChoixFichier.add(lblExplications, gbcLblExplications);

		// Explication : contrainte sur les baux en cours
		JLabel lblExplicationsDeux = new JLabel("    Les loyers importés doivent concerner des baux en cours.");
		lblExplicationsDeux.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbcLblExplicationsDeux = new GridBagConstraints();
		gbcLblExplicationsDeux.anchor = GridBagConstraints.LINE_START;
		gbcLblExplicationsDeux.insets = new Insets(0, 0, 5, 0);
		gbcLblExplicationsDeux.gridx = 0;
		gbcLblExplicationsDeux.gridy = 1;
		panelChoixFichier.add(lblExplicationsDeux, gbcLblExplicationsDeux);

		// Explication : contrainte sur le locataire
		JLabel lblExplicationsTrois = new JLabel(
				"    Le locataire précisé dans le fichier doit être celui du bail en cours.");
		GridBagConstraints gbcLblExplicationsTrois = new GridBagConstraints();
		gbcLblExplicationsTrois.anchor = GridBagConstraints.LINE_START;
		gbcLblExplicationsTrois.insets = new Insets(0, 0, 5, 0);
		gbcLblExplicationsTrois.gridx = 0;
		gbcLblExplicationsTrois.gridy = 2;
		panelChoixFichier.add(lblExplicationsTrois, gbcLblExplicationsTrois);

		// Label vide pour l'espacement
		JLabel lblExplicationsQuatre = new JLabel("");
		GridBagConstraints gbcLblExplicationsQuatre = new GridBagConstraints();
		gbcLblExplicationsQuatre.anchor = GridBagConstraints.LINE_START;
		gbcLblExplicationsQuatre.insets = new Insets(0, 0, 5, 0);
		gbcLblExplicationsQuatre.gridx = 0;
		gbcLblExplicationsQuatre.gridy = 3;
		panelChoixFichier.add(lblExplicationsQuatre, gbcLblExplicationsQuatre);

		// Bouton pour ouvrir le sélecteur de fichier CSV
		JButton btnChoisirFichier = new JButton("Choisir fichier");
		GridBagConstraints gbcBtnChoisirFichier = new GridBagConstraints();
		gbcBtnChoisirFichier.anchor = GridBagConstraints.WEST;
		gbcBtnChoisirFichier.insets = new Insets(MARGE, MARGE, MARGE, MARGE);
		gbcBtnChoisirFichier.gridx = 0;
		gbcBtnChoisirFichier.gridy = 4;
		panelChoixFichier.add(btnChoisirFichier, gbcBtnChoisirFichier);
		btnChoisirFichier.addActionListener(this.gestionClics);

		// Panel d'affichage du fichier sélectionné
		JPanel panelAfficherChoix = new JPanel();
		panelCentre.add(panelAfficherChoix, BorderLayout.SOUTH);

		JLabel lblSelection = new JLabel("Fichier sélectionné :");
		lblSelection.setHorizontalAlignment(SwingConstants.LEFT);
		panelAfficherChoix.add(lblSelection);

		// Label affichant le nom du fichier (initialement "Aucun")
		lblNomFichier = new JLabel("Aucun");
		panelAfficherChoix.add(lblNomFichier);

		// Panel du bas : boutons valider et annuler
		JPanel panelBoutons = new JPanel();
		getContentPane().add(panelBoutons, BorderLayout.SOUTH);

		// Bouton pour valider l'importation
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(gestionClics);
		panelBoutons.add(btnValider);

		// Bouton pour annuler et fermer la fenêtre
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(gestionClics);
		panelBoutons.add(btnAnnuler);

	}

	/**
	 * Récupère le label affichant le nom du fichier CSV sélectionné.
	 * 
	 * @return le label du nom du fichier
	 */
	public JLabel getLblNomFichier() {
		return lblNomFichier;
	}

	/**
	 * Modifie le label affichant le nom du fichier CSV sélectionné.
	 * 
	 * @param lblNomFichier le nouveau label
	 */
	public void setLblNomFichier(JLabel lblNomFichier) {
		this.lblNomFichier = lblNomFichier;
	}

}
