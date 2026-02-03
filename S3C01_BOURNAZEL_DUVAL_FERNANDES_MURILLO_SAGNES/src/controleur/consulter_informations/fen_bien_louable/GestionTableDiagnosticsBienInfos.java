package controleur.consulter_informations.fen_bien_louable;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Diagnostic;
import modele.dao.DaoDiagnostic;
import vue.consulter_informations.FenBienLouableInformations;

public class GestionTableDiagnosticsBienInfos implements ListSelectionListener {

	private FenBienLouableInformations fenetreTable;
	private DefaultTableModel modelTableDiag;
	private DaoDiagnostic daoDiag;

	/**
	 * Initialise la classe de gestion de la table des diagnostics d'un bien
	 * 
	 * @param fenetreTable fenetre contenant la table
	 */
	public GestionTableDiagnosticsBienInfos(FenBienLouableInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		daoDiag = new DaoDiagnostic();
		this.modelTableDiag = (DefaultTableModel) fenetreTable.getTableDiagnostics().getModel();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Seules les methodes ci-dessous sont utilisées
	}

	/**
	 * Remet à 0 la table contenant les diagnostics puis la remplit
	 * 
	 * @param idbien id du bien concerné
	 */
	public void ecrireLigneTable(String idbien) {
		List<Diagnostic> tableauDiag;
		try {
			modelTableDiag.setRowCount(0);
			tableauDiag = (List<Diagnostic>) daoDiag.findAllByIdBien(idbien);
			for (Diagnostic d : tableauDiag) {
				modelTableDiag.addRow(new Object[] { d.getLibelle(), String.valueOf(d.getDateDebut()),
						String.valueOf(d.getDateFin()) });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Ajustement automatique de la taille du tableau
		JTable table = fenetreTable.getTableDiagnostics();
		int nbLignes = Math.min(modelTableDiag.getRowCount(), 10);
		int hauteurLigne = table.getRowHeight();

		table.setPreferredScrollableViewportSize(
				new Dimension(table.getPreferredScrollableViewportSize().width, nbLignes * hauteurLigne));

		// Notifier le modèle que les données ont changé
		modelTableDiag.fireTableDataChanged();

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