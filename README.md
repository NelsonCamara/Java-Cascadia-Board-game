# 🎲 Cascadia - Jeu de Plateau en Java

Implémentation complète du jeu de société **Cascadia** (Spiel des Jahres 2022) en **Java 21**, proposant un mode console et un mode graphique. Projet réalisé dans le cadre d'un cursus universitaire en Informatique.

---

## 📖 À propos du projet

Cascadia est un jeu de placement de tuiles hexagonales où les joueurs construisent des écosystèmes en combinant des habitats (Forêt, Montagne, Prairie, Marais, Rivière) et des animaux sauvages (Ours, Wapiti, Saumon, Buse, Renard). Chaque espèce a ses propres règles de scoring basées sur les configurations spatiales.

Ce projet reproduit fidèlement les mécaniques du jeu pour 2 joueurs, avec deux variantes de scoring (Familiale et Intermédiaire) et deux modes d'affichage.

---

## 🏗️ Architecture & Conception

### Pattern MVC (Model-View-Controller)

Le projet suit une architecture **MVC stricte** avec une séparation claire des responsabilités :

- **Model** (`board/`, `items/`, `game/`) — Logique métier, gestion du plateau, des tuiles, des tokens et du scoring
- **View** (`draw/`) — Rendu graphique via Java AWT/Graphics2D avec des classes Drawer dédiées
- **Controller** (`events/`) — Gestion des événements clavier et des états de jeu via un système de handlers

### Structure des packages

```
src/cascadia/
├── board/          # Plateau de jeu, carte joueur, pioche, lots
│   ├── Board.java
│   ├── BoardManager.java
│   ├── Lot.java
│   ├── OptionsLots.java
│   ├── Pioche.java
│   └── PlayerMap.java
├── draw/           # Rendu graphique (Drawers)
│   ├── OptionsLotsDrawer.java
│   ├── PlayerDrawer.java
│   ├── PlayerMapDrawer.java
│   └── TileDrawer.java
├── events/         # Gestion des événements et états
│   ├── EventState.java
│   ├── Handler.java
│   ├── ItemPlacementHandler.java
│   ├── NavigationHandler.java
│   └── OptionsSelectionHandler.java
├── game/           # Logique de jeu et boucle principale
│   ├── GameLoop.java
│   ├── GamePlay.java
│   ├── GraphicGameLoop.java
│   ├── Player.java
│   ├── Score.java
│   └── TuilesGroups.java
├── items/          # Objets du jeu (tuiles, tokens)
│   ├── FaunaToken.java
│   ├── FaunaTokenType.java
│   ├── Tile.java
│   └── TileType.java
├── main/
│   └── Main.java
└── utils/          # Utilitaires (Position, MutableInt)
    ├── MutableInt.java
    └── Position.java
```

**28 classes** | **~2900 lignes de code**

---

## 🔧 Compétences techniques démontrées

### Programmation Orientée Objet avancée
- **Records Java** (`Tile`, `Lot`, `FaunaToken`, `Position`) — Utilisation des records pour l'immutabilité et la concision
- **Sealed interfaces** (`Handler`) — Hiérarchie de types fermée pour un contrôle strict des implémentations
- **Enums** (`TileType`, `FaunaTokenType`, `EventState`) — Modélisation type-safe des constantes du domaine
- **Immutabilité** — Les objets principaux (Tile, Player, Lot) sont immuables ; les modifications retournent de nouvelles instances (`updatedPlayerLot()`, `addToken()`)
- **Encapsulation** — Utilisation systématique de `Collections.unmodifiableList()` pour protéger les collections internes

### Java moderne (Java 21)
- **Pattern matching** avec `switch` expressions (rendu des couleurs de tuiles)
- **Stream API** — Filtrage, mapping et collecte pour la validation de positions, calcul de scores et extraction de données
- **`Optional<T>`** — Gestion explicite de la nullabilité pour les tokens et les lots
- **Compact constructors** dans les records pour la validation (`Objects.requireNonNull`)

### Algorithmique
- **DFS (Depth-First Search)** — Algorithme de parcours en profondeur pour identifier les groupes de tuiles adjacentes de même type (`TuilesGroups.calculateGroups()`)
- **Calcul de positions valides** — Expansion dynamique de la grille et vérification d'adjacence
- **Système de scoring paramétrique** — Deux variantes de scoring avec lookup tables (`Map<Integer, Integer>`)

### Architecture logicielle
- **Machine à états** (`EventState`) — Gestion des phases de jeu (INIT → CHOOSELOT → TILEPLACEMENT → TOKENPLACEMENT → END)
- **Séparation Console/Graphique** — Deux boucles de jeu distinctes (`GameLoop` et `GraphicGameLoop`) partageant le même `BoardManager`
- **Handlers d'événements** — Système extensible avec interface sealed (`NavigationHandler`, `OptionsSelectionHandler`, `ItemPlacementHandler`)
- **Délégation** — `BoardManager` comme façade coordonnant les opérations sur `Board`, `PlayerMap` et `OptionsLots`

### Interface graphique (Java AWT/Graphics2D)
- Rendu en temps réel avec navigation au clavier (flèches directionnelles)
- Système de caméra avec translation (scroll de la carte)
- Sélecteur visuel pour le placement de tuiles et tokens
- Mise en surbrillance des lots sélectionnés/choisis
- Séparation du rendu en classes Drawer spécialisées

### Bonnes pratiques
- **Javadoc complète** sur toutes les méthodes publiques
- **Programmation défensive** — `Objects.requireNonNull()` systématique, validation des paramètres
- **Gestion d'erreurs** — Exceptions explicites (`IllegalArgumentException`, `IllegalStateException`) avec messages descriptifs
- **Nommage explicite** — Classes, méthodes et variables avec des noms auto-documentés

---

## ▶️ Lancement

### Prérequis
- **Java 21** ou supérieur

### Exécution rapide
```bash
java -jar Cascadia.jar
```

### Compilation depuis les sources
```bash
javac -d out src/cascadia/**/*.java
java -cp out cascadia.main.Main
```

### Déroulement
1. Saisie des pseudos des deux joueurs
2. Choix du mode : **Graphique** (1) ou **Console** (0)
3. Choix de la variante : **Familiale** (1) ou **Intermédiaire** (0)
4. Chaque joueur alterne les tours : sélection d'un lot → placement de tuile → placement de token
5. Affichage des scores finaux

### Contrôles en mode graphique
| Touche | Action |
|--------|--------|
| `←` `→` `↑` `↓` | Naviguer sur la carte |
| `O` | Ouvrir/fermer le menu des lots |
| `←` `→` (dans le menu) | Parcourir les lots |
| `L` | Sélectionner un lot |
| `Z` `Q` `S` `D` | Déplacer le sélecteur de placement |
| `Espace` | Confirmer le placement |

---

## 📚 Documentation

Le dossier `docs/` contient :
- **dev.pdf** — Documentation technique développeur
- **user.pdf** — Guide utilisateur
- **doc/** — Javadoc générée

---

## 🛠️ Technologies

| Technologie | Usage |
|-------------|-------|
| Java 21 | Langage principal |
| Java AWT / Graphics2D | Interface graphique |
| Zen Library | Gestion des événements graphiques |

---

## 👤 Auteur

**Nelson Camara** — Étudiant en Master Informatique

---

*Projet académique — Reproduction du jeu de société Cascadia à des fins éducatives.*
