package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.*;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
    private final StudyCafePassRepository passRepository = StudyCafePassRepository.from(studyCafeFileHandler);
    private final StudyCafeLockerPassRepository lockerPassRepository = StudyCafeLockerPassRepository.from(studyCafeFileHandler);

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();
            outputHandler.askPassTypeSelection();

            //입장권 조회
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();
            List<StudyCafePass> hourlyPasses = passRepository.find(studyCafePassType);

            //입장권 선택
            outputHandler.showPassListForSelection(hourlyPasses);
            StudyCafePass selectedPass = inputHandler.getSelectPass(hourlyPasses);

            //사물함 이용
            StudyCafeLockerPass lockerPass = lockerPassRepository.findOneBy(selectedPass);
            askForUsingLockerPass(lockerPass);

            //금액 출력
            outputHandler.showPassOrderSummary(selectedPass, lockerPass);
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void askForUsingLockerPass(StudyCafeLockerPass pass) {
        if (pass == null) return;
        outputHandler.askLockerPass(pass);
        if (inputHandler.getLockerSelection()) {
            pass.use();
        }
    }
}
