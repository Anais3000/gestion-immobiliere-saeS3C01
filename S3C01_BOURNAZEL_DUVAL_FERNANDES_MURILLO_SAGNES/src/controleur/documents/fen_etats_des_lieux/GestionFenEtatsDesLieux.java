package controleur.documents.fen_etats_des_lieux;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.documents.FenEtatsDesLieux;

public class GestionFenEtatsDesLieux implements ActionListener {

	private FenEtatsDesLieux fEDL;

	/**
	 * Initialise la classe de gestion de la fenetre de consultation des états des
	 * lieux
	 * 
	 * @param fEDL fenetre permettant la consultation
	 */
	public GestionFenEtatsDesLieux(FenEtatsDesLieux fEDL) {
		this.fEDL = fEDL;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		if (itemSelectionne.getText().equals("Quitter")) {
			this.fEDL.dispose();
		}
	}
}
