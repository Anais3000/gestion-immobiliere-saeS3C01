package vue.ajouter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controleur.Outils;
import controleur.ajouter.GestionFenAjouterInterventionBatiment;
import vue.consulter_informations.FenBatimentInformations;

public class FenAjouterInterventionBatiment extends JFrame {

	// Variables Swing
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // Panel racine
	private JTextField textFieldIdentifiantIntervention; // Champ de saisie de l'identifiant de l'intervention
	private JTextField textFieldIntituleIntervention; // Champ de saisie de l'intitulé/description
	private JTextField textFieldNumFactureIntervention; // Champ de saisie du numéro de facture (active montant et date)
	private JTextField textFieldDateAcompteIntervention; // Champ de saisie de la date d'acompte (active montant
															// acompte)
	private JTextField textFieldDateFactureIntervention; // Champ de saisie de la date de facture
	private JTextField textFieldNumeroDevisIntervention; // Champ de saisie du numéro de devis (active montant devis)
	private JTextField textFieldDateIntervention; // Champ de saisie de la date de l'intervention
	private JTextField textFieldMontantDevitIntervention; // Champ de saisie du montant du devis
	private JTextField textFieldMontantFactureIntervention; // Champ de saisie du montant de la facture
	private JTextField textFieldAcompteIntervention; // Champ de saisie du montant de l'acompte
	private JTextField textFieldMontantNonDeductibleIntervention; // Champ de saisie du montant non déductible
	private JTextField textFieldReductionIntervention; // Champ de saisie de la réduction
	private JTextField textFieldIDBatiment; // Champ affichant l'ID du bâtiment (non modifiable)
	private JComboBox<String> comboBoxOrganismes; // ComboBox pour sélectionner l'organisme
	private JRadioButton rdbtnOrdures; // Bouton radio pour catégorie "Ordures"
	private JRadioButton rdbtnEntretien; // Bouton radio pour catégorie "Entretien"
	private JRadioButton rdbtnAucun; // Bouton radio pour "Aucune catégorie"

	// Contrôleur
	private transient GestionFenAjouterInterventionBatiment gestionClic; // Contrôleur pour gérer les événements de
																			// cette fenêtre

	// Variables de gestion
	private FenBatimentInformations fenAncestor; // La fenêtre parente (informations du bâtiment)

	// Constantes
	private static final String OPTIONNEL = "(optionnel)"; // Texte placeholder pour les champs optionnels

