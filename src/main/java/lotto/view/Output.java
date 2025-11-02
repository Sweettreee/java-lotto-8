package lotto.view;

import java.util.StringJoiner;
import lotto.model.UserLottoWinningStatus;
import lotto.model.UserLottoWinningStatus.theNumberOfWon;

public class Output {
    public static void printLotto(UserLottoWinningStatus userLottoWinningStatus) {
        System.out.println("\n" + userLottoWinningStatus.getLottoCount() + "개를 구매했습니다.");
        for (int i = 0; i < userLottoWinningStatus.getLottoCount(); i++) {
            StringJoiner joiner = new StringJoiner(", ");
            userLottoWinningStatus.getPurchasedLotto().get(i).getNumbers()
                    .forEach((number) -> joiner.add(number.toString()));
            System.out.println("[" + joiner.toString() + "]");
        }
    }

    public static void printResult(UserLottoWinningStatus userLottoWinningStatus) {
        System.out.println("\n당첨 통계");
        System.out.println("---");
        String[] resultMessage = {
                "3개 일치 (5,000원) - ", "4개 일치 (50,000원) - ", "5개 일치 (1,500,000원) - ", "5개 일치, 보너스 볼 일치 (30,000,000원) - ",
                "6개 일치 (2,000,000,000원) - "
        };
        int count = 0;
        for (theNumberOfWon entry : theNumberOfWon.values()) {
            System.out.println(resultMessage[count++] + userLottoWinningStatus.getEachWinningCount(entry) + "개");
        }
    }

    public static void printProfitRate(UserLottoWinningStatus userLottoWinningStatus) {
        System.out.println("총 수익률은 " + userLottoWinningStatus.getRateOfReturn() + "%입니다.");
    }
}
