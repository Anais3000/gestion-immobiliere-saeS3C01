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
import modele.dao.DaoBatiment;
import vue.consulter_informations.FenBatimentInformations;
import vue.modifier.FenModifierBatiment;

public class GestionFenModifierBatiment implements ActionListener {
	private FenModifierBatiment fenModifBat;

	/**
	 * Initialise la classe de gestion de la fenêtre permettant de modifier un
	 * batiment
	 * 
	 * @param fenModifBat fenetre permettant la modification
	 */
	public GestionFenModifierBatiment(FenModifierBatiment fenModifBat) {
		this.fenModifBat = fenModifBat;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			// Vérification des champs de modification
			if (!this.verifierChamps()) {
				break;
			}

			DaoBatiment daoBatiment = new DaoBatiment();

			try {
				daoBatiment.update(this.fenModifBat.getBatimentModifie());
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
						"Modification Batiment", "~ " + this.fenModifBat.getBatimentModifie().getIdBat()));
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			// Partie pour actualliser libelles
			FenBatimentInformations fenInfo = this.fenModifBat.getFenBatInfoAncetre();
			if (fenInfo != null) {
				fenInfo.actualisationLibelles(this.fenModifBat.getBatimentModifie());
			}
			if (fenInfo != null && fenInfo.getFenBatAncetre() != null) {
				fenInfo.getFenBatAncetre().getGestionTable().appliquerFiltres();
			}

			JOptionPane.showMessageDialog(null, "Modification du Bâtiment effectuée !", "Confirmation",
					JOptionPane.INFORMATION_MESSAGE);
			this.fenModifBat.dispose();
			break;
		case "Annuler":
			this.fenModifBat.dispose();
			break;
		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);
			break;

		}

	}

	/**
	 * Vérifie la validité des champs saisis
	 * 
	 * @return true si les champs sont valides, false sinon
	 */
	private boolean verifierChamps() {
		// Vérifier que le code postal du bâtiment est de 5 caractères
		if (fenModifBat.getTextFieldCodePostalBat().getText().length() != 5) {
			JOptionPane.showMessageDialog(null,
					"Format du Code Postal invalide. Le Code Postal du Bâtiment doit contenir 5 caractères",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le code postal du bâtiment est bien composé de chiffres
		if (!fenModifBat.getTextFieldCodePostalBat().getText().matches("\\d+")) {
			JOptionPane.showMessageDialog(null,
					"Format du Code Postal invalide. Le Code Postal du Bâtiment doit être composé de chiffres.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que la ville du bâtiment est bien composée seulement de caractères
		if (fenModifBat.getTextFieldVilleBat().getText().matches("\\d+")) {
			JOptionPane.showMessageDialog(null,
					"Format de la Ville invalide. La Ville du Bâtiment doit être une chaîne de caractère.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le numéro fiscal du bâtiment est bien composé de chiffres
		if (!fenModifBat.getTextFieldNumFiscal().getText().matches("\\d+")) {
			JOptionPane.showMessageDialog(null,
					"Format du Numéro Fiscal invalide. Le Numéro Fiscal doit être composé de chiffres.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le numéro fiscal est bien composé de 12 caractères
		if (fenModifBat.getTextFieldNumFiscal().getText().length() != 12) {
			JOptionPane.showMessageDialog(null,
					"Format du Numéro Fiscal invalide. Le Numéro Fiscal doit contenir 12 chiffres.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate.parse(fenModifBat.getTextFieldDateConstructionBat().getText(), formatter);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
					"Format de date invalide. Veuillez entrer la date au format JJ-MM-AAAA (ex: 15-03-2020)",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}
}
