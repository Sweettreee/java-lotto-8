package lotto.controller;

import static lotto.support.RandomNumberGenerator.randomNumberGenerate;

import java.util.Arrays;
import java.util.List;
import lotto.model.Lotto;
import lotto.model.UserLottoWinningStatus;
import lotto.model.WinningLottoNumber;
import lotto.view.Input;
import lotto.view.Output;

public class Game {
    void updateProfitRate(UserLottoWinningStatus userLottoWinningStatus) {
        long profitSum = userLottoWinningStatus.getSumRewards();
        userLottoWinningStatus.calculateRateOfReturn(profitSum);
    }

    void checkLotto(WinningLottoNumber winningLotto, UserLottoWinningStatus userLottoWinningStatus) {
        for (int i = 0; i < userLottoWinningStatus.getLottoCount(); i++) {
            Lotto currentLotto = userLottoWinningStatus.getPurchasedLotto().get(i);
            Integer winningMoney = userLottoWinningStatus.checkWinningLotteryNumber(winningLotto.getBonusNumber(),
                    currentLotto.getNumbers(),
                    winningLotto.getWinningNumbers());
            if (winningMoney != 0) {
                userLottoWinningStatus.updateUserStatus(winningMoney);
            }
        }
    }

    Lotto pickLotto(List<Integer> lotto) {
        return new Lotto(lotto);
    }

    void pickLottos(UserLottoWinningStatus userLottoWinningStatus) {
        int lottoCount = userLottoWinningStatus.getLottoCount();
        for (int i = 0; i < lottoCount; i++) {
            Lotto newLotto = pickLotto(randomNumberGenerate());
            userLottoWinningStatus.addLotto(newLotto);
        }
    }

    WinningLottoNumber pickWinningLotto() {
        List<Integer> winningNumbers = parsingWinningLottoNumbers(Input.readWinningNumbers());
        Integer bonusNumber = Input.readBonusNumber();
        return new WinningLottoNumber(winningNumbers, bonusNumber);
    }

    List<Integer> parsingWinningLottoNumbers(String winningLottoNumbers) {
        int[] tmp = Arrays.stream(winningLottoNumbers.split(",")).mapToInt(Integer::parseInt).toArray();
        return Arrays.stream(tmp).boxed().toList();
    }

    UserLottoWinningStatus readPaidMoneyThenMakeUserStatus() {
        UserLottoWinningStatus userLottoWinningStatus;
        while (true) {
            try {
                int paidMoney = Input.readPaidMoney(); // 금액 입력
                userLottoWinningStatus = new UserLottoWinningStatus(paidMoney);// 로또 유저 테이블 생성
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] 금액은 1000단위여야 합니다.");
            }
        }
        return userLottoWinningStatus;
    }

    public void run() {
        // 금액 입력 및 현황판 생성
        UserLottoWinningStatus userLottoWinningStatus = readPaidMoneyThenMakeUserStatus();

        // 로또 생성
        pickLottos(userLottoWinningStatus);

        // 생성한 로또 출력 함수
        Output.printLotto(userLottoWinningStatus);
        // 당첨 번호 생성 및 저장
        WinningLottoNumber winningLottoNumber = pickWinningLotto();

        // 로또 당첨 현황 업데이트
        checkLotto(winningLottoNumber, userLottoWinningStatus);

        // 로또 수익률 계산 및 저장
        updateProfitRate(userLottoWinningStatus);

        // 결과 출력
        Output.printResult(userLottoWinningStatus);
        Output.printProfitRate(userLottoWinningStatus);
    }
}
