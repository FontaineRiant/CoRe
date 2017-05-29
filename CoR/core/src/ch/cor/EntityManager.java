package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief Singleton gestionnaire des entités du jeu. Gère leur intégration et leur suppression
 * de la boucle principale du jeu.
 */
public class EntityManager {
    private static EntityManager instance = null;
    private static Spawner spawner = new Spawner();
    private BitmapFont font;
    private int points;
    private boolean gameOver = false;
    private boolean paused = true;
    private LinkedList<Entity> entities; // entités présentes en jeu
    private LinkedList<Entity> addQueue; // entités à ajouter à la prochaine boucle de jeu

    /**
     * GetInstance du singleton
     * @return l'instance unique de EntityManager
     */
    public static EntityManager getInstance() {
        if (instance == null) {
            instance = new EntityManager();
        }
        return instance;
    }

    /**
     * Constructeur privé du singleton
     */
    private EntityManager() {
        addQueue = new LinkedList<Entity>();
        entities = new LinkedList<Entity>();
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(1.5f);
    }

    /**
     * @brief Ajoute l'entité en argument aux entités à la prochaine itération
     * @param e
     */
    public void addEntity(Entity e) {
        addQueue.addLast(e);
    }

    /**
     * Signale que je jeu est terminé
     */
    public void setGameOver() {
        gameOver = true;
    }

    /**
     * Update toutes les entités
     */
    public void updateAll() {
        // Reset le jeu si le joueur appuie sur R
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            entities.clear();
            entities.add(new Player(100, Gdx.graphics.getHeight() / 2));
            spawner = new Spawner();
            points = 0;
            gameOver = false;
            paused = false;
        }

        // Met le jeu en pause si le joueur appuie sur la barre espace
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            paused = !paused;
        }

        // ajoute les entités de la file d'attente
        entities.addAll(addQueue);
        addQueue.clear();

        // si le jeur n'est pas en pause, update les entités
        if (!paused) {
            // réactive le spawner d'entités
            spawner.tick();

            LinkedList<Entity> toBeDeleted = new LinkedList<Entity>();
            for (Entity e : entities) {
                // update toutes les entités
                e.update();

                // répertorie les entités qui sont en fin de vie
                if (e.isMarkedForRemoval()) {
                    toBeDeleted.add(e);
                }
            }
            // retire les entités marquées pour la suppression
            entities.removeAll(toBeDeleted);
        }
    }

    /**
     * Dessine toutes les entités dans le batch en argument,
     * ainsi que l'UI.
     * @param batch
     */
    public void drawAll(SpriteBatch batch) {
        // dessine les entités
        for (Entity e : entities) {
            e.draw(batch);
        }

        // affichage du game over
        if (gameOver) {
            font.draw(batch, "GAME OVER (press 'R' to try again)", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 + 5);
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

    /**
     * Trouve et renvoie le ReactionHandler le plus proche des coordonnées en argument
     * @param coord coordonnées
     * @return ReactionHandler ou null si aucun ReactionHandler n'est trouvé
     */
    public ReactionHandler getNearestHandler(Vector2 coord) {
        ReactionHandler nearest = null;
        float distance = Float.MAX_VALUE;
        for (Entity e : entities) {
            if (e instanceof ReactionHandler) {
                ReactionHandler handler = (ReactionHandler) e;
                float tempDistance = coord.dst(handler.getBounds().getX(), handler.getBounds().getY());
                if (distance > tempDistance && !e.isMarkedForRemoval()) {
                    nearest = handler;
                    distance = tempDistance;
                }
            }
        }
        return nearest;
    }

    /**
     * Ajoute pointValue points au score affiché
     * @param pointValue
     */
    public void addPoints(int pointValue) {
        points += pointValue;
    }
}
