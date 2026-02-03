package rapport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class CreerQuittance {

	private static final String FORMAT_DATE = "dd/MM/yyyy";
	private static final String FORMAT_EUROS = "%.2f €";

	private CreerQuittance() {
		// constructeur vide pour empêcher son appel
	}

	/**
	 * Méthode statique permettant de générer une quittance de loyer
	 * 
	 * @param nomBailleur      nom du bailleur
	 * @param adresseBailleur  adresse du bailleur
	 * @param nomLocataire     nom du locataire
	 * @param adresseLocataire adresse du locataire
	 * @param codePostal       code postal du bien
	 * @param ville            ville du bien
	 * @param dateQuittance    date d'émission de la quittance
	 * @param paiement         montant total du paiement
	 * @param loyer            montant du loyer
	 * @param charge           montant de la provision pour charge
	 * @param cheminFichier    chemin du fichier à générer
	 * 
	 * @throws IOException
	 */
	public static void creerQuittance(String nomBailleur, String adresseBailleur, String nomLocataire,
			String adresseLocataire, String codePostal, String ville, LocalDate dateQuittance, Float paiement,
			Float loyer, Float charge, String cheminFichier) throws IOException {

		XWPFDocument doc = new XWPFDocument();

		// TITRE
		addPara(doc, "QUITTANCE DE LOYER", true, 16, ParagraphAlignment.CENTER);

		LocalDate debutMois = dateQuittance.withDayOfMonth(1);
		LocalDate finMois = dateQuittance.withDayOfMonth(dateQuittance.lengthOfMonth());

		addPara(doc,
				"Quittance de loyer pour la période du " + debutMois.format(DateTimeFormatter.ofPattern(FORMAT_DATE))
						+ " au " + finMois.format(DateTimeFormatter.ofPattern(FORMAT_DATE)),
				false, 11, ParagraphAlignment.CENTER);

		addBlank(doc);

		// BAILLEUR / LOCATAIRE
		XWPFTable tableIdentite = doc.createTable(1, 2);
		tableIdentite.setWidth("100%");

		tableIdentite.getRow(0).getCell(0).setText("BAILLEUR :\n\n" + nomBailleur + ", \n" + adresseBailleur);

		tableIdentite.getRow(0).getCell(1)
				.setText("LOCATAIRE :\n\n" + nomLocataire + ", \n" + adresseLocataire + " " + codePostal + " " + ville);

		addBlank(doc);

		// ADRESSE LOCATION
		XWPFTable tableAdresse = doc.createTable(1, 1);
		tableAdresse.setWidth("100%");

		tableAdresse.getRow(0).getCell(0)
				.setText("ADRESSE DE LA LOCATION :\n\n" + adresseLocataire + " " + codePostal + " " + ville);

		addBlank(doc);

		// TABLEAU MONTANTS
		float montantLoyer = loyer;
		float montantCharges = charge;
		float montantPaye = paiement;
		float ajustement = montantPaye - (montantLoyer + montantCharges);

		float total = montantLoyer + montantCharges + ajustement;

		XWPFTable tableMontant = doc.createTable();

		// Header
		XWPFTableRow header = tableMontant.getRow(0);
		header.getCell(0).setText("LIBELLÉ");
		header.addNewTableCell().setText("MONTANT");

		// Loyer
		XWPFTableRow rowLoyer = tableMontant.createRow();
		rowLoyer.getCell(0).setText("Loyer (hors charges)");
		rowLoyer.getCell(1).setText(String.format(FORMAT_EUROS, montantLoyer));

		// Charges
		XWPFTableRow rowCharges = tableMontant.createRow();
		rowCharges.getCell(0).setText("Provision pour charges");
		rowCharges.getCell(1).setText(String.format(FORMAT_EUROS, montantCharges));

		// Ajustement loyer
		XWPFTableRow rowAjustement = tableMontant.createRow();
		rowAjustement.getCell(0).setText("Ajustement de loyer");
		rowAjustement.getCell(1).setText(String.format(FORMAT_EUROS, ajustement));

		// Total
		XWPFTableRow rowTotal = tableMontant.createRow();
		rowTotal.getCell(0).setText("TOTAL");
		rowTotal.getCell(1).setText(String.format(FORMAT_EUROS, total));

		addBlank(doc);

		// DATE + SIGNATURE
		addPara(doc, "Fait à " + ville + ", le " + LocalDate.now().format(DateTimeFormatter.ofPattern(FORMAT_DATE)),
				false, 11, ParagraphAlignment.RIGHT);

		addBlank(doc);

		addPara(doc, "Signature :", false, 11, null);

		// PIED DE PAGE
		XWPFFooter footer = doc.createFooter(HeaderFooterType.DEFAULT);
		XWPFParagraph footerPara = footer.createParagraph();
		footerPara.setAlignment(ParagraphAlignment.CENTER);
		footerPara.createRun().setText("Gestion locative");

		// ÉCRITURE FICHIER
		FileOutputStream out = new FileOutputStream(cheminFichier);
		doc.write(out);

		out.close();

		doc.close();
	}

	// METHODES UTILITAIRES

	/**
	 * Permet de creer un paragraphe avec un style choisit
	 * 
	 * @param doc   document en question
	 * @param text  texte à insérer dans le paragraphe
	 * @param bold  true si gras, false sinon
	 * @param size  taille du texte
	 * @param align paramètre d'alignement du paragraphe sur le document
	 */
	private static void addPara(XWPFDocument doc, String text, boolean bold, int size, ParagraphAlignment align) {
		XWPFParagraph p = doc.createParagraph();
		if (align != null) {
			p.setAlignment(align);
		}
		XWPFRun run = p.createRun();
		run.setBold(bold);
		run.setFontSize(size);
		run.setText(text);
	}

	/**
	 * Permet d'ajouter un espace sur le document
	 * 
	 * @param doc document en question
	 */
	private static void addBlank(XWPFDocument doc) {
		XWPFParagraph p = doc.createParagraph();
		p.createRun().addBreak();
	}
}
