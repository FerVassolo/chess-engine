package example;

import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class HelloListener implements MessageListener<HelloPayload> {

    @Override
    public void handleMessage(@NotNull Message<HelloPayload> message) {
        System.out.println(message.getPayload());
    }
}
