package controleur.fen_principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import modele.dao.UtOracleDataSource;
import vue.Connexion;
import vue.FenetrePrincipale;
import vue.documents.FenContratsAssurance;
import vue.documents.FenContratsDeLocation;
import vue.documents.FenDiagnostics;
import vue.documents.FenEtatsDesLieux;
import vue.documents.FenFacturesEtDevis;
import vue.documents.FenImportCSV;
import vue.documents.FenQuittances;
import vue.documents.FenValeursIRL;
import vue.tables.FenBatiment;
import vue.tables.FenBienLouable;
import vue.tables.FenGarant;
import vue.tables.FenLocataire;
import vue.tables.FenOrganisme;
import vue.tables.FenPaiement;

public class GestionFenetrePrincipale implements ActionListener {

	private FenetrePrincipale fenetre;
	private String utilisateur;

	/**
	 * Initialise la classe de gestion de la fenêtre principale
	 * 
	 * @param fenetre fenetre principale de l'application
	 */
	public GestionFenetrePrincipale(FenetrePrincipale fenetre) {
		this.fenetre = fenetre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JMenuItem) e.getSource()).getText()) {
		case "Connecter":
			Connexion c = new Connexion();
			this.fenetre.getLayeredPane().add(c);
			c.show();
			this.fenetre.getMntmConnecter().setEnabled(false);
			this.fenetre.setConnecte(true);
			break;

		case "Déconnecter":
			this.fenetre.setConnecte(false);
			this.fenetre.activerItems(false);
			this.fenetre.getMntmConnecter().setEnabled(true);
			this.utilisateur = "";
			// Masque le contenu
			this.fenetre.afficherContenu(false);
			this.fenetre.setUtilisateurConnecte("");
			// Déconnection de la BD
			UtOracleDataSource.deconnecter();
			JOptionPane.showMessageDialog(null, "Vous avez bien été déconnecté.");
			break;

		case "Diagnostics":
			FenDiagnostics fDiag = new FenDiagnostics();
			fDiag.getGestionTable().remplirComboboxes();
			this.fenetre.getLayeredPane().add(fDiag);
			fDiag.show();
			break;
		case "États des lieux":
			FenEtatsDesLieux fEDL = new FenEtatsDesLieux();
			fEDL.getGestionTable().remplirComboBoxes();
			this.fenetre.getLayeredPane().add(fEDL);
			fEDL.show();
			break;
		case "Contrats d'assurance":
			FenContratsAssurance fCA = new FenContratsAssurance();
			fCA.getGestionTable().remplirComboBoxes();
			this.fenetre.getLayeredPane().add(fCA);
			fCA.show();
			break;
		case "Générer une quittance de loyer":
			FenQuittances fQ = new FenQuittances();
			fQ.getGestionTable().remplirComboBoxes();
			this.fenetre.getLayeredPane().add(fQ);
			fQ.show();
			break;
		case "Contrats de location":
			FenContratsDeLocation fCDL = new FenContratsDeLocation();
			fCDL.getGestionTable().remplirComboBoxes();
			this.fenetre.getLayeredPane().add(fCDL);
			fCDL.show();
			break;
		case "Factures et devis":
			FenFacturesEtDevis fFED = new FenFacturesEtDevis();
			fFED.getGestionTable().remplirComboBoxes();
			this.fenetre.getLayeredPane().add(fFED);
			fFED.show();
			break;
		case "Consulter les biens louables":
			FenBienLouable fBL = new FenBienLouable();
			this.fenetre.getLayeredPane().add(fBL);
			fBL.show();
			break;
		case "Consulter les bâtiments":
			FenBatiment fB = new FenBatiment();
			fB.getGestionTable().appliquerFiltres();
			this.fenetre.getLayeredPane().add(fB);
			fB.show();
			break;
		case "Consulter les locataires":
			FenLocataire fL = new FenLocataire();
			this.fenetre.getLayeredPane().add(fL);
			fL.show();
			break;
		case "Consulter les paiements":
			FenPaiement fP = new FenPaiement();
			fP.getGestionTable().appliquerFiltres();
			this.fenetre.getLayeredPane().add(fP);
			fP.show();
			break;
		case "Consulter les organismes":
			FenOrganisme fO = new FenOrganisme();
			fO.getGestionTable().ecrireLigneTableOrganisme();
			this.fenetre.getLayeredPane().add(fO);
			fO.show();
			break;
		case "Importer des loyers à partir d'un fichier CSV":
			FenImportCSV fIC = new FenImportCSV();
			this.fenetre.getLayeredPane().add(fIC);
			fIC.show();
			break;
		case "Consulter les garants":
			FenGarant fG = new FenGarant();
			this.fenetre.getLayeredPane().add(fG);
			fG.show();
			break;
		case "Valeurs de l'IRL enregistrées":
			FenValeursIRL fIRL = new FenValeursIRL();
			this.fenetre.getLayeredPane().add(fIRL);
			fIRL.show();
			break;
		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Permet d'obtenir l'utilisateur connecté
	 * 
	 * @return le login de l'utilisateur connecté, en String
	 */
	public String getUtilisateur() {
		return utilisateur;
	}
}
