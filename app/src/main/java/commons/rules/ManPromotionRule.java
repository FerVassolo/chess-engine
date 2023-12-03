package commons.rules;

import checkers.factories.CheckersKingFactory;
import chess.game.KingFactory;
import chess.game.QueenFactory;
import commons.game.Board;
import commons.game.Piece;
import commons.game.PieceName;
import commons.game.Position;

import java.util.HashMap;
import java.util.Map;

public class ManPromotionRule implements BoardDependantSpecialRule {

    CheckersKingFactory kingFactory = new CheckersKingFactory();

    // This is repeated code in many aspects... todo: make a PromotionUtils class
    @Override
    public boolean ruleIsActive(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        Piece movedPiece = board.getPiece(pieceOriginalPos);
        if(movedPiece.getName() != PieceName.MAN){
            return false;
        }
        return pieceNewPos.getRow() == (board.getHeight()-1) || pieceNewPos.getRow() == 0;
    }


    @Override
    public Board modifiedBoard(Position pieceOriginalPos, Position pieceNewPos, Board board) {
        Map<Position, Piece> newPosDisplay = new HashMap<>(board.getPositions()); // Create a copy
        Piece movedPiece = newPosDisplay.get(board.getPosByPos(pieceOriginalPos));
        Piece king = kingFactory.createPiece(movedPiece.getId(), movedPiece.getColor());
        newPosDisplay.put(board.getPosByPos(pieceOriginalPos), null);
        newPosDisplay.put(board.getPosByPos(pieceNewPos), king);
        return new Board(newPosDisplay, board.getHeight(), board.getWidth());
    }
}
