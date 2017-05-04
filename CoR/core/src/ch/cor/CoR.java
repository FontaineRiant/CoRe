package ch.cor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

public class CoR extends ApplicationAdapter {
    private static int STAR_COUNT = 1000;
    SpriteBatch batch;
    Texture img;
    Player player;
    ArrayList<Star> stars;

    @Override
    public void create() {
        Random r = new Random();
        batch = new SpriteBatch();
        player = new Player(100, Gdx.graphics.getHeight()/2);

        stars = new ArrayList<Star>();
        for(int i = 0; i < STAR_COUNT; i++) {
            stars.add(new Star());
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 0.1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for(Star st : stars) {
            st.update();
        }


        player.update();

        // draw le batch (tout en une fois, entre batch.begin() et batch.end() pour les performances)
        batch.begin();
        for(Star st : stars) {
            st.draw(batch);
        }
        player.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
    }
}
