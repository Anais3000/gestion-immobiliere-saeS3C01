package controleur.tables.fen_detail_charges;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import javax.swing.table.DefaultTableModel;

import modele.BienLouable;
import modele.Charge;
import modele.Louer;
import modele.dao.DaoBienLouable;
import modele.dao.DaoCharge;
import modele.dao.DaoLouer;
import vue.tables.FenDetailCharges;

public class GestionTableFenDetailCharges implements ActionListener {

	private FenDetailCharges f;
	private DefaultTableModel modeleTableFenDetailCharges;
	private DaoCharge daoC;
	private DaoLouer daoL;
	private DaoBienLouable daoBL;
	private BienLouable selectBien;
	private static final String PARTIES_COMMUNES = "parties communes";
	private static final String ORDURES = "ordures";

	public GestionTableFenDetailCharges(FenDetailCharges f, BienLouable selectBien) {
		this.f = f;
		this.modeleTableFenDetailCharges = (DefaultTableModel) this.f.getTableCharges().getModel();
		this.selectBien = selectBien;
		this.daoC = new DaoCharge();
		this.daoL = new DaoLouer();
		this.daoBL = new DaoBienLouable();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Méthodes utilisées ci-dessous
	}

	/**
	 * Calcule le coefficient de période pour une charge de type compteur Par
	 * exemple si le relevé de compteur porte sur la période de janvier à aout mais
	 * que le locataire arrive en mars, avec ce coefficient il ne paie sa part qu'à
	 * partir de mars
	 * 
	 * @param charge            charge en question
	 * @param dateDebutLocation date de début de la location
	 * @param dateFinRecherche  date de fin jusqu'à laquelle on veut calculer le
	 *                          coefficient
	 * @return le coefficient, de type Double
	 */
	private Double calculerCoefficientPeriode(Charge charge, LocalDate dateDebutLocation, LocalDate dateFinRecherche) {
		if (charge.getDateDebutPeriode() == null || charge.getDateFinPeriode() == null) {
			return null;
		}

		LocalDate dateDebutPeriodeCharge = charge.getDateDebutPeriode();
		LocalDate dateFinPeriodeCharge = charge.getDateFinPeriode();

		LocalDate debutEffectif = obtenirDateLaPlusTardive(dateDebutPeriodeCharge, dateDebutLocation);
		LocalDate finEffective = obtenirDateLaPlusTot(dateFinPeriodeCharge, dateFinRecherche);

		if (debutEffectif.isAfter(finEffective)) {
			return 0.0;
		}

		long joursPresence = calculerNombreJours(debutEffectif, finEffective);
		long dureeTotaleCompteur = calculerNombreJours(dateDebutPeriodeCharge, dateFinPeriodeCharge);

		return (double) joursPresence / dureeTotaleCompteur;
	}

	/*
	 * Retourne la date la plus tard entre deux dates
	 */
	private LocalDate obtenirDateLaPlusTardive(LocalDate date1, LocalDate date2) {
		if (date1.isAfter(date2)) {
			return date1;
		}
		return date2;
	}

	/*
	 * Retourne la date la plus tot entre deux dates
	 */
	private LocalDate obtenirDateLaPlusTot(LocalDate date1, LocalDate date2) {
		if (date1.isBefore(date2)) {
			return date1;
		}
		return date2;
	}

	/**
	 * Calcule le nombre de jours entre deux dates (ces memes dates comprises) ici
	 * on a utilisé ChronoUnit qui permet de calculer plus simplement les jours,
	 * minutes, heures... entre deux LocalDate
	 * 
	 * @param debut date de début
	 * @param fin   date de fin
	 * @return le nombre de jours, de type long
	 */
	private long calculerNombreJours(LocalDate debut, LocalDate fin) {
		return ChronoUnit.DAYS.between(debut, fin) + 1L;
	}

	/**
	 * Ajoute une ligne dans le tableau avec gestion du coefficient de période
	 * 
	 * @param c                      charge en question
	 * @param pourcentageRepartition pourcentage parties communes ou ordures
	 * @param dateDebutLocation      date de début de location
	 * @param dateFinRecherche       date de fin de la charge
	 * @param prefixe                préfixe donné à la charge afin de les rendre
	 *                               plus reconnaissables dans le tableau
	 */
	private void ajouterLigneCharge(Charge c, double pourcentageRepartition, LocalDate dateDebutLocation,
			LocalDate dateFinRecherche, String prefixe) {

		Double coeffPeriode = calculerCoefficientPeriode(c, dateDebutLocation, dateFinRecherche);
		double montantBase = c.getMontant();
		double montantAvecPourcentage;
		String affichagePourcentage;

		if (coeffPeriode != null) {
			montantAvecPourcentage = montantBase * coeffPeriode * (pourcentageRepartition / 100);
			// Affiche les coefficients sous la forme "XX.X % × YY.Y % période"
			// La periode c'est celle qui est utilisée pour le calcul du coefficient
			affichagePourcentage = String.format("%.1f %% × %.1f%% période", pourcentageRepartition,
					coeffPeriode * 100);
		} else {
			montantAvecPourcentage = montantBase * (pourcentageRepartition / 100);
			affichagePourcentage = pourcentageRepartition + " %";
		}

		this.modeleTableFenDetailCharges
				.addRow(new Object[] { prefixe + c.getLibelle(), c.getDateFacturation(), affichagePourcentage,
						String.format("%.2f", montantBase), String.format("%.2f", montantAvecPourcentage) });
	}

