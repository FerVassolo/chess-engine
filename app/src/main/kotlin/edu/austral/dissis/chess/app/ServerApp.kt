package edu.austral.dissis.chess.app


import edu.austral.dissis.chess.gui.createNormalChessGame
import server.GameServer
import java.sql.Time

fun main(){

    val game = createNormalChessGame()

    GameServer(game)
}