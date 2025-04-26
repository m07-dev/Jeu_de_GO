import javax.swing.*;

public class Plateau {    // Création du plateau de Jeu

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
    }

    // Fontion qui vérifie si on est en dehors du plateau
    public boolean enDehorsDesLimites(int posX, int posY) {
        if(posX < 0 || posX >= taille || posY < 0 || posY >= taille) {
            return false;
        }
        return true;
    }

    public boolean peutPoserPierre(int posX, int posY){
        if(enDehorsDesLimites(posX, posY) && (plateau[posX][posY]== '·')){
            return true;
        }
        return false;
    }

}
