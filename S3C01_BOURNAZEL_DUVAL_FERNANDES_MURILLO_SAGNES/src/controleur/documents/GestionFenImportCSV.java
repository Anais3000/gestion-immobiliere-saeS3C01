package controleur.documents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.BienLouable;
import modele.Historique;
import modele.Louer;
import modele.Paiement;
import modele.dao.DaoBienLouable;
import modele.dao.DaoLouer;
import modele.dao.DaoPaiement;
import vue.documents.FenImportCSV;

public class GestionFenImportCSV implements ActionListener {

	FenImportCSV f;
	private DaoPaiement daoP;
	private DaoBienLouable daoB;
	private DaoLouer daoL;
	private Map<String, BienLouable> mapBiens;
	private Collection<BienLouable> bienLouables;
	private String cheminFichier;
	private File fichierSelectionne;
	private int resultat;

	/**
	 * Initialise la classe de gestion de la fenetre d'import de CSV
	 * 
	 * @param f fenetre pour importer des CSV
	 */
	public GestionFenImportCSV(FenImportCSV f) {
		this.f = f;
		this.daoP = new DaoPaiement();
		this.daoB = new DaoBienLouable();
		this.daoL = new DaoLouer();
		try {
			bienLouables = daoB.findAll();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		}
		mapBiens = new HashMap<>();
		for (BienLouable b : bienLouables) {
			if (!mapBiens.containsKey(b.getIdBien())) {
				mapBiens.put(b.getIdBien(), b);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton itemSelectionne = (JButton) e.getSource();
		switch (itemSelectionne.getText()) {
		case "Choisir fichier":
			this.traiterChoisirFichier();
			break;
		case "Valider":
			this.traiterValiderImport();
			break;
		case "Annuler":
			f.dispose();
			break;
		default:
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	/**
	 * Retourne une liste de paiements suite à la lecture d'un fichier CSV
	 * 
	 * @param cheminFichier chemin du fichier CSV sur le disque local
	 * @return la liste de paiements
	 */
	public List<Paiement> lireFichierCSV(String cheminFichier) {
		List<Paiement> paiements = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(cheminFichier);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr)) {

			String ligne;
			boolean premiereLigne = true;

			while ((ligne = br.readLine()) != null) {
				if (premiereLigne) {
					premiereLigne = false;
					continue;
				}

				if (ligne.trim().isEmpty() || ligne.trim().equals(";;;;")) {
					continue;
				}

				String[] parties = ligne.split(";");

				if (parties.length >= 5) {
					Paiement p = this.traiterInsertionCSV(parties);
					String idLogement = parties[0].trim();
					String idLocataire = parties[1].trim();

					if (p == null) {
						JOptionPane.showMessageDialog(null, "Erreur lors de la lecture du paiement à insérer",
								Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
						return Collections.emptyList();
					}

					LocalDate dateFormat = p.getDatePaiement();

					// Vérifier l'existence du bail avant d'insérer
					if (!verifierExistenceBail(idLogement, idLocataire, dateFormat)) {
						JOptionPane
								.showMessageDialog(null,
										"Aucun bail actif trouvé pour le logement " + idLogement + " à la date "
												+ dateFormat.toString(),
										Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
						return Collections.emptyList();
					}

					// Insérer le paiement
					paiements = this.traiterInsererPaiement(p, paiements, idLogement, dateFormat);

					if (paiements.isEmpty()) {
						return Collections.emptyList();
					}
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erreur lors de la lecture du fichier : " + e.getMessage(),
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
		}

		return paiements;
	}

	/**
	 * Vérifie qu'un bail existe pour le bien donné à la date spécifiee
	 * 
	 * @param idBien       id du bien donné
	 * @param idLocataire  id du locataire supposé être en cours de bail
	 * @param datePaiement date du paiement (à laquelle on compare les dates de
	 *                     bail)
	 * 
	 * @return true si le bail existe, false sinon
	 */
	private boolean verifierExistenceBail(String idBien, String idLocataire, LocalDate datePaiement) {
		try {
			Louer bail = daoL.findByIdBienLouable(idBien);

			if (bail == null) {
				return false;
			}

			// Vérifier si la date du paiement est comprise dans au moins un bail

			LocalDate dateDebut = bail.getDateDebut();
			LocalDate dateFin = bail.getDateFin();

			// Le paiement doit être après ou à la date de début
			boolean apresDebut = !datePaiement.isBefore(dateDebut);

			// Le paiement doit être avant la date de fin (si elle existe)
			boolean avantFin = (dateFin == null) || !datePaiement.isAfter(dateFin);

			// On peut pas inserer des données d'un bail révolu
			if (dateFin != null && dateFin.isBefore(LocalDate.now())) {
				JOptionPane.showMessageDialog(null, "Impossible d'insérer des données pour des baux révolus.",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}

			if (!(bail.getIdLocataire().equals(idLocataire))) {
				JOptionPane.showMessageDialog(null,
						"Le locataire du bail " + bail.getIdLocataire() + " sur le logement " + bail.getIdBienLouable()
								+ " ne correspond pas à ce qui est dans le tableau : " + idLocataire + ".",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}

			return (apresDebut && avantFin);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erreur lors de la vérification du bail : " + e.getMessage(),
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	/**
	 * Tente d'inserer le paiement du loyer dans la liste des paiements paiements
	 * 
	 * @param p          paiement à insérer
	 * @param paiements  liste des paiements à modifier
	 * @param idLogement id du logement concerné
	 * @param dateFormat date du paiement en String formatté
	 * @return la liste des paiements modifiée
	 */
	private List<Paiement> traiterInsererPaiement(Paiement p, List<Paiement> paiements, String idLogement,
			LocalDate dateFormat) {
		try {
			daoP.create(p);
			paiements.add(p);
		} catch (SQLException e) {
			int code = e.getErrorCode();
			if (code == 20004) {
				JOptionPane.showMessageDialog(null,
						"Tentative d'insérer un paiement de loyer sur le logement " + idLogement + " à la date "
								+ dateFormat.toString()
								+ " alors qu'il n'existe pas de bail sur ce logement à cette date.",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return Collections.emptyList();
			}
		}
		return paiements;
	}

	/**
	 * Traite la sélection du fichier
	 */
	private void traiterChoisirFichier() {
		JFileChooser fileChooser = new JFileChooser();
		javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter(
				"Fichiers CSV", "csv");
		fileChooser.setFileFilter(filter);
		resultat = fileChooser.showOpenDialog(null);
		if (resultat == JFileChooser.APPROVE_OPTION) {
			fichierSelectionne = fileChooser.getSelectedFile();
			cheminFichier = fichierSelectionne.getAbsolutePath();
		}
		f.getLblNomFichier().setText(fichierSelectionne.getName());
	}

	/**
	 * Traite la validation de l'importation
	 */
	private void traiterValiderImport() {
		if (!fichierSelectionne.getName().endsWith(".csv")) {
			JOptionPane.showMessageDialog(null,
					"L'extension du fichier ne correspond pas. Il faut importer un fichier .csv", Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
		} else if (resultat == JFileChooser.APPROVE_OPTION) {
			List<Paiement> paiements = lireFichierCSV(cheminFichier);

			if (paiements == null || paiements.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Erreur : aucune donnée à importer.", Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
							"Importation CSV", "+ " + fichierSelectionne.getName()));
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(null, paiements.size() + " lignes importées avec succès !", "Succès",
						JOptionPane.INFORMATION_MESSAGE);
				f.dispose();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Veuillez choisir un fichier à importer.", "Erreur",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Traite l'insertion des données contenues dans le fichier csv pour chaque
	 * ligne
	 * 
	 * @param parties ligne du csv en cours de lecture
	 * @return un objet de type Paiement correspondant à la ligne lue
	 */
	private Paiement traiterInsertionCSV(String[] parties) {
		String idLogement = parties[0].trim();
		String date = parties[2].trim();

		date = "31-" + date.substring(0, 2) + "-20" + date.substring(3, 5);
		float loyer = Float.parseFloat(parties[3].trim().replace(",", "."));
		float provision = Float.parseFloat(parties[4].trim().replace(",", "."));

		Float montant = loyer + provision;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dateFormat = LocalDate.parse(date, formatter);
		Paiement p = new Paiement(null, montant, "recus", "loyer", dateFormat, idLogement, null, dateFormat);
		if (Math.abs(loyer - mapBiens.get(idLogement).getLoyer()) > 0.001F) {
			try {
				mapBiens.get(idLogement).setLoyer(loyer);
				daoB.update(mapBiens.get(idLogement));
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}

		return p;
	}
}