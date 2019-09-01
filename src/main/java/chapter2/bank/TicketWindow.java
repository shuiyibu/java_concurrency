package chapter2.bank;

/***************************************
 * @Description: 模拟银行较好系统
 ***************************************/
public class TicketWindow extends Thread {

    private final String name;//柜台名称

    private static final int MAX = 50;

    private static int index = 1; //static的生命周期很长

    public TicketWindow(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        while (index <= MAX) {

            System.out.println("柜台：" + name + "当前的号码是:" + (index++));
        }
    }
}
