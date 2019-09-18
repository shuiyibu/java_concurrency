package concurrents.active;

/**
 * 导引：
 * System.gc();
 * 调用线程和被调用线程 A a-> B b
 * Future 不适合多对多的情况；无法存储调用存储请求 网盘
 */
public class Test {

    //A a-> B b
    //
    //main
    public static void main(String[] args) {
//        System.gc();
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakerClientThread(activeObject, "Alice").start();
        new MakerClientThread(activeObject, "Bobby").start();

        new DisplayClientThread("Chris", activeObject).start();
    }
}
