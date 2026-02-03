package controleur.consulter_informations.fen_bien_louable;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Paiement;
import modele.dao.DaoPaiement;
import vue.consulter_informations.FenBienLouableInformations;

public class GestionTableSoldeToutCompteBienInfos implements ListSelectionListener {

	private FenBienLouableInformations fen;
	private DefaultTableModel modelTableSTC;
	private DaoPaiement daoPaiement;

	/**
	 * Initialise la classe de gestion de la table des anciens soldes de tout compte
	 * d'un bien
	 * 
	 * @param fen fenetre contenant la table
	 */
	public GestionTableSoldeToutCompteBienInfos(FenBienLouableInformations fen) {
		this.fen = fen;
		this.modelTableSTC = (DefaultTableModel) this.fen.getTableHistoriqueSoldeToutCompte().getModel();
		this.daoPaiement = new DaoPaiement();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Seules les methodes ci-dessous sont utilisées
	}

	/**
	 * Remet à 0 la table des anciens soldes et la remplit
	 * 
	 * @param idbien id du bien concerné
	 */
	public void ecrireLigneTable(String idbien) {
		List<Paiement> tableauHistSTC;
		try {
			modelTableSTC.setRowCount(0);
			tableauHistSTC = (List<Paiement>) this.daoPaiement.findAllPaiementByidBienEtLibelle(idbien,
					"solde de tout compte");
			for (Paiement p : tableauHistSTC) {
				this.modelTableSTC.addRow(new Object[] { p.getDatePaiement(), p.getSensPaiement(),
						Math.round(p.getMontant() * 100.0) / 100.0 + "€" });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Ajustement automatique de la taille du tableau
		JTable table = this.fen.getTableHistoriqueSoldeToutCompte();
		int nbLignes = Math.min(modelTableSTC.getRowCount(), 10);
		int hauteurLigne = table.getRowHeight();

		table.setPreferredScrollableViewportSize(
				new Dimension(table.getPreferredScrollableViewportSize().width, nbLignes * hauteurLigne));
		table.revalidate();
	}

}
