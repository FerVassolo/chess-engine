package chess.factories;

import commons.board.Board;
import commons.board.Position;
import commons.game.*;
import commons.piece.Piece;
import commons.piece.PieceFactory;

import java.util.Map;

public class BoardTypeCreator {


    public Board NormalBoardDisplay(){
        Board emptyBoard = new Board(8, 8);
        return NormalBoardDisplay(emptyBoard);
    }


    private Board NormalBoardDisplay(Board board){
        PawnFactory pawnFactory = new PawnFactory();
        Board whitePawnsRow = fillEntireLineWithOnePiece(board, pawnFactory, 1, 0, 0, Color.WHITE);
        Board blackPawnsRow = fillEntireLineWithOnePiece(whitePawnsRow, pawnFactory, 6, 0, 8, Color.BLACK);
        Board putKnights =putKinghtsOnNormalBoard(blackPawnsRow, new KnightFactory(), 16);
        Board putRooks = putRooksOnNormalBoard(putKnights, new RookFactory(), 20);
        Board putBishops = putBishopsOnNormalBoard(putRooks, new BishopFactory(), 24);
        Board putKings = putKingsOnNormalBoard(putBishops, new KingFactory(), 28);
        Board putQueens = putQueensOnNormalBoard(putKings, new QueenFactory(), 30);
        return putQueens;
    }

