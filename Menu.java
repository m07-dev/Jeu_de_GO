import javax.swing.JOptionPane;

public class Menu {
    // 1 = Humain vs Humain
    // 2 = IA vs Humain
    public static int afficherMenu() {
        String[] options = { "Humain vs Humain", "IA vs Humain" };
        int choix = JOptionPane.showOptionDialog(
            null,
            "Choisissez un mode de jeu :",
            "Menu",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]
        );

        // choix == 0 → Humain vs Humain
        // choix == 1 → IA vs Humain
        return (choix == 1) ? 2 : 1;
    }
}