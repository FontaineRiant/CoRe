package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 16.05.17
 */
public class Reaction implements Entity {
    private static final float LIFE_SPAN = 1.5f; // en secondes
    private static final float WIDTH = 15;
    private static Texture texture = new Texture(Gdx.files.internal("lazorv2.png"));
    private float elapsedTime = 0;
    private boolean stopped;
    private ColorUtils.Color color;
    private LinkedList<Vector2> links = new LinkedList<Vector2>();
    private LinkedList<Sprite> sprites = new LinkedList<Sprite>();
    private ShapeRenderer renderer = new ShapeRenderer();

    public Reaction() {
        stopped = false;
    }

    public void addLink(Vector2 coord, ColorUtils.Color color) {
        sprites.add(new Sprite(texture));
        this.color = color;
        sprites.getLast().setColor(color.getValue());
        links.add(coord);
        update();
    }

    @Override
    public void update() {
        elapsedTime += Gdx.graphics.getDeltaTime();

        if (elapsedTime > LIFE_SPAN) {
            stopped = true;
            return;
        }

        for(Sprite sp : sprites) {
            sp.setAlpha(1 - elapsedTime / LIFE_SPAN);
        }

        for (int i = 0; i < links.size() - 1; i++) {
            // dessine le sprite de la coordonnée i à i+1
            float x = links.get(i + 1).x - links.get(i).x;
            float y = links.get(i + 1).y - links.get(i).y;

            sprites.get(i).setSize((float) Math.sqrt(x * x + y * y), WIDTH);
            sprites.get(i).setOrigin(WIDTH / 2, WIDTH / 2);
            sprites.get(i).setPosition(links.get(i).x, links.get(i).y);
            Vector2 link = new Vector2(x, y);

//            sprites.get(i).setRotation((float) Math.toDegrees(x < 0 ? Math.PI - Math.atan(y / x) : Math.atan(y / x)));
            sprites.get(i).setRotation(link.angle());
        }
    }

    @Override
    public void draw(Batch batch) {
        for (int i = 0; i < sprites.size() -1; i++) {
            sprites.get(i).draw(batch);
        }
    }

    @Override
    public void dispose() {
        stopped = true;
        texture.dispose();
    }

    @Override
    public boolean isMarkedForRemoval() {
        return stopped;
    }

    public ColorUtils.Color getColor() {
        return color;
    }

    public int getReactionSize() {
        return links.size();
    }
}
