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
 * @brief Rock est un astéroide de couleur. Il explose et relance la réaction s'il est touché par sa couleur
 */
public class Rock implements Entity, ReactionHandler {
    private static final float SPEED = 200; // en pixels par secondes
    private static final float MAX_ROTATION_SPEED = 100; // degrés par sec
    private static final int POINT_VALUE = 1;
    private static final float MIN_SCALE = 0.5f; // facteur de taille minimal (taille réelle aléatoire)
    private static final Vector2 SIZE = new Vector2(40, 40); // taille maximale
    private static Texture texture = new Texture(Gdx.files.internal("rock.png"));
    private Sprite sprite = new Sprite(texture);
    private ColorUtils.Color color;
    private Vector2 position;
    private float rotation;
    private boolean isDestroyed = false;
    private float scale;

    /**
     * Constructeur
     *
     * @param color couleur de l'astéroide
     */
    public Rock(ColorUtils.Color color) {
        this.color = color;
        Random random = new Random();

        // initialise aléatoirement la taille, la position et la rotation de l'astéroide
        scale = random.nextFloat() * (1 - MIN_SCALE) + MIN_SCALE;
        position = new Vector2(Gdx.graphics.getWidth(), random.nextFloat() * Gdx.graphics.getHeight() - SIZE.y * scale / 2);
        rotation = (random.nextFloat() - 0.5f) * MAX_ROTATION_SPEED * 2.0f;

        // initialise le sprite
        sprite.setPosition(position.x, position.y);
        sprite.setColor(color.getValue());
        sprite.setSize(SIZE.x * scale, SIZE.y * scale);
        sprite.setOriginCenter();
    }

    @Override
    public void update() {
        // déplace l'astéroide vers la gauche
        position.x -= Gdx.graphics.getDeltaTime() * SPEED * scale;

        // fait tourner l'astéroide
        sprite.rotate(rotation * Gdx.graphics.getDeltaTime());

        // déplace le sprite sur la position de l'astéroide
        sprite.setPosition(position.x, position.y);
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    @Override
    public boolean isMarkedForRemoval() {
        // signale la fin de vie de l'astéroide s'il sort de l'écran ou s'il a explosé
        return position.x > Gdx.graphics.getWidth() || position.x < -SIZE.x * scale
                || position.y < -SIZE.y * scale || position.y > Gdx.graphics.getHeight()
                || isDestroyed;
    }

    @Override
    public void handleReaction(Reaction reaction) {
        // ajoute l'a position de l'astéroide aux liens de la réaction en chaine
        reaction.addLink(position);

        // traite la reaction si elle est blanche ou si elle est de la même couleur de l'astéroide
        if (color == reaction.getColor() || reaction.getColor() == ColorUtils.Color.WHITE) {
            // produit une explosion
            EntityManager.getInstance().addEntity(new Explosion(position, color));

            // se déclare détruit
            isDestroyed = true;

            // ajoute les points au score du joueur
            EntityManager.getInstance().addPoints(POINT_VALUE);

            // transmet la réaction au handler le plus proche
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
