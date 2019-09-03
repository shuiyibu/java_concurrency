package _3createthread;

/***
 *
 */
public class CreateThreadWithStackSize {

    private static int counter = 0;
    private int i = 0;
    private byte[] bytes = new byte[1024];

    /**
     * StackOverflowError
     * 3200
     *
     * @param args
     * @Description: JVM will create a thread named "main"
     */
    public static void main(String[] args) {
        //create a JVM stack
        try {
            add(0);
        } catch (Error e) {
            e.printStackTrace();
            System.out.println(counter);
        }
    }

    private static void add(int i) {//栈帧深度过长
        ++counter;
        add(i + 1);
    }
}


