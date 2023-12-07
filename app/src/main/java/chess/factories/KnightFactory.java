package chess.factories;

import commons.game.Color;
import commons.piece.Piece;
import commons.piece.PieceFactory;
import commons.piece.PieceName;
import commons.rules.movementRules.LMovement;
import commons.rules.movementRules.MovementRule;
import commons.rules.restrictionRules.RestrictionRule;

public class KnightFactory implements PieceFactory {
    MovementRule[] movementRules = new MovementRule[]{new LMovement()};

    @Override
    public Piece createPiece(int id, Color color) {
        RestrictionRule[] restrictionRules = new RestrictionRule[]{}; //Knights don't have any restriction.
        return new Piece(id, PieceName.KNIGHT, "Kn", color, movementRules, restrictionRules);
    }

    @Override
    public Piece createPieceWithSpecialRules(int id, Color color) {
        return null;
    }
}
