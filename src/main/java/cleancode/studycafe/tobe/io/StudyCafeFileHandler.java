package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StudyCafeFileHandler {

    public List<StudyCafePass> readStudyCafePasses() {
        List<StudyCafeLockerPass> lockerPasses = readLockerPasses();
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/pass-list.csv"));
            return lines.stream().map(line -> line.split(","))
                    .map(values -> {
                        int duration = Integer.parseInt(values[1]);
                        StudyCafeLockerPass locker = lockerPasses.stream().filter((lockerPass -> lockerPass.isCompatible(duration))).findFirst().orElse(null);
                        return StudyCafePass.of(values, locker);
                    }).toList();
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

    private List<StudyCafeLockerPass> readLockerPasses() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/cleancode/studycafe/locker.csv"));
            return lines.stream().map(line -> line.split(","))
                    .map(StudyCafeLockerPass::fromValues).toList();
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다.", e);
        }
    }

}