	/**
	 * Construit la page Ajouter Intervention Batiment qui permet d'ajouter une
	 * nouvelle intervention sur un bâtiment Ce constructeur initialise les champs à
	 * remplir suivants : L'identifiant et l'intitulé de l'intervention Les dates
	 * (intervention, facture, acompte) et numéros (facture, devis) Les montants
	 * (devis, facture, acompte, non déductible, réduction) La sélection de
	 * l'organisme et la catégorisation (ordures/entretien/aucun) Certains champs
	 * sont dynamiquement activés/désactivés selon les saisies
	 *
	 * @param idBat l'identifiant du bâtiment concerné par l'intervention
	 * @param fen   la fenêtre parente FenBatimentInformations qui sera mise à jour
	 *              après l'ajout
	 */
	public FenAjouterInterventionBatiment(String idBat, FenBatimentInformations fen) {
		// Affectation des variables
		this.fenAncestor = fen;

		// Configuration de base de la fenêtre
		setBounds(100, 100, 571, 613);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		// Panel titre
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 10));

		JLabel lblLabelTitreAjouterInterventionBatiment = new JLabel("Ajouter une Intervention Batiment");
		lblLabelTitreAjouterInterventionBatiment.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabelTitreAjouterInterventionBatiment.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 18));
		panel.add(lblLabelTitreAjouterInterventionBatiment, BorderLayout.NORTH);

		// Panel corps (contient le formulaire et les boutons)
		JPanel panelBorder = new JPanel();
		contentPane.add(panelBorder, BorderLayout.CENTER);
		panelBorder.setLayout(new BorderLayout(0, 0));

		// Panel formulaire de saisie
		JPanel panelGridWest = new JPanel();
		panelGridWest.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 9));
		panelBorder.add(panelGridWest, BorderLayout.WEST);
		panelGridWest.setLayout(new GridLayout(17, 1, 0, 10));

		JPanel panelGridCenter = new JPanel();
		panelBorder.add(panelGridCenter, BorderLayout.CENTER);
		panelGridCenter.setLayout(new GridLayout(17, 0, 0, 10));

		JLabel lblIdentifiantIntervention = new JLabel("Identifiant :");
		lblIdentifiantIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblIdentifiantIntervention);

		textFieldIdentifiantIntervention = new JTextField();
		panelGridCenter.add(textFieldIdentifiantIntervention);
		textFieldIdentifiantIntervention.setColumns(10);

		JLabel lblIdentifiantIntituleIntervention = new JLabel("Intitulé :");
		lblIdentifiantIntituleIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblIdentifiantIntituleIntervention);

		textFieldIntituleIntervention = new JTextField();
		panelGridCenter.add(textFieldIntituleIntervention);
		textFieldIntituleIntervention.setColumns(10);

		JLabel lblDateIntervention = new JLabel("Date Intervention :");
		lblDateIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblDateIntervention);

		textFieldDateIntervention = new JTextField();
		textFieldDateIntervention.setText("DD-MM-AAAA");
		panelGridCenter.add(textFieldDateIntervention);
		textFieldDateIntervention.setColumns(10);

		JLabel lblNumFactureIntervention = new JLabel("Numero Facture :");
		lblNumFactureIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblNumFactureIntervention);

		textFieldNumFactureIntervention = new JTextField();
		textFieldNumFactureIntervention.setText(OPTIONNEL);
		panelGridCenter.add(textFieldNumFactureIntervention);
		textFieldNumFactureIntervention.setColumns(10);

		// DocumentListener pour activer/désactiver les champs montant et date de
		// facture
		// en fonction de la présence d'un numéro de facture
		textFieldNumFactureIntervention.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				numFactureChangedRemoveInsertUpdate();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				numFactureChangedRemoveInsertUpdate();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				numFactureChangedRemoveInsertUpdate();
			}
		});

		JLabel lblMontantFactureIntervention = new JLabel("Montant Facture :");
		lblMontantFactureIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblMontantFactureIntervention);

		textFieldMontantFactureIntervention = new JTextField();
		textFieldMontantFactureIntervention.setText(OPTIONNEL);
		textFieldMontantFactureIntervention.setEnabled(false);
		panelGridCenter.add(textFieldMontantFactureIntervention);
		textFieldMontantFactureIntervention.setColumns(10);

		JLabel lblDateFactureIntervention = new JLabel("Date Facture :");
		lblDateFactureIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblDateFactureIntervention);

		textFieldDateFactureIntervention = new JTextField();
		textFieldDateFactureIntervention.setText("DD-MM-AAAA (optionnel)");
		textFieldDateFactureIntervention.setEnabled(false);
		panelGridCenter.add(textFieldDateFactureIntervention);
		textFieldDateFactureIntervention.setColumns(10);

		JLabel lblDateAcompteIntervention = new JLabel("Date Acompte :");
		lblDateAcompteIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblDateAcompteIntervention);

		textFieldDateAcompteIntervention = new JTextField();
		textFieldDateAcompteIntervention.setText("DD-MM-AAAA (optionnel)");
		panelGridCenter.add(textFieldDateAcompteIntervention);
		textFieldDateAcompteIntervention.setColumns(10);

		textFieldDateAcompteIntervention.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				acompteChangedRemoveInsertUpdate();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				acompteChangedRemoveInsertUpdate();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				acompteChangedRemoveInsertUpdate();
			}
		});

		JLabel lblAcompteIntervention = new JLabel("Acompte :");
		lblAcompteIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblAcompteIntervention);

		textFieldAcompteIntervention = new JTextField();
		textFieldAcompteIntervention.setText(OPTIONNEL);
		textFieldAcompteIntervention.setEnabled(false);
		panelGridCenter.add(textFieldAcompteIntervention);
		textFieldAcompteIntervention.setColumns(10);

		JLabel lblNumeroDevisIntervention = new JLabel("Numero Devis :");
		lblNumeroDevisIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblNumeroDevisIntervention);

		textFieldNumeroDevisIntervention = new JTextField();
		textFieldNumeroDevisIntervention.setText(OPTIONNEL);
		panelGridCenter.add(textFieldNumeroDevisIntervention);
		textFieldNumeroDevisIntervention.setColumns(10);

		textFieldNumeroDevisIntervention.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (!textFieldNumeroDevisIntervention.getText().isEmpty()) {
					textFieldMontantDevitIntervention.setEnabled(true);
				} else {
					textFieldMontantDevitIntervention.setEnabled(false);
					textFieldMontantDevitIntervention.setText("");
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (!textFieldNumeroDevisIntervention.getText().isEmpty()) {
					textFieldMontantDevitIntervention.setEnabled(true);
				} else {
					textFieldMontantDevitIntervention.setEnabled(false);
					textFieldMontantDevitIntervention.setText("");
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (!textFieldNumeroDevisIntervention.getText().isEmpty()) {
					textFieldMontantDevitIntervention.setEnabled(true);
				} else {
					textFieldMontantDevitIntervention.setEnabled(false);
					textFieldMontantDevitIntervention.setText("");
				}
			}
		});

		JLabel lblMontantDevisIntervention = new JLabel("Montant Devis :");
		lblMontantDevisIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblMontantDevisIntervention);

		textFieldMontantDevitIntervention = new JTextField();
		textFieldMontantDevitIntervention.setText(OPTIONNEL);
		textFieldMontantDevitIntervention.setEnabled(false);
		panelGridCenter.add(textFieldMontantDevitIntervention);
		textFieldMontantDevitIntervention.setColumns(10);

		JLabel lblMontantNonDeductibleIntervention = new JLabel("Montant Non Deductible* :");
		lblMontantNonDeductibleIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblMontantNonDeductibleIntervention);

		textFieldMontantNonDeductibleIntervention = new JTextField();
		textFieldMontantNonDeductibleIntervention.setText(OPTIONNEL);
		textFieldMontantNonDeductibleIntervention.setToolTipText("");
		textFieldMontantNonDeductibleIntervention.setColumns(10);
		panelGridCenter.add(textFieldMontantNonDeductibleIntervention);

		JLabel lblReductionIntervention = new JLabel("Réduction :");
		lblReductionIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblReductionIntervention);

		textFieldReductionIntervention = new JTextField();
		textFieldReductionIntervention.setText(OPTIONNEL);
		textFieldReductionIntervention.setToolTipText("");
		textFieldReductionIntervention.setColumns(10);
		panelGridCenter.add(textFieldReductionIntervention);

		JLabel lblIdBatimentIntervention = new JLabel("ID Batiment :");
		lblIdBatimentIntervention.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblIdBatimentIntervention);

		textFieldIDBatiment = new JTextField();
		textFieldIDBatiment.setText(idBat);
		textFieldIDBatiment.setEditable(false);
		textFieldIDBatiment.setToolTipText("");
		textFieldIDBatiment.setColumns(10);
		panelGridCenter.add(textFieldIDBatiment);

		JLabel lblNomOrganisme = new JLabel("Nom de l'organisme :");
		lblNomOrganisme.setFont(new Font(Outils.POLICE_TAHOMA, Font.PLAIN, 13));
		panelGridWest.add(lblNomOrganisme);

		comboBoxOrganismes = new JComboBox<>();
		panelGridCenter.add(comboBoxOrganismes);

		JLabel lblOrdures = new JLabel("Ordures :");
		lblOrdures.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelGridWest.add(lblOrdures);

		JLabel lblEntretien = new JLabel("Entretien : ");
		lblEntretien.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelGridWest.add(lblEntretien);

		JLabel lblAucun = new JLabel("Aucun : ");
		lblAucun.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelGridWest.add(lblAucun);

		rdbtnOrdures = new JRadioButton("Cochez si l'intervention vise les ordures ménagères");
		panelGridCenter.add(rdbtnOrdures);

		rdbtnEntretien = new JRadioButton("Cochez si l'intervention vise l'entretien des parties communes");
		panelGridCenter.add(rdbtnEntretien);

		rdbtnAucun = new JRadioButton("Cochez si l'intervention ne concerne aucun des deux");
		panelGridCenter.add(rdbtnAucun);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnOrdures);
		group.add(rdbtnEntretien);
		group.add(rdbtnAucun);

		// Instanciation du contrôleur
		this.gestionClic = new GestionFenAjouterInterventionBatiment(this, idBat);

		// Panel boutons
		JPanel panelBorderBoutons = new JPanel();
		panelBorder.add(panelBorderBoutons, BorderLayout.SOUTH);
		panelBorderBoutons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnValiderAjoutIB = new JButton("Valider");
		btnValiderAjoutIB.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnValiderAjoutIB);
		btnValiderAjoutIB.addActionListener(this.gestionClic);

		JButton btnAnnulerAjoutIB = new JButton("Annuler");
		btnAnnulerAjoutIB.setFont(new Font(Outils.POLICE_TAHOMA, Font.BOLD, 13));
		panelBorderBoutons.add(btnAnnulerAjoutIB);
		btnAnnulerAjoutIB.addActionListener(this.gestionClic);

		JLabel lblAsterisque = new JLabel(
				"* :  dépenses de consommables (et non le matériel durable) déductibles des impôts.");
		contentPane.add(lblAsterisque, BorderLayout.SOUTH);
	}

	/**
	 * Remplit la ComboBox des organismes avec les noms disponibles. Appelée par le
	 * contrôleur pour charger les organismes depuis la base.
	 * 
	 * @param noms la liste des noms d'organismes
	 */
	public void afficherOrganismes(List<String> noms) {
		for (String nom : noms) {
			comboBoxOrganismes.addItem(nom);
		}
	}

	/**
	 * Retourne la ComboBox de sélection d'organisme pour pouvoir l'utiliser dans
	 * les gestions.
	 * 
	 * @return la JComboBox contenant les organismes
	 */
	public JComboBox<String> getComboBoxOrganismes() {
		return comboBoxOrganismes;
	}

	/**
	 * Modifie la ComboBox de sélection d'organisme.
	 * 
	 * @param comboBoxOrganismes la nouvelle JComboBox d'organismes
	 */
	public void setComboBoxOrganismes(JComboBox<String> comboBoxOrganismes) {
		this.comboBoxOrganismes = comboBoxOrganismes;
	}

	/**
	 * Retourne le champ identifiant de l'intervention pour pouvoir l'utiliser dans
	 * les gestions.
	 * 
	 * @return le JTextField de l'identifiant
	 */
	public JTextField getTextFieldIdentifiantIntervention() {
		return textFieldIdentifiantIntervention;
	}

	/**
	 * Retourne le champ intitulé de l'intervention pour pouvoir l'utiliser dans les
	 * gestions.
	 * 
	 * @return le JTextField de l'intitulé
	 */
	public JTextField getTextFieldIntituleIntervention() {
		return textFieldIntituleIntervention;
	}

	/**
	 * Retourne le champ numéro de facture pour pouvoir l'utiliser dans les
	 * gestions.
	 * 
	 * @return le JTextField du numéro de facture
	 */
	public JTextField getTextFieldNumFactureIntervention() {
		return textFieldNumFactureIntervention;
	}

	/**
	 * Retourne le champ date d'acompte pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return le JTextField de la date d'acompte
	 */
	public JTextField getTextFieldDateAcompteIntervention() {
		return textFieldDateAcompteIntervention;
	}

	/**
	 * Retourne le champ date de facture pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return le JTextField de la date de facture
	 */
	public JTextField getTextFieldDateFactureIntervention() {
		return textFieldDateFactureIntervention;
	}

	/**
	 * Retourne le champ numéro de devis pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return le JTextField du numéro de devis
	 */
	public JTextField getTextFieldNumeroDevisIntervention() {
		return textFieldNumeroDevisIntervention;
	}

	/**
	 * Retourne le champ date de l'intervention pour pouvoir l'utiliser dans les
	 * gestions.
	 * 
	 * @return le JTextField de la date de l'intervention
	 */
	public JTextField getTextFieldDateIntervention() {
		return textFieldDateIntervention;
	}

	/**
	 * Retourne le champ montant du devis pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return le JTextField du montant du devis
	 */
	public JTextField getTextFieldMontantDevitIntervention() {
		return textFieldMontantDevitIntervention;
	}

	/**
	 * Retourne le champ montant de la facture pour pouvoir l'utiliser dans les
	 * gestions.
	 * 
	 * @return le JTextField du montant de la facture
	 */
	public JTextField getTextFieldMontantFactureIntervention() {
		return textFieldMontantFactureIntervention;
	}

	/**
	 * Retourne le champ montant de l'acompte pour pouvoir l'utiliser dans les
	 * gestions.
	 * 
	 * @return le JTextField du montant de l'acompte
	 */
	public JTextField getTextFieldAcompteIntervention() {
		return textFieldAcompteIntervention;
	}

	/**
	 * Retourne le champ montant non déductible pour pouvoir l'utiliser dans les
	 * gestions.
	 * 
	 * @return le JTextField du montant non déductible
	 */
	public JTextField getTextFieldMontantNonDeductibleIntervention() {
		return textFieldMontantNonDeductibleIntervention;
	}

	/**
	 * Retourne le champ réduction pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return le JTextField de la réduction
	 */
	public JTextField getTextFieldReductionIntervention() {
		return textFieldReductionIntervention;
	}

	/**
	 * Retourne le champ identifiant du bâtiment pour pouvoir l'utiliser dans les
	 * gestions.
	 * 
	 * @return le JTextField de l'identifiant du bâtiment
	 */
	public JTextField getTextFieldIDBatiment() {
		return textFieldIDBatiment;
	}

	/**
	 * Retourne le bouton radio Ordures pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return le JRadioButton de la catégorie Ordures
	 */
	public JRadioButton getRdbtnOrdures() {
		return rdbtnOrdures;
	}

	/**
	 * Retourne le bouton radio Entretien pour pouvoir l'utiliser dans les gestions.
	 * 
	 * @return le JRadioButton de la catégorie Entretien
	 */
	public JRadioButton getRdbtnEntretien() {
		return rdbtnEntretien;
	}

	/**
	 * Retourne la fenêtre parente FenBatimentInformations pour pouvoir l'utiliser
	 * dans les gestions.
	 * 
	 * @return la fenêtre FenBatimentInformations
	 */
	public FenBatimentInformations getFenAncestor() {
		return fenAncestor;
	}

	/**
	 * Modifie le champ identifiant de l'intervention.
	 * 
	 * @param textFieldIdentifiantIntervention le nouveau JTextField de
	 *                                         l'identifiant
	 */
	public void setTextFieldIdentifiantIntervention(JTextField textFieldIdentifiantIntervention) {
		this.textFieldIdentifiantIntervention = textFieldIdentifiantIntervention;
	}

	/**
	 * Modifie le champ intitulé de l'intervention.
	 * 
	 * @param textFieldIntituleIntervention le nouveau JTextField de l'intitulé
	 */
	public void setTextFieldIntituleIntervention(JTextField textFieldIntituleIntervention) {
		this.textFieldIntituleIntervention = textFieldIntituleIntervention;
	}

	/**
	 * Modifie le champ numéro de facture.
	 * 
	 * @param textFieldNumFactureIntervention le nouveau JTextField du numéro de
	 *                                        facture
	 */
	public void setTextFieldNumFactureIntervention(JTextField textFieldNumFactureIntervention) {
		this.textFieldNumFactureIntervention = textFieldNumFactureIntervention;
	}

	/**
	 * Modifie le champ date d'acompte.
	 * 
	 * @param textFieldDateAcompteIntervention le nouveau JTextField de la date
	 *                                         d'acompte
	 */
	public void setTextFieldDateAcompteIntervention(JTextField textFieldDateAcompteIntervention) {
		this.textFieldDateAcompteIntervention = textFieldDateAcompteIntervention;
	}

	/**
	 * Modifie le champ date de facture.
	 * 
	 * @param textFieldDateFactureIntervention le nouveau JTextField de la date de
	 *                                         facture
	 */
	public void setTextFieldDateFactureIntervention(JTextField textFieldDateFactureIntervention) {
		this.textFieldDateFactureIntervention = textFieldDateFactureIntervention;
	}

	/**
	 * Modifie le champ numéro de devis.
	 * 
	 * @param textFieldNumeroDevisIntervention le nouveau JTextField du numéro de
	 *                                         devis
	 */
	public void setTextFieldNumeroDevisIntervention(JTextField textFieldNumeroDevisIntervention) {
		this.textFieldNumeroDevisIntervention = textFieldNumeroDevisIntervention;
	}

	/**
	 * Modifie le champ date de l'intervention.
	 * 
	 * @param textFieldDateIntervention le nouveau JTextField de la date de
	 *                                  l'intervention
	 */
	public void setTextFieldDateIntervention(JTextField textFieldDateIntervention) {
		this.textFieldDateIntervention = textFieldDateIntervention;
	}

	/**
	 * Modifie le champ montant du devis.
	 * 
	 * @param textFieldMontantDevitIntervention le nouveau JTextField du montant du
	 *                                          devis
	 */
	public void setTextFieldMontantDevitIntervention(JTextField textFieldMontantDevitIntervention) {
		this.textFieldMontantDevitIntervention = textFieldMontantDevitIntervention;
	}

	/**
	 * Modifie le champ montant de la facture.
	 * 
	 * @param textFieldMontantFactureIntervention le nouveau JTextField du montant
	 *                                            de la facture
	 */
	public void setTextFieldMontantFactureIntervention(JTextField textFieldMontantFactureIntervention) {
		this.textFieldMontantFactureIntervention = textFieldMontantFactureIntervention;
	}

	/**
	 * Modifie le champ montant de l'acompte.
	 * 
	 * @param textFieldAcompteIntervention le nouveau JTextField du montant de
	 *                                     l'acompte
	 */
	public void setTextFieldAcompteIntervention(JTextField textFieldAcompteIntervention) {
		this.textFieldAcompteIntervention = textFieldAcompteIntervention;
	}

	/**
	 * Modifie le champ montant non déductible.
	 * 
	 * @param textFieldMontantNonDeductibleIntervention le nouveau JTextField du
	 *                                                  montant non déductible
	 */
	public void setTextFieldMontantNonDeductibleIntervention(JTextField textFieldMontantNonDeductibleIntervention) {
		this.textFieldMontantNonDeductibleIntervention = textFieldMontantNonDeductibleIntervention;
	}

	/**
	 * Modifie le champ réduction.
	 * 
	 * @param textFieldReductionIntervention le nouveau JTextField de la réduction
	 */
	public void setTextFieldReductionIntervention(JTextField textFieldReductionIntervention) {
		this.textFieldReductionIntervention = textFieldReductionIntervention;
	}

	/**
	 * Modifie le champ identifiant du bâtiment.
	 * 
	 * @param textFieldIDBatiment le nouveau JTextField de l'identifiant du bâtiment
	 */
	public void setTextFieldIDBatiment(JTextField textFieldIDBatiment) {
		this.textFieldIDBatiment = textFieldIDBatiment;
	}

	/**
	 * Active ou désactive les champs montant et date de facture selon que le numéro
	 * de facture est renseigné. Appelée par le DocumentListener.
	 */
	private void numFactureChangedRemoveInsertUpdate() {
		if (!textFieldNumFactureIntervention.getText().isEmpty()) {
			textFieldMontantFactureIntervention.setEnabled(true);
			textFieldDateFactureIntervention.setEnabled(true);
		} else {
			textFieldMontantFactureIntervention.setEnabled(false);
			textFieldDateFactureIntervention.setEnabled(false);
			textFieldMontantFactureIntervention.setText("");
			textFieldDateFactureIntervention.setText("");
		}
	}

	/**
	 * Active ou désactive le champ montant acompte selon que la date d'acompte est
	 * renseignée. Appelée par le DocumentListener.
	 */
	private void acompteChangedRemoveInsertUpdate() {
		if (!textFieldDateAcompteIntervention.getText().isEmpty()) {
			textFieldAcompteIntervention.setEnabled(true);
		} else {
			textFieldAcompteIntervention.setEnabled(false);
			textFieldAcompteIntervention.setText("");
		}
	}

}
