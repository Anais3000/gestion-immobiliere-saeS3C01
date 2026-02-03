package controleur.modifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.BienLouable;
import modele.Historique;
import modele.IRL;
import modele.Louer;
import modele.dao.DaoBienLouable;
import modele.dao.DaoIRL;
import modele.dao.DaoLouer;
import vue.ajouter.FenAjouterIRL;
import vue.consulter_informations.FenBienLouableInformations;
import vue.modifier.FenModifierBienLouable;

public class GestionFenModifierBienLouable implements ActionListener {

	private FenModifierBienLouable fenModifBL;

	/**
	 * Initialise la classe de gestion de la fenêtre permettant de modifier un bien
	 * louable
	 * 
	 * @param fenModifBL fenetre permettant la modification
	 */
	public GestionFenModifierBienLouable(FenModifierBienLouable fenModifBL) {
		this.fenModifBL = fenModifBL;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			String adresse = fenModifBL.getTextFieldAdresse().getText();
			String numFisc = fenModifBL.getTextFieldNumeroFiscal().getText();
			String partieC = fenModifBL.getTextFieldPourcentagePC().getText();
			String ordures = fenModifBL.getTextFieldOrduresMenageres().getText();
			String loyer = fenModifBL.getTextFieldLoyer().getText();
			String provisions = fenModifBL.getTextFieldProvision().getText();
			String nbPieces = fenModifBL.getTextFieldNombrePieces().getText();
			String surface = fenModifBL.getTextFieldSurfaceHabitable().getText();
			String ville = fenModifBL.getTextFieldVille().getText();
			String codePostal = fenModifBL.getTextFieldCodePostal().getText();
			String dateConstr = fenModifBL.getTextFieldDateConstruction().getText();

			if (!this.verifierChampsRenseignes(adresse, ville, codePostal, dateConstr, numFisc, partieC, ordures, loyer,
					provisions, nbPieces, surface)) {
				break;
			}

			// Vérifie la premiere partie des champs
			if (!this.verifierChampsPartieUn(partieC, ordures, numFisc, codePostal)) {
				break;
			}

			// Vérifie la seconde partie des champs
			if (!this.verifierChampsPartieDeux(loyer, provisions, nbPieces, surface, dateConstr)) {
				break;
			}

			BienLouable bienModifie = this.fenModifBL.getBienLouableModifie();

			// Vérification des valeurs en fonction de l'IRL
			if (!this.verifIRL(bienModifie)) {
				break;
			}

			this.mettreAJourBien(bienModifie);

			break;

		case "Annuler":
			this.fenModifBL.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Vérifie si le loyer et / ou la provision peuvent être changés en fonction des
	 * valeurs d'IRL et de si une location est en cours ou non
	 * 
	 * @param bienModifie bien à modifier
	 * 
	 * @return true si la modification peut être faite, false sinon
	 */
	private boolean verifIRL(BienLouable bienModifie) {
		DaoIRL daoIRL = new DaoIRL();

		BienLouable bienPrecedent = this.fenModifBL.getBienAvModif();
		float nouveauLoyer = bienModifie.getLoyer();
		float ancienLoyer = bienPrecedent.getLoyer();

		// GROSSE PARTIE VERIF IRL LOYER
		////////////////////////////////
		DaoLouer daoLouer = new DaoLouer();
		Louer locationActuelle = null;
		try {
			locationActuelle = daoLouer.findByIdBienLouableActuel(bienModifie.getIdBien());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (locationActuelle != null) {
			float nouvelleProvision = bienModifie.getProvisionPourCharges();
			float ancienneProvision = bienPrecedent.getProvisionPourCharges();

			if (!this.verifNouvelleAncienneProvision(nouvelleProvision, ancienneProvision)) {
				return false;
			}
		}

		if (nouveauLoyer == ancienLoyer || locationActuelle == null) {
			return true;
		}

		LocalDate dateAnniv = locationActuelle.getDateDebut();

		MonthDay anniv = MonthDay.from(dateAnniv);
		MonthDay ajd = MonthDay.from(LocalDate.now());

		// Trimestre référence (celui de la signature du bail)
		int trimestreReference = (dateAnniv.getMonthValue() - 1) / 3 + 1;

		// Annees actuelle et précédente
		int anneeActuelle = LocalDate.now().getYear();
		int anneePrecedente = anneeActuelle - 1;

		// Vérification de si l'anniversaire est déjà passé cette année
		if (ajd.isBefore(anniv)) {
			JOptionPane.showMessageDialog(null,
					"Vous ne pouvez pas modifier le loyer avant la date anniversaire du bail.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérification de si le loyer a été modifié cette année
		if (bienModifie.getDerniereAnneeModifLoyer() == anneeActuelle) {
			JOptionPane.showMessageDialog(null, "Vous avez déjà modifié le loyer pour cette année.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		IRL irlPrecedent = null;
		IRL irlActuel = null;
		try {
			irlPrecedent = daoIRL.findById(String.valueOf(anneePrecedente), String.valueOf(trimestreReference));
			irlActuel = daoIRL.findById(String.valueOf(anneeActuelle), String.valueOf(trimestreReference));
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (irlPrecedent == null) {
			JOptionPane.showMessageDialog(null,
					"Veuillez renseigner l'IRL du trimestre " + trimestreReference + " de l'année " + anneePrecedente,
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			FenAjouterIRL fenAI = new FenAjouterIRL(this.fenModifBL.getFenBienLouableAncetre().getFenBienAncetre(),
					null);
			fenAI.setVisible(true);
			return false;
		} else if (irlActuel == null) {
			JOptionPane.showMessageDialog(null,
					"Veuillez renseigner l'IRL du trimestre " + trimestreReference + " de l'année " + anneeActuelle,
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			FenAjouterIRL fenAI = new FenAjouterIRL(this.fenModifBL.getFenBienLouableAncetre().getFenBienAncetre(),
					null);
			fenAI.setVisible(true);
			return false;
		}

		// PARTIE VERIF IRL
		float loyerActuel = bienPrecedent.getLoyer();
		double irlAnneeActuelle = irlActuel.getValeur();
		double irlAnneePrecedente = irlPrecedent.getValeur();

		float nouveauLoyerMax = (float) (loyerActuel * (irlAnneeActuelle / irlAnneePrecedente));

		if (nouveauLoyer > nouveauLoyerMax) {
			JOptionPane.showMessageDialog(null, "L'augmentation de loyer n'est pas conforme à l'IRL.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		bienModifie.setDerniereAnneeModifLoyer(LocalDate.now().getYear());
		this.fenModifBL.setBienAvModif(bienModifie);

		return true;
	}

	/**
	 * Vérifie si la nouvelle provision est égale à l'ancienne provision
	 * 
	 * @param nouvelleProvision nouvelle provision à affecter au bien
	 * @param ancienneProvision provision affectée actuellement au bien
	 * 
	 * @return true si les deux montants sont égaux, false sinon
	 */
	private boolean verifNouvelleAncienneProvision(Float nouvelleProvision, Float ancienneProvision) {
		if (!nouvelleProvision.equals(ancienneProvision)) {
			JOptionPane.showMessageDialog(null,
					"Vous ne pouvez modifier la provision que lors de la régularisation sur un bien loué.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Met à jour le bien et les libellés de la fenetre précédente si il y en a une
	 * 
	 * @param bienModifie bien modifié
	 */
	private void mettreAJourBien(BienLouable bienModifie) {
		DaoBienLouable daoBien = new DaoBienLouable();

		try {
			daoBien.update(bienModifie);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Partie pour rafraichir les libelles
		FenBienLouableInformations fenInfo = this.fenModifBL.getFenBienLouableAncetre();
		if (fenInfo != null) {
			fenInfo.actualisationLibelles(this.fenModifBL.getBienLouableModifie());
		}
		if (fenInfo != null && fenInfo.getFenBienAncetre() != null) {
			try {
				fenInfo.getFenBienAncetre().getGestionTable().ecrireLigneTableBienLouable();
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
						"Modification Bien Louable", "~ " + this.fenModifBL.getBienLouableModifie().getIdBien()));
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		JOptionPane.showMessageDialog(null, "Modification du Bien effectuée !", "Confirmation",
				JOptionPane.INFORMATION_MESSAGE);
		this.fenModifBL.dispose();
	}

	/**
	 * Verifie que tous les champs requis sont renseignes
	 * 
	 * @param adresse
	 * @param ville
	 * @param codePostal
	 * @param dateConstr
	 * @param numFisc
	 * @param partieC
	 * @param ordures
	 * @param loyer
	 * @param provisions
	 * @param nbPieces
	 * @param surface
	 * 
	 * @return true si tous sont renseignés, false sinon
	 */
	private boolean verifierChampsRenseignes(String adresse, String ville, String codePostal, String dateConstr,
			String numFisc, String partieC, String ordures, String loyer, String provisions, String nbPieces,
			String surface) {
		// Vérifier que tous les champs sont bien renseignés
		if (adresse.isEmpty() || ville.isEmpty() || codePostal.isEmpty() || dateConstr.isEmpty() || numFisc.isEmpty()
				|| partieC.isEmpty() || ordures.isEmpty() || loyer.isEmpty() || provisions.isEmpty()
				|| nbPieces.isEmpty() || surface.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Tous les champs doivent être remplis !", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * Vérifie la validité des champs partie communes, ordures, numéro fiscal, code
	 * postal, et les pourcentages
	 * 
	 * @param partieC    pourcentage parties communes
	 * @param ordures    pourcentage ordures ménagères
	 * @param numFisc    numéro fiscal
	 * @param codePostal code postal
	 * 
	 * @return true si les champs sont valides, false sinon
	 */
	private boolean verifierChampsPartieUn(String partieC, String ordures, String numFisc, String codePostal) {

		Double pourcentageEntretien;
		Double pourcentageOrdures;

		try {
			// Vérifier que le pourcentage d'entretien est bien un double
			pourcentageEntretien = Double.parseDouble(partieC);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null,
					"Format du Pourcentage d'Entretien de Partie Commune invalide. Veuillez entrer un nombre décimal.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			// Vérifier que le pourcentage d'ordures ménagères est bien un double
			pourcentageOrdures = Double.parseDouble(ordures);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null,
					"Format du Pourcentage d'Ordure Ménagères invalide. Veuillez entrer un nombre décimal.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (numFisc.length() != 12) {
			JOptionPane.showMessageDialog(null,
					"Format de Numéro Fiscal invalide. La taille du Numéro Fiscal du Bien Louable doit être de 12 caractères.",
					Outils.ERREUR_FORMAT, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le Code Postal est bien un nombre
		if (!codePostal.matches("\\d+")) {
			JOptionPane.showMessageDialog(null,
					"Le Code Postal n'est pas au bon format, cela doit être un nombre entier !", Outils.ERREUR_FORMAT,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que la taille du Code Postal est bien de 5 caractères
		if (codePostal.length() != 5) {
			JOptionPane.showMessageDialog(null, "Le Code Postal n'est pas de la bonne longueur !", "Erreur de taille",
					JOptionPane.ERROR_MESSAGE);
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

		return true;
	}

	/**
	 * Vérifie la validité des champs loyer, provisions, nombre de pieces, surface,
	 * et date de construction
	 * 
	 * @param loyer
	 * @param provisions
	 * @param nbPieces   nombre de pièces
	 * @param surface
	 * @param dateConstr date de construction
	 * 
	 * @return true si les champs sont au bon format, false sinon
	 */
	private boolean verifierChampsPartieDeux(String loyer, String provisions, String nbPieces, String surface,
			String dateConstr) {
		// Vérifier que le loyer est bien un nombre à virgules (float)
		try {
			Float loyerForm = Float.parseFloat(loyer);
			// Vérifier qu'il n'est pas inférieur à 0
			if (loyerForm < 0) {
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
			// Vérifier que la valeur des provisions est un float
			Float provisionsForm = Float.parseFloat(provisions);
			// Vérifier que la valeur des provisions est positive
			if (provisionsForm < 0) {
				JOptionPane.showMessageDialog(null, "Les Provisions Pour Charges doivent être positives !",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Les Provisions Pour Charges est au mauvais format !",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que ce nombre est bien un entier
		if (!nbPieces.matches("\\d+")) {
			JOptionPane.showMessageDialog(null, "Le Nombre de Pièce doit être un entier", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le nombre de pièce est au moins égal à 1
		if (Integer.parseInt(nbPieces) < 0) {
			JOptionPane.showMessageDialog(null, "Le Bien Louable doit avoir au minimum 0 pièce.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			// Vérifier que la surface est un double
			Float.parseFloat(surface);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "La Surface du Bien Louable est invalide ! Elle doit être un nombre.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que la surface n'est pas inférieure à 0
		if (Float.parseFloat(surface) < 0) {
			JOptionPane.showMessageDialog(null, "La Surface du Bien Louable doit être positive ou nulle",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate.parse(dateConstr, formatter);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Veuillez saisir une date de construction valide (format DD-MM-YYYY).",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}
}
