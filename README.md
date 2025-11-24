# 🎮 Enciam Games

**Application Android Kotlin utilisant l’API RAWG**

---

## 📌 Description

**Enciam Games** est une application mobile développée en **Kotlin** permettant de :

* 🔍 **Rechercher** des jeux vidéo
* 🧹 **Filtrer** par popularité, note ou date
* 📄 **Consulter** les détails complets d’un jeu
* ❤️ **Ajouter ses propres jeux en favoris** via une base de données locale

L’application s’appuie sur l’API **RAWG** pour récupérer les données des jeux.

---

## ✨ Fonctionnalités principales

### 🏠 Écran d’accueil – Recherche & Liste des jeux

* 📜 Affichage de la liste complète des jeux provenant de l’API RAWG
* 🔎 Barre de recherche permettant de filtrer par **nom**
* ⚙️ Filtres dynamiques :

  * ⭐ Meilleures notes (Metacritic)
  * 🔥 Les plus joués
  * 📅 Date de sortie
* 👉 Un clic sur un jeu ouvre sa fiche détaillée

---

### 📄 Écran de détails du jeu

Informations affichées :

* 🖼️ Image de couverture
* 🎮 Nom
* 📝 Description
* 📅 Date de sortie
* ⭐ Note
* 👥 Nombre de joueurs

➡️ Toutes les données proviennent directement de l’API RAWG.

---

### ❤️ Écran des favoris (base locale)

Accessible depuis :

* Le bouton **❤️** sur l’écran principal
* La fiche d’un jeu

Informations affichées pour chaque jeu favori :

* 🎮 Nom
* 📅 Date de sortie
* 🖼️ Image
* ⭐ Note
* 👥 Nombre de joueurs

Lors de l’action **« Ajouter le jeu »** :

* 💾 Le jeu est enregistré dans la **Room Database**
* 🔄 Il est ajouté automatiquement à la liste principale
* ↩️ Redirection vers l’écran d’accueil
