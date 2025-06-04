package cleancode.studycafe.tobe.model;

public class StudyCafeLockerPass {

    private final int duration;
    private final int price;
    private boolean selected;

    protected StudyCafeLockerPass(int duration, int price) {
        this.duration = duration;
        this.price = price;
    }

    public static StudyCafeLockerPass of(int duration, int price) {
        return new StudyCafeLockerPass(duration, price);
    }

    public int getPrice() {
        return selected ? price : 0;
    }

    public void select() {
        selected = true;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isCompatible(StudyCafePass pass) {
        return duration == pass.getDuration();
    }

    public String toMenuString() {
        return StudyCafePassType.FIXED.toMenuString(duration, price);
    }
}
