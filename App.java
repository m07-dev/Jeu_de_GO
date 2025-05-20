import java.util.Random;
import javax.swing.JOptionPane;
import mypackage.Arena;
import mypackage.Plateau;

public class App {

    public static void main(String[] args) {
        int mode = Menu.afficherMenu(); // 1 ou 2
        Plateau goban = new Plateau(10);
        Arena arene = new Arena('w', 'b', 2, 0.5, 0);
        boolean finDeJeu = false;
        Random random = new Random();

        while (!finDeJeu) {
            if (Plateau.plateauRempli(goban.getPlateau())) {
                JOptionPane.showMessageDialog(
                    null,
                    "Le plateau est rempli ! Fin de la partie.",
                    "FIN DU JEU",
                    JOptionPane.INFORMATION_MESSAGE
                );
                finDeJeu = true;
                break;
            }

            int[] coup;

            // IA joue si mode IA vs Humain et c'est à l'IA
            if (mode == 2 && arene.joueurActuel == 1) {
                do {
                    int x = random.nextInt(Plateau.taille);
                    int y = random.nextInt(Plateau.taille);
                    if (Plateau.peutPoserPierre(x, y, goban.getPlateau())) {
                        coup = new int[] { x, y };
                        break;
                    } else {
                        coup = null;
                    }
                } while (true);
                JOptionPane.showMessageDialog(null, "L'IA a joué en " + coup[0] + " " + coup[1]);
            } else {
                // Joueur humain
                coup = arene.demanderCoup();
            }

            // Fin si 2 passes consécutives
            if (arene.nbr_passerTour == 2) {
                JOptionPane.showMessageDialog(
                    null,
                    "Le Jeu est Terminé, vous avez tous les deux passé votre tour",
                    "FIN DU JEU",
                    JOptionPane.INFORMATION_MESSAGE
                );
                finDeJeu = true;
                break;
            }

            if (coup != null) {
                int x = coup[0];
                int y = coup[1];
                char pierre = (arene.joueurActuel == 1) ? arene.pierreBlanc : arene.pierreNoir;

                if (goban.peutPoserPierre(x, y, goban.getPlateau())) {
                    int[][] visitee = new int[Plateau.taille][Plateau.taille];
                    int tailleZone = arene.calculerTailleZoneVide(x, y, goban.getPlateau(), visitee);

                    if (tailleZone <= 10 && arene.estZonePrivatisee(x, y, goban.getPlateau(), arene.joueurActuel, visitee)) {
                        if (mode == 2 && arene.joueurActuel == 1) {
                            continue; // IA rejoue
                        }
                        JOptionPane.showMessageDialog(null,
                            "Vous ne pouvez pas jouer dans une zone privatisée par l’adversaire (zone ≤ 10 cases)",
                            "Zone interdite",
                            JOptionPane.ERROR_MESSAGE
                        );
                        continue;
                    }

                    goban.poserPierre(x, y, pierre, goban.getPlateau());

                    boolean[][] caseVisitee = new boolean[Plateau.taille][Plateau.taille];
                    arene.groupePierre(x, y, goban.getPlateau(), caseVisitee, arene.joueurActuel);

                    goban.afficherPlateau();
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

        // === FIN DE PARTIE : Calcul du score final et annonce du gagnant ===
        // Calcul des groupes vivants et attribution de la prime au blanc
        arene.compterGroupesVivants(goban.getPlateau());

        // Affichage du résultat
        String resultat = "Points Blanc : " + arene.pointBlanc + "\n" +
                          "Points Noir : " + arene.pointNoir + "\n";
        if (arene.pointBlanc > arene.pointNoir) {
            resultat += "Le joueur BLANC gagne !";
        } else if (arene.pointNoir > arene.pointBlanc) {
            resultat += "Le joueur NOIR gagne !";
        } else {
            resultat += "Égalité !";
        }
        JOptionPane.showMessageDialog(null, resultat, "Résultat final", JOptionPane.INFORMATION_MESSAGE);
    }
}
