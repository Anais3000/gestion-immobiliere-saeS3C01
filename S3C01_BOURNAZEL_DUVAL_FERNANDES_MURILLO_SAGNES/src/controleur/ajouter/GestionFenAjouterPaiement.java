package controleur.ajouter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Historique;
import modele.Paiement;
import modele.dao.DaoPaiement;
import vue.ajouter.FenAjouterPaiementOccasionnel;
import vue.tables.FenPaiement;

public class GestionFenAjouterPaiement implements ActionListener {

	private FenAjouterPaiementOccasionnel fAP;
	private FenPaiement fenAncetre;
	private DaoPaiement dao;

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter un paiement
	 * 
	 * @param fenAncetre fenetre contenant la liste de tous les paiements (pour
	 *                   rafraichissement)
	 * @param fAP        fenetre permettant l'ajout
	 */
	public GestionFenAjouterPaiement(FenPaiement fenAncetre, FenAjouterPaiementOccasionnel fAP) {
		this.fAP = fAP;
		this.fenAncetre = fenAncetre;
		this.dao = new DaoPaiement();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":
			// Récupérer les valeurs
			String montant = fAP.getTextFieldMontant().getText();
			String libelle = fAP.getTextFieldLibelle().getText();
			String dateString = fAP.getTextFieldDatePaiement().getText();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

			String sens = "";
			if (fAP.getRdbtnEmisPaiement().isSelected()) {
				sens = "emis";
			}
			if (fAP.getRdbtnRecuPaiement().isSelected()) {
				sens = "recus";
			}

			// Vérifier la validité des champs saisis
			if (!this.verifierChamps(libelle, sens, dateString, montant)) {
				break;
			}

			Float montantFloat = Float.parseFloat(montant);

			LocalDate date = LocalDate.parse(dateString, formatter);

			Paiement p = new Paiement(null, montantFloat, sens, libelle, date, null, null, null);

			try {
				dao.create(p);
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(), "Ajout Paiement",
						"+ " + this.fAP.getTextFieldLibelle().getText() + " / Montant : "
								+ this.fAP.getTextFieldMontant().getText() + " €"));
			} catch (SQLException e1) {
				if (e1.getErrorCode() == 1) {
					JOptionPane.showMessageDialog(null, "Cet ID paiement existe déjà.", Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
				}
				break;
			}

			// Pour rafraichir les valeurs du tableau
			this.fenAncetre.getGestionTable().appliquerFiltres();
			this.fAP.dispose();

			JOptionPane.showMessageDialog(null, "Le paiement a bien été noté", "Information",
					JOptionPane.INFORMATION_MESSAGE);

			break;
		case "Annuler":
			this.fAP.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Vérifie la validité des champs attendus
	 * 
	 * @param libelle    libellé du paiement
	 * @param sens       sens du paiement, 'émis' ou 'recus'
	 * @param dateString date du paiement
	 * @param montant    montant du paiement
	 * 
	 * @return true si tous les champs sont conformes, false sinon
	 */
	private boolean verifierChamps(String libelle, String sens, String dateString, String montant) {
		// Vérifier que les valeurs saisies sont correctes
		if (libelle.trim().isEmpty() || sens.trim().isEmpty() || dateString.trim().isEmpty()
				|| montant.trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			Float.parseFloat(montant);
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "Le montant doit être un nombre à virgule (ex: 100.5).",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate.parse(dateString, formatter);
		} catch (DateTimeParseException ex) {
			JOptionPane.showMessageDialog(null, "La date doit être au format DD-MM-YYYY", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

}
