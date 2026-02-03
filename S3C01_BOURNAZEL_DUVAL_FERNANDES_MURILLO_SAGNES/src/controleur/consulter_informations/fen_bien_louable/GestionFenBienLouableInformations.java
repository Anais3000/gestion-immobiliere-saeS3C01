package controleur.consulter_informations.fen_bien_louable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import controleur.Outils;
import controleur.fen_principal.GestionTableHistoriqueFenPrinc;
import modele.Batiment;
import modele.BienLouable;
import modele.Compteur;
import modele.Diagnostic;
import modele.Historique;
import modele.Intervention;
import modele.Locataire;
import modele.Louer;
import modele.Necessiter;
import modele.ReleveCompteur;
import modele.dao.DaoBatiment;
import modele.dao.DaoBienLouable;
import modele.dao.DaoCompteur;
import modele.dao.DaoDiagnostic;
import modele.dao.DaoIntervention;
import modele.dao.DaoLocataire;
import modele.dao.DaoLouer;
import modele.dao.DaoNecessiterIntervention;
import modele.dao.DaoReleveCompteur;
import vue.FenEffectuerRegularisation;
import vue.FenFinDeBail;
import vue.ajouter.FenAjouterBail;
import vue.ajouter.FenAjouterCompteur;
import vue.ajouter.FenAjouterDiagnosticBien;
import vue.ajouter.FenAjouterEtatDesLieux;
import vue.ajouter.FenAjouterInterventionBienLouable;
import vue.ajouter.FenAjouterReleveCompteur;
import vue.ajouter.FenPaiementLoyer;
import vue.consulter_informations.FenBatimentInformations;
import vue.consulter_informations.FenBienLouableInformations;
import vue.consulter_informations.FenConsulterEtatDesLieux;
import vue.consulter_informations.FenLocataireInformations;
import vue.modifier.FenModifierBienLouable;

public class GestionFenBienLouableInformations implements ActionListener {

	private FenBienLouableInformations fenetre;
	private BienLouable selectBien;
	private DaoBienLouable daoBL;
	private DaoLouer daoLouer;
	private DaoLocataire daoLoc;
	private DaoCompteur daoCompt;

	private static final String INFOS_DERNIERELOC_IMPOSSIBLES = "Impossible de récupérer les informations de la dernière location.";
	private static final String INFOS_LOC_IMPOSSIBLES = "Impossible de récupérer les informations de la location.";
	private static final String GARAGE_ASSOCIE_LOGEMENT = "Ce garage est associé à un logement : ";

