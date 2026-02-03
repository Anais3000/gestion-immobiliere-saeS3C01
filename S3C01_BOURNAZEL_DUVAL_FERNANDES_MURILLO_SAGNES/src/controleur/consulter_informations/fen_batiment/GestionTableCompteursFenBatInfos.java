package controleur.consulter_informations.fen_batiment;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Compteur;
import modele.dao.DaoCompteur;
import vue.consulter_informations.FenBatimentInformations;

public class GestionTableCompteursFenBatInfos {

	private FenBatimentInformations fenetreTable;
	private DefaultTableModel modelTableCompt;
	private DaoCompteur daoCompteur;

	/**
	 * Initialise la classe de gestion de la table des compteurs liés à un batiment
	 * 
	 * @param fenetreTable fenetre des informations du batiment contenant la table
	 */
	public GestionTableCompteursFenBatInfos(FenBatimentInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		daoCompteur = new DaoCompteur();
		this.modelTableCompt = (DefaultTableModel) fenetreTable.getTableCompteurs().getModel();
	}

	/**
	 * Met à 0 la table des biens et y écrit les biens présents dans le batiment
	 */
	public void ecrireLigneTable() {
		List<Compteur> tableauCompt;
		try {
			modelTableCompt.setRowCount(0);
			tableauCompt = (List<Compteur>) daoCompteur.findAllByIdBat(fenetreTable.getSelectBatiment().getIdBat());
			for (Compteur c : tableauCompt) {
				modelTableCompt.addRow(
						new Object[] { c.getIdCompteur(), c.getTypeCompteur(), c.getDateInstallation().toString() });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Ajustement automatique de la taille du tableau
		JTable table = fenetreTable.getTableCompteurs();
		int nbLignes = Math.min(modelTableCompt.getRowCount(), 10);
		int hauteurLigne = table.getRowHeight();

		table.setPreferredScrollableViewportSize(
				new Dimension(table.getPreferredScrollableViewportSize().width, nbLignes * hauteurLigne));

		// Notifier le modèle que les données ont changé
		modelTableCompt.fireTableDataChanged();

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