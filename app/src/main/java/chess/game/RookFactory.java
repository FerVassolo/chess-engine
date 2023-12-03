package chess.game;

import commons.game.Color;
import commons.game.Piece;
import commons.game.PieceFactory;
import commons.game.PieceName;
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
