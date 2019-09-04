package concurrents.singleton;

/**
 * 懒汉式
 * 多线程下也是安全的
 * 长时间占用内存
 */
public class EagerPattern {

    /**
     * can't lazy load.
     */
    private static final EagerPattern instance = new EagerPattern();

    private EagerPattern() {
        //empty
    }

    public static EagerPattern getInstance() {
        return instance;
    }
}