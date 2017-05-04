package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;


import java.util.Random;

public class Star {
    private static float SPEED = 500;
    private static float BASE_SIZE = 3;
    private static Texture texture = new Texture(Gdx.files.internal("stardot.png"));
    private Sprite sprite = new Sprite(texture);
    private float x;
    private float y;
    private float scale;

    public Star() {
        init();
        x = (x - Gdx.graphics.getWidth()) * 2;
    }

    private void init() {
        Random r = new Random();
        x = (r.nextFloat()+1) * Gdx.graphics.getWidth();
        y = r.nextFloat() * Gdx.graphics.getHeight();
        scale = r.nextFloat();

        sprite.setSize(scale * BASE_SIZE, scale * BASE_SIZE);
        sprite.setOriginCenter();
    }

    public void update() {
        x -= SPEED * scale * Gdx.graphics.getDeltaTime();
        if(x < -sprite.getWidth()) {
            init();
        }
        sprite.setPosition(x, y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getScale() {
        return scale;
    }

    public void draw(Batch batch) {
        sprite.draw(batch);
    }
}
