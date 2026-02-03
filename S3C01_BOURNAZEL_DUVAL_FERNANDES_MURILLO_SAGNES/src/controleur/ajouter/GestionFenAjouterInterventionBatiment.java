package controleur.ajouter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Historique;
import modele.Intervention;
import modele.Organisme;
import modele.dao.DaoIntervention;
import modele.dao.DaoOrganisme;
import vue.ajouter.FenAjouterInterventionBatiment;

public class GestionFenAjouterInterventionBatiment implements ActionListener {

	private FenAjouterInterventionBatiment fAIB;
	private String idBat;
	private DaoOrganisme dao;
	private static final String OPTIONNEL = "(optionnel)";
	private static final String DATE_OPTIONNELLE = "DD-MM-AAAA (optionnel)";
	private static final String DATE = "dd-MM-yyyy";

	/**
	 * Classe interne pour encapsuler les résultats de validation je fais ça car
	 * sinon on ne peut pas passer en in out les types primitifs aux fonctions et
	 * c'est pas pratique
	 * 
	 * Ca permet de simplifier le code et notamment de réduire la complexité
	 * cognitive et cyclomatique.
	 */
	private static class ResultatValidation {
		LocalDate dateIntervFormat;
		LocalDate dateFactureFormat;
		Float montantFactFloat;
		LocalDate dateAcFormat;
		Float acompteFormat;
		Float montantDevisFormat;
		Float montantNonDedFormat;
		Float reductionFormat;
	}

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter une intervention à
	 * un batiment
	 * 
	 * @param fAIB  fenetre permettant l'ajout
	 * @param idBat id du batiment sur lequel ajouter l'intervention
	 */
	public GestionFenAjouterInterventionBatiment(FenAjouterInterventionBatiment fAIB, String idBat) {
		this.fAIB = fAIB;
		this.idBat = idBat;
		this.dao = new DaoOrganisme();
		try {
			fAIB.afficherOrganismes(this.getNomsOrganismes());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			// Récupérer tous les champs que l'on va utiliser
			String idInterv = fAIB.getTextFieldIdentifiantIntervention().getText();
			String intitul = fAIB.getTextFieldIntituleIntervention().getText();
			String dateInterv = fAIB.getTextFieldDateIntervention().getText();
			String numFacture = fAIB.getTextFieldNumFactureIntervention().getText();
			String dateAc = fAIB.getTextFieldDateAcompteIntervention().getText();
			String acompte = fAIB.getTextFieldAcompteIntervention().getText();
			String numDevis = fAIB.getTextFieldNumeroDevisIntervention().getText();
			String montantDevis = fAIB.getTextFieldMontantDevitIntervention().getText();
			String montantNonDed = fAIB.getTextFieldMontantNonDeductibleIntervention().getText();
			String reduction = fAIB.getTextFieldReductionIntervention().getText();
			String dateFacture = fAIB.getTextFieldDateFactureIntervention().getText();

			// Initialiser toutes les variables dont on a besoin
			ResultatValidation resultat = new ResultatValidation();

			if (!this.verifChampsUn(idInterv, intitul, dateInterv, resultat)) {
				break;
			}

			String montantFact = fAIB.getTextFieldMontantFactureIntervention().getText();

			if (!this.verifierChampsFacture(dateFacture, montantFact, resultat)) {
				break;
			}

			if (!this.verifierChampsAcompte(dateAc, acompte, resultat)) {
				break;
			}

			if (!this.verifierChampsMontantDevis(numDevis, montantDevis, resultat)) {
				break;
			}

			if (!this.verifierChampsReduction(montantNonDed, reduction, resultat)) {
				break;
			}

			int entretienPc = this.getEntretienPc();
			int ordures = this.getOrdures();

			this.traiterInsererInterv(idInterv, intitul, resultat.dateIntervFormat, numFacture,
					resultat.dateFactureFormat, resultat.montantFactFloat, resultat.dateAcFormat,
					resultat.acompteFormat, numDevis, resultat.montantDevisFormat, resultat.montantNonDedFormat,
					resultat.reductionFormat, entretienPc, ordures);

			break;
		case "Annuler":
			this.fAIB.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * retourne le nom de tous les organismes et le stocke dans la liste noms
	 * 
	 * @return Liste de String contenant les noms des organismes
	 * @throws SQLException
	 */
	public List<String> getNomsOrganismes() throws SQLException {
		List<String> noms = new ArrayList<>();
		for (Organisme o : dao.findAll()) {
			noms.add(o.getNom());
		}
		return noms;
	}

	/**
	 * Permet de vérifier la validité des champs idInterv, intitul et dateInterv
	 * 
	 * @param idInterv   identifiant de l'intervention
	 * @param intitul    intitulé de l'intervention
	 * @param dateInterv date de l'intervention
	 * @param resultat   objet encapsulant les résultats des conversions
	 * 
	 * @return true si les champs sont valides, false sinon
	 */
	private boolean verifChampsUn(String idInterv, String intitul, String dateInterv, ResultatValidation resultat) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE);

		// Vérifier que l'Identifiant n'est pas vide
		if (idInterv.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez saisir un identifiant d'intervention", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// Vérifier que l'Intitulé de l'intervention est bien renseigné
		if (intitul.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez saisir un intitulé d'intervention", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// Vérifier que la Date de l'intervention n'est pas vide et qu'elle est du bon
		// format "DD-MM-YYYY"
		if (dateInterv.isEmpty() || dateInterv.equals("DD-MM-AAAA")) {
			JOptionPane.showMessageDialog(null, "Veuillez saisir une date d'intervention", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			try {
				resultat.dateIntervFormat = LocalDate.parse(dateInterv, formatter);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null,
						"Veuillez saisir une date d'intervention valide (format DD-MM-YYYY).", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		return true;
	}

	/**
	 * Permet de vérifier la validité des champs de facture
	 * 
	 * @param dateFacture date de la facture
	 * @param montantFact montant de la facture
	 * @param resultat    objet encapsulant les résultats des conversions
	 * 
	 * @return true si les champs sont valides, false sinon
	 */
	private boolean verifierChampsFacture(String dateFacture, String montantFact, ResultatValidation resultat) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE);

		// Vérifier que si la date de la facture est renseigné, alors elle doit être au
		// format "DD-MM-YYYY", sinon la dateFacture est mise à null
		if (!(dateFacture.isEmpty() || dateFacture.equals(DATE_OPTIONNELLE))) {
			try {
				resultat.dateFactureFormat = LocalDate.parse(dateFacture, formatter);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Veuillez saisir une date de facture valide", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		// Vérifier que le Montant de la facture, si il est renseigné est bien de type
		// float et si il ne l'est pas il est alors mit à null
		if (!(montantFact.isEmpty() || montantFact.equals(OPTIONNEL)
				|| !(fAIB.getTextFieldMontantFactureIntervention().isEditable()))) {
			try {
				resultat.montantFactFloat = Float.parseFloat(montantFact);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null,
						"Veuillez saisir un montant de facture valide (valeurs numériques seulement).",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		return true;
	}

	/**
	 * Permet de vérifier la validité des champs de l'acompte
	 * 
	 * @param dateAc   date de l'acompte
	 * @param acompte  montant de l'acompte (en String)
	 * @param resultat objet encapsulant les résultats des conversions
	 * 
	 * @return true si les champs sont valides, false sinon
	 */
	private boolean verifierChampsAcompte(String dateAc, String acompte, ResultatValidation resultat) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE);

		// Vérifier que si la Date de la facture est renseignée, alors elle est au bont
		// format, sinon elle est nulle
		if (dateAc.isEmpty() || dateAc.equals(DATE_OPTIONNELLE)) {
			if (acompte.isEmpty() || acompte.equals(OPTIONNEL)) {
				resultat.dateAcFormat = null;
			}
		} else {
			try {
				resultat.dateAcFormat = LocalDate.parse(dateAc, formatter);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Veuillez saisir une date d'acompte valide (format DD-MM-YYYY).",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		// Si l'Acompte n'est renseigné correctement alors on affiche un pop-up
		if ((acompte.isEmpty() || acompte.equals(OPTIONNEL))
				&& !(dateAc.isEmpty() || dateAc.equals(DATE_OPTIONNELLE))) {
			JOptionPane.showMessageDialog(null, "Veuillez saisir un montant d'acompte", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return this.verifierAcompteFormat(acompte, dateAc, resultat);
	}

	/**
	 * Permet de vérifier que le format des champs d'acompte est le bon
	 * 
	 * @param acompte  montant de l'acompte (en String)
	 * @param dateAc   date de l'acompte
	 * @param resultat objet encapsulant les résultats des conversions
	 * 
	 * @return true si le format est le bon, false sinon
	 */
	private boolean verifierAcompteFormat(String acompte, String dateAc, ResultatValidation resultat) {
		// Vérifier que l'accompte est au bon format
		if (acompte.isEmpty() || acompte.equals(OPTIONNEL)) {
			resultat.acompteFormat = null;
		} else {
			if (dateAc.isEmpty() || dateAc.equals(DATE_OPTIONNELLE)) {
				JOptionPane.showMessageDialog(null, "Veuillez saisir une date d'acompte", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			try {
				resultat.acompteFormat = Float.parseFloat(acompte);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "Veuillez saisir un montant d'acompte valide (valeurs numériques)",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	/**
	 * Permet de vérifier la validité des champs liés au devis
	 * 
	 * @param numDevis     numéro du devis
	 * @param montantDevis montant du devis (en String)
	 * @param resultat     objet encapsulant les résultats des conversions
	 * 
	 * @return true si les champs sont valides, false sinon
	 */
	private boolean verifierChampsMontantDevis(String numDevis, String montantDevis, ResultatValidation resultat) {
		// Si le Numero devis n'est pas renseigné, alors on affiche un pop-up
		if ((numDevis.isEmpty() || numDevis.equals(OPTIONNEL))
				&& !(montantDevis.isEmpty() || montantDevis.equals(OPTIONNEL))) {
			JOptionPane.showMessageDialog(null, "Veuillez saisir un montant de devis", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Si le Montant devis n'est pas renseigné alors il est null
		if (montantDevis.isEmpty() || montantDevis.equals(OPTIONNEL)) {
			resultat.montantDevisFormat = null;
		} else {
			// Si le numéro de Devis n'est pas renseigné alors on affiche un pop-up
			if (numDevis.isEmpty() || numDevis.equals(OPTIONNEL)) {
				JOptionPane.showMessageDialog(null, "Veuillez saisir un numéro de devis", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			try {
				// Vérifier que le devis est au bon format (DD-MM-YYYY)
				resultat.montantDevisFormat = Float.parseFloat(montantDevis);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "Veuillez saisir un montant de devis valide (format DD-MM-YYYY).",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	/**
	 * Permet de vérifier la validité des champs liés à la réduction
	 * 
	 * @param montantNonDed montant non déductible (String)
	 * @param reduction     montant de la réduction (String)
	 * @param resultat      objet encapsulant les résultats des conversions
	 * 
	 * @return true si les champs sont valides, false sinon
	 */
	private boolean verifierChampsReduction(String montantNonDed, String reduction, ResultatValidation resultat) {
		// Vérifier que si le Montant non déductible n'est pas renseigné alors le
		// montant du devis est null
		if (montantNonDed.isEmpty() || montantNonDed.equals(OPTIONNEL)) {
			resultat.montantNonDedFormat = null;
		} else {
			// Si il est renseigné, alors vérifier que c'est bien un float
			try {
				resultat.montantNonDedFormat = Float.parseFloat(montantNonDed);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null,
						"Veuillez saisir un montant non déductible valide (valeur numérique).", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		// Vérifier que si la Réduction n'est pas renseignée alors le montant du devis
		// est null
		if (reduction.isEmpty() || reduction.equals(OPTIONNEL)) {
			resultat.reductionFormat = null;
		} else {
			try {
				resultat.reductionFormat = Float.parseFloat(reduction);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "Veuillez saisir une réduction valide (valeur numérique).",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	/**
	 * Permet de savoir si l'intervention concerne l'entretien des parties communes
	 * 
	 * @return 1 si elle concerne les parties communes, false sinon
	 */
	private int getEntretienPc() {
		if (fAIB.getRdbtnEntretien().isSelected()) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Permet de savoir si l'intervention concerne les ordures
	 * 
	 * @return 1 si elle concerne les ordures, false sinon
	 */
	private int getOrdures() {
		if (fAIB.getRdbtnOrdures().isSelected()) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Permet d'insérer l'objet de type Intervention correspondant
	 * 
	 * @param idInterv            identifiant de l'intervention
	 * @param intitul             intitulé de l'intervention
	 * @param dateIntervFormat    date formattée de l'intervention
	 * @param numFacture          numéro de la facture liée
	 * @param dateFactureFormat   date formattée de la facture liée
	 * @param montantFactFloat    montant de la facture liée
	 * @param dateAcFormat        date formatée de l'acompte lié
	 * @param acompteFormat       montant de l'acompte lié
	 * @param numDevis            numéro du devis lié
	 * @param montantDevisFormat  montant du devis lié
	 * @param montantNonDedFormat montant non déductible lié
	 * @param reductionFormat     montant de la réduction liée
	 * @param entretienPc         1 si l'intervention concerne l'entretien des
	 *                            parties communes, 0 sinon
	 * @param ordures             1 si l'intervention concerne les ordures, 0 sinon
	 * 
	 * @return true si l'insertion s'est correctement déroulée, false sinon
	 */
	private boolean traiterInsererInterv(String idInterv, String intitul, LocalDate dateIntervFormat, String numFacture,
			LocalDate dateFactureFormat, Float montantFactFloat, LocalDate dateAcFormat, Float acompteFormat,
			String numDevis, Float montantDevisFormat, Float montantNonDedFormat, Float reductionFormat,
			int entretienPc, int ordures) {
		try {
			DaoOrganisme daoOrg = new DaoOrganisme();
			DaoIntervention daoInterv = new DaoIntervention();

			String nomOrg = fAIB.getComboBoxOrganismes().getSelectedItem().toString().trim();
			Organisme org = daoOrg.findByNom(nomOrg);

			Intervention intervention = new Intervention(idInterv, intitul, dateIntervFormat, numFacture,
					dateFactureFormat, montantFactFloat, dateAcFormat, acompteFormat, numDevis, montantDevisFormat,
					montantNonDedFormat, reductionFormat, org.getNumSIRET(), idBat, entretienPc, ordures);

			daoInterv.create(intervention);

			GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
					"Ajout Intervention sur un Bâtiment", "+ " + intervention.getIdIntervention() + " ["
							+ intervention.getIntitule() + "] / Bat : " + intervention.getIdBatiment()));

			// Mise à jour de la fenetre en arrière plan des infos du BAT
			fAIB.getFenAncestor().getGestionTableInterv().ecrireLigneTable();

			JOptionPane.showMessageDialog(null, "Intervention ajoutée avec succès !", "Confirmation",
					JOptionPane.INFORMATION_MESSAGE);

			this.fAIB.dispose();

		} catch (SQLException e1) {
			if (e1.getErrorCode() == 1) {
				JOptionPane.showMessageDialog(fAIB, "Erreur : l'identifiant de l'intervention existe déjà !",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

		}
		return true;
	}
}