package controleur.modifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Historique;
import modele.dao.DaoOrganisme;
import vue.modifier.FenModifierOrganisme;

public class GestionFenModifierOrganisme implements ActionListener {
	private FenModifierOrganisme f;

	/**
	 * Initialise la classe de gestion de la fenêtre permettant de modifier un
	 * organisme
	 * 
	 * @param f fenetre permettant la modification de l'organisme
	 */
	public GestionFenModifierOrganisme(FenModifierOrganisme f) {
		this.f = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			String nom = f.getTextFieldNomOrg().getText();
			String specialite = f.getTextFieldSpecialite().getText();
			String adresse = f.getTextFieldAdresseOrg().getText();
			String codePostal = f.getTextFieldCodePostalOrg().getText();
			String ville = f.getTextFieldVilleOrg().getText();
			String tel = f.getTextFieldNumTelOrg().getText();
			String mail = f.getTextFieldMailOrg().getText();

			// Vérifier que tous les champs sont remplis
			if (nom.isEmpty() || specialite.isEmpty() || adresse.isEmpty() || codePostal.isEmpty() || ville.isEmpty()
					|| tel.isEmpty() || mail.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Tous les champs doivent être remplis !", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			// Verifie le format des champs
			if (!this.verifierChamps(tel, codePostal, mail)) {
				break;
			}

			DaoOrganisme daoOrganisme = new DaoOrganisme();
			try {
				daoOrganisme.update(this.f.getOrganismeModifie());
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
						"Modification Organisme", "~ " + this.f.getOrganismeModifie().getNom()));
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
			this.f.getFenOrganismeInformations().actualisationLibelles(this.f.getOrganismeModifie());
			this.f.getFenOrganismeInformations().getFenLocAncetre().getGestionTable().ecrireLigneTableOrganisme();
			JOptionPane.showMessageDialog(null, "Modification de l'organisme effectuée !", "Confirmation",
					JOptionPane.INFORMATION_MESSAGE);
			this.f.dispose();
			break;
		case "Annuler":
			this.f.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Vérifie le format des champs requis, téléphone, code postal et mail
	 * 
	 * @param tel
	 * @param codePostal
	 * @param mail
	 * 
	 * @return true si les champs sont au bon format, false sinon
	 */
	private boolean verifierChamps(String tel, String codePostal, String mail) {
		// Vérifier que la taille du Numéro de Téléphone est de 10 caractères
		if (tel.length() != 10) {
			JOptionPane.showMessageDialog(null, "Le numéro de téléphone n'est pas de la bonne longueur !",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le Numéro de Téléphone est bien un nombre
		if (!tel.matches("\\d+")) {
			JOptionPane.showMessageDialog(null, "Le numéro de téléphone n'est pas au bon format !",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que la taille du Code Postal est bien de 5 caractères
		if (codePostal.length() != 5) {
			JOptionPane.showMessageDialog(null, "Le Code Postal n'est pas de la bonne longueur !", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le Code Postal est bien un nombre
		if (!codePostal.matches("\\d+")) {
			JOptionPane.showMessageDialog(null,
					"Le Code Postal n'est pas au bon format, cela doit être un nombre entier !", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le Mail est valable, c'est-à-dire qu'il y a un arobase et un
		// '.com' ou '.fr' dans le chaîne de caractère
		if (!mail.contains("@") && (!mail.contains(".com") || !mail.contains(".fr"))) {
			JOptionPane.showMessageDialog(null, "Le mail n'est pas valable, il faut avoir un '@' et '.com' ou '.fr'.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

}
