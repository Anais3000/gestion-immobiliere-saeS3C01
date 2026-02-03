package modele;

import java.util.Objects;

public class Necessiter {

	private String idBien;
	private String idIntervention;

	/**
	 * Crée un objet de type Necessiter (pour associer une intervention à un bien
	 * louable)
	 * 
	 * @param idBien         identifiant du bien louable
	 * @param idIntervention identifiant de l'intervention sur le bien louable
	 */
	public Necessiter(String idBien, String idIntervention) {
		super();
		this.idBien = idBien;
		this.idIntervention = idIntervention;
	}

	public String getIdBien() {
		return idBien;
	}

	public void setIdBien(String idBien) {
		this.idBien = idBien;
	}

	public String getIdIntervention() {
		return idIntervention;
	}

	public void setIdIntervention(String idIntervention) {
		this.idIntervention = idIntervention;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idBien, idIntervention);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Necessiter other = (Necessiter) obj;
		return Objects.equals(idBien, other.idBien) && Objects.equals(idIntervention, other.idIntervention);
	}

}