	/**
	 * Vérifie si la période de la charge chevauche la période de location
	 * 
	 * @param c                 charge en question
	 * @param dateDebutLocation date de début de la location
	 * @param dateFinRecherche  date de fin de la charge
	 * 
	 * @return true si la période chevauche la période de location, false sinon
	 */
	private boolean periodeChevauchePeriodeLocation(Charge c, LocalDate dateDebutLocation, LocalDate dateFinRecherche) {
		if (c.getDateDebutPeriode() == null || c.getDateFinPeriode() == null) {
			return true; // Pas de période = intervention, toujours valide
		}
		return !(c.getDateFinPeriode().isBefore(dateDebutLocation)
				|| c.getDateDebutPeriode().isAfter(dateFinRecherche));
	}

	/**
	 * Traite une charge de bâtiment selon son libellé et ajoute la ligne
	 * correspondante dans le détail
	 * 
	 * @param c                 charge en question
	 * @param bien              bien louable concerné
	 * @param dateDebutLocation
	 * @param dateFinRecherche
	 * @param prefixe
	 */
	private void traiterChargeBatiment(Charge c, BienLouable bien, LocalDate dateDebutLocation,
			LocalDate dateFinRecherche, String prefixe) {
		if (c.getLibelle().toLowerCase().contains(PARTIES_COMMUNES)) {
			ajouterLigneCharge(c, bien.getPourcentageEntretienPartiesCommunes(), dateDebutLocation, dateFinRecherche,
					prefixe + "PC] ");
		} else if (c.getLibelle().toLowerCase().contains(ORDURES)) {
			ajouterLigneCharge(c, bien.getPourcentageOrduresMenageres(), dateDebutLocation, dateFinRecherche,
					prefixe + "ORD] ");
		} else {
			ajouterLigneCharge(c, bien.getPourcentageEntretienPartiesCommunes(), dateDebutLocation, dateFinRecherche,
					prefixe + "COMPTEUR] ");
		}
	}

	/**
	 * Traite une intervention de bâtiment selon son libellé et ajoute la ligne
	 * correspondante dans le détail
	 * 
	 * @param c                 charge en question
	 * @param bien              bien concerné
	 * @param dateDebutLocation
	 * @param dateFinRecherche
	 * @param prefixe
	 */
	private void traiterInterventionBatiment(Charge c, BienLouable bien, LocalDate dateDebutLocation,
			LocalDate dateFinRecherche, String prefixe) {
		if (c.getLibelle().toLowerCase().contains(PARTIES_COMMUNES)) {
			ajouterLigneCharge(c, bien.getPourcentageEntretienPartiesCommunes(), dateDebutLocation, dateFinRecherche,
					prefixe + "PC] ");
		} else if (c.getLibelle().toLowerCase().contains(ORDURES)) {
			ajouterLigneCharge(c, bien.getPourcentageOrduresMenageres(), dateDebutLocation, dateFinRecherche,
					prefixe + "ORD] ");
		}
	}

	/**
	 * Traite les charges directes d'un bien et ajoute la ligne correspondante dans
	 * le détail
	 * 
	 * @param idBien            bien concerné
	 * @param dateMinEffective  date minimale de quand la charge concerne le
	 *                          locataire en cours
	 * @param dateDebutLocation
	 * @param dateFinRecherche
	 * @param prefixe
	 * @throws SQLException
	 */
	private void traiterChargesBien(String idBien, LocalDate dateMinEffective, LocalDate dateDebutLocation,
			LocalDate dateFinRecherche, String prefixe) throws SQLException {
		Collection<Charge> charges = this.daoC.findAllByIdBienLouableDateDebutDateFin(idBien,
				dateMinEffective.toString(), dateFinRecherche.toString());
		for (Charge c : charges) {
			ajouterLigneCharge(c, 100.0, dateDebutLocation, dateFinRecherche, prefixe);
		}
	}

