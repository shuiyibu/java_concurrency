package concurrents.volatiles;

/***
 * 内存
 *  RAM
 *
 *  ROM(一般不用)
 *
 * CPU寄存器
 *
 * CPU指令
 */
public class VolatileTest {

    private final static int MAX_LIMIT = 500;
    private static volatile int INIT_VALUE = 0;//注意有无volatile关键字的区别

    public static void main(String[] args) {
        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (localValue < MAX_LIMIT) {
                if (localValue != INIT_VALUE) {//无写操作不会更新主内存
                    System.out.printf("The value updated to [%d]\n", INIT_VALUE);
                    localValue = INIT_VALUE;
                }
            }
        }, "READER").start();

        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (INIT_VALUE < MAX_LIMIT) {
                System.out.printf("Update the value to [%d]\n", ++localValue);
                INIT_VALUE = localValue;
                try {
                    Thread.sleep(100);//为了让volatile感知到
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "UPDATER").start();
    }
}