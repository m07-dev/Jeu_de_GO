import javax.swing.JOptionPane;
import mypackage.Arena;
import mypackage.Plateau;

public class App {
    public static void main(String[] args) {
        Plateau goban = new Plateau(10);
        Arena arene = new Arena('w', 'b', 1, 0.5, 0);
        boolean finDeJeu = false;

        while (!finDeJeu) {
            int[] coup = arene.demanderCoup();

            if (arene.nbr_passerTour == 2) {
                JOptionPane.showMessageDialog(
                    null,
                    "Le Jeu est Terminé, vous avez tous les deux passé votre tour",
                    "FIN DU JEU",
                    JOptionPane.INFORMATION_MESSAGE
                );
                finDeJeu = true;
                continue;
            }

            if (coup != null) {
                int x = coup[0];
                int y = coup[1];
                char pierre = (arene.joueurActuel == 1) ? arene.pierreBlanc : arene.pierreNoir;
                if (goban.peutPoserPierre(x, y,goban.getPlateau())) {
                    goban.poserPierre(x, y, pierre,goban.getPlateau());
                    boolean[][] caseVisitee = new boolean[Plateau.taille][Plateau.taille];
                    arene.groupePierre(x, y, goban.getPlateau(), caseVisitee, arene.joueurActuel);
                    goban.afficherPlateau();

                    System.out.println("Points Blanc : " + arene.pointBlanc + " Points Noir : " + arene.pointNoir);

                    arene.changerJoueur();
                } else {
                    JOptionPane.showMessageDialog(
                        null,
                        "Vous ne pouvez pas poser de pierre ici ! Veuillez choisir un autre endroit.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                System.out.println("Le joueur " + arene.joueurActuel + " a passé son tour.");
            }
        }
    }
}