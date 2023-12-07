package chess.factories;

import commons.game.Color;
import commons.piece.Piece;
import commons.piece.PieceFactory;
import commons.piece.PieceName;
import commons.rules.movementRules.DiagonalMovement;
import commons.rules.movementRules.LMovement;
import commons.rules.movementRules.MovementRule;
import commons.rules.restrictionRules.PieceInterposesDiagonallyRestriction;
import commons.rules.restrictionRules.RestrictionRule;

public class ArchbishopFactory implements PieceFactory {

    @Override
    public Piece createPiece(int id, Color color) {
        MovementRule[] movementRules = new MovementRule[]{new DiagonalMovement(), new LMovement()};
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new PieceInterposesDiagonallyRestriction()};
        return new Piece(id, PieceName.ARCHBISHOP, "Akn", color, movementRules, restrictionRules);
    }
}
