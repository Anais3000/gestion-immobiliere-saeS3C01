   CREATE TABLE sae_Locataire (
    Id_Locataire VARCHAR2(50) PRIMARY KEY,
    Nom VARCHAR2(50) NOT NULL,
    Prenom VARCHAR2(50)NOT NULL,
    Num_tel CHAR(10)NOT NULL,
    Mail VARCHAR2(50)NOT NULL,
    Date_naissance DATE NOT NULL,
    Ville_naissance VARCHAR2(50),
    Adresse VARCHAR2(50),
    Code_postal CHAR(5),
    Ville VARCHAR2(50)
);

    CREATE TABLE sae_Organisme (
    Num_SIRET CHAR(14) PRIMARY KEY,
    Nom VARCHAR2(50) NOT NULL,
    Adresse VARCHAR2(50),
    Code_postal CHAR(5),
    Ville VARCHAR2(50),
    Mail VARCHAR2(50) ,
    Num_tel CHAR(10) NOT NULL,
    Specialite VARCHAR2(50)
);

CREATE TABLE sae_Garant (
    Id_Garant VARCHAR2(50) PRIMARY KEY,
    Adresse VARCHAR2(50) NOT NULL,
    Mail VARCHAR2(50),
    Num_tel CHAR(10) NOT NULL
);

CREATE TABLE sae_Batiment (
    Id_Batiment VARCHAR(50) PRIMARY KEY,
    Date_construction DATE NOT NULL,
    Adresse VARCHAR2(50) NOT NULL,
    Code_postal CHAR(5) NOT NULL,
    Ville VARCHAR2(50) NOT NULL,
    Numero_Fiscal_Bat CHAR(12),
);

CREATE TABLE sae_Assurance (
    Numero_police_assurance VARCHAR2(50) PRIMARY KEY,
    Type_contrat VARCHAR2(50)NOT NULL check(Type_contrat IN('propriétaire','aide juridique')),
    Annee_couverture INT NOT NULL check(Annee_couverture BETWEEN 1000 and 9999),
    Montant_paye DECIMAL(10,2)NOT NULL,
    Id_Batiment VARCHAR2(50) NOT NULL,
    CONSTRAINT FK_Assurance_Batiment
        FOREIGN KEY (Id_Batiment) REFERENCES sae_Batiment(Id_Batiment)
    
);

CREATE TABLE sae_IRL(
   Annee INT check(Annee BETWEEN 1000 and 9999),
   Trimestre INT check(Trimestre in(1,2,3,4)),
   Valeur_IRL Decimal(10,2) NOT NULL,
   PRIMARY KEY(Annee, Trimestre)
);

CREATE TABLE sae_Bien_louable(
   Id_Bien VARCHAR2(50),
   Adresse VARCHAR2(50) NOT NULL,
   Ville VARCHAR2(50) NOT NULL,
   Code_postal CHAR(5) NOT NULL,
   Type_bien VARCHAR2(50)NOT NULL check(Type_bien in('Garage', 'Logement')),
   Date_construction DATE NOT NULL ,
   Surface_habitable DECIMAL(15,2) NOT NULL,
   Nb_pieces INT NOT NULL,
   Pourcentage_entretien_parties_communes DECIMAL(5,2) check(Pourcentage_entretien_parties_communes BETWEEN 0 and 100),
   Pourcentage_ordures_menageres DECIMAL(5,2) check(Pourcentage_ordures_menageres BETWEEN 0 and 100), 
   Numero_Fiscal CHAR(12) NOT NULL,
   Loyer DECIMAL(10,2)NOT NULL,
   Provision_pour_charges DECIMAL(10,2)NOT NULL,
   Id_Batiment VARCHAR2(50) NOT NULL,
   Derniere_Annee_Modif_Loyer DECIMAL(4,0),
   PRIMARY KEY(Id_Bien),
   FOREIGN KEY(Id_Batiment) REFERENCES sae_Batiment(Id_Batiment)
);

