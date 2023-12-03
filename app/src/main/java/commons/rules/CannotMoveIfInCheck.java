package commons.rules;

/**
 * cannot move if the new position leaves the player in check.
 */

import commons.game.Board;
import commons.game.Color;
import commons.game.Movement;
import commons.game.Position;

public class CannotMoveIfInCheck implements RestrictionRule{


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
