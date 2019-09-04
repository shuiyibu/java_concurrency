package concurrents.singleton;

/***
 * 可能会引起空指针异常（编译重排序）
 * 构造函数未初始化之前，getInstance就已经返回，可能导致某些变量未被初始化
 */
public class DoubleCheck {

    private static DoubleCheck instance;

    private DoubleCheck() {
        //---
    }

    //double check
    public static DoubleCheck getInstance() {

        if (null == instance) {
            synchronized (DoubleCheck.class) {
                if (null == instance)
                    instance = new DoubleCheck();
            }
        }

        return DoubleCheck.instance;
    }
}
