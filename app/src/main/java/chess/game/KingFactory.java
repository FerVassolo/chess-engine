package chess.game;

import chess.rules.*;
import commons.Color;
import commons.Piece;
import commons.PieceFactory;
import commons.PieceName;
import chess.rules.*;

public class KingFactory implements PieceFactory {
    MovementRule[] movementRules = new MovementRule[]{new DiagonalMovement(), new VerticalMovement(), new HorizontalMovement()};
    @Override
    public Piece createPiece(int id, Color color) {
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new DiagonalMaxQuantityRule(1), new VerticalMaxQuantityRule(1), new HorizontalMaxQuantityRule(1)};
        return new Piece(id, PieceName.KING, "K", color, movementRules, restrictionRules);
    }

    @Override
    public Piece createPieceWithSpecialRules(int id, Color color) {
        return null;
    }
}
