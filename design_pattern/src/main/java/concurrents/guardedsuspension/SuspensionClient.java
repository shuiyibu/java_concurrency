package concurrents.guardedsuspension;

/****
 *
 */
public class SuspensionClient {
    public static void main(String[] args) throws InterruptedException {

        final RequestQueue queue = new RequestQueue();
        new ClientThread(queue, "Alex").start();
        ServerThread serverThread = new ServerThread(queue);
        serverThread.start();
        //serverThread.join();

        Thread.sleep(20000);
        serverThread.close();
    }
}
