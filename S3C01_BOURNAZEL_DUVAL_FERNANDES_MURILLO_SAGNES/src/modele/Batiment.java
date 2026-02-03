package modele;

import java.time.LocalDate;
import java.util.Objects;

public class Batiment {

	private String idBat;
	private LocalDate dateConstruction;
	private String adresse;
	private String codePostal;
	private String ville;
	private String numFiscal;

	/**
	 * Crée un objet de type Batiment
	 * 
	 * @param idBat            identifiant du batiment
	 * @param dateConstruction date de construction du batiment
	 * @param adresse          adresse du batiment
	 * @param codePostal       code postal du batiment
	 * @param ville            ville du batiment
	 * @param numFiscal        numéro fiscal du batiment (si il y a lieu)
	 */
	public Batiment(String idBat, LocalDate dateConstruction, String adresse, String codePostal, String ville,
			String numFiscal) {
		this.idBat = idBat;
		this.dateConstruction = dateConstruction;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
		this.numFiscal = numFiscal;
	}

	public String getIdBat() {
		return idBat;
	}

	public void setIdBat(String idBat) throws IllegalArgumentException {
		if (idBat.length() != 10) {
			throw new IllegalArgumentException("L'identificateur du bâtiment n'est pas valable !");
		}
		this.idBat = idBat;
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

	public LocalDate getDateConstruction() {
		return dateConstruction;
	}

	public void setDateConstruction(LocalDate dateConstruction) {
		this.dateConstruction = dateConstruction;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getNumFiscal() {
		return numFiscal;
	}

	public void setNumFiscal(String numFiscal) {
		this.numFiscal = numFiscal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idBat);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Batiment other = (Batiment) obj;
		return Objects.equals(idBat, other.idBat);
	}

	@Override
	public String toString() {
		return "Bâtiment [id_Bat=" + idBat + ", date_Construction=" + dateConstruction + ", adresse=" + adresse + "]";
	}
}
