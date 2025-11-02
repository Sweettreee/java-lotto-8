package lotto.controller;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import lotto.view.IOTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameTest extends IOTest {
    @BeforeEach
    void setUp() {
        systemIn("3000\n1,2,3,4,5,6\n20");
    }

    @Test
    @DisplayName("로또를_뽑고나서부터_당첨결과_업데이트까지_전과정_검증")
    public void testGame() {
        Game game = new Game();

        assertDoesNotThrow(game::run);
    }
}
