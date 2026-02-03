package vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import controleur.GestionConnexion;

/**
 * Fenêtre interne de connexion à l'application. Permet à l'utilisateur de
 * saisir son login et mot de passe pour accéder au système.
 */
public class Connexion extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	// Variables Swing
	private JTextField textFieldLogin; // Champ de saisie du login
	private JPasswordField passwordField; // Champ de saisie du mot de passe

	// Contrôleurs
	private transient GestionConnexion gestionClic; // Contrôleur des événements des boutons

	/**
	 * Constructeur de la fenêtre de connexion. Initialise l'interface graphique
	 * avec les champs de saisie du login et mot de passe, ainsi que les boutons
	 * d'action.
	 */
	public Connexion() {

		this.gestionClic = new GestionConnexion(this);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new GridLayout(1, 2, 0, 0));

		setBounds(100, 100, 290, 164);
		getContentPane().setLayout(new BorderLayout(0, 0));

		// Panel central avec les champs de saisie
		JPanel panelChamps = new JPanel();
		getContentPane().add(panelChamps, BorderLayout.CENTER);

		JLabel lblLogin = new JLabel("Login : ");
		JLabel lblMotDePasse = new JLabel("Mot de passe :");

		textFieldLogin = new JTextField();
		textFieldLogin.setColumns(10);

		passwordField = new JPasswordField();

		// Configuration du GroupLayout pour les champs
		GroupLayout glPanelChamps = new GroupLayout(panelChamps);
		glPanelChamps.setHorizontalGroup(glPanelChamps.createParallelGroup(Alignment.LEADING).addGroup(glPanelChamps
				.createSequentialGroup().addGap(18)
				.addGroup(glPanelChamps.createParallelGroup(Alignment.LEADING).addComponent(lblMotDePasse)
						.addComponent(lblLogin, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(glPanelChamps.createParallelGroup(Alignment.LEADING, false).addComponent(textFieldLogin)
						.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
				.addContainerGap(18, Short.MAX_VALUE)));
		glPanelChamps.setVerticalGroup(glPanelChamps.createParallelGroup(Alignment.LEADING).addGroup(glPanelChamps
				.createSequentialGroup().addGap(28)
				.addGroup(glPanelChamps.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldLogin, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(glPanelChamps.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMotDePasse, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(30, Short.MAX_VALUE)));
		panelChamps.setLayout(glPanelChamps);

		// Panel du bas avec les boutons
		JPanel panelBoutons = new JPanel();
		getContentPane().add(panelBoutons, BorderLayout.SOUTH);

		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(this.gestionClic);
		panelBoutons.add(btnAnnuler);

		JButton btnSeConnecter = new JButton("Se connecter");
		btnSeConnecter.addActionListener(this.gestionClic);
		panelBoutons.add(btnSeConnecter);

	}

	/**
	 * Récupère la valeur du champ login.
	 * 
	 * @return le login saisi par l'utilisateur
	 */
	public String getTextFieldLogin() {
		return textFieldLogin.getText();
	}

	/**
	 * Récupère la valeur du champ login (alias de getTextFieldLogin()).
	 * 
	 * @return le login saisi par l'utilisateur
	 */
	public String getValeurChLogin() {
		return textFieldLogin.getText();
	}

	/**
	 * Récupère la valeur du champ mot de passe.
	 * 
	 * @return le mot de passe saisi par l'utilisateur
	 */
	public String getValeurPasswordField() {
		return new String(passwordField.getPassword());
	}
}
