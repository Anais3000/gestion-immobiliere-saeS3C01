package modele;

import java.time.LocalDateTime;

public class Historique {
	private LocalDateTime date;
	private String action;
	private String details;

	/**
	 * Crée un objet de type Historique
	 * 
	 * @param date    date de l'action effectuée
	 * @param action  intitulé de l'action effectuée
	 * @param details détails de l'action effectuée
	 */
	public Historique(LocalDateTime date, String action, String details) {
		this.date = date;
		this.action = action;
		this.details = details;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}