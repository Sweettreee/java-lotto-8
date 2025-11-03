package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import lotto.model.UserLottoWinningStatus.theNumberOfWon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

// 1. 오름차순으로 잘 저장이 됬는지
// 2. 각 당첨 기준별 횟수와 금액 계산 및 저장
// 3. 수익률 계산 및 저장
public class UserLottoWinningStatusTest {
    static Stream<Arguments> LottoAscendingTestData() {
        return Stream.of(
                // 정렬되지 않은 경우들
                Arguments.of(new Lotto(List.of(6, 5, 4, 3, 2, 1))),
                Arguments.of(new Lotto(List.of(6, 5, 4, 3, 2, 1))),
                Arguments.of(new Lotto(List.of(1, 4, 2, 41, 7, 5)))
        );
    }

    static Stream<Arguments> LottoNumberTestData() {
        return Stream.of(
                Arguments.of(new Lotto(List.of(1, 2, 3, 4, 5, 6)), 2000000000), // 6개 일치
                Arguments.of(new Lotto(List.of(1, 2, 3, 4, 5, 20)), 30000000), // 5개 일치
                Arguments.of(new Lotto(List.of(1, 2, 3, 4, 5, 7)), 1500000), // 5개 일치
                Arguments.of(new Lotto(List.of(1, 2, 3, 4, 7, 8)), 50000), // 4개 일치
                Arguments.of(new Lotto(List.of(1, 2, 3, 7, 8, 9)), 5000), // 3개 일치
                Arguments.of(new Lotto(List.of(1, 2, 7, 8, 9, 10)), 0), // 2개 일치
                Arguments.of(new Lotto(List.of(1, 7, 8, 9, 10, 11)), 0), // 1개 일치
                Arguments.of(new Lotto(List.of(7, 8, 9, 10, 11, 12)), 0) // 0개 일치
        );
    }

    static Stream<Arguments> LottoNumberCountTestData() {
        return Stream.of(
                Arguments.of(new Lotto(List.of(1, 2, 3, 4, 5, 6)), theNumberOfWon.SIX), // 6개 일치
                Arguments.of(new Lotto(List.of(1, 2, 3, 4, 5, 20)), theNumberOfWon.BONUS), // 5개 일치
                Arguments.of(new Lotto(List.of(1, 2, 3, 4, 5, 7)), theNumberOfWon.FIVE), // 5개 일치
                Arguments.of(new Lotto(List.of(1, 2, 3, 4, 7, 8)), theNumberOfWon.FOUR), // 4개 일치
                Arguments.of(new Lotto(List.of(1, 2, 3, 7, 8, 9)), theNumberOfWon.THREE) // 3개 일치
        );
    }

    @ParameterizedTest(name = "{displayName}")
    @MethodSource("LottoAscendingTestData")
    @DisplayName("로또가_오름차순으로_저장되있는지_검증")
    void sortListOfLotto(Lotto testLottoNumber) {
        assertThat(testLottoNumber.getNumbers()).isSorted();
    }

    @ParameterizedTest(name = "{displayName}(testLottoNumber = {0}, expected = {1})")
    @MethodSource("LottoNumberTestData")
    @DisplayName("로또_당첨_결과를_반영하여_당첨금액_검증")
    void testUpdateUserLottoStatus(Lotto testLottoNumber, Integer expected) {
        // given
        final int testPaidMoney = 1000;
        final Integer BONUS_NUMBER = 20;
        List<Integer> testWinningLottoNumber = List.of(1, 2, 3, 4, 5, 6);

        UserLottoWinningStatus testUserStatus = new UserLottoWinningStatus(testPaidMoney);
        testUserStatus.addLotto(testLottoNumber);

        // when
        List<Integer> testNumber = testLottoNumber.getNumbers();
        int testExpectedMoney = testUserStatus.checkWinningLotteryNumber(BONUS_NUMBER, testNumber,
                testWinningLottoNumber);

        // then
        assertThat(testExpectedMoney).isEqualTo(expected);
    }

