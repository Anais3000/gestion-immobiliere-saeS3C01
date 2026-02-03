package controleur.consulter_informations.fen_batiment;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.dao.DaoChiffresCles;
import vue.consulter_informations.FenBatimentInformations;

public class GestionTableTotalTravaux implements ActionListener {

	private FenBatimentInformations fenetreTable;
	private DaoChiffresCles daoCC;
	DefaultTableModel modeletable;

	/**
	 * Initialise la classe de gestion de la table des totaux des travaux sur année
	 * liés à un batiment
	 * 
	 * @param fenetreTable fenetre des informations du batiment contenant la table
	 */
	public GestionTableTotalTravaux(FenBatimentInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		daoCC = new DaoChiffresCles();
		modeletable = (DefaultTableModel) fenetreTable.getTableTravaux().getModel();
	}

	public void ecrireLigneTable() {
		modeletable.setRowCount(0);
		String annee = String.valueOf(fenetreTable.getComboBoxAnnee().getSelectedItem());
		HashMap<String, Double> montants;
		try {
			montants = (HashMap<String, Double>) daoCC
					.totalMontantTravauxParBatimentEtAnnee(fenetreTable.getSelectBatiment().getIdBat(), annee);
			for (Map.Entry<String, Double> entry : montants.entrySet()) {
				String organisme = entry.getKey();
				Double total = entry.getValue();
				modeletable.addRow(new Object[] { organisme, total });
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Ajustement automatique de la taille du tableau
		JTable table = fenetreTable.getTableTravaux();
		int nbLignes = Math.min(modeletable.getRowCount(), 10);
		int hauteurLigne = table.getRowHeight();

		table.setPreferredScrollableViewportSize(
				new Dimension(table.getPreferredScrollableViewportSize().width, nbLignes * hauteurLigne));

		// Notifier le modèle que les données ont changé
		modeletable.fireTableDataChanged();

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

	@Override
	public void actionPerformed(ActionEvent e) {
		this.ecrireLigneTable();
	}
}