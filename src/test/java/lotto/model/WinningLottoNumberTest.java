package lotto.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class WinningLottoNumberTest {
    private final Integer TEST_BONUS_NUMBER = 1;

    static Stream<Arguments> LottoNumberSizeTestData() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 4, 5, 6)), // 개수 부족
                Arguments.of(List.of(1, 2, 3, 4, 5, 6, 7)) // 개수 초과
        );
    }

    static Stream<Arguments> LottoNumberRangeTestData() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 3, 4, 5, 50)), // 45가 넘은 숫자 포함
                Arguments.of(List.of(-1, 1, 2, 4, 5, 6)), // 1보다 작은 숫자 포함
                Arguments.of(List.of(-9, 1, 2, 4, 5, 49)) // 45넘는 숫자 & 1보다 작은 숫자 포함
        );
    }

    static Stream<Arguments> LottoNumberDuplicationTestData() {
        return Stream.of(
                Arguments.of(List.of(1, 1, 3, 4, 5, 6)), // 중복된 숫자 2개
                Arguments.of(List.of(1, 1, 1, 4, 5, 6)), // 중복된 숫자 3개
                Arguments.of(List.of(1, 1, 1, 1, 1, 6)), // 중복된 숫자 4개
                Arguments.of(List.of(1, 1, 1, 1, 5, 6)), // 중복된 숫자 5개
                Arguments.of(List.of(1, 1, 1, 1, 1, 1)) // 중복된 숫자 6개
        );
    }

    static Stream<Arguments> LottoBonusNumberTestData() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 3, 4, 5, 6), -1),
                Arguments.of(List.of(1, 2, 3, 4, 5, 6), 47)
        );
    }

    @ParameterizedTest(name = "{displayName}")
    @MethodSource("LottoNumberSizeTestData")
    @DisplayName("담첨_번호의_개수가_6이_아니면_예외가_발생한다")
    void verifyWinningLottoNumberSize(List<Integer> testLottoNumbers) {
        assertThatThrownBy(() -> new WinningLottoNumber(testLottoNumbers, TEST_BONUS_NUMBER))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 당첨 번호는 6개여야 합니다.");
    }

    @ParameterizedTest(name = "{displayName}")
    @MethodSource("LottoNumberRangeTestData")
    @DisplayName("당첨_번호_범위가_1부터_45가_아니면_예외가_발생한다")
    void verifyWinningLottoNumberRange(List<Integer> testLottoNumbers) {
        assertThatThrownBy(() -> new WinningLottoNumber(testLottoNumbers, TEST_BONUS_NUMBER))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 당첨 번호는 1부터 45의 범위여야 합니다.");
    }

    @ParameterizedTest(name = "{displayName}")
    @MethodSource("LottoNumberDuplicationTestData")
    @DisplayName("당첨_번호에_중복된_숫자가_있으면_예외가_발생한다")
    void verifyWinningLottoNumberDuplication(List<Integer> testLottoNumbers) {
        assertThatThrownBy(() -> new WinningLottoNumber(testLottoNumbers, TEST_BONUS_NUMBER))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 당첨 번호는 서로 중복되지 않아야 합니다.");
    }

    @ParameterizedTest(name = "{displayName}(lottoNumbers = {0}, bonusNumber = {1})")
    @MethodSource("LottoBonusNumberTestData")
    @DisplayName("보너스_번호_범위가_1부터_45가_아니면_예외가_발생한다")
    void verifyBonusLottoNumberRange(List<Integer> testLottoNumbers, Integer testBonusNumber) {
        assertThatThrownBy(() -> new WinningLottoNumber(testLottoNumbers, testBonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 보너스 번호는 1부터 45의 범위여야 합니다.");
    }

    @Test
    @DisplayName("보너스_번호와_당첨_번호가_중복되면_예외가_발생한다")
    void verifyBonusNUmberDuplication() {
        List<Integer> testWinningNumbers = List.of(1, 2, 3, 4, 5, 20);
        Integer testBonusNumber = 20;
        assertThatThrownBy(() -> new WinningLottoNumber(testWinningNumbers, testBonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 보너스 번호와 당첨 번호는 중복되지 않아야 합니다");
    }
}
