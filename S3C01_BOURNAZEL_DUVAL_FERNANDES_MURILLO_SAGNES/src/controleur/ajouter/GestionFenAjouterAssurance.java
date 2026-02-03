package controleur.ajouter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Assurance;
import modele.Historique;
import modele.dao.DaoAssurance;
import vue.ajouter.FenAjouterAssurance;

public class GestionFenAjouterAssurance implements ActionListener {

	private FenAjouterAssurance fAA;
	private String idBat;

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter une assurance
	 * 
	 * @param fAA   fenêtre permettant l'ajout
	 * @param idBat id du batiment sur lequel ajouter l'assurance
	 */
	public GestionFenAjouterAssurance(FenAjouterAssurance fAA, String idBat) {
		this.fAA = fAA;
		this.idBat = idBat;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":
			// On récupère les champs
			String numPolice = fAA.getTextFieldNumPoliceAssurance().getText();
			String typeContrat = "";
			if (fAA.getRdbtnTypeUn().isSelected()) {
				typeContrat = "propriétaire";
			} else {
				typeContrat = "aide juridique";
			}

			boolean typeUn = fAA.getRdbtnTypeUn().isSelected();
			boolean typeDeux = fAA.getRdbtnTypeDeux().isSelected();
			String anneeTxt = fAA.getTextFieldAnneeCouvertureAssurance().getText();
			String montantTxt = fAA.getTextFieldMontantPayeAssurance().getText();

			if (!verifications(numPolice, typeUn, typeDeux, anneeTxt, montantTxt)) {
				break;
			}

			this.traitementAjoutAssurance(numPolice, typeContrat, anneeTxt, montantTxt);

			break;
		case "Annuler":
			this.fAA.dispose();
			break;
		default:
			JOptionPane.showMessageDialog(null, "Action inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/*
	 * Verifie si les champs saisis sont corrects
	 */
	private boolean verifications(String numPolice, boolean typeUn, boolean typeDeux, String anneeTxt,
			String montantTxt) {

		// Vérifier que tous les champs sont remplis
		if (numPolice.trim().isEmpty() || (!typeUn && !typeDeux) || anneeTxt.trim().isEmpty()
				|| montantTxt.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que l'année est de 4 chiffres
		if (!anneeTxt.matches("\\d{4}")) {
			JOptionPane.showMessageDialog(null, "L'année invalide !", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le montant est un nombre à virgule
		try {
			Float.parseFloat(montantTxt);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Le montant doit être un nombre à virgule (ex: 100.5).",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Traitement pour l'ajout en BD de l'assurance
	 * 
	 * @param numPolice   le numéro de police d'assurance
	 * @param typeContrat le type de contrat d'assurance (aide juridique ou
	 *                    propriétaire)
	 * @param anneeTxt    l'année couverte sous forme texte (String)
	 * @param montant     le montant de l'assurance sur l'année couverte
	 */
	private void traitementAjoutAssurance(String numPolice, String typeContrat, String anneeTxt, String montant) {
		try {
			DaoAssurance dao = new DaoAssurance();

			Assurance assurance = new Assurance(numPolice, typeContrat, Integer.valueOf(anneeTxt),
					Double.valueOf(montant), idBat);
			dao.create(assurance);

			GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
					"Ajout Assurance à un Bâtiment", "+ " + assurance.getNumPoliceAssurance() + " - "
							+ assurance.getAnneeCouverture() + " / Bat : " + this.idBat));

			// Mise à jour de la fenetre en arrière plan des infos du BAT
			fAA.getFenAncestor().getGestionTableAss().ecrireLigneTable();

			JOptionPane.showMessageDialog(null, "Assurance ajoutée avec succès !", "Confirmation",
					JOptionPane.INFORMATION_MESSAGE);

			this.fAA.dispose();

		} catch (SQLException e1) {
			if (e1.getErrorCode() == 1) {
				JOptionPane.showMessageDialog(fAA, "Erreur : l'identifiant de l'assurance existe déjà !",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
