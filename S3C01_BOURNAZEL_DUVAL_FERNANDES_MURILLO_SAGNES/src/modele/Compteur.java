package modele;

import java.time.LocalDate;
import java.util.Objects;

public class Compteur {

	private String idBat;
	private String idBien;
	private String typeCompteur;
	private String idCompteur;
	private LocalDate dateInstallation;
	private Float indexDepart;

	/**
	 * Crée un objet de type Compteur
	 * 
	 * @param idBat            identifiant du batiment contenant le compteur
	 * @param idBien           identifiant du bien contenant le compteur
	 * @param typeCompteur     type de compteur (électricité, eau)
	 * @param idCompteur       identifiant du compteur
	 * @param dateInstallation date d'installation du compteur
	 * @param indexDepart      index de départ du compter (si le propriétaire veut
	 *                         rentrer des compteurs déjà existants ayant un certain
	 *                         index)
	 */
	public Compteur(String idBat, String idBien, String typeCompteur, String idCompteur, LocalDate dateInstallation,
			Float indexDepart) {
		this.idBat = idBat;
		this.idBien = idBien;
		this.typeCompteur = typeCompteur;
		this.idCompteur = idCompteur;
		this.dateInstallation = dateInstallation;
		this.indexDepart = indexDepart;
	}

	public String getTypeCompteur() {
		return typeCompteur;
	}

	public void setTypeCompteur(String typeCompteur) {
		this.typeCompteur = typeCompteur;
	}

	public String getIdCompteur() {
		return idCompteur;
	}

	public void setIdCompteur(String idCompteur) throws IllegalArgumentException {
		if (idCompteur.length() != 10) {
			throw new IllegalArgumentException("L'identificateur du relevé du compteur n'est pas valable !");
		}
		this.idCompteur = idCompteur;
	}

	public String getIdBat() {
		return idBat;
	}

	public String getIdBien() {
		return idBien;
	}

	public void setIdBat(String idBat) {
		this.idBat = idBat;
	}

	public void setIdBien(String idBien) {
		this.idBien = idBien;
	}

	public LocalDate getDateInstallation() {
		return dateInstallation;
	}

	public void setDateInstallation(LocalDate dateInstallation) {
		this.dateInstallation = dateInstallation;
	}

	public Float getIndexDepart() {
		return indexDepart;
	}

	public void setIndexDepart(Float indexDepart) {
		this.indexDepart = indexDepart;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCompteur);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Compteur other = (Compteur) obj;
		return Objects.equals(idCompteur, other.idCompteur);
	}

	@Override
	public String toString() {
		return "Compteur [bat=" + idBat + ", bien=" + idBien + ", typeCompteur=" + typeCompteur + ", idCompteur="
				+ idCompteur + "]";
	}
}
