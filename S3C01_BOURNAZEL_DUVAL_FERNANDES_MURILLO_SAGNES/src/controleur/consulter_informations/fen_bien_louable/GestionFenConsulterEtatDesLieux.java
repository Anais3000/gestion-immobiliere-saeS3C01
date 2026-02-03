package controleur.consulter_informations.fen_bien_louable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.consulter_informations.FenConsulterEtatDesLieux;

public class GestionFenConsulterEtatDesLieux implements ActionListener {

	FenConsulterEtatDesLieux fCEDL;

	/**
	 * Initialise la classe de gestion de la fenêtre permettant de consulter un état
	 * des lieux
	 * 
	 * @param fCEDL fenetre permettant cette consultation
	 */
	public GestionFenConsulterEtatDesLieux(FenConsulterEtatDesLieux fCEDL) {
		this.fCEDL = fCEDL;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();

		if (itemSelectionne.getText().equals("Quitter")) {

			this.fCEDL.dispose();

		}

	}

}
