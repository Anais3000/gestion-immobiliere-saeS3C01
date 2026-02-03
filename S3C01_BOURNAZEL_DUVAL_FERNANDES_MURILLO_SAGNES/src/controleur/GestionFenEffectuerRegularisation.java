package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.BienLouable;
import modele.Historique;
import modele.Louer;
import modele.Paiement;
import modele.dao.DaoBienLouable;
import modele.dao.DaoLouer;
import modele.dao.DaoPaiement;
import modele.dao.DaoReleveCompteur;
import vue.FenEffectuerRegularisation;
import vue.tables.FenDetailCharges;

public class GestionFenEffectuerRegularisation implements ActionListener {

	private FenEffectuerRegularisation fER;
	private BienLouable bien;
	private Louer louer;
	private ArrayList<Louer> locationsGaragesAssocies;

	/**
	 * Initialise la classe de gestion de la fenêtre pour effectuer la
	 * régularisation des charges
	 * 
	 * @param fER   fenêtre pour effectuer la régularisation
	 * @param bien  bien louable régularisé
	 * @param louer location pour laquelle on souhaite régulariser
	 */
	public GestionFenEffectuerRegularisation(FenEffectuerRegularisation fER, BienLouable bien, Louer louer) {
		this.fER = fER;
		this.bien = bien;
		this.louer = louer;
		this.locationsGaragesAssocies = new ArrayList<>();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Quitter":
			this.fER.dispose();
			break;
		case "Détail des Charges":
			FenDetailCharges fDC;
			try {
				fDC = new FenDetailCharges(this.fER, this.bien);
				fDC.getGestionTableCharges().ecrireLigneTable();
				fDC.setVisible(true);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}

			break;
		case "Confirmer":

			if (!(this.fER.getRdbtnAjouterMoisSuivant().isSelected() || this.fER.getRdbtnLocataireAPaye().isSelected()
					|| this.fER.getRdbtnPaiementEmisAuLocataire().isSelected())) {
				JOptionPane.showMessageDialog(null, "Veuillez sélectionner une option.", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			DaoLouer daoLouer = new DaoLouer();

			// Si le proprio a émis le paiement au locataire [MONTANT NEGATIF SEULEMENT]
			if (this.fER.getRdbtnPaiementEmisAuLocataire().isSelected()) {
				this.traiterPaiementEmis();
			}

			// Si le proprio veut ajouter la somme au loyer des mois suivants [MONTANT
			// POSITIF SEULEMENT]
			if (this.fER.getRdbtnAjouterMoisSuivant().isSelected()) {
				this.traiterAjoutMoisSuivant();
			}

			// Si le locataire a payé [MONTANT POSITIF SEULEMENT]
			if (this.fER.getRdbtnLocataireAPaye().isSelected()) {
				this.traiterLocataireAPaye();
			}

			// A FAIRE A LA FIN : Mise a jour de la provision pour charges (si c'est le cas)
			Float ancienneProvision = this.bien.getProvisionPourCharges();
			Float nouvelleProvision = Float.parseFloat(this.fER.getTextFieldProvisionAjustee().getText());

			if (!ancienneProvision.equals(nouvelleProvision)) {
				this.traiterMAJProvision(nouvelleProvision);
			}

			// A la fin on marque la date de derniere regul comme la date de fin de la
			// période
			// prise en compte par la regul.
			this.louer.setDateDerniereRegul(getDateCetteAnnee());
			try {
				daoLouer.update(this.louer);
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
						"Ajout Regularisation des charges", "+ " + this.bien.getIdBien()));
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			this.fER.getFenAncetre().getGestionTableRegularisationCharges().ecrireLigneTable(this.bien.getIdBien());

			JOptionPane.showMessageDialog(null, "La régularisation a bien été effectuée", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			this.fER.dispose();
			break;

		default:
			// Cas non prévu (sécurité)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;

		}
	}

	/**
	 * Gere l'affichage du text field de la période
	 */
	public void setPeriode() {
		LocalDate dateAnneeDerniere = this.getDateAnneeDerniere();
		LocalDate dateCetteAnnee = this.getDateCetteAnnee();
		this.fER.getTextFieldPeriode().setText(dateAnneeDerniere + " - " + dateCetteAnnee);
	}

	/**
	 * Gere l'affichage du text field contenant la liste des garages associés
	 */
	public void setGaragesAssocies() {

		DaoLouer daoL = new DaoLouer();
		ArrayList<Louer> listeLocsGaragesAssocies = null;
		try {
			listeLocsGaragesAssocies = (ArrayList<Louer>) daoL.findAllLouerGaragesAssociesLogement(
					this.louer.getDateDebut().toString(), this.louer.getDateFin().toString(),
					this.louer.getIdBienLouable());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// On utilise un StringBuilder pour améliorer
		// sécurité et performances, au lieu de
		// faire des .equals
		StringBuilder sb = new StringBuilder();

		for (Louer l : listeLocsGaragesAssocies) {
			this.locationsGaragesAssocies.add(l);

			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(l.getIdBienLouable());
		}

		String idGarages = sb.toString();
		if (idGarages.equals(" ")) {
			this.fER.getTextFieldGarages().setText("Aucun garage associé.");
		} else {
			this.fER.getTextFieldGarages().setText(idGarages);
		}

	}

	/**
	 * Affiche les totaux dans les champs appropriés exemple : le total des charges
	 * payées, la nouvelle provision
	 */
	public void setTotaux() {
		this.fER.getTextFieldChargesPayees().setText(this.getTotalCharges().toString() + " €");
		this.fER.getTextFieldChargesVersee().setText(this.getTotalProvisions().toString() + " €");
		this.fER.getTextFieldRegularisatioTotal().setText(this.getTotalRegularisation().toString() + " €");
		// On peut se passer du if else mais c'est plus clair comme ça
		if (this.getTotalRegularisation() < 0) {
			this.fER.getRdbtnPaiementEmisAuLocataire().setVisible(true);
			this.fER.getRdbtnAjouterMoisSuivant().setVisible(false);
			this.fER.getRdbtnLocataireAPaye().setVisible(false);
			this.fER.getTxtNombreDeMois().setVisible(false);
			this.fER.getLblInfoMois().setVisible(false);
		} else {
			this.fER.getRdbtnPaiementEmisAuLocataire().setVisible(false);
			this.fER.getRdbtnAjouterMoisSuivant().setVisible(true);
			this.fER.getRdbtnLocataireAPaye().setVisible(true);
			this.fER.getTxtNombreDeMois().setVisible(true);
			this.fER.getLblInfoMois().setVisible(true);
		}
		float totalCharges = this.getTotalCharges();

		float nouvelleProvision = Math.round((totalCharges / 12) * 100f) / 100f;
		this.fER.getTextFieldProvisionAjustee().setText(String.valueOf(nouvelleProvision));
	}

	/**
	 * Permet d'obtenir la date de début de période de régularisation
	 * 
	 * @return la date de début de période sous forme d'une LocalDate
	 */
	public LocalDate getDateAnneeDerniere() {

		LocalDate debut = louer.getDateDebut();
		LocalDate ajd = LocalDate.now();

		LocalDate anniversaireCetteAnnee = LocalDate.of(ajd.getYear(), debut.getMonth(), debut.getDayOfMonth());

		if (ajd.isBefore(anniversaireCetteAnnee)) {
			return anniversaireCetteAnnee.minusYears(2);
		} else {
			return anniversaireCetteAnnee.minusYears(1);
		}
	}

	/**
	 * Permet d'obtenir la date de fin de période de régularisation
	 * 
	 * @return la date de fin de période sous forme d'une LocalDate
	 */
	public LocalDate getDateCetteAnnee() {
		return getDateAnneeDerniere().plusYears(1).minusDays(1);
	}

	/**
	 * Permet d'obtenir le total des charges dûes, comprenant celles du bien et de
	 * ses garages associés
	 * 
	 * @return le total des charges, en Float
	 */
	private Float getTotalCharges() {
		DaoReleveCompteur daoC = new DaoReleveCompteur();
		Float totalCharges = 0.0f;
		try {
			totalCharges = daoC.totalChargesSurPeriode(this.bien.getIdBien(), this.getDateAnneeDerniere().toString(),
					this.getDateCetteAnnee().toString());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return 0.0f;
		}

		// Partie pour les garages associés
		for (Louer l : locationsGaragesAssocies) {
			try {
				totalCharges += daoC.totalChargesSurPeriode(l.getIdBienLouable(),
						this.getDateAnneeDerniere().toString(), this.getDateCetteAnnee().toString());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return 0.0f;
			}
		}

		return totalCharges;
	}

	/**
	 * Retourne le total de la taxe d'ordures ménagères sur la période sélectionnée,
	 * pour le bien et pour ses garages associés.
	 * 
	 * @return le total de la taxe d'ordures, en Float
	 */
	private Float getTotalOrdures() {
		DaoReleveCompteur daoC = new DaoReleveCompteur();
		Float totalOrdures = 0.0f;
		try {
			totalOrdures = daoC.totalOrduresSurPeriode(this.bien.getIdBien(), this.getDateAnneeDerniere().toString(),
					this.getDateCetteAnnee().toString());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return 0.0f;
		}

		// Partie pour les garages associés
		for (Louer l : locationsGaragesAssocies) {
			try {
				totalOrdures += daoC.totalOrduresSurPeriode(l.getIdBienLouable(),
						this.getDateAnneeDerniere().toString(), this.getDateCetteAnnee().toString());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return 0.0f;
			}
		}

		return totalOrdures;
	}

	/**
	 * Retourne le total des provisions pour charges versées sur la période
	 * sélectionnée, pour le bien et pour ses garages associés.
	 * 
	 * @return le total des provisions versées par le locataire, en Float
	 */
	private Float getTotalProvisions() {
		DaoReleveCompteur daoC = new DaoReleveCompteur();
		Float totalProvisions = 0.0f;
		try {
			totalProvisions = daoC.totalProvisionsSurPeriode(this.bien.getIdBien(),
					this.getDateAnneeDerniere().toString(), this.getDateCetteAnnee().toString());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return 0.0f;
		}

		// Partie pour les garages associés
		for (Louer l : locationsGaragesAssocies) {
			try {
				totalProvisions += daoC.totalProvisionsSurPeriode(l.getIdBienLouable(),
						this.getDateAnneeDerniere().toString(), this.getDateCetteAnnee().toString());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return 0.0f;
			}
		}

		return totalProvisions;
	}

	/**
	 * Permet d'obtenir le total final de la régularisation sur la période de
	 * régularisation. Appelle une autre fonction SQL pour plus de sécurité, au lieu
	 * de simplement additionner la valeur des champs.
	 * 
	 * @return le total final de la régularisation, positif ou négatif, en float.
	 *         Positif : le locataire doit cette somme au propriétaire Négatif : le
	 *         propriétaire doit cette somme au locataire
	 */
	private Float getTotalRegularisation() {
		DaoReleveCompteur daoC = new DaoReleveCompteur();
		Float totalRegularisation = 0.0f;
		try {
			totalRegularisation = daoC.totalRegularisationSurPeriode(this.bien.getIdBien(),
					this.getDateAnneeDerniere().toString(), this.getDateCetteAnnee().toString());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return 0.0f;
		}

		for (Louer l : locationsGaragesAssocies) {
			try {
				totalRegularisation += daoC.totalRegularisationSurPeriode(l.getIdBienLouable(),
						this.getDateAnneeDerniere().toString(), this.getDateCetteAnnee().toString());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return 0.0f;
			}
		}

		return totalRegularisation;
	}

	/**
	 * Traite l'insertion d'un paiement émis spécifique dû à une régularisation
	 */
	private void traiterPaiementEmis() {
		DaoPaiement daoPaiement = new DaoPaiement();

		Paiement p = new Paiement(null, this.getTotalRegularisation() * -1, "emis", "regularisation", LocalDate.now(),
				bien.getIdBien(), null, null);

		try {
			daoPaiement.create(p);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Traite la répartition de la somme que le locataire doit au propriétaire sur
	 * le ou les loyers des mois suivants
	 */
	private void traiterAjoutMoisSuivant() {

		DaoLouer daoLouer = new DaoLouer();

		int nbMois = Integer.parseInt(this.fER.getTxtNombreDeMois().getText());
		if (nbMois > 12) {
			JOptionPane.showMessageDialog(null, "Vous ne pouvez pas ajuster le loyer sur plus de 12 mois.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return;
		}
		float totalRegul = this.getTotalRegularisation();
		float ajustParMois = totalRegul / nbMois;

		LocalDate finAjustement = LocalDate.now().withDayOfMonth(1).plusMonths(nbMois - 1L);
		LocalDate debutAjustement = LocalDate.now().withDayOfMonth(1);

		this.louer.setAjustementLoyer(ajustParMois);
		this.louer.setMoisDebutAjustement(debutAjustement);
		this.louer.setMoisFinAjustement(finAjustement);

		try {
			daoLouer.updateAjustementLoyer(this.louer);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Traite l'insertion d'un paiement reçu spécifique dû à une régularisation
	 */
	private void traiterLocataireAPaye() {
		DaoPaiement daoPaiement = new DaoPaiement();

		Paiement p = new Paiement(null, this.getTotalRegularisation(), "recus", "regularisation", LocalDate.now(),
				bien.getIdBien(), null, null);
		try {
			daoPaiement.create(p);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Traite la mise a jour de la provision du bien et actuallise les libellés de
	 * la fenêtre d'informations du bien.
	 * 
	 * @param nouvelleProvision Float, nouvelle provision à associer au bien
	 */
	private void traiterMAJProvision(Float nouvelleProvision) {
		// Faire une verif sur si c'est un nombre, max 2 chiffres après virgule
		DaoBienLouable daoBien = new DaoBienLouable();
		bien.setProvisionPourCharges(nouvelleProvision);
		try {
			daoBien.update(bien);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.fER.getFenAncetre().actualisationLibelles(bien); // mise a jour de la fenetre bien louable infos
		JOptionPane.showMessageDialog(null, "La provision pour charges a bien été ajustée", "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Verifie que la taxe d'ordures ménageres est strictement inférieure au montant
	 * des charges total
	 * 
	 * @return true si la taxe d'ordures est inférieur au montant des charges total,
	 *         false sinon
	 */
	public boolean verifTaxesOrdures() {
		float totalCharges = this.getTotalCharges();
		float totalOrdures = this.getTotalOrdures();

		if (totalOrdures >= totalCharges) {
			JOptionPane.showMessageDialog(null,
					"Le montant total des charges doit être strictement supérieur à la taxe des ordures ménagères.\nEntrez d'autres charges et réessayez.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

}