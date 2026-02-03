package controleur.consulter_informations.fen_bien_louable;

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
import modele.dao.DaoBienLouable;
import modele.dao.DaoChiffresCles;
import vue.consulter_informations.FenBienLouableInformations;

public class GestionTableTotalTravaux implements ActionListener {

	private FenBienLouableInformations fenetreTable;
	private DaoChiffresCles daoCC;
	private DaoBienLouable daoB;
	DefaultTableModel modeletable;

	/**
	 * Initialise la classe de gestion de la table des totaux de travaux par année
	 * d'un bien
	 * 
	 * @param fenetreTable fenetre contenant la table
	 */
	public GestionTableTotalTravaux(FenBienLouableInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		daoCC = new DaoChiffresCles();
		daoB = new DaoBienLouable();
		modeletable = (DefaultTableModel) fenetreTable.getTableTravaux().getModel();
	}

	/**
	 * Remet à 0 la table des travaux et la re remplit
	 */
	public void ecrireLigneTable() {
		modeletable.setRowCount(0);
		String annee = String.valueOf(fenetreTable.getComboBoxAnnee().getSelectedItem());
		HashMap<String, Double> montants;
		try {
			montants = (HashMap<String, Double>) daoCC
					.totalMontantTravauxParBienEtAnnee(fenetreTable.getSelectBien().getIdBien(), annee);
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
		ecrireLigneTable();
		setValeursLabels();
	}

	/**
	 * Met à jour la valeur des labels en fonction de l'année sélectionnée. Exemple
	 * : totaux des loyers, des provisions percues, etc.
	 */
	public void setValeursLabels() {
		String annee = String.valueOf(fenetreTable.getComboBoxAnnee().getSelectedItem());
		// bien (String) puis année (int)
		try {
			Float m = daoB.totalLoyersPercusParBienEtAnnee(fenetreTable.getSelectBien().getIdBien(), annee);
			String s = String.valueOf(m);
			fenetreTable.getLblValeurLoyers().setText(s);

			Float n = daoB.totalProvisionsPercuesParBienEtAnnee(fenetreTable.getSelectBien().getIdBien(), annee);
			String t = String.valueOf(n);
			fenetreTable.getLblValeurProvisions().setText(t);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}
}