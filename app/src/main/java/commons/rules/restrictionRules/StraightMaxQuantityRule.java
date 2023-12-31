package commons.rules.restrictionRules;

import commons.board.Position;
import commons.board.Board;
import commons.rules.movementRules.HorizontalMovement;
import commons.rules.movementRules.VerticalMovement;

// DEPRECATED
public class StraightMaxQuantityRule implements RestrictionRule{

    public int maxQty;

    public StraightMaxQuantityRule(int maxQty){
        this.maxQty = maxQty;
    }

    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        if(!isStraight(pieceOriginalPos, pieceNewPos))
            return true;
        int verticalDistance = Math.abs(pieceNewPos.getRow() - pieceOriginalPos.getRow());
        int horizontalDistance = Math.abs(pieceNewPos.getCol() - pieceOriginalPos.getCol());
        if(verticalDistance > maxQty || horizontalDistance > maxQty){
            //System.out.println("The selected piece can only move " + maxQty + " squares"); // probably an issue with the double movement
            return false;
        }
        return true;
    }

    @Override
    public String errorMessage() {
        return "The selected piece can only move " + maxQty + " squares";
    }

    public boolean isStraight(Position pieceOriginalPos, Position pieceNewPos){
        if(new VerticalMovement().validateMovement(pieceOriginalPos, pieceNewPos) || new HorizontalMovement().validateMovement(pieceOriginalPos, pieceNewPos))
            return true;
        return false;
    }



}
