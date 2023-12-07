package chess.factories;

import commons.game.Color;
import commons.piece.Piece;
import commons.piece.PieceFactory;
import commons.piece.PieceName;
import commons.rules.boardDependantRules.BoardDependantSpecialRule;
import chess.rules.boardDependant.PawnPromotion;
import chess.rules.commonSpecialRules.PawnDoubleMovementSpecialRule;
import commons.rules.commonSpecialRules.SpecialRule;
import commons.rules.movementRules.DiagonalMovement;
import commons.rules.movementRules.MovementRule;
import commons.rules.movementRules.VerticalMovement;
import commons.rules.restrictionRules.*;

public class PawnFactory implements PieceFactory {

    // Weather has or not special rules, the movement rules are always the same. Only the restrictions change.

    @Override
    public Piece createPiece(int id, Color color) {
        MovementRule[] movementRules = new MovementRule[]{new VerticalMovement(), new DiagonalMovement()};
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new VerticalMaxQuantityRule(1), new HorizontalMaxQuantityRule(1), new CannotCaptureVertically(), new OnlyForwardMovementIsValid(), new DiagonalMaxQuantityRule(1), new DiagonalMustCaptureRule()};
        SpecialRule[] specialRules = new SpecialRule[]{new PawnDoubleMovementSpecialRule(movementRules, restrictionRules)};
        BoardDependantSpecialRule[] promotionRules = new BoardDependantSpecialRule[]{new PawnPromotion()};
        return new Piece(id, PieceName.PAWN, "P", color, movementRules, restrictionRules, specialRules, promotionRules);
    }

}
