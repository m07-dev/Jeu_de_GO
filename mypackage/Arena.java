package mypackage;
import javax.swing.JOptionPane;

public class Arena {
    public int joueurActuel; // 1 = blanc, 2 = noir
    public char pierreBlanc;
    public char pierreNoir; 
    public double  pointBlanc;
    public double  pointNoir;
    public int nbr_passerTour;

    public Arena(char pierreBlanc, char pierreNoir, int joueurActuel, double  pointBlanc, double  pointNoir) {
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
                this.nbr_passerTour = 0;
                return new int[]{posX, posY};
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez entrer des entiers valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Méthode pour changer de joueur après chaque tour
    public void changerJoueur() {
        joueurActuel = (joueurActuel == 1) ? 2 : 1;
    }

    public void groupePierre(int posX, int posY, char[][] getPlateau, boolean[][] caseVisitee, int joueurActuel) {
        // Vérifie si la position est hors limites
        if (Plateau.enDehorsDesLimites(posX, posY, getPlateau)) {
            return; // Arrête la récursion si la position est hors limites
        }
        // Vérifie si la case a déjà été visitée
        if (caseVisitee[posX][posY]) {
            return; // Arrête la récursion si la case a déjà été visitée
        }
        // Vérifie si la case contient une pierre du joueur actuel
        char pierreActuelle = (joueurActuel == 1) ? 'w' : 'b';
        if (getPlateau[posX][posY] != pierreActuelle) {
            return; // Arrête la récursion si la case ne contient pas une pierre du joueur actuel
        }
        // Marque la case comme visitée
        caseVisitee[posX][posY] = true;
        // Explore les cases adjacentes
        groupePierre(posX, posY - 1, getPlateau, caseVisitee, joueurActuel); // Haut
        groupePierre(posX, posY + 1, getPlateau, caseVisitee, joueurActuel); // Bas
        groupePierre(posX - 1, posY, getPlateau, caseVisitee, joueurActuel); // Gauche
        groupePierre(posX + 1, posY, getPlateau, caseVisitee, joueurActuel); // Droite

        // Comptage des Points
        double cpt = 0;
        for (int i = 0; i < caseVisitee.length; i++) {
            for (int j = 0; j < caseVisitee[i].length; j++) {
                if (caseVisitee[i][j]) {
                    cpt++;
                }
            }
        }
        if (cpt >= 10) {
            if (joueurActuel == 1) {
                this.pointBlanc = this.pointBlanc + cpt; // Attribue les points au joueur blanc
            } else {
                this.pointNoir = this.pointNoir + cpt; // Attribue les points au joueur noir
            }
        }
        
    }

    public int calculerTailleZoneVide(int posX, int posY, char[][] getPlateau, int[][] visitee) {
        if (Plateau.enDehorsDesLimites(posX, posY, getPlateau)) {
            return 0;
        }
    
        // Si déjà visité, on ne refait pas
        if (visitee[posX][posY] != 0) {
            return 0;
        }
    
        char pierreActuelle = (joueurActuel == 1) ? 'w' : 'b';
        char pierreAdverse = (joueurActuel == 1) ? 'b' : 'w';
    
        if (getPlateau[posX][posY] == pierreActuelle) {
            visitee[posX][posY] = 1; // Marquer : 1 = du joueur actuel
            return 0;
        } else if (getPlateau[posX][posY] == pierreAdverse) {
            visitee[posX][posY] = 2; // Marquer : 2 = pierre ennemie
            return 0;
        }
    
        // C’est une case vide
        visitee[posX][posY] = 3; // 3 = vide
    
        int taille = 1;
    
        taille += calculerTailleZoneVide(posX - 1, posY, getPlateau, visitee);
        taille += calculerTailleZoneVide(posX + 1, posY, getPlateau, visitee);
        taille += calculerTailleZoneVide(posX, posY - 1, getPlateau, visitee);
        taille += calculerTailleZoneVide(posX, posY + 1, getPlateau, visitee);
    
        return taille;
    }

    // faire le methode non sucide ! ! ! ! ! !

    public boolean estZonePrivatisee(int x, int y, char[][] plateau, int joueurActuel, int[][] visitee) {
        int taille = calculerTailleZoneVide(x, y, plateau, visitee);
    
        if (taille > 10) return false; // Pas une zone restreinte
    
        char pierreAdverse = (joueurActuel == 1) ? 'b' : 'w';
    
        // Vérifie si une pierre adverse est autour de la zone vide
        for (int i = 0; i < visitee.length; i++) {
            for (int j = 0; j < visitee[i].length; j++) {
                if (visitee[i][j] == 2) {
                    return true; // La zone est bien privatisée par l’adversaire
                }
            }
        }
        return false;
    }
    
    // Compte toutes les pierres dans les groupes vivants de chaque joueur
    public void compterGroupesVivants(char[][] plateau) {
        int taille = plateau.length;
        boolean[][] visite = new boolean[taille][taille];
        int totalBlanc = 0;
        int totalNoir = 0;

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (!visite[i][j]) {
                    if (plateau[i][j] == pierreBlanc) {
                        int cpt = compterGroupe(i, j, plateau, visite, pierreBlanc);
                        totalBlanc += cpt;
                    } else if (plateau[i][j] == pierreNoir) {
                        int cpt = compterGroupe(i, j, plateau, visite, pierreNoir);
                        totalNoir += cpt;
                    }
                }
            }
        }
        // Ajoute la prime de 0,5 au blanc
        this.pointBlanc = totalBlanc + 0.5;
        this.pointNoir = totalNoir;
    }

    // Compte la taille d'un groupe connecté d'une couleur donnée
    private int compterGroupe(int x, int y, char[][] plateau, boolean[][] visite, char couleur) {
        if (x < 0 || x >= plateau.length || y < 0 || y >= plateau.length) return 0;
        if (visite[x][y] || plateau[x][y] != couleur) return 0;
        visite[x][y] = true;
        int cpt = 1;
        cpt += compterGroupe(x + 1, y, plateau, visite, couleur);
        cpt += compterGroupe(x - 1, y, plateau, visite, couleur);
        cpt += compterGroupe(x, y + 1, plateau, visite, couleur);
        cpt += compterGroupe(x, y - 1, plateau, visite, couleur);
        return cpt;
    }

    
    /*public static void testCalculerTailleZoneVide() {
        // Création d'un petit plateau 5x5
        char[][] plateau = {
            { 'w', 'w', 'w', 'b', '·' },
            { 'w', '·', 'w', '·', 'b' },
            { 'w', 'b', 'w', 'b', '·' },
            { 'w', '·', 'w', '·', '·' },
            { 'w', 'w', 'w', '·', '·' }
        };
    
        int[][] visitee = new int[5][5];
    
        // Création d'une arène avec joueur actuel = 1 (blanc)
        Arena arene = new Arena('w', 'b', 1, 0, 0);
    
        // Position de départ pour l'exploration de la zone vide
        int tailleZone = arene.calculerTailleZoneVide(1, 1, plateau, visitee);
    
        // Affichage du résultat
        System.out.println("Taille de la zone vide = " + tailleZone);
    
        System.out.println("Tableau visitee :");
        for (int i = 0; i < visitee.length; i++) {
            for (int j = 0; j < visitee[i].length; j++) {
                System.out.print(visitee[i][j] + " ");
            }
            System.out.println();
        }
    }*/

}
