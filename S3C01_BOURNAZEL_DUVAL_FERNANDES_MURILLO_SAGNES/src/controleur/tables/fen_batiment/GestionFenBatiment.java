package controleur.tables.fen_batiment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import vue.ajouter.FenAjouterBatiment;
import vue.tables.FenBatiment;

public class GestionFenBatiment implements ActionListener {

	private FenBatiment fb;

	/**
	 * Initialise la classe permettant la gestion de la fenetre contenant la liste
	 * des paiements
	 * 
	 * @param fb fenetre contenant la liste
	 */
	public GestionFenBatiment(FenBatiment fb) {
		this.fb = fb;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		switch (itemSelectionne.getText()) {
		case "Ajouter un bâtiment":
			FenAjouterBatiment frame = new FenAjouterBatiment(fb);
			frame.setVisible(true);
			break;
		case "Quitter":
			this.fb.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

}
