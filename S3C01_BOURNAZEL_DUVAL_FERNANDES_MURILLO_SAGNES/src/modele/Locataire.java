package modele;

import java.time.LocalDate;
import java.util.Objects;

public class Locataire {
	private String idLocataire; // clé priamaire, que le prop choisi
	private String nom;
	private String prenom;
	private String numTelephone;
	private String mail;
	private LocalDate dateNaissance;
	private String villeNaissance;
	private String adresse;
	private String codePostal;
	private String ville;

	/**
	 * Crée un objet de type Locataire
	 * 
	 * @param idLocataire    identifiant du locataire
	 * @param nom            nom du locataire
	 * @param prenom         prénom du locataire
	 * @param numTelephone   numéro de téléphone
	 * @param mail           adresse électronique du locataire
	 * @param dateNaissance  date de naissance du locataire
	 * @param villeNaissance ville de naissance du locataire
	 * @param adresse        adresse du locataire
	 * @param codePostal     code postal du locataire
	 * @param ville          ville du locataire
	 */
	public Locataire(String idLocataire, String nom, String prenom, String numTelephone, String mail,
			LocalDate dateNaissance, String villeNaissance, String adresse, String codePostal, String ville) {
		this.idLocataire = idLocataire;
		this.nom = nom;
		this.prenom = prenom;
		this.numTelephone = numTelephone;
		this.mail = mail;
		this.dateNaissance = dateNaissance;
		this.villeNaissance = villeNaissance;
		this.adresse = adresse;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public String getIdLocataire() {
		return idLocataire;
	}

	public void setIdLocataire(String idLocataire) {
		this.idLocataire = idLocataire;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNumTelephone() {
		return numTelephone;
	}

	public void setNumTelephone(String numTelephone) {
		this.numTelephone = numTelephone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getVilleNaissance() {
		return villeNaissance;
	}

	public void setVilleNaissance(String villeNaissance) {
		this.villeNaissance = villeNaissance;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
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

	@Override
	public String toString() {
		return "Locataire [idLocataire=" + idLocataire + ", nom=" + nom + ", prénom=" + prenom + ", numTelephone="
				+ numTelephone + ", mail=" + mail + ", dateNaissance=" + dateNaissance + ", villeNaissance="
				+ villeNaissance + ", adresse=" + adresse + ", codePostal=" + codePostal + ", ville=" + ville + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idLocataire);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Locataire other = (Locataire) obj;
		return Objects.equals(idLocataire, other.idLocataire);
	}

}
