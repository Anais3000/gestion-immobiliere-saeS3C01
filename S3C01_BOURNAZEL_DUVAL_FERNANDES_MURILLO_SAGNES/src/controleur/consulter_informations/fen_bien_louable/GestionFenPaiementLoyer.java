package controleur.consulter_informations.fen_bien_louable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.BienLouable;
import modele.Historique;
import modele.HistoriqueLoyer;
import modele.Louer;
import modele.Paiement;
import modele.dao.DaoHistoriqueLoyer;
import modele.dao.DaoPaiement;
import vue.ajouter.FenPaiementLoyer;

public class GestionFenPaiementLoyer implements ActionListener {

	FenPaiementLoyer fPL;
	private BienLouable bien;
	Louer loc;

	/**
	 * Initialise la classe de gestion de la fenêtre permettant de payer un loyer
	 * 
	 * @param fenPaiementLoyer fenetre permettant le paiement
	 * @param bien             bien sur lequel payer le loyer
	 * @param loc              objet représentant la location en cours pour laquelle
	 *                         payer le loyer
	 */
	public GestionFenPaiementLoyer(FenPaiementLoyer fenPaiementLoyer, BienLouable bien, Louer loc) {
		this.fPL = fenPaiementLoyer;
		this.bien = bien;
		this.loc = loc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		switch (itemSelectionne.getText()) {
		case "Valider":

			// Récupérer les valeurs
			Float montant = Float.parseFloat(fPL.getTextFieldMontant().getText());
			String libelle = "loyer";
			String dateString = fPL.getTextFieldDate().getText();
			String idBien = bien.getIdBien();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String idIntervention = null;

			String sens = "recus";

			// Récupérer le mois concerné depuis la combo box
			String moisAnneeSelectionne = (String) fPL.getComboBoxMois().getSelectedItem();

			String[] parts = moisAnneeSelectionne.split("/"); // pour faire un tableau de part et d'autre du /
			int mois = Integer.parseInt(parts[0]);
			int annee = Integer.parseInt(parts[1]);

			LocalDate moisConcerne = LocalDate.of(annee, mois, 1);

			// Date de Paiement
			try {
				LocalDate.parse(dateString, formatter);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null,
						"Format de date invalide. Veuillez entrer la date au format JJ-MM-AAAA (ex: 15-03-2020)",
						"Erreur de format", JOptionPane.ERROR_MESSAGE);
				break;
			}
			LocalDate date = LocalDate.parse(dateString, formatter);
			Paiement p = new Paiement(null, montant, sens, libelle, date, idBien, idIntervention, moisConcerne);

			DaoPaiement dao = new DaoPaiement();

			try {
				dao.create(p);
				GestionTableHistoriqueFenPrinc
						.ajouterHistorique(new Historique(LocalDateTime.now(), "Ajout Paiement Loyer",
								"+ " + idBien + " : " + montant + "€, Date concernée : " + moisConcerne));
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			JOptionPane.showMessageDialog(null, "Le mois de loyer a été marqué comme payé.", "Information",
					JOptionPane.INFORMATION_MESSAGE);

			this.fPL.dispose();

			break;
		case "Annuler":
			this.fPL.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", "Erreur", JOptionPane.ERROR_MESSAGE);
			break;
		}

	}

	/**
	 * Remplit la combo box de la liste des mois impayés
	 */
	public void remplirComboboxes() {
		List<String> listeMois = this.listeMoisImpayes();
		fPL.getComboBoxMois().setModel(new DefaultComboBoxModel<>(listeMois.toArray(new String[0])));
	}

