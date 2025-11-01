package lotto.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LottoTest {
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

    @ParameterizedTest(name = "{displayName}")
    @MethodSource("LottoNumberSizeTestData")
    @DisplayName("로또_번호의_개수가_6이_아니면_예외가_발생한다")
    void verifyLottoNumberSize(List<Integer> testLottoNumbers) {
        assertThatThrownBy(() -> new Lotto(testLottoNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호는 6개여야 합니다.");
    }

    @ParameterizedTest(name = "{displayName}")
    @MethodSource("LottoNumberRangeTestData")
    @DisplayName("로또_번호_범위가_1부터_45가_아니면_예외가_발생한다")
    void verifyLottoNumberRange(List<Integer> testLottoNumbers) {
        assertThatThrownBy(() -> new Lotto(testLottoNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호는 1부터 45의 범위여야 합니다.");
    }

    @ParameterizedTest(name = "{displayName}")
    @MethodSource("LottoNumberDuplicationTestData")
    @DisplayName("로또_번호에_중복된_숫자가_있으면_예외가_발생한다")
    void verifyLottoNumberDuplication(List<Integer> testLottoNumbers) {
        assertThatThrownBy(() -> new Lotto(testLottoNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호는 서로 중복되지 않아야 합니다.");
    }
}
