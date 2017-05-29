package ch.cor;

import com.badlogic.gdx.Gdx;

import java.util.Random;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief
 */
public class Spawner {
    private static final int STAR_COUNT = 1000;
    private static final float SPAWN_ACCELERATION = 0.005f; // spawn/s^2
    private float spawnRate = 0.5f; // secondes entre les apparitions
    private float timeSinceLastSpawn = 0f;
    private float rockSpawnChance = 1f;
    private float iceBlockSpawnChance = 0.1f;
    private float satelliteSpawnChance = 0.02f;
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

        // génération des étoiles
        for (int i = 0; i < STAR_COUNT; i++) {
            EntityManager.getInstance().addEntity(new Star());
        }
    }

    public void tick() {
        timeSinceLastSpawn += Gdx.graphics.getDeltaTime();
        spawnRate -= SPAWN_ACCELERATION * Gdx.graphics.getDeltaTime();

        if (timeSinceLastSpawn > spawnRate) {
            if (random.nextDouble() < newStateChance) {
                spawnType = State.values()[random.nextInt(State.values().length)];
            }


            if (random.nextDouble() < iceBlockSpawnChance) {
                EntityManager.getInstance().addEntity(new IceShard());
            }

            if (random.nextDouble() < satelliteSpawnChance) {
                EntityManager.getInstance().addEntity(new Satellite());
            }

            if (random.nextDouble() < rockSpawnChance) {
                EntityManager.getInstance().addEntity(new Rock(spawnType == State.WARM ? ColorUtils.getRandomNWarmColor() : ColorUtils.getRandomColdColor()));
            }

            timeSinceLastSpawn = 0;
        }
    }
}
