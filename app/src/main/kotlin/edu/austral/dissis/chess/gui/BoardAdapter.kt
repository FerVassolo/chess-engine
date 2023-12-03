import commons.game.Board
import commons.game.Piece
import commons.game.PieceName
import commons.game.Position
import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.chess.gui.PlayerColor

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

    private fun convertColor(color: commons.game.Color): PlayerColor {
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
            PieceName.MAN -> "pawn"
            PieceName.CHECKERS_KING -> "queen"
        }
    }
}
