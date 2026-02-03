package controleur.documents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.documents.FenFacturesEtDevis;

public class GestionFenFacturesEtDevis implements ActionListener {

	private FenFacturesEtDevis fFED;

	/**
	 * Initialise la classe de gestion de la fenêtre des factures et devis
	 * 
	 * @param fFED fenetre des factures et devis
	 */
	public GestionFenFacturesEtDevis(FenFacturesEtDevis fFED) {
		this.fFED = fFED;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();

		if (itemSelectionne.getText().equals("Quitter")) {
			this.fFED.dispose();
		}

	}

}
