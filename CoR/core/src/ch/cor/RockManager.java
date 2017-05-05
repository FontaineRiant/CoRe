package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 05.05.17
 */
public class RockManager implements LiveDrawable {
    private float spawnRate = 0.2f; // secondes entre de apparitions
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

        if(timeSinceLastSpawn > spawnRate) {
            rocks.add(new Rock(Gdx.graphics.getWidth(), random.nextFloat()*Gdx.graphics.getHeight(), Color.BROWN));
            timeSinceLastSpawn = 0;
        }

        ArrayList<Rock> toBeDeleted = new ArrayList<Rock>();
        for(Rock rock : rocks) {
            rock.update();
            if(rock.isOut()) {
                toBeDeleted.add(rock);
            }
        }

        rocks.removeAll(toBeDeleted);
    }

    @Override
    public void draw(Batch batch) {
        for(Rock rock : rocks) {
            rock.draw(batch);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isOut() {
        return false;
    }
}
