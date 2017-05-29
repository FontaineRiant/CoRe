package ch.cor;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * @author Finini Valentin, Friant Antoine, Meier Christopher, Palumbo Daniel, Stalder Lawrence
 * @date 28.05.2017
 * @brief Interface implémentée par les entités qui doivent bouger et s'afficher
 */
public interface Entity {
    /**
     * Met à jour le propriétés de l'entité (vitesse, taille, position, collision, ...)
     */
    void update();

    /**
     * Dessine l'entité. Doit contenir le minimum d'opérations possible.
     * @param batch
     */
    void draw(Batch batch);

    /**
     * @return vrai ssi l'entité est en fin de vie et doit arrêter d'être affichée/mis à jour
     */
    boolean isMarkedForRemoval();
}
