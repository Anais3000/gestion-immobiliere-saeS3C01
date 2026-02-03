package controleur.consulter_informations.fen_bien_louable;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Locataire;
import modele.Louer;
import modele.dao.DaoLocataire;
import modele.dao.DaoLouer;
import vue.consulter_informations.FenBienLouableInformations;

public class GestionTableEtatsLieuxFenBLInfos implements ListSelectionListener {

	private FenBienLouableInformations fenetreTable;
	private DefaultTableModel modelTableEtatLieux;
	private DaoLouer daoLouer;
	private DaoLocataire daoLoc;

	/**
	 * Initialise la classe de gestion de la table des etats des lieux d'un bien
	 * 
	 * @param fenetreTable fenetre contenant la table
	 */
	public GestionTableEtatsLieuxFenBLInfos(FenBienLouableInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		daoLouer = new DaoLouer();
		daoLoc = new DaoLocataire();
		this.modelTableEtatLieux = (DefaultTableModel) fenetreTable.getTableAnciensEtatsLieux().getModel();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Seules les methodes ci-dessous sont utilisées
	}

	/**
	 * Remet à 0 la table des etats des lieux puis la remplit
	 * 
	 * @param idbien id du bien concerné
	 */
	public void ecrireLigneTable(String idbien) {
		List<Louer> tableauLouer;
		try {
			modelTableEtatLieux.setRowCount(0);
			tableauLouer = (List<Louer>) daoLouer.findAllByIdBien(idbien);
			for (Louer l : tableauLouer) {
				Locataire loc = daoLoc.findById(l.getIdLocataire());
				if (l.getDateEtatDesLieuxSortie() != null) { // On vérifie si jamais
					modelTableEtatLieux.addRow(
							new Object[] { l.getDateEtatDesLieuxSortie(), "Sortie", l.getIdLocataire(), loc.getNom() });
				}
				if (l.getDateEtatDesLieuxEntree() != null) {
					modelTableEtatLieux.addRow(
							new Object[] { l.getDateEtatDesLieuxEntree(), "Entrée", l.getIdLocataire(), loc.getNom() });
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Ajustement automatique de la taille du tableau
		JTable table = fenetreTable.getTableAnciensEtatsLieux();
		int nbLignes = Math.min(modelTableEtatLieux.getRowCount(), 10);
		int hauteurLigne = table.getRowHeight();

		table.setPreferredScrollableViewportSize(
				new Dimension(table.getPreferredScrollableViewportSize().width, nbLignes * hauteurLigne));

		// Notifier le modèle que les données ont changé
		modelTableEtatLieux.fireTableDataChanged();

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