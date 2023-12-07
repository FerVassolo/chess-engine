package example;

import edu.austral.ingsis.clientserver.Message;
import edu.austral.ingsis.clientserver.MessageListener;
import org.jetbrains.annotations.NotNull;

public class PingListener implements MessageListener<String> {

    @Override
    public void handleMessage(@NotNull Message<String> message) {
        System.out.println(message.getPayload());
    }
}
