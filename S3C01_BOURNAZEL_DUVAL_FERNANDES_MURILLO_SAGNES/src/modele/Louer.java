package modele;

import java.time.LocalDate;
import java.util.Objects;

public class Louer {

	private LocalDate dateDebut;
	private LocalDate dateFin;
	private float depotDeGarantie;
	private LocalDate dateEtatDesLieuxEntree;
	private LocalDate dateEtatDesLieuxSortie;
	private String detailsEtatDesLieuxEntree;
	private String detailsEtatDesLieuxSortie;
	private LocalDate dateDerniereRegul;

	private Float ajustementLoyer;
	private LocalDate moisFinAjustement;
	private LocalDate moisDebutAjustement;

	private String idLogementAssocie;
	private int revolue;

	// clé étrangère & primaire de Louer
	private String idLocataire; // clé primaire de Locataire
	private String idBienLouable;
	private String idGarant;

	/**
	 * Crée un objet de type Louer (symbolisant un contrat de location)
	 * 
	 * @param dateDebut                 date de début du contrat
	 * @param dateFin                   date de fin du contrat
	 * @param depotDeGarantie           montant du dépot de garantie déposé par le
	 *                                  locataire
	 * @param dateEtatDesLieuxEntree    date de l'état des lieux d'entrée
	 * @param dateEtatDesLieuxSortie    date de l'état des lieux de sortie
	 * @param detailsEtatDesLieuxEntree détails de l'état des lieux d'entrée
	 * @param detailsEtatDesLieuxSortie détails de l'état des lieux de sortie
	 * @param idLocataire               identifiant du locataire concerné
	 * @param idBienLouable             identifiant du bien louable concerné
	 * @param idGarant                  identifiant du garant concerné
	 * @param dateDerniereRegul         date de la derniere régularisation
	 * @param ajustementLoyer           montant du (potentiel) ajustement de loyer à
	 *                                  payer chaque mois
	 * @param moisDebutAjustement       premier du mois du mois de début de la
	 *                                  période d'ajustement de loyer
	 * @param moisFinAjustement         premier du mois du mois de fin de la période
	 *                                  d'ajustement de loyer
	 * @param idLogementAssocie         identifiant du logement associé
	 * @param revolue                   1 si le contrat est révolu, 0 sinon
	 */
	public Louer(LocalDate dateDebut, LocalDate dateFin, float depotDeGarantie, LocalDate dateEtatDesLieuxEntree,
			LocalDate dateEtatDesLieuxSortie, String detailsEtatDesLieuxEntree, String detailsEtatDesLieuxSortie,
			String idLocataire, String idBienLouable, String idGarant, LocalDate dateDerniereRegul,
			Float ajustementLoyer, LocalDate moisFinAjustement, String idLogementAssocie, int revolue,
			LocalDate moisDebutAjustement) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.depotDeGarantie = depotDeGarantie;
		this.dateEtatDesLieuxEntree = dateEtatDesLieuxEntree;
		this.dateEtatDesLieuxSortie = dateEtatDesLieuxSortie;
		this.detailsEtatDesLieuxEntree = detailsEtatDesLieuxEntree;
		this.detailsEtatDesLieuxSortie = detailsEtatDesLieuxSortie;
		this.idLocataire = idLocataire;
		this.idBienLouable = idBienLouable;
		this.idGarant = idGarant;
		this.dateDerniereRegul = dateDerniereRegul;
		this.ajustementLoyer = ajustementLoyer;
		this.moisFinAjustement = moisFinAjustement;
		this.moisDebutAjustement = moisDebutAjustement;

		this.idLogementAssocie = idLogementAssocie;
		this.revolue = revolue;
	}

	// Getter & Setter
	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDate getDateDerniereRegul() {
		return dateDerniereRegul;
	}

	public void setDateDerniereRegul(LocalDate dateDerniereRegul) {
		this.dateDerniereRegul = dateDerniereRegul;
	}

	public String getIdGarant() {
		return idGarant;
	}

	public void setIdGarant(String idGarant) {
		this.idGarant = idGarant;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public Float getAjustementLoyer() {
		return ajustementLoyer;
	}

	public void setAjustementLoyer(Float ajustementLoyer) {
		this.ajustementLoyer = ajustementLoyer;
	}

	public LocalDate getMoisFinAjustement() {
		return moisFinAjustement;
	}

	public void setMoisFinAjustement(LocalDate moisFinAjustement) {
		this.moisFinAjustement = moisFinAjustement;
	}

	public float getDepotDeGarantie() {
		return depotDeGarantie;
	}

	public void setDepotDeGarantie(float depotDeGarantie) {
		this.depotDeGarantie = depotDeGarantie;
	}

	public LocalDate getDateEtatDesLieuxEntree() {
		return dateEtatDesLieuxEntree;
	}

	public void setDateEtatDesLieuxEntree(LocalDate dateEtatDesLieuxEntree) {
		this.dateEtatDesLieuxEntree = dateEtatDesLieuxEntree;
	}

	public LocalDate getDateEtatDesLieuxSortie() {
		return dateEtatDesLieuxSortie;
	}

	public void setDateEtatDesLieuxSortie(LocalDate dateEtatDesLieuxSortie) {
		this.dateEtatDesLieuxSortie = dateEtatDesLieuxSortie;
	}

	public String getDetailsEtatDesLieuxEntree() {
		return detailsEtatDesLieuxEntree;
	}

	public void setDetailsEtatDesLieuxEntree(String detailsEtatDesLieuxEntree) {
		this.detailsEtatDesLieuxEntree = detailsEtatDesLieuxEntree;
	}

	public String getDetailsEtatDesLieuxSortie() {
		return detailsEtatDesLieuxSortie;
	}

	public void setDetailsEtatDesLieuxSortie(String detailsEtatDesLieuxSortie) {
		this.detailsEtatDesLieuxSortie = detailsEtatDesLieuxSortie;
	}

	public String getIdLocataire() {
		return idLocataire;
	}

	public void setIdLocataire(String idLocataire) {
		this.idLocataire = idLocataire;
	}

	public String getIdBienLouable() {
		return idBienLouable;
	}

	public void setIdBienLouable(String idBienLouable) {
		this.idBienLouable = idBienLouable;
	}

	public String getIdLogementAssocie() {
		return idLogementAssocie;
	}

	public void setIdLogementAssocie(String idLogementAssocie) {
		this.idLogementAssocie = idLogementAssocie;
	}

	public int getRevolue() {
		return revolue;
	}

	public void setRevolue(int revolue) {
		this.revolue = revolue;
	}

	public LocalDate getMoisDebutAjustement() {
		return moisDebutAjustement;
	}

	public void setMoisDebutAjustement(LocalDate moisDebutAjustement) {
		this.moisDebutAjustement = moisDebutAjustement;
	}

	@Override
	public String toString() {
		return "Louer [dateDébut=" + dateDebut + ", dateFin=" + dateFin + ", dépotDeGarantie=" + depotDeGarantie
				+ ", dateEtatDesLieuxEntrée=" + dateEtatDesLieuxEntree + ", dateEtatDesLieuxSortie="
				+ dateEtatDesLieuxSortie + ", détailsEtatDesLieuxEntrée=" + detailsEtatDesLieuxEntree
				+ ", détailsEtatDesLieuxSortie=" + detailsEtatDesLieuxSortie + ", idLocataire=" + idLocataire + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateDebut, idLocataire);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Louer other = (Louer) obj;
		return Objects.equals(dateDebut, other.dateDebut) && Objects.equals(idLocataire, other.idLocataire);
	}

}
