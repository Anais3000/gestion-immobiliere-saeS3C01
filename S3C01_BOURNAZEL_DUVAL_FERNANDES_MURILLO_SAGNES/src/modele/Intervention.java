package modele;

import java.time.LocalDate;
import java.util.Objects;

public class Intervention {
	private String idIntervention; // clé primaire
	private String intitule;
	private LocalDate dateIntervention;

	private String numFacture;
	private LocalDate dateFacture;
	private Float montantFacture;

	private LocalDate dateAcompte; // doit être payer avant la facture
	private Float acompteFacture;

	private String numDevis;
	private Float montantDevis;

	private Float montantNonDeductible; // Impots ?
	private Float reduction; // Impots ?

	private int entretienPc;
	private int ordures;

	// clé étrangère
	private String idOrganisme; // clé primaire de organisme
	private String idBatiment; // clé primaire de batiment

	/**
	 * Crée un objet de type Intervention
	 * 
	 * @param idIntervention       identifiant de l'intervention
	 * @param intitule             intitulé de l'intervention
	 * @param dateIntervention     date à laquelle l'intervention a lieu
	 * @param numFacture           numéro de facture associée
	 * @param dateFacture          date de la facture associée
	 * @param montantFacture       montant de la facture associée
	 * @param dateAcompte          date de l'acompte associé
	 * @param acompteFacture       montant de l'acompte associé
	 * @param numDevis             numéro du devis associé
	 * @param montantDevis         montant du devis associé
	 * @param montantNonDeductible montant non déductible de l'intervention
	 * @param reduction            réduction sur le montant de l'intervention
	 * @param idOrganisme          identifiant de l'organisme réalisant
	 *                             l'intervention
	 * @param idBatiment           identifiant du batiment concerné
	 * @param entretienPc          1 si l'intervention concerne l'entretien des
	 *                             parties communes, 0 sinon
	 * @param ordures              1 si l'intervention concerne les ordures
	 *                             ménagères, 0 sinon
	 */
	public Intervention(String idIntervention, String intitule, LocalDate dateIntervention, String numFacture,
			LocalDate dateFacture, Float montantFacture, LocalDate dateAcompte, Float acompteFacture, String numDevis,
			Float montantDevis, Float montantNonDeductible, Float reduction, String idOrganisme, String idBatiment,
			int entretienPc, int ordures) {

		this.idIntervention = idIntervention;
		this.intitule = intitule;
		this.dateIntervention = dateIntervention;
		this.numFacture = numFacture;
		this.dateFacture = dateFacture;
		this.montantFacture = montantFacture;
		this.dateAcompte = dateAcompte;
		this.acompteFacture = acompteFacture;
		this.numDevis = numDevis;
		this.montantDevis = montantDevis;
		this.montantNonDeductible = montantNonDeductible;
		this.reduction = reduction;
		this.idOrganisme = idOrganisme;
		this.idBatiment = idBatiment;
		this.entretienPc = entretienPc;
		this.ordures = ordures;
	}

	public String getIdOrganisme() {
		return idOrganisme;
	}

	public void setOrganisme(String idOrganisme) {
		this.idOrganisme = idOrganisme;
	}

	public String getIdBatiment() {
		return idBatiment;
	}

	public void setBatiment(String idBatiment) {
		this.idBatiment = idBatiment;
	}

	public LocalDate getDateAcompte() {
		return dateAcompte;
	}

	public void setDateAcompte(LocalDate dateAcompte) {
		this.dateAcompte = dateAcompte;
	}

	public String getIdIntervention() {
		return idIntervention;
	}

	public void setIdIntervention(String idIntervention) {
		this.idIntervention = idIntervention;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public LocalDate getDateIntervention() {
		return dateIntervention;
	}

	public void setDateIntervention(LocalDate dateIntervention) {
		this.dateIntervention = dateIntervention;
	}

	public String getNumFacture() {
		return numFacture;
	}

	public void setNumFacture(String numFacture) {
		this.numFacture = numFacture;
	}

	public LocalDate getDateFacture() {
		return dateFacture;
	}

	public void setDateFacture(LocalDate dateFacture) {
		this.dateFacture = dateFacture;
	}

	public Float getMontantFacture() {
		return montantFacture;
	}

	public void setMontantFacture(Float montantFacture) {
		this.montantFacture = montantFacture;
	}

	public Float getAcompteFacture() {
		return acompteFacture;
	}

	public void setAcompteFacture(Float acompteFacture) {
		this.acompteFacture = acompteFacture;
	}

	public String getNumDevis() {
		return numDevis;
	}

	public void setNumDevis(String numDevis) {
		this.numDevis = numDevis;
	}

	public Float getMontantDevis() {
		return montantDevis;
	}

	public void setMontantDevis(Float montantDevis) {
		this.montantDevis = montantDevis;
	}

	public Float getMontantNonDeductible() {
		return montantNonDeductible;
	}

	public void setMontantNonDeductible(Float montantNonDeductible) {
		this.montantNonDeductible = montantNonDeductible;
	}

	public Float getReduction() {
		return reduction;
	}

	public void setReduction(Float reduction) {
		this.reduction = reduction;
	}

	public int getEntretienPc() {
		return entretienPc;
	}

	public void setEntretienPc(int entretienPc) {
		this.entretienPc = entretienPc;
	}

	public int getOrdures() {
		return ordures;
	}

	public void setOrdures(int ordures) {
		this.ordures = ordures;
	}

	@Override
	public String toString() {
		return "Intervention [idIntervention=" + idIntervention + ", intitulé=" + intitule + ", dateIntervention="
				+ dateIntervention + ", numFacture=" + numFacture + ", dateFacture=" + dateFacture + ", montantFacture="
				+ montantFacture + ", dateAcompte=" + dateAcompte + ", acompteFacture=" + acompteFacture + ", numDevis="
				+ numDevis + ", montantDevis=" + montantDevis + ", montantNonDéductible=" + montantNonDeductible
				+ ", réduction=" + reduction + ", numSiret=" + idOrganisme + ", idBatiment=" + idBatiment + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idIntervention);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Intervention other = (Intervention) obj;
		return Objects.equals(idIntervention, other.idIntervention);
	}

}
