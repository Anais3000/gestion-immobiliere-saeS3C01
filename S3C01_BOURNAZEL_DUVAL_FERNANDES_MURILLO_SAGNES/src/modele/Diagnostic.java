package modele;

import java.time.LocalDate;
import java.util.Objects;

public class Diagnostic {

	private String libelle;
	private LocalDate dateDebut;
	private LocalDate dateFin;
	private String details;
	private String idBien;

	/**
	 * Crée un objet de type diagnostic
	 * 
	 * @param libelle   libellé du diagnostic
	 * @param dateDebut date de début de la période couverte
	 * @param dateFin   date de fin de la période couverte
	 * @param details   détails du diagnostic (commentaires)
	 * @param idBien    id du bien louable concerné
	 */
	public Diagnostic(String libelle, LocalDate dateDebut, LocalDate dateFin, String details, String idBien) {
		this.libelle = libelle;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.details = details;
		this.idBien = idBien;
	}

	public String getIdBien() {
		return idBien;
	}

	public void setIdBien(String idBien) {
		this.idBien = idBien;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Diagnostics [libellé=" + libelle + ", date_Début=" + dateDebut + ", date_Fin=" + dateFin + ", détails="
				+ details + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateDebut, libelle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Diagnostic other = (Diagnostic) obj;
		return Objects.equals(dateDebut, other.dateDebut) && Objects.equals(libelle, other.libelle);
	}

}
