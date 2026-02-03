package modele;

import java.time.LocalDate;
import java.util.Objects;

public class ReleveCompteur {

	private String idCompteur;
	private LocalDate dateReleve;
	private double indexCompteur;
	private double prixParUnite;
	private double partieFixe;
	private double ancienIndex;

	/**
	 * Crée un objet de type ReleveCompteur
	 * 
	 * @param dateReleve   date du relevé
	 * @param index        index du compteur lors du relevé
	 * @param prixParUnite prix par unité lors du relevé
	 * @param partieFixe   prix de la partie fixe lors
	 * @param idCompteur   identifiant du compteur relevé
	 * @param ancienIndex  ancien index du compteur lors du relevé
	 * @throws IllegalArgumentException
	 */
	public ReleveCompteur(LocalDate dateReleve, double index, double prixParUnite, double partieFixe, String idCompteur,
			double ancienIndex) throws IllegalArgumentException {
		if (idCompteur == null) {
			throw new IllegalArgumentException("Vous devez renseignez le compteur associé !");
		}
		this.dateReleve = dateReleve;
		this.indexCompteur = index;
		this.prixParUnite = prixParUnite;
		this.partieFixe = partieFixe;
		this.idCompteur = idCompteur;
		this.ancienIndex = ancienIndex;
	}

	public String getIdCompteur() {
		return idCompteur;
	}

	public LocalDate getDateReleve() {
		return dateReleve;
	}

	public void setDateReleve(LocalDate dateReleve) {
		this.dateReleve = dateReleve;
	}

	public double getPrixParUnite() {
		return prixParUnite;
	}

	public void setPrixParUnite(double prixParUnite) {
		this.prixParUnite = prixParUnite;
	}

	public void setIdCompteur(String idCompteur) {
		this.idCompteur = idCompteur;
	}

	public double getIndexCompteur() {
		return indexCompteur;
	}

	public void setIndexCompteur(double index) {
		this.indexCompteur = index;
	}

	public double getPartieFixe() {
		return partieFixe;
	}

	public void setPartieFixe(double partieFixe) {
		this.partieFixe = partieFixe;
	}

	public double getAncienIndex() {
		return ancienIndex;
	}

	public void setAncienIndex(double ancienIndex) {
		this.ancienIndex = ancienIndex;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateReleve, idCompteur);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		ReleveCompteur other = (ReleveCompteur) obj;
		return Objects.equals(dateReleve, other.dateReleve) && Objects.equals(idCompteur, other.idCompteur);
	}

	@Override
	public String toString() {
		return "RelevéCompteur [dateRelevé=" + dateReleve + ", idCompteur=" + idCompteur + ", index=" + indexCompteur
				+ ", prixParUnité=" + prixParUnite + ", partieFixe=" + partieFixe + "]";
	}
}
