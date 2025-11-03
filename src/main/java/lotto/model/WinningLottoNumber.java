package lotto.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WinningLottoNumber {
    private final List<Integer> winningNumbers;
    private final Integer bonusNumber;

    public WinningLottoNumber(List<Integer> winningNumbers, Integer bonusNumber) {
        validate(winningNumbers, bonusNumber);
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    private void validate(List<Integer> winningNumbers, Integer bonusNumber) {
        Set<Integer> numSet = new HashSet<>(winningNumbers);

        if (winningNumbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 당첨 번호는 6개여야 합니다.");
        }

        if (!winningNumbers.stream().allMatch(i -> i >= 1 && i <= 45)) {
            throw new IllegalArgumentException("[ERROR] 로또 당첨 번호는 1부터 45의 범위여야 합니다.");
        }

        if (numSet.size() != winningNumbers.size()) {
            throw new IllegalArgumentException("[ERROR] 로또 당첨 번호는 서로 중복되지 않아야 합니다.");
        }

        if (bonusNumber == null || !(1 <= bonusNumber && bonusNumber <= 45)) {
            throw new IllegalArgumentException("[ERROR] 로또 보너스 번호는 1부터 45의 범위여야 합니다.");
        }

        if (numSet.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 로또 보너스 번호와 당첨 번호는 중복되지 않아야 합니다");
        }

    }

    public List<Integer> getWinningNumbers() {
        return winningNumbers;
    }

    public Integer getBonusNumber() {
        return bonusNumber;
    }
}
