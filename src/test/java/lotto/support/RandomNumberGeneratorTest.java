package lotto.support;

import static lotto.support.RandomNumberGenerator.randomNumberGenerate;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RandomNumberGeneratorTest {

    private List<Integer> numbers = new ArrayList<>(6);

    @Test
    @DisplayName("난수가_1부터_45까지_6개를_생성하지_않으면_오류")
    public void verifyRandomGeneratedNumbers() {
        numbers = randomNumberGenerate();

        for (Integer num : numbers) {
            assertThat(num).as("number is our of range(1-45)")
                    .isBetween(1, 45);
        }
    }
}
