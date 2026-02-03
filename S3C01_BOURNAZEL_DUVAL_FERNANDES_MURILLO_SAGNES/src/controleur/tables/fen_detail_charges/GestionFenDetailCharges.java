package controleur.tables.fen_detail_charges;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.tables.FenDetailCharges;

public class GestionFenDetailCharges implements ActionListener {

	private FenDetailCharges f;

	/**
	 * Initialise la classe permettant la gestion de la fenetre contenant le détail
	 * des charges lors d'une régularisation
	 * 
	 * @param f fenetre contenant le détail des charges
	 */
	public GestionFenDetailCharges(FenDetailCharges f) {
		this.f = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		if (itemSelectionne.getText().equals("Quitter")) {
			this.f.dispose();
		}
	}
}
