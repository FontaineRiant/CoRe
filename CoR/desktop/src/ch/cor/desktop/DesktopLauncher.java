package ch.cor.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ch.cor.CoR;

/**
 * @brief Classe contenant la méthode main, point d'entrée de l'application desktop
 */
public class DesktopLauncher {
    public static void main(String[] arg) {
        // configure l'application desktop (résolution, titre, non redimensionable)
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1600;
        config.height = 800;
        config.resizable = false;
        config.title = "CoRe";

        // lance l'application
        new LwjglApplication(new CoR(), config);
    }
}
