package ch.cor;

import com.badlogic.gdx.Gdx;
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
    private static final float SPEED = 1000;
    private static final Vector2 SIZE = new Vector2(32, 16);
    private static Texture texture = new Texture(Gdx.files.internal("shot.png"));
    private Sprite sprite = new Sprite(texture);
    private ColorUtils.Color color;
    private Vector2 position;
    private Vector2 vector;
    private RockManager rockManager;
    private boolean hit = false;
    private Reaction reaction;

    public Shot(Vector2 position, ColorUtils.Color color, RockManager rockManager) {
        this.color = color;
        this.rockManager = rockManager;

        vector = new Vector2(1, 0);
        this.position = new Vector2(position.x, position.y - SIZE.y / 2); // déplace l'origine au centre (horizontalement)

        sprite.setColor(color.getValue());
        sprite.setSize(SIZE.x, SIZE.y);
        sprite.setPosition(this.position.x, this.position.y);
        sprite.setOriginCenter();

        reaction = new Reaction();
    }

    @Override
    public void update() {
        if (!hit) {
            vector.nor();

            position.x += vector.x * Gdx.graphics.getDeltaTime() * SPEED;
            position.y += vector.y * Gdx.graphics.getDeltaTime() * SPEED;

            sprite.setRotation(vector.angle());
            sprite.setPosition(position.x, position.y);

            for (Rock rock : rockManager.getRocks()) {
                if (rock.getBounds().overlaps(sprite.getBoundingRectangle()) && !rock.isExploding()) {
                    reaction.addLink(rock.position, color);
                    rock.handleReaction(reaction);
                    hit = true;

                    break;
                }
            }

        } else {
            reaction.update();
        }
    }

    @Override
    public void draw(Batch batch) {
        if (hit) {
            reaction.draw(batch);
        } else {
            sprite.draw(batch);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isOut() {
        return position.x > Gdx.graphics.getWidth() || position.x < -SIZE.x - 100
                || position.y < -SIZE.y - 100 || position.y > Gdx.graphics.getHeight() || (hit && reaction.isOut());
    }

    public ColorUtils.Color getColor() {
        return color;
    }
}
