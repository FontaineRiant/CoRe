package ch.cor;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 05.05.17
 */
public interface LiveDrawable {
    public void update();
    public void draw(Batch batch);
    public void dispose();
    public boolean isOut();
}
