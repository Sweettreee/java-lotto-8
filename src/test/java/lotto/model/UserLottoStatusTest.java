package lotto.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

// 1. 오름차순으로 잘 저장이 됬는지
// 2. 각 당첨 기준별 횟수와 금액 계산 및 저장
// 3. 수익률 계산 및 저장
public class UserLottoStatusTest {
    static Stream<Arguments> LottoAscendingTestData() {
        return Stream.of(
                // 정렬되지 않은 경우들
                Arguments.of(new Lotto(List.of(6, 5, 4, 3, 2, 1))),
                Arguments.of(new Lotto(List.of(6, 5, 4, 3, 2, 1))),
                Arguments.of(new Lotto(List.of(1, 4, 2, 41, 7, 5)))
        );
    }

    static Stream<Arguments> LottoNumberData() {
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

    @ParameterizedTest(name = "{displayName}")
    @MethodSource("LottoAscendingTestData")
    @DisplayName("로또가_오름차순으로_저장되있는지_검증")
    void sortListOfLotto(Lotto testLottoNumber) {
        assertThat(testLottoNumber.getNumbers()).isSorted();
    }

    @ParameterizedTest(name = "{displayName}(testLottoNumber = {0}, expected = {1})")
    @MethodSource("LottoNumberData")
    @DisplayName("로또_당첨_결과를_반영하여_당첨금액_검증")
    void testUpdateUserLottoStatus(Lotto testLottoNumber, Integer expected) {
        // given
        final int TEST_PURCHASED_COUNT = 1;
        final Integer BONUS_NUMBER = 20;
        List<Integer> testWinningLottoNumber = List.of(1, 2, 3, 4, 5, 6);

        UserLottoStatus testUserStatus = new UserLottoStatus(TEST_PURCHASED_COUNT);
        testUserStatus.addLotto(testLottoNumber);

        // when
        List<Integer> testNumber = testLottoNumber.getNumbers();
        int testExpectedMoney = testUserStatus.checkWinningLotteryNumber(BONUS_NUMBER, testNumber,
                testWinningLottoNumber);

        // then
        assertThat(testExpectedMoney).isEqualTo(expected);
    }

    @Test
    @DisplayName("당첨결과_업데이트_확인")
    void checkUpdateUserLottoStatus() {
        // given
        UserLottoStatus testUserStatus = new UserLottoStatus(1);
        final int TESTMONEY = 5000;
        final int EXPECTEDRESULT = 1;
        final UserLottoStatus.theNumberOfWon TESTTYPE = UserLottoStatus.theNumberOfWon.THREE;

        // when
        testUserStatus.updateUserStatus(TESTMONEY, TESTTYPE);
        int testResult = testUserStatus.getCountofLottoStatus(TESTTYPE);

        // then
        assertThat(testResult).isEqualTo(EXPECTEDRESULT);
    }

    @Test
    @DisplayName("횟수_반환하기_확인")
    void getCountofLottoStatus() {
        // given
        UserLottoStatus testUserStatus = new UserLottoStatus(1);
        final int TESTMONEY = 5000;
        final UserLottoStatus.theNumberOfWon TESTTYPE = UserLottoStatus.theNumberOfWon.THREE;

        // when
        testUserStatus.updateUserStatus(TESTMONEY, TESTTYPE);
        int testResult = testUserStatus.getUserLottoMoney(TESTTYPE);
    }

    @Test
    @DisplayName("모든_당첨금액의_합_구하는지_확인")
    void checkMoneySum() {
        // given
        long testResult = 2001555000;
        UserLottoStatus testUserStatus = new UserLottoStatus(1);
        testUserStatus.updateUserStatus(5000, UserLottoStatus.theNumberOfWon.THREE);
        testUserStatus.updateUserStatus(50000, UserLottoStatus.theNumberOfWon.FOUR);
        testUserStatus.updateUserStatus(1500000, UserLottoStatus.theNumberOfWon.FIVE);
        testUserStatus.updateUserStatus(2000000000, UserLottoStatus.theNumberOfWon.SIX);

        // when
        long testSum = testUserStatus.getSumRewards();

        // then
        assertThat(testSum).isEqualTo(testResult);
    }

    @Test
    @DisplayName("수익률 계산")
    void updateUserLottoStatus() {
        // given
        int testTryNumber = 8;
        int testSum = 5000;
        UserLottoStatus testUserStatus = new UserLottoStatus(testTryNumber);
        final double expected = 62.5;

        // when
        double testResult = testUserStatus.calculateRateOfReturn(testSum);

        // then
        assertThat(testResult).isEqualTo(expected);
    }
}
