package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief Vaisseau spatial du joueur
 */
public class Player implements Entity {
    private final static float MAX_SPEED = 1000; // en pixels/sec
    private final static float ACCELERATION = 4000; // en pixels/sec^2
    private final static float DECELERATION = 3000; // en pixels/sec^2
    private final static float SIZE = 40; // en pixels
    private final static float RATE_OF_FIRE = 0.15f; // secondes entre 2 tirs

    // contrôles
    private final static int RED_KEY = Input.Keys.J;
    private final static int YELLOW_KEY = Input.Keys.K;
    private final static int BLUE_KEY = Input.Keys.L;
    private final static int UP_KEY = Input.Keys.W;
    private final static int DOWN_KEY = Input.Keys.S;
    private final static int LEFT_KEY = Input.Keys.A;
    private final static int RIGHT_KEY = Input.Keys.D;

    private boolean dead = false;
    private float timeSinceLastShot = 0;
    private Vector2 position;
    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("ship.png")));
    private Vector2 vector = new Vector2();

    /**
     * Constructeur
     * @param x position horizontale
     * @param y position verticale
     */
    public Player(float x, float y) {
        position = new Vector2(x, y);
        sprite.setSize(SIZE, SIZE);
        sprite.setOriginCenter();
        sprite.setRotation(-135);
        sprite.setPosition(x, y);
    }

    /**
     * Renvoie la vitesse après application de l'intertie
     * @param speed vitesse actuelle
     * @return vitesse après inertie
     */
    private float applyInertia(float speed) {
        float dec = DECELERATION * Math.signum(speed) * Gdx.graphics.getDeltaTime();
        if (Math.abs(speed) > Math.abs(dec)) {
            return speed - DECELERATION * Math.signum(speed) * Gdx.graphics.getDeltaTime();
        } else {
            return 0;
        }
    }

    @Override
    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Détecte une collision avec un reactionhandler
        ReactionHandler nearest = EntityManager.getInstance().getNearestHandler(position);
        if (nearest != null && nearest.getBounds().overlaps(sprite.getBoundingRectangle())) {
            // se déclare mort
            dead = true;

            // fait apparaitre une explosion blanche
            EntityManager.getInstance().addEntity(new Explosion(position, ColorUtils.Color.WHITE));

            // signale le gameover au manager pour afficher le message de gameover
            EntityManager.getInstance().setGameOver();
        }

        // Déplacement en fonction des inputs ...
        if (Gdx.input.isKeyPressed(RIGHT_KEY)) {
            vector.x += deltaTime * ACCELERATION;
        } else if (vector.x > 0) {
            vector.x = applyInertia(vector.x);
        }

        if (Gdx.input.isKeyPressed(LEFT_KEY)) {
            vector.x -= deltaTime * ACCELERATION;
        } else if (vector.x < 0) {
            vector.x = applyInertia(vector.x);
        }

        if (Gdx.input.isKeyPressed(UP_KEY)) {
            vector.y += deltaTime * ACCELERATION;
        } else if (vector.y > 0) {
            vector.y = applyInertia(vector.y);
        }

        if (Gdx.input.isKeyPressed(DOWN_KEY)) {
            vector.y -= deltaTime * ACCELERATION;
        } else if (vector.y < 0) {
            vector.y = applyInertia(vector.y);
        }

        // Limite à la vitesse maximale
        vector.x = Math.min(Math.abs(vector.x), MAX_SPEED) * Math.signum(vector.x);
        vector.y = Math.min(Math.abs(vector.y), MAX_SPEED) * Math.signum(vector.y);

        // Déplace la position du joueur
        position.x += vector.x * deltaTime;
        position.y += vector.y * deltaTime;

        // Limite la position aux bords de l'écran
        position.x = Math.max(0, Math.min(position.x, Gdx.graphics.getWidth() - sprite.getWidth()));
        position.y = Math.max(0, Math.min(position.y, Gdx.graphics.getHeight() - sprite.getHeight()));

        // Effectue le déplacement du sprite
        sprite.setPosition(position.x, position.y);

        // Gestion des tirs
        timeSinceLastShot += Gdx.graphics.getDeltaTime();
        if ((Gdx.input.isKeyPressed(RED_KEY) || Gdx.input.isKeyPressed(YELLOW_KEY) || Gdx.input.isKeyPressed(BLUE_KEY))
                && timeSinceLastShot >= RATE_OF_FIRE) {

            // Mélange les couleurs des inputs
            ColorUtils.Color color = ColorUtils.Color.WHITE;
            if (Gdx.input.isKeyPressed(RED_KEY)) {
                color = ColorUtils.add(color, ColorUtils.Color.RED);
            }

            if (Gdx.input.isKeyPressed(YELLOW_KEY)) {
                color = ColorUtils.add(color, ColorUtils.Color.YELLOW);
            }

            if (Gdx.input.isKeyPressed(BLUE_KEY)) {
                color = ColorUtils.add(color, ColorUtils.Color.BLUE);
            }

            // Ajoute le tir aux entités actives
            EntityManager.getInstance().addEntity(new Shot(new Vector2(position.x + SIZE, position.y + SIZE / 2), color));

            // Réinitialise le temps depuis le dernier tir
            timeSinceLastShot = 0;
        }

    }

    @Override
    public boolean isMarkedForRemoval() {
        // le joueur n'est retiré du jeu que lorsqu'il est mort
        return dead;
    }

    @Override
    public void draw(Batch batch) {
         sprite.draw(batch);
    }
}
