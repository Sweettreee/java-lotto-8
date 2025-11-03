package lotto.view;

import camp.nextstep.edu.missionutils.Console;

public class Input {
    public static int readPaidMoney() {
        System.out.println("구입금액을 입력해 주세요.");
        try {
            return Integer.parseInt(Console.readLine());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] 구입 금액은 정수여야합니다.");
        }
        return 0;
    }

    public static String readWinningNumbers() {
        System.out.println("\n당첨 번호를 입력해 주세요.");
        return Console.readLine();
    }

    public static Integer readBonusNumber() {
        System.out.println("\n보너스 번호를 입력해 주세요.");
        try {
            return Integer.parseInt(Console.readLine());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] 보너스 번호는 정수여야합니다.");
        }
        return 0;
    }
}
