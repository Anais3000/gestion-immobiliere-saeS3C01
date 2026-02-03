package controleur.ajouter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.BienLouable;
import modele.Historique;
import modele.Intervention;
import modele.Necessiter;
import modele.dao.DaoBienLouable;
import modele.dao.DaoIntervention;
import modele.dao.DaoNecessiterIntervention;
import vue.ajouter.FenAjouterInterventionBienLouable;

public class GestionFenAjouterInterventionBienLouable implements ActionListener {

	private FenAjouterInterventionBienLouable fAIB;
	private String idBien;
	private DaoIntervention dao;
	private DaoNecessiterIntervention daoNec;

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter une intervention
	 * sur un bien louable
	 * 
	 * @param fAIB   fenetre permettant l'ajout
	 * @param idBien id du bien sur lequel ajouter l'intervention
	 */
	public GestionFenAjouterInterventionBienLouable(FenAjouterInterventionBienLouable fAIB, String idBien) {
		this.fAIB = fAIB;
		this.idBien = idBien;
		this.dao = new DaoIntervention();
		this.daoNec = new DaoNecessiterIntervention();
		try {
			fAIB.afficherIntervs(this.getIdIntervs());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			try {
				// Si il n'y a rien dans la ComboBox alors on affiche un pop-up
				if (fAIB.getComboBoxInterv().getSelectedItem() == null) {
					JOptionPane.showMessageDialog(fAIB,
							"Aucune intervention en cours sur le batiment (avec date facture pas renseignée)",
							Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
					break;
				}

				String idInterv = fAIB.getComboBoxInterv().getSelectedItem().toString().trim();

				Necessiter necessiter = new Necessiter(idBien, idInterv);

				daoNec.create(necessiter);

				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
						"Ajout Intervention Bien Louable", "+ " + necessiter.getIdIntervention() + " // " + idBien));

				// Mise à jour de la fenetre en arrière plan des infos du BIEN
				fAIB.getFenAncestor().getGestionTableInterv().ecrireLigneTable(idBien);

				JOptionPane.showMessageDialog(null, "Intervention ajoutée avec succès !", "Confirmation",
						JOptionPane.INFORMATION_MESSAGE);

				this.fAIB.dispose();

			} catch (SQLException e1) {
				if (e1.getErrorCode() == 1) {
					JOptionPane.showMessageDialog(fAIB, "Erreur : l'intervention a déjà été ajoutée au bien !",
							Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
			break;
		case "Annuler":
			this.fAIB.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Fonction retournant une liste contenant les ID des interventions sur le
	 * batiment du bien en question
	 * 
	 * @return une liste de string contenant des ID d'interventions
	 * 
	 * @throws SQLException
	 */
	public List<String> getIdIntervs() throws SQLException {

		ArrayList<String> ids = new ArrayList<>();
		ArrayList<Intervention> intervs;

		DaoBienLouable daoBien = new DaoBienLouable();
		BienLouable bien = daoBien.findById(idBien);
		intervs = (ArrayList<Intervention>) dao.findAllByIdBat(bien.getIdBat());

		for (Intervention i : intervs) {
			ids.add(i.getIdIntervention());
		}
		return ids;
	}

}
