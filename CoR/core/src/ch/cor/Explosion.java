package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief Représente une brève explosion qui s'affiche à l'écran
 */
public class Explosion implements Entity {
    private static final float EXPLOSION_DURATION = 1; // en secondes
    private static final float EXPLOSION_SIZE = 128; // en pixels
    private static Texture explosionTexture = new Texture(Gdx.files.internal("explosion.png"));
    private Sprite sprite = new Sprite(explosionTexture);
    private float scale = 0;

    /**
     * Constructeur
     * @param position coordonnées de l'explosion
     * @param color couleur de l'explosion
     */
    public Explosion(Vector2 position, ColorUtils.Color color) {
        sprite.setColor(color.getValue());
        sprite.setPosition(position.x - EXPLOSION_SIZE / 2, position.y - EXPLOSION_SIZE / 2);
        sprite.setSize(EXPLOSION_SIZE, EXPLOSION_SIZE);
        sprite.setScale(scale);
        sprite.setOriginCenter();
    }

    @Override
    public void update() {
        // met à jour la taille de l'explosion
        scale += Gdx.graphics.getDeltaTime() * (1/EXPLOSION_DURATION);
        sprite.setScale(scale);

        // transparence en fonction de la taille de l'explosion
        sprite.setAlpha(1 - scale);
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }


    @Override
    public boolean isMarkedForRemoval() {
        // retire l'explosion de la boucle si elle est plus grande que sa taille max
        return scale > 1;
    }
}
