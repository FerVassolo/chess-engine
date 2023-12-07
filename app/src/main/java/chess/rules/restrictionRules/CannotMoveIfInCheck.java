package chess.rules.restrictionRules;

/**
 * cannot move if the new position leaves the player in check.
 */

import commons.board.Board;
import commons.game.Color;
import commons.movement.Movement;
import commons.board.Position;
import commons.rules.restrictionRules.RestrictionRule;
import chess.rules.utils.CheckUtils;

public class CannotMoveIfInCheck implements RestrictionRule {


    // todo, see if this is correct...
    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        Board movedPieceBoard = new Movement().movePiece(board, pieceOriginalPos, pieceNewPos);
        Color color =  board.getPiece(pieceOriginalPos).getColor();
        CheckUtils checkUtils = new CheckUtils();
        return checkUtils.verifyBoardIsNotInCheck(movedPieceBoard, color);
    }

    @Override
    public String errorMessage() {
        return "Cannot leave your king on check";
    }

}
