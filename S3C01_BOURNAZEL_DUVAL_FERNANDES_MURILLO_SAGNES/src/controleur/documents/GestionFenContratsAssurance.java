package controleur.documents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.documents.FenContratsAssurance;

public class GestionFenContratsAssurance implements ActionListener {

	private FenContratsAssurance fCA;

	/**
	 * Initialise la classe de gestion de la fenêtre des contrats d'assurances
	 * 
	 * @param fCA fenetre des contrats d'assurance
	 */
	public GestionFenContratsAssurance(FenContratsAssurance fCA) {
		this.fCA = fCA;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		if (itemSelectionne.getText().equals("Quitter")) {
			this.fCA.dispose();
		}
	}
}
