package phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class PhaserExample2 {
    private final static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(5);
        for (int i = 0; i < 6; i++) {
            new Atheletes(phaser, i);
        }
    }

    static class Atheletes extends Thread {
        private final Phaser phaser;
        private final int no;

        public Atheletes(Phaser phaser, int no) {
            this.phaser = phaser;
            this.no = no;
            start();
        }

        @Override
        public void run() {
            out.println(no + " : start running..");
            sleep(5);
            out.println(no + " : end running..");
            out.println("getPharser()=>" + phaser.getPhase());
            phaser.arriveAndAwaitAdvance();

            out.println(no + " : start long jump..");
            sleep(5);
            out.println(no + " : end long jump..");
            out.println("getPharser()=>" + phaser.getPhase());
            phaser.arriveAndAwaitAdvance();

            out.println(no + " : start bicycle..");
            sleep(5);
            out.println(no + " : end bicycle..");
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
