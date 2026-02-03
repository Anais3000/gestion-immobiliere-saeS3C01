package modele;

import java.util.Objects;

public class Organisme {

	private String numSiret;
	private String nom;
	private String adresse;
	private String ville;
	private String codePostal;
	private String mail;
	private String numTel;
	private String specialite;

	/**
	 * Crée un objet de type Organisme
	 * 
	 * @param numSiret   numéro de Siret de l'organisme
	 * @param nom        nom de l'organisme
	 * @param adresse    adresse de l'organisme
	 * @param ville      ville de l'organisme
	 * @param codePostal code postal de l'organisme
	 * @param mail       adresse électronique de l'organisme
	 * @param numTel     numéro de tétélphone de l'organisme
	 * @param specialite spécialité de l'organisme
	 */
	public Organisme(String numSiret, String nom, String adresse, String ville, String codePostal, String mail,
			String numTel, String specialite) {
		this.numSiret = numSiret;
		this.nom = nom;
		this.adresse = adresse;
		this.ville = ville;
		this.codePostal = codePostal;
		this.mail = mail;
		this.numTel = numTel;
		this.specialite = specialite;
	}

	public String getNumSIRET() {
		return numSiret;
	}

	public void setNumSIRET(String numSIRET) throws IllegalArgumentException {
		if (numSIRET.length() != 14 || !numSIRET.matches("\\d+")) { // \\d+ permet de vérifier que la chaîne de
																	// caractère ne contient que un ou plusieurs
																	// chiffres
			throw new IllegalArgumentException("Le numéro de SIRET n'est pas conforme !");
		}
		this.numSiret = numSIRET;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) throws IllegalArgumentException {
		if (!mail.contains("@") || (!mail.contains(".com") || !mail.contains(".fr"))) {
			throw new IllegalArgumentException("Le mail n'est donc pas valable");
		}
		this.mail = mail;
	}

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getNumTel() {
		return numTel;
	}

	public void setNumTel(String numTel) {
		if (numTel.length() != 10 || !numTel.matches("\\d+")) { // \\d+ permet de vérifier que la chaîne de caractère ne
			// contient que un ou plusieurs chiffres
			throw new IllegalArgumentException("Le numéro de téléphone n'est pas conforme !");
		}
		this.numTel = numTel;
	}

	@Override
	public int hashCode() {
		return Objects.hash(numSiret);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Organisme other = (Organisme) obj;
		return Objects.equals(numSiret, other.numSiret);
	}

	@Override
	public String toString() {
		return "Organisme [num_SIRET=" + numSiret + ", nom=" + nom + ", adresse=" + adresse + ", mail=" + mail
				+ ", tel=" + numTel + ", spécialité=" + specialite + "]";
	}

}
