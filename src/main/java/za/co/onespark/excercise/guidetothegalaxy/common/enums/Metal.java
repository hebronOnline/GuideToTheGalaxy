package za.co.onespark.excercise.guidetothegalaxy.common.enums;

public enum Metal {
    GOLD("Gold"),
    SILVER("Silver"),
    IRON("Iron");

    Metal(String name) {
        this.name = name;
    }

    private final String name;

    public boolean containsValidMetal(String metal) {
        Metal.valueOf(metal);
        return true;
    }
}
