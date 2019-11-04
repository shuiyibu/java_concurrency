package phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class PhaserExample5 {
    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(2) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                return false;
            }
        };

        new OnAdvanceTask("Alex", phaser).start();
        new OnAdvanceTask("Bob", phaser).start();

        TimeUnit.SECONDS.sleep(2);
        out.println(phaser.getUnarrivedParties());
        out.println(phaser.getArrivedParties());
    }

    static class OnAdvanceTask extends Thread {
        private final Phaser phaser;

        public OnAdvanceTask(String name, Phaser phaser) {
            super(name);
            this.phaser = phaser;
        }

        @Override
        public void run() {
            out.println(getName() + " I am start and the phase " + phaser.getPhase());
            phaser.arriveAndAwaitAdvance();
            out.println(getName() + " I am end ");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if ("Alex".equals(getName())) {
                out.println(getName() + " I am start and the phase " + phaser.getPhase());
                phaser.arriveAndAwaitAdvance();
                out.println(getName() + " I am end ");
            }

        }
    }
}
