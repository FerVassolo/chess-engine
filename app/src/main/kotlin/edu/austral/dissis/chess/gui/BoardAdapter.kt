/*
class BoardAdapter {

    fun convertEngineToUIBoard(engineBoard: Map<commons.Position, Piece>): List<ChessPiece> {
        val uiBoard = mutableListOf<ChessPiece>()
        for ((position, piece) in engineBoard) {
            val uiPosition = convertEngineToUIPosition(position)
            val uiPiece = convertEngineToUIPiece(piece)
            if (uiPiece != null) {
                uiPiece.position = uiPosition
                uiBoard.add(uiPiece)
            }
        }
        return uiBoard
    }


}*/
