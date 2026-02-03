package modele;

import java.time.LocalDate;
import java.util.Objects;

public class BienLouable {

	private String idBien;
	private String idBat;
	private String adresse;
	private String codePostal;
	private String ville;
	private float loyer;
	private float provisionPourCharges;
	private String typeBien;
	private LocalDate dateConstruction;
	private float surfaceHabitable;
	private int nbPieces;
	private double pourcentageEntretienPartiesCommunes;
	private double pourcentageOrduresMenageres;
	private String numeroFiscal;
	private Integer derniereAnneeModifLoyer;

	/**
	 * Crée un objet de type BienLouable
	 * 
	 * @param idBien                              identifiant du bien louable
	 * @param idBat                               identifiant du batiment auquel il
	 *                                            appartient
	 * @param adresse                             adresse du bien louable
	 * @param codePostal                          code postal du bien louable
	 * @param ville                               ville du bien louable
	 * @param loyer                               montant du loyer du bien louable
	 * @param provisionPourCharges                montant de la provision pour
	 *                                            charges du bien louable
	 * @param typeBien                            type de bien louable (logement ou
	 *                                            garage)
	 * @param dateConstruction                    date de construction du bien
	 *                                            louable
	 * @param surfaceHabitable                    surface habitable en m2 du bien
	 *                                            louable
	 * @param nbPieces                            nombre de pièces du bien louable
	 * @param pourcentageEntretienPartiesCommunes pourcentage de part des factures
	 *                                            d'entretien des parties communes
	 *                                            du batiment
	 * @param pourcentageOrduresMenageres         pourcentage de part des factures
	 *                                            d'enlèvement des ordures ménagères
	 *                                            du batiment
	 * @param numeroFiscal                        numéro fiscal du bien louable
	 * @param derniereAnneeModifLoyer             dernière année de modification de
	 *                                            loyer (peut être null)
	 */
	public BienLouable(String idBien, String idBat, String adresse, String codePostal, String ville, float loyer,
			float provisionPourCharges, String typeBien, LocalDate dateConstruction, float surfaceHabitable,
			int nbPieces, double pourcentageEntretienPartiesCommunes, double pourcentageOrduresMenageres,
			String numeroFiscal, Integer derniereAnneeModifLoyer) {
		this.idBien = idBien;
		this.idBat = idBat;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
		this.loyer = loyer;
		this.provisionPourCharges = provisionPourCharges;
		this.typeBien = typeBien;
		this.dateConstruction = dateConstruction;
		this.surfaceHabitable = surfaceHabitable;
		this.nbPieces = nbPieces;
		this.pourcentageEntretienPartiesCommunes = pourcentageEntretienPartiesCommunes;
		this.pourcentageOrduresMenageres = pourcentageOrduresMenageres;
		this.numeroFiscal = numeroFiscal;
		this.derniereAnneeModifLoyer = derniereAnneeModifLoyer;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public float getLoyer() {
		return loyer;
	}

	public void setLoyer(float loyer) {
		this.loyer = loyer;
	}

	public float getProvisionPourCharges() {
		return provisionPourCharges;
	}

	public void setProvisionPourCharges(float provisionPourCharges) {
		this.provisionPourCharges = provisionPourCharges;
	}

	public String getIdBat() {
		return idBat;
	}

	public void setIdBat(String idBat) {
		this.idBat = idBat;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTypeBien() {
		return typeBien;
	}

	public void setTypeBien(String typeBien) {
		this.typeBien = typeBien;
	}

	public LocalDate getDateConstruction() {
		return dateConstruction;
	}

	public void setDateConstruction(LocalDate dateConstruction) {
		this.dateConstruction = dateConstruction;
	}

	public float getSurfaceHabitable() {
		return surfaceHabitable;
	}

	public void setSurfaceHabitable(float surfaceHabitable) {
		this.surfaceHabitable = surfaceHabitable;
	}

	public int getNbPieces() {
		return nbPieces;
	}

	public void setNbPieces(int nbPieces) {
		this.nbPieces = nbPieces;
	}

	public double getPourcentageEntretienPartiesCommunes() {
		return pourcentageEntretienPartiesCommunes;
	}

	public void setPourcentageEntretienPartiesCommunes(double pourcentageEntretienPartiesCommunes)
			throws IllegalArgumentException {
		if (pourcentageEntretienPartiesCommunes > 100 || pourcentageEntretienPartiesCommunes < 0) {
			throw new IllegalArgumentException("Le pourcentage d'entretien parties communes n'est pas bon !");
		}
		this.pourcentageEntretienPartiesCommunes = pourcentageEntretienPartiesCommunes;
	}

	public double getPourcentageOrduresMenageres() {
		return pourcentageOrduresMenageres;
	}

	public void setPourcentageOrduresMenageres(double pourcentageOrduresMenageres) throws IllegalArgumentException {
		if (pourcentageOrduresMenageres > 100 || pourcentageOrduresMenageres < 0) {
			throw new IllegalArgumentException("Le pourcentage d'ordures ménagères n'est pas bon !");
		}
		this.pourcentageOrduresMenageres = pourcentageOrduresMenageres;
	}

	public String getNumeroFiscal() {
		return numeroFiscal;
	}

	public void setNumeroFiscal(String numeroFiscal) throws IllegalArgumentException {
		if (numeroFiscal.length() != 12) {
			throw new IllegalArgumentException("La taille du numéro fiscal n'est pas valable !");
		}
		this.numeroFiscal = numeroFiscal;
	}

	public String getIdBien() {
		return idBien;
	}

	public Integer getDerniereAnneeModifLoyer() {
		return derniereAnneeModifLoyer;
	}

	public void setDerniereAnneeModifLoyer(int derniereAnneeModifLoyer) {
		this.derniereAnneeModifLoyer = derniereAnneeModifLoyer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idBien);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		BienLouable other = (BienLouable) obj;
		return Objects.equals(idBien, other.idBien);
	}

	@Override
	public String toString() {
		return "BienLouable [idBien=" + idBien + ", idBat=" + idBat + ", adresse=" + adresse + ", codePostal="
				+ codePostal + ", ville=" + ville + ", loyer=" + loyer + ", provisionPourCharges="
				+ provisionPourCharges + ", typeBien=" + typeBien + ", dateConstruction=" + dateConstruction
				+ ", surfaceHabitable=" + surfaceHabitable + ", nbPièces=" + nbPieces
				+ ", pourcentageEntretienPartiesCommunes=" + pourcentageEntretienPartiesCommunes
				+ ", pourcentageOrduresMénagères=" + pourcentageOrduresMenageres + ", numéroFiscal=" + numeroFiscal
				+ "]";
	}

}
