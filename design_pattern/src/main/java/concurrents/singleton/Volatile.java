package concurrents.singleton;

/**
 * volatile可以保证可见性和有序性，严格遵循happens-before
 */
public class Volatile {

    private static volatile Volatile instance;

    private Volatile() {
        //
    }

    //double check add volatile
    public static Volatile getInstance() {

        if (null == instance) {
            synchronized (DoubleCheck.class) {
                if (null == instance)
                    instance = new Volatile();
            }
        }
        return Volatile.instance;
    }
}
