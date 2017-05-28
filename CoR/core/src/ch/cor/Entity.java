package ch.cor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 05.05.17
 */
public interface Entity {
    void update();
    void draw(Batch batch);
    boolean isMarkedForRemoval();
}
