package concurrents.singleton;

/***
 * 当实例创建后，严重影响性能
 */
public class SynchronizedSingleton {
    private static SynchronizedSingleton instance;

    private SynchronizedSingleton() {
        //empty
    }

    public synchronized static SynchronizedSingleton getInstance() {

        if (null == instance)
            instance = new SynchronizedSingleton();

        return SynchronizedSingleton.instance;
    }
}