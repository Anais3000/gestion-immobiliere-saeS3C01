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
import modele.Historique;
import modele.Intervention;
import modele.dao.DaoIntervention;
import vue.ajouter.FenUpdateFactureInterventionBatiment;

public class GestionFenUpdateFactureInterventionBatiment implements ActionListener {

	private FenUpdateFactureInterventionBatiment fUF;
	private String idInterv;
	private DaoIntervention dao;

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter les informations
	 * de facturation d'une intervention rentrée précédemment
	 * 
	 * @param fUF      fenetre permettant l'ajout
	 * @param idInterv id de l'intervention à laquelle on veut rajouter les
	 *                 informations
	 */
	public GestionFenUpdateFactureInterventionBatiment(FenUpdateFactureInterventionBatiment fUF, String idInterv) {
		this.fUF = fUF;
		this.idInterv = idInterv;
		this.dao = new DaoIntervention();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			// On récupère les valeurs des champs dont on a besoin
			String numFacture = this.fUF.getTextFieldNumFactureIntervention().getText();
			String montantFacture = this.fUF.getTextFieldMontantFactureIntervention().getText();
			String dateFacture = this.fUF.getTextFieldDateFactureIntervention().getText();

			// On vérifie que tous les champs soient bien remplis
			if (numFacture == null || montantFacture == null || dateFacture.equals("DD-MM-AAAA")) {
				JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs.", "Erreur",
						JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					Intervention i = this.dao.findById(this.idInterv);

					// On initialise un Format pour les dates
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

					i.setNumFacture(numFacture);
					i.setMontantFacture(Float.parseFloat(montantFacture));
					i.setDateFacture(LocalDate.parse(dateFacture, formatter));

					this.dao.updateFacture(i);

					GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
							"Ajout Infos Facture Intervention", "+ " + idInterv + " - " + "Num Facture : " + numFacture
									+ " (" + Float.parseFloat(montantFacture) + "€)"));

					// Mise à jour de la fenetre en arrière plan des infos du BAT
					fUF.getFenAncestor().getGestionTableInterv().ecrireLigneTable();

					JOptionPane.showMessageDialog(null, "Informations mises à jour avec succès !", "Confirmation",
							JOptionPane.INFORMATION_MESSAGE);

					this.fUF.dispose();

				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
			break;
		case "Annuler":
			this.fUF.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

}
