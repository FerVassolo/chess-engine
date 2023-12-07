package chess.factories;

import commons.game.Color;
import commons.piece.Piece;
import commons.piece.PieceFactory;
import commons.piece.PieceName;
import commons.rules.movementRules.DiagonalMovement;
import commons.rules.movementRules.MovementRule;
import commons.rules.restrictionRules.PieceInterposesDiagonallyRestriction;
import commons.rules.restrictionRules.RestrictionRule;

public class BishopFactory implements PieceFactory {
    MovementRule[] movementRules = new MovementRule[]{new DiagonalMovement()};
    @Override
    public Piece createPiece(int id, Color color) {
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new PieceInterposesDiagonallyRestriction()};
        return new Piece(id, PieceName.BISHOP, "B", color, movementRules, restrictionRules);
    }


}
