package mypackage;

public class Plateau {

    public static int taille;
    public static char[][] plateau;

    // Constructeur
    public Plateau(int taille) {
        Plateau.taille = taille;
        Plateau.plateau = new char[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                Plateau.plateau[i][j] = '·'; // '.' représente une case vide
            }
        }
    }

    // Affichage du plateau
    public static void afficherPlateau() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                System.out.print(plateau[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static char[][] getPlateau() {
        return plateau;
    }
    
    // Vérifie si le plateau est rempli
    public static boolean plateauRempli(char[][] plateau) {
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == '·') {
                    return false;
                }
            }
        }
        return true;
    }

    // Vérifie si la position est dans les limites
    public static boolean enDehorsDesLimites(int x, int y, char[][] plateau) {
        return x < 0 || x >= plateau.length || y < 0 || y >= plateau.length;
    }

    // Vérifie si on peut poser une pierre
    public static boolean peutPoserPierre(int x, int y, char[][] plateau) {
        return !enDehorsDesLimites(x, y, plateau) && plateau[x][y] == '·';
    }

    // Pose la pierre
    public static void poserPierre(int x, int y, char pierre, char[][] plateau) {
        if (peutPoserPierre(x, y, plateau)) {
            plateau[x][y] = pierre;
        }
    }

}