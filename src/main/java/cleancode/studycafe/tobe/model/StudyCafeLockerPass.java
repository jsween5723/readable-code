package cleancode.studycafe.tobe.model;

public class StudyCafeLockerPass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private boolean use;

    protected StudyCafeLockerPass(StudyCafePassType passType, int duration, int price) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
    }

    public static StudyCafeLockerPass of(StudyCafePassType passType, int duration, int price) {
        return new StudyCafeLockerPass(passType, duration, price);
    }

    public static StudyCafeLockerPass ofNothing() {
        return new NothingLockerPass();
    }

    public StudyCafePassType getPassType() {
        return passType;
    }

    public int getDuration() {
        return duration;
    }

    public int getPrice() {
        return price;
    }
    public void use() {
        use = true;
    }

    public boolean isUse() {
        return use;
    }

    @Override
    public String toString() {
        return passType.toMenuString(duration, price);
    }
}
