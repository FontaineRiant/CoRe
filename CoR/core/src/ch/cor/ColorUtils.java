package ch.cor;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 05.05.17
 */
public class ColorUtils {
    private static Random random = new Random();

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

        Color(com.badlogic.gdx.graphics.Color color) {
            this.color = color;
        }

        public com.badlogic.gdx.graphics.Color getValue() {
            return color;
        }
    }

    public static Color getRandomNonBlackOrWhiteColor() {
        return Color.values()[random.nextInt(Color.values().length-2)+1];
    }

    private static boolean isPrimary(Color col) {
        return col == Color.RED || col == Color.YELLOW || col == Color.BLUE;
    }

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
