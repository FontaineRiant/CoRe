package ch.cor;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;


/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief Classe utilitaire pour générer et mélanger des couleurs
 */
public class ColorUtils {
    private static Random random = new Random();

    /**
     * Enum Color effectue la correspondance entre la couleur logique du jeu
     * et la couleur d'affichage à l'écran.
     */
    public enum Color {
        BLACK(com.badlogic.gdx.graphics.Color.BLACK),
        RED(com.badlogic.gdx.graphics.Color.RED),
        ORANGE(com.badlogic.gdx.graphics.Color.ORANGE),
        YELLOW(com.badlogic.gdx.graphics.Color.YELLOW),
        GREEN(com.badlogic.gdx.graphics.Color.GREEN),
        BLUE(com.badlogic.gdx.graphics.Color.CYAN),
        PURPLE(com.badlogic.gdx.graphics.Color.VIOLET),
        WHITE(com.badlogic.gdx.graphics.Color.WHITE);

        private com.badlogic.gdx.graphics.Color color;

        /**
         * Constructeur privé
         * @param color la couleur d'affichage
         */
        Color(com.badlogic.gdx.graphics.Color color) {
            this.color = color;
        }

        /**
         * @return la couleur d'affichage
         */
        public com.badlogic.gdx.graphics.Color getValue() {
            return color;
        }
    }

    private ColorUtils(){}

    /**
     * Retourne une couleur du jeu aléatoire qui n'est ni noire ni blanche
     * @return Color alétoire
     */
    public static Color getRandomNonBlackOrWhiteColor() {
        return Color.values()[random.nextInt(Color.values().length-2)+1];
    }

    /**
     * @return Color une couleur froide aléatoire
     */
    public static Color getRandomColdColor() {
        return Color.values()[random.nextInt(3)+4];
    }

    /**
     * @return Color une couleur chaude aléatoire
     */
    public static Color getRandomWarmColor() {
        return Color.values()[random.nextInt(3)+1];
    }

    /**
     * @param col
     * @return true ssi l'argument n'est pas un mélange de couleur
     */
    private static boolean isPrimary(Color col) {
        return col == Color.RED || col == Color.YELLOW || col == Color.BLUE;
    }

    /**
     * @param a subcolor
     * @param b color
     * @return true ssi l'argument a est une couleur composant la couleur b (par mélange)
     */
    public static boolean isASubColor(Color a, Color b) {
        if ((a == Color.BLACK || b == Color.WHITE) && b != a) {
            return true;
        } else if (a == Color.YELLOW && (b == Color.GREEN || b == Color.ORANGE)) {
            return true;
        } else if (a == Color.BLUE && (b == Color.PURPLE || b == Color.GREEN)) {
            return true;
        } else if (a == Color.RED && (b == Color.ORANGE || b == Color.PURPLE)) {
            return true;
        } else if (b == Color.BLACK) {
            return true;
        }

        return false;
    }

    /**
     * @param a
     * @param b
     * @return la couleur correspondant au mélange de a et b
     */
    public static Color add(Color a, Color b) {
        if (a == b) {
            return a;
        } else if (a == Color.WHITE) {
            return b;
        } else if (b == Color.WHITE) {
            return a;
        } else if (a == Color.BLACK || b == Color.BLACK || !isPrimary(a) || !isPrimary(b)) {
            return Color.BLACK;
        } else if ((a == Color.BLUE && b == Color.YELLOW) || (a == Color.YELLOW && b == Color.BLUE)) {
            return Color.GREEN;
        } else if ((a == Color.BLUE && b == Color.RED) || (a == Color.RED && b == Color.BLUE)) {
            return Color.PURPLE;
        } else if ((a == Color.RED && b == Color.YELLOW) || (a == Color.YELLOW && b == Color.RED)) {
            return Color.ORANGE;
        } else {
            return Color.WHITE;
        }
    }
}
