package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 05.05.17
 */
public class Rock implements LiveDrawable, ReactionHandler {
    private static final float SPEED = 150;
    private static final float MAX_ROTATION_SPEED = 100; // degrés par sec
    private static final Vector2 SIZE = new Vector2(32, 32);
    private static final float EXPLOSION_VELOCITY = 75;
    private static final float EXPLOSION_SIZE = 128;
    private static final int POINT_VALUE = 1;
    private static Texture texture = new Texture(Gdx.files.internal("rock.png"));
    private static Texture explosionTexture = new Texture(Gdx.files.internal("explosion.png"));
    protected Sprite sprite = new Sprite(texture);
    protected ColorUtils.Color color;
    protected Vector2 position;
    protected boolean isExploding = false;
    protected RockManager rockManager;
    private float rotation;
    private boolean isOut = false;

    public Rock(float x, float y, ColorUtils.Color color, RockManager rockManager) {
        this.color = color;
        this.rockManager = rockManager;

        position = new Vector2(x, y - SIZE.y / 2); // déplace l'origine au centre (horizontalement)
        Random r = new Random();
        rotation = (r.nextFloat() - 0.5f) * MAX_ROTATION_SPEED * 2.0f;

        sprite.setColor(color.getValue());
        sprite.setSize(SIZE.x, SIZE.y);
        sprite.setOriginCenter();
    }

    @Override
    public void update() {
        position.x -= Gdx.graphics.getDeltaTime() * SPEED;

        if (isExploding) {
            if (sprite.getTexture() == texture) {
                sprite.setTexture(explosionTexture);
                sprite.setSize(0, 0);
                sprite.setColor(color.getValue());
            }

            sprite.setSize(sprite.getWidth() + Gdx.graphics.getDeltaTime() * EXPLOSION_VELOCITY,
                    sprite.getHeight() + Gdx.graphics.getDeltaTime() * EXPLOSION_VELOCITY);
            sprite.setAlpha(1 - sprite.getHeight() / EXPLOSION_SIZE);
            sprite.setOriginCenter();

            if (sprite.getHeight() > EXPLOSION_SIZE) {
                isOut = true;
            }
        }

        sprite.rotate(rotation * Gdx.graphics.getDeltaTime());
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
    public boolean isOut() {
        return position.x > Gdx.graphics.getWidth() || position.x < -SIZE.x
                || position.y < -SIZE.y || position.y > Gdx.graphics.getHeight()
                || isOut;
    }

    public boolean isExploding() {
        return isExploding;
    }

    @Override
    public void handleReaction(Reaction reaction) {
        if (color == reaction.getColor() || reaction.getColor() == ColorUtils.Color.WHITE) {
            reaction.addLink(position, reaction.getColor() == ColorUtils.Color.WHITE ? ColorUtils.Color.WHITE : color);
            isExploding = true;
            CoR.points += POINT_VALUE;
            ReactionHandler nearest = rockManager.getNearestRock(position);
            if (nearest != null) {
                nearest.handleReaction(reaction);
            }
        }
    }

    public Vector2 getPos() {
        return position;
    }

    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }
}
