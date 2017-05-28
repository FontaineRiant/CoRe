package ch.cor;

import com.badlogic.gdx.math.Rectangle;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief
 */
public interface ReactionHandler {
    void handleReaction(Reaction reaction);
    Rectangle getBounds();
}
