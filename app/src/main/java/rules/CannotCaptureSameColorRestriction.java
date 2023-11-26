package rules;

import game.Position;
import game.Board;

public class CannotCaptureSameColorRestriction implements RestrictionRule{
    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        if(board.getPiece(pieceNewPos) == null || board.getPiece(pieceNewPos) == null)
            return true;
        if(board.getPiece(pieceOriginalPos).getColor() == board.getPiece(pieceNewPos).getColor()){
            System.out.println("A Piece cannot capture a same color piece");
            return false;
        }
        return true;
    }

    @Override
    public void printErrorMessage() {
        System.out.println("A Piece cannot capture a same color piece");
    }
}
