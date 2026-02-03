package modele;

import java.util.Objects;

public class Assurance {

	private String numPoliceAssurance;
	private String typeContrat;
	private int anneeCouverture;
	private double montantPaye;
	private String idBat;

	/**
	 * Crée un objet de type assurance
	 * 
	 * @param numPoliceAssurance numéro de police d'assurance
	 * @param typeContrat        type de contrat (propriétaire ou aide juridique)
	 * @param anneeCouverture    année de couverture
	 * @param montantPaye        montant payé sur l'année de couverture
	 * @param bat                identifiant du batiment concerné par l'assurance
	 */
	public Assurance(String numPoliceAssurance, String typeContrat, int anneeCouverture, double montantPaye,
			String bat) {
		this.numPoliceAssurance = numPoliceAssurance;
		this.typeContrat = typeContrat;
		this.anneeCouverture = anneeCouverture;
		this.montantPaye = montantPaye;
		this.idBat = bat;
	}

	public String getBat() {
		return idBat;
	}

	public void setBat(String bat) {
		this.idBat = bat;
	}

	public String getNumPoliceAssurance() {
		return numPoliceAssurance;
	}

	public void setNumPoliceAssurance(String numPoliceAssurance) {
		this.numPoliceAssurance = numPoliceAssurance;
	}

	public String getTypeContrat() {
		return typeContrat;
	}

	public void setTypeContrat(String typeContrat) {
		this.typeContrat = typeContrat;
	}

	public int getAnneeCouverture() {
		return anneeCouverture;
	}

	public void setAnneeCouverture(int anneeCouverture) {
		this.anneeCouverture = anneeCouverture;
	}

	public double getMontantPaye() {
		return montantPaye;
	}

	public void setMontantPaye(double montantPaye) {
		this.montantPaye = montantPaye;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numPoliceAssurance);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Assurance other = (Assurance) obj;
		return Objects.equals(numPoliceAssurance, other.numPoliceAssurance);
	}

	@Override
	public String toString() {
		return "Assurance [num_Police_Assurance=" + numPoliceAssurance + ", type_Contrat=" + typeContrat
				+ ", année_Couverture=" + anneeCouverture + ", montant_Payé=" + montantPaye + "]";
	}

}
