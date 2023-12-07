package client.listeners;

import client.GameClient;
import commons.result.InvalidMoveResult;
import edu.austral.dissis.chess.gui.InvalidMove;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class InvalidMoveListener implements MessageListener<InvalidMove> {
    GameClient client;
    public InvalidMoveListener(GameClient client){
        this.client = client;
    }
    @Override
    public void handleMessage(@NotNull Message<InvalidMove> message) {
        client.handleInvalidMove(message);
    }
}
