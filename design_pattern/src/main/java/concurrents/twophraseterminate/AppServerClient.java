package concurrents.twophraseterminate;

import java.io.IOException;

/**
 *
 */
public class AppServerClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        AppServer server = new AppServer(13345);
        server.start();
        Thread.sleep(15_000L);
        server.shutdown();
    }
}
