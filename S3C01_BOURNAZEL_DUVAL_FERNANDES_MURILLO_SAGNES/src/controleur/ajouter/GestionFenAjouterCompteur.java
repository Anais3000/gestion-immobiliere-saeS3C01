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
import modele.Compteur;
import modele.Historique;
import modele.dao.DaoCompteur;
import vue.ajouter.FenAjouterCompteur;
import vue.consulter_informations.FenBatimentInformations;
import vue.consulter_informations.FenBienLouableInformations;

public class GestionFenAjouterCompteur implements ActionListener {

	private FenAjouterCompteur fAC;
	private String id;
	private String batimentOuBien;
	private static final String BATIMENT = "batiment";
	private static final String BIEN = "bien";

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter un compteur
	 * 
	 * @param fAC fenetre permettant l'ajout
	 * @param id  id du bien ou du batiment sur lequel ajouter le compteur
	 */
	public GestionFenAjouterCompteur(FenAjouterCompteur fAC, String id) {
		this.fAC = fAC;
		this.id = id;
		this.batimentOuBien = fAC.getBatimentOuBien();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

			// On récupère les champs dont on a besoin
			String idCompt = fAC.getTextFieldIdCompteur().getText();
			String type = fAC.getComboBoxTypeCompteur().getSelectedItem().toString();

			// On initialise les variables qu'on veut utiliser
			LocalDate dateInstal = null;
			Float indexDep = null;

			// Vérification des champs saisis
			if (!this.verifChamps()) {
				break;
			}

			dateInstal = LocalDate.parse(fAC.getTextFieldDateInstallation().getText(), formatter);
			indexDep = Float.parseFloat(fAC.getTextFieldIndexDep().getText());

			if (type.equals("Eau")) {
				type = "Eau";
			} else {
				type = "Electricité";
			}

			// Vérifier que tous les champs sont bien renseignés
			if (idCompt.isEmpty() || type.isEmpty() || dateInstal == null || indexDep == null) {
				JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs.", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			// Ajout compteur en BD avec la fonction
			if (!this.ajoutCompteur(type, idCompt, dateInstal, indexDep)) {
				break;
			}

			// Mettre a jour la fenetre en arriere plan
			this.traiterMajFenetreArrierePlan();

			break;
		case "Annuler":
			this.fAC.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Vérifie que les champs saisis sont au bon format
	 * 
	 * @return true si les formats sont bons, false sinon
	 */
	private boolean verifChamps() {
		// Vérifier que la date est au format "DD-MM-YYYY"
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate.parse(fAC.getTextFieldDateInstallation().getText(), formatter);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
					"Format de date invalide. Veuillez entrer la date au format JJ-MM-AAAA (ex: 15-03-2020)",
					"Erreur de format", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que l'index est bien un nombre à virgule (float)
		Float indexDep = null;
		try {
			indexDep = Float.parseFloat(fAC.getTextFieldIndexDep().getText());
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "L'index doit être un nombre à virgule (ex: 100.5).",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que l'index est positif
		if (indexDep < 0) {
			JOptionPane.showMessageDialog(null, "L'index doit être positif.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Traite l'ajout d'un compteur en BD
	 * 
	 * @param type       type du compteur (électricité, eau, etc..)
	 * @param idCompt    id du compteur
	 * @param dateInstal date d'installation du compteur
	 * @param indexDep   index de départ du compteur
	 * 
	 * @return true si l'ajout a fonctionné, false sinon
	 */
	private boolean ajoutCompteur(String type, String idCompt, LocalDate dateInstal, Float indexDep) {
		Compteur c;
		if (batimentOuBien.equals(BATIMENT)) {
			c = new Compteur(id, null, type, idCompt, dateInstal, indexDep);
		} else if (batimentOuBien.equals(BIEN)) {
			c = new Compteur(null, id, type, idCompt, dateInstal, indexDep);
		} else {
			throw new IllegalArgumentException("La valeur de batiment ou bien n'est ni batiment ni bien.");
		}

		DaoCompteur dao = new DaoCompteur();

		// Vérifier que le compteur bâtiment a ajouté n'existe pas déjà pas dans la base
		// de données
		try {
			dao.create(c);
			// HISTORIQUE
			if (batimentOuBien.equals(BATIMENT)) {
				GestionTableHistoriqueFenPrinc
						.ajouterHistorique(new Historique(LocalDateTime.now(), "Ajout Compteur à un Bâtiment",
								"+ " + c.getIdCompteur() + " [" + c.getTypeCompteur() + "] / Bat : " + c.getIdBat()));
			} else {
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
						"Ajout Compteur à un bien louable",
						"+ " + c.getIdCompteur() + " [" + c.getTypeCompteur() + "] / Bien Louable : " + c.getIdBien()));
			}

		} catch (SQLException e1) {
			if (e1.getErrorCode() == 1) {
				JOptionPane.showMessageDialog(null, "Cet ID compteur existe déjà.", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		// Mise à jour de la fenetre en arrière plan des infos du BAT
		if (batimentOuBien.equals(BATIMENT)) {
			FenBatimentInformations fBLI = (FenBatimentInformations) fAC.getFenAncetre();
			fBLI.getGestionTableCompt().ecrireLigneTable();
		} else {
			FenBienLouableInformations fBI = (FenBienLouableInformations) fAC.getFenAncetre();
			fBI.getGestionTableCompt().ecrireLigneTable(id);
		}

		JOptionPane.showMessageDialog(null, "Compteur ajouté avec succès !", "Confirmation",
				JOptionPane.INFORMATION_MESSAGE);
		this.fAC.dispose();

		return true;
	}

	/**
	 * Traite la mise à jour de la fenetre en arriere plan
	 */
	private void traiterMajFenetreArrierePlan() {
		// Mise à jour de la fenêtre en arrière-plan
		if (batimentOuBien.equals(BATIMENT)) {
			FenBatimentInformations fBI = (FenBatimentInformations) fAC.getFenAncetre();
			fBI.getGestionTableCompt().ecrireLigneTable();
		} else if (batimentOuBien.equals(BIEN)) {
			FenBienLouableInformations fBLI = (FenBienLouableInformations) fAC.getFenAncetre();
			fBLI.getGestionTableCompt().ecrireLigneTable(id);
		}
	}

}