	/**
	 * Permet d'obtenir les mois impayés sous forme de liste de String au format
	 * MOIS/ANNEE
	 * 
	 * @return la liste de string de mois de loyers impayés
	 */
	public List<String> listeMoisImpayes() {

		DaoPaiement daoP = new DaoPaiement();
		List<Paiement> listeP = null;
		try {
			listeP = (ArrayList<Paiement>) daoP.findAllByIdBien(this.bien.getIdBien());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return Collections.emptyList();
		}

		LocalDate dateDebut = this.loc.getDateDebut();
		LocalDate dateFin = LocalDate.now();

		// on se place au 1er du mois
		LocalDate dateCourante = dateDebut.withDayOfMonth(1);
		LocalDate fin = dateFin.withDayOfMonth(1);

		ArrayList<String> listeDates = new ArrayList<>();

		while (!dateCourante.isAfter(fin)) {

			int mois = dateCourante.getMonthValue();
			int annee = dateCourante.getYear();

			boolean moisDansPaiement = false;

			// Verif si le mois est deja payé pour ce bien
			for (Paiement p : listeP) {
				LocalDate moisPaiementLoyer = p.getMoisConcerne();
				if (moisPaiementLoyer != null && moisPaiementLoyer.getMonthValue() == mois
						&& moisPaiementLoyer.getYear() == annee && p.getLibelle().equals("loyer")) {
					moisDansPaiement = true;
				}

			}
			// Si pas mois dans paiement on le met dans la combo box
			if (!moisDansPaiement) {
				if (mois < 10) {
					listeDates.add("0" + mois + "/" + annee);
				} else {
					listeDates.add(mois + "/" + annee);
				}
			}
			dateCourante = dateCourante.plusMonths(1);
		}
		return listeDates;
	}

	/**
	 * Permet de rafraichir le montant du loyer impayé en fonction du mois
	 * sélectionné dans la combo box
	 */
	public void ajouterListenerComboBox() {
		fPL.getComboBoxMois().addActionListener(e -> {
			String moisAnneeSelectionne = (String) fPL.getComboBoxMois().getSelectedItem();
			if (moisAnneeSelectionne != null) {
				float montantCalcule = calculerMontantPourMois(moisAnneeSelectionne);
				fPL.getTextFieldMontant().setText(String.valueOf(montantCalcule));
			}
		});
	}

	/**
	 * Calcule le montant de loyer impayé pour le mois, en se basant sur les
	 * ajustements de loyer restant et l'historique des valeurs de loyer du bien
	 * 
	 * @param moisAnneeSelectionne mois de loyer impayé sélectionné
	 * @return la valeur totale due par le locataire, en float
	 */
	private float calculerMontantPourMois(String moisAnneeSelectionne) {

		DaoHistoriqueLoyer daoHistL = new DaoHistoriqueLoyer();

		// Parse le mois et l'année
		String[] parts = moisAnneeSelectionne.split("/");
		int mois = Integer.parseInt(parts[0]);
		int annee = Integer.parseInt(parts[1]);
		LocalDate moisConcerne = LocalDate.of(annee, mois, 1);

		HistoriqueLoyer loyProv = null;
		try {
			loyProv = daoHistL.findByIdBienPremierDuMois(this.bien.getIdBien(), moisConcerne.toString());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return 0.0f;
		}

		float loyerBase = 0f;
		if (loyProv != null) {
			loyerBase = loyProv.getLoyer() + loyProv.getProvision();
		} else {
			JOptionPane.showMessageDialog(fPL, "Impossible de récupérer le loyer pour ce mois. Montant par défaut = 0.",
					"Information", JOptionPane.INFORMATION_MESSAGE);
		}

		float ajustement = 0f;

		if (this.loc.getAjustementLoyer() != 0 && this.loc.getMoisFinAjustement() != null
				&& this.loc.getMoisDebutAjustement() != null) {
			LocalDate moisFinAjustement = this.loc.getMoisFinAjustement();
			LocalDate moisDebutAjustement = this.loc.getMoisDebutAjustement();

			// Vérifie si le mois concerné est dans la période d'ajustement
			if (!moisConcerne.isBefore(moisDebutAjustement) && !moisConcerne.isAfter(moisFinAjustement)) {
				ajustement = this.loc.getAjustementLoyer();
			}
		}

		return loyerBase + ajustement;
	}

	/**
	 * Ecrit le montant du loyer impayé dans le text field prévu à cet effet
	 */
	public void setTextMontant() {
		if (!(this.listeMoisImpayes().isEmpty())) {
			String moisAnneeSelectionne = this.listeMoisImpayes().get(0);
			fPL.getTextFieldMontant().setText(String.valueOf(this.calculerMontantPourMois(moisAnneeSelectionne)));
		}
	}

}
