package concurrents.volatiles;

/**
 * <p>
 * i=1
 * i=i+1
 * CPU1 -> main memory -> i -> cache i+1 -> cache -> main memory(单线程没问题)
 * CPU2 -> main memory -> i -> cache i+1 -> cache -> main memory（多线程缓存不一致）
 *
 * <p>
 * 解决方法：
 * - 给数据总线加锁:LOCK#(执行效率十分低下)
 * - 高速缓存一致性协议（推荐） Intel
 * <p>
 * 核心思想：
 * - 当CPU写入数据时，如果发现该变量被数据共享（也就是，在其他CPU中也存在该变量的副本），会发出一个信号，通知其他CPU该变量的缓存无效
 * - 当其他CPU访问该变量时，重新到主内存进行获取
 *
 * <p>
 * 总线：
 * - 数据总线
 * - 地址总线
 * - 控制总线
 * <p>
 */
public class VolatileTest2 {

    private final static int MAX_LIMIT = 500;
    private static volatile int INIT_VALUE = 0;

    public static void main(String[] args) {
//        new Thread(() -> {
//            while (INIT_VALUE < MAX_LIMIT) {
//                int localValue = INIT_VALUE;
//                localValue++;//有写操作会更新主内存
//                INIT_VALUE = localValue;
//                System.out.println("T1->" + localValue);
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "ADDER-1").start();
//
//        new Thread(() -> {
//            while (INIT_VALUE < MAX_LIMIT) {
//                int localValue = INIT_VALUE;
//                localValue++;
//                INIT_VALUE = localValue;
//                System.out.println("T2->" + localValue);
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "ADDER-1").start();
        /**
         * 10
         * 1. read from main memory INIT_VALUE -> 10
         * 2. INIT_VALUE = 10 + 1(NOT INIT_VALUE = INIT_VALUE + 1)
         * 3. INIT_VALUE = 11
         */
        new Thread(new AddVolatile("T1"), "ADDER-1").start();
        new Thread(new AddVolatile("T2"), "ADDER-2").start();
    }

    static class AddVolatile implements Runnable {
        private String threadName;

        public AddVolatile(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            while (INIT_VALUE < MAX_LIMIT) {
                System.out.println(threadName + "->" + (++INIT_VALUE));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
