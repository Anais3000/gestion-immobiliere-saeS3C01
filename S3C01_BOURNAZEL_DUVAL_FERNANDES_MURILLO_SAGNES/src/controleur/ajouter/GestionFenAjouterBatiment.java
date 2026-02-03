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
import modele.Batiment;
import modele.Historique;
import modele.dao.DaoBatiment;
import vue.ajouter.FenAjouterBatiment;

public class GestionFenAjouterBatiment implements ActionListener {

	private FenAjouterBatiment fAB;
	private DaoBatiment dao;

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter un batiment
	 * 
	 * @param fAB fenetre permettant l'ajout du batiment
	 */
	public GestionFenAjouterBatiment(FenAjouterBatiment fAB) {
		this.fAB = fAB;
		dao = new DaoBatiment();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			if (!this.verifTextFields()) {
				break;
			}

			if (!traiterAjoutBat()) {
				break;
			}

			fAB.getfB().getGestionTable().appliquerFiltres();

			JOptionPane.showMessageDialog(null, "Le batiment a bien été ajouté.", "Information",
					JOptionPane.INFORMATION_MESSAGE);

			this.fAB.dispose();
			break;
		case "Annuler":
			this.fAB.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Verifie que les text fields saisis sont au bon format
	 * 
	 * @return true si les formats sont bons, false sinon
	 */
	private boolean verifTextFields() {
		// Vérifier que le code postal du bâtiment est de 5 caractères
		if (fAB.getTextFieldCodePostalBat().getText().length() != 5) {
			JOptionPane.showMessageDialog(null,
					"Format du Code Postal invalide. Le Code Postal du Bâtiment doit contenir 5 caractères",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le code postal du bâtiment est bien composé de chiffres
		if (!fAB.getTextFieldCodePostalBat().getText().matches("\\d+")) {
			JOptionPane.showMessageDialog(null,
					"Format du Code Postal invalide. Le Code Postal du Bâtiment doit être composé de chiffres.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que la ville du bâtiment est bien composée seulement de caractères
		if (fAB.getTextFieldVilleBat().getText().matches("\\d+")) {
			JOptionPane.showMessageDialog(null,
					"Format de la Ville invalide. La Ville du Bâtiment doit être une chaîne de caractère.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le numéro fiscal du bâtiment est bien composé de chiffres
		if (!fAB.getTextFieldNumFiscal().getText().matches("\\d+")) {
			JOptionPane.showMessageDialog(null,
					"Format du Numéro Fiscal invalide. Le Numéro Fiscal doit être composé de chiffres.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le numéro fiscal est bien composé de 12 caractères
		if (fAB.getTextFieldNumFiscal().getText().length() != 12) {
			JOptionPane.showMessageDialog(null,
					"Format du Numéro Fiscal invalide. Le Numéro Fiscal doit contenir 12 chiffres.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Traite l'ajout d'un bâtiment en BD
	 * 
	 * @return true si l'ajout est effectif, false sinon
	 */
	private boolean traiterAjoutBat() {
		LocalDate date = null;

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			date = LocalDate.parse(fAB.getTextFieldDateConstructionBat().getText(), formatter);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
					"Format de date invalide. Veuillez entrer la date au format JJ-MM-AAAA (ex: 15-03-2020)",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		Batiment b = new Batiment(fAB.getTextFieldIDBat().getText(), date, fAB.getTextFieldAdresseBat().getText(),
				fAB.getTextFieldCodePostalBat().getText(), fAB.getTextFieldVilleBat().getText(),
				fAB.getTextFieldNumFiscal().getText());

		// Vérifier que le Bâtiment qu'on ajoute n'est pas déjà dans la base de données
		try {
			dao.create(b);
			GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(), "Ajout Batiment",
					"+ " + this.fAB.getTextFieldIDBat().getText()));
		} catch (SQLException e1) {
			// Erreur SQL 1 : quand il y a un doublon avec un élément qu'il y a déjà dans la
			// base
			if (e1.getErrorCode() == 1) {
				JOptionPane.showMessageDialog(null, "Cet ID bâtiment existe déjà.", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		return true;
	}

}
