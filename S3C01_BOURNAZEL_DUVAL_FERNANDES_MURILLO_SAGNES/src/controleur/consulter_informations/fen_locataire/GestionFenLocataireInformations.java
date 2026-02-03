package controleur.consulter_informations.fen_locataire;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import controleur.tables.fen_bien_louable.GestionTableFenBienLouable;
import modele.BienLouable;
import modele.Historique;
import modele.HistoriqueLoyer;
import modele.Locataire;
import modele.Louer;
import modele.Paiement;
import modele.dao.DaoBienLouable;
import modele.dao.DaoHistoriqueLoyer;
import modele.dao.DaoLocataire;
import modele.dao.DaoLouer;
import modele.dao.DaoPaiement;
import rapport.CreerQuittance;
import vue.ajouter.FenPaiementLoyer;
import vue.consulter_informations.FenLocataireInformations;
import vue.modifier.FenModifierLocataire;

public class GestionFenLocataireInformations implements ActionListener {

	private FenLocataireInformations fenetre;
	private Locataire selectLocataire;
	private DaoBienLouable daoBienLouable;
	private DaoPaiement daoPaiement;
	private DaoLouer daoLouer;
	private DaoHistoriqueLoyer daoHistLoyer;

	/**
	 * Initialise la classe de gestion de la fenêtre permettant de consulter les
	 * informations d'un locataire
	 * 
	 * @param fenetre
	 * @param selectLocataire
	 */
	public GestionFenLocataireInformations(FenLocataireInformations fenetre, Locataire selectLocataire) {
		this.fenetre = fenetre;
		this.selectLocataire = selectLocataire;
		this.daoBienLouable = new DaoBienLouable();
		this.daoPaiement = new DaoPaiement();
		this.daoLouer = new DaoLouer();
		this.daoHistLoyer = new DaoHistoriqueLoyer();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (((JButton) e.getSource()).getText()) {
		case "Modifier informations":
			FenModifierLocataire fenML = new FenModifierLocataire(selectLocataire, fenetre);
			fenML.setVisible(true);
			break;
		case "Consulter bien":

			if (!this.traiterConsulterBien()) {
				break;
			}

			break;
		case "Générer quittance":

			if (!this.traiterGenererQuittance()) {
				break;
			}

			break;
		case "Paiement loyer":

			if (!this.traiterPaiementLoyer()) {
				break;
			}

			break;
		case "Supprimer le locataire":

			this.traiterSupprimerLocataire();

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
	 * Met à jour l'objet locataire utilisé dans cette classe
	 * 
	 * @param loc nouvel objet locataire à utiliser à la place de l'ancien
	 */
	public void majLocataireSelect(Locataire loc) {
		this.selectLocataire = loc;
	}

	/**
	 * Retourne un bien louable en fonction de la ligne sélectionnée dans le tableau
	 * des biens louables
	 * 
	 * @param numeroLigne numéro de ligne sélectionnée
	 * @return l'objet de type BienLouable correspondant
	 * @throws SQLException
	 */
	public BienLouable lireLigneTableBiens(int numeroLigne) throws SQLException {
		String idBien = (String) this.fenetre.getTableBiens().getValueAt(numeroLigne, 0);
		return this.daoBienLouable.findById(idBien);
	}

	/**
	 * Permet de consulter le bien sélectionné dans le tableau et d'ouvrir la
	 * fenetre d'informations du bien
	 * 
	 * @return true si l'opération s'est correctement déroulée, false sinon
	 */
	private boolean traiterConsulterBien() {

		JTable tableBiens = fenetre.getTableBiens();
		int rowselectedBien = tableBiens.getSelectedRow();

		if (rowselectedBien != -1) {
			String idBien = tableBiens.getValueAt(rowselectedBien, 0).toString();

			DaoBienLouable daoBien = new DaoBienLouable();

			BienLouable bien = null;
			try {
				bien = daoBien.findById(idBien);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (bien == null) {
				JOptionPane.showMessageDialog(null, "Impossible de récupérer les informations du bien.",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}

			try {
				GestionTableFenBienLouable.chargerFenetreBienLouable(bien, null);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		return true;

	}

	/**
	 * Traite l'ouverture de la fenêtre pour générer une quittance de loyer
	 * 
	 * @return true si l'opération s'est correctement déroulée, false sinon
	 */
	private boolean traiterGenererQuittance() {
		int selectedRow = this.fenetre.getTableBiens().getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this.fenetre, "Veuillez sélectionner un bien.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {

			BienLouable bienLouableSelected = this.lireLigneTableBiens(selectedRow);
			Louer l = this.daoLouer.findByIdBienEtIdLocataire(bienLouableSelected.getIdBien(),
					this.selectLocataire.getIdLocataire());
			LocalDate dateDebutBail = l.getDateDebut();
			LocalDate dateFinBail = l.getDateFin();

			Collection<Paiement> paiementsBienLouableSelected = this.daoPaiement.findAllPaiementLoyerByIdBien(
					bienLouableSelected.getIdBien(), Date.valueOf(dateDebutBail).toString(),
					Date.valueOf(dateFinBail).toString());
			if (paiementsBienLouableSelected == null || paiementsBienLouableSelected.isEmpty()) {
				JOptionPane.showMessageDialog(this.fenetre, "Aucun paiement trouvé pour ce bien.",
						Outils.INFORMATION_STRING, JOptionPane.INFORMATION_MESSAGE);
				return false;
			}

			// Remplissage Combo
			JComboBox<LocalDate> comboMois = new JComboBox<>();
			for (Paiement p : paiementsBienLouableSelected) {
				comboMois.addItem(p.getMoisConcerne());
			}

			Object[] message = { "Mois :", comboMois };

			int choix = JOptionPane.showConfirmDialog(this.fenetre, message, "Générer quittance",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (choix != JOptionPane.OK_OPTION) {
				return false;
			}

			// SAISIE BAILLEUR
			JTextField fieldNomBailleur = new JTextField();
			JTextField fieldAdresseBailleur = new JTextField();

			Object[] messageBailleur = { "Nom / Prénom du bailleur :", fieldNomBailleur, "Adresse du bailleur :",
					fieldAdresseBailleur };

			int choixBailleur = JOptionPane.showConfirmDialog(this.fenetre, messageBailleur, "Informations du bailleur",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (choixBailleur != JOptionPane.OK_OPTION) {
				return false;
			}

			String nomBailleur = fieldNomBailleur.getText().trim();
			String adresseBailleur = fieldAdresseBailleur.getText().trim();

			if (nomBailleur.isEmpty() || adresseBailleur.isEmpty()) {
				JOptionPane.showMessageDialog(this.fenetre, "Veuillez renseigner le nom et l'adresse du bailleur.",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}

			LocalDate dateSelectionne = (LocalDate) comboMois.getSelectedItem();
			Float paiement = null;
			Float loyer = null;
			Float provision = null;

			BienLouable b;
			for (Paiement p : paiementsBienLouableSelected) {
				if (p.getMoisConcerne().equals(dateSelectionne)) {

					b = daoBienLouable.findById(p.getIdBien());

					HistoriqueLoyer histoLoyer = this.daoHistLoyer.findByIdBienPremierDuMois(b.getIdBien(),
							dateSelectionne.withDayOfMonth(1).toString());

					paiement = p.getMontant();
					loyer = histoLoyer.getLoyer();
					provision = histoLoyer.getProvision();

					break;

				}
			}

			if (paiement == null || loyer == null || provision == null) {
				JOptionPane.showMessageDialog(null, "Erreur, impossible de trouver les informations de paiement.",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}

			String chemin = System.getProperty("user.dir") + "/src/rapport/quittance_" + dateSelectionne + "_"
					+ this.selectLocataire.getNom() + "_" + this.selectLocataire.getPrenom() + ".docx";

			CreerQuittance.creerQuittance(nomBailleur, adresseBailleur,
					this.selectLocataire.getNom() + " " + this.selectLocataire.getPrenom(),
					this.selectLocataire.getAdresse(), this.selectLocataire.getCodePostal(),
					this.selectLocataire.getVille(), dateSelectionne, paiement, loyer, provision, chemin);

			Desktop.getDesktop().open(new File(chemin));

		} catch (SQLException | IOException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Traite l'ouverture de la fenetre pour le paiement de loyer
	 * 
	 * @return true si l'opération s'est bien passée, false sinon
	 */
	private boolean traiterPaiementLoyer() {

		JTable tableBiens = fenetre.getTableBiens();
		int rowselectedBien = tableBiens.getSelectedRow();

		// Vérifier d'abord qu'une ligne est sélectionnée
		if (rowselectedBien == -1) {
			JOptionPane.showMessageDialog(null, "Veuillez sélectionner un bien dans le tableau.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		String dateFinString = tableBiens.getValueAt(rowselectedBien, 3).toString();
		LocalDate dateFin = LocalDate.parse(dateFinString);

		if (dateFin.isAfter(LocalDate.now())) {

			if (!this.traiterOuverturePaiementLoyer(tableBiens, rowselectedBien, dateFinString)) {
				return false;
			}

		} else {
			JOptionPane.showMessageDialog(null, "Le bail est révolu !", Outils.INFORMATION_STRING,
					JOptionPane.INFORMATION_MESSAGE);
		}
		return true;
	}

	/**
	 * Traite ouverture de la fenetre paiement loyer
	 * 
	 * @param tableBiens      table des biens
	 * @param rowselectedBien ligne sélectionnée dans la table des biens
	 * @param dateFinString   date de fin prévue de la location
	 * @return true si l'opération s'est bien passée, false sinon
	 */
	private boolean traiterOuverturePaiementLoyer(JTable tableBiens, int rowselectedBien, String dateFinString) {
		String idBien = tableBiens.getValueAt(rowselectedBien, 0).toString();

		DaoBienLouable daoBien = new DaoBienLouable();

		BienLouable bien = null;
		try {
			bien = daoBien.findById(idBien);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (bien == null) {
			JOptionPane.showMessageDialog(null, "Impossible de récupérer les informations du bien.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		Louer location = null;
		try {
			location = daoLouer.findByIdBienLouableEtDateSortie(bien.getIdBien(), dateFinString);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		FenPaiementLoyer fenPL = new FenPaiementLoyer(bien, location);

		if (fenPL.getGestionClic().listeMoisImpayes().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Tous les loyers de ce bail ont été réglés.", Outils.INFORMATION_STRING,
					JOptionPane.INFORMATION_MESSAGE);
			fenPL.dispose();
		} else {
			fenPL.setVisible(true);
		}

		return true;
	}

	/**
	 * Traite la suppression d'un locataire
	 */
	private void traiterSupprimerLocataire() {
		// Fenêtre de confirmation
		int confirm = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce locataire ?",
				"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (confirm == JOptionPane.YES_OPTION) {
			DaoLocataire daoL = new DaoLocataire();
			try {
				daoL.delete(fenetre.getSelectLocataire());
				fenetre.getFenLocAncetre().getGestionTable().ecrireLigneTableLocataire();
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
						"Supprimer Locataire", "- " + this.fenetre.getSelectLocataire().getNom() + " "
								+ this.fenetre.getSelectLocataire().getPrenom()));
				fenetre.dispose();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
