package edu.austral.dissis.chess.gui

import chess.factories.BoardTypeCreator
import commons.board.Board
import commons.game.Color
import commons.game.Game
import commons.game.Player
import commons.rules.endgameRules.CheckMate
import commons.rules.endgameRules.EndGameRule
import commons.rules.endgameRules.RunOutOfPieces
import commons.rules.restrictionRules.CannotCaptureSameColorRestriction
import commons.rules.restrictionRules.CannotMoveIfInCheck
import commons.rules.restrictionRules.CheckersCanCaptureMustCaptureRule
import commons.rules.restrictionRules.OutOfBoundsRestriction
import java.sql.Time


fun createNormalChessGame() : Game {
    val gameRules = arrayOf(
        CannotCaptureSameColorRestriction(),
        OutOfBoundsRestriction(),
        CannotMoveIfInCheck()
    )
    val endGameRules = arrayOf<EndGameRule>(CheckMate())
    val players = samplePlayers()
    val board: Board = BoardTypeCreator().NormalBoardDisplay()

    return Game(gameRules, endGameRules, players, board, Time(0))
}

fun createNormalCheckersGame() : Game{
    val gameRules = arrayOf(
        CannotCaptureSameColorRestriction(),
        OutOfBoundsRestriction(),
        CheckersCanCaptureMustCaptureRule(),
    )
    val endGameRules = arrayOf<EndGameRule>(RunOutOfPieces())
    val players = samplePlayers()
    val board: Board = checkers.factories.BoardTypeCreator().NormalBoardDisplay()

    return Game(gameRules, endGameRules, players, board, Time(0))
}

fun createMySpecialGame() : Game{
    val gameRules = arrayOf(
        CannotCaptureSameColorRestriction(),
        OutOfBoundsRestriction(),
    )
    val endGameRules = arrayOf<EndGameRule>(RunOutOfPieces())
    val players = samplePlayers()
    val board: Board = BoardTypeCreator().aKingTwoArchbishops()
    return Game(gameRules, endGameRules, players, board, Time(0))
}

fun samplePlayers() : Array<Player>{
    val fer = Player("fer", 1, Time(0), Color.WHITE)
    val leo = Player("leo", 2, Time(0), Color.BLACK)
    return arrayOf(fer, leo)
}