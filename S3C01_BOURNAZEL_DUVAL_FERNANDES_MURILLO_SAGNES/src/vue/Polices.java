package vue;

import java.awt.Font;

// Cette énumération sert à mettre en commun nos polices d'écriture histoire que ce soit un peu cohérent
public enum Polices {
	TITRE(new Font("Arial", Font.BOLD | Font.ITALIC, 20));

	private final Font font;

	private Polices(Font font) {
		this.font = font;
	}

	public Font getFont() {
		return font;
	}

	// Pour utiliser une police : Polices.TITRE.getFont()
}
