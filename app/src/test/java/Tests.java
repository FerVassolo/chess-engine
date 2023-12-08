import chess.factories.BishopFactory;
import chess.factories.BoardTypeCreator;
import commons.board.Board;
import commons.board.Position;
import commons.game.*;
import commons.movement.Movement;
import commons.movement.MovementValidator;
import commons.piece.Piece;
import chess.rules.endgameRules.CheckMate;
import commons.rules.endgameRules.EndGameRule;
import commons.rules.restrictionRules.CannotCaptureSameColorRestriction;
import chess.rules.restrictionRules.CannotMoveIfInCheck;
import commons.rules.restrictionRules.OutOfBoundsRestriction;
import commons.rules.restrictionRules.RestrictionRule;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class Tests {

    private Player fer;
    private Player leo;
    private RestrictionRule[] gameRules = new RestrictionRule[]{new CannotCaptureSameColorRestriction(), new OutOfBoundsRestriction(), new CannotMoveIfInCheck()};
    EndGameRule[] endGameRules = new EndGameRule[]{new CheckMate()};
    private Player[] players;
    private Board board;
    private Game game;

    @Before
    public void setUp() {
        fer =  new Player("fer", 1, new Time(0), Color.WHITE);
        leo = new Player("leo", 2, new Time(0), Color.BLACK);
        players = new Player[]{fer, leo};
        board = new BoardTypeCreator().NormalBoardDisplay();
        game = new Game(gameRules, endGameRules, players, board, new Time(0));
    }

    @Test
    public void testWrongMove(){
        Board whiteMoved = new Movement().makeMove(game, game.getBoard(), new Position(3, 1), new Position(2, 1));
        assertEquals(game.getBoard(), whiteMoved);
    }

    /**
     * If the movement is invalid the board should remain the same.
     */
    @Test
    public void testBlackWrongMove(){
        Board whiteMoved = new Movement().makeMove(game, game.getBoard(), new Position(1, 1), new Position(2, 1));
        game.passTurn();
        Board blackMoved = new Movement().makeMove(game, whiteMoved, new Position(4, 1), new Position(3, 1));  // This is not a valid movement, so the board should remain the same
        assertEquals(whiteMoved, blackMoved);
    }

    @Test
    public void testIfInvalidMovementIsValidatedWithBlack(){
        boolean isValid = new MovementValidator().validateMovement(game, game.getBoard(), new Position(3, 1), new Position(2, 1));
        assertFalse(isValid);
    }

    @Test
    public void testIfValidMovementIsValidatedWithBlack(){
        Board whiteMoved = new Movement().makeMove(game, game.getBoard(), new Position(1, 1), new Position(2, 1));
        Game newGame = new Game(gameRules, endGameRules, players, whiteMoved, new Time(0));
        newGame.passTurn();
        boolean isValid = new MovementValidator().validateMovement(newGame, whiteMoved, new Position(6, 1), new Position(5, 1));
        assertTrue(isValid);
    }

    @Test
    public void testOutOfBoundsShouldNotFail(){
        Board moved = new Movement().makeMove(game, game.getBoard(), new Position(1, 1), new Position(2, 1));
        System.out.println(moved.getPiece( new Position(2, 1)));
        assertFalse(board.equals(moved));
    }
    @Test
    public void testOutOfBoundsRuleIfAPieceWantsToGoBelow0(){
        Game newGame = newGameWithBishopInAtAxis(2, 0);
        Board moved = new Movement().makeMove(newGame, newGame.getBoard(), new Position(2, 0), new Position(1, -1));
        assertEquals(newGame.getBoard(), moved);
    }
    @Test
    public void testOutOfBoundsRuleIfAPieceWantsToGoAboveBoardDimensions(){
        Game newGame = newGameWithBishopInAtAxis(2, 7);
        Board moved = new Movement().makeMove(newGame, newGame.getBoard(), new Position(2, 7), new Position(3, 8));
        assertEquals(newGame.getBoard(), moved);
    }

    public Game newGameWithBishopInAtAxis(int row, int col){
        Map<Position, Piece> newMap = board.getPositions();
        BishopFactory pieceFactory = new BishopFactory();
        newMap.put(board.getPosByAxis(row, col), pieceFactory.createPiece(33, Color.WHITE));
        Board newBoard = new Board(newMap, 8, 8);
        return new Game(gameRules, endGameRules, players, newBoard, new Time(0));
    }

    @Test
    public void testCannotMoveWithCheck(){
        Board testBoard = new BoardTypeCreator().sampleStalematePosition();
        game.passTurn();
        Board moved = new Movement().makeMove(game, testBoard, new Position(3, 0), new Position(2, 0));
        assertEquals(moved, testBoard);
    }


    @Test
    public void testStalemate(){

    }

    @Test
    public void testOnPassant(){

    }

    @Test
    public void testPossibleCheckMateWhereNotInCheck(){
        Board testBoard = new BoardTypeCreator().king30to41onlyValidMove();
        CheckMate checkMate = new CheckMate();
        assertTrue(checkMate.checkEndRule(game, testBoard));
    }

    @Test
    public void testPossibleCheckMateWhereKingCanEscape(){
        Board testBoard = new BoardTypeCreator().checkWithOnePieceKingCanEscape();
        CheckMate checkMate = new CheckMate();
        assertTrue(checkMate.checkEndRule(game, testBoard));
    }

    @Test
    public void testKingCannotEscape(){
        Board testBoard = new BoardTypeCreator().checkWithOnePiece();
        CheckMate checkMate = new CheckMate();
        assertFalse(checkMate.kingCanEscape(testBoard, Color.WHITE, game));
    }

    @Test
    public void testPieceCanCapurePiece(){
        Board testBoard = new BoardTypeCreator().checkWithOnePiece();
        CheckMate checkMate = new CheckMate();
        assertTrue(checkMate.pieceCanCapturePiece(testBoard.getPosByAxis(1, 1), testBoard.getPosByAxis(2, 0),testBoard, game));
    }

    @Test
    public void testPieceCannotBlockPiece(){
        Board testBoard = new BoardTypeCreator().checkWithOnePiece();
        CheckMate checkMate = new CheckMate();
        System.out.println(game);
        ArrayList<Position> possibleBlockingPositions = new Movement().getPieceAllPossibleMoves(testBoard.getPosByAxis(2, 0), testBoard, game);
        assertFalse(checkMate.pieceCanBlockPiece(testBoard.getPosByAxis(1, 1), testBoard, game, possibleBlockingPositions));
    }
    @Test
    public void testPieceCanBlockPiece(){
        Board testBoard = new BoardTypeCreator().checkWithOnePieceCanBeBlocked();
        CheckMate checkMate = new CheckMate();
        System.out.println(game);
        ArrayList<Position> possibleBlockingPositions = new Movement().getPieceAllPossibleMoves(testBoard.getPosByAxis(2, 0), testBoard, game);
        assertTrue(checkMate.pieceCanBlockPiece(testBoard.getPosByAxis(1, 1), testBoard, game, possibleBlockingPositions));
    }

    @Test
    public void testPossibleCheckMateCanBeAvoidedByCapturingAttackingPiece(){
        Board testBoard = new BoardTypeCreator().checkWithOnePiece();
        CheckMate checkMate = new CheckMate();
        System.out.println(game.getCurrentPlayer().getColor());
        assertTrue(checkMate.checkEndRule(game, testBoard));
    }

    @Test
    public void testPossibleCheckMateCanBeAvoidedByBlockingAttackingPiece(){
        Board testBoard = new BoardTypeCreator().checkWithOnePieceCanBeBlocked();
        CheckMate checkMate = new CheckMate();
        assertTrue(checkMate.checkEndRule(game, testBoard));
    }


    @Test
    public void testCheckMate(){
        Board testBoard = new BoardTypeCreator().checkMateBoard();
        CheckMate checkMate = new CheckMate();
        assertFalse(checkMate.checkEndRule(game, testBoard));
    }
    @Test
    public void testCheckMate2(){
        Board testBoard = new BoardTypeCreator().checkMateWithMultiplePiecesBoard();
        CheckMate checkMate = new CheckMate();
        assertFalse(checkMate.checkEndRule(game, testBoard));
    }
}
