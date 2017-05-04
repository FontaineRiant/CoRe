package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private final static float MAX_SPEED = 700; // pixels/sec
    private final static float ACCELERATION = 3000; // pixels/sec^2
    private final static float INERTIA = 3000; // pixels/sec^2
    private final static float SIZE = 40;

    private float x, y;
    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("ship.png")));
    private Vector2 vector = new Vector2();

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        sprite.setSize(SIZE, SIZE);
        sprite.setOriginCenter();
        sprite.setRotation(-135);
    }

    private float applyInertia(float speed) {
        float dec = INERTIA * Math.signum(speed) * Gdx.graphics.getDeltaTime();
        if (Math.abs(speed) > Math.abs(dec)) {
            return speed - INERTIA * Math.signum(speed) * Gdx.graphics.getDeltaTime();
        } else {
            return 0;
        }
    }

    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Inputs de déplacements
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            vector.x += deltaTime * ACCELERATION;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            vector.x = applyInertia(vector.x);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            vector.x -= deltaTime * ACCELERATION;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            vector.y += deltaTime * ACCELERATION;
        } else if(!Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            vector.y = applyInertia(vector.y);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            vector.y -= deltaTime * ACCELERATION;
        }

        // Impose la vitesse max
        vector.x = Math.min(Math.abs(vector.x), MAX_SPEED) * Math.signum(vector.x);
        vector.y = Math.min(Math.abs(vector.y), MAX_SPEED) * Math.signum(vector.y);

        // Affecte la nouvelle position
        x += vector.x * deltaTime;
        y += vector.y * deltaTime;

        // Comportement du vecteur aux bordures
        if (x < 0 || x >= Gdx.graphics.getWidth() - sprite.getWidth()) {
            vector.x = 0;
        }

        if (y < 0 || y >= Gdx.graphics.getHeight() - sprite.getHeight()) {
            vector.y = 0;
        }

        // Bordures du jeu, repositionnement
        x = Math.max(0, Math.min(x, Gdx.graphics.getWidth() - sprite.getWidth()));
        y = Math.max(0, Math.min(y, Gdx.graphics.getHeight() - sprite.getHeight()));

        // Effectue le déplacement
        sprite.setPosition(x, y);
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }

    public void draw(Batch batch) {
        sprite.draw(batch);
    }
}
