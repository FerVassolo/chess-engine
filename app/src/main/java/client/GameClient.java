/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package client;

import client.listeners.*;
import com.fasterxml.jackson.core.type.TypeReference;
import edu.austral.dissis.chess.gui.*;
import edu.austral.ingsis.clientserver.Client;
import edu.austral.ingsis.clientserver.ClientBuilder;
import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder;



import java.net.InetSocketAddress;

public class GameClient {

    private Client client;
    private ClientBuilder clientBuilder = NettyClientBuilder.Companion.createDefault();
    private GameView gameView;

    public void start(GameView gameView){
        this.gameView = gameView;
        client = buildClient();
        client.connect();
        client.send(new Message<>("hello", new HelloPayload("Server")));
        gameView.addListener(new MoveEventListener(this));
    }

    public GameView getGameView() {
        return gameView;
    }

    public void sendMove(Move move) {
        client.send(new Message<>("movement", move));
    }

    public void handleMove(Message<MoveResult> message) {
        gameView.handleMoveResult(message.getPayload());
    }

    public void handleNewGameState(Message<NewGameState> message) {
        gameView.handleMoveResult(message.getPayload());
    }

    public void handleInvalidMove(Message<InvalidMove> message) {
        gameView.handleMoveResult(message.getPayload());
    }

    public void handleEndGame(Message<GameOver> message){
        gameView.handleMoveResult(message.getPayload());
    }



    public Client buildClient(){
        final var client = clientBuilder
                .withAddress(new InetSocketAddress("localhost", 8095))
                .withConnectionListener(new MyClientConnectionListener())
                // se suscribe al topico ping, cada vez que el server mande un mensaje el client lo va a recibir.
                .addMessageListener("init-game", new TypeReference<>() {}, new InitGameListener(this))
                .addMessageListener("new-game-state", new TypeReference<>() {}, new NewGameStateListener(this))
                .addMessageListener("invalid-move", new TypeReference<>() {}, new InvalidMoveListener(this))
                .addMessageListener("endgame", new TypeReference<>() {}, new GameOverListener(this)
                )
                .build();
        return client;
    }


}
