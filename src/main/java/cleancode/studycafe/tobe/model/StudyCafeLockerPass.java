package cleancode.studycafe.tobe.model;

public class StudyCafeLockerPass {

    private final int duration;
    private final int price;
    private boolean selected;

    private StudyCafeLockerPass(int duration, int price) {
        this.duration = duration;
        this.price = price;
    }

    public static StudyCafeLockerPass of(int duration, int price) {
        return new StudyCafeLockerPass(duration, price);
    }

    int getPrice() {
        return selected ? price : 0;
    }

    public boolean isCompatible(int passDuration) {
        return duration == passDuration;
    }

    void select() {
        selected = true;
    }

    boolean isSelected() {
        return selected;
    }

    String toMenuString() {
        return StudyCafePassType.FIXED.toMenuString(duration, price);
    }
}
