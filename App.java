import graphics.Color;
import graphics.Ellipse;
import graphics.Rectangle;
import java.util.Random;
import javax.swing.JOptionPane;
import mypackage.Arena;
import mypackage.Plateau;

public class App {

    // Taille d'une case en pixels pour l'affichage
    public static int TAILLE_CASE = 30;

    public static void main(String[] args) {
        // Demande la taille du goban à l'utilisateur
        int tailleArene = Integer.valueOf(JOptionPane.showInputDialog("Quel est la taille de votre Goban"));

        // Affiche le menu et récupère le mode choisi (1 = joueur contre joueur, 2 = IA vs humain)
        int mode = Menu.afficherMenu();

        // Création du plateau et de l'arène de jeu
        Plateau goban = new Plateau(tailleArene);
        Arena arene = new Arena('w', 'b', 2, 0.5, 0);

        boolean finDeJeu = false;
        Random random = new Random();

        // Boucle principale du jeu
        while (!finDeJeu) {
            // Fin si le plateau est plein ou aucun coup légal possible
            if (Plateau.plateauRempli(goban.getPlateau()) || arene.aucunCoupLegalPossible(goban.getPlateau())) {
                JOptionPane.showMessageDialog(null, "Plus aucun coup légal n'est possible. Fin de la partie.", "FIN DU JEU", JOptionPane.INFORMATION_MESSAGE);
                finDeJeu = true;
                break;
            }

            int[] coup;

            // Si mode IA et c'est au tour de l'IA
            if (mode == 2 && arene.joueurActuel == 1) {
                // L'IA choisit aléatoirement un coup légal
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
                // Sinon, on demande le coup au joueur humain
                coup = arene.demanderCoup();
            }

            // Fin si les deux joueurs ont passé leur tour
            if (arene.nbr_passerTour == 2) {
                JOptionPane.showMessageDialog(null, "Le Jeu est Terminé, vous avez tous les deux passé votre tour", "FIN DU JEU", JOptionPane.INFORMATION_MESSAGE);
                finDeJeu = true;
                break;
            }

            // Si un coup a été joué
            if (coup != null) {
                int x = coup[0];
                int y = coup[1];
                // Choisit la pierre en fonction du joueur
                char pierre = (arene.joueurActuel == 1) ? arene.pierreBlanc : arene.pierreNoir;

                // Vérifie que la pierre peut être posée ici
                if (goban.peutPoserPierre(x, y, goban.getPlateau())) {
                    int[][] visitee = new int[Plateau.taille][Plateau.taille];
                    // Calcule la taille de la zone vide autour du coup
                    int tailleZone = arene.calculerTailleZoneVide(x, y, goban.getPlateau(), visitee);

                    // Vérifie si la zone est privatisée par l’adversaire
                    if (tailleZone <= 10 && arene.estZonePrivatisee(x, y, goban.getPlateau(), arene.joueurActuel, visitee)) {
                        // Si c’est l’IA, elle rejoue
                        if (mode == 2 && arene.joueurActuel == 1) continue;

                        // Sinon, message d'erreur à l'utilisateur
                        JOptionPane.showMessageDialog(null,
                            "Vous ne pouvez pas jouer dans une zone privatisée par l’adversaire (zone ≤ 10 cases)",
                            "Zone interdite", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    // Pose la pierre sur le plateau
                    goban.poserPierre(x, y, pierre, goban.getPlateau());

                    boolean[][] caseVisitee = new boolean[Plateau.taille][Plateau.taille];
                    // Met à jour les groupes de pierres (captures éventuelles)
                    arene.groupePierre(x, y, goban.getPlateau(), caseVisitee, arene.joueurActuel);

                    // Change de joueur
                    arene.changerJoueur();

                    // Affiche le plateau graphiquement
                    String[][] affichage = convertirPlateau(goban.getPlateau());
                    affiche_tab(affichage);
                } else {
                    // Coup invalide, affiche un message d’erreur
                    JOptionPane.showMessageDialog(null, "Vous ne pouvez pas poser de pierre ici ! Veuillez choisir un autre endroit.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Le joueur a passé son tour
                System.out.println("Le joueur " + arene.joueurActuel + " a passé son tour.");
            }
        }

        // À la fin, compte les groupes vivants pour calculer les points
        arene.compterGroupesVivants(goban.getPlateau());

        // Affiche le résultat final
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

    // Affiche graphiquement le plateau
    public static void affiche_tab(String[][] tab) {
        int taille = tab.length;

        // Fond du plateau
        Rectangle fond = new Rectangle(0, 0, taille * TAILLE_CASE, taille * TAILLE_CASE);
        fond.setColor(Color.DARK_GRAY);
        fond.fill();

        // Parcours des cases pour dessiner chaque case et jeton
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                Rectangle cell = new Rectangle(j * TAILLE_CASE, i * TAILLE_CASE, TAILLE_CASE, TAILLE_CASE);
                cell.setColor(Color.BLACK);
                cell.draw();

                // Dessine le jeton si présent (noir ou blanc)
                dessiner_jeton(j, i, tab[i][j]);
            }
        }
    }

    // Dessine un jeton noir ou blanc dans la case donnée
    public static void dessiner_jeton(int j, int i, String valeur) {
        if (valeur.equals("N") || valeur.equals("B")) {
            Ellipse jeton = new Ellipse(j * TAILLE_CASE, i * TAILLE_CASE, TAILLE_CASE, TAILLE_CASE);
            if (valeur.equals("N")) {
                jeton.setColor(Color.BLACK);
            } else {
                jeton.setColor(Color.WHITE);
            }
            jeton.fill();
        }
    }

    // Convertit le plateau de char en tableau de String pour l’affichage
    public static String[][] convertirPlateau(char[][] plateau) {
        String[][] tab = new String[plateau.length][plateau.length];
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                if (plateau[i][j] == 'b') tab[i][j] = "N";      // pierre noire
                else if (plateau[i][j] == 'w') tab[i][j] = "B"; // pierre blanche
                else tab[i][j] = "";                            // case vide
            }
        }
        return tab;
    }
}
