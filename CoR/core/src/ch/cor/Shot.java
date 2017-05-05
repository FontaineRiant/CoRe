package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 05.05.17
 */
public class Shot implements LiveDrawable {
    private static float SPEED = 1500;
    private static Vector2 SIZE = new Vector2(32, 16);
    private static Texture texture = new Texture(Gdx.files.internal("shot.png"));
    private boolean homing = false;
    private Sprite sprite = new Sprite(texture);
    private Color color;
    private float x;
    private float y;

    public Shot(float x, float y, Color color) {
        this.color = color;
        this.x = x;
        this.y = y - SIZE.y/2; // déplace l'origine au centre (horizontalement)

        sprite.setColor(color);
        sprite.setSize(SIZE.x, SIZE.y);
        sprite.setPosition(this.x, this.y);
        sprite.setOriginCenter();
    }

    public void setHoming() {
        // TODO : passer une cible en argument et fair en sorte que le tir la suive
        homing = true;
    }

    @Override
    public void update() {
        x+= Gdx.graphics.getDeltaTime() * SPEED;
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
        return x > Gdx.graphics.getWidth();
    }

    public Color getColor() {
        return color;
    }
}
