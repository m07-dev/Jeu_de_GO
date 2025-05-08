package mypackage;
import javax.swing.JOptionPane;

public class Arena {
    public int joueurActuel;
    public char pierreBlanc;
    public char pierreNoir; // 1 = blanc, 2 = noir
    int pointBlanc;
    int pointNoir;

    public Arena(char pierreBlanc, char pierreNoir, int joueurActuel, int pointBlanc, int pointNoir) {
        this.pierreBlanc = pierreBlanc;
        this.pierreNoir = pierreNoir;
        this.joueurActuel = joueurActuel;
        this.pointBlanc = pointBlanc;
        this.pointNoir = pointNoir;
    }

    // Méthode pour passer son tour
    public void passerTour(){
        if(joueurActuel == 1){
            joueurActuel = 2;
        }else{
            joueurActuel = 1;
        }
    }
    
    public int[] demanderCoup() {
        String coup = JOptionPane.showInputDialog("Joueur " + joueurActuel + " Entrez la position X et la position Y pour poser la pierre (x,y) ou bien passer votre tour" );
        if (coup.equalsIgnoreCase("passer")) { // Vérifie si le joueur veut passer
            passerTour();
            return null; // Retourne null pour indiquer qu'aucun coup n'a été joué
        }
        String[] sCoup = coup.split(" ");
        int posX = Integer.parseInt(sCoup[0]);
        int posY = Integer.parseInt(sCoup[1]);
        return new int[]{posX, posY};
    }

    // Méthode pour changer de joueur après chaque tour
    public void changerJoueur() {
        joueurActuel = (joueurActuel == 1) ? 2 : 1;
    }

}

    /*
     * int joueurActuel = 1;
     * 
     * String coup = JOptionPane.
     * showInputDialog("Entrez la position X et la Position Y pour poser la pierre"
     * );
     * String[] sCoup = coup.split(" ");
     * int posX = Integer.parseInt(sCoup[0]);
     * int posY = Integer.parseInt(sCoup[1]);
     * 
     * // Fonction qui verifie la position des pierres
     * public boolean rock_taken(int posX, int posY) {
     * if (plateau[posX][posY] == '.') {
     * return true;
     * // On pose la pierre
     * }
     * return false; // DMD UN AUTRE COUP
     * }
     * 
     * 
     * // alterne entre joueur 1 et joueur 2
     * /*public jouer(int joueurActuel) {
     * 
     * if joueurActuel == 1
     * {
     * this.plateau[][] = w
     * 
     * 
     * 
     * 
     * }joueurActuel = 1 - joueurActuel;
     * }
     */

