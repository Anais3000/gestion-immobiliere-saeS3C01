package controleur.tables.fen_organisme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import vue.ajouter.FenAjouterOrganisme;
import vue.tables.FenOrganisme;

public class GestionFenOrganisme implements ActionListener {

	FenOrganisme fO;

	/**
	 * Initialise la classe permettant la gestion de la fenetre contenant la liste
	 * des organismes
	 * 
	 * @param fO fenetre contenant la liste
	 */
	public GestionFenOrganisme(FenOrganisme fO) {
		this.fO = fO;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		switch (itemSelectionne.getText()) {
		case "Ajouter un organisme":
			FenAjouterOrganisme fAO = new FenAjouterOrganisme(fO);
			fAO.setVisible(true);
			break;
		case "Quitter":
			this.fO.dispose();
			break;
		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

}
