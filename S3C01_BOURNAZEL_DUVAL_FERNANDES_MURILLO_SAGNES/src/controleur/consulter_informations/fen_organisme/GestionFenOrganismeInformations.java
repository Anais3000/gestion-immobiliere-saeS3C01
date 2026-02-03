package controleur.consulter_informations.fen_organisme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Historique;
import modele.dao.DaoOrganisme;
import vue.consulter_informations.FenOrganismeInformations;
import vue.modifier.FenModifierOrganisme;

public class GestionFenOrganismeInformations implements ActionListener {

	private FenOrganismeInformations fenetre;

	/**
	 * Initialise la classe de gestion de la fenêtre des informations d'un organisme
	 * 
	 * @param f fenetre permetant la consultation des informations
	 */
	public GestionFenOrganismeInformations(FenOrganismeInformations f) {
		this.fenetre = f;
		f.getGestionTableInterv().ecrireLigneTable(f.getSelectOrganisme().getNumSIRET());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Modifier informations":
			FenModifierOrganisme fenORI = new FenModifierOrganisme(this.fenetre.getSelectOrganisme(), this.fenetre);
			fenORI.setVisible(true);
			break;
		case "Quitter":
			this.fenetre.dispose();
			break;
		case "Supprimer l'organisme":
			// Fenêtre de confirmation
			int confirm = JOptionPane.showConfirmDialog(null,
					"Voulez-vous vraiment supprimer cet organisme ? Cela supprimera toutes les interventions associées.",
					"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {
				DaoOrganisme daoO = new DaoOrganisme();
				try {
					daoO.delete(fenetre.getSelectOrganisme());
					GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
							"Supprimer Organisme", "- " + this.fenetre.getSelectOrganisme().getNom()));
					this.fenetre.getFenLocAncetre().getGestionTable().ecrireLigneTableOrganisme();
					this.fenetre.dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
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
