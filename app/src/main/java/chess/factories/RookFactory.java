package chess.factories;

import commons.game.Color;
import commons.piece.Piece;
import commons.piece.PieceFactory;
import commons.piece.PieceName;
import commons.rules.movementRules.HorizontalMovement;
import commons.rules.movementRules.MovementRule;
import commons.rules.movementRules.VerticalMovement;
import commons.rules.restrictionRules.PieceInterposesHorizontallyRestriction;
import commons.rules.restrictionRules.PieceInterposesVerticallyRestriction;
import commons.rules.restrictionRules.RestrictionRule;

public class RookFactory implements PieceFactory {

    MovementRule[] movementRules = new MovementRule[]{new VerticalMovement(), new HorizontalMovement()};
    @Override
    public Piece createPiece(int id, Color color) {
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new PieceInterposesVerticallyRestriction(), new PieceInterposesHorizontallyRestriction()};
        return new Piece(id, PieceName.ROOK, "R", color, movementRules, restrictionRules);
    }

    @Override
    public Piece createPieceWithSpecialRules(int id, Color color) {
        return null;
    }
}
