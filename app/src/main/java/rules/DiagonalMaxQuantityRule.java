package rules;

import game.Position;
import game.Board;

public class DiagonalMaxQuantityRule implements RestrictionRule{

    int maxQty;

    public DiagonalMaxQuantityRule(int maxQty){
        this.maxQty = maxQty;
    }
    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        if(!isDiagonal(pieceOriginalPos, pieceNewPos))
            return true; // If the movement isn't diagonal this restriction doesn't apply.
        int originalRow = pieceOriginalPos.getRow();
        int newRow = pieceNewPos.getRow();
        int sub = Math.abs(newRow - originalRow);
        return sub <= maxQty;
    }

    @Override
    public void printErrorMessage() {
        System.out.println("Piece can only move up to " + maxQty + " diagonal squares");
    }

    public boolean isDiagonal(Position pieceOriginalPos, Position pieceNewPos){
        return new DiagonalMovement().validateMovement(pieceOriginalPos, pieceNewPos);
    }
}
