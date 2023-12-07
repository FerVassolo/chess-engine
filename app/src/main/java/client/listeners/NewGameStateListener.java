package client.listeners;

import client.GameClient;
import edu.austral.dissis.chess.gui.NewGameState;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class NewGameStateListener implements MessageListener<NewGameState> {
    GameClient client;
    public NewGameStateListener(GameClient client){
        this.client = client;
    }
    @Override
    public void handleMessage(@NotNull Message<NewGameState> message) {
        client.handleNewGameState(message);
    }
}
