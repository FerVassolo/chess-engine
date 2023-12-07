package server;

import com.fasterxml.jackson.core.type.TypeReference;
import commons.game.Game;
import edu.austral.dissis.chess.gui.Move;
import edu.austral.ingsis.clientserver.Server;
import edu.austral.ingsis.clientserver.ServerBuilder;
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder;
import server.listeners.InitConnectionListener;
import server.listeners.MoveMessageListener;

public class GameServer {
    private Game game;
    private Server server;
    private ServerBuilder serverBuilder = NettyServerBuilder.Companion.createDefault();

    public GameServer(Game game) {
        this.game = game;
        this.server = buildServer();
        this.server.start();
    }

    public Game getGame(){
        return game;
    }

    public Server getServer(){
        return server;
    }

    /**Three options <br>
     * 1. Moves and returns moved board
     * 2. Invalid Move and returns error message
     * 3. Game has ended and returns endgame.
     * */
    public void applyMove(Move move){


    }



    public Server buildServer(){
        final Server client = serverBuilder
                .withPort(8095)
                .withConnectionListener(new InitConnectionListener(this)) // when a client connects
                .addMessageListener("movement", new TypeReference<>() {}, new MoveMessageListener(this))
                .build();

        return client;
    }


}
/* = NettyClientBuilder.Companion.createDefault()
                .withAddress(new InetSocketAddress("localhost", 8095))
                .withConnectionListener(new MyClientConnectionListener())
                .addMessageListener("ping", new TypeReference<>() {
                }, new PingListener())
                .build();*/