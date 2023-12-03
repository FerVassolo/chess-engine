package commons.rules;

import commons.game.Board;
import commons.game.Piece;
import commons.game.Position;
import javafx.geometry.Pos;

// the amount of jumps is already taken care by DiagonalMaxQty
// Actually it is checkers normal movement for all pieces.

public class DiagonalCheckersCapture implements RestrictionRule{

    // to capture, no matter the distance, squares should be:
    // piece -> empty -> piece of other color -> empty -> piece of other color -> empty -> ...


    // MODULARIZE
    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        if(!isDiagonal(pieceOriginalPos, pieceNewPos))
            return true; // If the movement isn't diagonal this restriction doesn't apply.
        int rowDirection = pieceNewPos.getRow() > pieceOriginalPos.getRow() ? 1 : -1; // 1: up, 2: down
        int colDirection = pieceNewPos.getCol() > pieceOriginalPos.getCol() ? 1 : -1; // 1: right, 2: left
        int distance = Math.abs(pieceNewPos.getRow() - pieceOriginalPos.getRow());

        //rule doesn't apply cause there is no possibility of capture
        if(distance <= 1){
            return true;
        }

        boolean shouldBeEmpty = false; // first near square should be occupied by a opposite color piece
        for(int i = 0; i < distance; i++){
            Piece piece = board.getPiece(new Position(pieceOriginalPos.getRow() + rowDirection, pieceOriginalPos.getCol() + colDirection));
            if(!canMoveThere(pieceOriginalPos, piece, shouldBeEmpty, board)){
                return false;
            }
            shouldBeEmpty = !shouldBeEmpty;
            rowDirection = incrementDirection(rowDirection);
            colDirection = incrementDirection(colDirection);
        }

        // can move
        return true;
    }

    public int incrementDirection(int direction){
        if (direction > 0 )
            return ++direction;
        else
            return --direction;
    }

    private boolean canMoveThere(Position pieceOriginalPos, Piece piece, boolean shouldBeEmpty, Board board){
        if((piece != null && (shouldBeEmpty || (piece.getColor() == board.getPiece(pieceOriginalPos).getColor())))|| (piece == null && !shouldBeEmpty)){
            return false;
        }
        return true;
    }

    @Override
    public String errorMessage() {
        return "Piece cannot capture";
    }

    // As all movements in checkers are diagonal, but this will make it extensible to other game modes
    public boolean isDiagonal(Position pieceOriginalPos, Position pieceNewPos){
        return new DiagonalMovement().validateMovement(pieceOriginalPos, pieceNewPos);
    }

}
