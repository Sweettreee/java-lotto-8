package lotto.support;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;

public class RandomNumberGenerator {
    public static List<Integer> numbers = new ArrayList<>(6);

    public static List<Integer> randomNumberGenerate() {
        return numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
    }
}
