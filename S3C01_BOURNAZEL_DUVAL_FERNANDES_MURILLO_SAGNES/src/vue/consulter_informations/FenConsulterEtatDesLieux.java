package vue.consulter_informations;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import controleur.consulter_informations.fen_bien_louable.GestionFenConsulterEtatDesLieux;
import modele.BienLouable;
import modele.Locataire;
import modele.Louer;

/**
 * Fenêtre de consultation d'un état des lieux (entrée ou sortie). Affiche les
 * informations du locataire, du bien louable et le contenu détaillé de l'état
 * des lieux dans une zone de texte non-éditable.
 */
public class FenConsulterEtatDesLieux extends JFrame {

	private static final long serialVersionUID = 1L;

	// Contrôleur
	private transient GestionFenConsulterEtatDesLieux gestionClic; // Contrôleur de la fenêtre

	/**
	 * Énumération définissant le type d'état des lieux : entrée ou sortie.
	 */
	public enum TypeEtat {
		ENTREE, SORTIE
	}

	/**
	 * Constructeur de la fenêtre de consultation d'état des lieux. Affiche les
	 * informations du bail, du locataire et du bien louable, ainsi que le contenu
	 * complet de l'état des lieux selon son type (entrée ou sortie).
	 * 
	 * @param locEnCours      le bail en cours
	 * @param typeEtatLieux   le type d'état des lieux (ENTREE ou SORTIE)
	 * @param bien            le bien louable concerné
	 * @param locataireActuel le locataire concerné
	 */
	public FenConsulterEtatDesLieux(Louer locEnCours, TypeEtat typeEtatLieux, BienLouable bien,
			Locataire locataireActuel) {

		gestionClic = new GestionFenConsulterEtatDesLieux(this);
		setBounds(100, 100, 800, 500);

		JPanel panelHaut = new JPanel();
		getContentPane().add(panelHaut, BorderLayout.NORTH);
		panelHaut.setLayout(new GridLayout(5, 2, 0, 0));

		JLabel lblEtatDesLieux = new JLabel("Etat des lieux " + typeEtatLieux.toString());
		lblEtatDesLieux.setFont(new Font("Tahoma", Font.BOLD, 20));
		panelHaut.add(lblEtatDesLieux);

		JPanel panel = new JPanel();
		panelHaut.add(panel);

		JPanel panel2 = new JPanel();
		panelHaut.add(panel2);

		JPanel panel3 = new JPanel();
		panelHaut.add(panel3);

		JLabel lblDate = new JLabel("Date");
		if (typeEtatLieux == TypeEtat.ENTREE) {
			lblDate.setText(locEnCours.getDateEtatDesLieuxEntree().toString());
		} else {
			lblDate.setText(locEnCours.getDateEtatDesLieuxSortie().toString());
		}
		lblDate.setHorizontalAlignment(SwingConstants.LEFT);
		panelHaut.add(lblDate);

		JLabel lblIdLocataire = new JLabel("Id Locataire");
		lblIdLocataire.setText(locataireActuel.getIdLocataire());
		lblIdLocataire.setHorizontalAlignment(SwingConstants.RIGHT);
		panelHaut.add(lblIdLocataire);

		JLabel lblIDBienLouable = new JLabel("Id bien louable");
		lblIDBienLouable.setText(locEnCours.getIdBienLouable());
		lblIDBienLouable.setHorizontalAlignment(SwingConstants.LEFT);
		lblIDBienLouable.setVerticalAlignment(SwingConstants.BOTTOM);
		lblIDBienLouable.setAlignmentX(500.0f);
		panelHaut.add(lblIDBienLouable);

		JLabel lblNomLocataire = new JLabel("Nom Locataire");
		lblNomLocataire.setText(locataireActuel.getNom());
		lblNomLocataire.setHorizontalAlignment(SwingConstants.RIGHT);
		panelHaut.add(lblNomLocataire);

		JLabel lblAdresse = new JLabel("Adresse");
		lblAdresse.setText(bien.getAdresse());
		lblAdresse.setHorizontalAlignment(SwingConstants.LEFT);
		panelHaut.add(lblAdresse);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JTextArea txtrDetails = new JTextArea();
		if (typeEtatLieux == TypeEtat.ENTREE) {
			txtrDetails.setText(locEnCours.getDetailsEtatDesLieuxEntree());
		} else {
			txtrDetails.setText(locEnCours.getDetailsEtatDesLieuxSortie());
		}
		txtrDetails.setEditable(false);
		scrollPane.setViewportView(txtrDetails);
		txtrDetails.setLineWrap(true);
		txtrDetails.setWrapStyleWord(true);

		JPanel panelSud = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelSud.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		getContentPane().add(panelSud, BorderLayout.SOUTH);

		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(gestionClic);
		panelSud.add(btnQuitter);

	}

}
