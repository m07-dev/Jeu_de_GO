public class goban {
    // Création du plateau de Jeu
    char[][] GoBan = new char[19][19];

    // Constructeur pour initialiser le plateau
    public goban() {
        for (int i = 0; i < GoBan.length; i++) {
            for (int j = 0; j < GoBan[i].length; j++) {
                GoBan[i][j] = '·'; // '.' représente une case vide
            }
        }
    }

    // Fonction qui affiche les barres verticales
    public void afficheBars() {
        for (int i = 0; i < GoBan.length; i++) {
            System.out.print("| "); // Affiche une barre verticale pour chaque colonne
        }
        System.out.println(); 
    }

    // Fonction qui affichera le plateau de Jeu avec les barres
    public void affichePlateau() {
        for (int i = 0; i < GoBan.length; i++) {
            for (int j = 0; j < GoBan[i].length; j++) {
                System.out.print(GoBan[i][j] + "–"); // Affiche chaque case avec un '–'
            }
            System.out.println(); 
            afficheBars(); // Affiche les barres après chaque ligne
        }
    }

    
}