package concurrents.future;

/**
 * 同步调用
 * <p>
 * IFuture        ->代表的是未来的一个凭据
 * IFutureTask    ->将你的调用逻辑进行了隔离
 * FutureService ->桥接 Future和 IFutureTask
 */
public class SyncInvoker {

    public static void main(String[] args) throws InterruptedException {
        /*String result = get();
        System.out.println(result);*/

        FutureService futureService = new FutureService();
        futureService.submit(() -> {
            try {
                Thread.sleep(10000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "FINISH";
        }, System.out::println);

        System.out.println("===========");
        System.out.println(" do other thing.");
        Thread.sleep(1000);
        System.out.println("===========");

        //  System.out.println(future.get());

    }

    private static String get()
            throws InterruptedException {
        Thread.sleep(10000l);
        return "FINISH";
    }
}