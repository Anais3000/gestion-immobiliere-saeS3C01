package controleur.documents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.documents.FenDiagnostics;

public class GestionFenDiagnostics implements ActionListener {

	private FenDiagnostics fD;

	/**
	 * Initialise la classe de gestion de la fenêtre des diagnostics
	 * 
	 * @param fD fenetre des diagnostics
	 */
	public GestionFenDiagnostics(FenDiagnostics fD) {
		this.fD = fD;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		if (itemSelectionne.getText().equals("Quitter")) {
			this.fD.dispose();
		}
	}
}
