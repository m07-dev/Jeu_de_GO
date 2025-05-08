package mypackage;

public class Plateau { // Création du plateau de Jeu

    int taille;
    char[][] plateau;

    // Constructeur pour initialiser le plateau
    public Plateau(int taille) {
        this.taille = taille;
        plateau = new char[taille][taille];
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                plateau[i][j] = '·'; // '.' représente une case vide
            }
        }
    }

    // Fonction qui affiche le plateau de Jeu
    public void afficherPlateau() {
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                System.out.print(plateau[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Fontion qui vérifie si on est en dehors du plateau
    public boolean enDehorsDesLimites(int posX, int posY) {
        if (posX < 0 || posX >= this.taille || posY < 0 || posY >= this.taille) {
            return false;
        }
        return true;
    }

    // Fonction qui vérifie si on peut ou pas poser une pierre
    public boolean peutPoserPierre(int posX, int posY) {
        if (enDehorsDesLimites(posX, posY) && plateau[posX][posY] == '·') {
            return true;
        }
        return false;
    }

    // Fonction qui poser la pierre
    public void poserPierre(int posX, int posY, char pierre) {
        if (peutPoserPierre(posX, posY)) {
            plateau[posX][posY] = pierre; // Il faudra changer en fonction du joueur sa pierre
        }
    }

    

}
