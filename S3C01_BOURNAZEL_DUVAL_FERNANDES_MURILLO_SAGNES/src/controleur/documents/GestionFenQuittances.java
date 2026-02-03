package controleur.documents;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.BienLouable;
import modele.Historique;
import modele.HistoriqueLoyer;
import modele.Locataire;
import modele.Paiement;
import modele.dao.DaoBienLouable;
import modele.dao.DaoHistoriqueLoyer;
import modele.dao.DaoLocataire;
import modele.dao.DaoPaiement;
import rapport.CreerQuittance;
import vue.documents.FenQuittances;

public class GestionFenQuittances implements ActionListener {

	private FenQuittances fQ;

	/**
	 * Initialise la classe de gestion de la fenêtre des quittances de loyer à
	 * générer
	 * 
	 * @param fQ fenetre des quittances de loyer à generer
	 */
	public GestionFenQuittances(FenQuittances fQ) {
		this.fQ = fQ;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		switch (itemSelectionne.getText()) {
		case "Quitter":
			this.fQ.dispose();
			break;

		case "Générer quittance":
			this.traiterGenererQuittance();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Traite la génération d'une quittance
	 */
	private void traiterGenererQuittance() {
		int selectedRow = this.fQ.getTableQuittance().getSelectedRow();

		if (selectedRow == -1) {
			return;
		}

		// SAISIE BAILLEUR
		String[] infosBailleur = this.saisirInfosBailleur();
		String nomBailleur = infosBailleur[0];
		String adresseBailleur = infosBailleur[1];

		DaoPaiement daoP = new DaoPaiement();
		DaoBienLouable daoB = new DaoBienLouable();
		DaoHistoriqueLoyer daoH = new DaoHistoriqueLoyer();
		DaoLocataire daoL = new DaoLocataire();

		String idBien = this.fQ.getTableQuittance().getValueAt(selectedRow, 1).toString();
		String idLocataire = this.fQ.getTableQuittance().getValueAt(selectedRow, 3).toString();
		String moisConcerne = this.fQ.getTableQuittance().getValueAt(selectedRow, 0).toString();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate moisConcerneFormate = LocalDate.parse(moisConcerne, formatter);

		ArrayList<Paiement> paiementsBienLouableSelected = this.recupererPaiements(daoP, idBien, moisConcerneFormate);
		if (paiementsBienLouableSelected == null || paiementsBienLouableSelected.isEmpty()) {
			JOptionPane.showMessageDialog(this.fQ, "Aucun paiement trouvé pour ce bien.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		LocalDate dateSelectionne = LocalDate.parse(this.fQ.getTableQuittance().getValueAt(selectedRow, 0).toString(),
				formatter);

		Float[] infosPaiement = this.recupererInfosPaiement(paiementsBienLouableSelected, dateSelectionne, daoB, daoH,
				idBien);

		Float paiement = infosPaiement[0];
		Float loyer = infosPaiement[1];
		Float provision = infosPaiement[2];

		Locataire selectLocataire = this.recupererLocataire(daoL, idLocataire);
		if (selectLocataire == null) {
			return;
		}

		this.genererEtOuvrirQuittance(nomBailleur, adresseBailleur, selectLocataire, dateSelectionne, paiement, loyer,
				provision);
	}

	/**
	 * Permet la saisie des informations du bailleur
	 * 
	 * @return [nom, adresse] ou null si annulé
	 */
	private String[] saisirInfosBailleur() {
		JTextField fieldNomBailleur = new JTextField();
		JTextField fieldAdresseBailleur = new JTextField();

		Object[] messageBailleur = { "Nom / Prénom du bailleur :", fieldNomBailleur, "Adresse du bailleur :",
				fieldAdresseBailleur };

		int choixBailleur = JOptionPane.showConfirmDialog(this.fQ, messageBailleur, "Informations du bailleur",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (choixBailleur != JOptionPane.OK_OPTION) {
			return new String[0];
		}

		String nomBailleur = fieldNomBailleur.getText().trim();
		String adresseBailleur = fieldAdresseBailleur.getText().trim();

		if (nomBailleur.isEmpty() || adresseBailleur.isEmpty()) {
			JOptionPane.showMessageDialog(this.fQ, "Veuillez renseigner le nom et l'adresse du bailleur.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return new String[0];
		}

		return new String[] { nomBailleur, adresseBailleur };
	}

	/**
	 * Récupère les paiements pour un bien et une date donnée
	 * 
	 * @param daoP                objet d'accès à la table sae_paiements
	 * @param idBien              id du bien concerné
	 * @param moisConcerneFormate date donnée formattée en LocalDate
	 * @return la liste des paiements du locataire
	 */
	private ArrayList<Paiement> recupererPaiements(DaoPaiement daoP, String idBien, LocalDate moisConcerneFormate) {
		try {
			return (ArrayList<Paiement>) daoP.findAllPaiementLoyerByIdBien(idBien,
					Date.valueOf(moisConcerneFormate).toString(), Date.valueOf(moisConcerneFormate).toString());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return new ArrayList<>(Collections.emptyList());
		}
	}

	/**
	 * Récupère les informations de paiement du loyer payé
	 * 
	 * @param paiements       liste des paiements du locataire
	 * @param dateSelectionne date sélectionnée pour la quittance
	 * @param daoB            objet d'accès à la table sae_bien_louable
	 * @param daoH            objet d'accès à la table sae_historique_loyers
	 * @param idBien          id du bien concerné
	 * @return [paiement, loyer, provision] ou null
	 */
	private Float[] recupererInfosPaiement(ArrayList<Paiement> paiements, LocalDate dateSelectionne,
			DaoBienLouable daoB, DaoHistoriqueLoyer daoH, String idBien) {

		for (Paiement p : paiements) {
			if (p.getMoisConcerne().equals(dateSelectionne)) {

				BienLouable b = null;
				try {
					b = daoB.findById(idBien);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					return new Float[0];
				}

				if (b == null) {
					JOptionPane.showMessageDialog(null, "Impossible de récupérer le bien.", Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					return new Float[0];
				}

				HistoriqueLoyer histoLoyer = null;
				try {
					histoLoyer = daoH.findByIdBienPremierDuMois(b.getIdBien(),
							dateSelectionne.withDayOfMonth(1).toString());
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					return new Float[0];
				}

				if (histoLoyer == null) {
					JOptionPane.showMessageDialog(null, "Impossible de récupérer l'historique de loyers.",
							Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
					return new Float[0];
				}

				return new Float[] { p.getMontant(), histoLoyer.getLoyer(), histoLoyer.getProvision() };
			}
		}

		JOptionPane.showMessageDialog(null, "Erreur, impossible de trouver les informations de paiement.",
				Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
		return new Float[0];
	}

	/**
	 * Récupère un locataire en fonction de son id
	 * 
	 * @param daoL        objet d'accès à la table sae_locataire
	 * @param idLocataire id du locataire recherché
	 * @return l'objet Locataire correspondant
	 */
	private Locataire recupererLocataire(DaoLocataire daoL, String idLocataire) {
		try {
			Locataire locataire = daoL.findById(idLocataire);
			if (locataire == null) {
				JOptionPane.showMessageDialog(null, "Impossible de récupérer le locataire.", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
			return locataire;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	/**
	 * Génère et ouvre la quittance en fonction des informations souhaitées
	 * 
	 * @param nomBailleur     nom du bailleur
	 * @param adresseBailleur adresse du bailleur
	 * @param selectLocataire locataire concerné
	 * @param dateSelectionne date sélectionnée pour la quittance
	 * @param paiement        paiement de loyer concerné
	 * @param loyer           montant du loyer concerné
	 * @param provision       montant de la provision pour charges concernée
	 */
	private void genererEtOuvrirQuittance(String nomBailleur, String adresseBailleur, Locataire selectLocataire,
			LocalDate dateSelectionne, Float paiement, Float loyer, Float provision) {

		String chemin = System.getProperty("user.dir") + "/src/rapport/quittance_" + dateSelectionne + "_"
				+ selectLocataire.getNom() + "_" + selectLocataire.getPrenom() + ".docx";

		try {
			CreerQuittance.creerQuittance(nomBailleur, adresseBailleur,
					selectLocataire.getNom() + " " + selectLocataire.getPrenom(), selectLocataire.getAdresse(),
					selectLocataire.getCodePostal(), selectLocataire.getVille(), dateSelectionne, paiement, loyer,
					provision, chemin);

			GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(), "Génération Quittance",
					"+ " + dateSelectionne + " // Locataire : " + selectLocataire.getIdLocataire()));

			Desktop.getDesktop().open(new File(chemin));

		} catch (IOException | SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}
}