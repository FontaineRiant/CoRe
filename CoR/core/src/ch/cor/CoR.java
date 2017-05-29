package ch.cor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief Boucle principale de jeu. Gère la musique et l'EntityManager
 * Cette classe est utilisée par libgdx
 */
public class CoR extends ApplicationAdapter {
    private SpriteBatch batch;
    private ArrayList<Music> playList;
    private int musicIndex;

    /**
     * Méthode exécutée au démarrage du jeu par libgdx
     */
    @Override
    public void create() {
        batch = new SpriteBatch();

        // on récupère les fichiers de musique (.mp3)
        playList = new ArrayList<Music>();
        FileHandle dir = Gdx.files.internal("music");
        FileHandle[] directoryListing = dir.list(".mp3");
        if (directoryListing != null) {
            for (FileHandle child : directoryListing) {
                playList.add(Gdx.audio.newMusic(child));
            }
        }

        // on choisit une musique aléatoire
        musicIndex = new Random().nextInt(playList.size());
    }

    /**
     * Boucle principale exécutée une fois par frame (60 fois par seconde par défaut) par libgdx
     */
    @Override
    public void render() {
        // couleur de fond
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // si la musique s'est terminée, on passe à la suivante sur la playlist
        if (!playList.get(musicIndex).isPlaying()) {
            musicIndex = (musicIndex + 1) % playList.size();
            playList.get(musicIndex).play();
        }

        // mise à jour de toutes les entités
        EntityManager.getInstance().updateAll();

        // draw le batch (tout en une fois, entre batch.begin() et batch.end() pour les performances)
        batch.begin();
        // dessine toutes les entités
        EntityManager.getInstance().drawAll(batch);
        batch.end();
    }

    /**
     * Libération des ressources. Appelée par libgdx à la fermeture du jeu.
     */
    @Override
    public void dispose() {
        batch.dispose();
        for (Music mu : playList) {
            mu.dispose();
        }
    }
}
