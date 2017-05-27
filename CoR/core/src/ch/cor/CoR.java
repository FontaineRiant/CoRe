package ch.cor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class CoR extends ApplicationAdapter {
    private SpriteBatch batch;
    private ArrayList<Music> playList;
    private int musicIndex;
    private BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();

        // joueur
        EntityManager.getInstance().addEntity(new Player(100, Gdx.graphics.getHeight() / 2));

        // musique
        playList = new ArrayList<Music>();
        FileHandle dir = Gdx.files.internal("music");
        FileHandle[] directoryListing = dir.list(".mp3");
        if (directoryListing != null) {
            for (FileHandle child : directoryListing) {
                playList.add(Gdx.audio.newMusic(child));
            }
        }
        musicIndex = new Random().nextInt(playList.size());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!playList.get(musicIndex).isPlaying()) {
            musicIndex = (++musicIndex) % playList.size();
            playList.get(musicIndex).play();
        }

        EntityManager.getInstance().updateAll();

        // draw le batch (tout en une fois, entre batch.begin() et batch.end() pour les performances)
        batch.begin();
        EntityManager.getInstance().drawAll(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Music mu : playList) {
            mu.dispose();
        }
    }
}
