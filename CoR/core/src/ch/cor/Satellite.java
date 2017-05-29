package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief
 */
public class Satellite implements ReactionHandler, Entity {
    private static final float SPEED = 150; // en pixels/s
    private static final float MAX_ROTATION_SPEED = 100; // en degrés par sec
    private static final Vector2 SIZE = new Vector2(50, 50); // en pixels
    private static final int POINT_VALUE = 20;
    private static Texture texture = new Texture(Gdx.files.internal("satellite.png"));
    private Sprite sprite = new Sprite(texture);
    private Vector2 position;
    private float rotation;
    private boolean isOut = false;
    private int hp = POINT_VALUE;

    public Satellite() {
        Random r = new Random();
        position = new Vector2(Gdx.graphics.getWidth(), r.nextFloat() * Gdx.graphics.getHeight() - SIZE.y / 2);
        rotation = (r.nextFloat() - 0.5f) * MAX_ROTATION_SPEED * 2.0f;

        sprite.setPosition(position.x, position.y);
        sprite.setSize(SIZE.x, SIZE.y);
        sprite.setOriginCenter();
    }

    @Override
    public void update() {
        position.x -= Gdx.graphics.getDeltaTime() * SPEED;
        sprite.rotate(rotation * Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    @Override
    public boolean isMarkedForRemoval() {
        return position.x > Gdx.graphics.getWidth() || position.x < -SIZE.x
                || position.y < -SIZE.y || position.y > Gdx.graphics.getHeight()
                || isOut;
    }

    @Override
    public void handleReaction(Reaction reaction) {
        reaction.addLink(position);
        if (reaction.getColor() == ColorUtils.Color.BLACK) {
            isOut = true;

            ReactionHandler nearest = EntityManager.getInstance().getNearestHandler(position);
            if (nearest != null) {
                reaction.setColor(ColorUtils.getRandomNWarmColor());
                nearest.handleReaction(reaction);
                reaction.setColor(ColorUtils.getRandomColdColor());
                nearest.handleReaction(reaction);
            }

            if(--hp > 0) {
                isOut = false;
            } else {
                EntityManager.getInstance().addPoints(POINT_VALUE);
                EntityManager.getInstance().addEntity(new Explosion(position,ColorUtils.Color.BLACK));
            }
        } else if(reaction.getColor() == ColorUtils.Color.WHITE) {
            isOut = true;
            ReactionHandler nearest = EntityManager.getInstance().getNearestHandler(position);
            if (nearest != null) {
                reaction.setColor(ColorUtils.Color.WHITE);
                nearest.handleReaction(reaction);
            }
            isOut = false;
        }
    }

    @Override
    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }
}
