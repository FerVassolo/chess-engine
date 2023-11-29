package chess.game;

import commons.Color;
import commons.Piece;
import commons.PieceFactory;
import commons.PieceName;
import commons.rules.DiagonalMovement;
import commons.rules.MovementRule;
import commons.rules.PieceInterposesDiagonallyRestriction;
import commons.rules.RestrictionRule;

public class BishopFactory implements PieceFactory {
    MovementRule[] movementRules = new MovementRule[]{new DiagonalMovement()};
    @Override
    public Piece createPiece(int id, Color color) {
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new PieceInterposesDiagonallyRestriction()};
        return new Piece(id, PieceName.BISHOP, "B", color, movementRules, restrictionRules);
    }

    @Override
    public Piece createPieceWithSpecialRules(int id, Color color) {
        return null;
    }
}
