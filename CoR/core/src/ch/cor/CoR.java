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
import java.util.LinkedList;
import java.util.Random;

public class CoR extends ApplicationAdapter {
    private static final int STAR_COUNT = 1000;
    public static int points;
    private boolean paused = false;
    private SpriteBatch batch;
    private LinkedList<LiveDrawable> drawables;
    private RockManager rockManager;
    private ArrayList<Music> playList;
    private int musicIndex;
    private Random random = new Random();
    private BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        rockManager = new RockManager();
        drawables = new LinkedList<LiveDrawable>();

        font = new BitmapFont();
        points = 0;

        drawables.add(new Player(100, Gdx.graphics.getHeight() / 2, rockManager));
        drawables.add(rockManager);
        for (int i = 0; i < STAR_COUNT; i++) {
            drawables.addFirst(new Star());
        }

        // musique
        playList = new ArrayList<Music>();
        FileHandle dir = Gdx.files.internal("music");
        FileHandle[] directoryListing = dir.list(".mp3");
        if (directoryListing != null) {
            for (FileHandle child : directoryListing) {
                playList.add(Gdx.audio.newMusic(child));
            }
        }
        musicIndex = random.nextInt(playList.size());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            paused = !paused;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            playList.get(musicIndex).stop();
            create();
        }

        if (!paused) {
            if (!playList.get(musicIndex).isPlaying()) {
                musicIndex = (++musicIndex) % playList.size();
                playList.get(musicIndex).play();
            }

            ArrayList<LiveDrawable> toBeDeleted = new ArrayList<LiveDrawable>();
            for (LiveDrawable ld : drawables) {
                ld.update();
                if (ld.isOut()) {
                    toBeDeleted.add(ld);
                }
            }
            // retire les élément qui sortent de l'écran
            drawables.removeAll(toBeDeleted);
        }

        // draw le batch (tout en une fois, entre batch.begin() et batch.end() pour les performances)
        batch.begin();
        for (LiveDrawable ld : drawables) {
            ld.draw(batch);
        }
        // affichage du score
        font.draw(batch, "Score : " + points, 5, Gdx.graphics.getHeight() - 5);
        if (paused) {
            // affichage du menu pause
            font.draw(batch,
                    "PAUSED\n\n" +
                            "Controls :\n" +
                            "Pause/unpause : SPACEBAR\n" +
                            "Restart : R\n" +
                            "Movement : W, A, S, D\n" +
                            "Red : J\n" +
                            "Yellow : K\n" +
                            "Blue : L\n", Gdx.graphics.getWidth()/2 - 150, Gdx.graphics.getHeight()/2 + font.getLineHeight() * 5);
        }
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
