package lotto.controller;

import static lotto.support.RandomNumberGenerator.randomNumberGenerate;

import java.util.Arrays;
import java.util.List;
import lotto.model.Lotto;
import lotto.model.UserLottoWinningStatus;
import lotto.model.WinningLottoNumber;
import lotto.support.Parsing;
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
            while (true) {
                try {
                    Lotto newLotto = pickLotto(randomNumberGenerate());
                    userLottoWinningStatus.addLotto(newLotto);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
    }

    WinningLottoNumber pickWinningLotto() {
        List<Integer> winningNumbers;
        Integer bonusNumber;
        while (true) {
            try {
                winningNumbers = parsingWinningLottoNumbers(Input.readWinningNumbers());
                bonusNumber = Input.readBonusNumber();
                break;
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        return new WinningLottoNumber(winningNumbers, bonusNumber);
    }

    List<Integer> parsingWinningLottoNumbers(String winningLottoNumbers) {
        int[] tmp = Parsing.parseStringIntoInteger(winningLottoNumbers);
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
                System.out.println(e.getMessage());
            }
        }
        return userLottoWinningStatus;
    }

    public void run() {
        UserLottoWinningStatus userLottoWinningStatus = readPaidMoneyThenMakeUserStatus(); // 금액 입력 및 현황판 생성
        pickLottos(userLottoWinningStatus); // 로또 생성
        Output.printLotto(userLottoWinningStatus); // 생성한 로또 출력 함수
        WinningLottoNumber winningLottoNumber = pickWinningLotto(); // 당첨 번호 생성 및 저장
        checkLotto(winningLottoNumber, userLottoWinningStatus); // 로또 당첨 현황 업데이트
        updateProfitRate(userLottoWinningStatus); // 로또 수익률 계산 및 저장
        Output.printResult(userLottoWinningStatus); // 당첨 종류별 결과 출력
        Output.printProfitRate(userLottoWinningStatus); // 수익률 출력
    }
}
