package controleur.consulter_informations.fen_batiment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Assurance;
import modele.Batiment;
import modele.Compteur;
import modele.Historique;
import modele.Intervention;
import modele.Paiement;
import modele.ReleveCompteur;
import modele.dao.DaoAssurance;
import modele.dao.DaoBatiment;
import modele.dao.DaoCompteur;
import modele.dao.DaoIntervention;
import modele.dao.DaoPaiement;
import modele.dao.DaoReleveCompteur;
import vue.ajouter.FenAjouterAssurance;
import vue.ajouter.FenAjouterBienLouable;
import vue.ajouter.FenAjouterCompteur;
import vue.ajouter.FenAjouterInterventionBatiment;
import vue.ajouter.FenAjouterReleveCompteur;
import vue.ajouter.FenUpdateFactureInterventionBatiment;
import vue.consulter_informations.FenBatimentInformations;
import vue.modifier.FenModifierBatiment;

public class GestionFenBatimentInformations implements ActionListener {

	private FenBatimentInformations fenetre;
	private Batiment selectBatiment;
	private DaoCompteur daoCompteur;
	private DaoBatiment daoBat;

	/**
	 * Initialise la classe de gestion de la fenêtre contenant les informations d'un
	 * batiment
	 * 
	 * @param fenetre        fenetre contenant les informations
	 * @param selectBatiment batiment auquel on souhaite consulter les infos
	 */
	public GestionFenBatimentInformations(FenBatimentInformations fenetre, Batiment selectBatiment) {
		this.fenetre = fenetre;
		this.selectBatiment = selectBatiment;
		daoCompteur = new DaoCompteur();
		daoBat = new DaoBatiment();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Modifier informations":
			this.traiterModifInfosBat();
			break;
		case "Consulter le bien":
			// Ouvre la fenêtre de consultation du bien sélectionné
			this.fenetre.getGestionTableBiens().consulterBien();
			break;
		case "Ajouter un bien":
			// Ouvre la fenêtre d'ajout de bien
			FenAjouterBienLouable fenABL = new FenAjouterBienLouable(this.fenetre.getSelectBatiment(), this.fenetre,
					null);
			fenABL.setVisible(true);
			break;
		case "Supprimer l'intervention":
			// Supprime l'intervention sélectionnée et actualise la table
			this.traiterSupprimerIntervention();
			break;
		case "Ajouter une intervention":
			// Ouvre la fenêtre d'ajout d'intervention
			FenAjouterInterventionBatiment fenAjouterBatiment = new FenAjouterInterventionBatiment(
					selectBatiment.getIdBat(), fenetre);
			fenAjouterBatiment.setVisible(true);
			break;
		case "Ajouter les infos. facture":
			// Ouvre la fenêtre d'ajout des informations de facturation si elles n'ont pas
			// été renseignées pour l'intervention sélectionnée
			if (!this.traiterAjoutInfosFacture()) {
				break;
			}
			fenetre.getGestionTableInterv().ecrireLigneTable();
			break;
		case "Payer l'intervention":
			// Enregistre un nouveau paiement correspondant au règlement de l'intervention
			// sélectionnée
			if (!this.traiterPayerInterv()) {
				break;
			}
			break;
		case "Ajouter une assurance":
			// Ouvre la fenêtre d'ajout d'assurance et met à jour la table
			FenAjouterAssurance fenAA = new FenAjouterAssurance(selectBatiment.getIdBat(), fenetre);
			fenAA.setVisible(true);
			break;
		case "Supprimer l'assurance":
			// Supprime l'assurance sélectionnée et met à jour la table
			this.traiterSupprimerAssurance();
			break;
		case "Ajouter un relevé":
			// Ouvre la fenêtre d'ajout de relevé de compteur et met à jour la table
			this.traiterAjoutReleve();
			break;
		case "Supprimer le relevé":
			// Supprime le relevé sélectionné et met à jour la table
			this.traiterSupprimerReleve();
			break;
		case "Ajouter un compteur":
			// Ouvre la fenêtre d'ajout de compteur et met à jour la table
			FenAjouterCompteur fenAC = new FenAjouterCompteur(selectBatiment.getIdBat(), fenetre, "batiment");
			fenAC.setVisible(true);
			break;
		case "Supprimer le compteur":
			// Supprime le compteur sélectionné et met à jour la table
			this.traiterSupprimerCompteur();
			break;
		case "Quitter":
			// Ferme la fenêtre
			this.fenetre.dispose();
			break;
		case "Supprimer le bâtiment":
			// Supprime le bâtiment concerné par la fenêtre
			this.traiterSupprimerBatiment();

			break;
		case "Calculer Taxe Foncière":
			if (!this.traiterTaxeFonciere()) {
				break;
			}
			break;
		case "Somme sur Année":
			JTextField fieldAnneeAssurance = new JTextField();

			Object[] messageAssu = { "Année :", fieldAnneeAssurance };

			int assuPane = JOptionPane.showConfirmDialog(this.fenetre, messageAssu,
					"Somme des Assurances sur une Année", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (assuPane != JOptionPane.OK_OPTION) {
				break;
			}

			// Vérifier que l'année est de 4 chiffres
			if (!fieldAnneeAssurance.getText().matches("\\d{4}")) {
				JOptionPane.showMessageDialog(null, "L'année est invalide !", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			JLabel lblSomme = new JLabel();
			try {
				lblSomme.setText(daoBat.totalAssuranceBatAnnee(selectBatiment.getIdBat(), fieldAnneeAssurance.getText())
						.toString());
			} catch (SQLException e1) {
				break;
			}

			Object[] messageAssurance = {
					"Somme des Montants d'Assurances sur l'Année " + fieldAnneeAssurance.getText() + " :", lblSomme };

			int assurancePane = JOptionPane.showConfirmDialog(this.fenetre, messageAssurance, "Somme des Assurances",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (assurancePane != JOptionPane.OK_OPTION) {
				break;
			}
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Met à jour l'attribut bat de l'instance de cette classe
	 * 
	 * @param bat nouveau batiment remplaceant l'ancien
	 */
	public void majBatSelect(Batiment bat) {
		this.selectBatiment = bat;
	}

	/**
	 * Traite l'appui sur le bouton pour supprimer une intervention
	 */
	private void traiterSupprimerIntervention() {
		JTable tableInterv = fenetre.getTableInterventions();
		int rowselected = tableInterv.getSelectedRow();

		if (rowselected != -1) {

			// Fenêtre de confirmation
			int confirm = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette intervention ?",
					Outils.CONFIRMATION_STRING, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {

				DaoIntervention daoInterv = new DaoIntervention();

				String idInterv = tableInterv.getValueAt(rowselected, 0).toString();

				Intervention interv = null;
				try {
					interv = daoInterv.findById(idInterv);
					daoInterv.delete(interv);
					GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
							"Supprimer une Intervention sur un Bâtiment", "- " + interv.getIdIntervention() + "["
									+ interv.getIntitule() + "] / Bat : " + interv.getIdBatiment()));
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				fenetre.getGestionTableInterv().ecrireLigneTable();
			}
		}
	}

	/**
	 * Traite l'ajout des infos de facturation d'une intervention (ouvre la fenetre
	 * permettant de le faire)
	 * 
	 * @return true si l'opération s'est bien passée, false sinon
	 */
	private boolean traiterAjoutInfosFacture() {
		int rowselected = fenetre.getTableInterventions().getSelectedRow();

		if (rowselected != -1) {

			String idInterv = fenetre.getTableInterventions().getValueAt(rowselected, 0).toString();

			DaoIntervention daoInterv = new DaoIntervention();
			Intervention i = null;
			try {
				i = daoInterv.findById(idInterv);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (i == null) {
				JOptionPane.showMessageDialog(null, "Impossible de récupérer les informations de l'intervention.",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (i.getMontantFacture() == null || i.getDateFacture() == null || i.getNumFacture() == null) {
				FenUpdateFactureInterventionBatiment fenUFI = new FenUpdateFactureInterventionBatiment(idInterv,
						fenetre);
				fenUFI.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null,
						"Les informations de facturation sont déjà renseignées pour cette intervention.",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			}
		}

		return true;
	}

	/**
	 * Traite l'appui sur le bouton pour payer une intervention et insère le
	 * paiement
	 * 
	 * @return true si l'opération s'est bien passée, false sinon
	 */
	private boolean traiterPayerInterv() {
		int rowselected = fenetre.getTableInterventions().getSelectedRow();

		if (rowselected != -1) {

			DaoPaiement daoP = new DaoPaiement();
			DaoIntervention daoI = new DaoIntervention();

			String idInterv = fenetre.getTableInterventions().getValueAt(rowselected, 0).toString();

			Paiement p = null;
			try {
				p = daoP.findByIdInterv(idInterv);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (p != null) {
				JOptionPane.showMessageDialog(null, "Cette intervention a déjà été payée.", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			Intervention interv = null;
			try {
				interv = daoI.findById(idInterv);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (interv == null) {
				JOptionPane.showMessageDialog(null, "Impossible de récupérer les informations de l'intervention.",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (interv.getMontantFacture() == null) {
				JOptionPane.showMessageDialog(null, "Veuillez d'abord renseigner les informations de facture.",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}

			Float montant = Float.parseFloat(fenetre.getTableInterventions().getValueAt(rowselected, 3).toString());
			Paiement pInterv = new Paiement(null, montant, "emis", "Intervention " + idInterv, LocalDate.now(), null,
					idInterv, null);

			try {
				daoP.create(pInterv);
				GestionTableHistoriqueFenPrinc
						.ajouterHistorique(new Historique(LocalDateTime.now(), "Ajout Paiement Intervention",
								"+ " + idInterv + " : " + montant + "€ / Bat : " + selectBatiment.getIdBat()));
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			JOptionPane.showMessageDialog(null, "L'intervention a été marquée comme payée.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}

		return true;

	}

	/**
	 * Traite la suppression d'une assurance
	 */
	private void traiterSupprimerAssurance() {
		JTable tableAss = fenetre.getTableAssurances();
		int rowselected = tableAss.getSelectedRow();

		if (rowselected != -1) {

			// Fenêtre de confirmation
			int confirm = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette assurance ?",
					Outils.CONFIRMATION_STRING, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {

				DaoAssurance daoAss = new DaoAssurance();

				String idAss = tableAss.getValueAt(rowselected, 0).toString();

				Assurance ass = null;
				try {
					ass = daoAss.findById(idAss);
					daoAss.delete(ass);
					GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
							"Supprimer l'Assurance d'un Bâtiment", "- " + ass.getNumPoliceAssurance() + " - "
									+ ass.getAnneeCouverture() + "/ Bat : " + selectBatiment.getIdBat()));
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				fenetre.getGestionTableAss().ecrireLigneTable();

			}
		}
	}

	/**
	 * Traite l'ajout d'un relevé de compteur
	 */
	private void traiterAjoutReleve() {
		Collection<Compteur> listeCompteurs;
		try {
			listeCompteurs = daoCompteur.findAllByIdBat(selectBatiment.getIdBat());
			if (listeCompteurs.isEmpty()) {
				JOptionPane.showConfirmDialog(null,
						"Vous ne pouvez pas ajouter un un relevé de compteur à un bâtiment qui n'a pas de compteur associé.",
						Outils.CONFIRMATION_STRING, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			} else {
				FenAjouterReleveCompteur fenARC = null;
				fenARC = new FenAjouterReleveCompteur(selectBatiment.getIdBat(), fenetre, "batiment");
				fenARC.setVisible(true);
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		fenetre.getGestionTableRelev().ecrireLigneTable();
	}

	/**
	 * Traite la suppression d'un relevé de compteur
	 */
	private void traiterSupprimerReleve() {
		JTable tableRelev = fenetre.getTableReleves();
		int rowselected = tableRelev.getSelectedRow();

		if (rowselected != -1) {

			// Fenêtre de confirmation
			int confirm = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce relevé ?",
					Outils.CONFIRMATION_STRING, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {

				DaoReleveCompteur daoRelev = new DaoReleveCompteur();

				String idCompteur = tableRelev.getValueAt(rowselected, 0).toString();
				String dateReleve = tableRelev.getValueAt(rowselected, 1).toString();

				ReleveCompteur relev = null;
				try {
					relev = daoRelev.findById(dateReleve, idCompteur);
					daoRelev.delete(relev);
					GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
							"Supprimer Relevé Compteur d'un Bâtiment", "- " + relev.getDateReleve() + " - "
									+ relev.getIdCompteur() + "/ Bat : " + selectBatiment.getIdBat()));
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				fenetre.getGestionTableRelev().ecrireLigneTable();

			}
		}
	}

	/**
	 * Traite la suppression d'un compteur
	 */
	private void traiterSupprimerCompteur() {
		JTable tableCompt = fenetre.getTableCompteurs();
		int rowselected = tableCompt.getSelectedRow();

		if (rowselected != -1) {

			// Fenêtre de confirmation
			int confirm = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce compteur ?",
					Outils.CONFIRMATION_STRING, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {
				String idCompt = tableCompt.getValueAt(rowselected, 0).toString();
				Compteur compt = null;
				try {
					compt = daoCompteur.findById(idCompt);
					daoCompteur.delete(compt);
					GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
							"Supprimer Compteur d'un Bâtiment", "- " + compt.getIdCompteur() + "["
									+ compt.getTypeCompteur() + "] / Bat : " + selectBatiment.getIdBat()));
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				fenetre.getGestionTableCompt().ecrireLigneTable();

			}
		}
	}

	/**
	 * Traite la suppression d'un batiment
	 */
	private void traiterSupprimerBatiment() {
		// Fenêtre de confirmation
		int confirmation = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce bâtiment ?",
				Outils.CONFIRMATION_STRING, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (confirmation == JOptionPane.YES_OPTION) {
			DaoBatiment daoB = new DaoBatiment();
			try {
				daoB.delete(selectBatiment);
				GestionTableHistoriqueFenPrinc.ajouterHistorique(
						new Historique(LocalDateTime.now(), "Supprimer Bâtiment", "- " + selectBatiment.getIdBat()));
				this.fenetre.dispose();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		this.fenetre.getFenBatAncetre().getGestionTable().appliquerFiltres();
	}

	/**
	 * Initialise les valeurs du total travaux du batiment sur l'année sélectionnée
	 * dans la combo box
	 */
	public void initialiserValeurs() {
		int anneeConstruction = selectBatiment.getDateConstruction().getYear();
		int anneeCourante = LocalDate.now().getYear();

		for (int i = anneeConstruction; i <= anneeCourante; i++) {
			fenetre.getComboBoxAnnee().addItem(i);
		}

		// Initialiser la table avec la première année sélectionnée
		if (fenetre.getComboBoxAnnee().getItemCount() > 0) {
			fenetre.getGestionTableTotalTravaux().ecrireLigneTable();
		}
	}

	public void traiterModifInfosBat() {
		// Ouvre la fenêtre de modification de bien
		FenModifierBatiment fenMB = new FenModifierBatiment(selectBatiment, fenetre);
		fenMB.setVisible(true);
	}

	public boolean traiterTaxeFonciere() {
		// SAISIE MONTANT ET ANNEE
		JTextField fieldMontantTaxe = new JTextField();
		JTextField fieldAnneeTaxe = new JTextField();

		Object[] messageTaxe = { "Montant :", fieldMontantTaxe, "Année :", fieldAnneeTaxe };

		int taxePane = JOptionPane.showConfirmDialog(this.fenetre, messageTaxe, "Calcul de la Taxe Foncière",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (taxePane != JOptionPane.OK_OPTION) {
			return false;
		}

		// Vérifier que tous les champs sont renseignés
		if (fieldMontantTaxe.getText().isEmpty() || fieldAnneeTaxe.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		// Vérifier que l'année est de 4 chiffres
		if (!fieldAnneeTaxe.getText().matches("\\d{4}")) {
			JOptionPane.showMessageDialog(null, "L'année est invalide !", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le montant est un nombre à virgule
		try {
			Float.parseFloat(fieldMontantTaxe.getText());
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "Le montant doit être un nombre à virgule (ex: 100.5).",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		JLabel lblTaxeF = new JLabel();
		lblTaxeF.setText(fieldMontantTaxe.getText());
		JLabel lblOrdures = new JLabel();
		try {
			lblOrdures.setText(daoBat.totalOrduresBatimentSurAnnee(selectBatiment.getIdBat(), fieldAnneeTaxe.getText())
					.toString());
		} catch (SQLException e1) {
			return false;
		}
		JLabel lblReste = new JLabel();
		lblReste.setText(String
				.valueOf(Float.parseFloat(lblOrdures.getText()) - (Float.parseFloat(fieldMontantTaxe.getText()))));

		Object[] messageCalculs = { "Montant Taxe Foncière :", lblTaxeF,
				"Total Ordures Bâtiment sur l'année " + fieldAnneeTaxe.getText() + " : ", lblOrdures, "Reste : ",
				lblReste };

		int calculsPane = JOptionPane.showConfirmDialog(this.fenetre, messageCalculs, "Résultats de la Taxe Foncière",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

		return (calculsPane == JOptionPane.OK_OPTION);
	}

}
