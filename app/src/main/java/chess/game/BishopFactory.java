package chess.game;

import commons.Color;
import commons.Piece;
import commons.PieceFactory;
import commons.PieceName;
import chess.rules.DiagonalMovement;
import chess.rules.MovementRule;
import chess.rules.PieceInterposesDiagonallyRestriction;
import chess.rules.RestrictionRule;

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
