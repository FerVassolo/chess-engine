package client.listeners;

import client.GameClient;
import edu.austral.dissis.chess.gui.GameOver;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class GameOverListener implements MessageListener<GameOver> {
    GameClient client;
    public GameOverListener(GameClient client){
        this.client = client;
    }
    @Override
    public void handleMessage(@NotNull Message<GameOver> message) {
        client.handleEndGame(message);
    }
}
