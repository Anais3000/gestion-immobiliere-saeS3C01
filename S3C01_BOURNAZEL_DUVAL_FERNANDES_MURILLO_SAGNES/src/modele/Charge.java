package modele;

import java.time.LocalDate;
import java.util.Objects;

public class Charge {
	private String idCharge; // possiblement null si idBatiment renseigner et inversement
	private String libelle;
	private LocalDate dateFacturation;
	private int montant;
	private String commentaire;
	private LocalDate dateDebutPeriode;
	private LocalDate dateFinPeriode;

	// clé étrangère
	private String idBat; // clé primaire batiment, possiblement null si idCharge renseigner et
							// inversement
	private String idBien; // clé primaire Bien louable

	/**
	 * Crée un objet de type Charge
	 * 
	 * @param idCharge         identifiant de la charge
	 * @param libelle          libellé de la charge
	 * @param dateFacturation  date de facturation de la charge
	 * @param montant          montant en € de la charge
	 * @param commentaire      commentaire (optionnel), descriptif de la charge
	 * @param idBat            identifiant du batiment concerné (si il y a lieu)
	 * @param idBien           identifiant du bien concerné (si il y a lieu)
	 * @param dateDebutPeriode date de début de la période couverte par la charge
	 *                         (si il y a lieu, pour les compteurs des parties
	 *                         communes par exemple)
	 * @param dateFinPeriode   date de fin de la période couverte par la charge
	 */
	public Charge(String idCharge, String libelle, LocalDate dateFacturation, int montant, String commentaire,
			String idBat, String idBien, LocalDate dateDebutPeriode, LocalDate dateFinPeriode) {
		this.idCharge = idCharge;
		this.libelle = libelle;
		this.dateFacturation = dateFacturation;
		this.montant = montant;
		this.commentaire = commentaire;
		this.idBat = idBat;
		this.idBien = idBien;
		this.dateDebutPeriode = dateDebutPeriode;
		this.dateFinPeriode = dateFinPeriode;
	}

	public String getIdCharge() {
		return idCharge;
	}

	public void setIdCharge(String idCharge) {
		this.idCharge = idCharge;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public LocalDate getDateFacturation() {
		return dateFacturation;
	}

	public void setDateFacturation(LocalDate dateFacturation) {
		this.dateFacturation = dateFacturation;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String description) {
		this.commentaire = description;
	}

	public String getIdBatiment() {
		return idBat;
	}

	public void setIdBatiment(String batiment) {
		this.idBat = batiment;
	}

	public String getIdBienLouable() {
		return idBien;
	}

	public void setIdBienLouable(String idBien) {
		this.idBien = idBien;
	}

	public LocalDate getDateDebutPeriode() {
		return dateDebutPeriode;
	}

	public void setDateDebutPeriode(LocalDate dateDebutPeriode) {
		this.dateDebutPeriode = dateDebutPeriode;
	}

	public LocalDate getDateFinPeriode() {
		return dateFinPeriode;
	}

	public void setDateFinPeriode(LocalDate dateFinPeriode) {
		this.dateFinPeriode = dateFinPeriode;
	}

	@Override
	public String toString() {
		return "Charge [idCharge=" + idCharge + ", libellé=" + libelle + ", dateFacturation=" + dateFacturation
				+ ", montant=" + montant + ", description=" + commentaire + ", batiment=" + idBat + ", bienLouable="
				+ idBien + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCharge);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Charge other = (Charge) obj;
		return idCharge == other.idCharge;
	}
}
