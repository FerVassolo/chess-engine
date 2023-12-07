package edu.austral.dissis.chess.gui


import commons.board.Board
import commons.game.*
import commons.movement.Movement
import commons.movement.MovementValidator
import edu.austral.dissis.chess.gui.PlayerColor.BLACK
import edu.austral.dissis.chess.gui.PlayerColor.WHITE
import javafx.application.Application.launch


fun main() {
    launch(ChessGameApplication::class.java)
}


class GameEngineAdapter(var game : Game) : GameEngine {

    // Class variables
    private var currentPlayer = WHITE

    private var pieces = BoardAdapter().engineBoardToUIBoard(game.board);

    override fun init(): InitialState {
        val board = game.board
        val size = BoardSize(board.width, board.height)
        return InitialState(size, pieces, WHITE)
    }

    override fun applyMove(move: Move): MoveResult {
        if (gameHasEnded(game, game.lastBoard)) {
            return handleGameEnd()
        }

        val fromPiece = findPieceAtPosition(move.from)
        val toPiece = findPieceAtPosition(move.to)

        val fromPieceJava = BoardAdapter().kotlinPositionIntoJavaPosition(move.from)
        val toPieceJava = BoardAdapter().kotlinPositionIntoJavaPosition(move.to)
        val currentBoard = game.lastBoard

        val newBoard = makeMoveOnBoard(fromPieceJava, toPieceJava)

        // If invalid for whatever reason: return why.
        // else: make the move
        return when {
            fromPiece == null -> InvalidMove("No piece in (${move.from.row}, ${move.from.column})")
            fromPiece.color != currentPlayer -> InvalidMove("Piece does not belong to the current player")
            toPiece != null && toPiece.color == currentPlayer -> InvalidMove("There is a piece in (${move.to.row}, ${move.to.column})")
            currentBoard == newBoard -> InvalidMove(validationMessage(game, game.lastBoard, fromPieceJava, toPieceJava))
            else -> {
                // could return either the newState or a GameOverMessage.
                makeMove(fromPiece, toPiece, move, newBoard)
            }
        }
    }

    private fun handleGameEnd(): MoveResult {
        return GameOver(pieces[0].color)
    }

    private fun findPieceAtPosition(position: Position): ChessPiece? {
        return pieces.find { it.position == position }
    }

    private fun makeMoveOnBoard(from: commons.board.Position, to: commons.board.Position): Board {
        return Movement().makeMove(game, game.lastBoard, from, to)
    }

    private fun makeMove(fromPiece: ChessPiece, toPiece: ChessPiece?, move: Move, newBoard: Board): MoveResult {
        game.passTurn()
        game.setHistoryOfBoards(newBoard)

        pieces = updatePieces(fromPiece, toPiece, move)

        currentPlayer = if (currentPlayer == WHITE) BLACK else WHITE
        if (pieces.size == 1) {
            return handleGameEnd()
        }
        updatePiecesForSpecialCases()
        pieces = BoardAdapter().engineBoardToUIBoard(game.historyOfBoards[game.historyOfBoards.size -1]);
        return NewGameState(pieces, currentPlayer)
    }

    private fun updatePieces(fromPiece: ChessPiece, toPiece: ChessPiece?, move: Move): List<ChessPiece> {
        return pieces.filter { it != fromPiece && it != toPiece }
            .plus(fromPiece.copy(position = move.to))
    }

    private fun updatePiecesForSpecialCases(): Unit {
        pieces = pieces.map {
            if (((it.color == WHITE && it.position.row == 8) || (it.color == BLACK && it.position.row == 1)) && it.pieceId == "pawn") {
                it.copy(pieceId = "queen")
            } else {
                it
            }
        }
    }
    fun gameHasEnded(game: Game, board: Board?): Boolean {
        for (rule in game.endGameRules) {
            if (!rule.checkEndRule(game, board)) {
                return true
            }
        }
        return false
    }

    fun validationMessage(game: Game, board: Board?, from : commons.board.Position, to : commons.board.Position) : String{
        val validator = MovementValidator();
        validator.validateMovement(game, board, from, to);
        return validator.error;
    }


}




//todo: la reina se transofrma como en e juego q nos dieron. Sacar eso.

