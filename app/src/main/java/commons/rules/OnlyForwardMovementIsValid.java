package commons.rules;

import commons.Position;
import commons.Board;
import commons.Piece;
import commons.Color;

public class OnlyForwardMovementIsValid implements RestrictionRule {

    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) throws IllegalArgumentException {
        Piece piece = board.getPiece(pieceOriginalPos);
        Color pieceColor = piece.getColor();
        int sub =  pieceNewPos.getRow() - pieceOriginalPos.getRow();
        if (pieceColor == Color.WHITE) {
            if (sub > 0) {
                return true;
            }
            System.out.println("Movement must be forward");
            return false;
        }
        else if (pieceColor == Color.BLACK){
            if (sub < 0) {
                return true;
            }
            System.out.println("Movement must be forward");
            return false;
        }
        else{
            System.out.println("Color is not yet implemented");
            return false;
        }
    }

    @Override
    public String errorMessage() {
        return "Movement must be forward";
    }

}
