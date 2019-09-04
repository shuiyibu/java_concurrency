package concurrents.singleton;

/***
 * 懒加载
 * 线程安全
 * 效率高
 */
public class ElegantHolder {

    private ElegantHolder() {

    }

    public static ElegantHolder getInstance() {
        return InstanceHolder.instance;
    }

    private static class InstanceHolder {
        private final static ElegantHolder instance = new ElegantHolder();
    }
}