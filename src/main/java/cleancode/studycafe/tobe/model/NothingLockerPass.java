package cleancode.studycafe.tobe.model;

public class NothingLockerPass extends StudyCafeLockerPass {
    public NothingLockerPass() {
        super(null, 0, 0);
    }

    @Override
    public boolean isUsed() {
        return false;
    }
}
