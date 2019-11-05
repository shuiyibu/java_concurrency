package atomic;

import sun.misc.Unsafe;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2019-09-25 10:38
 * @Description:
 */
public class UnsafeForTest {
    private static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchFieldException, IOException, NoSuchMethodException, InvocationTargetException {
        Simple simple = new Simple();
        out.println(simple.getI());

        /**
         * construct......
         * 1
         */
        Simple simple1 = Simple.class.newInstance();
        out.println(simple1.getI());

        /**
         * construct......
         * 1
         */
        Class.forName("atomic.UnsafeForTest");
        Unsafe unsafe = getUnsafe();

        /**
         * 可以绕过类的初始化
         * 0
         * class atomic.UnsafeForTest$Simple
         * sun.misc.Launcher$AppClassLoader@14dad5dc
         */
        Simple simple2 = (Simple) unsafe.allocateInstance(Simple.class);
        out.println(simple2.getI());
        out.println(simple2.getClass());
        out.println(simple2.getClass().getClassLoader());


        Guard guard = new Guard();
        guard.work();

        /**
         * I am working...
         */
        Field field = guard.getClass().getDeclaredField("ACCESS_ALLOWED");
        unsafe.putInt(guard, unsafe.objectFieldOffset(field), 42);
        guard.work();

        byte[] content = loadClassContent();
        Class aclass = unsafe.defineClass(null, content, 0, content.length, null, null);
        int i = (int) aclass.getMethod("getI").invoke(aclass.newInstance(), null);
        out.println(i);


        out.println(sizeOf(new Simple()));
    }

    private static byte[] loadClassContent() throws IOException {
        String path = UnsafeForTest.class.getClassLoader().getResource("").getPath();
        String fileName = path + File.separator + "A.class";
        File file = new File(fileName);

        FileInputStream fis = new FileInputStream(file);
        byte[] content = new byte[(int) file.length()];
        fis.read(content);
        fis.close();
        return content;

    }

    private static long sizeOf(Object obj) {
        Unsafe unsafe = getUnsafe();
        Set<Field> fields = new HashSet<>();
        Class c = obj.getClass();
        while (c != Object.class) {
            Field[] declaredFields = c.getDeclaredFields();
            for (Field field : declaredFields) {
                if ((field.getModifiers() & Modifier.STATIC) == 0) {//只筛选非静态
                    fields.add(field);
                }
            }
            c = c.getSuperclass();
        }
        long maxOffset = 0;
        for (Field field : fields) {
            long offset = unsafe.objectFieldOffset(field);
            if (offset > maxOffset) {
                maxOffset = offset;
            }
        }
        return ((maxOffset / 8) + 1) * 8;
    }

    static class Guard {
        private int ACCESS_ALLOWED = 1;

        private boolean allow() {
            return 42 == ACCESS_ALLOWED;
        }

        public void work() {
            if (allow()) {
                out.println("I am working...");
            }
        }
    }

    static class Simple {
        private long l = 0;
        private int i = 10;
        private byte b = 0x10;

        public Simple() {
            this.i = 1;
            out.println("construct......");
        }

        public long getI() {
            return i;
        }
    }
}
