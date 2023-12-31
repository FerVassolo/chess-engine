package commons.rules.restrictionRules;

import commons.board.Position;
import commons.board.Board;
import commons.piece.Piece;
import commons.rules.movementRules.DiagonalMovement;

public class PieceInterposesDiagonallyRestriction implements RestrictionRule {

    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        if (!isDiagonal(pieceOriginalPos, pieceNewPos)) {
            return true;
        }
        int rowDirection = pieceNewPos.getRow() > pieceOriginalPos.getRow() ? 1 : -1; // 1: up, 2: down
        int colDirection = pieceNewPos.getCol() > pieceOriginalPos.getCol() ? 1 : -1; // 1: right, 2: left
        int distance = Math.abs(pieceNewPos.getRow() - pieceOriginalPos.getRow()); // row distance = colDistance
        // Whether it can land or not on a same color piece is a game rule, not a piece restriction.
        // That's is why I do distance -1, I don't care about where it lands but only the path it takes
        for(int i = 0; i < distance-1; i++){
            Piece piece = board.getPiece(new Position(pieceOriginalPos.getRow() + rowDirection, pieceOriginalPos.getCol() + colDirection));
            if(piece != null){
                System.out.println("There is an interposing piece");
                return false;
            }
            rowDirection = incrementDirection(rowDirection);
            colDirection = incrementDirection(colDirection);
        }

        // No pieces found in the diagonal path
        return true;
    }

    public int incrementDirection(int direction){
        if (direction > 0 )
            return ++direction;
        else
            return --direction;
    }
    @Override
    public String errorMessage() {
        return "There is an interposing piece";
    }

    public boolean isDiagonal(Position pieceOriginalPos, Position pieceNewPos) {
        return new DiagonalMovement().validateMovement(pieceOriginalPos, pieceNewPos);
    }
}
