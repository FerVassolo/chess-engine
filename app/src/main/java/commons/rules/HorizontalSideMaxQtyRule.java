package commons.rules;

import commons.game.Board;
import commons.game.Position;

public class HorizontalSideMaxQtyRule implements RestrictionRule{
    private final int leftMax;
    private final int rightMax;

    public HorizontalSideMaxQtyRule(int leftMax, int rightMax) {
        this.leftMax = leftMax;
        this.rightMax = rightMax;
    }

    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        if(!isHorizontal(pieceOriginalPos, pieceNewPos))
            return true;

        int horizontalDistance = Math.abs(pieceNewPos.getCol() - pieceOriginalPos.getCol());
        int side = sideItsMovingTo(pieceOriginalPos, pieceNewPos);
        if(side == 1 || horizontalDistance > rightMax)
            return false;
        else if(side == -1 || horizontalDistance > leftMax)
            return false;

        return true;


    }
    public int sideItsMovingTo(Position pieceOriginalPos, Position pieceNewPos){
        int side; //1 -> right, -1 -> left
        if(pieceNewPos.getCol() - pieceOriginalPos.getCol() > 0)
            side = 1;
        else
            side = -1;
        return side;
    }

    @Override
    public String errorMessage() {
        int minimumDistance = Math.min(leftMax, rightMax);
        return "The selected piece can only move " + minimumDistance + " squares horizontally";
    }
    public boolean isHorizontal(Position pieceOriginalPos, Position pieceNewPos){
        return new HorizontalMovement().validateMovement(pieceOriginalPos, pieceNewPos);
    }
}
