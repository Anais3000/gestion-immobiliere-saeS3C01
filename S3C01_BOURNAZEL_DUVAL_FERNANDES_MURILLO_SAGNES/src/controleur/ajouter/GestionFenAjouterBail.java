package controleur.ajouter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import controleur.tables.fen_bien_louable.GestionTableFenBienLouable;
import modele.BienLouable;
import modele.Historique;
import modele.Locataire;
import modele.Louer;
import modele.Paiement;
import modele.dao.DaoBienLouable;
import modele.dao.DaoLocataire;
import modele.dao.DaoLouer;
import modele.dao.DaoPaiement;
import vue.ajouter.FenAjouterBail;
import vue.ajouter.FenChoisirOuAjouterGarant;

public class GestionFenAjouterBail implements ActionListener {

	private FenAjouterBail f;
	private ArrayList<BienLouable> garagesAssocies;
	private DaoLouer dao;

	/**
	 * Initialise la classe de gestion de la fenêtre pour créer un bail
	 * 
	 * @param f fenêtre permettant la création du bail
	 */
	public GestionFenAjouterBail(FenAjouterBail f) {
		this.f = f;
		this.garagesAssocies = new ArrayList<>();
		this.chargerLocataires();
		this.dao = new DaoLouer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {

		case "Ajouter le garage":

			this.traiterAjoutGarage();

			break;

		case "Valider":
			// On récupère les champs qu'on va utiliser
			String idLoc = f.getComboBoxLocataire().getSelectedItem().toString();
			String idGarant = f.getLabelGarant().getText();
			String dateDebut = f.getTextFieldDateDebut().getText();
			String dateFin = f.getTextFieldDateFin().getText();
			String depotGarantie = f.getTextFieldDepotGarantie().getText();
			String idBien = f.getBienConcerne().getIdBien();

			// On initialise les variables qu'on veut utiliser
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

			if (!this.verifierChamps(idLoc, idGarant, dateDebut, dateFin, depotGarantie)) {
				break;
			}

			LocalDate dateDebutForm = LocalDate.parse(dateDebut, formatter);
			LocalDate dateFinForm = LocalDate.parse(dateFin, formatter);

			Louer derniereLoc = null;
			try {
				derniereLoc = dao.findByIdBienLouable(idBien);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			// Traite l'ajout de la location en BD
			if (!this.traiterAjoutLoc(derniereLoc, dateDebutForm, dateFinForm, depotGarantie, idLoc, idBien,
					idGarant)) {
				break;
			}

			break;
		case "Ajouter un garant":
			FenChoisirOuAjouterGarant frame = new FenChoisirOuAjouterGarant(this.f);
			frame.setVisible(true);
			break;
		case "Annuler":
			this.f.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Charge l'ID des locataires dans la combo box
	 */
	public void chargerLocataires() {
		DaoLocataire daoLoc = new DaoLocataire();
		List<Locataire> liste = null;
		try {
			liste = (List<Locataire>) daoLoc.findAll();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (Locataire l : liste) {
			model.addElement(l.getIdLocataire());
		}
		f.getComboBoxLocataire().setModel(model);
	}

	/**
	 * Remplit la combobox des garages avec tous les garages non loués
	 */
	public void remplirComboGarages() {

		if (this.f.getBienConcerne().getTypeBien().equals("Garage")) {
			this.f.getBtnAjouterGarage().setVisible(false);
			this.f.getComboBoxGarage().setVisible(false);
			this.f.getLblAucunGarageDisp().setVisible(true);
			this.f.getLblAucunGarageDisp().setText("Impossible d'associer un garage à d'autres garages.");
		} else {

			DaoBienLouable daoL = new DaoBienLouable();
			ArrayList<BienLouable> garagesDispos = null;
			try {
				garagesDispos = (ArrayList<BienLouable>) daoL.findGaragesNonLoues();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.f.getComboBoxGarage().removeAllItems();
			for (BienLouable g : garagesDispos) {
				if (!(this.garagesAssocies.contains(g))) {
					this.f.getComboBoxGarage().addItem(g.getIdBien());
				}
			}
			if (this.f.getComboBoxGarage().getItemCount() == 0) {
				this.f.getBtnAjouterGarage().setVisible(false);
				this.f.getComboBoxGarage().setVisible(false);
				this.f.getLblAucunGarageDisp().setVisible(true);
			} else {
				this.f.getBtnAjouterGarage().setVisible(true);
				this.f.getComboBoxGarage().setVisible(true);
				this.f.getLblAucunGarageDisp().setVisible(false);
			}

		}
	}

	/**
	 * Mets à jour la liste des garages associés
	 */
	private void mettreAJourListeGarages() {
		StringBuilder stbd = new StringBuilder();

		for (BienLouable g : this.garagesAssocies) {
			if (stbd.length() > 0) {
				stbd.append(", ");
			}
			stbd.append(g.getIdBien());
		}
		this.f.getLblListeGarages().setText(stbd.toString());
	}

	/**
	 * Traite l'ajout d'un garage associé à un logement depuis le bouton "Ajouter le
	 * garage"
	 */
	private void traiterAjoutGarage() {
		String idGarage = this.f.getComboBoxGarage().getSelectedItem().toString();

		int res = JOptionPane.showConfirmDialog(null, "Associer le garage " + idGarage + " à ce bail ?", "Confirmation",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		// Si "YES" alors on ajoute le garage à liste des garages associés
		if (res == JOptionPane.YES_OPTION) {
			DaoBienLouable daoBien = new DaoBienLouable();
			BienLouable garage = null;
			try {
				garage = daoBien.findById(idGarage);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.garagesAssocies.add(garage); // ajout du garage a la liste des garages associes.
			this.remplirComboGarages();
			this.mettreAJourListeGarages();
		}
	}

	/**
	 * Vérifie la validité des champs saisis
	 * 
	 * @param idLoc         id du locataire
	 * @param idGarant      id du garant
	 * @param dateDebut     date de début du bail
	 * @param dateFin       date de fin du bail
	 * @param depotGarantie dépot de garantie
	 * 
	 * @return true si tous les champs saisis sont valides, false sinon
	 */
	private boolean verifierChamps(String idLoc, String idGarant, String dateDebut, String dateFin,
			String depotGarantie) {
		// On initialise les variables qu'on veut utiliser
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		// Vérifier que tous les champs sont renseignés
		if (idLoc.isEmpty() || idGarant.isEmpty() || dateDebut.isEmpty() || dateFin.isEmpty()
				|| depotGarantie.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		// Vérifier que la date de début du bail est au format date 'DD-MM-YYYY'
		try {
			LocalDate.parse(dateDebut, formatter);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
					"Format de date de début invalide. Veuillez entrer la date au format JJ-MM-AAAA (ex: 15-03-2020)",
					"Erreur de format", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que la date de fin du bail est au format date 'DD-MM-YYYY'
		try {
			LocalDate.parse(dateFin, formatter);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
					"Format de date de fin invalide. Veuillez entrer la date au format JJ-MM-AAAA (ex: 15-03-2020)",
					"Erreur de format", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le dépôt de garantie est un nombre à virgule (float)
		try {
			Float.parseFloat(depotGarantie);
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "Le dépôt de garantie doit être un nombre à virgule (ex: 100.5).",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		LocalDate dateDebutForm = LocalDate.parse(dateDebut, formatter);
		LocalDate dateFinForm = LocalDate.parse(dateFin, formatter);

		// Vérifier que la date de début du bail soit avant la date de fin
		if (dateFinForm.isBefore(dateDebutForm)) {
			JOptionPane.showMessageDialog(null, "La date de fin doit être après la date de début.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Traite l'ajout de la location en BD
	 * 
	 * @param derniereLoc   dernière location en date sur le bien
	 * @param dateDebutForm date de début de la location
	 * @param dateFinForm   date de fin de la location
	 * @param depotGarantie depot de garantie
	 * @param idLoc         id du locataire
	 * @param idBien        id du bien
	 * @param idGarant      id du garant
	 * 
	 * @return true si l'ajout a marché, false sinon
	 */
	private boolean traiterAjoutLoc(Louer derniereLoc, LocalDate dateDebutForm, LocalDate dateFinForm,
			String depotGarantie, String idLoc, String idBien, String idGarant) {

		// Vérifier que la date de début du nouveau locataire doit être après la date de
		// fin de l'ancien
		if (derniereLoc != null && dateDebutForm.isBefore(derniereLoc.getDateFin())) {
			JOptionPane
					.showMessageDialog(null,
							"La date de début doit être égale ou postérieure à la date de fin de la derniere loc : "
									+ derniereLoc.getDateFin().toString(),
							Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Verifier que la date de début de location est supérieure ou égale à la date
		// de construction du bien
		DaoBienLouable daoBien = new DaoBienLouable();
		BienLouable bien = null;
		try {
			bien = daoBien.findById(idBien);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifie le bien et la date de debut de locaiton
		if (!this.verifBienDateDebut(bien, idBien, dateDebutForm)) {
			return false;
		}

		Float depotGarantieF = Float.parseFloat(depotGarantie);

		Louer louer = new Louer(dateDebutForm, dateFinForm, depotGarantieF, null, null, null, null, idLoc, idBien,
				idGarant, null, null, null, null, 0, null);

		// Vérifier que le bail n'existe pas déjà dans la base de données
		try {
			dao.create(louer);
		} catch (SQLException e1) {
			if (e1.getErrorCode() == 1) {
				JOptionPane.showMessageDialog(null,
						"Vous ne pouvez pas recréer cette location pour cette date, ce locataire et ce bien",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		// Ajout de la location pour les garages
		for (BienLouable g : this.garagesAssocies) {
			louer = new Louer(dateDebutForm, dateFinForm, 0, null, null, null, null, idLoc, g.getIdBien(), idGarant,
					null, null, null, idBien, 0, null);
			try {
				dao.create(louer);
			} catch (SQLException e1) {
				if (e1.getErrorCode() == 1) {
					JOptionPane.showMessageDialog(null,
							"Vous ne pouvez pas recréer cette location pour cette date, ce locataire et ce bien",
							Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}

		JOptionPane.showMessageDialog(null, "La location est effective !.", "Information",
				JOptionPane.INFORMATION_MESSAGE);

		this.f.getFenAncetre().dispose(); // on ferme ancienne fenetre et on rouvre pour afficher les nouvelles
											// données
		try {
			GestionTableFenBienLouable.chargerFenetreBienLouable(this.f.getBienConcerne(), null); // en utilisant la
																									// methode de la
																									// gestion
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Insere le paiement du depot de garantie
		if (!this.traiterInsertionPaiement(depotGarantieF, dateDebutForm, idBien, idLoc, idGarant)) {
			return false;
		}

		this.f.dispose();

		return true;
	}

	/**
	 * Vérifie que le bien et la date de début sont valides
	 * 
	 * @param bien          bien louable concerné
	 * @param idBien        id du bien louable
	 * @param dateDebutForm date de début de location
	 * @return
	 */
	private boolean verifBienDateDebut(BienLouable bien, String idBien, LocalDate dateDebutForm) {
		if (bien == null) {
			JOptionPane.showMessageDialog(null, "Impossible de trouver le bien : " + idBien, Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (dateDebutForm.isBefore(bien.getDateConstruction())) {
			JOptionPane
					.showMessageDialog(null,
							"Impossible de créer un bail commenceant avant la date de construction du bien : "
									+ bien.getDateConstruction().toString(),
							Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	private boolean traiterInsertionPaiement(float depotGarantieF, LocalDate dateDebutForm, String idBien, String idLoc,
			String dateFinForm) {
		// Insertion du paiement dépot de garantie
		DaoPaiement daoP = new DaoPaiement();
		Paiement p = new Paiement(null, depotGarantieF, "recus", "Dépot de garantie", dateDebutForm, idBien, null,
				null);
		try {
			daoP.create(p);
			GestionTableHistoriqueFenPrinc.ajouterHistorique(
					new Historique(LocalDateTime.now(), "Ajout Bail", "+ " + idBien + " // Locataire : " + idLoc
							+ ", Date début bail : " + dateDebutForm + " - Date fin bail : " + dateFinForm));
			GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(), "Ajout Garant au Bail",
					"+ " + idBien + " // Locataire : " + idLoc + " - Dépot de garantie : " + depotGarantieF + "€"));
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}
