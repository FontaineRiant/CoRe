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
public class RockManager implements LiveDrawable {
    private float spawnRate = 0.5f; // secondes entre les apparitions
    private float bigRockSpawnChance = 0.2f;
    private float iceBlockSpawnChance = 0.1f;
    private float newStateChance = 0.1f;
    private float timeSinceLastSpawn = 0f;
    private ArrayList<Rock> rocks;
    private Random random;
    private State spawnType;

    private enum State {
        WARM,
        COLD
    }

    public RockManager() {
        random = new Random();
        rocks = new ArrayList<Rock>();
        spawnType = State.values()[random.nextInt(State.values().length)];
    }

    @Override
    public void update() {
        timeSinceLastSpawn += Gdx.graphics.getDeltaTime();

        if (timeSinceLastSpawn > spawnRate) {
            if (random.nextDouble() < newStateChance) {
                spawnType = State.values()[random.nextInt(State.values().length)];
            }

            if (random.nextDouble() < bigRockSpawnChance) {
                rocks.add(new BigRock(Gdx.graphics.getWidth(),
                        random.nextFloat() * Gdx.graphics.getHeight(),
                        spawnType == State.WARM ? ColorUtils.getRandomNWarmColor() : ColorUtils.getRandomColdColor(),
                        this));
            }

            if (random.nextDouble() < iceBlockSpawnChance) {
                rocks.add(new IceBlock(Gdx.graphics.getWidth(),
                        random.nextFloat() * Gdx.graphics.getHeight(),
                        this));
            }

            rocks.add(new Rock(Gdx.graphics.getWidth(),
                    random.nextFloat() * Gdx.graphics.getHeight(),
                    spawnType == State.WARM ? ColorUtils.getRandomNWarmColor() : ColorUtils.getRandomColdColor(),
                    this));
            timeSinceLastSpawn = 0;
        }

        ArrayList<Rock> toBeDeleted = new ArrayList<Rock>();
        for (Rock rock : rocks) {
            rock.update();
            if (rock.isOut()) {
                toBeDeleted.add(rock);
            }
        }

        rocks.removeAll(toBeDeleted);
    }

    @Override
    public void draw(Batch batch) {
        for (LiveDrawable entity : rocks) {
            entity.draw(batch);
        }
    }

    @Override
    public void dispose() {

    }

    public Rock getNearestRock(Vector2 coord) {
        Rock nearest = null;
        float distance = Float.MAX_VALUE;
        for (Rock rock : rocks) {
            float tempDistance = rock.getPos().dst2(coord);
            if (distance > tempDistance && !rock.isExploding()) {
                nearest = rock;
                distance = tempDistance;
            }
        }
        return nearest;
    }

    @Override
    public boolean isOut() {
        return false;
    }

    public ArrayList<Rock> getRocks() {
        return rocks;
    }
}
