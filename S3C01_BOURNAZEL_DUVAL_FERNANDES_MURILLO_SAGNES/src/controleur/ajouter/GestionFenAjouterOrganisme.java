package controleur.ajouter;

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
import vue.ajouter.FenAjouterOrganisme;

public class GestionFenAjouterOrganisme implements ActionListener {

	private FenAjouterOrganisme fAO;
	private DaoOrganisme daoOrga;

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter un organisme
	 * 
	 * @param fAO fenetre permettant l'ajout
	 */
	public GestionFenAjouterOrganisme(FenAjouterOrganisme fAO) {
		this.fAO = fAO;
		this.daoOrga = new DaoOrganisme();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":
			try {
				// Récupérer tous les champs dont on a besoin
				String numSiret = fAO.getTextFieldNumSiretOrga().getText();
				String nom = fAO.getTextFieldNomOrga().getText();
				String adresse = fAO.getTextFieldAdresseOrga().getText();
				String ville = fAO.getTextFieldVilleOrga().getText();
				String codePostal = fAO.getTextFieldCodePostalOrga().getText();
				String mail = fAO.getTextFieldMailOrga().getText();
				String tel = fAO.getTextFieldNumTelOrga().getText();
				String specialite = fAO.getTextFieldSpecialiteOrga().getText();

				// Vérifier que tous les champs sont renseignés
				if (!this.verifierChamps(numSiret, nom, adresse, ville, codePostal, mail, tel, specialite)) {
					break;
				}

				daoOrga.create(fAO.getNouvelOrganisme());
				fAO.getFenOrganisme().getGestionTable().ecrireLigneTableOrganisme();
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(), "Ajout Organisme",
						"+ " + this.fAO.getNouvelOrganisme().getNom()));
			} catch (SQLException e1) {
				if (e1.getErrorCode() == 1) {
					JOptionPane.showMessageDialog(null, "Cet ID organisme existe déjà.", Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
				}
				break;
			}
			this.fAO.dispose();
			break;
		case "Annuler":
			this.fAO.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Vérifie la validité des champs saisis (bien saisis et au bon format)
	 * 
	 * @param numSiret   numéro SIRET de l'organisme
	 * @param nom        nom de l'organisme
	 * @param adresse    adresse de l'organisme
	 * @param ville      ville de l'organisme
	 * @param codePostal code postal de l'organisme
	 * @param mail       adresse mail de l'organisme
	 * @param tel        numéro de téléphone de l'organisme
	 * @param specialite domaine de spécialité de l'organisme
	 * 
	 * @return true si tous les champs sont conformes, false sinon
	 */
	private boolean verifierChamps(String numSiret, String nom, String adresse, String ville, String codePostal,
			String mail, String tel, String specialite) {
		if (numSiret.isEmpty() || nom.isEmpty() || adresse.isEmpty() || ville.isEmpty() || codePostal.isEmpty()
				|| mail.isEmpty() || tel.isEmpty() || specialite.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le Numéro de SIRET contient bien 14 caractères
		if (numSiret.length() != 14) {
			JOptionPane.showMessageDialog(null, "Le numéro de SIRET doit contenir 14 chiffres.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le Numéro de SIRET est composé d'entiers
		if (!numSiret.matches("\\d+")) {
			JOptionPane.showMessageDialog(null, "Le numéro de SIRET doit être composé de chiffres entiers.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que la taille du Code Postal est bien de 5 caractères
		if (codePostal.length() != 5) {
			JOptionPane.showMessageDialog(null, "Le Code Postal n'est pas de la bonne longueur !", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le code postal est bien composé de chiffres entiers
		if (!codePostal.matches("\\d+")) {
			JOptionPane.showMessageDialog(null,
					"Le Code Postal n'est pas au bon format, cela doit être un nombre entier !", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que la taille du Numéro de Téléphone est bien de 10 caractères
		if (tel.length() != 10) {
			JOptionPane.showMessageDialog(null, "Le numéro de téléphone n'est pas de la bonne longueur !",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le Numéro de Téléphone est bien composé de chiffres
		if (!tel.matches("\\d+")) {
			JOptionPane.showMessageDialog(null, "Le numéro de téléphone n'est pas au bon format !",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le Mail est au bon format
		if (!mail.contains("@") && (!mail.contains(".com") || !mail.contains(".fr"))) {
			JOptionPane.showMessageDialog(null, "Le mail n'est pas valable, il faut avoir un '@' et '.com' ou '.fr'.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

}
