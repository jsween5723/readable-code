package cleancode.studycafe.tobe.model;

class FixedStudyCafePass extends StudyCafePass{
    private final StudyCafeLockerPass lockerPass;
    protected FixedStudyCafePass(int duration, int price, double discountRate, StudyCafeLockerPass lockerPass) {
        super(StudyCafePassType.FIXED, duration, price, discountRate);
        this.lockerPass = lockerPass;
    }


    @Override
    public boolean isNotUsingLocker() {
        return false;
    }

    @Override
    public int getLockerPrice() {
        return lockerPass.getPrice();
    }

    @Override
    public String getLockerMenuString() {
        return lockerPass.toMenuString();
    }

    @Override
    public void selectLocker() {
        lockerPass.select();
    }

    @Override
    public boolean isLockerSelected() {
        return lockerPass.isSelected();
    }
}
