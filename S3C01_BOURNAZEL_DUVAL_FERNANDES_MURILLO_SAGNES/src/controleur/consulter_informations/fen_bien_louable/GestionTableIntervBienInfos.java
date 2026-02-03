package controleur.consulter_informations.fen_bien_louable;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Intervention;
import modele.dao.DaoIntervention;
import vue.consulter_informations.FenBienLouableInformations;

public class GestionTableIntervBienInfos implements ListSelectionListener {

	private FenBienLouableInformations fenetreTable;
	private DefaultTableModel modelTableInterv;
	private DaoIntervention daoInterv;

	/**
	 * Initialise la classe de gestion de la table des interventions d'un bien
	 * 
	 * @param fenetreTable fenetre contenant la table
	 */
	public GestionTableIntervBienInfos(FenBienLouableInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		daoInterv = new DaoIntervention();
		this.modelTableInterv = (DefaultTableModel) fenetreTable.getTableInterventions().getModel();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Seules les methodes ci-dessous sont utilisées
	}

	/**
	 * Remet à 0 la table contenant les informations puis la remplit
	 * 
	 * @param idbien id du bien concerné
	 */
	public void ecrireLigneTable(String idbien) {
		List<Intervention> tableauInterv;
		try {
			modelTableInterv.setRowCount(0);
			tableauInterv = (List<Intervention>) daoInterv.findAllByIdBien(idbien);
			for (Intervention i : tableauInterv) {
				modelTableInterv.addRow(new Object[] { i.getIdIntervention(), i.getIntitule(), i.getNumFacture(),
						i.getMontantFacture(), i.getNumDevis(), i.getMontantDevis(), i.getDateIntervention() });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Ajustement automatique de la taille du tableau
		JTable table = fenetreTable.getTableInterventions();
		int nbLignes = Math.min(modelTableInterv.getRowCount(), 10);
		int hauteurLigne = table.getRowHeight();

		table.setPreferredScrollableViewportSize(
				new Dimension(table.getPreferredScrollableViewportSize().width, nbLignes * hauteurLigne));

		// Notifier le modèle que les données ont changé
		modelTableInterv.fireTableDataChanged();

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