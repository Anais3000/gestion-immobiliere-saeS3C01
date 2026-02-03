package controleur.tables;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Historique;
import modele.Paiement;
import modele.dao.DaoPaiement;
import vue.ajouter.FenAjouterPaiementOccasionnel;
import vue.tables.FenPaiement;

public class GestionFenPaiement implements ActionListener {
	private FenPaiement fP;
	private DaoPaiement daoP;

	/**
	 * Initialise la classe permettant la gestion de la fenetre contenant la liste
	 * des paiements
	 * 
	 * @param fP fenetre contenant la liste
	 */
	public GestionFenPaiement(FenPaiement fP) {
		this.fP = fP;
		daoP = new DaoPaiement();
	}

	/**
	 * Retourne l'objet paiement associé à la ligne sélectionnée
	 * 
	 * @param numeroLigne numéro de ligne sélectionné
	 * @return l'objet de type paiement
	 * @throws SQLException
	 */
	public Paiement lireLigneTablePaiement(int numeroLigne) throws SQLException {
		String idPaiement = (String) this.fP.getTablePaiements().getModel().getValueAt(numeroLigne, 0);
		return this.daoP.findById(idPaiement);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		switch (itemSelectionne.getText()) {
		case "Ajouter un paiement":
			FenAjouterPaiementOccasionnel frame = new FenAjouterPaiementOccasionnel(this.fP);
			frame.setVisible(true);
			break;
		case "Quitter":
			this.fP.dispose();
			break;
		case "Supprimer le paiement sélectionné":
			// Fenêtre de confirmation
			int confirm = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce paiement ?",
					"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {
				int selectedRow = this.fP.getTablePaiements().getSelectedRow();
				try {
					Paiement selectPaiement = this.lireLigneTablePaiement(selectedRow);
					daoP.delete(selectPaiement);
					GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
							"Supprimer paiement", "- " + selectPaiement.getLibelle()));
					fP.getGestionTable().appliquerFiltres();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}
}
