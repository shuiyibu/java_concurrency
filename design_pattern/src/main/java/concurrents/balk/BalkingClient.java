package concurrents.balk;

/**
 *
 */
public class BalkingClient {
    public static void main(String[] args) {
        BalkingData balkingData = new BalkingData("classPath:balking.txt", "===BEGIN====");
        new CustomerThread(balkingData).start();
        new WaiterThread(balkingData).start();
    }
}