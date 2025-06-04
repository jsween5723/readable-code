package cleancode.studycafe.tobe.model;

import cleancode.studycafe.tobe.io.StudyCafeFileHandler;

import java.util.List;

public class StudyCafePassRepository {
    private final List<StudyCafePass> passes;

    private StudyCafePassRepository(List<StudyCafePass> passes) {
        this.passes = passes;
    }

    public List<StudyCafePass> find(StudyCafePassType type) {
        return passes.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == type)
                .toList();
    }

    public static StudyCafePassRepository from(StudyCafeFileHandler studyCafeFileHandler) {
        List<StudyCafePass> passes = studyCafeFileHandler.readStudyCafePasses();
        return new StudyCafePassRepository(passes);
    }
}
