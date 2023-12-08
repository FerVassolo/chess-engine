package edu.austral.dissis.chess.app

import client.GameClient
import edu.austral.dissis.chess.gui.*
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.stage.Stage


fun main() {
    launch(GameApp::class.java)
}

class GameApp : Application() {
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