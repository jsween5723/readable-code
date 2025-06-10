package cleancode.minesweeper.tobe.minesweeper;


import cleancode.minesweeper.tobe.minesweeper.board.Board;
import cleancode.minesweeper.tobe.minesweeper.board.Coordinate;
import cleancode.minesweeper.tobe.minesweeper.io.IOHandler;

public class MinesweeperGame {
    private final IOHandler ioHandler = new IOHandler();
    private final Board board;
    private GameStatus gameStatus = GameStatus.PROCESSING;

    public MinesweeperGame(MineSweeperGameLevel gameLevel) {
        this.board = Board.withConfig(gameLevel.boardConfig);
    }

    public void start() {
        ioHandler.printStart(board);
        while (isGameContinue()) {
            try {
                Coordinate coordinate = ioHandler.selectCellCoordinate();
                board.validateCoordinates(coordinate);
                CellAction actionNumber = ioHandler.selectAction();
                switch (actionNumber) {
                    case OPEN -> board.open(coordinate);
                    case TOGGLE_FLAG -> board.toggleFlag(coordinate);
                }
                ioHandler.printBoard(board);
                checkGameStatus();
            } catch (RuntimeException e) {
                ioHandler.printExceptionMessage(e);
            }
        }
    }

    private boolean isGameContinue() {
        return gameStatus == GameStatus.PROCESSING;
    }

    private void checkGameStatus() {
        if (board.isMineOpened()) {
            gameStatus = GameStatus.LOSE;
            ioHandler.printGameOverMessage();
        }
        if (board.isCleared()) {
            gameStatus = GameStatus.WIN;
            ioHandler.printGameClearMessage();
        }
    }

    enum GameStatus {
        WIN, LOSE, PROCESSING
    }
}
