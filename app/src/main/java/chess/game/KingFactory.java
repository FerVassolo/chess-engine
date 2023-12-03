package chess.game;

import commons.game.PieceName;
import commons.rules.*;
import commons.game.Color;
import commons.game.Piece;
import commons.game.PieceFactory;

public class KingFactory implements PieceFactory {
    MovementRule[] movementRules = new MovementRule[]{new DiagonalMovement(), new VerticalMovement(), new HorizontalMovement()};
    @Override
    public Piece createPiece(int id, Color color) {
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new DiagonalMaxQuantityRule(1), new VerticalMaxQuantityRule(1), new HorizontalMaxQuantityRule(1)};
        BoardHistoryDependantSpecialRule[] modifyingSpecialRules = new BoardHistoryDependantSpecialRule[]{new CastlingRule()};
        return new Piece(id, PieceName.KING, "K", color, movementRules, restrictionRules, modifyingSpecialRules);
    }

    @Override
    public Piece createPieceWithSpecialRules(int id, Color color) {
        return null;
    }
}
