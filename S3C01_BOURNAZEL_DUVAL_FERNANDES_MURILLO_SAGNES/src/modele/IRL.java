package modele;

import java.util.Objects;

public class IRL {

	private int annee;
	private int trimestre;
	private double valeur;

	/**
	 * Crée un objet de type IRL
	 * 
	 * @param annee     année de la valeur d'IRL
	 * @param trimestre trimestre de la valeur d'IRL
	 * @param valeur    valeur d'IRL
	 */
	public IRL(int annee, int trimestre, double valeur) {
		this.annee = annee;
		this.trimestre = trimestre;
		this.valeur = valeur;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public int getTrimestre() {
		return trimestre;
	}

	public void setTrimestre(int trimestre) {
		this.trimestre = trimestre;
	}

	public double getValeur() {
		return valeur;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
	}

	@Override
	public int hashCode() {
		return Objects.hash(annee, trimestre, valeur);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		IRL other = (IRL) obj;
		return annee == other.annee && trimestre == other.trimestre;
	}

	@Override
	public String toString() {
		return "IRL [année=" + annee + ", trimestre=" + trimestre + ", valeur=" + valeur + "]";
	}

}
