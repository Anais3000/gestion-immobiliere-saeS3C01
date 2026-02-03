package controleur.consulter_informations.fen_batiment;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import controleur.tables.fen_bien_louable.GestionTableFenBienLouable;
import modele.BienLouable;
import modele.dao.DaoBienLouable;
import vue.consulter_informations.FenBatimentInformations;

public class GestionTableBiensFenBatInfos implements ListSelectionListener {

	private FenBatimentInformations fenetreTable;
	private DefaultTableModel modelTableBiens;
	private DaoBienLouable daoBiens;

	/**
	 * Initialise la classe de gestion de la table de biens liés à un batiment
	 * 
	 * @param fenetreTable fenetre des informations du batiment contenant la table
	 */
	public GestionTableBiensFenBatInfos(FenBatimentInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		daoBiens = new DaoBienLouable();
		this.modelTableBiens = (DefaultTableModel) fenetreTable.getTableBiens().getModel();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Aucune action nécessaire lors de la sélection d'une ligne
		// La consultation se fait via le bouton "Consulter le bien"
	}

	/**
	 * Charge la fenêtre informations du bien louable sélectionné
	 */
	public void consulterBien() {
		JTable tableBiens = this.fenetreTable.getTableBiens();
		int selectedRow = tableBiens.getSelectedRow();
		if (selectedRow != -1) {
			String idBien = tableBiens.getValueAt(selectedRow, 0).toString();
			DaoBienLouable daoBien = new DaoBienLouable();
			BienLouable bien = null;
			try {
				bien = daoBien.findById(idBien);
				GestionTableFenBienLouable.chargerFenetreBienLouable(bien, null);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Met à 0 la table des assurances et y écrit les assurances du batiment
	 */
	public void ecrireLigneTable() {
		List<BienLouable> tableauBiens;
		try {
			modelTableBiens.setRowCount(0);
			tableauBiens = (List<BienLouable>) daoBiens.findAllByIdBat(fenetreTable.getSelectBatiment().getIdBat());
			for (BienLouable b : tableauBiens) {
				modelTableBiens.addRow(new Object[] { b.getIdBien(), b.getTypeBien(), b.getDateConstruction(),
						b.getSurfaceHabitable(), b.getNbPieces(), b.getNumeroFiscal() });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Ajustement automatique de la taille du tableau
		JTable table = fenetreTable.getTableBiens();
		int nbLignes = Math.min(modelTableBiens.getRowCount(), 10);
		int hauteurLigne = table.getRowHeight();

		table.setPreferredScrollableViewportSize(
				new Dimension(table.getPreferredScrollableViewportSize().width, nbLignes * hauteurLigne));

		// Notifier le modèle que les données ont changé
		modelTableBiens.fireTableDataChanged();

		// Revalider et repeindre la table et son conteneur parent
		table.revalidate();
		table.repaint();

		if (table.getParent() != null) {
			table.getParent().revalidate();
			table.getParent().repaint();
		}

		// Forcer le rafraîchissement de la fenêtre entière
		fenetreTable.revalidate();
		fenetreTable.repaint();
	}
}
