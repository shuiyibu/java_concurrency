package concurrents.immutable;

/**
 * 不可变对象
 * A strategy for defining immutable objects
 * JDK不可变对象：
 * String
 */
final public class Person {
    private final String name;
    private final String address;

    public Person(final String name, final String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}