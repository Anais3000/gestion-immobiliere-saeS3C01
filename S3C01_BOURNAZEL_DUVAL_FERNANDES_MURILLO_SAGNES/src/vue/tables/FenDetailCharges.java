package vue.tables;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controleur.tables.fen_detail_charges.GestionFenDetailCharges;
import controleur.tables.fen_detail_charges.GestionTableFenDetailCharges;
import modele.BienLouable;
import vue.FenEffectuerRegularisation;
import vue.Polices;

/**
 * Fenêtre d'affichage des détails des charges pour un bien louable. Cette
 * fenêtre permet de consulter les charges associées à un bien louable
 * sélectionné dans le cadre d'une régularisation. Elle affiche un tableau
 * contenant les informations détaillées sur chaque charge (libellé, date,
 * coefficient, montants).
 */
public class FenDetailCharges extends JFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JPanel panelTable; // Panel principal de la fenêtre
	private JTable table; // Table affichant les détails des charges
	private JScrollPane scrollPane; // Panel de défilement pour la table
	private JPanel panelQuitter; // Panel du bas contenant le bouton quitter
	private JButton btnQuitter; // Bouton pour quitter la fenêtre
	private transient BienLouable selectBien; // Bien louable sélectionné
	private FenEffectuerRegularisation fenAncetre; // Fenêtre parente

	// Contrôleurs
	private transient GestionFenDetailCharges gestionClic; // Contrôleur des événements des boutons
	private transient GestionTableFenDetailCharges gestionTableCharges; // Contrôleur des événements de la table

	/**
	 * Constructeur de la fenêtre des détails des charges. Initialise l'interface
	 * graphique avec une table des charges et un bouton Quitter.
	 *
	 * @param fenAncetre La fenêtre parente FenEffectuerRegularisation
	 * @param selectBien Le bien louable sélectionné
	 * @throws SQLException En cas d'erreur lors de l'accès à la base de données
	 */
	public FenDetailCharges(FenEffectuerRegularisation fenAncetre, BienLouable selectBien) throws SQLException {

		this.fenAncetre = fenAncetre;
		this.selectBien = selectBien;
		gestionClic = new GestionFenDetailCharges(this);
		setBounds(100, 100, 800, 500);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// ------------------------------- Les panels
		// ------------------------------------
		JPanel panelHaut = new JPanel();
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setBorder(new EmptyBorder(20, 10, 20, 10));
		panelHaut.setLayout(new BorderLayout(0, 0));

		// Titre
		JLabel lblTitre = new JLabel("Détails des Charges");
		panelHaut.add(lblTitre, BorderLayout.WEST);
		lblTitre.setFont(Polices.TITRE.getFont());

		// Panel central avec la table
		panelTable = new JPanel();
		getContentPane().add(panelTable, BorderLayout.CENTER);
		panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.X_AXIS));

		scrollPane = new JScrollPane();
		panelTable.add(scrollPane);

		// ----------------------------- Table ----------------------
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null }, { null, null, null, null, null },
						{ null, null, null, null, null }, { null, null, null, null, null },
						{ null, null, null, null, null }, { null, null, null, null, null }, },
				new String[] { "Libell\u00E9", "Date Facturation", "Coefficient (%)", "Montant", "Montant Calculé" }) {
			Class[] columnTypes = new Class[] { Integer.class, Object.class, Object.class, Object.class, Object.class };

			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(2).setPreferredWidth(96);
		scrollPane.setViewportView(table);
		gestionTableCharges = new GestionTableFenDetailCharges(this, this.selectBien);

		// ----------------------------- Bouton quitter ----------------------
		panelQuitter = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelQuitter.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panelQuitter, BorderLayout.SOUTH);

		btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(gestionClic);
		panelQuitter.add(btnQuitter);
	}

	/**
	 * Retourne la table des charges.
	 *
	 * @return La table JTable contenant les détails des charges
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * Retourne la table des charges (alias de getTable()).
	 *
	 * @return La table JTable contenant les détails des charges
	 */
	public JTable getTableCharges() {
		return this.table;
	}

	/**
	 * Retourne le contrôleur de gestion des clics.
	 *
	 * @return Le contrôleur GestionFenDetailCharges
	 */
	public GestionFenDetailCharges getGestionClic() {
		return gestionClic;
	}

	/**
	 * Retourne le contrôleur de gestion de la table des charges.
	 *
	 * @return Le contrôleur GestionTableFenDetailCharges
	 */
	public GestionTableFenDetailCharges getGestionTableCharges() {
		return gestionTableCharges;
	}

	/**
	 * Retourne la fenêtre parente FenEffectuerRegularisation.
	 *
	 * @return La fenêtre parente
	 */
	public FenEffectuerRegularisation getFenAncetre() {
		return fenAncetre;
	}

}
