package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import controleur.fen_principal.GestionTableSituationMoisFenPrincipal;
import modele.dao.DaoBienLouable;
import modele.dao.DaoHistoriqueLoyer;
import modele.dao.DaoLouer;
import modele.dao.UtOracleDataSource;
import vue.Connexion;
import vue.FenetrePrincipale;

public class GestionConnexion implements ActionListener {

	private Connexion fenetre;
	private DaoBienLouable daoBL;
	private DaoLouer daoLouer;
	private DaoHistoriqueLoyer daoHL;
	private FenetrePrincipale fenetrePrincipale;

	/**
	 * Initialise la classe de gestion de la fenêtre de connexion
	 * 
	 * @param fenetre fenetre de connexion
	 */
	public GestionConnexion(Connexion fenetre) {
		this.fenetre = fenetre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FenetrePrincipale fenetreP = (FenetrePrincipale) fenetre.getTopLevelAncestor();
		this.fenetrePrincipale = fenetreP;
		switch (((JButton) e.getSource()).getText()) {
		case "Se connecter":
			String login = fenetre.getValeurChLogin();
			String password = fenetre.getValeurPasswordField();
			if (login.equals("Millan") && password.equals("$iutinfo")) {
				try {
					UtOracleDataSource.creerAcces("mrn5206a", "sae2526D4");
					// Rend le contenu visible et met à jour le label utilisateur
					this.daoBL = new DaoBienLouable();
					this.daoLouer = new DaoLouer();
					this.daoHL = new DaoHistoriqueLoyer();
					fenetreP.setConnecte(true);
					fenetreP.activerItems(true);
					fenetreP.afficherContenu(true);
					fenetreP.setUtilisateurConnecte(this.fenetre.getTextFieldLogin());
					fenetreP.setTitre(this.fenetre.getTextFieldLogin());
					this.renouvelerBauxAuto();
					this.actualiserPageAcceuil();
					fenetre.dispose();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null,
							"Le login et le mot de passe sont corrects mais la connexion au serveur a échoué.",
							"Erreur de connexion", JOptionPane.ERROR_MESSAGE);
					fenetreP.getMntmConnecter().setEnabled(true);
					fenetre.dispose();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Login ou mot de passe incorrect", "Erreur de connexion",
						JOptionPane.ERROR_MESSAGE);
				fenetreP.getMntmConnecter().setEnabled(true);
			}

			break;
		case "Annuler":
			fenetreP.setConnecte(false);
			fenetreP.activerItems(false);
			this.fenetre.dispose();
			break;

		default:
			// Cas non prévu (sécurité)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Renouvelle les baux révolus automatiquement à la connexion, car un bail se
	 * renouvèle si ni le locataire ni le propriétaire n'a demandé d'y mettre fin.
	 */
	public void renouvelerBauxAuto() {
		DaoLouer daoL = new DaoLouer();
		try {
			daoL.renouvelerBauxAutomatique();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Actualise les informations de la page d'accueil, comme par exemple le nombre
	 * de biens loués sur le nombre de biens total, etc.
	 */
	public void actualiserPageAcceuil() {
		// Setteur tableau de bord Page Principal
		try {
			fenetrePrincipale.setTotalLoyersMois(Math.round(this.daoHL.findTotalLoyerMoisActuel()));
			fenetrePrincipale.setMontantEncaisse(Math.round(this.daoHL.findTotalLoyerPayesMoisActuel()));
			fenetrePrincipale.setLoyersImpayes(Math.round(this.daoHL.findTotalLoyerImpayesMoisActuel()));
			fenetrePrincipale.setBiensLouesTotal(this.daoLouer.findAllActuel().size(), this.daoBL.findAll().size());
			GestionTableHistoriqueFenPrinc.ecrireLigneTableHistorique();
			GestionTableSituationMoisFenPrincipal.ecrireLigneTableSituationDuMois();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

}