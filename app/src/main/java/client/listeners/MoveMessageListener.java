package client.listeners;

import client.GameClient;
import commons.board.Board;
import edu.austral.dissis.chess.gui.MoveResult;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

// Change to moveResult
public class MoveMessageListener implements MessageListener<MoveResult> {
    GameClient client;
    public MoveMessageListener(GameClient client){
        this.client = client;
    }
    @Override
    public void handleMessage(@NotNull Message<MoveResult> message) {
        client.handleMove(message);
    }
}
