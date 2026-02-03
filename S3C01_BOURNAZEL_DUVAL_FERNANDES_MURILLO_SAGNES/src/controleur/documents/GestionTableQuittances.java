package controleur.documents;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controleur.Outils;
import modele.Locataire;
import modele.Paiement;
import modele.dao.DaoLocataire;
import modele.dao.DaoPaiement;
import vue.documents.FenQuittances;

public class GestionTableQuittances implements ListSelectionListener {
	private FenQuittances fQ;
	private DefaultTableModel modeleTableFenQuittances;
	private DaoPaiement daoPaiement;
	private DaoLocataire daoLoc;
	private Map<String, Locataire> bienLocataires = new HashMap<>();/*
																	 * on fait une map avec en clé l'id du bienlouable
																	 * et en valeur le locataire qui loue ce bien cela
																	 * évite de faire 100 fois findByIdBienLouable si il
																	 * y a 100 paiements, ça joue beaucoup sur
																	 * l'optimisation puisqu'on le calcule qu'une seule
																	 * fois du coup
																	 */

	/**
	 * Initialise la classe de gestion de la table de la fenetre des quittances
	 * 
	 * @param f fenetre contenant la liste des quittances
	 */
	public GestionTableQuittances(FenQuittances fQ) {
		this.fQ = fQ;
		this.modeleTableFenQuittances = fQ.getGestionTableModel();
		this.daoPaiement = new DaoPaiement();
		this.daoLoc = new DaoLocataire();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Ci-dessous les méthodes utilisées
	}

