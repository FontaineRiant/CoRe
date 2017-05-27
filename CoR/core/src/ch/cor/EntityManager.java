package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

/**
 * singleton
 */
public class EntityManager {
    private static EntityManager instance = null;
    private static Spawner spawner = new Spawner();
    private BitmapFont font;
    private int points;
    private boolean gameOver = false;
    private boolean paused = false;
    private LinkedList<Entity> entities;
    private LinkedList<Entity> addQueue;

    public static EntityManager getInstance() {
        if (instance == null) {
            instance = new EntityManager();
        }
        return instance;
    }

    private EntityManager() {
        addQueue = new LinkedList<Entity>();
        entities = new LinkedList<Entity>();
        font = new BitmapFont();
    }

    public void addEntity(Entity e) {
        addQueue.addLast(e);
    }

    public void setGameOver() {
        gameOver = true;
    }

    public void updateAll() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            entities.clear();
            entities.add(new Player(100, Gdx.graphics.getHeight() / 2));
            spawner = new Spawner();
            points = 0;
            gameOver = false;
            paused = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            paused = !paused;
        }

        // ajoute les entités dans la file d'attente
        entities.addAll(addQueue);
        addQueue.clear();

        if (!paused) {
            spawner.tick();

            LinkedList<Entity> toBeDeleted = new LinkedList<Entity>();
            for (Entity e : entities) {
                e.update();
                if (e.isMarkedForRemoval()) {
                    toBeDeleted.add(e);
                }
            }
            // retire les élément marqués pour la suppression
            entities.removeAll(toBeDeleted);
        }
    }

    public void drawAll(SpriteBatch batch) {
        for (Entity e : entities) {
            e.draw(batch);
        }

        // affichage du game over
        if (gameOver) {
            font.draw(batch, "GAME OVER (press 'R' to try again)", Gdx.graphics.getWidth() / 2 - 120, Gdx.graphics.getHeight() / 2 + 5);
        }

        // affichage du score
        font.draw(batch, "Score : " + points, 5, Gdx.graphics.getHeight() - 5);

        // affichage du menu pause
        if (paused) {
            font.draw(batch,
                    "PAUSED\n\n" +
                            "Controls :\n" +
                            "Pause/unpause : SPACEBAR\n" +
                            "Restart : R\n" +
                            "Movement : W, A, S, D\n" +
                            "Red : J\n" +
                            "Yellow : K\n" +
                            "Blue : L\n", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 + font.getLineHeight() * 5);
        }
    }

    public ReactionHandler getNearestHandler(Vector2 coord) {
        ReactionHandler nearest = null;
        float distance = Float.MAX_VALUE;
        for (Entity e : entities) {
            if (e instanceof ReactionHandler) {
                ReactionHandler handler = (ReactionHandler) e;
                float tempDistance = handler.getPos().dst2(coord);
                if (distance > tempDistance && !e.isMarkedForRemoval()) {
                    nearest = handler;
                    distance = tempDistance;
                }
            }
        }
        return nearest;
    }

    public void addPoints(int pointValue) {
        points += pointValue;
    }
}
