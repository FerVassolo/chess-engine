package checkers.factories;

import commons.game.Color;
import commons.game.Piece;
import commons.game.PieceFactory;
import commons.game.PieceName;
import commons.rules.*;

public class ManFactory implements PieceFactory {


    /** Movement is indeed diagonal and forward, those are already implemented <br>
     now we have the rule that max is 1 if the square to that side is empty<br>
     we have 2 if the square is ocuppied by a other color piece and the next one is empty.<br>
     none if both are ocupied<br>
     none if is same color.<br>
     can make, diagonalRight(0, 1, 2 or 4 or so on) and same for left. and for kings also for top and down<br>
     actually, max diagonal is always 2. whether is 2 or 1 or 0 will depend on what the GAME dictates<br>*/
    @Override
    public Piece createPiece(int id, Color color) {
        MovementRule[] movementRules = new MovementRule[]{new DiagonalMovement()};
        RestrictionRule[] restrictionRules = new RestrictionRule[]{new OnlyForwardMovementIsValid(), new DiagonalCheckersCapture(), new CannotLandOnPiece()};

        // put the promotion and the eating rule.
        // ¿¿¿What if both happen at the same time??? One way is still having the promotion one, other is, after calling the
        // board dependant rules, overlaping, where a Queen has priority of a pawn, and a null from a piece
        // So, null > queen == king > pawn. Sííííííí
        // Ahora lo q pasa es q, como rule en man promotion es active, va a promote y después destruirlo o algo así
        BoardDependantSpecialRule[] boardDependantSpecialRules = new BoardDependantSpecialRule[]{new ManPromotionRule(), new CheckersCapturingRule()};
        return new Piece(id, PieceName.MAN, "M", color, movementRules, restrictionRules, new SpecialRule[]{}, boardDependantSpecialRules);
    }

    @Override
    public Piece createPieceWithSpecialRules(int id, Color color) {
        return null;
    }
}
