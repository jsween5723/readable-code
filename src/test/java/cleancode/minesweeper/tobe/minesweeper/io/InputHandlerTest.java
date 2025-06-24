package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.CellAction;
import cleancode.minesweeper.tobe.minesweeper.board.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InputHandlerTest {
    private final Scanner scanner = mock(Scanner.class);
    private final InputHandler inputHandler = new InputHandler(scanner);

    @Nested
    @DisplayName("유저액션을 입력받을 수 있다!")
    class InputAction {
        @Test
        @DisplayName("문자열이 입력되면 예외가 발생한다.")
        void inputAction() {
            //given
            String input = "asdasd";
            when(scanner.nextLine()).thenReturn(input);
            //when
            //then
            assertThatThrownBy(inputHandler::inputAction).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("1,2가 아니면 예외가 발생한다.")
        @ParameterizedTest()
        @ValueSource(ints = {0, 3})
        void inputAction3(int target) {
            //given
            String input = target + "";
            when(scanner.nextLine()).thenReturn(input);
            //when
            //then
            assertThatThrownBy(inputHandler::inputAction).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("1이면 OPEN을 반환한다.")
        void inputAction3() {
            //given
            String input = "1";
            when(scanner.nextLine()).thenReturn(input);
            //when
            CellAction result = inputHandler.inputAction();
            //then
            assertThat(result).isEqualTo(CellAction.OPEN);
        }


        @Test
        @DisplayName("2면 TOGGLE_FLAG을 반환한다.")
        void inputAction4() {
            //given
            String input = "2";
            when(scanner.nextLine()).thenReturn(input);
            //when
            CellAction result = inputHandler.inputAction();
            //then
            assertThat(result).isEqualTo(CellAction.TOGGLE_FLAG);
        }
    }

    @Nested
    @DisplayName("좌표를 입력받는다.")
    class InputCoordinate {
        @ParameterizedTest
        @DisplayName("입력받은 정보가 첫문자와 뒤의 정수가 Coordinate로 파싱된다.")
        @ValueSource(ints = {20, 1, 45, 100})
        void inputCoordinate(int row) {
            //given
            String input = "A" + row;
            when(scanner.nextLine()).thenReturn(input);
            Coordinate expected = new Coordinate(row - 1, 0);
            //when
            Coordinate coordinate = inputHandler.inputCoordinate();
            assertThat(coordinate).isEqualTo(expected);
        }
    }

}