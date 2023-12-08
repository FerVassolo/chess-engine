package edu.austral.dissis.chess.gui


import commons.board.Board
import commons.game.*
import commons.movement.Movement
import commons.movement.MovementValidator
import commons.result.EndgameResult
import commons.result.InvalidMoveResult
import commons.result.ValidMoveResult
import edu.austral.dissis.chess.gui.PlayerColor.BLACK
import edu.austral.dissis.chess.gui.PlayerColor.WHITE
import javafx.application.Application.launch


fun main() {
    launch(ChessGameApplication::class.java)
}


class GameAdapter(var game : Game) : GameEngine {

    // Class variables

    private var pieces = BoardAdapter().engineBoardToUIBoard(game.board);

    override fun init(): InitialState {
        val board = game.board
        val size = BoardSize(board.width, board.height)
        return InitialState(size, pieces, WHITE)
    }

    override fun applyMove(move: Move): MoveResult {
        val fromPiece = findPieceAtPosition(move.from)
        val toPiece = findPieceAtPosition(move.to)
        val fromPieceJava = BoardAdapter().kotlinPositionIntoJavaPosition(move.from)
        val toPieceJava = BoardAdapter().kotlinPositionIntoJavaPosition(move.to)
        val currentBoard = game.lastBoard
        val result = game.move(currentBoard, fromPieceJava, toPieceJava);
        if(fromPiece == null)
            return InvalidMove("No piece in (${move.from.row}, ${move.from.column})")

        when (result) {
            is EndgameResult ->{ return handleGameEnd(BoardAdapter().convertColor(result.winner.color)) }
            is InvalidMoveResult ->{ return InvalidMove(result.errorMessage) }
            is ValidMoveResult -> { return makeMove(fromPiece, toPiece, move) }
        }
        return InvalidMove("Something failed");
    }

    private fun handleGameEnd(winner : PlayerColor): MoveResult {
        return GameOver(winner)
    }

    private fun findPieceAtPosition(position: Position): ChessPiece? {
        return pieces.find { it.position == position }
    }

    private fun makeMove(fromPiece: ChessPiece, toPiece: ChessPiece?, move: Move): MoveResult {

        pieces = updatePieces(fromPiece, toPiece, move)

        pieces = BoardAdapter().engineBoardToUIBoard(game.historyOfBoards[game.historyOfBoards.size -1]);
        var adapter = BoardAdapter();
        var color = adapter.convertColor(game.currentPlayer.color)
        return NewGameState(pieces, color)
    }

    private fun updatePieces(fromPiece: ChessPiece, toPiece: ChessPiece?, move: Move): List<ChessPiece> {
        return pieces.filter { it != fromPiece && it != toPiece }
            .plus(fromPiece.copy(position = move.to))
    }



}




//todo: la reina se transofrma como en e juego q nos dieron. Sacar eso.

