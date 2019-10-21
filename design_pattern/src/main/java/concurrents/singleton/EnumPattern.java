package concurrents.singleton;

import java.util.stream.IntStream;

/***
 * Doug Lee力推
 */
public class EnumPattern {
    private EnumPattern() {

    }

    public static EnumPattern getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 100)
                .forEach(i -> new Thread(String.valueOf(i)) {
                    @Override
                    public void run() {
                        System.out.println(EnumPattern.getInstance());
                    }
                }.start());
    }

    /**
     * 枚举类型使线程安全的
     * 构造函数仅会被加载一次
     */
    private enum Singleton {
        INSTANCE;

        private final EnumPattern instance;

        Singleton() {
            instance = new EnumPattern();
        }

        public EnumPattern getInstance() {
            return instance;
        }
    }
}
