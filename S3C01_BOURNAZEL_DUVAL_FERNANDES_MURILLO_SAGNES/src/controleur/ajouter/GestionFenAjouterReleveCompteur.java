package controleur.ajouter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Compteur;
import modele.Historique;
import modele.ReleveCompteur;
import modele.dao.DaoCompteur;
import modele.dao.DaoReleveCompteur;
import vue.ajouter.FenAjouterReleveCompteur;
import vue.consulter_informations.FenBatimentInformations;
import vue.consulter_informations.FenBienLouableInformations;

public class GestionFenAjouterReleveCompteur implements ActionListener {

	private FenAjouterReleveCompteur fARC;
	private String id;
	private String batimentOuBien;
	private static final String BATIMENT = "batiment";
	private static final String BIEN = "bien";

	/**
	 * Initialise la classe de gestion de la fenêtre pour ajouter un relevé de
	 * compteur
	 * 
	 * @param fARC fenetre permettant l'ajout
	 * @param id   id du compteur à relever
	 * @throws SQLException
	 */
	public GestionFenAjouterReleveCompteur(FenAjouterReleveCompteur fARC, String id) throws SQLException {
		this.fARC = fARC;
		this.id = id;
		batimentOuBien = fARC.getBatimentOuBien();
		charger();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Valider":

			// Récupérer les valeurs des champs dont on a besoin
			String date = fARC.getTextFieldDateReleve().getText();
			String index = fARC.getTextFieldIndex().getText();
			String prixUnite = fARC.getTextFieldPrixUnite().getText();
			String partieFixe = fARC.getTextFieldPartieFixe().getText();

			String idCompteur = fARC.getComboBoxCompteur().getSelectedItem().toString();

			DaoCompteur daoCpt = new DaoCompteur();

			// Initialisation de toutes les variables que l'on va utiliser
			Compteur cpt = null;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

			// Vérifie les champs date, index, prixUnite, partieFixe
			if (!this.verifierChampsUn(date, index, prixUnite, partieFixe)) {
				break;
			}

			LocalDate dateReleve = LocalDate.parse(date, formatter);

			try {
				cpt = daoCpt.findById(idCompteur);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				break;
			}

			// Vérifier que le compteur n'est pas null
			if (cpt == null) {
				JOptionPane.showMessageDialog(null, "Impossible de récupérer les informations du compteur.",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				break;
			}

			// Vérifie l'index en fonction du compteur (du précédent relevé)
			if (!this.verifierChampsDeux(index, cpt)) {
				break;
			}

			ReleveCompteur dernierReleve = this.getDernierReleveCompteur();

			double ancienIndex;
			if (dernierReleve == null || fARC.getRdbtnOui().isSelected()) {
				ancienIndex = cpt.getIndexDepart();
			} else {
				ancienIndex = dernierReleve.getIndexCompteur();
			}

			Float prixUniteF = Float.parseFloat(prixUnite);
			Float partieFixeF = Float.parseFloat(partieFixe);
			Float indexF = Float.parseFloat(index);

			// Inserer le relevé en BD
			if (!this.traiterInsererReleve(dateReleve, indexF, prixUniteF, partieFixeF, idCompteur, ancienIndex)) {
				break;
			}

			// Mise a jour des fenetres en arriere plan
			this.traiterMAJArrierePlan();

			JOptionPane.showMessageDialog(null, "Relevé avec succès !", "Confirmation",
					JOptionPane.INFORMATION_MESSAGE);

			this.fARC.dispose();
			break;
		case "Annuler":
			this.fARC.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Remplit la combobox compteur et initialise le texte du TextField Index
	 * 
	 * @throws SQLException
	 */
	private void charger() throws SQLException {
		DaoCompteur dao = new DaoCompteur();
		List<Compteur> liste;
		if (batimentOuBien.equals(BATIMENT)) {
			liste = (List<Compteur>) dao.findAllByIdBat(id); // IDBAT
		} else if (batimentOuBien.equals(BIEN)) {
			liste = (List<Compteur>) dao.findAllByIdBien(id);
		} else {
			throw new IllegalArgumentException("La valeur de batiment ou bien n'est ni batiment ni bien.");
		}

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		for (Compteur c : liste) {
			model.addElement(c.getIdCompteur());
		}
		fARC.getComboBoxCompteur().setModel(model);

		ReleveCompteur dernierReleve = this.getDernierReleveCompteur();
		if (dernierReleve == null) {
			this.fARC.getTextFieldIndex().setText("0");
		} else {
			this.fARC.getTextFieldIndex().setText(String.valueOf(dernierReleve.getIndexCompteur()));
		}
	}

	/**
	 * Renvoie l'ancien relevé du même compteur
	 * 
	 * @return un objet ReleveCompteur contenant les informations du dernier relevé
	 *         de ce compteur
	 */
	private ReleveCompteur getDernierReleveCompteur() {
		DaoReleveCompteur dao = new DaoReleveCompteur();
		ReleveCompteur rc = null;
		try {
			rc = dao.findLastByIdCompt(fARC.getComboBoxCompteur().getSelectedItem().toString());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return rc;
	}

	/**
	 * Je fais une lambda expression pour eviter de refaire une classe entiere juste
	 * pour ce listener
	 */
	public void ajoutListenerComboBoxCompteur() {
		this.fARC.getComboBoxCompteur().addActionListener(e -> {

			String idCompteur = fARC.getComboBoxCompteur().getSelectedItem().toString();
			DaoCompteur daoCpt = new DaoCompteur();

			// Maj du champ index en fonction de l'index du dernier relevé
			if (idCompteur != null) {
				ReleveCompteur dernierReleve = this.getDernierReleveCompteur();

				if (dernierReleve != null) {
					fARC.getTextFieldIndex().setText(String.valueOf(dernierReleve.getIndexCompteur()));
				} else {
					Compteur cpt = null;
					try {
						cpt = daoCpt.findById(idCompteur);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(),
								Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
						return;
					}
					fARC.getTextFieldIndex().setText(String.valueOf(cpt.getIndexDepart()));
				}
			}
		});
	}

	/**
	 * Vérifie la validité des champs date, index, prixUnite et partieFixe
	 * 
	 * @param date       date du relevé
	 * @param index      index du relevé
	 * @param prixUnite  prix par unité du relevé
	 * @param partieFixe prix de la partie fixe lors du relevé
	 * 
	 * @return true si tous les champs sont valides, false sinon
	 */
	private boolean verifierChampsUn(String date, String index, String prixUnite, String partieFixe) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		// Si un des champs n'est pas rempli alors on affiche un pop-up erreur
		if (date.isEmpty() || index.isEmpty() || prixUnite.isEmpty() || partieFixe.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que la date est du bon format (DD-MM-YYYY)
		try {
			LocalDate.parse(date, formatter);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
					"Format de date invalide. Veuillez entrer la date au format JJ-MM-AAAA (ex: 15-03-2020)",
					"Erreur de format", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérification que l'Index est bien un nombre à virgule (float)
		try {
			Float.parseFloat(index);
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "L'index doit être un nombre à virgule (ex: 100.5).",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérification que le Prix à l'Unité est bien un nombre à virgule (float)
		try {
			Float.parseFloat(prixUnite);
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "Le prix à l'unité doit être un nombre à virgule (ex: 100.5).",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérification que la Partie Fixe est bien un nombre à virgule (float)
		try {
			Float.parseFloat(partieFixe);
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, "La partie fixe doit être un nombre à virgule (ex: 100.5).",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Verification de si l'Index, le Prix à l'Unité ou la Partie Fixe n'ont pas de
		// valeurs négatives
		if (Double.valueOf(index) < 0 || Double.valueOf(prixUnite) < 0 || Double.valueOf(partieFixe) < 0) {
			JOptionPane.showMessageDialog(null, "Veuillez saisir des valeurs positives.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Vérifie la validité des champs idCompteur et index S'assure que le nouvel
	 * index ne peut etre inferieur au précédent que si le compteur a été changé
	 * 
	 * @param index index du compteur
	 * @param cpt   compteur en question
	 * 
	 * @return return true si les champs sont valides, false sinon
	 */
	private boolean verifierChampsDeux(String index, Compteur cpt) {

		ReleveCompteur dernierReleve = this.getDernierReleveCompteur();

		// Verification de si le compteur a deja été relevé aujourd'hui
		if (dernierReleve != null && dernierReleve.getDateReleve().isEqual(LocalDate.now())) {
			JOptionPane.showMessageDialog(null,
					"Vous ne pouvez pas relever un compteur deux fois dans la même journée.", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Verification de si l'index a baissé mais que le compteur n'a pas été changé
		if (dernierReleve != null && fARC.getRdbtnNon().isSelected()
				&& Double.valueOf(index) < dernierReleve.getIndexCompteur()) {
			JOptionPane.showMessageDialog(null,
					"Vous ne pouvez pas saisir un index inférieur au précédent sans avoir changé le compteur."
							+ "\nIndex précédent : " + dernierReleve.getIndexCompteur(),
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// Vérifier que le nouvel index n'est pas inférieur à celui d'avant si on ne l'a
		// pas modifié
		if (dernierReleve == null && fARC.getRdbtnNon().isSelected() && Double.valueOf(index) < cpt.getIndexDepart()) {
			JOptionPane.showMessageDialog(null,
					"Vous ne pouvez pas saisir un index inférieur au précédent sans avoir changé le compteur."
							+ "\nIndex précédent : " + cpt.getIndexDepart(),
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Traite l'insertion d'un relevé de compteur
	 * 
	 * @param dateReleve  date du relevé
	 * @param index       index du relevé
	 * @param prixUnite   prix par unité lors du relevé
	 * @param partieFixe  prix de la partie fixe lors du relevé
	 * @param idCompteur  id du compteur
	 * @param ancienIndex ancien index, celui du précédent relevé
	 * 
	 * @return true si l'insertion s'est bien passée, false sinon
	 */
	private boolean traiterInsererReleve(LocalDate dateReleve, double index, double prixUnite, double partieFixe,
			String idCompteur, double ancienIndex) {

		ReleveCompteur r = new ReleveCompteur(dateReleve, Double.valueOf(index), Double.valueOf(prixUnite),
				Double.valueOf(partieFixe), idCompteur, ancienIndex);

		DaoReleveCompteur dao = new DaoReleveCompteur();
		try {
			dao.create(r);
			// HISTORIQUE
			if (batimentOuBien.equals(BATIMENT)) {
				GestionTableHistoriqueFenPrinc
						.ajouterHistorique(new Historique(LocalDateTime.now(), "Ajout Relevé Compteur à un Bâtiment",
								"+ " + r.getDateReleve() + " - " + r.getIdCompteur() + " / Bat : " + id)); // IDBAT
			}
		} catch (SQLException e1) {
			// Vérifier que ce relevé de compteur n'existe pas déjà dans la Base de Données
			if (e1.getErrorCode() == 1) {
				JOptionPane.showMessageDialog(null, "Cet ID relevé existe déjà.", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		return true;
	}

	/**
	 * MAJ des fenetres en arriere plan
	 */
	private void traiterMAJArrierePlan() {
		// Mise à jour de la fenetre en arrière plan des infos du BAT
		if (batimentOuBien.equals(BATIMENT)) {
			FenBatimentInformations fBI = (FenBatimentInformations) fARC.getFenAncetre();
			fBI.getGestionTableRelev().ecrireLigneTable();
		} else if (batimentOuBien.equals(BIEN)) {
			FenBienLouableInformations fBI = (FenBienLouableInformations) fARC.getFenAncetre();
			fBI.getGestionTableRelev().ecrireLigneTable(id);
		} else {
			throw new IllegalArgumentException("La valeur de batiment ou bien n'est ni batiment ni bien.");
		}
	}

}