    private Board fillEntireLineWithOnePiece(Board board, PieceFactory pieceFactory, int row, int currentCol, int id, Color color){
        // Check if conditions are valid
        if (currentCol >= board.getWidth() || row >= board.getHeight() || row < 0){
            return board;
        }
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(row, currentCol), pieceFactory.createPiece(id, color));
        Board newBoard = new Board(newMap, 8, 8);
        return fillEntireLineWithOnePiece(newBoard, pieceFactory, row, ++currentCol, ++id, color);
    }

    private Board putPieceAtPos(Board board, PieceFactory pieceFactory, int row, int col, int id, Color color){
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(row, col), pieceFactory.createPiece(id, color));
        return new Board(newMap, 8, 8);
    }
    private Board putKinghtsOnNormalBoard(Board board, PieceFactory pieceFactory, int id){
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 1), pieceFactory.createPiece(id, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 6), pieceFactory.createPiece(++id, Color.WHITE));
        newMap.put(board.getPosByAxis(7, 6), pieceFactory.createPiece(++id, Color.BLACK));
        newMap.put(board.getPosByAxis(7, 1), pieceFactory.createPiece(++id, Color.BLACK));
        return new Board(newMap, 8, 8);
    }
    private Board putRooksOnNormalBoard(Board board, PieceFactory pieceFactory, int id){
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 0), pieceFactory.createPiece(id, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 7), pieceFactory.createPiece(++id, Color.WHITE));
        newMap.put(board.getPosByAxis(7, 0), pieceFactory.createPiece(++id, Color.BLACK));
        newMap.put(board.getPosByAxis(7, 7), pieceFactory.createPiece(++id, Color.BLACK));
        return new Board(newMap, 8, 8);
    }
    private Board putBishopsOnNormalBoard(Board board, PieceFactory pieceFactory, int id){
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 2), pieceFactory.createPiece(id, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 5), pieceFactory.createPiece(++id, Color.WHITE));
        newMap.put(board.getPosByAxis(7, 2), pieceFactory.createPiece(++id, Color.BLACK));
        newMap.put(board.getPosByAxis(7, 5), pieceFactory.createPiece(++id, Color.BLACK));
        return new Board(newMap, 8, 8);
    }
    private Board putKingsOnNormalBoard(Board board, PieceFactory pieceFactory, int id){
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 4), pieceFactory.createPiece(id, Color.WHITE));
        newMap.put(board.getPosByAxis(7, 4), pieceFactory.createPiece(++id, Color.BLACK));
        return new Board(newMap, 8, 8);
    }

    private Board putQueensOnNormalBoard(Board board, PieceFactory pieceFactory, int id){
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 3), pieceFactory.createPiece(id, Color.WHITE));
        newMap.put(board.getPosByAxis(7, 3), pieceFactory.createPiece(++id, Color.BLACK));
        return new Board(newMap, 8, 8);
    }

    public Board sampleStalematePosition(){
        Board board = new Board(8, 8);
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(3, 0), new KingFactory().createPiece(0, Color.BLACK));
        newMap.put(board.getPosByAxis(4, 1), new PawnFactory().createPiece(1, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 2), new QueenFactory().createPiece(2, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 1), new RookFactory().createPiece(3, Color.WHITE));
        newMap.put(board.getPosByAxis(1, 5), new PawnFactory().createPiece(4, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 6), new PawnFactory().createPiece(5, Color.WHITE));
        newMap.put(board.getPosByAxis(1, 7), new PawnFactory().createPiece(6, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 6), new KingFactory().createPiece(7, Color.WHITE));
        return new Board(newMap, 8, 8);
    }

    public Board king30to41onlyValidMove(){
        Board board = new Board(8, 8);
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(3, 0), new KingFactory().createPiece(0, Color.BLACK));
        newMap.put(board.getPosByAxis(4, 1), new PawnFactory().createPiece(1, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 2), new QueenFactory().createPiece(2, Color.WHITE));
        newMap.put(board.getPosByAxis(1, 5), new PawnFactory().createPiece(4, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 6), new PawnFactory().createPiece(5, Color.WHITE));
        newMap.put(board.getPosByAxis(1, 7), new PawnFactory().createPiece(6, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 6), new KingFactory().createPiece(7, Color.WHITE));
        return new Board(newMap, 8, 8);
    }

    public Board checkWithOnePieceKingCanEscape(){
        Board board = new Board(8, 8);
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 0), new KingFactory().createPiece(0, Color.WHITE));
        newMap.put(board.getPosByAxis(1, 1), new PawnFactory().createPiece(1, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 0), new QueenFactory().createPiece(2, Color.BLACK));
        newMap.put(board.getPosByAxis(3, 6), new KingFactory().createPiece(0, Color.BLACK));
        return new Board(newMap, 8, 8);
    }
    public Board checkWithOnePiece(){
        Board board = new Board(8, 8);
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 0), new KingFactory().createPiece(0, Color.WHITE));
        newMap.put(board.getPosByAxis(1, 1), new PawnFactory().createPiece(1, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 1), new RookFactory().createPiece(1, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 0), new QueenFactory().createPiece(2, Color.BLACK));
        newMap.put(board.getPosByAxis(3, 6), new KingFactory().createPiece(0, Color.BLACK));
        return new Board(newMap, 8, 8);
    }
    public Board checkWithOnePieceCanBeBlocked(){
        Board board = new Board(8, 8);
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 0), new KingFactory().createPiece(0, Color.WHITE));
        newMap.put(board.getPosByAxis(1, 1), new RookFactory().createPiece(1, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 1), new RookFactory().createPiece(1, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 0), new QueenFactory().createPiece(2, Color.BLACK));
        newMap.put(board.getPosByAxis(3, 6), new KingFactory().createPiece(0, Color.BLACK));
        return new Board(newMap, 8, 8);
    }
    public Board checkMateBoard(){  Board board = new Board(8, 8);
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 0), new KingFactory().createPiece(0, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 1), new RookFactory().createPiece(1, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 0), new QueenFactory().createPiece(2, Color.BLACK));
        newMap.put(board.getPosByAxis(3, 6), new KingFactory().createPiece(0, Color.BLACK));
        return new Board(newMap, 8, 8);
    }
    public Board checkMateWithMultiplePiecesBoard(){
        Board board = new Board(8, 8);
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 0), new KingFactory().createPiece(0, Color.WHITE));
        newMap.put(board.getPosByAxis(1, 1), new RookFactory().createPiece(1, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 1), new RookFactory().createPiece(1, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 0), new QueenFactory().createPiece(2, Color.BLACK));
        newMap.put(board.getPosByAxis(2, 2), new BishopFactory().createPiece(2, Color.BLACK));
        newMap.put(board.getPosByAxis(3, 6), new KingFactory().createPiece(0, Color.BLACK));
        return new Board(newMap, 8, 8);
    }
    public Board checkWithMultiplePiecesBoard(){
        Board board = new Board(8, 8);
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 0), new KingFactory().createPiece(0, Color.WHITE));
        newMap.put(board.getPosByAxis(2, 0), new QueenFactory().createPiece(2, Color.BLACK));
        newMap.put(board.getPosByAxis(2, 2), new BishopFactory().createPiece(2, Color.BLACK));
        newMap.put(board.getPosByAxis(3, 6), new KingFactory().createPiece(0, Color.BLACK));
        return new Board(newMap, 8, 8);
    }
    public Board checkWithMultiplePiecesCannotMoveKingBoard(){
        Board board = new Board(8, 8);
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(4, 3), new KingFactory().createPiece(0, Color.WHITE));
        newMap.put(board.getPosByAxis(5, 2), new RookFactory().createPiece(1, Color.WHITE));
        newMap.put(board.getPosByAxis(3, 3), new PawnFactory().createPiece(2, Color.WHITE));
        newMap.put(board.getPosByAxis(3, 4), new PawnFactory().createPiece(3, Color.WHITE));
        newMap.put(board.getPosByAxis(7, 7), new QueenFactory().createPiece(4, Color.BLACK));
        newMap.put(board.getPosByAxis(7, 6), new BishopFactory().createPiece(5, Color.BLACK));
        newMap.put(board.getPosByAxis(7, 5), new BishopFactory().createPiece(6, Color.BLACK));
        newMap.put(board.getPosByAxis(0, 0), new KingFactory().createPiece(7, Color.BLACK));
        return new Board(newMap, 8, 8);
    }

    public Board aKingTwoArchbishops(){
        Board board = new Board(5, 5);
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 2), new KingFactory().createPiece(0, Color.WHITE));
        newMap.put(board.getPosByAxis(4, 2), new KingFactory().createPiece(1, Color.BLACK));
        newMap.put(board.getPosByAxis(0, 1), new ArchbishopFactory().createPiece(2, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 3), new ArchbishopFactory().createPiece(3, Color.WHITE));
        newMap.put(board.getPosByAxis(4, 1), new ArchbishopFactory().createPiece(4, Color.BLACK));
        newMap.put(board.getPosByAxis(4, 3), new ArchbishopFactory().createPiece(5, Color.BLACK));
        newMap.put(board.getPosByAxis(4, 4), new PawnFactory().createPiece(6, Color.BLACK));
        newMap.put(board.getPosByAxis(0, 4), new PawnFactory().createPiece(7, Color.WHITE));
        return new Board(newMap, 5, 5);
    }
    public Board normalBoardWithArchbishops(){
        Board board = new Board(10, 10);
        PawnFactory pawnFactory = new PawnFactory();
        Board whitePawnsRow = fillForBishops(board, pawnFactory, 1, 0, 0, Color.WHITE);
        Board blackPawnsRow = fillForBishops(whitePawnsRow, pawnFactory, 8, 0, 10, Color.BLACK);
        Board putKnights =putKinghtsOnSpecialBoard(blackPawnsRow, new KnightFactory(), 20);
        Board putRooks = putRooksOnSpecialBoard(putKnights, new RookFactory(), 24);
        Board putBishops = putBishopsOnSpecialBoard(putRooks, new BishopFactory(), 28);
        Board putKings = putKingsOnSpecial(putBishops, new KingFactory(), 32);
        Board putQueens = putQueenOnSpecial(putKings, new QueenFactory(), 34);
        Board putArbishops = putArbishops(putQueens, new ArchbishopFactory(), 36);
        return putArbishops;
    }

    private Board fillForBishops(Board board, PieceFactory pieceFactory, int row, int currentCol, int id, Color color){
        // Check if conditions are valid
        if (currentCol >= board.getWidth() || row >= board.getHeight() || row < 0){
            return board;
        }
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(row, currentCol), pieceFactory.createPiece(id, color));
        Board newBoard = new Board(newMap, 10, 10);
        return fillForBishops(newBoard, pieceFactory, row, ++currentCol, ++id, color);
    }



    private Board putKinghtsOnSpecialBoard(Board board, PieceFactory pieceFactory, int id){
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 1), pieceFactory.createPiece(id, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 8), pieceFactory.createPiece(++id, Color.WHITE));
        newMap.put(board.getPosByAxis(9, 8), pieceFactory.createPiece(++id, Color.BLACK));
        newMap.put(board.getPosByAxis(9, 1), pieceFactory.createPiece(++id, Color.BLACK));
        return new Board(newMap, 10, 10);
    }
    private Board putRooksOnSpecialBoard(Board board, PieceFactory pieceFactory, int id){
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 0), pieceFactory.createPiece(id, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 9), pieceFactory.createPiece(++id, Color.WHITE));
        newMap.put(board.getPosByAxis(9, 0), pieceFactory.createPiece(++id, Color.BLACK));
        newMap.put(board.getPosByAxis(9, 9), pieceFactory.createPiece(++id, Color.BLACK));
        return new Board(newMap, 10, 10);
    }
    private Board putBishopsOnSpecialBoard(Board board, PieceFactory pieceFactory, int id){
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 2), pieceFactory.createPiece(id, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 7), pieceFactory.createPiece(++id, Color.WHITE));
        newMap.put(board.getPosByAxis(9, 2), pieceFactory.createPiece(++id, Color.BLACK));
        newMap.put(board.getPosByAxis(9, 7), pieceFactory.createPiece(++id, Color.BLACK));
        return new Board(newMap, 10, 10);
    }
    private Board putKingsOnSpecial(Board board, PieceFactory pieceFactory, int id){
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 5), pieceFactory.createPiece(id, Color.WHITE));
        newMap.put(board.getPosByAxis(9, 5), pieceFactory.createPiece(++id, Color.BLACK));
        return new Board(newMap, 10, 10);
    }

    private Board  putQueenOnSpecial(Board board, PieceFactory pieceFactory, int id){
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 4), pieceFactory.createPiece(id, Color.WHITE));
        newMap.put(board.getPosByAxis(9, 4), pieceFactory.createPiece(++id, Color.BLACK));
        return new Board(newMap, 10, 10);
    }

    private Board putArbishops( Board board, PieceFactory pieceFactory, int id){
        Map<Position, Piece> newMap = board.getPositions();
        newMap.put(board.getPosByAxis(0, 3), pieceFactory.createPiece(id, Color.WHITE));
        newMap.put(board.getPosByAxis(0, 6), pieceFactory.createPiece(++id, Color.WHITE));
        newMap.put(board.getPosByAxis(9, 3), pieceFactory.createPiece(++id, Color.BLACK));
        newMap.put(board.getPosByAxis(9, 6), pieceFactory.createPiece(++id, Color.BLACK));
        return new Board(newMap, 10, 10);
    }
}
