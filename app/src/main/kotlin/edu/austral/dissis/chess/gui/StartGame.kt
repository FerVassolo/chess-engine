package edu.austral.dissis.chess.gui

import commons.board.Board
import commons.game.Game
import commons.game.Player
import commons.rules.endgameRules.EndGameRule
import commons.rules.restrictionRules.RestrictionRule
import java.sql.Time

class StartGame : GameEngine {


    override fun init(): InitialState {
        TODO("Not yet implemented")
    }

    override fun applyMove(move: Move): MoveResult {
        TODO("Not yet implemented")
    }


    fun createGame(players: Array<Player>, gameRules: Array<RestrictionRule>, endgameRules: Array<EndGameRule>, board: Board): Game {
        return Game(gameRules, endgameRules, players, board, Time(0))
    }


}