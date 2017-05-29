package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief Short représente un projectile tiré par le joueur
 */
public class Shot implements Entity {
    private static final float SPEED = 1000; // vitesse du projectile en pixels/seconde
    private static final Vector2 SIZE = new Vector2(32, 16); // dimensions du tir en pixels
    private static Texture texture = new Texture(Gdx.files.internal("shot.png"));
    private Sprite sprite = new Sprite(texture);
    private ColorUtils.Color color;
    private Vector2 position;
    private boolean hit = false;

    /**
     * Constructeur du projectile
     * @param position position d'origine du tir
     * @param color couleur du tir
     */
    public Shot(Vector2 position, ColorUtils.Color color) {
        this.color = color;

        // déplace l'origine au centre (horizontalement)
        this.position = new Vector2(position.x, position.y - SIZE.y / 2);

        // initialise le sprite
        sprite.setColor(color.getValue());
        sprite.setSize(SIZE.x, SIZE.y);
        sprite.setPosition(this.position.x, this.position.y);
        sprite.setOriginCenter();

    }

    @Override
    public void update() {
        // déplace le projectile vers la droite
        position.x += Gdx.graphics.getDeltaTime() * SPEED;

        // déplace le sprite
        sprite.setPosition(position.x, position.y);

        // détecte les collisions avec un ReactionHandler
        ReactionHandler nearest = EntityManager.getInstance().getNearestHandler(position);
        if (nearest != null && sprite.getBoundingRectangle().overlaps(nearest.getBounds())) {
            // Créé une réaction de la couleur du projectile
            Reaction reaction = new Reaction(color);

            // Ajoute la réaction aux entités à afficher
            EntityManager.getInstance().addEntity(reaction);

            // Ajoute une explosion aux entités à afficher
            EntityManager.getInstance().addEntity(new Explosion(position, color));

            // Envoie la réaction au handler touché (point d'entrée de la chaine de responsabilité)
            nearest.handleReaction(reaction);

            // signale que le tir a touché une cible
            hit = true;
        }
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    @Override
    public boolean isMarkedForRemoval() {
        // si le tir sort de l'écran ou qu'il a touché une cible, on cesse de l'afficher
        return position.x > Gdx.graphics.getWidth() || position.x < -SIZE.x - 100
                || position.y < -SIZE.y - 100 || position.y > Gdx.graphics.getHeight() || hit;
    }
}
