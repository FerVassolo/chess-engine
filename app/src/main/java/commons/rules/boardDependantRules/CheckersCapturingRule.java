package commons.rules.boardDependantRules;

import commons.board.Board;
import commons.piece.Piece;
import commons.board.Position;
import commons.rules.movementRules.DiagonalMovement;

import java.util.HashMap;
import java.util.Map;

public class CheckersCapturingRule implements BoardDependantSpecialRule {

    @Override
    public boolean ruleIsActive(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        // if moved distance is bigger than 1, then all the squares it went through are now null.
        return getDistance(pieceOriginalPos, pieceNewPos) > 1;
    }

    @Override
    public Board modifiedBoard(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        Position[] capturedPositions = new DiagonalMovement().getPath(pieceOriginalPos, pieceNewPos);
        return erasePiecesInPath(pieceOriginalPos, pieceNewPos, board, capturedPositions);
    }

    public Board erasePiecesInPath(Position pieceOriginalPos, Position pieceNewPos, Board board, Position[] capturedPositions){
        Map<Position, Piece> newPosDisplay = new HashMap<>(board.getPositions());
        Piece movedPiece = newPosDisplay.get(board.getPosByPos(pieceOriginalPos));
        for(Position pos: capturedPositions){
            newPosDisplay.put(board.getPosByPos(pos), null);
        }
        newPosDisplay.put(board.getPosByPos(pieceOriginalPos), null);
        newPosDisplay.put(board.getPosByPos(pieceNewPos), movedPiece);
        return new Board(newPosDisplay, board.getHeight(), board.getWidth());
    }

    public int getDistance(Position pieceOriginalPos, Position pieceNewPos){
        int from = pieceOriginalPos.getRow();
        int to = pieceNewPos.getRow();
        return Math.abs(from - to);
    }


}
