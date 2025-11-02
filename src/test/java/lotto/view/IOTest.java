package lotto.view;

import java.io.ByteArrayInputStream;

public class IOTest {
    public static void systemIn(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }
}
