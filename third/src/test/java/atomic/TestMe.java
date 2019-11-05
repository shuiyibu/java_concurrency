package atomic;

/**
 * @Auther: langdylan
 * @Date: 2019-09-23 14:45
 * @Description:
 */
public class TestMe {
    private volatile int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
