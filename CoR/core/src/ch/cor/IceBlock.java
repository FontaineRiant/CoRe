package ch.cor;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 18.05.17
 */
public class IceBlock extends Rock {
    public IceBlock(float x, float y, RockManager rockManager) {
        super(x, y, ColorUtils.Color.WHITE, rockManager);
    }

    @Override
    public void handleReaction(Reaction reaction) {
        if (reaction.getReactionSize() > 1) {
            reaction.addLink(position, ColorUtils.Color.WHITE);
            isExploding = true;
            ReactionHandler nearest = rockManager.getNearestRock(position);
            if(nearest != null) {
                nearest.handleReaction(reaction);
            }
        }
    }
}
