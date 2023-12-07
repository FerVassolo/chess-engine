package commons.rules.restrictionRules;

import commons.board.Position;
import commons.board.Board;

public class CannotCaptureVertically implements RestrictionRule{

    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        int originalCol = pieceOriginalPos.getCol();
        int newCol = pieceNewPos.getCol();
        if(originalCol - newCol == 0){
            if(board.getPiece(pieceNewPos) != null)
            {
                // It will print twice cause the on passant checks it and then the normal rules check it again.
                System.out.println("Pawn cannot move vertically if a piece of any color interposes");
                return false;
            }
        }
        return true;
    }

    @Override
    public String errorMessage() {
        return "Pawn cannot move vertically if a piece of any color interposes";
    }
}
