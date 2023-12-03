package commons.rules;

import commons.game.Position;
import commons.game.Board;

public class HorizontalMaxQuantityRule implements RestrictionRule {

    private final int maxQty;

    public HorizontalMaxQuantityRule(int maxQty) {
        this.maxQty = maxQty;
    }



    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        if(!isHorizontal(pieceOriginalPos, pieceNewPos))
            return true;
        int horizontalDistance = Math.abs(pieceNewPos.getCol() - pieceOriginalPos.getCol());
        if(horizontalDistance > maxQty){
            //System.out.println("The selected piece can only move " + maxQty + " squares"); // probably an issue with the double movement
            return false;
        }
        return true;
    }

    @Override
    public String errorMessage() {
        return "The selected piece can only move " + maxQty + " squares horizontally";
    }
    public boolean isHorizontal(Position pieceOriginalPos, Position pieceNewPos){
        return new HorizontalMovement().validateMovement(pieceOriginalPos, pieceNewPos);
    }
}
