package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;


import java.util.Random;

public class Star implements Entity {
    private static float SPEED = 750;
    private static float BASE_SIZE = 3;
    private static Texture texture = new Texture(Gdx.files.internal("stardot.png"));
    private static Random random = new Random();
    private Sprite sprite = new Sprite(texture);
    private Vector2 position;
    private float scale;

    public Star() {
        position = new Vector2();
        init();
        position.x = random.nextFloat() * Gdx.graphics.getWidth();
    }

    private void init() {
        position.x = Gdx.graphics.getWidth();
        position.y = random.nextFloat() * Gdx.graphics.getHeight();
        scale = random.nextFloat();

        sprite.setSize(scale * BASE_SIZE, scale * BASE_SIZE);
        sprite.setOriginCenter();
    }

    @Override
    public void update() {
        position.x -= SPEED * scale * Gdx.graphics.getDeltaTime();
        if (position.x < -sprite.getWidth()) {
            init();
        }
        sprite.setPosition(position.x, position.y);
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isMarkedForRemoval() {
        return false;
    }
}
