# Jeu de GO

## Objectif : 
- Reproduire le Jeu de Go avec les règles classique et implémenter de l'intelligence artificielle pour jouer contre ou avec elle.

## Règles a respecter : 
- On joue à tour de rôle, le noir en premier.
- Jouer, c’est soit passer son tour, soit placé une pièce de sa couleur sur une case vide (pas sur les intersections !), tout en respectant la règle de non-suicide.
- Un groupe est formé d’un ensemble de pièces qui se touchent côte à côte, donc deux pièces connectées en diagonale ne forment pas un groupe.
- Un groupe est vivant s’il est formé d’au moins 10 pièces.
- Une zone privatisée par un joueur est une zone entourée de pièces de la couleur respective où il reste encore des cases vides.
- Un joueur n’a pas le droit de placer une pièce dans une zone privatisée qui ne lui appartient pas et qui est composée de 10 cases vides ou moins (faire cela revient à se "suicider").
- Si la zone privatisée a au moins 11 cases, jouer dedans est permis.

## Tâches à accomplir : 
- Afficher la configuration courante (tableau de caractères, sans graphisme). 
- Gérer le tour de rôle.
- Demander au joueur courant quel coup jouer (si configuration non-finale) et vérifier qu’il s’agit d’un coup légal.
- Actualiser le plateau.
- Identifier si configuration est finale (impossibilité de coup légal ou deux passes de suite).
- Compter le nombre de pièces de chaque couleur dans les groupes vivants.
- Implémenter une IA aléatoire qui joue des coups légaux.
- Proposer un menu, au démarrage, pour choisir entre jeu entre deux humains et jeu contre l’IA implémentée.
    ### Tâches optionelle : 
    - Affichage avec des pixels, soit en utilisant la bibliothèque graphique simplifiée de Cay S. Horstmann, soit une autre plus développée (JavaSwing, etc...).

## Technologies :
- Java (Open JDK version "21.0.6" 2025-01-21)

## Getting Started : 
   SOON...
