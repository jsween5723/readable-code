package cleancode.minesweeper.tobe.minesweeper.io;

import cleancode.minesweeper.tobe.minesweeper.CellAction;
import cleancode.minesweeper.tobe.minesweeper.board.Coordinate;

import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner = new Scanner(System.in);

    public CellAction inputAction() {
        int index = scanner.nextLine().charAt(0) - '0';
        return switch (index) {
            case 1 -> CellAction.OPEN;
            case 2 -> CellAction.TOGGLE_FLAG;
            default -> throw new IllegalArgumentException("잘못된 번호를 선택하셨습니다.");
        };
    }

    public Coordinate inputCoordinate() {
        String input = scanner.nextLine();
        int c = input.charAt(0) - 'A';
        int r = Integer.parseInt(input.substring(1))-1;
        return new Coordinate(r, c);
    }
}
