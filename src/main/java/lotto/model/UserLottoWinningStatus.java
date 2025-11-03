package lotto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserLottoWinningStatus {
    public enum theNumberOfWon {
        THREE,
        FOUR,
        FIVE,
        BONUS,
        SIX
    }

    private final int lottoCount;
    private final List<Lotto> purchasedLotto;

    private final Map<theNumberOfWon, Integer> rewardInstruction = new HashMap<theNumberOfWon, Integer>();
    private final Map<theNumberOfWon, Integer> winningAmounts = new HashMap<theNumberOfWon, Integer>();
    private double rateOfReturn;

    public UserLottoWinningStatus(int paidMoney) {
        validate(paidMoney);
        this.lottoCount = paidMoney / 1000;
        purchasedLotto = new ArrayList<>(this.lottoCount);

        setupRewardInstruction();
        setupUserWinningAmounts();
    }

    private void validate(int paidMoney) {
        if (paidMoney % 1000 != 0 && 1000 <= paidMoney && paidMoney <= 2000000000) {
            throw new IllegalArgumentException("[ERROR] 금액은 1000단위여야 합니다.");
        }

        if (paidMoney < 0) {
            throw new IllegalArgumentException("[ERROR] 금액은 음수를 허용하지 않습니다.");
        }

        if (paidMoney > 2000000000) {
            throw new IllegalArgumentException("[ERROR] 금액은 20억을 넘을 수 없습니다.");
        }
    }

    private void setupRewardInstruction() {
        rewardInstruction.put(theNumberOfWon.THREE, 5000);
        rewardInstruction.put(theNumberOfWon.FOUR, 50000);
        rewardInstruction.put(theNumberOfWon.FIVE, 1500000);
        rewardInstruction.put(theNumberOfWon.SIX, 2000000000);
        rewardInstruction.put(theNumberOfWon.BONUS, 30000000);
    }

    private void setupUserWinningAmounts() {
        winningAmounts.put(theNumberOfWon.THREE, 0);
        winningAmounts.put(theNumberOfWon.FOUR, 0);
        winningAmounts.put(theNumberOfWon.FIVE, 0);
        winningAmounts.put(theNumberOfWon.SIX, 0);
        winningAmounts.put(theNumberOfWon.BONUS, 0);
    }

    public int getLottoCount() {
        return lottoCount;
    }

    public List<Lotto> getPurchasedLotto() {
        return purchasedLotto;
    }

    public double getRateOfReturn() {
        return rateOfReturn;
    }

    public void addLotto(Lotto newLotto) {
        purchasedLotto.add(newLotto);
    }

    public Integer checkWinningLotteryNumber(Integer bonusNumber, List<Integer> lottoNumber,
                                             List<Integer> winningLottoNumber) {
        int commonNumber = getLottoWonNumberCount(lottoNumber, winningLottoNumber);
        if (commonNumber == 6) {
            return rewardInstruction.get(theNumberOfWon.SIX);
        }
        if (commonNumber == 5) {
            int bonusSize = getLottoWonNumberCount(lottoNumber, List.of(bonusNumber));
            if (bonusSize == 1) {
                return rewardInstruction.get(theNumberOfWon.BONUS);
            }
            return rewardInstruction.get(theNumberOfWon.FIVE);
        }
        if (commonNumber == 4) {
            return rewardInstruction.get(theNumberOfWon.FOUR);
        }
        if (commonNumber == 3) {
            return rewardInstruction.get(theNumberOfWon.THREE);
        }
        return 0;
    }

    public int getLottoWonNumberCount(List<Integer> lottoNumber, List<Integer> winningLottoNumber) {
        List<Integer> winningLotteryNumberCount = lottoNumber.stream()
                .filter(winningLottoNumber::contains)
                .toList();
        return winningLotteryNumberCount.size();
    }

    public void updateUserStatus(Integer money) {
        theNumberOfWon rewardType = moneyType(money);
        winningAmounts.replace(rewardType, winningAmounts.get(rewardType) + money);
    }

    public theNumberOfWon moneyType(Integer money) {
        for (Map.Entry<theNumberOfWon, Integer> entry : rewardInstruction.entrySet()) {
            if (Objects.equals(entry.getValue(), money)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public int getUserEachLottoMoney(theNumberOfWon rewardType) {
        return winningAmounts.get(rewardType);
    }

    public void calculateRateOfReturn(long sum) {
        double result = ((double) sum / (lottoCount * 1000)) * 100;
        rateOfReturn = Math.round(result * 100) / 100.0;
    }

    public long getSumRewards() {
        int sum = 0;
        for (theNumberOfWon key : rewardInstruction.keySet()) {
            sum += winningAmounts.get(key);
        }

        return sum;
    }

    public long getEachWinningCount(theNumberOfWon rewardType) {
        return winningAmounts.get(rewardType) / rewardInstruction.get(rewardType);
    }
}



