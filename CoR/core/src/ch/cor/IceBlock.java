package ch.cor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 18.05.17
 */
public class IceBlock extends Rock {

    private static Texture texture = new Texture(Gdx.files.internal("iceblock.png"));

    public IceBlock(float x, float y, RockManager rockManager) {
        super(x, y, ColorUtils.Color.WHITE, rockManager);
        sprite.setTexture(texture);
    }

    @Override
    public void handleReaction(Reaction reaction) {
        if (reaction.getReactionSize() > 1) {
            reaction.addLink(position, ColorUtils.Color.WHITE);
            explode();
            ReactionHandler nearest = rockManager.getNearestRock(position);
            if(nearest != null) {
                nearest.handleReaction(reaction);
            }
        }
    }
}
