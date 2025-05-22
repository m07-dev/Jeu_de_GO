package mypackage;
import javax.swing.JOptionPane;

public class Arena {
    // Variables représentant l'état du jeu
    public int joueurActuel; // 1 = blanc, 2 = noir
    public char pierreBlanc; // symbole pierre blanche
    public char pierreNoir;  // symbole pierre noire
    public double pointBlanc; // score joueur blanc
    public double pointNoir;  // score joueur noir
    public int nbr_passerTour; // nombre de passes consécutives

    /**
     * Constructeur de l'arène (partie) avec initialisation des joueurs, pierres et scores
     */
    public Arena(char pierreBlanc, char pierreNoir, int joueurActuel, double pointBlanc, double pointNoir) {
        this.pierreBlanc = pierreBlanc;
        this.pierreNoir = pierreNoir;
        this.joueurActuel = joueurActuel;
        this.pointBlanc = pointBlanc;
        this.pointNoir = pointNoir;
        this.nbr_passerTour = 0;
    }

    /**
     * Méthode appelée pour passer son tour.
     * Change le joueur actuel et incrémente le compteur de passes.
     */
    public void passerTour() {
        if (joueurActuel == 1) {
            joueurActuel = 2;
        } else {
            joueurActuel = 1;
        }
        this.nbr_passerTour++;
    }
    
