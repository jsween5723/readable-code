package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.board.Board;

public class OutputHandler {

    public void printBoard(Board board) {
        System.out.println(board);
        System.out.println();
    }

    public void printGameOverMessage() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    public void printGameClearMessage() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    public void printStartMessage() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    public void printInputActionMessage() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    public void printInputCoordinateMessage() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }
    public void printExceptionMessage(RuntimeException exception) {
        System.out.println(exception.getMessage());
    }
}
