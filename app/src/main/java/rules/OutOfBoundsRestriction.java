package rules;

import game.Position;
import game.Board;

public class OutOfBoundsRestriction implements RestrictionRule{
    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        int width = board.getWidth();
        int height = board.getHeight();
        boolean posArePos = posHasPositiveAxis(pieceOriginalPos) && posHasPositiveAxis(pieceNewPos);
        boolean posAreBelowLimit = posIsBelowLimit(pieceNewPos, width, height) && posIsBelowLimit(pieceOriginalPos, width, height);
        if(!posAreBelowLimit || !posArePos)
            System.out.println("Out of Bounds");

        return posAreBelowLimit && posArePos;
    }

    @Override
    public String errorMessage() {
        return "Out of Bounds";
    }

    public boolean posHasPositiveAxis(Position pos){
        return pos.getCol() >= 0 && pos.getRow() >= 0;
    }


    public boolean posIsBelowLimit(Position pos, int width, int height){
        return pos.getRow() < height && pos.getCol() < width;
    }
}
