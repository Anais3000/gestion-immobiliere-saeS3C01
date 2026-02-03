package controleur.ajouter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Historique;
import modele.dao.DaoIRL;
import vue.ajouter.FenAjouterIRL;
import vue.documents.FenValeursIRL;
import vue.tables.FenBienLouable;

public class GestionFenAjouterIRL implements ActionListener {

	private FenAjouterIRL fAI;
	private FenBienLouable fenBL;
	private FenValeursIRL fenIRL;
	private DaoIRL daoIRL;

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter une valeur d'IRL
	 * 
	 * @param fAI    fenetre permettant l'ajout
	 * @param fenBL  fenetre bien louable depuis laquelle on a voulu ajouter l'IRL
	 * @param fenIRL fenetre des valeurs d'IRL depuis laquelle on a voulu ajouter
	 *               l'IRL
	 * 
	 *               Note : si l'une (ou les deux) fenetres fenBL ou fenIRL sont
	 *               null, elles ne seront pas rafraichies
	 */
	public GestionFenAjouterIRL(FenAjouterIRL fAI, FenBienLouable fenBL, FenValeursIRL fenIRL) {
		this.fAI = fAI;
		this.fenBL = fenBL;
		this.fenIRL = fenIRL;
		this.daoIRL = new DaoIRL();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			// Récupérer les valeurs dans les champs dont on a besoin pour les vérifications
			String annee = fAI.getTextFieldAnneeIRL().getText();
			String valeur = fAI.getTextFieldValeurIRL().getText();

			if (!this.verifierChamps()) {
				break;
			}

			String trimestre = this.fAI.getComboBoxTrimestreIRL().getSelectedItem().toString();

			int confirm = JOptionPane.showConfirmDialog(null, "Confirmez-vous l'ajout de cette valeur d'IRL ?\n Année "
					+ annee + ", " + trimestre + " trimestre, Valeur : " + valeur, "Confirmation",
					JOptionPane.YES_NO_OPTION);

			if (confirm == 0 && this.traiterAjoutIRL()) {
				this.traiterMAJArrierePlan();
			}

			break;
		case "Annuler":
			this.fAI.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Vérifie la validité des champs saisis dans la fenêtre, comme l'année par
	 * exemple
	 * 
	 * @return true si les champs sont valides, false sinon
	 */
	private boolean verifierChamps() {
		String annee = fAI.getTextFieldAnneeIRL().getText();
		String valeur = fAI.getTextFieldValeurIRL().getText();

		// Tous les champs doivent être remplis
		if (annee.isEmpty() || valeur.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Tous les champs doivent être renseignés !", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que l'année contient bien 4 caractères qui sont des chiffres
		if (!annee.matches("\\d+") || annee.length() != 4) {
			JOptionPane.showMessageDialog(null, "L'année est invalide, elle doit être composé de 4 chiffres !",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que la valeur est bien un nombre à virgules (float)
		try {
			Float.parseFloat(valeur);
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null,
					"La valeur est au mauvais format ! Elle doit être un nombre à virgule (ex: 100.5).",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Traite l'ajout de la nouvelle valeur d'IRL
	 * 
	 * @return true si l'IRL est bien ajouté, false sinon
	 */
	private boolean traiterAjoutIRL() {
		// Vérifier que l'IRL à ajouter n'est pas déjà dans la base de données
		try {
			daoIRL.create(fAI.getNouvelIRL());
			GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(), "Ajout IRL",
					"+ " + fAI.getNouvelIRL().getAnnee() + " - Trimestre : " + fAI.getNouvelIRL().getTrimestre()
							+ ", Valeur : " + fAI.getNouvelIRL().getValeur()));
		} catch (SQLException e1) {
			if (e1.getErrorCode() == 1) {
				JOptionPane.showMessageDialog(null, "L'IRL de cette période a déjà été renseigné.",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}
		JOptionPane.showMessageDialog(null, "L'IRL de ce trimestre a bien été ajouté.", "Information",
				JOptionPane.INFORMATION_MESSAGE);
		this.fAI.dispose();

		return true;
	}

	/**
	 * Met a jour les fenetres en arriere plan
	 */
	private void traiterMAJArrierePlan() {
		if (fenBL != null) {
			this.fenBL.getGestionClic().afficherIRL();
		}
		if (fenIRL != null) {
			this.fenIRL.getGestionClic().ecrireLignesTable();
		}
	}

}
