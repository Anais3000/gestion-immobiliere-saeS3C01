package controleur.consulter_informations.fen_organisme;

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
import vue.consulter_informations.FenOrganismeInformations;

public class GestionTableIntervOrganisme implements ListSelectionListener {

	private FenOrganismeInformations fenetreTable;
	private DefaultTableModel modelTableInterv;
	private DaoIntervention daoInterv;

	/**
	 * Initialise la classe de gestion de la table des interventions d'un organisme
	 * 
	 * @param fenetreTable fenetre contenant la table des interventions
	 */
	public GestionTableIntervOrganisme(FenOrganismeInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		daoInterv = new DaoIntervention();
		this.modelTableInterv = (DefaultTableModel) fenetreTable.getTableInterventions().getModel();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Seule la méthode ci-dessous est utilisée
	}

	/**
	 * Vide entièrement la table des interventions puis la remplit
	 * 
	 * @param idOrganisme id de l'organisme associé aux interventions
	 */
	public void ecrireLigneTable(String idOrganisme) {
		List<Intervention> tableauInterv;
		try {
			modelTableInterv.setRowCount(0);
			tableauInterv = (List<Intervention>) daoInterv.findAllByIdOrganisme(idOrganisme);
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