package za.co.onespark.excercise.guidetothegalaxy.common.enums;

public enum Metal {
    GOLD("Gold"),
    SILVER("Silver"),
    IRON("Iron");

    Metal(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return this.name;
    }

    public static Metal fromName(String name) {
        for(Metal metal : values()) {
            if(metal.getName().equals(name)){
                return metal;
            }
        }
        throw new IllegalArgumentException("Value for " + name + " not found");
    }
}
