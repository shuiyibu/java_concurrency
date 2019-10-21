package concurrents.singlethreadexecution;

/**
 * @description:
 * @auther:
 * @date: 2019-09-04 22:34
 */
public class Client {
    public static void main(String[] args) {
        Gate gate = new Gate();
        User bj = new User("Baobao", "Beijing", gate);
        User sh = new User("ShangLao", "ShangHai", gate);
        User gz = new User("GuangLao", "GuangZhou", gate);

        bj.start();
        sh.start();
        gz.start();
    }
}
