package edu.austral.dissis.chess.app

import client.GameClient
import edu.austral.dissis.chess.gui.*
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.stage.Stage
import example.* // Import classes from the specified directory


fun main() {
    launch(GameApp2::class.java)
}

class GameApp2 : Application() {
    //private val gameEngine = MySpecialChessGame()
    private val imageResolver = CachedImageResolver(DefaultImageResolver())

    companion object {
        const val GameTitle = "Chess"
        val gameClient = GameClient()
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = GameTitle

        val root = GameView(imageResolver)
        primaryStage.scene = Scene(root)

        gameClient.start(root)
        primaryStage.show()
    }
}