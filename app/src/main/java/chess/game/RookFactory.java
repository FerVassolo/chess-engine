package chess.game;

import commons.Color;
import commons.Piece;
import commons.PieceFactory;
import commons.PieceName;
import commons.rules.*;
import commons.rules.*;

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
