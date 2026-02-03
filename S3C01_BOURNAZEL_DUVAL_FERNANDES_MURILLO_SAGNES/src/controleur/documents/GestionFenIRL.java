package controleur.documents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Historique;
import modele.IRL;
import modele.dao.DaoIRL;
import vue.ajouter.FenAjouterIRL;
import vue.documents.FenValeursIRL;

public class GestionFenIRL implements ActionListener {

	private FenValeursIRL fIRL;
	private DaoIRL daoIRL;

	/**
	 * Initialise la classe de gestion de la fenêtre des valeurs d'IRL enregistrées
	 * 
	 * @param fIRL fenetre des valeurs d'IRL enregistrées
	 */
	public GestionFenIRL(FenValeursIRL fIRL) {
		this.fIRL = fIRL;
		this.daoIRL = new DaoIRL();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();

		switch (itemSelectionne.getText()) {

		case "Ajouter":
			FenAjouterIRL fAIRL = new FenAjouterIRL(null, this.fIRL);
			fAIRL.setVisible(true);
			break;

		case "Supprimer":

			if (!this.traiterSupprimerIRL()) {
				break;
			}

			break;

		case "Quitter":
			this.fIRL.dispose();
			break;

		default:
			JOptionPane.showMessageDialog(null, "Erreur inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);

		}

	}

	/*
	 * Met à 0 la table contenant les valeurs d'IRL et la remplit
	 */
	public void ecrireLignesTable() {
		DefaultTableModel modele = ((DefaultTableModel) this.fIRL.getTable().getModel());
		modele.setRowCount(0);

		ArrayList<IRL> listeIRL = null;
		try {
			listeIRL = (ArrayList<IRL>) this.daoIRL.findAll();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		for (IRL i : listeIRL) {
			modele.addRow(new Object[] { i.getAnnee(), i.getTrimestre(), i.getValeur() });
		}
	}

	/**
	 * Traite la suppression d'un IRL et demande le rafraichissement du tableau
	 * 
	 * @return true si la suppression est effective, false sinon
	 */
	private boolean traiterSupprimerIRL() {
		int selectedRow = this.fIRL.getTable().getSelectedRow();
		if (selectedRow == -1) {
			return false;
		}

		String annee = this.fIRL.getTable().getValueAt(selectedRow, 0).toString();
		String trimestre = this.fIRL.getTable().getValueAt(selectedRow, 1).toString();

		int confirm = JOptionPane.showConfirmDialog(null,
				"Voulez-vous supprimer la valeur d'IRL du " + trimestre + "e trimestre de " + annee + "?",
				"Confirmation", JOptionPane.YES_NO_OPTION);

		if (confirm != JOptionPane.OK_OPTION) {
			return false;
		}

		IRL irl = null;
		try {
			irl = daoIRL.findById(annee, trimestre);
			daoIRL.delete(irl);
			GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(), "Suppression IRL",
					"+ " + annee + " - Trimestre : " + trimestre + ", Valeur : " + irl.getValeur()));
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		this.ecrireLignesTable();
		return true;
	}

}
