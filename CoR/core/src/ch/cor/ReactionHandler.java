package ch.cor;

import com.badlogic.gdx.math.Rectangle;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief Interface implémentée par les maillons de la chaine de responsabilité
 */
public interface ReactionHandler {
    /**
     * Traite la requête de type Reaction
     * @param reaction requête
     */
    void handleReaction(Reaction reaction);

    /**
     * @return Rectangle représentant la hitbox du handler
     */
    Rectangle getBounds();
}