CREATE TABLE sae_Charge(
   Id_Charge VARCHAR2(50),
   Libelle VARCHAR2(50) NOT NULL,
   Date_Facturation DATE NOT NULL,
   Montant DECIMAL(10,2),
   Commentaire VARCHAR2(50),
   Id_Batiment VARCHAR2(50),
   Id_Bien VARCHAR2(50),
   Date_Debut_Periode DATE,
   Date_Fin_Periode DATE,
   PRIMARY KEY(Id_Charge),
   FOREIGN KEY(Id_Batiment) REFERENCES sae_Batiment(Id_Batiment),
   FOREIGN KEY(Id_Bien) REFERENCES sae_Bien_louable(Id_Bien)
);

CREATE TABLE sae_Diagnostics(
   Id_Bien VARCHAR2(50),
   Libelle VARCHAR2(50),
   Date_debut DATE NOT NULL,
   Date_fin DATE NOT NULL,
   Details CLOB,
   PRIMARY KEY(Id_Bien, Libelle, Date_debut),
   FOREIGN KEY(Id_Bien) REFERENCES sae_Bien_louable(Id_Bien)
);

CREATE TABLE sae_Intervention(
   Id_Intervention VARCHAR2(50),
   Intitule VARCHAR2(50) NOT NULL,
   Numero_facture VARCHAR(50) NOT NULL,
   Montant_facture DECIMAL(10,2) NOT NULL,
   Acompte DECIMAL(10,2),
   Date_acompte DATE,
   Date_facture DATE NOT NULL,
   Numero_devis VARCHAR(50) NOT NULL,
   Montant_devis DECIMAL(10,2) NOT NULL,
   Date_intervention DATE NOT NULL,
   Montant_non_deductible DECIMAL(10,2),
   Reduction Decimal(10,2),
   Id_Batiment VARCHAR2(50)NOT NULL,
   Num_SIRET CHAR(14) NOT NULL,
   Entretien_PC NUMBER(1,0),
   Ordures NUMBER(1,0),
   PRIMARY KEY(Id_Intervention),
   FOREIGN KEY(Id_Batiment) REFERENCES sae_Batiment(Id_Batiment),
   FOREIGN KEY(Num_SIRET) REFERENCES sae_Organisme(Num_SIRET)
);

CREATE TABLE sae_Paiement(
   Id_Paiement VARCHAR2(50),
   Montant Decimal(10,2) NOT NULL,
   Sens_paiement VARCHAR2(50) NOT NULL check(Sens_paiement in('emis','recus')),
   Libelle VARCHAR2(50),
   Date_paiement DATE NOT NULL,
   Id_Bien VARCHAR2(50),
   Id_Intervention VARCHAR2(50),
   Mois_Concerne DATE,
   PRIMARY KEY(Id_Paiement),
   FOREIGN KEY(Id_Bien) REFERENCES sae_Bien_louable(Id_Bien),
   Foreign key (Id_Intervention) References sae_Intervention(Id_Intervention)
);

CREATE TABLE sae_Louer(
   Id_Bien VARCHAR2(50),
   Id_Locataire VARCHAR2(50),
   Date_Debut DATE NOT NULL,
   Date_Fin DATE NOT NULL,
   Depot_de_Garantie DECIMAL(10,2) NOT NULL,
   Date_etat_des_lieux_entree DATE,
   Date_etat_des_lieux_sortie DATE,
   Details_etat_des_lieux_entree CLOB,
   Details_etat_des_lieux_sortie CLOB,
   Id_Garant VARCHAR2(50),
   Date_Derniere_Regul DATE,
   Ajustement_Loyer DECIMAL(10,2),
   Moi_Fin_Ajustement DATE,
   Id_Logement_Associe VARCHAR2(50),
   Revolue NUMBER(1,0),
   Mois_Debut_Ajustement DATE,
   PRIMARY KEY(Id_Bien, Id_Locataire, Date_Debut),
   FOREIGN KEY(Id_Bien) REFERENCES sae_Bien_louable(Id_Bien),
   FOREIGN KEY(Id_Locataire) REFERENCES sae_Locataire(Id_Locataire),
   FOREIGN KEY(Id_Garant) REFERENCES sae_Garant(Id_Garant)
);

