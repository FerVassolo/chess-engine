package chess.game;

import commons.Color;
import commons.Piece;
import commons.PieceFactory;
import commons.PieceName;
import commons.rules.LMovement;
import commons.rules.MovementRule;
import commons.rules.RestrictionRule;

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
