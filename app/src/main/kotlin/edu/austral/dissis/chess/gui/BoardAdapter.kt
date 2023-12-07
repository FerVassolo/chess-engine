package edu.austral.dissis.chess.gui
import commons.board.Board
import commons.piece.Piece
import commons.piece.PieceName
import commons.board.Position

class BoardAdapter {

    fun engineBoardToUIBoard(board: Board): List<ChessPiece> {
        val pieces = mutableListOf<ChessPiece>()

        val positions: Map<Position, Piece>? = board.positions
        positions?.forEach { (pos, piece) ->
            if (piece != null) {
                val chessPiece = ChessPiece(piece.id.toString(), convertColor(piece.color), edu.austral.dissis.chess.gui.Position(pos.row + 1, pos.col + 1), convertPieceName(piece.name))
                pieces.add(chessPiece)
            }
        }
        return pieces
    }

    fun convertColor(color: commons.game.Color): PlayerColor {
        return when (color) {
            commons.game.Color.WHITE -> PlayerColor.WHITE
            commons.game.Color.BLACK -> PlayerColor.BLACK
        }
    }

    private fun convertPieceName(name: PieceName): String {
        return when (name) {
            PieceName.PAWN -> "pawn"
            PieceName.ROOK -> "rook"
            PieceName.KNIGHT -> "knight"
            PieceName.BISHOP -> "bishop"
            PieceName.KING -> "king"
            PieceName.QUEEN -> "queen"
            PieceName.ARCHBISHOP -> "chancellor" // Actually because archbishop black icon is white as well
            PieceName.MAN -> "pawn"
            PieceName.CHECKERS_KING -> "queen"
        }
    }
    fun kotlinPositionIntoJavaPosition(piecePos: edu.austral.dissis.chess.gui.Position): commons.board.Position {
        return commons.board.Position(piecePos.row - 1, piecePos.column - 1);
    }

}
