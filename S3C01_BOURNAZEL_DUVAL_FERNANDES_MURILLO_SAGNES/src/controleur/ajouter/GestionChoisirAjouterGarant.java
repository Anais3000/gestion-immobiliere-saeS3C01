package controleur.ajouter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controleur.Outils;
import modele.Garant;
import modele.dao.DaoGarant;
import vue.ajouter.FenChoisirOuAjouterGarant;

public class GestionChoisirAjouterGarant implements ActionListener {
	private FenChoisirOuAjouterGarant g;

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter un garant
	 * 
	 * @param g fenêtre permettant l'ajout
	 */
	public GestionChoisirAjouterGarant(FenChoisirOuAjouterGarant g) {
		this.g = g;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((AbstractButton) e.getSource()).getText()) {
		case "Rechercher Garant":
			g.getRdbtnAjouterGarant().setSelected(false);
			g.getComboBoxRechercheGarant().setEnabled(true);
			// Grise les labels & textfields de Ajouter Garant
			for (JLabel l : g.getlLabelAjouter()) {
				l.setEnabled(false);
			}
			for (JTextField tf : g.getlJTextField()) {
				tf.setEnabled(false);
			}
			break;
		case "Ajouter Garant":
			// Grise le bouton Rechercher et la combo Rechercher
			g.getRdbtnRechercherGarant().setSelected(false);
			g.getComboBoxRechercheGarant().setEnabled(false);
			// dégrise les labels & textfields de Ajouter Garant
			for (JLabel l : g.getlLabelAjouter()) {
				l.setEnabled(true);
			}
			for (JTextField tf : g.getlJTextField()) {
				tf.setEnabled(true);
			}
			break;
		case "Valider":

			this.traiterTypeAjoutGarant();

			break;
		case "Annuler":
			this.g.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Charge les IDs des garants dans la combo box
	 */
	public void chargerGarants() {
		DaoGarant dao = new DaoGarant();
		List<Garant> liste = null;
		try {
			liste = (List<Garant>) dao.findAll();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (Garant garant : liste) {
			model.addElement(garant.getIdGarant());
		}
		g.getComboBoxRechercheGarant().setModel(model);
	}

	/**
	 * Traite la vérification des champs, l'ajout d'un garant en BD et la mise a
	 * jour des libellés de la fenetre précédente
	 * 
	 * @return true si l'ajout a fonctionné, false sinon
	 */
	private boolean traiterAjoutGarant() {
		// cas ou ajouter garant sélectionné
		String idGarant = this.g.getTextFieldIdGarant().getText();
		String adresse = this.g.getTextFieldAdresse().getText();
		String mail = this.g.getTextFieldMail().getText();
		String numTel = this.g.getTextFieldNumTel().getText();

		// Vérifier que tous les champs sont renseignés
		if (idGarant.isEmpty() || adresse.isEmpty() || mail.isEmpty() || numTel.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		// Vérifier que le Mail est au bon format
		if (!mail.contains("@") && (!mail.contains(".com") || !mail.contains(".fr"))) {
			JOptionPane.showMessageDialog(null, "Le mail n'est pas valable, il faut avoir un '@' et '.com' ou '.fr'.");
			return false;
		}

		// Vérifier que la taille du Numéro de Téléphone est bien de 10 caractères
		if (numTel.length() != 10) {
			JOptionPane.showMessageDialog(null, "Le numéro de téléphone n'est pas de la bonne longueur !");
			return false;
		}

		// Vérifier que le Numéro de Téléphone est bien constitué de chiffres
		if (!numTel.matches("\\d+")) {
			JOptionPane.showMessageDialog(null, "Le numéro de téléphone n'est pas au bon format !");
			return false;
		}

		Garant garant = new Garant(idGarant, adresse, mail, numTel);

		DaoGarant dao = new DaoGarant();
		// Vérifier que la méthode "create" du dao ne retourne pas l'erreur 1 du doublon
		// dans la table
		try {
			dao.create(garant);
		} catch (SQLException e1) {
			if (e1.getErrorCode() == 1) {
				JOptionPane.showMessageDialog(null, "Un garant avec cet ID existe déjà.", "Erreur",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		if (g.isAjouterBail()) {
			this.g.getFenAncetre().garantSelectionne(idGarant);
		}
		if (!g.isAjouterBail()) {
			this.g.getfG().getGestionTable().rechargerGarants();
		}

		return true;
	}

	/**
	 * En foncion de si le garant a été ajouté ou juste sélectionné, met à jour les
	 * libellés et affiche information
	 */
	private void traiterTypeAjoutGarant() {
		if (this.g.getRdbtnAjouterGarant().isSelected() && this.traiterAjoutGarant()) {
			JOptionPane.showMessageDialog(null, "Garant bien ajouté", "Information", JOptionPane.INFORMATION_MESSAGE);
			this.g.dispose();
		} else if (this.g.getRdbtnRechercherGarant().isSelected()) {
			String idGarant = this.g.getComboBoxRechercheGarant().getSelectedItem().toString();
			this.g.getFenAncetre().garantSelectionne(idGarant);
		}
	}
}
