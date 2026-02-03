package controleur.consulter_informations.fen_batiment;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Compteur;
import modele.ReleveCompteur;
import modele.dao.DaoCompteur;
import modele.dao.DaoReleveCompteur;
import vue.consulter_informations.FenBatimentInformations;

public class GestionTableRelevFenBatInfos {

	private FenBatimentInformations fenetreTable;
	private DefaultTableModel modelTableRelev;
	private DaoReleveCompteur daoReleve;

	/**
	 * Initialise la classe de gestion de la table des relevés de compteur liés à un
	 * batiment
	 * 
	 * @param fenetreTable fenetre des informations du batiment contenant la table
	 */
	public GestionTableRelevFenBatInfos(FenBatimentInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		daoReleve = new DaoReleveCompteur();
		this.modelTableRelev = (DefaultTableModel) fenetreTable.getTableReleves().getModel();
	}

	public void ecrireLigneTable() {
		List<ReleveCompteur> tableauReleve;
		try {
			modelTableRelev.setRowCount(0);
			tableauReleve = (List<ReleveCompteur>) daoReleve
					.findAllByIdBat(fenetreTable.getSelectBatiment().getIdBat());
			for (ReleveCompteur r : tableauReleve) {

				double total;
				// Car si nouvel index inférieur alors c'est NOUVEAU compteur et ancien index =
				// 0
				if (r.getIndexCompteur() < r.getAncienIndex()) {
					total = r.getIndexCompteur() * r.getPrixParUnite() + r.getPartieFixe();
				} else {
					total = (r.getIndexCompteur() - r.getAncienIndex()) * r.getPrixParUnite() + r.getPartieFixe();
				}
				double totalArrondi = Math.round(total * 100.0) / 100.0;
				DaoCompteur daoCompteur = new DaoCompteur();
				Compteur cpt = daoCompteur.findById(r.getIdCompteur());
				modelTableRelev.addRow(new Object[] { cpt.getIdCompteur(), r.getDateReleve(), r.getIndexCompteur(),
						r.getPrixParUnite(), r.getPartieFixe(), totalArrondi + "€" });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Ajustement automatique de la taille du tableau
		JTable table = fenetreTable.getTableReleves();
		int nbLignes = Math.min(modelTableRelev.getRowCount(), 10);
		int hauteurLigne = table.getRowHeight();

		table.setPreferredScrollableViewportSize(
				new Dimension(table.getPreferredScrollableViewportSize().width, nbLignes * hauteurLigne));

		// Notifier le modèle que les données ont changé
		modelTableRelev.fireTableDataChanged();

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