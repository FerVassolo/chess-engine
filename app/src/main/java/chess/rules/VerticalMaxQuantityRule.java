package chess.rules;

import commons.Position;
import commons.Board;


public class VerticalMaxQuantityRule implements RestrictionRule {

    private final int maxQty;

    public VerticalMaxQuantityRule(int maxQty) {
        this.maxQty = maxQty;
    }

    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        if(!isVertical(pieceOriginalPos, pieceNewPos))
            return true;
        int verticalDistance = Math.abs(pieceNewPos.getRow() - pieceOriginalPos.getRow());
        if(verticalDistance > maxQty){
            return false;
        }
        return true;
    }

    @Override
    public String errorMessage() {
        return "The selected piece can only move " + maxQty + " squares vertically";
    }
    public boolean isVertical(Position pieceOriginalPos, Position pieceNewPos){
        return new VerticalMovement().validateMovement(pieceOriginalPos, pieceNewPos);
    }
}
