package controleur.modifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Historique;
import modele.dao.DaoLocataire;
import vue.modifier.FenModifierLocataire;

public class GestionFenModifierLocataires implements ActionListener {
	private FenModifierLocataire fenModifInfo;

	/**
	 * Initialise la classe de gestion de la fenêtre permettant de modifier un
	 * locataire
	 * 
	 * @param fenModifInfo fenetre permettant la modification du locataire
	 */
	public GestionFenModifierLocataires(FenModifierLocataire fenModifInfo) {
		this.fenModifInfo = fenModifInfo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			// On initialise les variables dont on aura besoin pour les vérifications

			String nom = fenModifInfo.getTextFieldNomLoc().getText();
			String prenom = fenModifInfo.getTextFieldPrenomLoc().getText();
			String tel = fenModifInfo.getTextFieldTelLoc().getText();
			String mail = fenModifInfo.getTextFieldMailLoc().getText();
			String dateNaissance = fenModifInfo.getTextFieldDateNaissanceLoc().getText();
			String villeNaissance = fenModifInfo.getTextFieldVilleNaissanceLoc().getText();
			String adresse = fenModifInfo.getTextFieldAdresseLoc().getText();
			String codePostal = fenModifInfo.getTextFieldCodePostal().getText();
			String ville = fenModifInfo.getTextFieldVilleLoc().getText();

			// Vérifier que tous les champs sont remplis
			if (nom.isEmpty() || prenom.isEmpty() || tel.isEmpty() || mail.isEmpty() || dateNaissance.isEmpty()
					|| villeNaissance.isEmpty() || adresse.isEmpty() || codePostal.isEmpty() || ville.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Tous les champs doivent être remplis !", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			// Vérification du format des champs
			if (!this.verifierChamps(tel, codePostal, mail, dateNaissance)) {
				break;
			}

			// Mise à jour du locataire
			this.majLocataire();

			break;
		case "Annuler":
			this.fenModifInfo.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Vérifie les formats des champs requis
	 * 
	 * @param tel
	 * @param codePostal
	 * @param mail
	 * @param dateNaissance
	 * 
	 * @return true si les champs sont au bon format, false sinon
	 */
	private boolean verifierChamps(String tel, String codePostal, String mail, String dateNaissance) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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

		// Vérifier que la Date de Naissance du Locataire est au bon format 'DD-MM-YYYY'
		try {
			LocalDate.parse(dateNaissance, formatter);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
					"Format de date invalide. Veuillez entrer la date au format JJ-MM-AAAA (ex: 15-03-2020)",
					"Erreur de format", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Met à jour le locataire selon les champs de la fenêtre de modification
	 */
	private void majLocataire() {

		DaoLocataire daoLocataire = new DaoLocataire();
		try {
			daoLocataire.update(this.fenModifInfo.getLocataireModifie());
			GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
					"Modification Locataire", "~ " + this.fenModifInfo.getLocataireModifie().getNom() + " "
							+ this.fenModifInfo.getLocataireModifie().getPrenom()));
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.fenModifInfo.getFenLocInfoAncetre().actualisationLibelles(this.fenModifInfo.getLocataireModifie());
		this.fenModifInfo.getFenLocInfoAncetre().getFenLocAncetre().getGestionTable().ecrireLigneTableLocataire();
		JOptionPane.showMessageDialog(null, "Modification du Locataire effectuée !", "Confirmation",
				JOptionPane.INFORMATION_MESSAGE);

		this.fenModifInfo.dispose();
	}
}
