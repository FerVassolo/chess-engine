package edu.austral.dissis.chess.gui

import BoardAdapter
import chess.game.BoardTypeCreator
import commons.game.*
import commons.rules.*
import edu.austral.dissis.chess.gui.PlayerColor.BLACK
import edu.austral.dissis.chess.gui.PlayerColor.WHITE
import javafx.application.Application.launch
import java.sql.Time


fun main() {
    launch(ChessGameApplication::class.java)
}


class SimpleGameEngine8x8 : GameEngine {

    var game = createNormalGame()
    // Class variables
    private var currentPlayer = WHITE

    private var pieces = BoardAdapter().engineBoardToUIBoard(game.board);

    override fun init(): InitialState {
        return InitialState(BoardSize(8, 8), pieces, WHITE)
    }

    private fun createNormalGame(): Game {
        val fer = Player("fer", 1, Time(0), Color.WHITE)
        val leo = Player("leo", 2, Time(0), Color.BLACK)
        val gameRules = arrayOf(
            CannotCaptureSameColorRestriction(),
            OutOfBoundsRestriction(),
            CannotMoveIfInCheck()
        )
        val endGameRules = arrayOf<EndGameRule>(CheckMate())
        val players = arrayOf(fer, leo)
        // Using the adapter you should not need to have the board created in the back
        val board: Board = BoardTypeCreator().NormalBoardDisplay()

        return Game(gameRules, endGameRules, players, board, Time(0))
    }

    // This is not precisely the movement validator, in my opiniomn. But actually is more the StartGame.java
    // I like the way it returns the invalid Moves and Stuff.
    // I will put the StartGame here.
    override fun applyMove(move: Move): MoveResult {
        if(gameHasEnded(game, game.lastBoard)){
            return GameOver(pieces[0].color)
        }
        // this shows takes one more turn to move the rook


        // In this context, "it" represents each element in the pieces list as the find function iterates through them.
        val fromPiece = pieces.find { it.position == move.from } // int row, int col
        val toPiece = pieces.find { it.position == move.to }

        val fromPieceJava = kotlinPositionIntoJavaPosition(move.from);
        val toPieceJava = kotlinPositionIntoJavaPosition(move.to)
        val currentBoard = game.lastBoard;

        val newBoard = Movement()
            .makeMove(game, game.lastBoard, fromPieceJava, toPieceJava);

        if (fromPiece == null)
            return InvalidMove("No piece in (${move.from.row}, ${move.from.column})")
        else if (fromPiece.color != currentPlayer)
            return InvalidMove("Piece does not belong to current player")
        else if (toPiece != null && toPiece.color == currentPlayer)
            return InvalidMove("There is a piece in (${move.to.row}, ${move.to.column})")
        else {
            if (currentBoard == newBoard) {
                return InvalidMove("Invalid Movement: todo")
            }
            else{
                game.passTurn()
                game.setHistoryOfBoards(newBoard)
            }
            pieces = pieces
                .filter { it != fromPiece && it != toPiece }
                .plus(fromPiece.copy(position = move.to))

            currentPlayer = if (currentPlayer == WHITE) BLACK else WHITE

            if (pieces.size == 1) // This simple game ends when there is only one piece left
                return GameOver(pieces[0].color)
        }

        pieces = pieces.map {
            if (((it.color == WHITE && it.position.row == 8) || it.color == BLACK && it.position.row == 1) && it.pieceId == "pawn")
                it.copy(pieceId = "queen")
            else
                it
        }
        // This one here is specially for the castling!
        pieces = BoardAdapter().engineBoardToUIBoard(game.historyOfBoards[game.historyOfBoards.size -1]);
        return NewGameState(pieces, currentPlayer)
    }


}


class ChessGameApplication : AbstractChessGameApplication() {
    override val gameEngine = SimpleGameEngine8x8()
    override val imageResolver = CachedImageResolver(DefaultImageResolver())
}

fun kotlinPositionIntoJavaPosition(piecePos: Position): commons.game.Position {
    return commons.game.Position(piecePos.row - 1, piecePos.column - 1);
}

fun gameHasEnded(game: Game, board: Board?): Boolean {
    for (rule in game.endGameRules) {
        if (!rule.checkEndRule(game, board)) {
            return true
        }
    }
    return false
}
//todo: la reina se transofrma como en e juego q nos dieron. Sacar eso.

