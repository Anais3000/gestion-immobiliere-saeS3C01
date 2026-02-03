package controleur.consulter_informations.fen_locataire;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Paiement;
import modele.dao.DaoPaiement;
import vue.consulter_informations.FenLocataireInformations;

public class GestionTablePaimentsFenLocInfos implements ListSelectionListener {

	private FenLocataireInformations fenetreTable;
	private DefaultTableModel modeleTablePaiementsFenLocInfos;
	private DaoPaiement daoPaiement;

	/**
	 * Initialise la classe de gestion du tableau des paiements effectués par un
	 * locataire
	 * 
	 * @param fenetreTable fenetre contenant le tableau
	 */
	public GestionTablePaimentsFenLocInfos(FenLocataireInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		this.modeleTablePaiementsFenLocInfos = (DefaultTableModel) fenetreTable.getTablePaiements().getModel();
		daoPaiement = new DaoPaiement();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Seule la méthode ci-dessous est utilisée

	}

	/**
	 * Met à 0 la table des paiements puis la remplit
	 */
	public void ecrireLigneTablePaiements() {
		try {
			Collection<Paiement> collectionPaiements = daoPaiement
					.findAllByIdLoc(this.fenetreTable.getSelectLocataire().getIdLocataire());
			this.modeleTablePaiementsFenLocInfos.setRowCount(0);
			for (Paiement p : collectionPaiements) { // Il faudra vérifié quand on aura plus de donnée si le l'attribut
														// p.getIdIntervention() null
				this.modeleTablePaiementsFenLocInfos.addRow(new Object[] { p.getIdPaiement(), p.getLibelle(),
						p.getDatePaiement(), p.getMoisConcerne(), p.getMontant(), p.getIdBien() });
			}
			// Ajustement automatique de la taille du tableau
			JTable table = fenetreTable.getTablePaiements();
			int nbLignes = Math.min(this.modeleTablePaiementsFenLocInfos.getRowCount(), 10);
			int hauteurLigne = table.getRowHeight();

			table.setPreferredScrollableViewportSize(
					new Dimension(table.getPreferredScrollableViewportSize().width, nbLignes * hauteurLigne));
			table.revalidate();
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
