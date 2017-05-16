package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.Random;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 05.05.17
 */
public class RockManager implements LiveDrawable {
    private float spawnRate = 0.2f; // secondes entre les apparitions
    private float timeSinceLastSpawn = 0f;
    private ArrayList<Rock> rocks;
    private Random random;

    public RockManager() {
        random = new Random();
        rocks = new ArrayList<Rock>();

    }

    @Override
    public void update() {
        timeSinceLastSpawn += Gdx.graphics.getDeltaTime();

        if (timeSinceLastSpawn > spawnRate) {
            rocks.add(new Rock(Gdx.graphics.getWidth(),
                    random.nextFloat() * Gdx.graphics.getHeight(),
                    ColorUtils.getRandomNonBlackOrWhiteColor(), this));
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
        for (Rock rock : rocks) {
            rock.draw(batch);
        }
    }

    @Override
    public void dispose() {

    }

    public Rock getNearest(float x, float y) {
        Rock nearest = null;
        float distance = Float.MAX_VALUE;
        for (Rock rock : rocks) {
            float tempDistance = rock.getPos().dst2(x, y);
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
