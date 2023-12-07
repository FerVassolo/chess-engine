package checkers.factories;

import commons.board.Board;
import commons.board.Position;
import commons.game.*;
import commons.piece.Piece;
import commons.piece.PieceFactory;

import java.util.Map;

public class BoardTypeCreator {
    public Board NormalBoardDisplay(){
        Board emptyBoard = new Board(8, 8);
        return fillNormalBoard(emptyBoard, new ManFactory());
    }

    private Board fillNormalBoard(Board board, PieceFactory pieceFactory){
        Map<Position, Piece> newMap = board.getPositions();

        newMap.put(board.getPosByAxis(0, 1), pieceFactory.createPiece(1, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 3), pieceFactory.createPiece(2, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 5), pieceFactory.createPiece(3, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 7), pieceFactory.createPiece(4, Color.WHITE));
        newMap.put(board.getPosByAxis(1, 0), pieceFactory.createPiece(5, Color.WHITE));
        newMap.put(board.getPosByAxis(1, 2), pieceFactory.createPiece(6, Color.WHITE));
        newMap.put(board.getPosByAxis(1, 4), pieceFactory.createPiece(7, Color.WHITE));
        newMap.put(board.getPosByAxis(1, 6), pieceFactory.createPiece(8, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 1), pieceFactory.createPiece(9, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 3), pieceFactory.createPiece(10, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 5), pieceFactory.createPiece(11, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 7), pieceFactory.createPiece(12, Color.WHITE));
        newMap.put(board.getPosByAxis(5, 0), pieceFactory.createPiece(13, Color.BLACK));
        newMap.put(board.getPosByAxis(5, 2), pieceFactory.createPiece(14, Color.BLACK));
        newMap.put(board.getPosByAxis(5, 4), pieceFactory.createPiece(15, Color.BLACK));
        newMap.put(board.getPosByAxis(5, 6), pieceFactory.createPiece(16, Color.BLACK));
        newMap.put(board.getPosByAxis(6, 1), pieceFactory.createPiece(17, Color.BLACK));
        newMap.put(board.getPosByAxis(6, 3), pieceFactory.createPiece(18, Color.BLACK));
        newMap.put(board.getPosByAxis(6, 5), pieceFactory.createPiece(19, Color.BLACK));
        newMap.put(board.getPosByAxis(6, 7), pieceFactory.createPiece(20, Color.BLACK));
        newMap.put(board.getPosByAxis(7, 0), pieceFactory.createPiece(21, Color.BLACK));
        newMap.put(board.getPosByAxis(7, 2), pieceFactory.createPiece(22, Color.BLACK));
        newMap.put(board.getPosByAxis(7, 4), pieceFactory.createPiece(23, Color.BLACK));
        newMap.put(board.getPosByAxis(7, 6), pieceFactory.createPiece(24, Color.BLACK));

        return new Board(newMap, 8, 8);

    }
}
