package controleur.documents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.documents.FenContratsDeLocation;

public class GestionFenContratsDeLocation implements ActionListener {

	private FenContratsDeLocation fCDL;

	/**
	 * Initialise la classe de gestion de la fenêtre des contrats de location
	 * 
	 * @param fCDL fenetre des contrats de location
	 */
	public GestionFenContratsDeLocation(FenContratsDeLocation fCDL) {
		this.fCDL = fCDL;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		if (itemSelectionne.getText().equals("Quitter")) {
			this.fCDL.dispose();
		}

	}

}
