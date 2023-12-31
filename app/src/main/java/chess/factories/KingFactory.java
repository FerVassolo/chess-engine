package chess.factories;

import commons.piece.PieceName;
import commons.game.Color;
import commons.piece.Piece;
import commons.piece.PieceFactory;
import commons.rules.boardHistoryDependantRules.BoardHistoryDependantSpecialRule;
import chess.rules.boardHistoryDependant.CastlingRule;
import commons.rules.movementRules.DiagonalMovement;
import commons.rules.movementRules.HorizontalMovement;
import commons.rules.movementRules.MovementRule;
import commons.rules.movementRules.VerticalMovement;
import commons.rules.restrictionRules.DiagonalMaxQuantityRule;
import commons.rules.restrictionRules.HorizontalMaxQuantityRule;
import commons.rules.restrictionRules.RestrictionRule;
import commons.rules.restrictionRules.VerticalMaxQuantityRule;

public class KingFactory implements PieceFactory {
    MovementRule[] movementRules = new MovementRule[]{new DiagonalMovement(), new VerticalMovement(), new HorizontalMovement()};
    @Override
    public Piece createPiece(int id, Color color) {
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new DiagonalMaxQuantityRule(1), new VerticalMaxQuantityRule(1), new HorizontalMaxQuantityRule(1)};
        BoardHistoryDependantSpecialRule[] modifyingSpecialRules = new BoardHistoryDependantSpecialRule[]{new CastlingRule()};
        return new Piece(id, PieceName.KING, "K", color, movementRules, restrictionRules, modifyingSpecialRules);
    }

}
