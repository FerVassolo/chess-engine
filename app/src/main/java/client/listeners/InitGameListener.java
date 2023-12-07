package client.listeners;

import client.GameClient;
import edu.austral.dissis.chess.gui.InitialState;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class InitGameListener implements MessageListener<InitialState> {

    GameClient client;
    public InitGameListener(GameClient client){
        this.client = client;
    }

    @Override
    public void handleMessage(@NotNull Message<InitialState> message) {
        client.getGameView().handleInitialState(message.getPayload());
    }
}
