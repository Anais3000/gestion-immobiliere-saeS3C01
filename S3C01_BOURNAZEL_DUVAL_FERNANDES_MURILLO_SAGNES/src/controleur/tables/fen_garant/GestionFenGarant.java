package controleur.tables.fen_garant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Garant;
import modele.Historique;
import modele.dao.DaoGarant;
import vue.ajouter.FenChoisirOuAjouterGarant;
import vue.tables.FenGarant;

public class GestionFenGarant implements ActionListener {

	private FenGarant f;
	private DaoGarant daoG;

	/**
	 * Initialise la classe permettant la gestion de la fenetre contenant la liste
	 * des garants
	 * 
	 * @param f fenetre contenant la liste
	 */
	public GestionFenGarant(FenGarant f) {
		this.f = f;
		daoG = new DaoGarant();
	}

	/**
	 * Retourne l'objet de type Garant correspondant au numéro de ligne spécifié
	 * 
	 * @param numeroLigne numéro de ligne de la table
	 * @return l'objet de type Garant correspondant
	 * 
	 * @throws SQLException
	 */
	public Garant lireLigneTableGarant(int numeroLigne) throws SQLException {
		String idGarant = (String) this.f.getTableGarant().getValueAt(numeroLigne, 0);
		return this.daoG.findById(idGarant);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		switch (itemSelectionne.getText()) {
		case "Ajouter un garant":
			FenChoisirOuAjouterGarant frame = new FenChoisirOuAjouterGarant(f);
			frame.setVisible(true);
			break;
		case "Supprimer le garant sélectionné":
			int confirm = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cet ce garant ?",
					"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {
				int selectedRow = this.f.getTableGarant().getSelectedRow();
				try {
					Garant selectGarant = this.lireLigneTableGarant(selectedRow);
					daoG.delete(selectGarant);
					GestionTableHistoriqueFenPrinc.ajouterHistorique(
							new Historique(LocalDateTime.now(), "Supprimer Garant", "- " + selectGarant.getIdGarant()));
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
			this.f.getGestionTable().rechargerGarants();

			break;
		case "Quitter":
			this.f.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

}