CREATE TABLE sae_Compteur(
   Id_Bien VARCHAR2(50),
   Id_Batiment VARCHAR2(50),
   Id_Compteur VARCHAR2(50),
   Type_Compteur VARCHAR2(20) check(Type_Compteur in('Eau', 'Electricité')),
   Date_Installation DATE,
   Index_Depart DECIMAL(10,2),
   PRIMARY KEY(Id_Compteur),
   FOREIGN KEY(Id_Bien) REFERENCES sae_Bien_louable(Id_Bien),
   FOREIGN KEY(Id_Batiment) REFERENCES sae_Batiment(Id_Batiment)
);

CREATE TABLE sae_Releve_compteur(
   Date_releve DATE,
   Index_Compteur DECIMAL(10,2)NOT NULL,
   Prix_unite DECIMAL(10,2)NOT NULL,
   Partie_fixe DECIMAL(10,2)NOT NULL,
   Id_Compteur VARCHAR2(50) NOT NULL,
   Ancien_Index DECIMAL(10,2),
   PRIMARY KEY(Date_releve, Id_compteur),
   FOREIGN KEY(Id_Compteur) REFERENCES sae_Compteur(Id_Compteur)
);

CREATE TABLE sae_Necessiter(
   Id_Bien VARCHAR2(50),
   Id_Intervention VARCHAR2(50),
   PRIMARY KEY(Id_Bien, Id_Intervention),
   FOREIGN KEY(Id_Bien) REFERENCES sae_Bien_louable(Id_Bien),
   FOREIGN KEY(Id_Intervention) REFERENCES sae_Intervention(Id_Intervention)
);

CREATE TABLE sae_historique (
    id_historique NUMBER GENERATED ALWAYS AS IDENTITY START WITH 1 INCREMENT BY 1,
    date_action DATE NOT NULL,
    action VARCHAR2(50) NOT NULL,
    details CLOB,
    PRIMARY KEY(id_historique)
);

CREATE TABLE sae_historique_loyers(
   Id_Hist NUMBER GENERATED ALWAYS AS IDENTITY START WITH 1 INCREMENT BY 1,
   Loyer DECIMAL(10,2),
   Provision DECIMAL(10,2),
   Id_Bien VARCHAR2(50) NOT NULL ,
   Mois_Concerne DATE,
   PRIMARY KEY (Id_Hist),
   FOREIGN KEY(Id_Bien) REFERENCES sae_Bien_louable(Id_Bien),
)


ALTER TABLE sae_Bien_louable
ADD CONSTRAINT chk_Numero_Fiscal
CHECK (LENGTH(Numero_fiscal) = 12);

ALTER TABLE sae_Batiement
ADD CONSTRAINT chk_Numero_Fiscal_Batiment
CHECK (Numero_fiscal_bat is null or LENGTH(Numero_fiscal_bat) = 12);

ALTER TABLE sae_Diagnostics
ADD CONSTRAINT chk_libelle_diagnostics
CHECK (Libelle IN ('Certificat de superficie','Exposition au plomb','Diagnostic électricité','Certificat de surface habitable','Etat des risques et pollutions','Etat des nuisansces sonores et aériennes','Déclaration de sinistres indemnisés','DPE','Dossier amiante parties privatives'));

ALTER TABLE sae_Intervention
ADD CONSTRAINT chk_Entretien_PC
CHECK (Entretien_PC IN(0,1));

ALTER TABLE sae_Intervention
ADD CONSTRAINT chk_Ordures
CHECK (Ordures IN(0,1));

ALTER TABLE sae_Louer
ADD CONSTRAINT CK_SAE_LOUER_REVOLUE
CHECK (Revolue IN(0,1));




