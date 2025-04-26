public class Arena {

    char pierreBlanc;
    char pierreNoir;
    int joueurActuel;

    public Arena(char pierreBlanc,char pierreNoir, int joueurActuel){
        this.pierreBlanc = 'w';
        this.pierreNoir = 'b';
        this.joueurActuel = 2;
    }
    /*
    int joueurActuel = 1;

     String coup = JOptionPane.showInputDialog("Entrez la position X et la Position Y pour poser la pierre");
    String[] sCoup = coup.split(" ");
    int posX = Integer.parseInt(sCoup[0]);
    int posY = Integer.parseInt(sCoup[1]);

    // Fonction qui verifie la position des pierres
    public boolean rock_taken(int posX, int posY) {
        if (plateau[posX][posY] == '.') {
            return true;
            // On pose la pierre
        }
        return false; // DMD UN AUTRE COUP
    }


    // alterne entre joueur 1 et joueur 2
    /*public jouer(int joueurActuel) {

        if joueurActuel == 1 
             {
            this.plateau[][] = w


        
        
        }joueurActuel = 1 - joueurActuel;
    }*/
     
     
}
