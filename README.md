# Gestionnaire de Collection de Jeux Vidéo

Ce projet est une application Java permettant de gérer une collection personnelle de jeux vidéo. Elle propose une interface graphique moderne avec JavaFX, la persistance des données avec Hibernate et une base locale SQLite. Le projet est structuré avec Maven.

## Fonctionnalités principales
- Affichage de la liste des jeux vidéo (TableView)
- Ajout, modification, suppression de jeux (CRUD complet)
- Recherche par titre
- Gestion des supports (plateformes)
- Stockage des données en base SQLite locale
- Architecture claire (DAO, MVC/MVP)

## Technologies utilisées
- **Java 11+**
- **JavaFX** (FXML, SceneBuilder)
- **Hibernate** (ORM)
- **SQLite** (base de données locale)
- **Maven** (gestion de projet)

## Prérequis
- Java JDK 11 ou supérieur
- Maven 3.6+
- (Optionnel) SceneBuilder pour éditer les fichiers FXML

## Installation
1. **Cloner le projet**
   ```bash
   git clone <url-du-repo>
   cd VideoGameCollection
   ```
2. **Construire le projet**
   ```bash
   mvn clean install
   ```
3. **Lancer l'application**
   ```bash
   mvn javafx:run
   ```
   ou exécuter le JAR généré dans `target/` :
   ```bash
   java -jar target/VideoGameCollection-1.0-SNAPSHOT.jar
   ```

## Structure du projet
```
VideoGameCollection
│   pom.xml
│   README.md
│
└───src
    └───main
        ├───java
        │   └───com.sophiaynovcampus
        │       ├───controller
        │       ├───dao
        │       ├───model
        │       └───util
        └───resources
            ├───fxml
            └───hibernate.cfg.xml
```
- **controller/** : Contrôleurs JavaFX (logique de l'UI)
- **dao/** : Accès aux données (CRUD)
- **model/** : Entités Hibernate (JeuVideo, Support)
- **util/** : Utilitaires (Hibernate, SQLite)
- **resources/fxml/** : Interfaces graphiques FXML

## Configuration
- La base SQLite est stockée dans le fichier `jeuxvideo.db` à la racine du projet.
- Les paramètres Hibernate sont dans `src/main/resources/hibernate.cfg.xml`.

## Auteurs
- Projet réalisé par [Ameslant Ugo,Tony Nebuloni] et collaborateurs dans le cadre d'un projet universitaire.

## Licence
Ce projet est open-source, licence à définir selon vos besoins. 
