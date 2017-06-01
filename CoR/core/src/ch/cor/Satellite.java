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
 * @brief Le Satellite absorbe un certain nombre de coups blancs ou noirs, et renvoie la réaction
 * deux fois à chaque impact absorbé.
 */
public class Satellite implements ReactionHandler, Entity {
    private static final float SPEED = 150; // en pixels/s
    private static final float MAX_ROTATION_SPEED = 100; // en degrés par sec
    private static final Vector2 SIZE = new Vector2(50, 50); // en pixels
    private static final int POINT_VALUE = 20;
    private static Texture texture = new Texture(Gdx.files.internal("satellite.png"));
    private Sprite sprite = new Sprite(texture);
    private Vector2 position;
    private float rotation;
    private boolean isDestroyed = false;
    private int hp = POINT_VALUE;

    /**
     * Constructeur
     */
    public Satellite() {
        Random r = new Random();
        // position et rotation aléatoires
        position = new Vector2(Gdx.graphics.getWidth(), r.nextFloat() * Gdx.graphics.getHeight() - SIZE.y / 2);
        rotation = (r.nextFloat() - 0.5f) * MAX_ROTATION_SPEED * 2.0f;

        // initialise le sprite
        sprite.setPosition(position.x, position.y);
        sprite.setSize(SIZE.x, SIZE.y);
        sprite.setOriginCenter();
    }

    @Override
    public void update() {
        // déplace le satellite vers la gauche
        position.x -= Gdx.graphics.getDeltaTime() * SPEED;

        // fait tourner le sprite
        sprite.rotate(rotation * Gdx.graphics.getDeltaTime());

        // déplace le sprite sur la position du satellite
        sprite.setPosition(position.x, position.y);
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    @Override
    public boolean isMarkedForRemoval() {
        // marque le satellite pour la suppression s'il sort de l'écran ou s'il est détruit
        return position.x > Gdx.graphics.getWidth() || position.x < -SIZE.x
                || position.y < -SIZE.y || position.y > Gdx.graphics.getHeight()
                || isDestroyed;
    }

    @Override
    public void handleReaction(Reaction reaction) {
        // ajoute la coordonnée du satellite aux liens de la réaction
        reaction.addLink(position);

        // si la requête reçue est noire
        if (reaction.getColor() == ColorUtils.Color.BLACK) {
            // signale le satellite détruit pour qu'il ne se détecte pas lors de la recherche de handler proche
            isDestroyed = true;

            // envoie 2 couleurs aléatoires (une chaude et une froide) au handler le plus proche
            ReactionHandler nearest = EntityManager.getInstance().getNearestHandler(position);
            if (nearest != null) {
                reaction.setColor(ColorUtils.getRandomWarmColor());
                nearest.handleReaction(reaction);
                reaction.setColor(ColorUtils.getRandomColdColor());
                nearest.handleReaction(reaction);
            }

            // réduit ses points de vie de 1
            if(--hp > 0) {
                // s'il rest des hp, on annule sa destruction
                isDestroyed = false;
            } else {
                // sinon, on ajoute des points au score et on produit une explosion
                EntityManager.getInstance().addPoints(POINT_VALUE);
                EntityManager.getInstance().addEntity(new Explosion(position,ColorUtils.Color.BLACK));
            }
        } else if(reaction.getColor() == ColorUtils.Color.WHITE) {
            // si la couleur reçue est blanche, il trasmet au suivant sans traiter la réaction
            // signale le satellite détruit pour qu'il ne se détecte pas lors de la recherche de handler proche
            isDestroyed = true;

            // encoie la réaction au handler le plus proche
            ReactionHandler nearest = EntityManager.getInstance().getNearestHandler(position);
            if (nearest != null) {
                nearest.handleReaction(reaction);
            }

            // annule sa destruction
            isDestroyed = false;
        }
    }

    @Override
    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }
}
