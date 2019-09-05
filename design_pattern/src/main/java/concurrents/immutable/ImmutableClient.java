package concurrents.immutable;

import java.util.stream.IntStream;

/**
 *
 */
public class ImmutableClient {
    public static void main(String[] args) {

        //Share data
        Person person = new Person("Alex", "GanSu");
        IntStream.range(0, 5).forEach(i ->
                new UsePersonThread(person).start()
        );
    }
}
