package lotto.support;

import java.util.Arrays;

public class Parsing {
    public static int[] parseStringIntoInteger(String winningLottoNumbers) {
        try {
            return Arrays.stream(winningLottoNumbers.split(",")).mapToInt(Integer::parseInt).toArray();
        } catch (NumberFormatException e) {
            throw new NumberFormatException("[ERROR] 당첨 번호는 정수여야합니다.");
        }
    }
}
