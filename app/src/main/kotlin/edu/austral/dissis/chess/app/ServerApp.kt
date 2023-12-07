package edu.austral.dissis.chess.app

import checkers.factories.BoardTypeCreator
import commons.board.Board
import commons.game.Color
import commons.game.Game
import commons.game.Player
import commons.rules.endgameRules.EndGameRule
import commons.rules.endgameRules.RunOutOfPieces
import commons.rules.restrictionRules.CannotCaptureSameColorRestriction
import commons.rules.restrictionRules.CheckersCanCaptureMustCaptureRule
import commons.rules.restrictionRules.OutOfBoundsRestriction
import edu.austral.dissis.chess.gui.createNormalChessGame
import server.GameServer
import java.sql.Time

fun main(){

    val game = createNormalChessGame()

    GameServer(game)
}