package ch.cor;

import com.badlogic.gdx.Gdx;

import java.util.Random;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief Spawner est la classe qui créé les obstacles et les étoiles à intervalles réguliers
 */
public class Spawner {
    private static final int STAR_COUNT = 1000; // nombre d'étoiles en arrière plan
    private static final float SPAWN_ACCELERATION = 0.005f; // accélération en tick/seconde^2
    private float spawnRate = 0.5f; // secondes entre les spawns
    private float timeSinceLastSpawn = 0f; // temps depuis la dernière vague de spawn
    private float rockSpawnChance = 1f; // chance de faire apparaitre un astéroide à chaque spawn
    private float iceBlockSpawnChance = 0.1f; // chance de faire apparaitre un bloc de glace à chaque spawn
    private float satelliteSpawnChance = 0.02f; // chance de faire apparaitre un satellite à chaque spawn
    private float newStateChance = 0.1f; // chance de passer d'un état à l'autre (chaud/froid)
    private Random random;
    private State spawnType; // état du spawner (chaud/froid)

    private enum State {
        WARM,
        COLD
    }

    /**
     * Constructeur du spawner
     */
    public Spawner() {
        random = new Random();
        // état de départ aléatoire
        spawnType = State.values()[random.nextInt(State.values().length)];

        // génération des étoiles
        for (int i = 0; i < STAR_COUNT; i++) {
            EntityManager.getInstance().addEntity(new Star());
        }
    }

    /**
     * @brief Tente de faire apparaitre des objets (en respectant le rythme des ticks)
     */
    public void tick() {
        timeSinceLastSpawn += Gdx.graphics.getDeltaTime();
        // accélère le rythme des apparitions
        spawnRate -= SPAWN_ACCELERATION * Gdx.graphics.getDeltaTime();

        // si le temps depuis le dernier spawn est suffisemment long, déclanche des spawns
        if (timeSinceLastSpawn > spawnRate) {

            // tente de changer d'état
            if (random.nextDouble() < newStateChance) {
                spawnType = State.values()[random.nextInt(State.values().length)];
            }

            // tente de faire apparaitre un bloc de glace
            if (random.nextDouble() < iceBlockSpawnChance) {
                EntityManager.getInstance().addEntity(new IceShard());
            }

            // tente de faire apparaitre un satellite
            if (random.nextDouble() < satelliteSpawnChance) {
                EntityManager.getInstance().addEntity(new Satellite());
            }

            // tente de faire apparaitre un astéroide
            if (random.nextDouble() < rockSpawnChance) {
                EntityManager.getInstance().addEntity(new Rock(spawnType == State.WARM ? ColorUtils.getRandomWarmColor() : ColorUtils.getRandomColdColor()));
            }

            // réinitialise le temps depuis le dernier spawn
            timeSinceLastSpawn = 0;
        }
    }
}