	/**
	 * Traite les charges de bâtiment avec période et ajoute la ligne correspondante
	 * dans le détail
	 * 
	 * @param idBat             id du batiment concerné
	 * @param bien              bien concerné
	 * @param dateMinEffective
	 * @param dateDebutLocation
	 * @param dateFinRecherche
	 * @param prefixe
	 * @throws SQLException
	 */
	private void traiterChargesBatimentAvecPeriode(String idBat, BienLouable bien, LocalDate dateMinEffective,
			LocalDate dateDebutLocation, LocalDate dateFinRecherche, String prefixe) throws SQLException {
		Collection<Charge> charges = this.daoC.findAllByIdBatimentDateDebutDateFin(idBat, dateMinEffective.toString(),
				dateFinRecherche.toString());
		for (Charge c : charges) {
			if (!periodeChevauchePeriodeLocation(c, dateDebutLocation, dateFinRecherche)) {
				continue;
			}
			traiterChargeBatiment(c, bien, dateDebutLocation, dateFinRecherche, prefixe);
		}
	}

	/**
	 * Traite les interventions de bâtiment sans période (donc tout ce qui n'est pas
	 * un compteur) et ajoute la ligne correspondante dans le détail
	 * 
	 * @param idBat             id du batiment en question
	 * @param bien              bien louable en question
	 * @param dateMinEffective
	 * @param dateDebutLocation
	 * @param dateFinRecherche
	 * @param prefixe
	 * @throws SQLException
	 */
	private void traiterInterventionsBatiment(String idBat, BienLouable bien, LocalDate dateMinEffective,
			LocalDate dateDebutLocation, LocalDate dateFinRecherche, String prefixe) throws SQLException {
		Collection<Charge> charges = this.daoC.findAllByIdInterventionDateDebutDateFin(idBat,
				dateMinEffective.toString(), dateFinRecherche.toString());
		for (Charge c : charges) {
			traiterInterventionBatiment(c, bien, dateDebutLocation, dateFinRecherche, prefixe);
		}
	}

	/**
	 * Traite toutes les charges d'un garage en s'appuyant sur les autres fonctions
	 * précédentes et ajoute la ligne correspondante dans le détail
	 * 
	 * @param location          location en question
	 * @param dateMinEffective
	 * @param dateDebutLocation
	 * @param dateFinRecherche
	 * @throws SQLException
	 */
	private void traiterChargesGarage(Louer location, LocalDate dateMinEffective, LocalDate dateDebutLocation,
			LocalDate dateFinRecherche) throws SQLException {
		BienLouable garage = this.daoBL.findById(location.getIdBienLouable());

		traiterChargesBien(garage.getIdBien(), dateMinEffective, dateDebutLocation, dateFinRecherche, "[GAR] ");
		traiterChargesBatimentAvecPeriode(garage.getIdBat(), garage, dateMinEffective, dateDebutLocation,
				dateFinRecherche, "[GAR-BAT-");
		traiterInterventionsBatiment(garage.getIdBat(), garage, dateMinEffective, dateDebutLocation, dateFinRecherche,
				"[GAR-INT-");
	}

	/**
	 * Remet à 0 la table contenant les informations du détail des charges puis la
	 * remplit avec le détail correspondant
	 * 
	 * @throws SQLException
	 */
	public void ecrireLigneTable() throws SQLException {
		modeleTableFenDetailCharges.setRowCount(0);

		LocalDate dateDebutRecherche = LocalDate
				.parse(this.f.getFenAncetre().getGestionClic().getDateAnneeDerniere().toString());
		LocalDate dateFinRecherche = LocalDate
				.parse(this.f.getFenAncetre().getGestionClic().getDateCetteAnnee().toString());

		Louer locationBien = this.daoL.findByIdBienLouable(selectBien.getIdBien());
		if (locationBien == null) {
			throw new SQLException("Impossible de trouver la location pour le bien " + selectBien.getIdBien());
		}

		LocalDate dateDebutLocation = LocalDate.parse(locationBien.getDateDebut().toString());
		LocalDate dateMinEffective = obtenirDateLaPlusTardive(dateDebutLocation, dateDebutRecherche);

		// Traiter le bien principal
		traiterChargesBien(this.selectBien.getIdBien(), dateMinEffective, dateDebutLocation, dateFinRecherche,
				"[BIEN] ");
		traiterChargesBatimentAvecPeriode(this.selectBien.getIdBat(), this.selectBien, dateMinEffective,
				dateDebutLocation, dateFinRecherche, "[BAT-");
		traiterInterventionsBatiment(this.selectBien.getIdBat(), this.selectBien, dateMinEffective, dateDebutLocation,
				dateFinRecherche, "[INT-");

		// Traiter les garages
		Collection<Louer> garages = this.daoL.findAllLouerGaragesAssociesLogement(
				locationBien.getDateDebut().toString(), locationBien.getDateFin().toString(),
				locationBien.getIdBienLouable());

		if (garages != null) {
			for (Louer garage : garages) {
				traiterChargesGarage(garage, dateMinEffective, dateDebutLocation, dateFinRecherche);
			}
		}
	}
}