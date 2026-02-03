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
import modele.Historique;
import modele.Locataire;
import modele.Louer;
import modele.Paiement;
import modele.dao.DaoLouer;
import modele.dao.DaoPaiement;
import modele.dao.DaoReleveCompteur;
import vue.FenFinDeBail;
import vue.ajouter.FenAjouterEtatDesLieux;

public class GestionFenFinDeBail implements ActionListener {

	private FenFinDeBail fenetre;
	private Louer location;
	private Locataire locataire;
	private ArrayList<Louer> listeLocationsGarages;
	private DaoLouer daoL = new DaoLouer();

	/**
	 * Initialise la classe de gestion de la fenêtre de fin de bail
	 * 
	 * @param f         fenetre de fin de bail
	 * @param location  location à laquelle mettre fin
	 * @param locataire locataire quittant la location
	 */
	public GestionFenFinDeBail(FenFinDeBail f, Louer location, Locataire locataire) {
		this.fenetre = f;
		this.location = location;
		this.locataire = locataire;
		this.daoL = new DaoLouer();
		try {
			this.listeLocationsGarages = (ArrayList<Louer>) daoL.findAllLouerGaragesAssociesLogement(
					this.location.getDateDebut().toString(), this.location.getDateFin().toString(),
					this.location.getIdBienLouable());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Calculer total dû":
			this.calculerTotalDu();
			break;
		case "Ajouter l'état des lieux de sortie et mettre fin au bail":

			if (!this.verifierPreconditions()) {
				break;
			}

			Float totalDu = this.getTotalDuFinDeBail();

			if (this.fenetre.getRdbtnPaiementEmis().isSelected()) {
				this.traitementEmis(totalDu);
			}

			if (this.fenetre.getRdbtnPaiementRecu().isSelected()) {
				this.traiterRecu(totalDu);
			}

			if (this.fenetre.getRdbtnPaiementPasFait().isSelected()) {
				this.traiterPasFait();
			}

			try {
				this.daoL.update(this.location);
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(), "Fin de Bail",
						"- " + this.location.getIdBienLouable() + " // Locataire : " + this.locataire.getIdLocataire()
								+ ", Total dû :" + this.getTotalDuFinDeBail()));
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}

			break;
		case "Quitter":
			this.fenetre.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Initialise les champs de la fenêtre, comme par exemple le total des loyers
	 * impayés, le total des charges payées, etc...
	 */
	public void remplirChamps() {
		this.fenetre.getTextFieldNomLocataire().setText(this.locataire.getNom());
		this.fenetre.getTextFieldIdLoc().setText(this.locataire.getIdLocataire());
		this.fenetre.getTextFieldIdBienLouable().setText(this.location.getIdBienLouable());

		// Partie pour les garages associés
		StringBuilder sb = new StringBuilder();

		for (Louer g : this.listeLocationsGarages) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(g.getIdBienLouable());
		}

		if (sb.length() == 0) {
			this.fenetre.getTextFieldGarages().setText("Aucun garage associé.");
		} else {
			this.fenetre.getTextFieldGarages().setText(sb.toString());
		}

		String periode = this.getDateDebutPeriode() + " - " + this.getDateFinPeriode();
		this.fenetre.getTextFieldPeriode().setText(periode);

		this.fenetre.getTextFieldChargesPayees().setText(String.valueOf(this.getTotalChargesPayees()));
		this.fenetre.getTextFieldChargesVersee().setText(String.valueOf(this.getTotalProvisionsVersees()));
		this.fenetre.getTextFieldDegradations().setText("0");
		this.fenetre.getTextFieldLoyersImpayes().setText(String.valueOf(this.getTotalLoyersImpayes()));

		this.fenetre.getTextFieldAjustementsRestants().setText(String.valueOf(this.getTotalAjustementsRestants()));