	/**
	 * Remplit les combo box de valeurs afin de pouvoir appliquer des filtres avec
	 */
	public void remplirComboBoxes() {
		List<Paiement> tableauPaiement;
		try {
			modeleTableFenQuittances.setRowCount(0);
			tableauPaiement = (List<Paiement>) daoPaiement.findAll();

			// Utilisation d'un comparator pour trier la liste, comme avec Mme Julien
			// on aurait pu le faire avec la requête MAIS le find all est utilisé par
			// d'autres classes qui necessitent un autre ordre.
			tableauPaiement.sort(Comparator
					.comparing(Paiement::getMoisConcerne, Comparator.nullsLast(Comparator.naturalOrder())).reversed());

			fQ.getComboBoxBienLouable().addItem("Tous");
			fQ.getComboBoxLocataire().addItem("Tous");
			for (Paiement p : tableauPaiement) {
				this.remplirNoms(p);
			}

			appliquerFiltres(tableauPaiement);
			activerFiltresAutomatiques(tableauPaiement);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Permet d'appliquer les filtres choisis par l'utilisater dans les combo box et
	 * dans les text fields
	 * 
	 * @param paiements la liste des paiements (paiements de loyer) sur lesquels
	 *                  sont appliqués les filtres
	 */
	public void appliquerFiltres(Collection<Paiement> paiements) {
		modeleTableFenQuittances.setRowCount(0);
		String filtreBienLouable = "";
		String filtreLocataire = "";

		if (fQ.getComboBoxBienLouable().getSelectedItem() != null) {
			filtreBienLouable = (String) fQ.getComboBoxBienLouable().getSelectedItem();
		}
		if (fQ.getComboBoxLocataire().getSelectedItem() != null) {
			filtreLocataire = (String) fQ.getComboBoxLocataire().getSelectedItem();
		}

		LocalDate ldDebut = Outils.parseDateField(fQ.getTextFieldDateDebut().getText());
		LocalDate ldFin = Outils.parseDateField(fQ.getTextFieldDateFin().getText());

		for (Paiement p : paiements) {
			LocalDate date = p.getMoisConcerne();
			String nomComplet = "";
			Locataire loc = bienLocataires.get(p.getIdBien());// on va chercher dans la map la valeur du locataire avec
																// la clé de l'id du bien du paiement
			String dateForm = null;

			if (loc != null) {
				nomComplet = loc.getNom() + " " + loc.getPrenom();
			}

			// ICI EXCEPTIONNELEMENT date peut être null, car fournit par la map et les
			// paiements n'ont pas tous un mois concerne (date)
			// Pareil pour les loc
			if (date == null || loc == null) {
				continue;
			}

			// Si date n'est pas null alors on convertit YYYY-MM-DD en DD-MM-YYYY pour que
			// ce soit plus compréhensible
			// et cohérent avec les filtres et le reste de l'application
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			dateForm = date.format(formatter);

			if (this.condFiltreRespectees(p, filtreBienLouable, nomComplet, filtreLocataire, ldDebut, date, ldFin)) {
				modeleTableFenQuittances
						.addRow(new Object[] { dateForm, p.getIdBien(), nomComplet, loc.getIdLocataire() });
			}

		}
	}

	/**
	 * Regarde si une ligne respecte les conditions du filtre pour l'ajouter ou non
	 * au tableau
	 */
	private boolean condFiltreRespectees(Paiement p, String filtreBienLouable, String nomComplet,
			String filtreLocataire, LocalDate ldDebut, LocalDate date, LocalDate ldFin) {
		boolean cond1 = p.getLibelle().toLowerCase().contains("loyer");
		boolean cond2 = (filtreBienLouable.equals(p.getIdBien()) || filtreBienLouable.equals("Tous"));
		boolean cond3 = (nomComplet.equals(filtreLocataire) || filtreLocataire.equals("Tous"));
		boolean cond4 = (ldDebut == null || (!date.isBefore(ldDebut) && ldDebut.toString().length() >= 10));
		boolean cond5 = (ldFin == null || (!date.isAfter(ldFin) && ldFin.toString().length() >= 10));
		return cond1 && cond2 && cond3 && cond4 && cond5;
	}

	/**
	 * Permet d'activer les filtres de façon permanente pour que dès qu'un filtre
	 * soit sélectionné, il soit appliqué
	 * 
	 * @param paiements la liste des paiements (loyers payés) sur lesquels sont
	 *                  appliqués les filtres
	 */
	public void activerFiltresAutomatiques(Collection<Paiement> paiements) {

		DocumentListener dl = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				appliquerFiltres(paiements);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				appliquerFiltres(paiements);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				appliquerFiltres(paiements);
			}
		};

		// Champs combos
		fQ.getComboBoxBienLouable().addActionListener(e -> appliquerFiltres(paiements));
		fQ.getComboBoxLocataire().addActionListener(e -> appliquerFiltres(paiements));

		// Champs de date : on écoute le texte, pas la valeur
		fQ.getTextFieldDateDebut().getDocument().addDocumentListener(dl);
		fQ.getTextFieldDateFin().getDocument().addDocumentListener(dl);

	}

	/**
	 * Remplit la combo box avec les noms complets et les id bien
	 * 
	 * @param p paiement de loyer concerné
	 * @throws SQLException
	 */
	private void remplirNoms(Paiement p) throws SQLException {
		Locataire loc = daoLoc.findByIdBienLouable(p.getIdBien());
		if (p.getLibelle().toLowerCase().contains("loyer") && loc != null) {
			String nomComplet = loc.getNom() + " " + loc.getPrenom();
			if (!Outils.comboContient(fQ.getComboBoxBienLouable(), p.getIdBien())) {
				fQ.getComboBoxBienLouable().addItem(p.getIdBien());
			}
			if (!Outils.comboContient(fQ.getComboBoxLocataire(), nomComplet)) {
				fQ.getComboBoxLocataire().addItem(nomComplet);
			}
			// remplissage de la map avec l'idBienLouable et le locataire si ils n'existent
			// déjà pas dans la map
			if (!bienLocataires.containsKey(p.getIdBien())) {
				bienLocataires.put(p.getIdBien(), loc);
			}
		}
	}
}
