package controleur.fen_principal;

import java.awt.Dimension;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Historique;
import modele.dao.DaoHistorique;
import vue.FenetrePrincipale;

public class GestionTableHistoriqueFenPrinc implements ListSelectionListener {

	private static FenetrePrincipale fenPrincipal;
	private static DefaultTableModel modeleTableHistoriqueFenPrinc;
	private static DaoHistorique daoHistorique;

	/**
	 * Initialise la classe de gestion de la table d'historique des actions
	 * effectuées sur l'application
	 * 
	 * @param f                             fenetre principale qui contient cette
	 *                                      table d'historique
	 * @param modeleTableHistoriqueFenPrinc modèle de la table d'historique
	 */
	public GestionTableHistoriqueFenPrinc(FenetrePrincipale f, DefaultTableModel modeleTableHistoriqueFenPrinc) {
		GestionTableHistoriqueFenPrinc.fenPrincipal = f;
		GestionTableHistoriqueFenPrinc.modeleTableHistoriqueFenPrinc = modeleTableHistoriqueFenPrinc;
		GestionTableHistoriqueFenPrinc.daoHistorique = new DaoHistorique();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			// Les codes sont ci-dessous
		}
	}

	/**
	 * Vide la table et écrit les dernières actions effectuées sur l'application
	 */
	public static void ecrireLigneTableHistorique() {
		try {
			modeleTableHistoriqueFenPrinc.setRowCount(0);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			for (Historique h : daoHistorique.findAll()) {
				modeleTableHistoriqueFenPrinc
						.addRow(new Object[] { h.getDate().format(formatter), h.getAction(), h.getDetails() });
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Ajustement automatique de la taille du tableau
		JTable table = fenPrincipal.getTableHistorique();
		int nbLignes = Math.min(modeleTableHistoriqueFenPrinc.getRowCount(), 5);
		int hauteurLigne = table.getRowHeight();

		table.setPreferredScrollableViewportSize(
				new Dimension(table.getPreferredScrollableViewportSize().width, nbLignes * hauteurLigne));

		table.revalidate();
	}

	/**
	 * Fonction permettant d'ajouter une ligne d'historique dans la tabe
	 * d'historiques
	 * 
	 * @param h objet de type Historique à rajouter
	 * @throws SQLException
	 */
	public static void ajouterHistorique(Historique h) throws SQLException {
		GestionTableHistoriqueFenPrinc.daoHistorique.create(h);
		GestionTableHistoriqueFenPrinc.ecrireLigneTableHistorique();
		GestionTableSituationMoisFenPrincipal.actualisationChampsPageAcceuil();
	}

}
