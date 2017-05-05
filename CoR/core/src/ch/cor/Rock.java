package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 05.05.17
 */
public class Rock implements LiveDrawable, ShotHandler {
    private static float SPEED = 200;
    private static float MAX_ROTATION_SPEED = 100; // degrés par sec
    private static Vector2 SIZE = new Vector2(32, 32);
    private static Texture texture = new Texture(Gdx.files.internal("rock.png"));
    private Sprite sprite = new Sprite(texture);
    private boolean homing = false;
    private Color color;
    private float x;
    private float y;
    private float rotation;
    private boolean isExploding = false;

    public Rock(float x, float y, Color color) {
        this.color = color;
        this.x = x;
        this.y = y - SIZE.y / 2; // déplace l'origine au centre (horizontalement)

        Random r = new Random();
        rotation = (r.nextFloat() - 0.5f) * MAX_ROTATION_SPEED * 2.0f;

        sprite.setColor(color);
        sprite.setSize(SIZE.x, SIZE.y);
        sprite.setPosition(this.x, this.y);
        sprite.setOriginCenter();
    }

    @Override
    public void update() {
        x -= Gdx.graphics.getDeltaTime() * SPEED;
        sprite.rotate(rotation * Gdx.graphics.getDeltaTime());
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
        return x > Gdx.graphics.getWidth() || x < -SIZE.x || y < -SIZE.y || y > Gdx.graphics.getHeight() || isExploding;
    }

    @Override
    public void handleShot(Shot shot) {

    }
}
