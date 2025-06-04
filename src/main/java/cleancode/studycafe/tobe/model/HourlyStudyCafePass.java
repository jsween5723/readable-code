package cleancode.studycafe.tobe.model;

class HourlyStudyCafePass extends StudyCafePass{
    protected HourlyStudyCafePass(int duration, int price, double discountRate) {
        super(StudyCafePassType.HOURLY, duration, price, discountRate);
    }
}
