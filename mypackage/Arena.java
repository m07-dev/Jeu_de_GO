package mypackage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Arena {
    public int joueurActuel;
    public char pierreBlanc;
    public char pierreNoir; // 1 = blanc, 2 = noir
    int pointBlanc;
    int pointNoir;
    public int nbr_passerTour;

    public Arena(char pierreBlanc, char pierreNoir, int joueurActuel, int pointBlanc, int pointNoir) {
        this.pierreBlanc = pierreBlanc;
        this.pierreNoir = pierreNoir;
        this.joueurActuel = joueurActuel;
        this.pointBlanc = pointBlanc;
        this.pointNoir = pointNoir;
        this.nbr_passerTour = 0;
    }

    // Méthode pour passer son tour
    public void passerTour(){
        if(joueurActuel == 1){
            joueurActuel = 2;
        }else{
            joueurActuel = 1;
        }
        this.nbr_passerTour++;
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
        this.nbr_passerTour = 0;
        return new int[]{posX, posY};
    }

    // Méthode pour changer de joueur après chaque tour
    public void changerJoueur() {
        joueurActuel = (joueurActuel == 1) ? 2 : 1;
    }

    // Méthode pour former un groupe de Pierre
    public int groupePierre(int posX, int posX){
        List listGroupeVistee = new ArrayList<>();
        while(listGroupe.size() < 10){

        }
    }
}


