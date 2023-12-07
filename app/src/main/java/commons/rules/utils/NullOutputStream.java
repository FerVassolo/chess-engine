package commons.rules.utils;

import java.io.IOException;
import java.io.OutputStream;

/**A method to cancel any system.out.println that could appear
 * */
public class NullOutputStream extends OutputStream {
    @Override
    public void write(int b) throws IOException {
        // Do nothing, discard the output
    }
}
