package commons.rules;

import commons.game.Board;
import commons.game.Position;

public class CannotLandOnPiece implements RestrictionRule{
    @Override
    public boolean validateRule(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        // if it is null then it is OK to move
        return board.getPiece(pieceNewPos) == null;
    }

    @Override
    public String errorMessage() {
        return "Cannot land on a square where there is a piece";
    }
}