	/**
	 * Initialise la gestion de la fenêtre permettant de consulter les informations
	 * d'un bien louable
	 * 
	 * @param f          fenetre permettant de consulter les informations
	 * @param selectBien bien louable duquel on veut consulter les infos
	 */
	public GestionFenBienLouableInformations(FenBienLouableInformations f, BienLouable selectBien) {
		this.fenetre = f;
		this.selectBien = selectBien;
		this.daoBL = new DaoBienLouable();
		this.daoLouer = new DaoLouer();
		this.daoLoc = new DaoLocataire();
		this.daoCompt = new DaoCompteur();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (((JButton) e.getSource()).getText()) {
		case "Consulter bâtiment":

			if (!this.traiterConsulterBatiment()) {
				break;
			}

			break;

		case "Modifier informations":

			FenModifierBienLouable fenMBLI = new FenModifierBienLouable(selectBien, fenetre);
			fenMBLI.setVisible(true);
			break;

		case "Supprimer le Bien Louable":

			this.traiterSuppressionBL();

			break;
		case "Ajouter":

			if (!this.traiterAjoutELDEntree()) {
				break;
			}

			break;
		case "Consulter":

			if (!this.traiterConsulterEDLEntree()) {
				break;
			}

			break;
		case "Paiement loyer":

			this.traiterPayerLoyer();

			break;
		case "Créer un bail":

			FenAjouterBail fAB = new FenAjouterBail(this.fenetre, selectBien);
			fAB.setVisible(true);
			break;

		case "Résilier le bail":

			if (!this.traiterResilierBail()) {
				break;
			}

			break;
		case "Ajouter un relevé":

			if (!this.traiterAjoutReleve()) {
				break;
			}

			break;
		case "Supprimer le relevé":

			this.traiterSupprimerReleve();

			break;
		case "Effectuer la régularisation":

			if (!this.traiterEffectuerRegul()) {
				break;
			}

			break;
		case "Ajouter une intervention":

			this.traiterAjoutInterv();

			break;

		case "Supprimer l'intervention":

			this.traiterSupprimerInterv();

			break;

		case "Ajouter un diagnostic":

			FenAjouterDiagnosticBien fenAD = new FenAjouterDiagnosticBien(selectBien.getIdBien(), fenetre);
			fenAD.setVisible(true);
			break;

		case "Supprimer le diagnostic":

			this.traiterSupprimerDiagnostic();

			break;
		case "Consulter locataire":

			this.traiterConsulterLocataire();

			break;
		case "Consulter état des lieux":

			if (!this.traiterConsulterEDL()) {
				break;
			}

			break;
		case "Ajouter un compteur":

			FenAjouterCompteur fenAC = new FenAjouterCompteur(selectBien.getIdBien(), fenetre, "bien");
			fenAC.setVisible(true);
			break;

		case "Supprimer le compteur":

			this.traiterSupprimerCompteur();

			break;
		case "Quitter":
			this.fenetre.dispose();
			break;

		default:
			// Cas non prévu (sécurité & requis par SonarQube et les autres outils)
			JOptionPane.showMessageDialog(null, "Erreur inconnue", Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			break;
		}

	}

	/**
	 * Met à jour l'attribut bien de cette fenetre, en cas de mise a jour
	 * 
	 * @param bien nouveau objet bien qui remplace l'ancien
	 */
	public void majBienSelect(BienLouable bien) {
		this.selectBien = bien;
	}

	private boolean traiterConsulterBatiment() {
		DaoBatiment daoBat = new DaoBatiment();
		Batiment bat = null;
		try {
			bat = daoBat.findById(selectBien.getIdBat());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (bat == null) {
			JOptionPane.showMessageDialog(null, "Impossible de récupérer les informations du bâtiment.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		FenBatimentInformations fenBI = new FenBatimentInformations(bat, null);
		fenBI.getGestionTableAss().ecrireLigneTable();
		fenBI.getGestionTableBiens().ecrireLigneTable();
		fenBI.getGestionTableCompt().ecrireLigneTable();
		fenBI.getGestionTableInterv().ecrireLigneTable();
		fenBI.getGestionTableRelev().ecrireLigneTable();
		fenBI.setVisible(true);

		return true;
	}

	/**
	 * Initialise les valeurs de la combo box qui sert à consulter le montant des
	 * loyers et provisions percues sur une année
	 */
	public void initialiserValeurs() {
		int anneeConstruction = selectBien.getDateConstruction().getYear();
		int anneeCourante = LocalDate.now().getYear();

		for (int i = anneeConstruction; i <= anneeCourante; i++) {
			fenetre.getComboBoxAnnee().addItem(i);
		}
		fenetre.getLblValeurLoyers().setText("lorem");
		fenetre.getLblValeurLoyers().setText("lorem");
	}

	/**
	 * Traite la suppression du bien louable
	 */
	private void traiterSuppressionBL() {
		// Fenêtre de confirmation
		int confirmation = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce bien louable ?",
				Outils.CONFIRMATION_STRING, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (confirmation == JOptionPane.YES_OPTION) {

			try {
				this.daoBL.delete(selectBien);
				fenetre.getFenBienAncetre().getGestionTable().ecrireLigneTableBienLouable();
				GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
						"Supprimer Bien Louable", "- " + this.selectBien.getIdBien()));
				fenetre.dispose();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Traite l'ajout de l'état des lieux d'entrée
	 * 
	 * @return true si l'opération s'est correctement déroulée, false sinon
	 */
	private boolean traiterAjoutELDEntree() {
		Louer location = null;

		try {
			location = daoLouer.findByIdBienLouableEtDateEntree(selectBien.getIdBien(),
					fenetre.getLblDateDebutBailValeur().getText());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (location == null) {
			JOptionPane.showMessageDialog(null, INFOS_LOC_IMPOSSIBLES, Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (location.getIdLogementAssocie() != null) {
			JOptionPane.showMessageDialog(null,
					GARAGE_ASSOCIE_LOGEMENT + location.getIdLogementAssocie()
							+ ".\nAjoutez l'état des lieux sur ce logement.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		FenAjouterEtatDesLieux fenAEL = new FenAjouterEtatDesLieux(this.fenetre, location,
				FenAjouterEtatDesLieux.Type.ENTREE);
		fenAEL.setVisible(true);

		return true;
	}

	/**
	 * Traite la consultation de l'état des lieux d'entrée et l'ouverture de la
	 * fenetre appropriée
	 * 
	 * @return true si l'opération s'est correctement déroulée, false sinon
	 */
	private boolean traiterConsulterEDLEntree() {
		Louer derniereLoc = null;
		try {
			derniereLoc = daoLouer.findByIdBienLouable(selectBien.getIdBien());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (derniereLoc == null) {
			JOptionPane.showMessageDialog(null, INFOS_DERNIERELOC_IMPOSSIBLES, Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		Locataire locataireEDL = null;
		try {
			locataireEDL = daoLoc.findById(derniereLoc.getIdLocataire());
		} catch (SQLException e2) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e2.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		FenConsulterEtatDesLieux fCEDL = new FenConsulterEtatDesLieux(derniereLoc,
				FenConsulterEtatDesLieux.TypeEtat.ENTREE, selectBien, locataireEDL);
		fCEDL.setVisible(true);

		return true;
	}

	/**
	 * Traite le paiement de loyer, ouvre la fenetre permettant ce paiement
	 */
	private void traiterPayerLoyer() {
		Louer location = null;
		try {
			location = daoLouer.findByIdBienLouableEtDateEntree(selectBien.getIdBien(),
					fenetre.getLblDateDebutBailValeur().getText());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		FenPaiementLoyer fenPL = new FenPaiementLoyer(selectBien, location);

		if (fenPL.getComboBoxMois().getItemCount() > 0) {
			fenPL.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Tous les loyers dûs de ce bail ont été payés à ce jour.",
					Outils.INFORMATION_STRING, JOptionPane.INFORMATION_MESSAGE);
			fenPL.dispose();
		}
	}

	/**
	 * Traite la résiliation d'un bail, ouvre la fenetre permettant cette
	 * résiliation
	 * 
	 * @return true si l'opération s'est correctement déroulée, false sinon
	 */
	private boolean traiterResilierBail() {
		Louer location = null;
		Locataire locataire = null;
		try {
			location = daoLouer.findByIdBienLouableEtDateEntree(selectBien.getIdBien(),
					fenetre.getLblDateDebutBailValeur().getText());
			locataire = daoLoc.findById(location.getIdLocataire());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (location.getDateDebut().equals(LocalDate.now())) {
			JOptionPane.showMessageDialog(null, "Vous ne pouvez pas mettre fin à un bail qui a commencé aujourd'hui.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (locataire == null) {
			JOptionPane.showMessageDialog(null, "Impossible de récupérer les informations du locataire.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (location.getIdLogementAssocie() != null) {
			int res = JOptionPane.showConfirmDialog(null,
					GARAGE_ASSOCIE_LOGEMENT + location.getIdLogementAssocie() + ".\nVoulez-vous rendre le garage ?",
					Outils.CONFIRMATION_STRING, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (res == JOptionPane.NO_OPTION) {
				return false;
			}
		}

		if (location.getDateFin().isBefore(LocalDate.now())) {
			int res = JOptionPane.showConfirmDialog(null,
					"Le bail est terminé depuis longtemps. Mettre fin au bail sans faire de solde de tout compte ?",
					Outils.CONFIRMATION_STRING, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (res == JOptionPane.YES_OPTION) {

				location.setRevolue(1);
				try {
					daoLouer.update(location);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					return false;
				}
				this.fenetre.desactiverChampsBail();
			}
			return false;
		}

		FenFinDeBail fFDB = new FenFinDeBail(this.fenetre, location, locataire);
		fFDB.setVisible(true);

		return true;
	}

	/**
	 * Traite l'ajout d'un relevé de compteur et ouvre la fenetre appropriée
	 * 
	 * @return true si l'opération s'est bien passée, false sinon
	 */
	private boolean traiterAjoutReleve() {

		FenAjouterReleveCompteur fenARC = null;
		ArrayList<Compteur> listeCompteurs = null;
		try {
			listeCompteurs = (ArrayList<Compteur>) daoCompt.findAllByIdBien(selectBien.getIdBien());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (listeCompteurs == null) {
			JOptionPane.showMessageDialog(null, "Impossible de récupérer les informations de listes de compteur.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (listeCompteurs.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez d'abord ajouter un compteur au bien.",
					Outils.INFORMATION_STRING, JOptionPane.INFORMATION_MESSAGE);
		} else {

			try {
				fenARC = new FenAjouterReleveCompteur(selectBien.getIdBien(), fenetre, "bien");
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			fenARC.setVisible(true);

		}
		return true;
	}

	/**
	 * Traite la demande d'effectuer la régularisation des charges et ouvre la
	 * fenetre appropriée
	 * 
	 * @return true si l'opération s'est bien passée, false sinon
	 */
	private boolean traiterEffectuerRegul() {
		if (fenetre.getLblDateDebutBailValeur().getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vous ne pouvez pas effectuer la régularisation d'un bien non loué.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		Louer location = null;
		Locataire locataire = null;
		try {
			location = daoLouer.findByIdBienLouableEtDateEntree(selectBien.getIdBien(),
					fenetre.getLblDateDebutBailValeur().getText());
			locataire = daoLoc.findById(location.getIdLocataire());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (location.getIdLogementAssocie() != null) {
			JOptionPane.showMessageDialog(null,
					GARAGE_ASSOCIE_LOGEMENT + location.getIdLogementAssocie()
							+ ".\nEffectuez la régularisation sur ce logement.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (location.getDateFin().isBefore(LocalDate.now())) {
			JOptionPane.showMessageDialog(null, "Vous ne pouvez pas effectuer la régularisation d'un bail révolu.",
					Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
			return false;
		}

		LocalDate dateEntree = location.getDateDebut();
		LocalDate dateAujourdHui = LocalDate.now();

		// VERIF 1 : Pas de régul avant le premier anniversaire
		if (location.getDateDerniereRegul() == null) {
			// Première regularisation
			if (dateAujourdHui.isBefore(dateEntree.plusYears(1))) {
				JOptionPane.showMessageDialog(null,
						"Vous ne pouvez pas effectuer la régularisation avant le premier anniversaire de l'entrée dans les lieux.",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			// VERIF 2 : Une seule régul / an
			LocalDate prochaineRegulPossible = location.getDateDerniereRegul().plusYears(1).plusDays(1);

			if (dateAujourdHui.isBefore(prochaineRegulPossible)) {
				JOptionPane.showMessageDialog(null,
						"Vous avez déjà effectué la régularisation pour cette période. "
								+ "\nProchaine régularisation possible à partir du " + prochaineRegulPossible + ".",
						Outils.ERREUR_STRING, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		FenEffectuerRegularisation fenER = new FenEffectuerRegularisation(this.fenetre, this.selectBien, location,
				locataire);

		if (!fenER.getGestionClic().verifTaxesOrdures()) {
			fenER.dispose();
		} else {
			fenER.setVisible(true);
		}

		return true;
	}

	/**
	 * Traite la suppression d'un relevé de compteur et ouvre la fenetre le
	 * permettant
	 */
	private void traiterSupprimerReleve() {
		JTable tableRelev = fenetre.getTableReleves();
		int rowselected = tableRelev.getSelectedRow();

		if (rowselected != -1) {

			// Fenêtre de confirmation
			int confirm = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce relevé ?",
					Outils.CONFIRMATION_STRING, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {

				DaoReleveCompteur daoRelev = new DaoReleveCompteur();

				String idCompteur = tableRelev.getValueAt(rowselected, 0).toString();
				String dateReleve = tableRelev.getValueAt(rowselected, 1).toString();

				ReleveCompteur relev = null;
				try {
					relev = daoRelev.findById(dateReleve, idCompteur);
					daoRelev.delete(relev);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				fenetre.getGestionTableRelev().ecrireLigneTable(selectBien.getIdBien());

			}
		}
	}

	/**
	 * Traite l'ajout d'une intervention sur le bien et ouvre la fenetre le
	 * permettant
	 */
	private void traiterAjoutInterv() {
		DaoIntervention dao = new DaoIntervention();

		ArrayList<String> ids = new ArrayList<>();
		ArrayList<Intervention> intervs = new ArrayList<>();
		try {

			intervs = (ArrayList<Intervention>) dao.findAllByIdBat(selectBien.getIdBat());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		for (Intervention i : intervs) {
			if (i.getDateFacture() != null) {
				if (i.getDateFacture().isBefore(LocalDate.now())) {
					ids.add(i.getIdIntervention());
				}
			} else if (i.getDateFacture() == null) {
				ids.add(i.getIdIntervention());
			}
		}

		if (ids.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Veuillez d'abord ajouter une intervention en cours au bâtiment.",
					Outils.INFORMATION_STRING, JOptionPane.INFORMATION_MESSAGE);
		} else {
			FenAjouterInterventionBienLouable fenInterventionBienLouable = new FenAjouterInterventionBienLouable(
					selectBien.getIdBien(), fenetre);
			fenInterventionBienLouable.setVisible(true);
		}
	}

	/**
	 * Traite la suppression d'un diagnostic et ouvre la fenetre le permettant
	 */
	private void traiterSupprimerDiagnostic() {
		JTable tableDiag = fenetre.getTableDiagnostics();
		int rowselected = tableDiag.getSelectedRow();

		if (rowselected != -1) {

			// Fenêtre de confirmation
			int confirm = JOptionPane.showConfirmDialog(null,
					"Voulez-vous vraiment supprimer ce diagnostic de ce bien ?", Outils.CONFIRMATION_STRING,
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {

				DaoDiagnostic daoDiag = new DaoDiagnostic();

				String libDiag = tableDiag.getValueAt(rowselected, 0).toString();
				String dateDebut = tableDiag.getValueAt(rowselected, 1).toString();

				Diagnostic diag = null;
				try {
					diag = daoDiag.findById(libDiag, dateDebut, selectBien.getIdBien());
					daoDiag.delete(diag);
					GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
							"Supprimer Diagnostic Bien Louable", "- " + diag.getLibelle() + " // " + diag.getIdBien()));
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				fenetre.getGestionTableDiag().ecrireLigneTable(selectBien.getIdBien());

			}
		}
	}

	/**
	 * Traite la consultation du locataire et ouvre la fenetre le permettant
	 */
	private void traiterConsulterLocataire() {
		JTable tableLoc = fenetre.getTableAnciensLocataires();
		int rowselected = tableLoc.getSelectedRow();

		if (rowselected != -1) {

			String idLoc = tableLoc.getValueAt(rowselected, 1).toString();

			Locataire loc = null;
			try {
				loc = daoLoc.findById(idLoc);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			FenLocataireInformations fenLI = new FenLocataireInformations(loc, null);

			fenLI.getGestionTableBiens().ecrireLigneTableBienLouable();
			fenLI.getGestionTablePaiements().ecrireLigneTablePaiements();
			fenLI.setVisible(true);

		}
	}

	/**
	 * Traite la consultation d'un etat des lieux spécifique et ouvre la fenetre le
	 * permettant
	 * 
	 * @return true si l'ouverture s'est bien passée, false sinon
	 */
	private boolean traiterConsulterEDL() {
		JTable tableEtatLieux = fenetre.getTableAnciensEtatsLieux();
		int rowselected = tableEtatLieux.getSelectedRow();

		if (rowselected != -1) {

			String dateEtat = tableEtatLieux.getValueAt(rowselected, 0).toString();
			String typeEtat = tableEtatLieux.getValueAt(rowselected, 1).toString();

			if (typeEtat.equals("Entrée")) {

				if (!this.traiterConsulterEDLEntreeTableau(dateEtat)) {
					return false;
				}

			} else {

				if (!this.traiterConsulterEDLSortieTableau(dateEtat)) {
					return false;
				}

			}
		}
		return true;
	}

	/**
	 * Traite la suppression d'un compteur et l'ouverture de la fenetre le
	 * permettant
	 */
	private void traiterSupprimerCompteur() {
		JTable tableCompt = fenetre.getTableCompteurs();
		int rowselected = tableCompt.getSelectedRow();

		if (rowselected != -1) {

			// Fenêtre de confirmation
			int confirm = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce compteur ?",
					Outils.CONFIRMATION_STRING, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {

				String idCompt = tableCompt.getValueAt(rowselected, 0).toString();

				Compteur compt = null;
				try {
					compt = daoCompt.findById(idCompt);
					daoCompt.delete(compt);
					GestionTableHistoriqueFenPrinc.ajouterHistorique(new Historique(LocalDateTime.now(),
							"Supprimer Compteur Bien Louable", "- " + compt.getIdCompteur() + " ["
									+ compt.getTypeCompteur() + "] // Bien Louable : " + compt.getIdBien()));
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				fenetre.getGestionTableCompt().ecrireLigneTable(selectBien.getIdBien());

			}
		}
	}

	/**
	 * Traite la suppression d'une intervention sur le bien et ouvre la fenetre le
	 * permettant
	 */
	private void traiterSupprimerInterv() {
		JTable tableInterv = fenetre.getTableInterventions();
		int rowselected = tableInterv.getSelectedRow();

		if (rowselected != -1) {

			// Fenêtre de confirmation
			int confirm = JOptionPane.showConfirmDialog(null,
					"Voulez-vous vraiment supprimer cette intervention sur ce bien ? Elle ne sera pas supprimée du bâtiment.",
					Outils.CONFIRMATION_STRING, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {

				DaoNecessiterIntervention daoNec = new DaoNecessiterIntervention();

				String idInterv = tableInterv.getValueAt(rowselected, 0).toString();

				Necessiter nec = null;
				try {
					nec = daoNec.findById(selectBien.getIdBien(), idInterv);
					daoNec.delete(nec);
					GestionTableHistoriqueFenPrinc.ajouterHistorique(
							new Historique(LocalDateTime.now(), "Supprimer Intervention Bien Louable",
									"- " + nec.getIdIntervention() + " // " + selectBien.getIdBien()));
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				fenetre.getGestionTableInterv().ecrireLigneTable(selectBien.getIdBien());

			}
		}
	}

	/**
	 * Traite l'ouverture d'un état des lieux d'entrée dans le tableau des états des
	 * lieux
	 * 
	 * @param dateEtat date de l'état des lieux
	 * @return true si l'opération s'est correctement déroulée, false sinon
	 */
	private boolean traiterConsulterEDLEntreeTableau(String dateEtat) {
		Louer ancienneLoc = null;
		try {
			ancienneLoc = daoLouer.findByIdBienLouableEtDateEntreeEDL(selectBien.getIdBien(), dateEtat);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (ancienneLoc == null) {
			JOptionPane.showMessageDialog(null, INFOS_DERNIERELOC_IMPOSSIBLES, Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		Locataire locataireEDL = null;
		try {
			locataireEDL = daoLoc.findById(ancienneLoc.getIdLocataire());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		FenConsulterEtatDesLieux fCEL = new FenConsulterEtatDesLieux(ancienneLoc,
				FenConsulterEtatDesLieux.TypeEtat.ENTREE, selectBien, locataireEDL);

		fCEL.setVisible(true);

		return true;
	}

	/**
	 * Traite l'ouverture d'un état des lieux de sortie dans le tableau des états
	 * des lieux
	 * 
	 * @param dateEtat date de l'état des lieux
	 * @return true si l'opération s'est correctement déroulée, false sinon
	 */
	private boolean traiterConsulterEDLSortieTableau(String dateEtat) {
		Louer ancienneLoc = null;
		try {
			ancienneLoc = daoLouer.findByIdBienLouableEtDateSortieEDL(selectBien.getIdBien(), dateEtat);
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (ancienneLoc == null) {
			JOptionPane.showMessageDialog(null, INFOS_DERNIERELOC_IMPOSSIBLES, Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		Locataire locataireEDL = null;
		try {
			locataireEDL = daoLoc.findById(ancienneLoc.getIdLocataire());
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, Outils.ERREUR_EXCEPTION + e1.getMessage(), Outils.ERREUR_STRING,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		FenConsulterEtatDesLieux fCEL = new FenConsulterEtatDesLieux(ancienneLoc,
				FenConsulterEtatDesLieux.TypeEtat.SORTIE, selectBien, locataireEDL);

		fCEL.setVisible(true);

		return true;
	}

}
