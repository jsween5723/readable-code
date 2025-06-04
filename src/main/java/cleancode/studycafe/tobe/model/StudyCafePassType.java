package cleancode.studycafe.tobe.model;

import java.util.function.BiFunction;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권", (Integer duration, Integer price) -> String.format("%s시간권 - %d원", duration, price)),
    WEEKLY("주 단위 이용권", (Integer duration, Integer price) -> String.format("%s주권 - %d원", duration, price)),
    FIXED("1인 고정석", (Integer duration, Integer price) -> String.format("%s주권 - %d원", duration, price));

    private final String description;
    private final BiFunction<Integer, Integer, String> toMenu;

    StudyCafePassType(String description, BiFunction<Integer, Integer, String> calculator) {
        this.description = description;
        this.toMenu = calculator;
    }

    public String toMenuString(Integer duration, Integer price) {
        return toMenu.apply(duration, price);
    }
}
