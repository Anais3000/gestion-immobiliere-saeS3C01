package controleur.consulter_informations.fen_bien_louable;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Compteur;
import modele.dao.DaoCompteur;
import vue.consulter_informations.FenBienLouableInformations;

public class GestionTableCompteursBienInfos implements ListSelectionListener {

	private FenBienLouableInformations fenetreTable;
	private DefaultTableModel modelTableCompt;
	private DaoCompteur daoCompt;

	/**
	 * Initialise la classe de gestion de la table des compteurs d'un bien
	 * 
	 * @param fenetreTable fenetre contenant la table
	 */
	public GestionTableCompteursBienInfos(FenBienLouableInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		daoCompt = new DaoCompteur();
		this.modelTableCompt = (DefaultTableModel) fenetreTable.getTableCompteurs().getModel();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Seules les méthodes ci-dessous sont utilisées
	}

	/**
	 * Remet à 0 la table des compteur et la remplit
	 * 
	 * @param idbien id du bien concerné
	 */
	public void ecrireLigneTable(String idbien) {
		List<Compteur> tableauCompt;
		try {
			modelTableCompt.setRowCount(0);
			tableauCompt = (List<Compteur>) daoCompt.findAllByIdBien(idbien);
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