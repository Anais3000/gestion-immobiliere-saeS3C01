*********************************************************************
** S.A.É. S3.C.01 - Création et exploitation d'une base de données **
*********************************************************************

Version de java : 1.8

Version du JDK utilisé pour nos tests Sonarqube : JDK 21

---------------------------------------------------------------------
AUTEURS
---------------------------------------------------------------------

  . BOURNAZEL-LOTTY Guillaume
  . DUVAL Anaïs
  . FERNANDES Lucas
  . MURILLO Unai
  . SAGNES Rémi

Étudiants à l'IUT Informatique de Toulouse, Université de Toulouse
Deuxième année, groupe D



---------------------------------------------------------------------
INFORMATIONS DE CONNEXION
---------------------------------------------------------------------

La connexion est rendue impossible pour des raisons de confidentialité



---------------------------------------------------------------------
DESCRIPTION
---------------------------------------------------------------------

L'application que nous avons conçu permet la gestion de biens 
immobiliers non meublés. Elle permet la gestion des locataires 
actuels, mais aussi de ceux qui ont quitté leur location. 
L'utilisateur peut accéder aux informations sur les anciens 
locataires, afin de faire des statistiques, ou répondre aux 
sollicitations de l'administration.

Cette application permet d'effectuer les actions inhérentes à une
location et à la gestion de biens. Elle permet également d'archiver 
les différents documents résultant de ces locations : baux,
diagnostics, états des lieux, etc.



---------------------------------------------------------------------
FONCTIONNALITÉS
---------------------------------------------------------------------

Ajouts, Modifications et Suppressions :
--------------------------------------------
  - Bâtiments
  - Biens louables et les associer à des bâtiments
  - Locataires
  - Garants
  - Organismes
  - Valeurs d'IRL
  - Compteurs et les associer à des biens (logements/bâtiments)
  - Assurances
  - Interventions à un bâtiment / logement
  - Diagnostics


Actions régulières :
--------------------
  - Création de bail et association du/des garages
  - Relevé de compteur
  - Paiement de loyer
  - Paiement émis/reçu occasionnel
  - Résiliation d'un bail avec solde de tout compte
  - Régularisation des charges (1 fois par an après la date 
    anniversaire du bail)
  - Renseignement d'une nouvelle valeur d'IRL


Note :
------
Les paiements sont générés automatiquement pour :
  - les relevés de compteur
  - les interventions réglées
  - les paiements de loyer
  - les régularisations des charges
  - les soldes de tout compte
  - etc.

Ainsi la fenêtre d'insertion de paiements occasionnels ne doit 
servir qu'à ajouter des paiements OCCASIONNELS.


Suivis :
--------
  - Anciens relevés de compteur
  - Anciens locataires
  - Anciennes régularisations
  - Anciens soldes de tout compte
  - Anciens contrats de location
  - Anciens diagnostics
  - Anciens états des lieux
  - Anciennes factures & devis
  - Anciens contrats d'assurance
  - Anciennes valeurs d'IRL enregistrées


Génération de documents :
-------------------------
Génération automatique de quittances de loyer, concernant une 
location et un mois de loyer payé, au format .docx


Import de données :
-------------------
Import des anciens loyers avec un fichier .csv

IMPORTANT : les baux concernés par les loyers doivent être encore en 
cours et les identifiants des biens et des locataires doivent être 
strictement les mêmes que ceux créés dans l'application.



---------------------------------------------------------------------
UTILISATION
---------------------------------------------------------------------

Pour plus d'informations, consulter le guide d'utilisation présent 
dans le répertoire /documents du projet.






---------------------------------------------------------------------
MENTIONS
---------------------------------------------------------------------

  . Cours, travaux pratiques et recommandations de M.MILLAN Thierry
  . Cours, travaux pratiques et recommandations de M.BOUGHANEM Mohand
	


 _____________________________________
|   Année universitaire 2025 - 2026   |
|                                     |
|        TOUS DROITS RÉSERVÉS         |
|_____________________________________|
