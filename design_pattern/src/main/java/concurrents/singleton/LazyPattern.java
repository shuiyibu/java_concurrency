package concurrents.singleton;

/***
 * 存在并发问题
 */
public class LazyPattern {

    private static LazyPattern instance;

    private LazyPattern() {
        //empty
    }

    public static LazyPattern getInstance() {
        if (null == instance)
            instance = new LazyPattern();

        return LazyPattern.instance;
    }
}
