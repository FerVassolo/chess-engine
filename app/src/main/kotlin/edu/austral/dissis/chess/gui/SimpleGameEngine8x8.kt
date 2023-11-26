package edu.austral.dissis.chess.gui

import edu.austral.dissis.chess.gui.PlayerColor.BLACK
import edu.austral.dissis.chess.gui.PlayerColor.WHITE
import game.*
import javafx.application.Application.launch
import rules.*
import java.sql.Time


fun main() {
    launch(ChessGameApplication::class.java)
}


class SimpleGameEngine8x8 : GameEngine {

    var game = createNormalGame()
    // Class variables
    private var currentPlayer = WHITE

    // Ive got this for displaying. But if I do the same in the back then I'm repeating code.
    // I'll do an 'adapter' that can transform the board in the front and pass it to the back correctly.
    // not as for now tho.
    private var pieces = listOf(
            ChessPiece("1", WHITE, Position(1, 1), "rook"),
            ChessPiece("2", WHITE, Position(1, 2), "knight"),
            ChessPiece("3", WHITE, Position(1, 3), "bishop"),
            ChessPiece("4", WHITE, Position(1, 4), "queen"),
            ChessPiece("5", WHITE, Position(1, 5), "king"),
            ChessPiece("6", WHITE, Position(1, 6), "bishop"),
            ChessPiece("7", WHITE, Position(1, 7), "knight"),
            ChessPiece("8", WHITE, Position(1, 8), "rook"),
            ChessPiece("9", WHITE, Position(2, 1), "pawn"),
            ChessPiece("10", WHITE, Position(2, 2), "pawn"),
            ChessPiece("11", WHITE, Position(2, 3), "pawn"),
            ChessPiece("12", WHITE, Position(2, 4), "pawn"),
            ChessPiece("13", WHITE, Position(2, 5), "pawn"),
            ChessPiece("14", WHITE, Position(2, 6), "pawn"),
            ChessPiece("15", WHITE, Position(2, 7), "pawn"),
            ChessPiece("16", WHITE, Position(2, 8), "pawn"),
            ChessPiece("17", BLACK, Position(8, 1), "rook"),
            ChessPiece("18", BLACK, Position(8, 2), "knight"),
            ChessPiece("19", BLACK, Position(8, 3), "bishop"),
            ChessPiece("20", BLACK, Position(8, 4), "queen"),
            ChessPiece("21", BLACK, Position(8, 5), "king"),
            ChessPiece("22", BLACK, Position(8, 6), "bishop"),
            ChessPiece("23", BLACK, Position(8, 7), "knight"),
            ChessPiece("24", BLACK, Position(8, 8), "rook"),
            ChessPiece("25", BLACK, Position(7, 1), "pawn"),
            ChessPiece("26", BLACK, Position(7, 2), "pawn"),
            ChessPiece("27", BLACK, Position(7, 3), "pawn"),
            ChessPiece("28", BLACK, Position(7, 4), "pawn"),
            ChessPiece("29", BLACK, Position(7, 5), "pawn"),
            ChessPiece("30", BLACK, Position(7, 6), "pawn"),
            ChessPiece("31", BLACK, Position(7, 7), "pawn"),
            ChessPiece("32", BLACK, Position(7, 8), "pawn"),
    )

    override fun init(): InitialState {
        return InitialState(BoardSize(8, 8), pieces, WHITE)
    }

    private fun createNormalGame(): Game {
        val fer = Player("fer", 1, Time(0), Color.WHITE)
        val leo = Player("leo", 2, Time(0), Color.BLACK)
        val gameRules = arrayOf(CannotCaptureSameColorRestriction(), OutOfBoundsRestriction(), CannotMoveIfInCheck())
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

        // In this context, "it" represents each element in the pieces list as the find function iterates through them.
        val fromPiece = pieces.find { it.position == move.from } // int row, int col
        val toPiece = pieces.find { it.position == move.to }

        val fromPieceJava = kotlinPositionIntoJavaPosition(move.from);
        val toPieceJava = kotlinPositionIntoJavaPosition(move.to)
        val currentBoard = game.lastBoard;

        val newBoard = Movement().makeMove(game, game.lastBoard, fromPieceJava, toPieceJava);

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
            if ((it.color == WHITE && it.position.row == 8) || it.color == BLACK && it.position.row == 1)
                it.copy(pieceId = "queen")
            else
                it
        }

        return NewGameState(pieces, currentPlayer)
    }


}

class MovePrinter : PieceMovedListener {
    override fun onMovePiece(from: Position, to: Position) {
        print("Move: from ")
        print(from)
        print(" to ")
        println(to)
    }
}

class ChessGameApplication : AbstractChessGameApplication() {
    override val gameEngine = SimpleGameEngine8x8()
    override val imageResolver = CachedImageResolver(DefaultImageResolver())
}

fun kotlinPositionIntoJavaPosition(piecePos: Position):  game.Position{
    return game.Position(piecePos.row - 1, piecePos.column - 1);
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

//todo: en el más mínimo jaque tengo checkmate. ¡¡¡ESTÁ MAL!!!