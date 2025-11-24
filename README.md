🎮 Enciam Games

Application Android Kotlin utilisant l’API RAWG

📌 Description

Enciam Games est une application mobile développée en Kotlin permettant de rechercher, filtrer et consulter des jeux vidéo grâce à l’API RAWG.
Elle offre également la possibilité d’ajouter vos propres jeux à une base de données locale afin de compléter la liste récupérée en ligne.

✨ Fonctionnalités principales
🏠 Écran d’accueil – Recherche & Liste des jeux

Affichage de la liste complète des jeux provenant de l’API RAWG.

Barre de recherche permettant de filtrer par :

Nom du jeu

Critères dynamiques :

Meilleures notes (basées sur Metacritic)

Jeux les plus joués

Date de sortie

Sélection d’un jeu pour accéder à l’écran de détails.

📄 Écran de détails du jeu

Affiche toutes les informations disponibles sur le jeu sélectionné :

Image de couverture

Nom du jeu

Description

Date de sortie

Note

Nombre de joueurs
Les données sont récupérées en temps réel via l’API RAWG.

❤️ Écran des favoris (base locale)

Accessible depuis :

Le bouton « cœur » sur l’écran principal

La fiche d’un jeu vidéo

Fonctionnalités :

Affichage complet des jeux ajoutés en favoris avec :

Nom

Date de sortie

Image

Note

Nombre de joueurs

Lors de l’ajout d’un jeu (« Ajouter le jeu ») :

Le jeu est enregistré dans la base de données locale (Room Database)

Il est ajouté dynamiquement à la liste principale

Redirection automatique vers l’écran d’accueil