    /**
     * Demande au joueur actuel d'entrer un coup sous forme de coordonnées X Y.
     * Si le joueur annule ou tape "passer", il passe son tour.
     * Valide la saisie et retourne le coup sous forme d'un tableau int[] {x, y}.
     */
    public int[] demanderCoup() {
        while (true) {
            String coup = JOptionPane.showInputDialog("Joueur " + joueurActuel + " : Entrez la position X et Y séparés par un espace, ou cliquer 'annuler' pour passer votre tour.");
            
            if (coup == null || coup.equalsIgnoreCase("passer")) {
                passerTour();
                return null;
            }
    
            String[] sCoup = coup.trim().split(" ");
            if (sCoup.length != 2) {
                JOptionPane.showMessageDialog(null, "Veuillez entrer exactement deux nombres séparés par un espace.", "Erreur", JOptionPane.ERROR_MESSAGE);
                continue;
            }
    
            try {
                int posX = Integer.parseInt(sCoup[0]);
                int posY = Integer.parseInt(sCoup[1]);
                this.nbr_passerTour = 0; // remise à zéro car coup joué
                return new int[]{posX, posY};
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer des entiers valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Change le joueur actuel pour alterner les tours
     */
    public void changerJoueur() {
        joueurActuel = (joueurActuel == 1) ? 2 : 1;
    }

    /**
     * Méthode récursive qui marque toutes les pierres connectées du joueur actuel
     * à partir d'une position donnée, en marquant les cases visitées dans caseVisitee.
     */
    public void groupePierre(int posX, int posY, char[][] getPlateau, boolean[][] caseVisitee, int joueurActuel) {
        // Arrêt récursion si hors limites
        if (Plateau.enDehorsDesLimites(posX, posY, getPlateau)) return;
        // Arrêt récursion si déjà visité
        if (caseVisitee[posX][posY]) return;
        // Vérifie si la case contient une pierre du joueur actuel
        char pierreActuelle = (joueurActuel == 1) ? 'w' : 'b';
        if (getPlateau[posX][posY] != pierreActuelle) return;
        // Marque la case comme visitée
        caseVisitee[posX][posY] = true;
        // Explore récursivement les cases adjacentes (haut, bas, gauche, droite)
        groupePierre(posX, posY - 1, getPlateau, caseVisitee, joueurActuel);
        groupePierre(posX, posY + 1, getPlateau, caseVisitee, joueurActuel);
        groupePierre(posX - 1, posY, getPlateau, caseVisitee, joueurActuel);
        groupePierre(posX + 1, posY, getPlateau, caseVisitee, joueurActuel);
    }

    /**
     * Calcule la taille d'une zone vide contiguë à partir d'une position (posX, posY).
     * Utilise un tableau visitee pour marquer les cases déjà comptées (0 = non visité, 1 = joueur, 2 = adversaire, 3 = vide).
     * Retourne la taille (nombre de cases vides contiguës).
     */
    public int calculerTailleZoneVide(int posX, int posY, char[][] getPlateau, int[][] visitee) {
        // Si hors limites, retourne 0
        if (Plateau.enDehorsDesLimites(posX, posY, getPlateau)) return 0;
    
        // Si déjà visité, retourne 0
        if (visitee[posX][posY] != 0) return 0;
    
        char pierreActuelle = (joueurActuel == 1) ? 'w' : 'b';
        char pierreAdverse = (joueurActuel == 1) ? 'b' : 'w';
    
        // Si case contient pierre du joueur actuel, marque et retourne 0
        if (getPlateau[posX][posY] == pierreActuelle) {
            visitee[posX][posY] = 1;
            return 0;
        // Si case contient pierre adverse, marque et retourne 0
        } else if (getPlateau[posX][posY] == pierreAdverse) {
            visitee[posX][posY] = 2;
            return 0;
        }
    
        // Case vide, marque et compte cette case
        visitee[posX][posY] = 3;
        int taille = 1;
    
        // Ajoute récursivement les cases adjacentes vides
        taille += calculerTailleZoneVide(posX - 1, posY, getPlateau, visitee);
        taille += calculerTailleZoneVide(posX + 1, posY, getPlateau, visitee);
        taille += calculerTailleZoneVide(posX, posY - 1, getPlateau, visitee);
        taille += calculerTailleZoneVide(posX, posY + 1, getPlateau, visitee);
    
        return taille;
    }

    /**
     * Détermine si une zone est privatisée (contrôlée uniquement par l'adversaire)
     * Une zone est privatisée si elle fait ≤ 10 cases et contient des pierres adverses
     * mais aucune pierre du joueur actuel.
     */
    public boolean estZonePrivatisee(int x, int y, char[][] plateau, int joueurActuel, int[][] visitee) {
        int taille = calculerTailleZoneVide(x, y, plateau, visitee);
        if (taille > 10) return false; // trop grande pour être privatisée
    
        boolean aPierreJoueur = false;
        boolean aPierreAdverse = false;
    
        // Parcourt le tableau visitee pour vérifier présence de pierres
        for (int i = 0; i < visitee.length; i++) {
            for (int j = 0; j < visitee[i].length; j++) {
                if (visitee[i][j] == 1) aPierreJoueur = true;
                if (visitee[i][j] == 2) aPierreAdverse = true;
            }
        }
    
        // Zone privatisée si contient des pierres adverses et pas du joueur
        return aPierreAdverse && !aPierreJoueur;
    }
    
    /**
     * Compte tous les groupes vivants (≥ 10 pierres connectées) des deux joueurs,
     * et met à jour les points en conséquence.
     */
    public void compterGroupesVivants(char[][] plateau) {
        int taille = plateau.length;
        boolean[][] visite = new boolean[taille][taille];
        int totalBlanc = 0;
        int totalNoir = 0;

        // Parcourt tout le plateau
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                // Si case non visitée
                if (!visite[i][j]) {
                    if (plateau[i][j] == pierreBlanc) {
                        int cpt = compterGroupe(i, j, plateau, visite, pierreBlanc);
                        if (cpt >= 10) {
                            totalBlanc += cpt;
                        }
                    } else if (plateau[i][j] == pierreNoir) {
                        int cpt = compterGroupe(i, j, plateau, visite, pierreNoir);
                        if (cpt >= 10) {
                            totalNoir += cpt;
                        }
                    }
                }
            }
        }
    
        // Ajoute la prime de 0.5 au joueur blanc
        this.pointBlanc = totalBlanc + 0.5;
        this.pointNoir = totalNoir;
    }
    
    /**
     * Compte récursivement la taille d'un groupe connecté d'une couleur donnée.
     * Marque les cases visitées pour éviter les doubles comptes.
     */
    private int compterGroupe(int x, int y, char[][] plateau, boolean[][] visite, char couleur) {
        if (x < 0 || x >= plateau.length || y < 0 || y >= plateau.length) return 0;
        if (visite[x][y] || plateau[x][y] != couleur) return 0;
        visite[x][y] = true;
        int cpt = 1;
        // Explore récursivement les cases adjacentes
        cpt += compterGroupe(x + 1, y, plateau, visite, couleur);
        cpt += compterGroupe(x - 1, y, plateau, visite, couleur);
        cpt += compterGroupe(x, y + 1, plateau, visite, couleur);
        cpt += compterGroupe(x, y - 1, plateau, visite, couleur);
        return cpt;
    }

   // Vérifie s'il n'existe aucun coup légal possible pour le joueur actuel
    public boolean aucunCoupLegalPossible(char[][] plateau) {
        for (int x = 0; x < plateau.length; x++) {
            for (int y = 0; y < plateau.length; y++) {
                // Si on peut poser une pierre en (x,y)
                if (Plateau.peutPoserPierre(x, y, plateau)) {
                    int[][] visitee = new int[plateau.length][plateau.length];
                    int tailleZone = this.calculerTailleZoneVide(x, y, plateau, visitee);
                    // Si la zone n'est pas privatisée ou si elle est trop grande, c'est un coup légal
                    if (tailleZone > 10 || !this.estZonePrivatisee(x, y, plateau, this.joueurActuel, visitee)) {
                        return false; // Au moins un coup légal possible
                    }
                }
            }
        }
        return true; // Aucun coup légal possible
    }


}
