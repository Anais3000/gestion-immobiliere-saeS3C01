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
import modele.Diagnostic;
import modele.Historique;
import modele.dao.DaoDiagnostic;
import vue.ajouter.FenAjouterDiagnosticBien;

public class GestionFenAjouterDiagnosticBien implements ActionListener {

	private FenAjouterDiagnosticBien fAD;
	private String idBien;

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter un diagnostic à un
	 * bien
	 * 
	 * @param fAD    fenetre permettant l'ajout
	 * @param idBien id du bien sur lequel ajouter le diagnostic
	 */
	public GestionFenAjouterDiagnosticBien(FenAjouterDiagnosticBien fAD, String idBien) {
		this.fAD = fAD;
		this.idBien = idBien;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			// Récupérer tous les champs dont on a besoin
			String type = fAD.getComboBoxLibDiagnostic().getSelectedItem().toString();
			String dateDebut = fAD.getTextFieldDateDebutDiagnostic().getText();
			String dateFin = fAD.getTextDateFinDiagnostic().getText();
			String detail = fAD.getDetailsDiagnostic().getText();

			// Initialisation de toutes les variables dont on aura besoin pour vérifier le
			// format des dates
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate dateDebutForma = null;
			LocalDate dateFinForma = null;

			// Verifier les champs
			if (!this.verifierChamps(type, dateDebut, dateFin)) {
				break;
			}

			dateDebutForma = LocalDate.parse(dateDebut, formatter);
			dateFinForma = LocalDate.parse(dateFin, formatter);

			// Traite l'ajout du diagnostic en BD
			if (!this.traiterAjout(type, dateDebutForma, dateFinForma, detail)) {
				break;
			}

			break;
		case "Annuler":
			this.fAD.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Verifier la validité des champs saisis
	 * 
	 * @param type      type de diagnostic
	 * @param dateDebut date début de validité du diagnostic
	 * @param dateFin   date fin de validité du diagnostic
	 * 
	 * @return true si tous les champs sont valides, false sinon
	 */
	private boolean verifierChamps(String type, String dateDebut, String dateFin) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		// Vérifier que tous les champs requis sont renseignés
		if (type.isEmpty() || dateDebut.isEmpty() || dateFin.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez renseigner les champs requis.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			LocalDate.parse(dateDebut, formatter);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
					"Format de date de début invalide. Veuillez entrer la date au format JJ-MM-AAAA (ex: 15-03-2020)",
					"Erreur de format", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que la date de fin est bien au format 'DD-MM-YYYY'
		try {
			LocalDate.parse(dateFin, formatter);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
					"Format de date de fin invalide. Veuillez entrer la date au format JJ-MM-AAAA (ex: 15-03-2020)",
					"Erreur de format", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Traite l'ajout du diagnostic en BD
	 * 
	 * @param type           du diagnostic
	 * @param dateDebutForma date début de validité du diagnostic
	 * @param dateFinForma   date fin de validité du diagnostic
	 * @param detail         détails du diagnostic
	 * 
	 * @return true si l'ajout s'est bien passé, false sinon
	 */
	private boolean traiterAjout(String type, LocalDate dateDebutForma, LocalDate dateFinForma, String detail) {
		Diagnostic d = new Diagnostic(type, dateDebutForma, dateFinForma, detail, idBien);

		DaoDiagnostic dao = new DaoDiagnostic();

		// Vérifier que le diagnostic a ajouter n'existe pas déjà dans la base de
		// données
		try {
			dao.create(d);
			GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
					"Ajout Diagnostic Bien Louable", "+ " + type + " // " + idBien));
		} catch (SQLException e1) {
			if (e1.getErrorCode() == 1) {
				JOptionPane.showMessageDialog(null, "Cet ID diagnostic existe déjà.", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		// Mise à jour de la fenetre en arrière plan des infos du BAT
		fAD.getFenAncetre().getGestionTableDiag().ecrireLigneTable(idBien);

		JOptionPane.showMessageDialog(null, "Diagnostic ajouté avec succès !", "Confirmation",
				JOptionPane.INFORMATION_MESSAGE);

		this.fAD.dispose();

		return true;
	}

}
