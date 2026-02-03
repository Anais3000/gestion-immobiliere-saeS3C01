package controleur.ajouter.fen_ajouter_bien_louable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Historique;
import modele.dao.DaoBienLouable;
import vue.ajouter.FenAjouterBienLouable;

public class GestionFenAjouterBienLouable implements ActionListener {

	private FenAjouterBienLouable fABL;
	private DaoBienLouable daoBL;

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter un bien louable
	 * 
	 * @param fABL fenetre permettant l'ajout
	 */
	public GestionFenAjouterBienLouable(FenAjouterBienLouable fABL) {
		this.fABL = fABL;
		this.daoBL = new DaoBienLouable();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			// Vérifier que tous les champs sont bien renseignés
			if (fABL.getTextFieldAdresseBL().getText().isEmpty()
					|| fABL.getTextFieldNumeroFiscalBL().getText().isEmpty()
					|| fABL.getTextFieldPourcentageEntretienPCBL().getText().isEmpty()
					|| fABL.getTextFieldPourcentageOrdureMenageresBL().getText().isEmpty()
					|| fABL.getTextFieldLoyer().getText().isEmpty() || fABL.getTextFieldProvision().getText().isEmpty()
					|| fABL.getSpinnerNbPieces().getValue() == null || fABL.getTextFieldSurface().getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Tous les champs doivent être remplis !", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			// Vérification des champs saisis
			if (!this.verifierChamps()) {
				break;
			}

			// Création du bien louable
			if (!this.traiterAjoutBL()) {
				break;
			}

			// Ecrire la ligne dans la table de la fenetre ancetre
			this.traiterEcrireLigneTable();

			break;

		case "Annuler":
			this.fABL.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Vérification des champs saisis, tels que l'identifiant du bien louable par
	 * exemple
	 * 
	 * @return true si les champs saisis sont valides, false sinon
	 */
	private boolean verifierChamps() {
		// Vérifier que la taille de l'identifiant n'est pas supérieur à 50
		if (fABL.getTextFieldIdentifiantBL().getText().length() > 50) {
			JOptionPane.showMessageDialog(null,
					"Format d'Identifiant invalide. La taille de l'Identifiant du Bien Louable doit être inférieur ou égal à 50 caractères.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// Vérifier que la taille du numéro fiscal est égale à 12
		if (fABL.getTextFieldNumeroFiscalBL().getText().length() != 12) {
			JOptionPane.showMessageDialog(null,
					"Format de Numéro Fiscal invalide. La taille du Numéro Fiscal du Bien Louable doit être de 12 caractères.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		Double pourcentageEntretien;
		Double pourcentageOrdures;

		try {
			// Vérifier que le pourcentage d'entretien est bien un double
			pourcentageEntretien = Double.parseDouble(fABL.getTextFieldPourcentageEntretienPCBL().getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null,
					"Format du Pourcentage d'Entretien de Partie Commune invalide. Veuillez entrer un nombre décimal.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			// Vérifier que le pourcentage d'ordures ménagères est bien un double
			pourcentageOrdures = Double.parseDouble(fABL.getTextFieldPourcentageOrdureMenageresBL().getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null,
					"Format du Pourcentage d'Ordure Ménagères invalide. Veuillez entrer un nombre décimal.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// Vérifier que les pourcentages soient bien des pourcentages donc entre 0 et
		// 100
		if (pourcentageEntretien > 100 || pourcentageEntretien < 0) {
			JOptionPane.showMessageDialog(null,
					"Format du Pourcentage d'Entretien de Partie Commune invalide. Le pourcentage doit être compris entre 0 et 100.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (pourcentageOrdures > 100 || pourcentageOrdures < 0) {
			JOptionPane.showMessageDialog(null,
					"Format du Pourcentage d'Ordure Ménagères invalide. Le pourcentage doit être compris entre 0 et 100.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le loyer est bien un nombre à virgules (float)
		try {
			Float loyer = Float.parseFloat(fABL.getTextFieldLoyer().getText());
			// Vérifier qu'il n'est pas inférieur à 0
			if (loyer < 0) {
				JOptionPane.showMessageDialog(null, "Le Loyer doit être positif !", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Le Loyer est au mauvais format ! Il doit être un nombre à virgule",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			// Vérifier que la valeur des provisions est un double
			Float provisions = Float.parseFloat(fABL.getTextFieldProvision().getText());
			// Vérifier que la valeur des provisions est positive
			if (provisions < 0) {
				JOptionPane.showMessageDialog(null, "Les Provisions Pour Charges doivent être positives !",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Les Provisions Pour Charges est au mauvais format !",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le nombre de pièce est au moins égal à 0
		if ((int) fABL.getSpinnerNbPieces().getValue() < 0) {
			JOptionPane.showMessageDialog(null, "Le Bien Louable doit avoir un nombre positif ou nul de pièces.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!this.verifSurface()) {
			return false;
		}

		// Vérifier que ce nombre est bien un entier
		if (!fABL.getSpinnerNbPieces().getValue().toString().matches("\\d+")) {
			JOptionPane.showMessageDialog(null, "Le Nombre de Pièce doit être un entier", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Ajout le bien en BD et met à jour l'historique et les tables
	 * 
	 * @return true si l'opération s'est correctement déroulée, false sinon
	 */
	private boolean traiterAjoutBL() {
		try {
			this.daoBL.create(this.fABL.getNvBienLouable());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Erreur création du bien.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (this.fABL.getfBI() != null) {
			// Fenêtre Batiment informations
			this.fABL.getfBI().getGestionTableBiens().ecrireLigneTable();
		} else if (this.fABL.getfBL() != null) {
			// Fenêtre bien Louable (table)
			try {
				this.fABL.getfBL().getGestionTable().ecrireLigneTableBienLouable();
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
						"Ajout Bien Louable", "+ " + this.fABL.getTextFieldIdentifiantBL().getText()));
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Erreur création du bien.", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		JOptionPane.showMessageDialog(null, "Bien Louable ajouté avec succès !", "Confirmation",
				JOptionPane.INFORMATION_MESSAGE);
		this.fABL.dispose();

		return true;
	}

	/**
	 * Ecrit la ligne contenant le bien dans le tableau de la fenetre ancetre
	 */
	private void traiterEcrireLigneTable() {
		if (this.fABL.getfBI() != null) {
			fABL.getfBI().getGestionTableBiens().ecrireLigneTable();
		} else if (this.fABL.getfBL() != null) {
			try {
				fABL.getfBL().getGestionTable().ecrireLigneTableBienLouable();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Vérifie la valeur de la surface saisie
	 * 
	 * @return true si la valeur est correcte, false sinon
	 */
	private boolean verifSurface() {
		String valeurSurface = fABL.getTextFieldSurface().getText();
		Float surface;
		try {
			// Vérifier que la surface est un double
			surface = Float.parseFloat(valeurSurface);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null,
					"La Surface du Bien Louable est invalide ! Elle doit être un nombre à virgule (ex:100.5).",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que la surface n'est pas inférieure à 0
		if (surface < 0) {
			JOptionPane.showMessageDialog(null, "La Surface du Bien Louable doit être positive ou nulle.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

}
