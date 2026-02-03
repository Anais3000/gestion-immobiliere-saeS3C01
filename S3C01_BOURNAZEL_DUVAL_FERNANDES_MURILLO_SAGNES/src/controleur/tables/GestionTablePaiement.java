package controleur.tables;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Intervention;
import modele.Locataire;
import modele.Paiement;
import modele.dao.DaoIntervention;
import modele.dao.DaoLocataire;
import modele.dao.DaoPaiement;
import vue.tables.FenPaiement;

public class GestionTablePaiement implements ListSelectionListener {

	private FenPaiement fen;

	private DaoPaiement daoPaiement;

	private DefaultTableModel modelTable;

	private DaoLocataire daoL;
	private DaoIntervention daoI;

	private Map<String, Intervention> mapIntervention = new HashMap<>();
	private Map<String, Locataire> mapLocataire = new HashMap<>();
	private Collection<Paiement> paiements;

	/**
	 * Initialise la classe permettant la gestion du tableau général des paiements
	 * 
	 * @param fen fenetre des paiements contenant le tableau
	 */
	public GestionTablePaiement(FenPaiement fen) {
		this.fen = fen;
		this.daoPaiement = new DaoPaiement();
		this.modelTable = (DefaultTableModel) fen.getTablePaiements().getModel();
		this.daoL = new DaoLocataire();
		this.daoI = new DaoIntervention();
		try {
			paiements = daoPaiement.findAll();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			for (Paiement p : paiements) {
				// initialiser les map
				if (!mapIntervention.containsKey(p.getIdIntervention())) {
					Intervention interv = daoI.findById(p.getIdIntervention());
					mapIntervention.put(p.getIdIntervention(), interv);
				}
				if (!mapLocataire.containsKey(p.getIdBien())) {
					Locataire loc = daoL.findByIdBienLouable(p.getIdBien());
					mapLocataire.put(p.getIdBien(), loc);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		fen.getBtnSupprimer().setEnabled(true);
	}

	/**
	 * Vide entièrement la table des paiements
	 *
	 * @param table table qu'on veut vider
	 */
	public static void viderTable(JTable table) {
		try {
			DefaultTableModel modeleTable = (DefaultTableModel) table.getModel();
			for (int i = 0; i < modeleTable.getRowCount(); i++) {
				for (int y = 0; y < modeleTable.getColumnCount(); y++) {
					modeleTable.setValueAt(null, i, y);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Permet d'appliquer les filtres saisis par l'utilisateur
	 */
	public void appliquerFiltres() {

		while (modelTable.getRowCount() > 0) {
			modelTable.removeRow(0);
		}

		double filtreMin = (double) fen.getSpinnerMontantMin().getValue();
		double filtreMax = (double) fen.getSpinnerMontantMax().getValue();
		if (filtreMax == 0.0) {
			filtreMax = Double.MAX_VALUE; // 0 signifie “aucun filtre”
		}
		String filtreNomLoc = fen.getTextFieldNomLoc().getText().trim().toLowerCase();

		LocalDate ldDebut = Outils.parseDateField(fen.getTextFieldDateDebut().getText());
		LocalDate ldFin = Outils.parseDateField(fen.getTextFieldDateFin().getText());

		try {
			paiements = daoPaiement.findAll();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		for (Paiement p : paiements) {
			LocalDate date = p.getDatePaiement();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String dateFormatee = date.format(formatter);
			Locataire loc = mapLocataire.get(p.getIdBien());
			Intervention interv = mapIntervention.get(p.getIdIntervention());

			// "Id Paiement","Date Paiement", "Locataire", "logement", "intervention",
			// "Libellé","Émis/Reçu","Montant"

			boolean cond1 = this.cond1(p, filtreMax, filtreMin, ldDebut, date, ldFin);

			boolean cond2 = this.cond2(interv, filtreNomLoc);

			boolean cond3 = loc != null;

			boolean cond5 = this.cond5(loc, interv, filtreNomLoc);

			if (cond1) {
				if (cond2) {
					modelTable.addRow(new Object[] { p.getIdPaiement(), dateFormatee, null, p.getIdBien(),
							interv.getIntitule(), p.getLibelle(), p.getSensPaiement(), p.getMontant() });
				} else if (cond3) {
					String nomPrenom = loc.getNom() + " " + loc.getPrenom();
					boolean cond4 = this.cond4(nomPrenom, filtreNomLoc);
					if (cond4) {
						modelTable.addRow(new Object[] { p.getIdPaiement(), dateFormatee, nomPrenom, p.getIdBien(),
								null, p.getLibelle(), p.getSensPaiement(), p.getMontant() });
					}
				} else if (cond5) {
					modelTable.addRow(new Object[] { p.getIdPaiement(), dateFormatee, null, null, null, p.getLibelle(),
							p.getSensPaiement(), p.getMontant() });
				}
			}
		}
	}

	private boolean cond1(Paiement p, double filtreMax, double filtreMin, LocalDate ldDebut, LocalDate date,
			LocalDate ldFin) {
		return p.getMontant() <= filtreMax && Math.abs(p.getMontant()) >= filtreMin
				&& (ldDebut == null || !date.isBefore(ldDebut)) && (ldFin == null || !date.isAfter(ldFin));
	}

	private boolean cond2(Intervention interv, String filtreNomLoc) {
		return (interv != null && filtreNomLoc.isEmpty());
	}

	private boolean cond4(String nomPrenom, String filtreNomLoc) {
		return (nomPrenom.trim().toLowerCase().contains(filtreNomLoc));
	}

	private boolean cond5(Locataire loc, Intervention interv, String filtreNomLoc) {
		return (loc == null && interv == null && filtreNomLoc.isEmpty());
	}

	/**
	 * Permet d'écouter les champs de filtres de façon permanente pour que la table
	 * se mette à jour dès qu'un filtre change
	 */
	public void activerFiltresAutomatiques() {

		DocumentListener dl = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				appliquerFiltres();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				appliquerFiltres();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				appliquerFiltres();
			}
		};
		fen.getTextFieldDateDebut().getDocument().addDocumentListener(dl);
		fen.getTextFieldDateFin().getDocument().addDocumentListener(dl);
		fen.getTextFieldNomLoc().getDocument().addDocumentListener(dl);
		fen.getSpinnerMontantMin().addChangeListener(e -> appliquerFiltres());
		fen.getSpinnerMontantMax().addChangeListener(e -> appliquerFiltres());
	}
}
