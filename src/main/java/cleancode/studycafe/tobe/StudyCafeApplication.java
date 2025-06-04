package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafePassRepository;

public class StudyCafeApplication {

    public static void main(String[] args) {
        StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();
        StudyCafePassRepository repository = StudyCafePassRepository.from(studyCafeFileHandler);
        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(repository);
        studyCafePassMachine.run();
    }

}
