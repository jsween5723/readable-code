package cleancode.minesweeper.tobe.io;

import java.util.Scanner;

public class InputHandler {
    private Scanner scanner = new Scanner(System.in);
    public String getUserActionInput() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        return scanner.nextLine();
    }

    public String getCoordinatesInput() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        return scanner.nextLine();
    }
}
