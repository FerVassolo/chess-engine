package rules;

import game.Position;
import game.Board;

public class PieceInterposesVerticallyRestriction implements RestrictionRule {

    // doesn't verify the newPiecePos
    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        if (!isVertical(pieceOriginalPos, pieceNewPos)) {
            return true;
        }
        int originalRow = pieceOriginalPos.getRow();
        int newRow = pieceNewPos.getRow();
        int col = pieceNewPos.getCol();
        if(originalRow < newRow) {
            for (int i = originalRow+1; i < newRow; i++) {
                Position pos = board.getPosByAxis(i, col);
                if(board.getPiece(pos) != null){
                    System.out.println("There are pieces vertically interposing the movement");
                    return false;
                }
            }
        }
        else if(originalRow > newRow) {
            for (int i = newRow+1; i < originalRow; i++) {
                Position pos = board.getPosByAxis(i, col);
                if(board.getPiece(pos) != null){
                    System.out.println("There are pieces vertically interposing the movement");
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void printErrorMessage() {
        System.out.println("There are pieces vertically interposing the movement");
    }

    public boolean isVertical(Position pieceOriginalPos, Position pieceNewPos) {
        return new VerticalMovement().validateMovement(pieceOriginalPos, pieceNewPos);
    }
}