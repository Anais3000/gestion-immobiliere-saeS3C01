package controleur;

import java.time.LocalDate;

import javax.swing.JComboBox;

/**
 * Classe pour stocker les méthodes "outil" qu'on réutilise dans plusieurs
 * classes comme par exemple des méthodes ou des constantes pour éviter les
 * répétitions
 */
public class Outils {

	public static final String ERREUR_STRING = "Erreur";
	public static final String ERREUR_FORMAT = "Erreur de format";
	public static final String ERREUR_EXCEPTION = "Erreur : ";
	public static final String CONFIRMATION_STRING = "Confirmation";
	public static final String INFORMATION_STRING = "Information";

	public static final String POLICE_TAHOMA = "Tahoma";
	public static final String POLICE_SANSSERIF = "SansSerif";

	private Outils() {
		// constructeur privé pour empêcher l'instanciation (demandé par sonarqube)
	}

	/**
	 * Parse une date au format string à LocalDate
	 * 
	 * @param txt date au format string
	 * @return une LocalDate correspondant à la date String
	 */
	public static LocalDate parseDateField(String txt) {
		txt = txt.trim();
		if (txt.isEmpty()) {
			return null;
		}
		try {
			String[] parts = txt.split("-");
			if (parts.length != 3) {
				return null;
			}

			int jour = Integer.parseInt(parts[0]);
			int mois = Integer.parseInt(parts[1]);
			int annee = Integer.parseInt(parts[2]);

			return LocalDate.of(annee, mois, jour);

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Permet de savoir si une combo box contient un certain élément
	 * 
	 * @param combo combobox voulue
	 * @param value élement recherché
	 * @return true si l'élément est présent dans la combo, false sinon
	 */
	public static boolean comboContient(JComboBox<?> combo, Object value) {
		if (value == null) {
			return false;
		}
		for (int i = 0; i < combo.getItemCount(); i++) {
			Object item = combo.getItemAt(i);
			if (item != null && item.equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Convertit une LocalDate en string de format "DD-MM-YYYY"
	 * 
	 * @param date date à convertir
	 * 
	 * @return String, conversion de la LocalDate en String
	 */
	public static String formaterDateDDMMYYY(LocalDate date) {
		String sInitial = date.toString();
		String s1 = sInitial.substring(0, 4);
		String s2 = sInitial.substring(5, 7);
		String s3 = sInitial.substring(8, 10);
		return s3 + "-" + s2 + "-" + s1;
	}

	/**
	 * Convertit un string "DD-MM-YYYY" en string "YYYY-MMM-DD"
	 * 
	 * @param sInitial string à convertir
	 * @return String, le string convertit
	 */
	public static String formaterDateYYYYMMDD(String sInitial) {
		String s1 = sInitial.substring(0, 2);
		String s2 = sInitial.substring(3, 5);
		String s3 = sInitial.substring(6, 10);
		return s3 + "-" + s2 + "-" + s1;
	}

	/**
	 * Permet de savoir si un string est composé de chiffres uniquement
	 * 
	 * @param input chaine de caractères ou tableau de chaines
	 * @return true si le string ne contient que des chiffres, false sinon
	 */
	public static boolean estStringDeChiffres(String... input) {
		for (String s : input) {
			char[] tab = s.toCharArray();
			for (char c : tab) {
				if (!Character.isDigit(c)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Vérifie qu'une string est de format "DD-MM-YYYY"
	 * 
	 * @param s String à vérifier
	 * @return true si le format attendu est respecté, false sinon
	 */
	public static boolean estStringFormatDDMMYYYY(String s) {
		if (s.length() != 10) {
			return false;
		}

		String s1 = s.substring(0, 2);
		String s2 = s.substring(3, 5);
		String s3 = s.substring(6, 10);

		int i1 = Integer.parseInt(s1);
		int i2 = Integer.parseInt(s2);
		int i3 = Integer.parseInt(s3);

		return estStringDeChiffres(s1, s2, s3) && i1 >= 1 && i1 <= 31 && i2 >= 1 && i2 <= 12 && i3 >= 0;
	}

}
