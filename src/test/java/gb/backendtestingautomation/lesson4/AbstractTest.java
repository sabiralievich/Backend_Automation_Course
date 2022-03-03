package gb.backendtestingautomation.lesson4;

import java.io.IOException;
import java.io.InputStream;

public class AbstractTest {
    public String getResourceAsString(String resource) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(resource);
        assert inputStream != null;
        byte[] bytes = inputStream.readAllBytes();
        return new String(bytes);
    }
}
