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
import modele.BienLouable;
import modele.Louer;
import modele.dao.DaoBienLouable;
import modele.dao.DaoLouer;
import vue.consulter_informations.FenLocataireInformations;

public class GestionTableBiensFenLocInfos implements ListSelectionListener {

	private FenLocataireInformations fenetreTable;
	private DaoLouer daoLouer;
	private DaoBienLouable daoBienLouable;
	private DefaultTableModel modeleTableBiensFenLocInfos;

	/**
	 * Initialise la classe de gestion du tableau des biens loués par un locataire
	 * 
	 * @param fenetreTable fenetre contenant le tableau
	 */
	public GestionTableBiensFenLocInfos(FenLocataireInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		this.daoLouer = new DaoLouer();
		this.daoBienLouable = new DaoBienLouable();
		this.modeleTableBiensFenLocInfos = (DefaultTableModel) fenetreTable.getTableBiens().getModel();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Seule la méthode ci-dessous est utilisée

	}

	/**
	 * Met à 0 la table contenant les biens loués puis la remplit
	 */
	public void ecrireLigneTableBienLouable() {
		try {
			Collection<Louer> collectionLouers = daoLouer
					.findAllByIdLoc(fenetreTable.getSelectLocataire().getIdLocataire());
			this.modeleTableBiensFenLocInfos.setRowCount(0);
			for (Louer l : collectionLouers) {
				BienLouable bien = daoBienLouable.findById(l.getIdBienLouable());
				this.modeleTableBiensFenLocInfos.addRow(new Object[] { bien.getIdBien(), bien.getLoyer(),
						bien.getProvisionPourCharges(), l.getDateFin() });
			}
			// Ajustement automatique de la taille du tableau
			JTable table = fenetreTable.getTableBiens();
			int nbLignes = Math.min(this.modeleTableBiensFenLocInfos.getRowCount(), 10);
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
