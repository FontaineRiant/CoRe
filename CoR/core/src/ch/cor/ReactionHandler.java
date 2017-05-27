package ch.cor;

import com.badlogic.gdx.math.Vector2;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 16.05.17
 */
public interface ReactionHandler {
    void handleReaction(Reaction reaction);

    Vector2 getPos();
}
