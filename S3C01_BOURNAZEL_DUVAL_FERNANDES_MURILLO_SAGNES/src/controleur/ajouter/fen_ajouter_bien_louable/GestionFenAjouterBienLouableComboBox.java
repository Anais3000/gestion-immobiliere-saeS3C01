package controleur.ajouter.fen_ajouter_bien_louable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import controleur.Outils;
import modele.Batiment;
import modele.dao.DaoBatiment;
import vue.ajouter.FenAjouterBienLouable;

public class GestionFenAjouterBienLouableComboBox implements ActionListener {
	private FenAjouterBienLouable fenABL;
	private DaoBatiment daoBat;

	/**
	 * Initialise la classe de gestion de la combo box permettant de rafraichir les
	 * informations liées au batiment sélectionné lors de l'ajout d'un bien louable
	 * 
	 * @param fenABL fenetre d'ajout du bien louable
	 * @param daoBat objet d'accès aux données de la table sae_batiment
	 */
	public GestionFenAjouterBienLouableComboBox(FenAjouterBienLouable fenABL, DaoBatiment daoBat) {
		this.fenABL = fenABL;
		this.daoBat = daoBat;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String idBat = (String) this.fenABL.getComboBoxIDBatBL().getSelectedItem();
		try {
			Batiment bat = this.daoBat.findById(idBat);
			this.fenABL.setTextFieldAdresseBL(bat.getAdresse());
			this.fenABL.setTextFieldCodePostalBL(bat.getCodePostal());
			this.fenABL.setTextFieldVilleBL(bat.getVille());
			this.fenABL.setTextFieldDateConstruction(bat.getDateConstruction().toString());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Charge les id des batiments dans la combo-box de sélection du bâtiment lors
	 * de la création d'un bien louable
	 * 
	 * @throws SQLException
	 */
	public void chargerComboBoxIDBatBL() throws SQLException {
		List<Batiment> liste = (List<Batiment>) this.daoBat.findAll();
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (Batiment c : liste) {
			model.addElement(c.getIdBat());
		}
		this.fenABL.getComboBoxIDBatBL().setModel(model);
	}
}
