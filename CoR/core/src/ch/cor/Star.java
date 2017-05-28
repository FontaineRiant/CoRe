package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief Star représente un étoile de l'arrière plan du jeu
 */
public class Star implements Entity {
    private static final float SPEED = 750; // en pixels par secondes
    private static final float BASE_SIZE = 3; // taille des étoiles les plus grandes (en pixels)
    private static Texture texture = new Texture(Gdx.files.internal("stardot.png"));
    private static Random random = new Random();
    private Sprite sprite = new Sprite(texture);
    private Vector2 position;
    private float scale;

    public Star() {
        position = new Vector2();
        init();
        position.x = random.nextFloat() * Gdx.graphics.getWidth();

        sprite.setPosition(position.x, position.y);
    }

    private void init() {
        // déplace l'étoile à droite de l'écran à une hauteur aléatoire
        position.x = Gdx.graphics.getWidth();
        position.y = random.nextFloat() * Gdx.graphics.getHeight();

        // taille et vitesse aléatoire
        scale = random.nextFloat();
        sprite.setSize(scale * BASE_SIZE, scale * BASE_SIZE);
    }

    @Override
    public void update() {
        // déplace l'étoie vers la gauche à une vitesse proportionnelle à sa taille
        position.x -= SPEED * scale * Gdx.graphics.getDeltaTime();

        // si elle sort de l'écran, on la réinitialise
        if (position.x < -sprite.getWidth()) {
            init();
        }

        //effectue le déplacement du sprite
        sprite.setPosition(position.x, position.y);
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    @Override
    public boolean isMarkedForRemoval() {
        // les étoiles ne se détruisent jamais
        return false;
    }
}
