package chess.factories;

import commons.piece.PieceName;
import commons.game.Color;
import commons.piece.Piece;
import commons.piece.PieceFactory;
import commons.rules.movementRules.DiagonalMovement;
import commons.rules.movementRules.HorizontalMovement;
import commons.rules.movementRules.MovementRule;
import commons.rules.movementRules.VerticalMovement;
import commons.rules.restrictionRules.PieceInterposesDiagonallyRestriction;
import commons.rules.restrictionRules.PieceInterposesHorizontallyRestriction;
import commons.rules.restrictionRules.PieceInterposesVerticallyRestriction;
import commons.rules.restrictionRules.RestrictionRule;

public class QueenFactory implements PieceFactory {
    MovementRule[] movementRules = new MovementRule[]{new DiagonalMovement(), new VerticalMovement(), new HorizontalMovement()};
    @Override
    public Piece createPiece(int id, Color color) {
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new PieceInterposesVerticallyRestriction(), new PieceInterposesHorizontallyRestriction(), new PieceInterposesDiagonallyRestriction()};
        return new Piece(id, PieceName.QUEEN, "Q", color, movementRules, restrictionRules);
    }


}
