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
public class Rock implements Entity, ReactionHandler {
    private static final float SPEED = 200;
    private static final float MAX_ROTATION_SPEED = 100; // degrés par sec
    private static final int POINT_VALUE = 1;
    private static final float MIN_SCALE = 0.5f;
    private static final Vector2 SIZE = new Vector2(40, 40);
    private static Texture texture = new Texture(Gdx.files.internal("rock.png"));
    private Sprite sprite = new Sprite(texture);
    private ColorUtils.Color color;
    private Vector2 position;
    private float rotation;
    private boolean isOut = false;
    private float scale;

    public Rock(ColorUtils.Color color) {
        this.color = color;
        Random random = new Random();

        scale = random.nextFloat() * (1 - MIN_SCALE) + MIN_SCALE;
        position = new Vector2(Gdx.graphics.getWidth(), random.nextFloat() * Gdx.graphics.getHeight() - SIZE.y*scale / 2);
        Random r = new Random();
        rotation = (r.nextFloat() - 0.5f) * MAX_ROTATION_SPEED * 2.0f;

        sprite.setPosition(position.x, position.y);
        sprite.setColor(color.getValue());
        sprite.setSize(SIZE.x*scale, SIZE.y*scale);
        sprite.setOriginCenter();
    }

    @Override
    public void update() {
        position.x -= Gdx.graphics.getDeltaTime() * SPEED * scale;
        sprite.rotate(rotation * Gdx.graphics.getDeltaTime());
        sprite.setPosition(position.x, position.y);
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    @Override
    public boolean isMarkedForRemoval() {
        return position.x > Gdx.graphics.getWidth() || position.x < -SIZE.x*scale
                || position.y < -SIZE.y*scale || position.y > Gdx.graphics.getHeight()
                || isOut;
    }

    @Override
    public void handleReaction(Reaction reaction) {

        reaction.addLink(position);

        if (color == reaction.getColor() || reaction.getColor() == ColorUtils.Color.WHITE) {
            reaction.setColor(reaction.getColor() == ColorUtils.Color.WHITE ? ColorUtils.Color.WHITE : color);
            EntityManager.getInstance().addEntity(new Explosion(position, color));
            isOut = true;

            EntityManager.getInstance().addPoints(POINT_VALUE);
            ReactionHandler nearest = EntityManager.getInstance().getNearestHandler(position);
            if (nearest != null) {
                nearest.handleReaction(reaction);
            }
        }


    }

    @Override
    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }
}
