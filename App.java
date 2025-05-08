import javax.swing.JOptionPane;
import mypackage.Arena;
import mypackage.Plateau;

public class App {
    public static void main(String[] args) {
        Plateau Goban = new Plateau(10);
        Arena Arene = new Arena('w', 'b', 2, 0, 0);
        boolean finDeJeu = true;

        while (finDeJeu) { 
        int[] coup = Arene.demanderCoup();
        if (coup != null) {
            int x = coup[0];
            int y = coup[1];
            char pierre = (Arene.joueurActuel == 1) ? Arene.pierreBlanc : Arene.pierreNoir;
            if (Goban.peutPoserPierre(x, y)) {
                Goban.poserPierre(x, y, pierre);
                Goban.afficherPlateau();
                Arene.changerJoueur();
            } else {
                JOptionPane.showMessageDialog(null,"Vous ne pouvez pas poser de pierre ici ! Veuillez choisir un autre endroit","Attention", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            System.out.println("Le joueur " + Arene.joueurActuel + " a passé son tour.");
        }
    }
    }
}
