package chapter6;

/**
 *
 */
public class ThreadCloseGraceful {

    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        worker.shutdown();
    }

    private static class Worker extends Thread {
        private volatile boolean start = true;

        @Override
        public void run() {
            while (start) {
                //TO DO SOMETHING 如此在这里阻塞，那么无论使flag还是interrupt都将会失效
            }
        }

        public void shutdown() {
            this.start = false;
        }
    }
}
