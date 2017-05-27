package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 05.05.17
 */
public class Shot implements Entity {
    private static final float SPEED = 1000;
    private static final Vector2 SIZE = new Vector2(32, 16);
    private static Texture texture = new Texture(Gdx.files.internal("shot.png"));
    private Sprite sprite = new Sprite(texture);
    private ColorUtils.Color color;
    private Vector2 position;
    private Vector2 vector;
    private boolean hit = false;

    public Shot(Vector2 position, ColorUtils.Color color) {
        this.color = color;

        vector = new Vector2(1, 0);
        this.position = new Vector2(position.x, position.y - SIZE.y / 2); // déplace l'origine au centre (horizontalement)

        sprite.setColor(color.getValue());
        sprite.setSize(SIZE.x, SIZE.y);
        sprite.setPosition(this.position.x, this.position.y);
        sprite.setOriginCenter();

    }

    @Override
    public void update() {
        vector.nor();

        position.x += vector.x * Gdx.graphics.getDeltaTime() * SPEED;
        position.y += vector.y * Gdx.graphics.getDeltaTime() * SPEED;

        sprite.setRotation(vector.angle());
        sprite.setPosition(position.x, position.y);

        ReactionHandler nearest = EntityManager.getInstance().getNearestHandler(position);
        if (nearest != null && position.dst(nearest.getPos()) < SIZE.y) {
            Reaction reaction = new Reaction();
            reaction.addLink(nearest.getPos(), color);
            EntityManager.getInstance().addEntity(reaction);
            nearest.handleReaction(reaction);
            hit = true;
        }
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
        return position.x > Gdx.graphics.getWidth() || position.x < -SIZE.x - 100
                || position.y < -SIZE.y - 100 || position.y > Gdx.graphics.getHeight() || hit;
    }

    public ColorUtils.Color getColor() {
        return color;
    }
}
