package atomic;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2019-09-20 10:20
 * @Description:
 */
public class AtomicReferenceTest {
    public static void main(String[] args) {
        AtomicReference<Simple> simple = new AtomicReference<>(new Simple("dylan", 18));
        boolean result = simple.compareAndSet(new Simple("", 1), new Simple("", 1));
        out.println(result);

        JButton button = new JButton();
        AtomicReference<Simple> s = new AtomicReference<>(new Simple("dylan", 18));
        button.addActionListener(e -> s.set(new Simple("", 1)));
    }

    static class Simple {
        private String name;
        private int age;

        public Simple(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