		this.fenetre.getTextFieldDepotGarantie().setText(String.valueOf(this.location.getDepotDeGarantie()));

	}

	/**
	 * Retourne la date de début de période pour les calculs de charges, etc..
	 * 
	 * @return la date de début de période en LocalDate
	 */
	private LocalDate getDateDebutPeriode() {
		LocalDate dateEntree = this.location.getDateDebut();

		// Premier anniversaire du bail
		LocalDate premierAnniversaire = dateEntree.plusYears(1);

		if (LocalDate.now().isBefore(premierAnniversaire)) {
			return dateEntree;
		}

		if (this.location.getDateDerniereRegul() == null) {
			return dateEntree.withYear(LocalDate.now().getYear() - 1);
		} else {
			return location.getDateDerniereRegul().plusDays(1);
		}
	}

	/**
	 * Retourne la date de fin de période pour les calculs de charges, etc
	 * 
	 * @return la date de fin de période sous forme de LocalDate
	 */
	private LocalDate getDateFinPeriode() {
		return LocalDate.now();
	}

	/**
	 * Retourne le total de charges payées par le propriétaire sur la période
	 * 
	 * @return le total des charges, en Float
	 */
	private Float getTotalChargesPayees() {
		DaoReleveCompteur daoRC = new DaoReleveCompteur();
		Float totalChargesPayees = null;
		try {
			totalChargesPayees = daoRC.totalChargesSurPeriode(this.location.getIdBienLouable(),
					this.getDateDebutPeriode().toString(), this.getDateFinPeriode().toString());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return 0.0f;
		}

		// Partie garages
		for (Louer g : this.listeLocationsGarages) {
			try {
				totalChargesPayees += daoRC.totalChargesSurPeriode(g.getIdBienLouable(),
						this.getDateDebutPeriode().toString(), this.getDateFinPeriode().toString());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return 0.0f;
			}
		}

		return totalChargesPayees;
	}

	/**
	 * Retourne le total de provisions versées par le locataire sur la période
	 * 
	 * @return le total des provisions versées, en Float
	 */
	private Float getTotalProvisionsVersees() {
		DaoReleveCompteur daoRC = new DaoReleveCompteur();
		Float totalProvisionsVersees = null;
		try {
			totalProvisionsVersees = daoRC.totalProvisionsSurPeriode(this.location.getIdBienLouable(),
					this.getDateDebutPeriode().toString(), this.getDateFinPeriode().toString());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return 0.0f;
		}

		// Partie garages
		for (Louer g : this.listeLocationsGarages) {
			try {
				totalProvisionsVersees += daoRC.totalProvisionsSurPeriode(g.getIdBienLouable(),
						this.getDateDebutPeriode().toString(), this.getDateFinPeriode().toString());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return 0.0f;
			}
		}

		return totalProvisionsVersees;
	}

	/**
	 * Retourne le total de loyers impayés par le locataire depuis le début du bail
	 * 
	 * @return le total des loyers impayés, en Float
	 */
	private Float getTotalLoyersImpayes() {
		Float totalLoyersImpayes = null;
		try {
			totalLoyersImpayes = daoL.totalLoyersImpayes(this.location.getIdBienLouable(),
					this.locataire.getIdLocataire(), this.location.getDateDebut().toString());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return 0.0f;
		}

		// Partie garages
		for (Louer g : this.listeLocationsGarages) {
			try {
				totalLoyersImpayes += daoL.totalLoyersImpayes(g.getIdBienLouable(), g.getIdLocataire(),
						g.getDateDebut().toString());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return 0.0f;
			}
		}

		return totalLoyersImpayes;
	}

	/**
	 * Retourne le potentiel total d'ajustements de loyer restants à payer par le
	 * locataire suite à une régularisation des charges
	 * 
	 * @return le total d'ajustements restants, en Float
	 */
	private Float getTotalAjustementsRestants() {
		Float totalAjustementsRestants = null;
		try {
			totalAjustementsRestants = daoL.totalAjustementsRestants(this.location.getIdBienLouable(),
					this.locataire.getIdLocataire(), this.location.getDateDebut().toString());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return 0.0f;
		}

		// Partie garages
		for (Louer g : this.listeLocationsGarages) {
			try {
				totalAjustementsRestants += daoL.totalAjustementsRestants(g.getIdBienLouable(), g.getIdLocataire(),
						g.getDateDebut().toString());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return 0.0f;
			}
		}

		return totalAjustementsRestants;
	}

	/**
	 * Retourne le total dû final en fin de bail (solde de tout compte)
	 * 
	 * @return le total dû, en Float
	 */
	private Float getTotalDuFinDeBail() {
		String idBien = this.location.getIdBienLouable();
		String idLocataire = this.locataire.getIdLocataire();
		String dateDebut = this.location.getDateDebut().toString();
		String montantDegrads = this.fenetre.getTextFieldDegradations().getText();
		String dateDebutPeriode = this.getDateDebutPeriode().toString();
		String dateFinPeriode = this.getDateFinPeriode().toString();

		Float totalDu = 0.0f;
		try {
			totalDu = daoL.totalDuFinDeBail(idBien, idLocataire, dateDebut, montantDegrads, dateDebutPeriode,
					dateFinPeriode);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return 0.0f;
		}

		// Partie garages
		for (Louer g : this.listeLocationsGarages) {
			try {
				totalDu += daoL.totalDuFinDeBail(g.getIdBienLouable(), g.getIdLocataire(), g.getDateDebut().toString(),
						String.valueOf(0), dateDebutPeriode, dateFinPeriode);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return 0.0f;
			}
		}

		return totalDu;
	}

	/**
	 * Met à jour les elements graphiques de la fenetre en fonction du resultat du
	 * calcul du total dû
	 */
	private void calculerTotalDu() {
		Float totalDu = this.getTotalDuFinDeBail();
		fenetre.getTextFieldTotalDu().setText(String.valueOf(totalDu));
		if (totalDu > 0) {
			fenetre.getRdbtnPaiementRecu().setVisible(true);
			fenetre.getRdbtnPaiementPasFait().setVisible(true);
			fenetre.getRdbtnPaiementEmis().setVisible(false);
		} else {
			fenetre.getRdbtnPaiementEmis().setVisible(true);
			fenetre.getRdbtnPaiementPasFait().setVisible(true);
			fenetre.getRdbtnPaiementRecu().setVisible(false);
		}
	}

	/**
	 * Vérifie les préconditions pour appuyer sur le bouton "mettre fin au bail"
	 * 
	 * @return true si les boutons de paiement sont sélectionnés et si le total dû a
	 *         été calculé, false sinon
	 */
	private boolean verifierPreconditions() {
		if (this.fenetre.getTextFieldTotalDu().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez calculer le total dû.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!(this.fenetre.getRdbtnPaiementEmis().isSelected() || this.fenetre.getRdbtnPaiementPasFait().isSelected()
				|| this.fenetre.getRdbtnPaiementRecu().isSelected())) {
			JOptionPane.showMessageDialog(null, "Veuillez sélectionner une option de paiement.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	/**
	 * Termine la location et met à jour les champs de la fenetre du bien louable
	 * 
	 * 
	 * @throws SQLException
	 */
	private void terminerLocation() throws SQLException {
		this.location.setDateFin(LocalDate.now());
		this.location.setRevolue(1);
		this.fenetre.getFenAncetre().desactiverChampsBail();
		this.fenetre.getFenAncetre().getGestionTableSTC().ecrireLigneTable(this.location.getIdBienLouable());
		this.daoL.update(this.location);

		for (Louer g : this.listeLocationsGarages) {
			g.setDateFin(LocalDate.now());
			g.setRevolue(1);
			this.daoL.update(g);
		}

		JOptionPane.showMessageDialog(null, "Le bail est terminé.", "Validation", JOptionPane.INFORMATION_MESSAGE);

		FenAjouterEtatDesLieux fenAEDL = new FenAjouterEtatDesLieux(this.fenetre.getFenAncetre(), this.location,
				FenAjouterEtatDesLieux.Type.SORTIE);
		fenAEDL.setVisible(true);

		this.fenetre.dispose();
	}

	/**
	 * Traiter l'insertion d'un paiement émis Multiplie le total du paiement par
	 * moins 1 car un paiement émis depuis cette fenetre est forcément d'un montant
	 * négatif, montant que le propriétaire doit payer au locataire
	 * 
	 * Cette fonction ne fait pas l'objet d'un trigger SQL car il est possible en
	 * fin de bail que le locataire soit parti sans payer, ainsi il n'y a pas une
	 * insertion de paiement à chaque fin de bail
	 * 
	 * @param totalDu le total du paiement
	 */
	private void traitementEmis(Float totalDu) {
		DaoPaiement daoP = new DaoPaiement();

		int choix = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir mettre fin au bail ?"
				+ "\nUn paiement de " + totalDu * -1 + "€ sera marqué comme émis.", "Confirmation",
				JOptionPane.YES_NO_OPTION);

		this.calculerTotalDu();
		if (choix == JOptionPane.YES_OPTION) {
			Paiement p = new Paiement(null, totalDu * -1, "emis", "solde de tout compte", LocalDate.now(),
					this.location.getIdBienLouable(), null, null);
			try {
				daoP.create(p);
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
						"Ajout Paiement au Locataire", "+ solde de tout compte // Locataire : "
								+ this.locataire.getIdLocataire() + ", Montant émis :" + totalDu));
				this.terminerLocation();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Traiter l'insertion d'un paiement recu
	 * 
	 * Cette fonction ne fait pas l'objet d'un trigger SQL car il est possible en
	 * fin de bail que le locataire soit parti sans payer, ainsi il n'y a pas une
	 * insertion de paiement à chaque fin de bail
	 * 
	 * @param totalDu le total du paiement
	 */
	private void traiterRecu(Float totalDu) {
		DaoPaiement daoP = new DaoPaiement();

		int choix = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir mettre fin au bail ?"
				+ "\nUn paiement de " + totalDu + "€ sera marqué comme reçu.", "Confirmation",
				JOptionPane.YES_NO_OPTION);

		this.calculerTotalDu();
		if (choix == JOptionPane.YES_OPTION) {
			Paiement p = new Paiement(null, totalDu, "recus", "solde de tout compte", LocalDate.now(),
					this.location.getIdBienLouable(), null, null);

			try {
				daoP.create(p);
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
						"Ajout Paiement du Locataire", "+ solde de tout compte // Locataire : "
								+ this.locataire.getIdLocataire() + ", Montant reçu:" + totalDu));
				this.terminerLocation();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/*
	 * Traiter le cas ou le paiement n'est pas fait
	 */
	private void traiterPasFait() {
		int choix = JOptionPane.showConfirmDialog(null,
				"Êtes-vous sûr de vouloir mettre fin au bail ?" + "\nAucun paiement ne sera enregistré.", "Attention",
				JOptionPane.WARNING_MESSAGE);

		if (choix == JOptionPane.YES_OPTION) {
			try {
				this.terminerLocation();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
