package controleur.tables.fen_bien_louable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import modele.IRL;
import modele.dao.DaoBatiment;
import modele.dao.DaoIRL;
import vue.ajouter.FenAjouterBienLouable;
import vue.ajouter.FenAjouterIRL;
import vue.tables.FenBienLouable;

public class GestionFenBienLouable implements ActionListener {

	private FenBienLouable f;

	/**
	 * Initialise la classe permettant la gestion de la fenetre contenant la liste
	 * des biens louables
	 * 
	 * @param f fenetre contenant la liste
	 */
	public GestionFenBienLouable(FenBienLouable f) {
		this.f = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		switch (itemSelectionne.getText()) {
		case "Ajouter un bien louable":

			// Teste si un batiment existe
			DaoBatiment daoBat = new DaoBatiment();
			try {
				if (daoBat.findAll().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Aucun bâtiment existant.\nVeuillez d'abord créer un bâtiment.",
							"Impossible d'ajouter un bien louable", JOptionPane.ERROR_MESSAGE);
					break;
				}
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			FenAjouterBienLouable frame = new FenAjouterBienLouable(null, null, this.f);

			frame.setVisible(true);
			break;
		case "Ajouter l'IRL":
			FenAjouterIRL frameIRL = new FenAjouterIRL(this.f, null);
			frameIRL.setVisible(true);
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

	/**
	 * Permet d'afficher l'IRL de la période en cours en haut de la fenêtre
	 */
	public void afficherIRL() {

		DaoIRL daoI = new DaoIRL();

		int anneeActuelle = LocalDate.now().getYear();
		int trimestreActuel = (LocalDate.now().getMonthValue() - 1) / 3 + 1;

		IRL irlActuel = null;
		try {
			irlActuel = daoI.findById(String.valueOf(anneeActuelle), String.valueOf(trimestreActuel));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (irlActuel == null) {
			this.f.getLblValeurIRL().setText("Valeur actuelle de l'IRL : aucune");
		} else {
			this.f.getLblValeurIRL().setText("Valeur actuelle de l'IRL : " + irlActuel.getValeur());
		}

	}
}
