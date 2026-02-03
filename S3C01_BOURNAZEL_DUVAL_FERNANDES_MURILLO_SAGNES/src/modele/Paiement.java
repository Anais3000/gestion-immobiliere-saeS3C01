package modele;

import java.time.LocalDate;
import java.util.Objects;

public class Paiement {

	private String idPaiement;
	private float montant;
	private String sensPaiement;
	private String libelle;
	private LocalDate datePaiement;
	private LocalDate moisConcerne;

	private String idBien;
	private String idIntervention;

	/**
	 * Crée un objet de type Paiement
	 * 
	 * @param idPaiement     identifiant du paiement
	 * @param montant        montant en € du paiement
	 * @param sensPaiement   sens du paiement (émis ou recus)
	 * @param libelle        libellé du paiement
	 * @param datePaiement   date du paiement
	 * @param idBien         identifiant du bien concerné (optionnel)
	 * @param idIntervention identifiant de l'intervention concernée (optionnel)
	 * @param moisConcerne   mois concerné par le paiement (optionnel, seulement
	 *                       pour les paiements de loyer)
	 */
	public Paiement(String idPaiement, float montant, String sensPaiement, String libelle, LocalDate datePaiement,
			String idBien, String idIntervention, LocalDate moisConcerne) {
		super();
		this.idPaiement = idPaiement;
		this.montant = montant;
		this.sensPaiement = sensPaiement;
		this.libelle = libelle;
		this.datePaiement = datePaiement;
		this.idBien = idBien;
		this.idIntervention = idIntervention;
		this.moisConcerne = moisConcerne;
	}

	public String getIdPaiement() {
		return idPaiement;
	}

	public void setIdPaiement(String idPaiement) {
		this.idPaiement = idPaiement;
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public String getSensPaiement() {
		return sensPaiement;
	}

	public void setSensPaiement(String sensPaiement) {
		this.sensPaiement = sensPaiement;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public LocalDate getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(LocalDate datePaiement) {
		this.datePaiement = datePaiement;
	}

	public String getIdBien() {
		return idBien;
	}

	public void setIdBien(String idBien) {
		this.idBien = idBien;
	}

	public String getIdIntervention() {
		return idIntervention;
	}

	public void setIdIntervention(String idIntervention) {
		this.idIntervention = idIntervention;
	}

	public LocalDate getMoisConcerne() {
		return moisConcerne;
	}

	public void setMoisConcerne(LocalDate moisConcerne) {
		this.moisConcerne = moisConcerne;
	}

	@Override
	public int hashCode() {
		return Objects.hash(datePaiement, idBien, idIntervention, idPaiement, libelle, montant, sensPaiement);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Paiement other = (Paiement) obj;
		return Objects.equals(datePaiement, other.datePaiement) && Objects.equals(idBien, other.idBien)
				&& Objects.equals(idIntervention, other.idIntervention) && Objects.equals(idPaiement, other.idPaiement)
				&& Objects.equals(libelle, other.libelle)
				&& Float.floatToIntBits(montant) == Float.floatToIntBits(other.montant)
				&& Objects.equals(sensPaiement, other.sensPaiement);
	}

}
