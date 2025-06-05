package cleancode.studycafe.tobe.model;

public abstract class StudyCafePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private final double discountRate;

    protected StudyCafePass(StudyCafePassType passType, int duration, int price, double discountRate) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    private static StudyCafePass weeklyOf(int duration, int price, double discountRate) {
        return new WeeklyStudyCafePass(duration, price, discountRate);
    }

    private static StudyCafePass hourlyOf(int duration, int price, double discountRate) {
        return new HourlyStudyCafePass(duration, price, discountRate);
    }

    private static StudyCafePass fixedOf(int duration, int price, double discountRate, StudyCafeLockerPass lockerPass) {
        return new FixedStudyCafePass(duration, price, discountRate, lockerPass);
    }

    public static StudyCafePass of(String[] values, StudyCafeLockerPass lockerPass) {
        StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
        int duration = Integer.parseInt(values[1]);
        int price = Integer.parseInt(values[2]);
        double discountRate = Double.parseDouble(values[3]);
        return switch (studyCafePassType) {
            case HOURLY -> hourlyOf(duration, price, discountRate);
            case WEEKLY -> weeklyOf(duration, price, discountRate);
            case FIXED ->
                    fixedOf(duration, price, discountRate, lockerPass);
        };
    }

    public StudyCafePassType getPassType() {
        return passType;
    }

    public int getTotalPrice() {
        int discountPrice = (int) (price * discountRate);
        if (discountPrice > 0) {
            System.out.println("이벤트 할인 금액: " + discountPrice + "원");
        }
        return price - discountPrice + getLockerPrice();
    }

    public String getMenuString() {
        return passType.toMenuString(duration, price);
    }

    //    3 케이스중 1개만 override 하므로 선정의 후 override
    public boolean isLockerSelected() {
        return false;
    }

    public void selectLocker() {
    }

    public boolean isNotUsingLocker() {
        return true;
    }

    public int getLockerPrice() {
        return 0;
    }

    public String getLockerMenuString() {
        return "";
    }

}
