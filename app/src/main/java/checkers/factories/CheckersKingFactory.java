package checkers.factories;

import commons.game.Color;
import commons.game.Piece;
import commons.game.PieceFactory;
import commons.game.PieceName;
import commons.rules.*;

public class CheckersKingFactory implements PieceFactory {
    @Override
    public Piece createPiece(int id, Color color) {
        MovementRule[] movementRules = new MovementRule[]{new DiagonalMovement()};
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new DiagonalCheckersCapture(), new CannotLandOnPiece()};
        BoardDependantSpecialRule[] boardDependantSpecialRules = new BoardDependantSpecialRule[]{new CheckersCapturingRule()};
        return new Piece(id, PieceName.CHECKERS_KING, "k", color, movementRules, restrictionRules, new SpecialRule[]{}, boardDependantSpecialRules);
    }

    @Override
    public Piece createPieceWithSpecialRules(int id, Color color) {
        return null;
    }
}
