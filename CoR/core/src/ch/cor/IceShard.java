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
 * @brief
 */
public class IceShard implements Entity, ReactionHandler {
    private static final float SPEED = 150; // en pixels par seconde
    private static final float MAX_ROTATION_SPEED = 100; // en degrés par seconde
    private static final Vector2 SIZE = new Vector2(50, 50); // en pixels
    private static Texture texture = new Texture(Gdx.files.internal("iceblock.png"));
    private Sprite sprite = new Sprite(texture);
    private Vector2 position;
    private float rotation;
    private boolean isDestroyed = false;

    /**
     * Constructeur
     */
    public IceShard() {
        Random random = new Random();
        // initialise la position et la vitesse de rotation aléatoirement
        position = new Vector2(Gdx.graphics.getWidth(), random.nextFloat() * Gdx.graphics.getHeight() - SIZE.y / 2);
        rotation = (random.nextFloat() - 0.5f) * MAX_ROTATION_SPEED * 2.0f;

        // initialise le sprite
        sprite.setPosition(position.x, position.y);
        sprite.setSize(SIZE.x, SIZE.y);
        sprite.setOriginCenter();
    }

    @Override
    public void update() {
        // déplace le sprite vers la gauche
        position.x -= Gdx.graphics.getDeltaTime() * SPEED;
        sprite.setPosition(position.x, position.y);

        // fait tourner le sprite
        sprite.rotate(rotation * Gdx.graphics.getDeltaTime());
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    @Override
    public boolean isMarkedForRemoval() {
        return position.x > Gdx.graphics.getWidth() || position.x < -SIZE.x
                || position.y < -SIZE.y || position.y > Gdx.graphics.getHeight()
                || isDestroyed;
    }

    @Override
    public void handleReaction(Reaction reaction) {
        // ajoute le lien à la réaction pour afficher le lazer
        reaction.addLink(position);

        // gère la réaction si elle possède déjà 2 liens (=> 1 prédécesseur)
        if (reaction.getReactionSize() > 1) {
            // change la couleur de la réaction à la couleur de ce bloc de glace (blanc)
            reaction.setColor(ColorUtils.Color.WHITE);

            // fait apparaitre une explosion
            EntityManager.getInstance().addEntity(new Explosion(position, ColorUtils.Color.WHITE));

            // signale que le bloc est détruit
            isDestroyed = true;

            // passe la réaction au handler le plus proche
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
