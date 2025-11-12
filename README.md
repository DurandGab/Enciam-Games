Enciam Games – Application Kotlin utilisant l’API RAWG
Description : Enciam Games  est une application mobile développée en Kotlin qui permet de rechercher, filtrer et consulter des jeux vidéo via l’API RAWG.
L’application offre également la possibilité d’ajouter ses propres jeux dans une base de données locale, afin de compléter la liste existante.

Fonctionnalités principales

1. Écran d’accueil – Recherche et liste des jeux
Affiche la liste complète des jeux provenant de l’API RAWG.
Barre de recherche permettant de filtrer par :
- Nom du jeu
- Genre

Filtres dynamiques :
- Les jeux les mieux notés
- Les plus joués
- Date de sortie

En cliquant sur un jeu, redirection vers l’écran de détails.

2. Écran de détails du jeu
Affiche toutes les informations détaillées sur le jeu sélectionné :
- Image de couverture
- Nom, description, date de sortie
- Note, genre, plateforme, éditeur
- Nombre de joueurs, image du jeu

Les données sont directement extraites depuis l’API RAWG.

3. Écran d’ajout de jeu (base locale)
Accessible depuis un bouton "Ajouter votre jeu vidéo" sur l’écran principal.
Formulaire pour créer un jeu personnalisé avec les champs suivants :
- Nom du jeu
- Genre
- Date de sortie
- Description
- Image du jeu

En validant (“Ajouter le jeu”), le jeu est :
- Enregistré dans une base de données locale (Room Database).
- Ajouté dynamiquement à la liste principale.
- Redirection automatique vers l’écran d’accueil.
