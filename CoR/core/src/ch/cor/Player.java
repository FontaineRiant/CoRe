package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Player implements LiveDrawable {
    private final static float MAX_SPEED = 1000; // pixels/sec
    private final static float ACCELERATION = 5000; // pixels/sec^2
    private final static float INERTIA = 3000; // pixels/sec^2
    private final static float SIZE = 40;
    private final static float RATE_OF_FIRE = 0.1f; // secondes entre 2 tirs
    private final static int RED_KEY = Input.Keys.J;
    private final static int YELLOW_KEY = Input.Keys.K;
    private final static int BLUE_KEY = Input.Keys.L;
    private final static int UP_KEY = Input.Keys.W;
    private final static int DOWN_KEY = Input.Keys.S;
    private final static int LEFT_KEY = Input.Keys.A;
    private final static int RIGHT_KEY = Input.Keys.D;

    private RockManager rockManager;
    private float timeSinceLastShot = 0;
    private float x, y;
    private Sprite sprite = new Sprite(new Texture(Gdx.files.internal("ship.png")));
    private Vector2 vector = new Vector2();
    private ArrayList<Shot> shots = new ArrayList<Shot>();

    public Player(float x, float y, RockManager rockManager) {
        this.rockManager = rockManager;
        this.x = x;
        this.y = y;
        sprite.setSize(SIZE, SIZE);
        sprite.setOriginCenter();
        sprite.setRotation(-135);
        sprite.setPosition(x, y);
    }

    private float applyInertia(float speed) {
        float dec = INERTIA * Math.signum(speed) * Gdx.graphics.getDeltaTime();
        if (Math.abs(speed) > Math.abs(dec)) {
            return speed - INERTIA * Math.signum(speed) * Gdx.graphics.getDeltaTime();
        } else {
            return 0;
        }
    }

    @Override
    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Mort
        for(Rock rock : rockManager.getRocks()) {
            if (sprite.getBoundingRectangle().overlaps(rock.getBounds())) {
                System.exit(0);
            }
        }

        // Inputs de déplacements
        if (Gdx.input.isKeyPressed(RIGHT_KEY)) {
            vector.x += deltaTime * ACCELERATION;
        } else if (vector.x > 0){
            vector.x = applyInertia(vector.x);
        }

        if (Gdx.input.isKeyPressed(LEFT_KEY)) {
            vector.x -= deltaTime * ACCELERATION;
        } else if (vector.x < 0) {
            vector.x = applyInertia(vector.x);
        }

        if (Gdx.input.isKeyPressed(UP_KEY)) {
            vector.y += deltaTime * ACCELERATION;
        } else if(vector.y > 0) {
            vector.y = applyInertia(vector.y);
        }

        if (Gdx.input.isKeyPressed(DOWN_KEY)) {
            vector.y -= deltaTime * ACCELERATION;
        } else if(vector.y < 0) {
            vector.y = applyInertia(vector.y);
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

        // Tirs
        timeSinceLastShot += Gdx.graphics.getDeltaTime();

        ArrayList<Shot> toBeDeleted = new ArrayList<Shot>();
        for(Shot sh : shots) {
            sh.update();
            if(sh.isOut()) {
                toBeDeleted.add(sh);
            }
        }

        shots.removeAll(toBeDeleted);

        if((Gdx.input.isKeyPressed(RED_KEY) || Gdx.input.isKeyPressed(YELLOW_KEY) || Gdx.input.isKeyPressed(BLUE_KEY))
                && timeSinceLastShot >= RATE_OF_FIRE) {
            ColorUtils.Color color = ColorUtils.Color.WHITE;
            if(Gdx.input.isKeyPressed(RED_KEY)) {
                color = ColorUtils.add(color, ColorUtils.Color.RED);
            }

            if(Gdx.input.isKeyPressed(YELLOW_KEY)) {
                color = ColorUtils.add(color, ColorUtils.Color.YELLOW);
            }

            if(Gdx.input.isKeyPressed(BLUE_KEY)) {
                color = ColorUtils.add(color, ColorUtils.Color.BLUE);
            }

            shots.add(new Shot(x + SIZE, y + SIZE /2, color, rockManager));
            timeSinceLastShot = 0;
        }

    }

    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }

    @Override
    public boolean isOut() {
        return false;
    }

    @Override
    public void draw(Batch batch) {
        for(Shot sh : shots) {
            sh.draw(batch);
        }
        sprite.draw(batch);
    }
}
