package controleur.fen_principal;

import java.awt.Dimension;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.BienLouable;
import modele.Louer;
import modele.Paiement;
import modele.dao.DaoBienLouable;
import modele.dao.DaoHistoriqueLoyer;
import modele.dao.DaoLouer;
import modele.dao.DaoPaiement;
import vue.FenetrePrincipale;

public class GestionTableSituationMoisFenPrincipal implements ListSelectionListener {

	private static FenetrePrincipale fenPrincipal;
	private static DefaultTableModel modeleTableSituationMoisFenPrinc;
	private static DaoLouer daoLouer;
	private static DaoBienLouable daoBienLouable;
	private static DaoPaiement daoPaiement;
	private static DaoHistoriqueLoyer daoHL;

	/**
	 * Initialise la classe de gestion de la table de situation du mois de la
	 * fenetre principale, contenant les loyers dûs de ce mois-ci par les
	 * locataires, ceux payés, etc..
	 * 
	 * @param f                                fenetre principale de l'application
	 * @param modeleTableSituationMoisFenPrinc modele de la table de situation du
	 *                                         mois
	 */
	public GestionTableSituationMoisFenPrincipal(FenetrePrincipale f,
			DefaultTableModel modeleTableSituationMoisFenPrinc) {
		GestionTableSituationMoisFenPrincipal.fenPrincipal = f;
		GestionTableSituationMoisFenPrincipal.modeleTableSituationMoisFenPrinc = modeleTableSituationMoisFenPrinc;
		GestionTableSituationMoisFenPrincipal.daoLouer = new DaoLouer();
		GestionTableSituationMoisFenPrincipal.daoBienLouable = new DaoBienLouable();
		GestionTableSituationMoisFenPrincipal.daoPaiement = new DaoPaiement();
		GestionTableSituationMoisFenPrincipal.daoHL = new DaoHistoriqueLoyer();

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Les méthodes se trouvent ci dessous

	}

	/**
	 * Vide la table de situation du mois et y insère les valeurs présentes en BD
	 */
	public static void ecrireLigneTableSituationDuMois() {
		try {
			modeleTableSituationMoisFenPrinc.setRowCount(0);
			for (Louer l : daoLouer.findAllActuel()) {
				BienLouable bl = daoBienLouable.findById(l.getIdBienLouable());
				Paiement p = daoPaiement.findByIdLocataireIdBien(l.getIdLocataire(), l.getIdBienLouable());

				Float ajustementLoyer = 0.0f;

				if (l.getMoisDebutAjustement() != null && l.getMoisFinAjustement() != null) {
					LocalDate debutAjust = l.getMoisDebutAjustement();
					LocalDate finAjust = l.getMoisFinAjustement();
					LocalDate ajd = LocalDate.now();

					boolean apresDebut = ajd.getYear() > debutAjust.getYear() || (ajd.getYear() == debutAjust.getYear()
							&& ajd.getMonthValue() >= debutAjust.getMonthValue());

					boolean avantFin = ajd.getYear() < finAjust.getYear()
							|| (ajd.getYear() == finAjust.getYear() && ajd.getMonthValue() <= finAjust.getMonthValue());

					if (apresDebut && avantFin) {
						ajustementLoyer = l.getAjustementLoyer();
					}
				}

				insererSituationMois(p, l, bl, ajustementLoyer);

			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Ajustement automatique de la taille du tableau
		JTable table = fenPrincipal.getTableSituationMois();
		int nbLignes = Math.min(modeleTableSituationMoisFenPrinc.getRowCount(), 5);
		int hauteurLigne = table.getRowHeight();

		table.setPreferredScrollableViewportSize(
				new Dimension(table.getPreferredScrollableViewportSize().width, nbLignes * hauteurLigne));

		table.revalidate();
	}

	/**
	 * Insere dans la table les valeurs supplémentaires pour la situation du mois
	 * 
	 * @param p               paiement à insérer
	 * @param l               location concernée
	 * @param bl              bien louable concerné
	 * @param ajustementLoyer ajustement de loyer (si il y a lieu)
	 */
	private static void insererSituationMois(Paiement p, Louer l, BienLouable bl, Float ajustementLoyer) {
		if (p == null) {
			modeleTableSituationMoisFenPrinc.addRow(new Object[] { l.getIdLocataire(), l.getIdBienLouable(),
					bl.getLoyer(), bl.getProvisionPourCharges(), ajustementLoyer, "", "Non Payé" });
		} else {
			modeleTableSituationMoisFenPrinc.addRow(new Object[] { l.getIdLocataire(), l.getIdBienLouable(),
					bl.getLoyer(), bl.getProvisionPourCharges(), ajustementLoyer, p.getDatePaiement(), "Payé" });
		}
	}

	/**
	 * Permet d'actualiser les champs liés à la table de situation du mois Par
	 * exemple, le total des loyers encaissés pendant le mois en cours,...
	 */
	public static void actualisationChampsPageAcceuil() {
		try {
			GestionTableSituationMoisFenPrincipal.fenPrincipal.setTotalLoyersMois(
					Math.round(GestionTableSituationMoisFenPrincipal.daoHL.findTotalLoyerMoisActuel()));
			GestionTableSituationMoisFenPrincipal.fenPrincipal.setMontantEncaisse(
					Math.round(GestionTableSituationMoisFenPrincipal.daoHL.findTotalLoyerPayesMoisActuel()));
			GestionTableSituationMoisFenPrincipal.fenPrincipal.setLoyersImpayes(
					Math.round(GestionTableSituationMoisFenPrincipal.daoHL.findTotalLoyerImpayesMoisActuel()));
			GestionTableSituationMoisFenPrincipal.fenPrincipal.setBiensLouesTotal(
					GestionTableSituationMoisFenPrincipal.daoLouer.findAllActuel().size(),
					GestionTableSituationMoisFenPrincipal.daoBienLouable.findAll().size());
			GestionTableSituationMoisFenPrincipal.ecrireLigneTableSituationDuMois();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}

	}

}
