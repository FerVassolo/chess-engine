package game;

import rules.*;

public class KingFactory implements PieceFactory{
    MovementRule[] movementRules = new MovementRule[]{new DiagonalMovement(), new VerticalMovement(), new HorizontalMovement()};
    @Override
    public Piece createPiece(int id, Color color) {
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new DiagonalMaxQuantityRule(1), new StraightMaxQuantityRule(1)};
        return new Piece(id, PieceName.KING, "K", color, movementRules, restrictionRules);
    }

    @Override
    public Piece createPieceWithSpecialRules(int id, Color color) {
        return null;
    }
}
