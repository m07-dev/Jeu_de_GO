package mypackage;

public class AreneTest {

    public static void main(String[] args) {
        testCalculerTailleZoneVide();
        testGroupePierre();
        testEstZonePrivatisee();
        testCompterGroupesVivants(); 
        testCompterGroupesVivants(); 
        testAucunCoupLegalPossible();
    }

    // Teste la fonction de calcul de la taille d'une zone vide
    public static void testCalculerTailleZoneVide() {
        char[][] plateau = {
            { 'w', 'w', 'w', 'b', '·' },
            { 'w', '.', 'w', 'w', 'b' },
            { 'w', '.', 'w', '.', 'w' },
            { 'w', '.', '.', '·', 'w' },
            { 'w', 'w', 'w', 'w', '·' }
        };
        int[][] visitee = new int[5][5];
        Arena arene = new Arena('w', 'b', 1, 0, 0);
        int tailleZone = arene.calculerTailleZoneVide(2, 1, plateau, visitee);

        System.out.println("Test calculerTailleZoneVide :");
        System.out.println("Taille attendue : 3, taille obtenue : " + tailleZone);
        System.out.println("Tableau visitee :");
        for (int i = 0; i < visitee.length; i++) {
            for (int j = 0; j < visitee[i].length; j++) {
                System.out.print(visitee[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Teste la fonction de groupe de pierres
    public static void testGroupePierre() {
        char[][] plateau = {
            { 'w', 'w', 'w', 'b', '·' },
            { 'w', '.', 'w', 'b', 'b' },
            { 'w', '.', 'w', 'b', '·' },
            { 'w', 'w', 'w', '·', '·' },
            { 'w', 'w', 'w', '·', '·' }
        };
        boolean[][] caseVisitee = new boolean[5][5];
        Arena arene = new Arena('w', 'b', 1, 0, 0);
        arene.groupePierre(2, 1, plateau, caseVisitee, 1);

        int cpt = 0;
        for (int i = 0; i < caseVisitee.length; i++) {
            for (int j = 0; j < caseVisitee[i].length; j++) {
                if (caseVisitee[i][j]) cpt++;
            }
        }

        System.out.println("Test groupePierre :");
        System.out.println("Nombre de pierres dans le groupe (attendu : 13) : " + cpt);
        System.out.println();
    }

    // Teste la fonction de détection de zone privatisée
    public static void testEstZonePrivatisee() {
        char[][] plateau = {
            { 'w', 'w', 'w', 'b', '·' },
            { 'w', '.', 'w', 'w', 'b' },
            { 'w', '.', 'w', '.', 'w' },
            { 'w', '.', '.', '·', 'w' },
            { 'w', 'w', 'w', 'w', '·' }
        };
        int[][] visitee = new int[5][5];
        Arena arene = new Arena('w', 'b', 1, 0, 0); // joueurActuel = noir
        boolean priv = arene.estZonePrivatisee(2, 1, plateau, 2, visitee);

        System.out.println("Test estZonePrivatisee :");
        System.out.println("Zone attendue comme privatisée (true) : " + priv);
        System.out.println();
    }

    // Teste le comptage des points dans les groupes vivants (groupes >= 10)
    public static void testCompterGroupesVivants() {
        // Plateau 5x5 avec un grand groupe blanc et un groupe noir
        char W = 'w', B = 'b', V = '·';
        char[][] plateau = {
            { W, W, W, B, B },
            { W, W, W, B, B },
            { W, W, W, B, B },
            { W, W, W, B, B },
            { W, W, W, V, V }
        };
        Arena arene = new Arena('w', 'b', 1, 0, 0);

        // On suppose que tu as bien ajouté la méthode compterGroupesVivants dans Arena
        arene.compterGroupesVivants(plateau);

        System.out.println("Test compterGroupesVivants :");
        System.out.println("Points Blanc attendus : 15.5, obtenus : " + arene.pointBlanc);
        System.out.println("Points Noir attendus : 8, obtenus : " + arene.pointNoir);

        if (arene.pointBlanc > arene.pointNoir) {
            System.out.println("Résultat attendu : Blanc gagne.");
        } else if (arene.pointNoir > arene.pointBlanc) {
            System.out.println("Résultat attendu : Noir gagne.");
        } else {
            System.out.println("Résultat attendu : Égalité.");
        }
        System.out.println();
    }

    public static void testAucunCoupLegalPossible() {
        // Cas 1 : Aucun coup légal possible
        // On crée un plateau où aucune case ne permet de poser une pierre (supposons avec des pierres partout ou zones privatisées)
        char[][] plateauBloque = {
            { 'w', 'b', 'w', 'b', 'w' },
            { 'b', 'w', 'b', 'w', 'b' },
            { 'w', 'b', 'w', 'b', 'w' },
            { 'b', 'w', 'b', 'w', 'b' },
            { 'w', 'b', 'w', 'b', 'w' }
        };
        Arena arene = new Arena('w', 'b', 1, 0, 0);
        boolean resultBloque = arene.aucunCoupLegalPossible(plateauBloque);
        System.out.println("Test aucunCoupLegalPossible (aucun coup possible) : attendu=true, obtenu=" + resultBloque);
    
            // Cas 2 : Au moins un coup légal possible (plateau avec cases vides)
        char[][] plateauLibre = {
            { '·', '·', '·', '·', '·' },
            { '·', '·', '·', '·', '·' },
            { '·', '·', '·', '·', '·' },
            { '·', '·', '·', '·', '·' },
            { '·', '·', '·', '·', '·' }
        };
        boolean resultLibre = arene.aucunCoupLegalPossible(plateauLibre);
        System.out.println("Test aucunCoupLegalPossible (au moins un coup possible) : attendu=false, obtenu=" + resultLibre);
    }
        
}
    

