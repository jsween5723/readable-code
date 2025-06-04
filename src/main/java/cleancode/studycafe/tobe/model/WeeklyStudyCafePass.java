package cleancode.studycafe.tobe.model;

class WeeklyStudyCafePass extends StudyCafePass{
    protected WeeklyStudyCafePass(int duration, int price, double discountRate) {
        super(StudyCafePassType.WEEKLY, duration, price, discountRate);
    }
}
