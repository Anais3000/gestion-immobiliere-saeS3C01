package controleur.ajouter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Historique;
import modele.Louer;
import modele.dao.DaoLouer;
import vue.ajouter.FenAjouterEtatDesLieux;

public class GestionFenAjouterEtatDesLieux implements ActionListener {

	private FenAjouterEtatDesLieux fAEDL;
	private DaoLouer daoLouer;
	private static final String ERREUR_EDL = "Erreur mise à jour EDL location.";

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter un etat des lieux
	 * 
	 * @param fAEDL fenetre permettant l'ajout
	 */
	public GestionFenAjouterEtatDesLieux(FenAjouterEtatDesLieux fAEDL) {
		this.fAEDL = fAEDL;
		this.daoLouer = new DaoLouer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			// Récupérer toutes les variables dont on a besoin
			String dateDebut = fAEDL.getTextFieldDateEDL().getText();
			Louer location = this.fAEDL.getLouer();

			// Initialiser toutes les variables qu'on va utiliser
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate dateDebutForm = null;

			// Vérifier que la date est au bon format ('DD-MM-YYYY')
			try {
				dateDebutForm = LocalDate.parse(dateDebut, formatter);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null,
						"Format de date invalide. Veuillez entrer la date au format JJ-MM-AAAA (ex: 15-03-2020)",
						"Erreur de format", JOptionPane.ERROR_MESSAGE);
				break;
			}

			JRadioButton btnEntree = this.fAEDL.getRdbtnEntreeEDL();

			if (btnEntree.isSelected()) {

				// Traiter l'ajout de l'EDL entrée
				if (!this.traiterEDLEntree(location, dateDebutForm)) {
					break;
				}

			} else { // sinon c'est le bouton sortie qui est sélectionné

				// Traiter l'ajout de l'EDL entrée
				if (!this.traiterEDLSortie(location)) {
					break;
				}

			}
			this.fAEDL.dispose();

			JOptionPane.showMessageDialog(null, "L'état des lieux a été enregistré avec succès.", "Information",
					JOptionPane.INFORMATION_MESSAGE);

			// On rend visible le bouton pour consulter l'edl d'entrée et on cache celui
			// pour l'ajouter
			this.fAEDL.getFenAncetre().getBtnAjouterEtatLieuxEntree().setVisible(false);
			this.fAEDL.getFenAncetre().getBtnConsulterEtatLieuxEntree().setVisible(true);

			// Et on rafraichit le tableau des EDL du bien
			this.fAEDL.getFenAncetre().getGestionTableAnciensEtatsLieux().ecrireLigneTable(location.getIdBienLouable());

			this.fAEDL.dispose();
			break;
		case "Annuler":
			this.fAEDL.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Ajoute l'EDL entrée pour le bien en question et ses garages associés
	 * 
	 * @param location      location à laquelle on veut ajouter l'etat des lieux
	 * @param dateDebutForm date de l'état des lieux
	 * 
	 * @return true si l'ajout s'est bien passé, false sinon
	 */
	private boolean traiterEDLEntree(Louer location, LocalDate dateDebutForm) {
		location.setDateEtatDesLieuxEntree(dateDebutForm);
		location.setDetailsEtatDesLieuxEntree(this.fAEDL.getTextAreaDetails().getText());
		try {
			daoLouer.updateEtatLieuxEntree(location);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, ERREUR_EDL, Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		ArrayList<Louer> garagesAssocies = null;
		try {
			garagesAssocies = (ArrayList<Louer>) daoLouer.findAllLouerGaragesAssociesLogement(
					location.getDateDebut().toString(), location.getDateFin().toString(), location.getIdBienLouable());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Erreur récupération garages associés.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		for (Louer g : garagesAssocies) {
			g.setDateEtatDesLieuxEntree(dateDebutForm);
			g.setDetailsEtatDesLieuxEntree(this.fAEDL.getTextAreaDetails().getText());
			try {
				daoLouer.updateEtatLieuxEntree(g);
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
						"Ajout détails Etat des lieux", "+ " + location.getIdBienLouable() + " ,"
								+ "Nouvean Locataire : " + location.getIdLocataire()));
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Erreur mise à jour des locations des garages.",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		return true;
	}

	/**
	 * Ajoute l'EDL sortie pour le bien en question et ses garages associés
	 * 
	 * @param location location à laquelle on veut ajouter l'état des lieux
	 * 
	 * @return true si l'ajout s'est bien passé, false sinon
	 */
	private boolean traiterEDLSortie(Louer location) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		LocalDate date = LocalDate.parse(this.fAEDL.getTextFieldDateEDL().getText(), formatter);
		location.setDateEtatDesLieuxSortie(date);
		location.setDetailsEtatDesLieuxSortie(this.fAEDL.getTextAreaDetails().getText());
		try {
			daoLouer.updateEtatLieuxSortie(location);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, ERREUR_EDL, Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Partie garages rendus en même temps que le logement (ajout auto de l'état des
		// lieux, le même que celui du logement)
		ArrayList<Louer> garagesAssocies = null;
		try {
			garagesAssocies = (ArrayList<Louer>) daoLouer.findAllLouerGaragesAssociesLogement(
					location.getDateDebut().toString(), location.getDateFin().toString(), location.getIdBienLouable());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Erreur récupération garages associés.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		for (Louer g : garagesAssocies) {
			g.setDateEtatDesLieuxSortie(LocalDate.parse(this.fAEDL.getTextFieldDateEDL().getText(), formatter));
			g.setDetailsEtatDesLieuxSortie(this.fAEDL.getTextAreaDetails().getText());
			try {
				daoLouer.updateEtatLieuxSortie(g);
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
						"Ajout détails Etat des lieux",
						"+ " + location.getIdBienLouable() + " ," + "Ancien Locataire : " + location.getIdLocataire()));
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, ERREUR_EDL, Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		return true;
	}

}
