package controleur.consulter_informations.fen_batiment;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Assurance;
import modele.dao.DaoAssurance;
import vue.consulter_informations.FenBatimentInformations;

public class GestionTableAssFenBatInfos {

	private FenBatimentInformations fenetreTable;
	private DefaultTableModel modelTableAss;
	private DaoAssurance daoAss;

	/**
	 * Initialise la classe de gestion de la table d'assurances liées à un batiment
	 * 
	 * @param fenetreTable fenetre des informations du batiment contenant la table
	 */
	public GestionTableAssFenBatInfos(FenBatimentInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		daoAss = new DaoAssurance();
		this.modelTableAss = (DefaultTableModel) fenetreTable.getTableAssurances().getModel();
	}

	/**
	 * Met à 0 la table des assurances et y écrit les assurances du batiment
	 */
	public void ecrireLigneTable() {
		List<Assurance> tableauAss;
		try {
			modelTableAss.setRowCount(0);
			tableauAss = (List<Assurance>) daoAss.findAllByIdBat(fenetreTable.getSelectBatiment().getIdBat());
			for (Assurance a : tableauAss) {
				modelTableAss.addRow(new Object[] { a.getNumPoliceAssurance(), a.getTypeContrat(),
						a.getAnneeCouverture(), a.getMontantPaye() });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Ajustement automatique de la taille du tableau
		JTable table = fenetreTable.getTableAssurances();
		int nbLignes = Math.min(modelTableAss.getRowCount(), 10);
		int hauteurLigne = table.getRowHeight();

		table.setPreferredScrollableViewportSize(
				new Dimension(table.getPreferredScrollableViewportSize().width, nbLignes * hauteurLigne));

		table.revalidate();
		table.repaint();
		if (table.getParent() != null) {
			table.getParent().revalidate();
			table.getParent().repaint();
		}

		fenetreTable.revalidate();
		fenetreTable.repaint();
	}

}