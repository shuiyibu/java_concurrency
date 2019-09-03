

`static`的生命周期很长

```java
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

```





# 构造Thread

| Constructor and Description                                  |
| :----------------------------------------------------------- |
| `Thread()` <br/> Allocates a new `Thread` object.            |
| `Thread(Runnable target)`<br/>Allocates a new `Thread` object. |
| `Thread(Runnable target, String name)`<br/>Allocates a new `Thread` object. |
| `Thread(String name)`<br/>Allocates a new `Thread` object.   |
| `Thread(ThreadGroup group, Runnable target)`<br/>Allocates a new `Thread` object. |
| `Thread(ThreadGroup group, Runnable target, String name)`<br/>Allocates a new `Thread` object so that it has `target` as its run object, has the specified `name` as its name, and belongs to the thread group referred to by `group`. |
| `Thread(ThreadGroup group, Runnable target, String name, long stackSize)`<br/>Allocates a new `Thread` object so that it has `target` as its run object, has the specified `name` as its name, and belongs to the thread group referred to by `group`, and has the specified *stack size*. |
| `Thread(ThreadGroup group, String name)`<br/>Allocates a new `Thread` object. |



# Deamon线程的创建



```java
public class ThreadInterrupt {
    /**
     * interrupt
     * java.lang.InterruptedException
     *
     * @param args
     */
    public static void main(String[] args) {
    		//模拟打断join状态下的线程
        Thread t = new Thread(() -> {
            while (true) {

            }
        });

        t.start();
        Thread main = Thread.currentThread();
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            main.interrupt();//main线程打断
            System.out.println("interrupt");
        });

        t2.start();
        try {
            t.join();//这里是使main线程等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

```





# 线程安全与数据共享



# 文献综述

- [JDK8 API](https://docs.oracle.com/javase/8/docs/api/)
- 