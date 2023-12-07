package commons.rules.boardDependantRules;

import chess.factories.QueenFactory;
import commons.board.Board;
import commons.piece.Piece;
import commons.piece.PieceName;
import commons.board.Position;

import java.util.HashMap;
import java.util.Map;

public class PawnPromotion implements BoardDependantSpecialRule {

    QueenFactory queenFactory = new QueenFactory();

    @Override
    public boolean ruleIsActive(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        Piece movedPiece = board.getPiece(pieceOriginalPos);
        if(movedPiece.getName() != PieceName.PAWN){
            return false;
        }
        return pieceNewPos.getRow() == (board.getHeight()-1) || pieceNewPos.getRow() == 0;
    }


    @Override
    public Board modifiedBoard(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        Map<Position, Piece> newPosDisplay = new HashMap<>(board.getPositions()); // Create a copy
        Piece movedPiece = newPosDisplay.get(board.getPosByPos(pieceOriginalPos));
        Piece queen = queenFactory.createPiece(movedPiece.getId(), movedPiece.getColor());
        newPosDisplay.put(board.getPosByPos(pieceOriginalPos), null);
        newPosDisplay.put(board.getPosByPos(pieceNewPos), queen);
        return new Board(newPosDisplay, board.getHeight(), board.getWidth());
    }
}
