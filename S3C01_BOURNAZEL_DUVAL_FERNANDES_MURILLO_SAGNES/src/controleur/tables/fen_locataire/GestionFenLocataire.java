package controleur.tables.fen_locataire;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import vue.ajouter.FenAjouterLocataire;
import vue.tables.FenLocataire;

public class GestionFenLocataire implements ActionListener {

	private FenLocataire f;

	/**
	 * Initialise la classe permettant la gestion de la fenetre contenant la liste
	 * des locataires
	 * 
	 * @param f fenetre contenant la liste
	 */
	public GestionFenLocataire(FenLocataire f) {
		this.f = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		switch (itemSelectionne.getText()) {
		case "Ajouter un locataire":
			FenAjouterLocataire frame = new FenAjouterLocataire(f);
			frame.setVisible(true);
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