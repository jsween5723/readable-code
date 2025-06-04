package cleancode.studycafe.tobe.model;

import cleancode.studycafe.tobe.io.StudyCafeFileHandler;

import java.util.List;

public class StudyCafeLockerPassRepository {
    private final List<StudyCafeLockerPass> passes;

    private StudyCafeLockerPassRepository(List<StudyCafeLockerPass> passes) {
        this.passes = passes;
    }

    public StudyCafeLockerPass findOneBy(StudyCafePass selectedPass) {
        if (selectedPass.getPassType() != StudyCafePassType.FIXED) return null;
        return passes.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                                && option.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);
    }

    public static StudyCafeLockerPassRepository from(StudyCafeFileHandler studyCafeFileHandler) {
        List<StudyCafeLockerPass> passes = studyCafeFileHandler.readLockerPasses();
        return new StudyCafeLockerPassRepository(passes);
    }
}
