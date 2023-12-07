package checkers.factories;

import commons.game.Color;
import commons.piece.Piece;
import commons.piece.PieceFactory;
import commons.piece.PieceName;
import commons.rules.boardDependantRules.BoardDependantSpecialRule;
import commons.rules.boardDependantRules.CheckersCapturingRule;
import commons.rules.boardDependantRules.ManPromotionRule;
import commons.rules.commonSpecialRules.SpecialRule;
import commons.rules.movementRules.DiagonalMovement;
import commons.rules.movementRules.MovementRule;
import commons.rules.restrictionRules.CannotLandOnPiece;
import commons.rules.restrictionRules.DiagonalCheckersCapture;
import commons.rules.restrictionRules.OnlyForwardMovementIsValid;
import commons.rules.restrictionRules.RestrictionRule;

public class ManFactory implements PieceFactory {


    @Override
    public Piece createPiece(int id, Color color) {
        MovementRule[] movementRules = new MovementRule[]{new DiagonalMovement()};
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new OnlyForwardMovementIsValid(), new DiagonalCheckersCapture(), new CannotLandOnPiece()};
        BoardDependantSpecialRule[] boardDependantSpecialRules = new BoardDependantSpecialRule[]{new ManPromotionRule(), new CheckersCapturingRule()};
        return new Piece(id, PieceName.MAN, "M", color, movementRules, restrictionRules, new SpecialRule[]{}, boardDependantSpecialRules);
    }

    @Override
    public Piece createPieceWithSpecialRules(int id, Color color) {
        return null;
    }
}
