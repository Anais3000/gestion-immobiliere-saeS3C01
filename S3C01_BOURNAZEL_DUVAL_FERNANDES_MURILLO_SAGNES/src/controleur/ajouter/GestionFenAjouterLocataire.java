package controleur.ajouter;

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
import vue.ajouter.FenAjouterLocataire;

public class GestionFenAjouterLocataire implements ActionListener {

	private FenAjouterLocataire fAL;
	private DaoLocataire daoLoc;

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter un nouveau
	 * locataire
	 * 
	 * @param fAL fenetre permettant l'ajout
	 */
	public GestionFenAjouterLocataire(FenAjouterLocataire fAL) {
		this.fAL = fAL;
		this.daoLoc = new DaoLocataire();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":
			try {
				// Récupérer les champs dont on a besoin
				String identifiant = fAL.getTextFieldIdentifiantLoc().getText();
				String nom = fAL.getTextFieldNomLoc().getText();
				String prenom = fAL.getTextFieldPrenomLoc().getText();
				String tel = fAL.getTextFieldTelLoc().getText();
				String mail = fAL.getTextFieldMailLoc().getText();
				String dateNaissance = fAL.getTextFieldDateNaissanceLoc().getText();
				String villeNaissance = fAL.getTextFieldVilleNaissanceLoc().getText();
				String adresse = fAL.getTextFieldAdresseLoc().getText();
				String codePostal = fAL.getTextFieldCodePostal().getText();
				String ville = fAL.getTextFieldVilleLoc().getText();

				// Vérifier que tous les champs sont renseignés
				if (!this.verifierChamps(identifiant, nom, prenom, tel, mail, dateNaissance, villeNaissance, adresse,
						codePostal, ville)) {
					break;
				}

				daoLoc.create(fAL.getNouveauLocataire());
				fAL.getFenLocataire().getGestionTable().ecrireLigneTableLocataire();
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(), "Ajout Locataire",
						"+ " + fAL.getNouveauLocataire().getIdLocataire()));

			} catch (SQLException e1) {
				// Erreur 1 : Doublon avec une clé primaire dans la base
				if (e1.getErrorCode() == 1) {
					JOptionPane.showMessageDialog(null, "Cet ID de Locataire existe déjà.", Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
				}
				break;
			}
			this.fAL.dispose();
			break;
		case "Annuler":
			this.fAL.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Vérifie que tous les champs sont renseignés et au bon format
	 * 
	 * @param identifiant    id du locataire
	 * @param nom            nom du locataire
	 * @param prenom         prenom du locataire
	 * @param tel            numéro de téléphone du locataire
	 * @param mail           adresse mail du locataire
	 * @param dateNaissance  date de naissance du locataire
	 * @param villeNaissance ville de naissance du locataire
	 * @param adresse        adresse du locataire
	 * @param codePostal     code postal du locataire
	 * @param ville          ville du locataire
	 * 
	 * @return true si tous les champs sont conformes aux formats attendus, false
	 *         sinon
	 */
	private boolean verifierChamps(String identifiant, String nom, String prenom, String tel, String mail,
			String dateNaissance, String villeNaissance, String adresse, String codePostal, String ville) {

		if (identifiant.isEmpty() || nom.isEmpty() || prenom.isEmpty() || tel.isEmpty() || mail.isEmpty()
				|| dateNaissance.isEmpty() || dateNaissance.equals("dd-mm-yyyy") || villeNaissance.isEmpty()
				|| adresse.isEmpty() || codePostal.isEmpty() || ville.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le Mail est valable, c'est-à-dire qu'il y a un arobase et un
		// '.com' ou '.fr' dans le chaîne de caractère
		if (!mail.contains("@") && (!mail.contains(".com") || !mail.contains(".fr"))) {
			JOptionPane.showMessageDialog(null, "Le mail n'est pas valable, il faut avoir un '@' et '.com' ou '.fr'.");
			return false;
		}

		// Vérifier que la taille du Numéro de Téléphone est de 10 caractères
		if (tel.length() != 10) {
			JOptionPane.showMessageDialog(null, "Le numéro de téléphone n'est pas de la bonne longueur !");
			return false;
		}

		// Vérifier que le Numéro de Téléphone est bien un nombre
		if (!tel.matches("\\d+")) {
			JOptionPane.showMessageDialog(null, "Le numéro de téléphone n'est pas au bon format !");
			return false;
		}

		// Vérifier que la taille du Code Postal est bien de 5 caractères
		if (codePostal.length() != 5) {
			JOptionPane.showMessageDialog(null, "Le Code Postal n'est pas de la bonne longueur !");
			return false;
		}

		// Vérifier que le Code Postal est bien un nombre
		if (!codePostal.matches("\\d+")) {
			JOptionPane.showMessageDialog(null,
					"Le Code Postal n'est pas au bon format, cela doit être un nombre entier !");
			return false;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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
}
