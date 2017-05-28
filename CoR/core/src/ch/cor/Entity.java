package ch.cor;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief
 */
public interface Entity {
    void update();
    void draw(Batch batch);
    boolean isMarkedForRemoval();
}
