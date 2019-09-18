package concurrents.twophraseterminate;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class AppServer extends Thread {

    private static final int DEFAULT_PORT = 12722;
    private final int port;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * 多监听客户端接口
     */
    private volatile boolean start = true;

    /**
     * 工作线程
     */
    private List<ClientHandler> clientHandlers = new ArrayList<>();
    private ServerSocket server;

    public AppServer() {
        this(DEFAULT_PORT);
    }

    public AppServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            this.server = new ServerSocket(port);
            while (start) {
                Socket client = server.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                executor.submit(clientHandler);
                this.clientHandlers.add(clientHandler);
            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
        } finally {
            this.dispose();
        }
    }

    private void dispose() {
        System.out.println("dispose");
        this.clientHandlers.stream().forEach(ClientHandler::stop);
        this.executor.shutdown();
    }

    public void shutdown() throws IOException {
        this.start = false;
        this.interrupt();
        this.server.close();
    }
}