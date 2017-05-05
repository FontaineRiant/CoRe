package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;


import java.util.Random;

public class Star implements LiveDrawable {
    private static float SPEED = 750;
    private static float BASE_SIZE = 3;
    private static Texture texture = new Texture(Gdx.files.internal("stardot.png"));
    private static Random random = new Random();
    private Sprite sprite = new Sprite(texture);
    private float x;
    private float y;
    private float scale;

    public Star() {
        init();
        x = random.nextFloat() * Gdx.graphics.getWidth();
    }

    private void init() {
        x = Gdx.graphics.getWidth();
        y = random.nextFloat() * Gdx.graphics.getHeight();
        scale = random.nextFloat();

        sprite.setSize(scale * BASE_SIZE, scale * BASE_SIZE);
        sprite.setOriginCenter();
    }

    @Override
    public void update() {
        x -= SPEED * scale * Gdx.graphics.getDeltaTime();
        if(x < -sprite.getWidth()) {
            init();
        }
        sprite.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isOut() {
        return false;
    }
}
