package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.Cell;

public class OutputHandler {
    public void showInvalidInputError() {
        System.out.println("잘못된 번호를 선택하셨습니다.");
    }
    public void showGameOverMessage() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }
    public void showGameClearMessage() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }
    public void showStartMessage() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    public void showBoard(Cell[][] board) {
        System.out.println("   a b c d e f g h i j");
        for (int col = 0; col < board.length; col++) {
            System.out.printf("%d  ", col + 1);
            for (int row = 0; row < board[0].length; row++) {
                System.out.print(board[col][row] + " ");
            }
            System.out.println();
        }
    }
}
