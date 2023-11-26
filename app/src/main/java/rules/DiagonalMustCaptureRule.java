package rules;

import game.Position;
import game.Board;
import game.Color;

public class DiagonalMustCaptureRule implements RestrictionRule{

    // Check
    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        if(!isDiagonal(pieceOriginalPos, pieceNewPos)) {
            return true; // If the movement isn't diagonal this restriction doesn't apply.
        }
        Color currentColor = board.getPiece(pieceOriginalPos).getColor();
        if( board.getPiece(pieceNewPos) == null){
            System.out.println("Diagonal movement must capture");
            return false;
        }
        Color newColor = board.getPiece(pieceNewPos).getColor();
        if(currentColor != newColor)
            return true;
        System.out.println("Diagonal movement must capture");
        return false;
    }

    @Override
    public String errorMessage() {
        return "Diagonal movement must capture";
    }

    public boolean isDiagonal(Position pieceOriginalPos, Position pieceNewPos){
        return new DiagonalMovement().validateMovement(pieceOriginalPos, pieceNewPos);
    }

}
