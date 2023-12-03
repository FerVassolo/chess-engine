package commons.rules;

import commons.game.Position;
import commons.game.Board;
import commons.game.Piece;

import java.util.HashMap;
import java.util.Map;

public class PieceInterposesHorizontallyRestriction implements RestrictionRule{

    // doesn't verify the newPiecePos
    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        if (!isHorizontal(pieceOriginalPos, pieceNewPos)) {
            return true;
        }
        int originalCol = pieceOriginalPos.getCol();
        int newCol = pieceNewPos.getCol();
        Map<Position, Piece> positionsInRow = getAllPiecesInRow(pieceNewPos, board);
        for(Position pos : positionsInRow.keySet()){
            int posCol = pos.getCol();
            if(numIsAtInterval(originalCol, newCol, posCol) && pieceExists(pos, positionsInRow)){
                System.out.println("There are pieces horizontally interposing the movement");
                return false;
            }
        }
        return true;
    }

    @Override
    public String errorMessage() {
        return "There are pieces horizontally interposing the movement";
    }

    public Map<Position, Piece> getAllPiecesInRow(Position pieceNewPos, Board board){
        int row = pieceNewPos.getRow();
        Map<Position, Piece> pieces = new HashMap<>();
        for(Position pos : board.getPositionsMapKeys()){
            if(pos.getRow() == row){
                pieces.put(pos, board.getPiece(pos));
            }
        }
        return pieces;
    }

    public boolean numIsAtInterval(int num1, int num2, int numberToCheck){
        int max = Math.max(num1, num2);
        int min = Math.min(num1, num2);
        return numberToCheck > min && numberToCheck < max;
    }

    public boolean pieceExists(Position pos,  Map<Position, Piece> positionsInRow){
        return positionsInRow.get(pos) != null;
    }
    public boolean isHorizontal(Position pieceOriginalPos, Position pieceNewPos) {
        return new HorizontalMovement().validateMovement(pieceOriginalPos, pieceNewPos);
    }
}
