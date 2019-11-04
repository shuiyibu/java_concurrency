package phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class PhaserExample3 {
    private final static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);
        for (int i = 0; i < 6; i++) {
            new PhaserExample2.Atheletes(phaser, i);
        }
        new InjureAtheletes(phaser, 6).start();
    }

    static class InjureAtheletes extends Thread {
        private final Phaser phaser;
        private final int no;

        public InjureAtheletes(Phaser phaser, int no) {
            this.phaser = phaser;
            this.no = no;
            start();
        }

        @Override
        public void run() {
            sport(phaser, "running");

            sport(phaser, " long jump");

            sport(phaser, "injured");
            out.println("Oh shit, I am injured............");
            phaser.arriveAndDeregister();
        }

        private void sport(Phaser phaser, String sportName) {
            out.println(no + ": start " + sportName);
            sleep(5);
            out.println(no + ": end " + sportName);
            out.println("getPharser()=>" + phaser.getPhase());
            phaser.arriveAndAwaitAdvance();
        }

        private void sleep(int time) {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(time));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}