package ch.cor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class CoR extends ApplicationAdapter {
    private static int STAR_COUNT = 1000;
    private SpriteBatch batch;
    private LinkedList<LiveDrawable> drawables;
    private RockManager rockManager;

    @Override
    public void create() {
        Random r = new Random();
        batch = new SpriteBatch();
        rockManager = new RockManager();
        drawables = new LinkedList<LiveDrawable>();

        drawables.add(rockManager);
        drawables.add(new Player(100, Gdx.graphics.getHeight()/2));
        for(int i = 0; i < STAR_COUNT; i++) {
            drawables.addFirst(new Star());
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ArrayList<LiveDrawable> toBeDeleted = new ArrayList<LiveDrawable>();
        for(LiveDrawable ld : drawables) {
            ld.update();
            if(ld.isOut()) {
                toBeDeleted.add(ld);
            }
        }
        // retire les élément qui sortent de l'écran
        drawables.removeAll(toBeDeleted);

        // draw le batch (tout en une fois, entre batch.begin() et batch.end() pour les performances)
        batch.begin();
        for(LiveDrawable ld : drawables) {
            ld.draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
