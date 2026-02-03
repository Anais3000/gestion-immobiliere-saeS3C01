package modele;

import java.util.Objects;

public class Garant {

	private String idGarant;
	private String adresse;
	private String mail;
	private String numTel;

	/**
	 * Crée un objet de type Garant
	 * 
	 * @param idGarant identifiant du garant
	 * @param adresse  adresse du garant
	 * @param mail     adresse électronique du garant
	 * @param tel      numéro de téléphone du garant
	 */
	public Garant(String idGarant, String adresse, String mail, String tel) {
		this.idGarant = idGarant;
		this.adresse = adresse;
		this.mail = mail;
		this.numTel = tel;
	}

	public String getIdGarant() {
		return idGarant;
	}

	public void setIdGarant(String idGarant) {
		this.idGarant = idGarant;
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
		if (!mail.contains("@") && (!mail.contains(".com") || !mail.contains(".fr"))) {
			throw new IllegalArgumentException("Le mail n'est donc pas valable");
		}
		this.mail = mail;
	}

	public String getTel() {
		return numTel;
	}

	public void setTel(String tel) throws IllegalArgumentException {
		if (tel.length() != 10) {
			throw new IllegalArgumentException("Le numéro de téléphone n'est pas de la bonne longueur !");
		}
		this.numTel = tel;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idGarant);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Garant other = (Garant) obj;
		return Objects.equals(idGarant, other.idGarant);
	}

	@Override
	public String toString() {
		return "Garant [id_Garant=" + idGarant + ", adresse=" + adresse + ", mail=" + mail + ", tel=" + numTel + "]";
	}
}
