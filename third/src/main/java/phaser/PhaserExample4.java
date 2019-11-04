package phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class PhaserExample4 {
    public static void main(String[] args) throws InterruptedException {
        final Phaser phaser = new Phaser(1);

        /**
         * 0
         * 1
         * 2
         * 3
         */
        out.println(phaser.getPhase());

        phaser.arriveAndAwaitAdvance();
        out.println(phaser.getPhase());

        phaser.arriveAndAwaitAdvance();
        out.println(phaser.getPhase());

        phaser.arriveAndAwaitAdvance();
        out.println(phaser.getPhase());
        out.println("======================================");


        /**
         * 1
         * 2
         * 3
         */
        out.println(phaser.getRegisteredParties());

        phaser.register();
        out.println(phaser.getRegisteredParties());

        phaser.register();
        out.println(phaser.getRegisteredParties());
        out.println("======================================");


        /**
         * 0
         * 3
         */
        out.println(phaser.getArrivedParties());
        out.println(phaser.getUnarrivedParties());
        out.println("======================================");


        /**
         * 13
         * 13
         * 0
         *
         * 13
         * 12
         * 1
         */
        phaser.bulkRegister(10);
        out.println(phaser.getRegisteredParties());
        out.println(phaser.getUnarrivedParties());
        out.println(phaser.getArrivedParties());
        new Thread(phaser::arriveAndAwaitAdvance).start();
        TimeUnit.SECONDS.sleep(1);
        out.println("======================================");
        out.println(phaser.getRegisteredParties());
        out.println(phaser.getUnarrivedParties());
        out.println(phaser.getArrivedParties());


    }


}
