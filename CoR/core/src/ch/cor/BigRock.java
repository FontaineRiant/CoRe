package ch.cor;

/**
 * Project : CoR
 * Author(s) : Antoine Friant
 * Date : 18.05.17
 */
public class BigRock extends Rock {
    private static final int POINT_VALUE = 10;
    private int hitpoints = 2;

    public BigRock(float x, float y, ColorUtils.Color color, RockManager rockManager) {
        super(x, y, color, rockManager);
        sprite.setScale(2f);
    }

    @Override
    public void handleReaction(Reaction reaction) {
        if ((color == reaction.getColor() && --hitpoints < 1) || reaction.getColor() == ColorUtils.Color.WHITE) {
            reaction.addLink(position, reaction.getColor() == ColorUtils.Color.WHITE ? ColorUtils.Color.WHITE : color);
            isExploding = true;
            CoR.points += POINT_VALUE;
            ReactionHandler nearest = rockManager.getNearestRock(position);
            if (nearest != null) {
                nearest.handleReaction(reaction);
                nearest = rockManager.getNearestRock(position);
                if (nearest != null) {
                    nearest.handleReaction(reaction);
                }
            }
        }
    }
}
