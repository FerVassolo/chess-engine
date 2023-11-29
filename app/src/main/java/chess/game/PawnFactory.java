package chess.game;

import chess.rules.*;
import commons.Color;
import commons.Piece;
import commons.PieceFactory;
import commons.PieceName;
import chess.rules.*;

public class PawnFactory implements PieceFactory {

    // Weather has or not special rules, the movement rules are always the same. Only the restrictions change.

    @Override
    public Piece createPiece(int id, Color color) {
        MovementRule[] movementRules = new MovementRule[]{new VerticalMovement(), new DiagonalMovement()};
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new VerticalMaxQuantityRule(1), new HorizontalMaxQuantityRule(1), new CannotCaptureVertically(), new OnlyForwardMovementIsValid(), new DiagonalMaxQuantityRule(1), new DiagonalMustCaptureRule()};
        return new Piece(id, PieceName.PAWN, "P", color, movementRules, restrictionRules);
    }

    public Piece createPieceWithSpecialRules(int id, Color color){
        MovementRule[] movementRules = new MovementRule[]{new VerticalMovement(), new DiagonalMovement()};
        Piece piece = createPiece(id, color);
        SpecialRule[] specialRules = new SpecialRule[]{new PawnDoubleMovementSpecialRule(movementRules, piece.getRestrictionRules())};
        PromotionRule[] promotionRules = new PromotionRule[]{new PawnPromotion()};
        return new Piece(id, PieceName.PAWN, "P", color, movementRules, piece.getRestrictionRules(), specialRules, promotionRules);
    }

}