    @Test
    @DisplayName("당첨금액_업데이트_기능_검증")
    void checkUpdateUserLottoStatus() {
        // given
        final int testPaidMoney = 1000;
        UserLottoWinningStatus testUserStatus = new UserLottoWinningStatus(testPaidMoney);

        final int TEST_MONEY = 5000;
        final int expectedResult = 5000;
        final UserLottoWinningStatus.theNumberOfWon TEST_TYPE = UserLottoWinningStatus.theNumberOfWon.THREE;

        // when
        testUserStatus.updateUserStatus(TEST_MONEY);
        int testResult = testUserStatus.getUserEachLottoMoney(TEST_TYPE);

        // then
        assertThat(testResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest(name = "{displayName}")
    @MethodSource("LottoNumberCountTestData")
    @DisplayName("횟수_반환_기능_검증")
    void getCountofLottoStatus(Lotto testLottoNumber, theNumberOfWon moneyType) {
        // given
        final int testPaidMoney = 1000;
        final int expectedResult = 1;
        final Integer BONUS_NUMBER = 20;
        List<Integer> testWinningLottoNumber = List.of(1, 2, 3, 4, 5, 6);

        UserLottoWinningStatus testUserStatus = new UserLottoWinningStatus(testPaidMoney);
        testUserStatus.addLotto(testLottoNumber);

        // when
        List<Integer> testNumber = testLottoNumber.getNumbers();
        int testMoney = testUserStatus.checkWinningLotteryNumber(BONUS_NUMBER, testNumber,
                testWinningLottoNumber);
        testUserStatus.updateUserStatus(testMoney);
        long testResult = testUserStatus.getEachWinningCount(moneyType);

        // then
        assertThat(testResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("모든_당첨금액의_합_구하는지_검증")
    void checkMoneySum() {
        // given
        int testPaidMoney = 1000;
        final long expectedResult = 2001555000;
        UserLottoWinningStatus testUserStatus = new UserLottoWinningStatus(testPaidMoney);
        testUserStatus.updateUserStatus(5000);
        testUserStatus.updateUserStatus(50000);
        testUserStatus.updateUserStatus(1500000);
        testUserStatus.updateUserStatus(2000000000);

        // when
        double testSum = testUserStatus.getSumRewards();

        // then
        assertThat(testSum).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("수익률_계산_기능_검증")
    void updateUserLottoStatus() {
        // given
        final int testPaidMoney = 8000;
        final int testSum = 5000;
        UserLottoWinningStatus testUserStatus = new UserLottoWinningStatus(testPaidMoney);
        final double expectedResult = 62.5;

        // when
        testUserStatus.calculateRateOfReturn(testSum);
        double testResult = testUserStatus.getRateOfReturn();

        // then
        assertThat(testResult).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("입력된_금액이_1000의_배수가_아니면_예외를_발생시킨다")
    void verifyPaidMoneyNotMultipleThousand() {
        final int testPaidMoney = 1010;
        assertThatThrownBy(() -> new UserLottoWinningStatus(testPaidMoney))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 금액은 1000단위여야 합니다.");
    }

    @Test
    @DisplayName("입력된_금액이_음수라면_예외를_발생시킨다")
    void verifyPaidMoneyNotNegative() {
        final int testPaidMoney = -1000;
        assertThatThrownBy(() -> new UserLottoWinningStatus(testPaidMoney))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 금액은 음수를 허용하지 않습니다.");
    }

    @Test
    @DisplayName("입력된_금액이_20억을_초과하면_예외를_발생시킨다")
    void verifyPaidMoneyLimit() {
        final int testPaidMoney = 2100000000;
        assertThatThrownBy(() -> new UserLottoWinningStatus(testPaidMoney))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 금액은 20억을 넘을 수 없습니다.");
    }
}
