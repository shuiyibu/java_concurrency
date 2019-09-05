package concurrents.immutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class ImmutableTest {

    private final int age;
    private final String name;
    private final List<String> list;


    public ImmutableTest(int age, String name) {
        this.age = age;
        this.name = name;
        list = new ArrayList<>();
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public List<String> getList() {
        return Collections.unmodifiableList(list);//引用对象类的get方法一定不可以把引用返回，比如：return list;
    }
}