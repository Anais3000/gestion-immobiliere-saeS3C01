package controleur.consulter_informations.fen_bien_louable;

import java.awt.Dimension;
import java.time.LocalDate;
import java.util.ArrayList;
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

public class GestionTableAnciensLocBienInfos implements ListSelectionListener {

	private FenBienLouableInformations fenetreTable;
	private DefaultTableModel modelTableAnciensLocs;
	private DaoLouer daoLouer;
	private DaoLocataire daoLocataire;

	/**
	 * Initialise la classe de gestion de la table des anciens locataires d'un bien
	 * 
	 * @param fenetreTable fenetre contenant la table
	 */
	public GestionTableAnciensLocBienInfos(FenBienLouableInformations fenetreTable) {
		this.fenetreTable = fenetreTable;
		daoLouer = new DaoLouer();
		daoLocataire = new DaoLocataire();
		this.modelTableAnciensLocs = (DefaultTableModel) fenetreTable.getTableAnciensLocataires().getModel();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Methode non utilisée, les methodes dessous le sont
	}

	/**
	 * Remet à 0 la table des anciens locataires du bien et la remplit
	 * 
	 * @param idbien id du bien concerné
	 */
	public void ecrireLigneTable(String idbien) {
		List<Louer> tableauLouer;
		ArrayList<String> listeIdLoc = new ArrayList<>();
		try {
			modelTableAnciensLocs.setRowCount(0);
			tableauLouer = (List<Louer>) daoLouer.findAllByIdBien(idbien);
			for (Louer l : tableauLouer) {
				if (!(listeIdLoc.contains(l.getIdLocataire())) && l.getDateFin().isBefore(LocalDate.now())) { // Pour
																												// verif
																												// que
																												// la
																												// date
																												// de
																												// fin
																												// est
																												// avant
																												// ajd
					listeIdLoc.add(l.getIdLocataire()); // POUR NE PAS AVOIR 2 FOIS LE MEME LOC

					Locataire loc = daoLocataire.findById(l.getIdLocataire());

					modelTableAnciensLocs.addRow(new Object[] { String.valueOf(l.getDateFin()), loc.getIdLocataire(),
							loc.getNom(), loc.getPrenom(), loc.getNumTelephone() });
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Ajustement automatique de la taille du tableau
		JTable table = fenetreTable.getTableAnciensLocataires();
		int nbLignes = Math.min(modelTableAnciensLocs.getRowCount(), 10);
		int hauteurLigne = table.getRowHeight();

		table.setPreferredScrollableViewportSize(
				new Dimension(table.getPreferredScrollableViewportSize().width, nbLignes * hauteurLigne));

		// Notifier le modèle que les données ont changé
		modelTableAnciensLocs.fireTableDataChanged();

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