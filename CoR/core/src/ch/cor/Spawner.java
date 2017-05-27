package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 05.05.17
 */
public class Spawner {
    private static final int STAR_COUNT = 1000;
    private float spawnRate = 0.5f; // secondes entre les apparitions
    private float timeSinceLastSpawn = 0f;
    private float rockSpawnChance = 1f;
    private float iceBlockSpawnChance = 0.1f;
    private float newStateChance = 0.1f;
    private Random random;
    private State spawnType;

    private enum State {
        WARM,
        COLD
    }

    public Spawner() {
        random = new Random();
        spawnType = State.values()[random.nextInt(State.values().length)];

        // étoiles
        for (int i = 0; i < STAR_COUNT; i++) {
            EntityManager.getInstance().addEntity(new Star());
        }
    }

    public void tick() {
        timeSinceLastSpawn += Gdx.graphics.getDeltaTime();

        if (timeSinceLastSpawn > spawnRate) {
            if (random.nextDouble() < newStateChance) {
                spawnType = State.values()[random.nextInt(State.values().length)];
            }


            if (random.nextDouble() < iceBlockSpawnChance) {
                EntityManager.getInstance().addEntity(new IceBlock(Gdx.graphics.getWidth(),
                        random.nextFloat() * Gdx.graphics.getHeight()));
            }
            if (random.nextDouble() < rockSpawnChance) {
                EntityManager.getInstance().addEntity(new Rock(Gdx.graphics.getWidth(),
                        random.nextFloat() * Gdx.graphics.getHeight(),
                        spawnType == State.WARM ? ColorUtils.getRandomNWarmColor() : ColorUtils.getRandomColdColor()));
            }

            timeSinceLastSpawn = 0;
        }
    }
}
