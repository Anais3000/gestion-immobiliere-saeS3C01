package modele;

import java.time.LocalDate;

public class HistoriqueLoyer {

	private Float loyer;
	private Float provision;
	private String idBien;
	private LocalDate moisConcerne;

	/**
	 * Crée un objet de type HistoriqueLoyer
	 * 
	 * @param loyer        montant du loyer
	 * @param provision    montant de la provision pour charges
	 * @param idBien       id du bien concerné
	 * @param moisConcerne premier du mois (ex : 01 01 2026) pour lequel ces
	 *                     montants de loyer et de provision sont effectifs
	 */
	public HistoriqueLoyer(Float loyer, Float provision, String idBien, LocalDate moisConcerne) {
		this.loyer = loyer;
		this.provision = provision;
		this.idBien = idBien;
		this.moisConcerne = moisConcerne;
	}

	public Float getLoyer() {
		return loyer;
	}

	public void setLoyer(Float loyer) {
		this.loyer = loyer;
	}

	public Float getProvision() {
		return provision;
	}

	public void setProvision(Float provision) {
		this.provision = provision;
	}

	public String getIdBien() {
		return idBien;
	}

	public void setIdBien(String idBien) {
		this.idBien = idBien;
	}

	public LocalDate getMoisConcerne() {
		return moisConcerne;
	}

	public void setMoisConcerne(LocalDate moisConcerne) {
		this.moisConcerne = moisConcerne;
	}

}