package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Explosion implements Entity {

    private static final float EXPLOSION_DURATION = 1; // en secondes
    private static final float EXPLOSION_SIZE = 128;
    private float scale = 0;
    private boolean isOut = false;
    private static Texture explosionTexture = new Texture(Gdx.files.internal("explosion.png"));
    private Sprite sprite = new Sprite(explosionTexture);

    public Explosion(Vector2 position, ColorUtils.Color color) {
        sprite.setColor(color.getValue());
        sprite.setPosition(position.x - EXPLOSION_SIZE / 2, position.y - EXPLOSION_SIZE / 2);
        sprite.setSize(EXPLOSION_SIZE, EXPLOSION_SIZE);
        sprite.setOriginCenter();
    }

    @Override
    public void update() {
        sprite.setScale(scale);
        scale += Gdx.graphics.getDeltaTime() * (1/EXPLOSION_DURATION);
        sprite.setAlpha(1 - scale);

        if (scale > 1) {
            isOut = true;
        }
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }


    @Override
    public boolean isMarkedForRemoval() {
        return isOut;
    }
}
