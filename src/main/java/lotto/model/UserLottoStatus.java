package lotto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserLottoStatus {
    public enum theNumberOfWon {
        THREE,
        FOUR,
        FIVE,
        BONUS,
        SIX
    }

    private int purchasedCount;
    private final List<Lotto> purchasedLotto;

    private final Map<theNumberOfWon, Integer> rewards = new HashMap<theNumberOfWon, Integer>();
    private final Map<theNumberOfWon, Integer> userLottoStatus = new HashMap<theNumberOfWon, Integer>();
    private double rateOfReturn;

    UserLottoStatus(int purchasedCount) {
        purchasedLotto = new ArrayList<>();
        this.purchasedCount = purchasedCount;
        setupRewardTable();
        setupUserLottoStatus();
    }

    private void setupRewardTable() {
        rewards.put(theNumberOfWon.THREE, 5000);
        rewards.put(theNumberOfWon.FOUR, 50000);
        rewards.put(theNumberOfWon.FIVE, 1500000);
        rewards.put(theNumberOfWon.SIX, 2000000000);
        rewards.put(theNumberOfWon.BONUS, 30000000);
    }

    private void setupUserLottoStatus() {
        userLottoStatus.put(theNumberOfWon.THREE, 0);
        userLottoStatus.put(theNumberOfWon.FOUR, 0);
        userLottoStatus.put(theNumberOfWon.FIVE, 0);
        userLottoStatus.put(theNumberOfWon.SIX, 0);
        userLottoStatus.put(theNumberOfWon.BONUS, 0);
    }

    public void addLotto(Lotto newLotto) {
        purchasedLotto.add(newLotto);
    }

    public List<Lotto> getPurchasedLottos() {
        return purchasedLotto;
    }

    public int getLotterWonNumberCount(List<Integer> lottoNumber, List<Integer> winningLottoNumber) {
        List<Integer> winningLotteryNumberCount = lottoNumber.stream()
                .filter(winningLottoNumber::contains)
                .toList();
        return winningLotteryNumberCount.size();
    }

    public int checkWinningLotteryNumber(Integer bonusNumber, List<Integer> lottoNumber,
                                         List<Integer> winningLottoNumber) {
        int size = getLotterWonNumberCount(lottoNumber, winningLottoNumber);
        if (size == 6) {
            return rewards.get(theNumberOfWon.SIX);
        }
        if (size == 5) {
            int bonusSize = getLotterWonNumberCount(lottoNumber, List.of(bonusNumber));
            if (bonusSize == 1) {
                return rewards.get(theNumberOfWon.BONUS);
            }
            return rewards.get(theNumberOfWon.FIVE);
        }
        if (size == 4) {
            return rewards.get(theNumberOfWon.FOUR);
        }
        if (size == 3) {
            return rewards.get(theNumberOfWon.THREE);
        }
        return 0;
    }

    public void updateUserStatus(int money, theNumberOfWon rewardType) {
        userLottoStatus.replace(rewardType, userLottoStatus.get(rewardType) + money);
    }

    public int getCountofLottoStatus(theNumberOfWon rewardType) {
        return userLottoStatus.get(rewardType) / rewards.get(rewardType);
    }

    public int getUserLottoMoney(theNumberOfWon rewardType) {
        return userLottoStatus.get(rewardType);
    }

    public long getSumRewards() {
        int sum = 0;
        for (theNumberOfWon key : rewards.keySet()) {
            sum += userLottoStatus.get(key);
        }

        return sum;
    }

    public double calculateRateOfReturn(int sum) {
        double result = ((double) sum / (purchasedCount * 1000)) * 100;
        return rateOfReturn = Math.round(result * 100) / 100.0;
    }
}



