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
    private static float SPEED = 1000;
    private static Vector2 SIZE = new Vector2(64, 32);
    private static Texture texture = new Texture(Gdx.files.internal("shot.png"));
    private boolean homing = false;
    private Rock target;
    private Sprite sprite = new Sprite(texture);
    private ColorUtils.Color color;
    private Vector2 position;
    private Vector2 vector;
    private RockManager rockManager;
    private boolean isHandled = false;

    public Shot(float x, float y, ColorUtils.Color color, RockManager rockManager) {
        this.color = color;
        this.rockManager = rockManager;

        vector = new Vector2(1, 0);
        position = new Vector2(x, y - SIZE.y / 2);// déplace l'origine au centre (horizontalement)

        sprite.setColor(color.getValue());
        sprite.setSize(SIZE.x, SIZE.y);
        sprite.setPosition(position.x, position.y);
        sprite.setOriginCenter();
    }

    public void setHoming(Rock target) {
        if (target == null) {
            isHandled = true;
        } else {
            this.target = target;
            homing = true;
        }
    }

    @Override
    public void update() {
        if (homing) {
            if (target.isOut()) {
                homing = false;
                target = null;
            } else {
                vector.x = target.getPos().x - position.x;
                vector.y = target.getPos().y - position.y;
            }
        }

        vector.nor();

        position.x += vector.x * Gdx.graphics.getDeltaTime() * SPEED;
        position.y += vector.y * Gdx.graphics.getDeltaTime() * SPEED;

        for (Rock rock : rockManager.getRocks()) {
            if (rock.getBounds().overlaps(sprite.getBoundingRectangle()) && !rock.isExploding()) {
                homing = false;
                rock.handleShot(this);
                break;
            }
        }

        sprite.setRotation(vector.angle());
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
        return position.x > Gdx.graphics.getWidth() || position.x < -SIZE.x - 100
                || position.y < -SIZE.y - 100 || position.y > Gdx.graphics.getHeight() || isHandled;
    }

    public void setHandled() {
        isHandled = true;
    }

    public ColorUtils.Color getColor() {
        return color;
    }
}
