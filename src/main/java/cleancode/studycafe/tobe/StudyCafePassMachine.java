package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();
            outputHandler.askPassTypeSelection();
            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();
            List<StudyCafePass> hourlyPasses = getStudyCafePasses(studyCafePassType);
            outputHandler.showPassListForSelection(hourlyPasses);
            //입장권 선택
            StudyCafePass selectedPass = inputHandler.getSelectPass(hourlyPasses);
            //총 요금 출력
            StudyCafeLockerPass lockerPass = getLockerPass(selectedPass);
            outputHandler.showPassOrderSummary(selectedPass, lockerPass);
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private List<StudyCafePass> getStudyCafePasses(StudyCafePassType type) {
        List<StudyCafePass> studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
        return studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == type)
                .toList();
    }

    private StudyCafeLockerPass getLockerPass(StudyCafePass selectedPass) {
        if (selectedPass.getPassType() != StudyCafePassType.FIXED) return null;
        List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
        StudyCafeLockerPass existsLockerPass = lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                                && option.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);
        if (existsLockerPass != null) {
            outputHandler.askLockerPass(existsLockerPass);
            return inputHandler.getLockerSelection() ? existsLockerPass : null;
        }
        return null;
    }
}
