package game;

import rules.*;

public class QueenFactory implements PieceFactory{
    MovementRule[] movementRules = new MovementRule[]{new DiagonalMovement(), new VerticalMovement(), new HorizontalMovement()};
    @Override
    public Piece createPiece(int id, Color color) {
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new PieceInterposesVerticallyRestriction(), new PieceInterposesHorizontallyRestriction(), new PieceInterposesDiagonallyRestriction()};
        return new Piece(id, PieceName.QUEEN, "Q", color, movementRules, restrictionRules);
    }

    @Override
    public Piece createPieceWithSpecialRules(int id, Color color) {
        return null;
    }
}
